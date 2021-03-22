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

import org.xmlpull.v1.XmlSerializer;

import com.escape_technology_llc.synder.generators.GenerateContext;
import com.escape_technology_llc.synder.generators.ModuleGeneratorImpl;
import com.sun.syndication.feed.module.ItunesModule;
import com.sun.syndication.feed.module.Module;

public class Itunes extends ModuleGeneratorImpl {
	public Itunes(GenerateContext ctx) {
		super(ItunesModule.URI, "itunes", ctx);
	}
	@Override
	public <T> void generate(Module mx, T outputMedium) throws Exception {
		if (!(outputMedium instanceof XmlSerializer))
			throw new IllegalArgumentException("outputMedium");
		if (!(mx instanceof ItunesModule))
			throw new IllegalArgumentException("mx");
		final XmlSerializer xs = (XmlSerializer) outputMedium;
		final ItunesModule mod = (ItunesModule)mx;
		if(mod.getAuthor() != null) {
			XmlPullGeneratorImpl.textElement(xs, getTag(), "author", mod.getAuthor());
		}
		if(mod.getDuration() != null) {
			XmlPullGeneratorImpl.textElement(xs, getTag(), "duration", mod.getDuration());
		}
		if(mod.getExplicit() != null) {
			XmlPullGeneratorImpl.textElement(xs, getTag(), "explicit", mod.getExplicit());
		}
		if(mod.getKeywords() != null) {
			XmlPullGeneratorImpl.textElement(xs, getTag(), "keywords", mod.getKeywords());
		}
		if(mod.getSubtitle() != null) {
			XmlPullGeneratorImpl.textElement(xs, getTag(), "subtitle", mod.getSubtitle());
		}
		if(mod.getSummary() != null) {
			XmlPullGeneratorImpl.cdataElement(xs, getTag(), "summary", mod.getSummary().getValue());
		}
	}
}
