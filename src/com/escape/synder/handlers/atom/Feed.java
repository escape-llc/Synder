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
package com.escape.synder.handlers.atom;

import java.util.HashMap;
import java.util.List;

import org.xml.sax.Attributes;

import com.escape.synder.ParseContext;
import com.escape.synder.SynderContentHandler;
import com.escape.synder.handlers.Atom10;
import com.escape.synder.handlers.ForeignMarkupElement;
import com.escape.synder.handlers.HandlerFactories;
import com.escape.synder.handlers.HandlerFactory;
import com.escape.synder.handlers.HandlerImpl;
import com.escape.synder.handlers.SubHandler;
import com.escape.synder.setters.Setter;
import com.escape.synder.setters.SyndCategoryListSetter;
import com.escape.synder.setters.SyndContentSetter;
import com.escape.synder.setters.SyndForeignMarkupListSetter;
import com.escape.synder.setters.SyndLinkListSetter;
import com.escape.synder.setters.W3CDateSetter;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.impl.SyndFeedImpl;

/**
 * Handler for Atom Feed element.
 * Captures title->TitleEx, subtitle->DescriptionEx, updated->PublishedDate, generator->Generator, rights->Rights, logo->Logo,
 * link->add to Links, icon->Image, category->add to Categories.
 * Supports Foreign Markup capture to feed.ForeignMarkup.
 * Authors and Contributors are captured by the Atom10 implementation.
 * @author escape-llc
 *
 */
public class Feed extends SubHandler<SyndFeedImpl> {
	static final HashMap<String,HandlerFactory<SyndFeedImpl>> props;
	static {
		props = new HashMap<String,HandlerFactory<SyndFeedImpl>>();
		try {
			props.put(Atom10.TITLE, new HandlerFactories.Feed_ContentElement(new SyndContentSetter(SyndFeedImpl.class, "TitleEx")));
			props.put(Atom10.SUBTITLE, new HandlerFactories.Feed_ContentElement(new SyndContentSetter(SyndFeedImpl.class, "DescriptionEx")));
			props.put(Atom10.UPDATED, new HandlerFactories.Feed_SimpleElement(new W3CDateSetter(SyndFeedImpl.class, "PublishedDate")));
			props.put(Atom10.LINK, new HandlerFactories.Feed_LinkElement(new SyndLinkListSetter(SyndFeedImpl.class, "Links")));
			props.put(Atom10.GENERATOR, new HandlerFactories.Feed_GeneratorElement(new Setter(SyndFeedImpl.class, "Generator", SyndLink.class)));
			props.put(Atom10.ICON, new HandlerFactories.Feed_SimpleImageElement(new Setter(SyndFeedImpl.class, "Image", SyndImage.class)));
			props.put(Atom10.LOGO, new HandlerFactories.Feed_SimpleImageElement(new Setter(SyndFeedImpl.class, "Logo", SyndImage.class)));
			props.put(Atom10.CATEGORY, new HandlerFactories.Feed_CategoryElement(new SyndCategoryListSetter(SyndFeedImpl.class, "Categories")));
			props.put(Atom10.RIGHTS, new HandlerFactories.Feed_ContentElement(new SyndContentSetter(SyndFeedImpl.class, "Rights")));
		} catch (Exception e) {
			//System.err.println(e);
		}
	}
	final boolean fm;
	public Feed(ParseContext ctx, SyndFeedImpl root, List<HandlerImpl> nsx) {
		super(ctx, root, nsx);
		fm = ctx.getOption(ParseContext.Features.FOREIGN_MARKUP_FEED) == null;
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path,
			SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		final String nx = SynderContentHandler.formatTag(uri, localName);
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
				final ForeignMarkupElement<SyndFeedImpl> fme = new ForeignMarkupElement<SyndFeedImpl>(
						pc, root, new SyndForeignMarkupListSetter(SyndFeedImpl.class, "ForeignMarkup"));
				return fme;
			} catch (Exception e) {
				return qx;
			}
		}
	}
}
