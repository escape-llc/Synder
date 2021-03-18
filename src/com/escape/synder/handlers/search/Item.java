/*
 * Copyright 2014 eScape Technology LLC.
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
package com.escape.synder.handlers.search;

import java.util.HashMap;
import java.util.List;

import org.xml.sax.Attributes;

import com.escape.synder.ParseContext;
import com.escape.synder.SyndEntryImpl2;
import com.escape.synder.SynderContentHandler;
import com.escape.synder.handlers.HandlerFactories;
import com.escape.synder.handlers.HandlerFactory;
import com.escape.synder.handlers.HandlerImpl;
import com.escape.synder.handlers.SearchSuggestion20;
import com.escape.synder.handlers.SubHandler;
import com.escape.synder.setters.Setter;
import com.escape.synder.setters.SyndContentSetter;
import com.escape.synder.setters.SyndImageSetter;
import com.sun.syndication.feed.synd.impl.SyndEntryImpl;

/**
 * Handler for Open Search SearchSuggestion Item element.
 * Captures Text as SyndEntry.TitleEx as SyndContent.
 * Captures Description as SyndEntry.Description as SyndContent.
 * Captures Url as SyndEntry.Link.
 * Captures Image as add SyndLink to SyndEntry.Links.
 * @author escape-llc
 *
 */
public class Item extends SubHandler<SyndEntryImpl2> {
	static final HashMap<String,HandlerFactory<SyndEntryImpl>> props;
	static {
		props = new HashMap<String,HandlerFactory<SyndEntryImpl>>();
		try {
			props.put(SearchSuggestion20.TEXT, new HandlerFactories.Entry_ContentElement(
					new SyndContentSetter(SyndEntryImpl2.class, "TitleEx")));
			props.put(SearchSuggestion20.DESCRIPTION, new HandlerFactories.Entry_ContentElement(
					new SyndContentSetter(SyndEntryImpl2.class, "Description")));
			props.put(SearchSuggestion20.URL,
					new HandlerFactories.Entry_SimpleElement(new Setter(SyndEntryImpl2.class, "Link")));
			props.put(SearchSuggestion20.IMAGE,
					new HandlerFactories.Entry_SearchImageElement(new SyndImageSetter(SyndEntryImpl2.class, "Image")));
			props.put(SearchSuggestion20.MS_TEXT, new HandlerFactories.Entry_ContentElement(
					new SyndContentSetter(SyndEntryImpl2.class, "TitleEx")));
			props.put(SearchSuggestion20.MS_DESCRIPTION, new HandlerFactories.Entry_ContentElement(
					new SyndContentSetter(SyndEntryImpl2.class, "Description")));
			props.put(SearchSuggestion20.MS_URL,
					new HandlerFactories.Entry_SimpleElement(new Setter(SyndEntryImpl2.class, "Link")));
			props.put(SearchSuggestion20.MS_IMAGE,
					new HandlerFactories.Entry_SearchImageElement(new SyndImageSetter(SyndEntryImpl2.class, "Image")));
		}
		catch(Exception e) {
		}
	}
	public Item(ParseContext ctx, SyndEntryImpl2 root, List<HandlerImpl> nsx) {
		super(ctx, root, nsx);
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
			return qx;
		}
	}
}
