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
package com.escape.synder.generators.xmlpull;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.escape.synder.generators.GenerateContext;
import com.escape.synder.generators.GeneratorImpl;
import com.escape.synder.generators.ModuleGeneratorImpl;
import com.sun.syndication.feed.module.Module;

/**
 * Feed generator base class for org.xmlpull.v1.XmlSerializer.
 * @author escape-llc
 */
public abstract class XmlPullGeneratorImpl extends GeneratorImpl {
	protected XmlPullGeneratorImpl(String tag, GenerateContext ctx) {
		super(tag, ctx);
	}
	protected static void applyModules(XmlSerializer xs, List<Module> mods, List<ModuleGeneratorImpl> fns) throws Exception {
		for(final Module mx: mods) {
			final ModuleGeneratorImpl mgi = findGenerator(mx, fns);
			if(mgi != null) {
				mgi.generate(mx, xs);
				xs.ignorableWhitespace("\n");
			}
		}
	}
	/**
	 * Find the implementation for this module.
	 * @param mx Source module.
	 * @param mgis Source list of all generators.
	 * @return Module generator or <b>null</b>.
	 */
	protected static ModuleGeneratorImpl findGenerator(Module mx, List<ModuleGeneratorImpl> mgis) {
		for(final ModuleGeneratorImpl mgi: mgis) {
			if(mgi.detect(mx.getUri())) return mgi;
		}
		return null;
	}
	/**
	 * Generate a date-formatted element.
	 * If date is <b>null</b>, no output is generated.
	 * @param xs Target serializer.
	 * @param nsuri namespace URI.
	 * @param tag tag name.
	 * @param dt Source date.
	 * @param df Source formatter.
	 * @throws Exception
	 */
	protected static void dateElement(XmlSerializer xs, String nsuri,
			String tag, Date dt, DateFormat df) throws Exception {
		if(df == null) throw new IllegalArgumentException("df");
		if (dt != null) {
			xs.startTag(nsuri, tag);
			xs.text(df.format(dt));
			xs.endTag(nsuri, tag);
		}
	}
	/**
	 * Generate element if string is not null-or-empty.
	 * @param xs Target serializer.
	 * @param nsuri namespace URI.
	 * @param tag tag name.
	 * @param tx Source text.
	 * @throws Exception
	 */
	protected static void textElementOptional(XmlSerializer xs, String nsuri,
			String tag, String tx) throws Exception {
		if (tx != null && tx.length() > 0) {
			xs.startTag(nsuri, tag);
			xs.text(tx);
			xs.endTag(nsuri, tag);
		}
	}
	/**
	 * Generate possibly-empty element.
	 * @param xs Target serializer.
	 * @param nsuri namespace URI.
	 * @param tag tag name.
	 * @param tx Source text.
	 * @throws Exception
	 */
	protected static void textElement(XmlSerializer xs, String nsuri,
			String tag, String tx) throws Exception {
		xs.startTag(nsuri, tag);
		if (tx != null && tx.length() > 0) xs.text(tx);
		xs.endTag(nsuri, tag);
	}
	/**
	 * Generate possibly-empty CDATA section.
	 * @param xs Target serializer.
	 * @param nsuri namespace URI.
	 * @param tag tag name.
	 * @param tx Source text.
	 * @throws Exception
	 */
	protected static void cdataElement(XmlSerializer xs, String nsuri,
			String tag, String tx) throws Exception {
		xs.startTag(nsuri, tag);
		if (tx != null && tx.length() > 0) xs.cdsect(tx);
		xs.endTag(nsuri, tag);
	}
	/**
	 * Generate a sequence of elements from given list.
	 * @param xs Output medium.
	 * @param lx Source list.
	 * @param nsuri Output namespace URI.
	 * @param tag Output tag name.
	 * @throws Exception
	 */
	public static void list(XmlSerializer xs, List<String> lx, String nsuri, String tag) throws Exception {
		if(lx.size() > 0) {
			for(final String cx: lx) {
				textElement(xs, nsuri, tag, cx);
			}
		}
	}
	/**
	 * Generate a sequence of Date elements (in W3C format) from given list.
	 * @param xs Output medium.
	 * @param lx Source list.
	 * @param nsuri Output namespace URI.
	 * @param tag Output tag name.
	 * @throws Exception
	 */
	public static void listDates(GenerateContext ctx, XmlSerializer xs, List<Date> lx, String nsuri, String tag) throws Exception {
		if(lx.size() > 0) {
			final DateFormat df = ctx.getDateFormatW3C();
			for(final Date cx: lx) {
				dateElement(xs, nsuri, tag, cx, df);
			}
		}
	}
}
