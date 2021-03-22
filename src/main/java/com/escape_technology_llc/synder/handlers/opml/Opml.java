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
package com.escape_technology_llc.synder.handlers.opml;

import java.util.HashMap;
import java.util.List;

import org.xml.sax.Attributes;

import com.escape_technology_llc.synder.ParseContext;
import com.escape_technology_llc.synder.SynderContentHandler;
import com.escape_technology_llc.synder.handlers.HandlerFactories;
import com.escape_technology_llc.synder.handlers.HandlerFactory;
import com.escape_technology_llc.synder.handlers.HandlerImpl;
import com.escape_technology_llc.synder.handlers.Opml20;
import com.escape_technology_llc.synder.handlers.SubHandler;
import com.escape_technology_llc.synder.setters.RFC822DateSetter;
import com.escape_technology_llc.synder.setters.SyndContentSetter;
import com.sun.syndication.feed.synd.impl.SyndFeedImpl;

/**
 * Handler for OPML 2.0 opml/head Element.
 * Captures title-&gt;TitleEx, dateCreated-&gt;PublishedDate, dateModified-&gt;UpdatedDate.
 * @author escape-llc
 *
 */
public class Opml extends SubHandler<SyndFeedImpl> {
	static final HashMap<String,HandlerFactory<SyndFeedImpl>> props;
	static {
		props = new HashMap<String,HandlerFactory<SyndFeedImpl>>();
		try {
			props.put(Opml20.TITLE, new HandlerFactories.Feed_ContentElement(
					new SyndContentSetter(SyndFeedImpl.class, "TitleEx")));
			props.put(Opml20.DATE_CREATED, new HandlerFactories.Feed_SimpleElement(
					new RFC822DateSetter(SyndFeedImpl.class, "PublishedDate")));
			props.put(Opml20.DATE_MODIFIED, new HandlerFactories.Feed_SimpleElement(
					new RFC822DateSetter(SyndFeedImpl.class, "UpdatedDate")));
		}
		catch(Exception e) {
		}
	}
	public Opml(ParseContext ctx, SyndFeedImpl root, List<HandlerImpl> nsx) {
		super(ctx, root, nsx);
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path, SubHandler<?> tos,
			String uri, String localName, String name, Attributes attributes) {
		final String nx = SynderContentHandler.formatTag(uri, localName);
		if(props.containsKey(nx)) {
			return props.get(nx).create(pc, attributes, root);
		}
		return super.query(pc, path, tos, uri, localName, name, attributes);
	}
}
