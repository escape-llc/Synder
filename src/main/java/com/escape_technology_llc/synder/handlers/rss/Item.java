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
package com.escape_technology_llc.synder.handlers.rss;

import java.util.HashMap;
import java.util.List;

import org.xml.sax.Attributes;

import com.escape_technology_llc.synder.ParseContext;
import com.escape_technology_llc.synder.SynderContentHandler;
import com.escape_technology_llc.synder.handlers.ForeignMarkupElement;
import com.escape_technology_llc.synder.handlers.HandlerFactories;
import com.escape_technology_llc.synder.handlers.HandlerFactory;
import com.escape_technology_llc.synder.handlers.RssAny;
import com.escape_technology_llc.synder.handlers.SubHandler;
import com.escape_technology_llc.synder.handlers.HandlerImpl;
import com.escape_technology_llc.synder.setters.RFC822DateSetter;
import com.escape_technology_llc.synder.setters.Setter;
import com.escape_technology_llc.synder.setters.SyndCategoryListSetter;
import com.escape_technology_llc.synder.setters.SyndContentListSetter;
import com.escape_technology_llc.synder.setters.SyndEnclosureListSetter;
import com.escape_technology_llc.synder.setters.SyndForeignMarkupListSetter;
import com.escape_technology_llc.synder.setters.SyndLinkListSetter;
import com.escape_technology_llc.synder.setters.SyndPersonListSetter;
import com.sun.syndication.feed.synd.impl.SyndEntryImpl;

/**
 * Handler for RSS Item element.
 * Captures Title, author->add to Authors, description->add to Contents,pubDate->PublishedDate,link->add to Links,comment->add to Links,
 * source->add to Links,category->add to Categories,enclosure->add to Enclosures.
 * @author escape-llc
 *
 */
public class Item extends SubHandler<SyndEntryImpl> {
	static final HashMap<String,HandlerFactory<SyndEntryImpl>> props;
	static {
		props = new HashMap<String,HandlerFactory<SyndEntryImpl>>();
		try {
			props.put("title", new HandlerFactories.Entry_SimpleElement(
				new Setter(SyndEntryImpl.class, "Title")));
			props.put("author", new HandlerFactories.Entry_SimpleElement(
					new SyndPersonListSetter(SyndEntryImpl.class, "Authors")));
			props.put("description", new HandlerFactories.Entry_SimpleElement(
				new SyndContentListSetter(SyndEntryImpl.class, "Contents")));
			props.put("pubDate", new HandlerFactories.Entry_SimpleElement(
				new RFC822DateSetter(SyndEntryImpl.class, "PublishedDate")));
			props.put("link", new HandlerFactories.Entry_SimpleLinkElement(
				new SyndLinkListSetter(SyndEntryImpl.class, "Links")));
			props.put("guid", new HandlerFactories.Entry_GuidElement(
				new SyndLinkListSetter(SyndEntryImpl.class, "Links")));
			props.put("comments", new HandlerFactories.Entry_SimpleLinkElement(
					new SyndLinkListSetter(SyndEntryImpl.class, "Links")));
			props.put("source", new HandlerFactories.Entry_RssLinkElement(
					new SyndLinkListSetter(SyndEntryImpl.class, "Links")));
			props.put("category", new HandlerFactories.Entry_CategoryElement(
				new SyndCategoryListSetter(SyndEntryImpl.class, "Categories")));
			props.put("enclosure", new HandlerFactories.Entry_EnclosureElement(
				new SyndEnclosureListSetter(SyndEntryImpl.class, "Enclosures")));
		} catch (Exception e) {
		}
	}
	final boolean fm;
	public Item(ParseContext ctx, SyndEntryImpl item, List<HandlerImpl> nsx) {
		super(ctx, item, nsx);
		fm = ctx.getOption(ParseContext.Features.FOREIGN_MARKUP_ENTRY) == null;
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path,
			SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		final String nx = SynderContentHandler.formatTag(RssAny.RSS10_NSURI.equals(uri) ? "" : uri, localName);
		if(props.containsKey(nx)) {
			return props.get(nx).create(pc, attributes, root);
		}
		else {
			final SubHandler<?> qx = super.query(pc, path, this, uri, localName, name, attributes);
			if(fm) return qx;
			if(qx != null) return qx;
			if(tos instanceof ForeignMarkupElement<?>)
				return tos;
			try {
				final ForeignMarkupElement<SyndEntryImpl> fme = new ForeignMarkupElement<SyndEntryImpl>(
						pc, root, new SyndForeignMarkupListSetter(SyndEntryImpl.class,
								"ForeignMarkup"));
				return fme;
			} catch (Exception e) {
				return qx;
			}
		}
	}
}
