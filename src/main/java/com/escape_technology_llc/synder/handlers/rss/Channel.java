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
import com.escape_technology_llc.synder.setters.SyndForeignMarkupListSetter;
import com.escape_technology_llc.synder.setters.SyndLinkListSetter;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.feed.synd.impl.SyndFeedImpl;

/**
 * Handler for RSS Channel element.
 * Captures Title, Description, pubDate->feed.PublishedDate, lastBuildDate->feed.UpdatedDate,link as add to feed.Links, Image.
 * @author escape-llc
 *
 */
public class Channel extends SubHandler<SyndFeedImpl> {
	static final HashMap<String,HandlerFactory<SyndFeedImpl>> props;
	static {
		props = new HashMap<String,HandlerFactory<SyndFeedImpl>>();
		try {
			props.put("title", new HandlerFactories.Feed_SimpleElement(
					new Setter(SyndFeedImpl.class, "Title")));
			props.put("description", new HandlerFactories.Feed_SimpleElement(
					new Setter(SyndFeedImpl.class, "Description")));
			props.put("pubDate", new HandlerFactories.Feed_SimpleElement(
					new RFC822DateSetter(SyndFeedImpl.class, "PublishedDate")));
			props.put("lastBuildDate", new HandlerFactories.Feed_SimpleElement(
					new RFC822DateSetter(SyndFeedImpl.class, "UpdatedDate")));
			props.put("link", new HandlerFactories.Feed_SimpleLinkElement(
					new SyndLinkListSetter(SyndFeedImpl.class, "Links")));
			props.put("image", new Feed_ImageElement(
					new Setter(SyndFeedImpl.class, "Image", SyndImage.class)));
		} catch (Exception e) {
		}
	}
	static final class Feed_ImageElement extends HandlerFactory<SyndFeedImpl> {
		public Feed_ImageElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax,
				SyndFeedImpl root) {
			return new Image<SyndFeedImpl>(pc, root, sx);
		}
	}
	final boolean fm;
	public Channel(ParseContext ctx, SyndFeedImpl root, List<HandlerImpl> nsx) {
		super(ctx, root, nsx);
		fm = ctx.getOption(ParseContext.Features.FOREIGN_MARKUP_FEED) == null;
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
				final ForeignMarkupElement<SyndFeedImpl> fme = new ForeignMarkupElement<SyndFeedImpl>(
						pc, root, new SyndForeignMarkupListSetter(SyndFeedImpl.class,
								"ForeignMarkup"));
				return fme;
			} catch (Exception e) {
				return qx;
			}
		}
	}
}
