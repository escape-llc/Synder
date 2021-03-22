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
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndPerson;

/**
 * XmlSerializer implementation for Atom 1.0.
 * 
 * @author escape-llc
 * 
 */
public final class Atom10 extends XmlPullGeneratorImpl {
	final String defaultns;
	public Atom10(GenerateContext ctx) {
		super("atom_1.0", ctx);
		final String optnull = ctx.getOption(GenerateContext.Features.DEFAULT_NAMESPACE_PREFIX_NULL);
		if(optnull != null) {
			// use NULL as TAG mapping.
			defaultns = null;
		}
		else {
			// use option value or default ""
			final String opt = ctx.getOption(GenerateContext.Features.DEFAULT_NAMESPACE_PREFIX);
			defaultns = opt != null ? opt : "";
		}
	}
	void categoryElement(XmlSerializer xs, String npx, SyndCategory sc) throws Exception {
		xs.startTag(npx, "category");
		xs.attribute(null, "term", sc.getName());
		attributeOptional(xs, "scheme", sc.getTaxonomyUri());
		attributeOptional(xs, "label", sc.getLabel());
		xs.endTag(npx, "category");
	}
	void enclosureElement(XmlSerializer xs, String npx, SyndEnclosure se) throws Exception {
		xs.startTag(npx, "link");
		xs.attribute(null, "rel", "enclosure");
		attributeOptional(xs, "type", se.getType());
		if (se.getLength() > 0) {
			xs.attribute(null, "length", Long.toString(se.getLength()));
		}
		if (se.getUrl() != null) {
			xs.attribute(null, "href", se.getUrl());
		}
		xs.endTag(npx, "link");
	}
	void linkElement(XmlSerializer xs, String npx, SyndLink sl) throws Exception {
		xs.startTag(npx, "link");
		attributeOptional(xs, "rel", sl.getRel());
		attributeOptional(xs, "type", sl.getType());
		xs.attribute(null, "href", sl.getHref());
		if (sl.getTitle() != null && sl.getTitle().length() > 0)
			xs.text(sl.getTitle());
		xs.endTag(npx, "link");
	}
	void contentElement(XmlSerializer xs, String npx, String tag, SyndContent sc) throws Exception {
		xs.startTag(npx, tag);
		// TODO map other types to Atom text/html
		xs.attribute(null, "type", sc.getType());
		xs.text(sc.getValue());
		xs.endTag(npx, tag);
	}
	void generatorElement(XmlSerializer xs, String npx, SyndLink sc) throws Exception {
		xs.startTag(npx, "generator");
		attributeOptional(xs, "uri", sc.getHref());
		attributeOptional(xs, "version", sc.getType());
		xs.text(sc.getTitle());
		xs.endTag(npx, "generator");
	}
	void personElement(XmlSerializer xs, String npx, String tag, SyndPerson sp) throws Exception {
		xs.startTag(npx, tag);
		textElement(xs, npx, "name", sp.getName());
		textElementOptional(xs, npx, "email", sp.getEmail());
		textElementOptional(xs, npx, "uri", sp.getUri());
		xs.endTag(npx, tag);
	}
	void attributeOptional(XmlSerializer xs, String attr, String value) throws Exception {
		if (value != null && value.length() > 0)
			xs.attribute(null, attr, value);
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
		xs.setPrefix(defaultns, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI);
		for (final ModuleGeneratorImpl mgi : fns) {
			xs.setPrefix(mgi.getPrefix(), mgi.getTag());
		}
		for (final ModuleGeneratorImpl mgi : ins) {
			if (xs.getPrefix(mgi.getTag(), false) == null)
				xs.setPrefix(mgi.getPrefix(), mgi.getTag());
		}
		xs.startDocument(enc, false);
		try {
			xs.startTag(com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "feed");
			try {
				xs.attribute(null, "version", "1.0");
				if(sf.getTitleEx() != null) {
					contentElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "title", sf.getTitleEx());
				}
				if(sf.getDescriptionEx() != null) {
					contentElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "subtitle", sf.getDescriptionEx());
				}
				if(sf.getGenerator() != null) {
					generatorElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, sf.getGenerator());
				}
				for(final SyndPerson sp: sf.getAuthors()) {
					personElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "author", sp);
				}
				for(final SyndPerson sp: sf.getContributors()) {
					personElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "contributor", sp);
				}
				for (final SyndLink sl : sf.getLinks()) {
					linkElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, sl);
				}
				final SyndImage img = sf.getImage();
				if(img != null) {
					textElementOptional(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "icon", img.getUrl());
				}
				final SyndImage img2 = sf.getLogo();
				if(img2 != null) {
					textElementOptional(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "logo", img2.getUrl());
				}
				final SyndContent rights = sf.getRights();
				if(rights != null) {
					contentElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "rights", rights);
				}
				applyModules(xs, sf.getModules(), fns);
				xs.ignorableWhitespace("\n");
				for (final SyndEntry se : sf.getEntries()) {
					xs.startTag(com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "entry");
					try {
						textElementOptional(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "id", se.getLink());
						if(se.getTitleEx() != null) {
							contentElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "title", se.getTitleEx());
						}
						dateElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "published", se.getPublishedDate(), ctx.getDateFormatW3C());
						dateElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "updated", se.getUpdatedDate(), ctx.getDateFormatW3C());
						final SyndContent erights = se.getRights();
						if(erights != null) {
							contentElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "rights", erights);
						}
						for(final SyndPerson sp: se.getAuthors()) {
							personElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "author", sp);
						}
						for(final SyndPerson sp: se.getContributors()) {
							personElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "contributor", sp);
						}
						xs.ignorableWhitespace("\n");
						for (final SyndLink sl : se.getLinks()) {
							linkElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, sl);
						}
						xs.ignorableWhitespace("\n");
						for(final SyndCategory sc: se.getCategories()) {
							categoryElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, sc);
						}
						for(final SyndEnclosure senc: se.getEnclosures()) {
							enclosureElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, senc);
						}
						applyModules(xs, se.getModules(), ins);
						xs.ignorableWhitespace("\n");
						for (final SyndContent sc : se.getContents()) {
							contentElement(xs, com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "content", sc);
							xs.ignorableWhitespace("\n");
						}
					} finally {
						xs.endTag(com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "entry");
						xs.ignorableWhitespace("\n");
					}
				}
			} finally {
				xs.endTag(com.escape_technology_llc.synder.handlers.Atom10.ATOM_10_URI, "feed");
			}
		} finally {
			xs.endDocument();
		}
	}
}
