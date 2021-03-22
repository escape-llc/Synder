/*
 * Copyright 2010 eScape Technology LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.escape_technology_llc.synder.generators.xmlpull;

import java.io.OutputStream;

import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.escape_technology_llc.synder.generators.GeneratorImpl;
import com.escape_technology_llc.synder.generators.DefaultGenerateContext.Generate;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * Generate via XmlSerializer obtained from XmlPullParserFactory.
 * @author escape-llc
 *
 */
public class GenerateXmlPull extends Generate {
	@Override
	public void generate(SyndFeed sf, GeneratorImpl sgi, OutputStream os,
			String enc) throws Exception {
		final XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
		final XmlSerializer xs = xppf.newSerializer();
		try {
			xs.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
		}
		catch(Exception ise) {
		}
		generateCommon(sf, sgi, xs, os, enc);
	}
	/**
	 * Common setup and processing of XmlSerializer.
	 * @param sf Source feed.
	 * @param sgi Source generator.
	 * @param xs Target serializer.
	 * @param os Target stream.
	 * @param enc Stream encoding.
	 * @throws Exception on errors.
	 */
	protected void generateCommon(SyndFeed sf, GeneratorImpl sgi,
			XmlSerializer xs, OutputStream os, String enc)
			throws Exception {
		xs.setOutput(os, enc);
		sgi.generate(sf, enc, xs);
	}
}
