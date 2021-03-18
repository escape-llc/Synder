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

import org.xmlpull.v1.XmlSerializer;

import com.escape.synder.generators.GenerateContext;
import com.escape.synder.generators.ModuleGeneratorImpl;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.feed.synd.SyndLink;

/**
 * XmlSerializer implementation for RSS 2.0.
 * 
 * @author escape-llc
 * 
 */
public final class Rss20 extends XmlPullGeneratorImpl {
	public Rss20(GenerateContext ctx) {
		super("rss_2.0", ctx);
	}
	void categoryElement(XmlSerializer xs, SyndCategory sc) throws Exception {
		xs.startTag(null, "category");
		if (sc.getTaxonomyUri() != null) {
			xs.attribute(null, "domain", sc.getTaxonomyUri());
		}
		xs.text(sc.getName());
		xs.endTag(null, "category");
	}
	void imageElement(XmlSerializer xs, SyndImage si) throws Exception {
		xs.startTag(null, "image");
		textElementOptional(xs, null, "url", si.getUrl());
		textElementOptional(xs, null, "title", si.getTitle());
		textElementOptional(xs, null, "link", si.getLink());
		xs.endTag(null, "image");
	}
	void enclosureElement(XmlSerializer xs, SyndEnclosure se) throws Exception {
		xs.startTag(null, "enclosure");
		if (se.getType() != null) {
			xs.attribute(null, "type", se.getType());
		}
		if (se.getLength() > 0) {
			xs.attribute(null, "length", Long.toString(se.getLength()));
		}
		if (se.getUrl() != null) {
			xs.attribute(null, "url", se.getUrl());
		}
		xs.endTag(null, "enclosure");
	}
	/**
	 * Generate Feed. Expects XmlSerializer.
	 */
	@Override
	public <T> void generate(SyndFeed sf, String enc, T outputMedium)
			throws Exception {
		if (!(outputMedium instanceof XmlSerializer))
			throw new IllegalArgumentException("outputMedium");
		final XmlSerializer xs = (XmlSerializer) outputMedium;
		for (final ModuleGeneratorImpl mgi : fns) {
			xs.setPrefix(mgi.getPrefix(), mgi.getTag());
		}
		for (final ModuleGeneratorImpl mgi : ins) {
			if (xs.getPrefix(mgi.getTag(), false) == null)
				xs.setPrefix(mgi.getPrefix(), mgi.getTag());
		}
		xs.startDocument(enc, false);
		try {
			xs.startTag(null, "rss");
			try {
				xs.attribute(null, "version", "2.0");
				xs.startTag(null, "channel");
				textElementOptional(xs, null, "title", sf.getTitle());
				if(sf.getDescriptionEx() != null) {
					textElementOptional(xs, null, "description", sf.getDescriptionEx().getValue());
				}
				dateElement(xs, null, "pubDate", sf.getPublishedDate(), ctx
						.getDateFormatRFC822());
				dateElement(xs, null, "lastBuildDate", sf.getUpdatedDate(), ctx
						.getDateFormatRFC822());
				final SyndImage img = sf.getImage();
				if(img != null) {
					imageElement(xs, img);
				}
				textElementOptional(xs, null, "link", sf.getLink());
				applyModules(xs, sf.getModules(), fns);
				for (final SyndEntry se : sf.getEntries()) {
					xs.startTag(null, "item");
					try {
						if (se.getLink() != null) {
							textElement(xs, null, "link", se.getLink());
						} else {
							for (final SyndLink sl : se.getLinks()) {
								final String tag = sl.getType();
								if ("guid".equals(tag)) {
									xs.startTag(null, "guid");
										xs.attribute(null, "isPermaLink", sl
												.getRel() != null ? sl.getRel()
												: "false");
										xs.text(sl.getHref());
										xs.endTag(null, "guid");
								} else if("source".equals(tag)) {
									xs.startTag(null, "source");
									xs.attribute(null, "href", sl.getHref());
									xs.text(sl.getTitle());
									xs.endTag(null, "source");
								} else if("comments".equals(tag)) {
									xs.startTag(null, "comments");
									xs.text(sl.getHref());
									xs.endTag(null, "comments");
								} else {
									textElement(xs, null, "link", sl.getHref());
								}
							}
						}
						textElementOptional(xs, null, "title", se.getTitle());
						textElementOptional(xs, null, "author", se.getAuthor());
						dateElement(xs, null, "pubDate", se.getPublishedDate(),
								ctx.getDateFormatRFC822());
						for(final SyndCategory sc: se.getCategories()) {
							categoryElement(xs, sc);
						}
						for(final SyndEnclosure senc: se.getEnclosures()) {
							enclosureElement(xs, senc);
						}
						applyModules(xs, se.getModules(), ins);
						if (se.getDescription() != null)
							cdataElement(xs, null, "description", se
									.getDescription().getValue());
						else {
							for (final SyndContent sc : se.getContents()) {
								cdataElement(xs, null, "description", sc
										.getValue());
								break;
							}
						}
					} finally {
						xs.endTag(null, "item");
						xs.ignorableWhitespace("\n");
					}
				}
			} finally {
				xs.endTag(null, "channel");
				xs.endTag(null, "rss");
			}
		} finally {
			xs.endDocument();
		}
	}
}
