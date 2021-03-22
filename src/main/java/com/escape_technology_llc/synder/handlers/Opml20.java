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
package com.escape_technology_llc.synder.handlers;

import org.xml.sax.Attributes;

import com.escape_technology_llc.synder.ParseContext;
import com.escape_technology_llc.synder.handlers.opml.Opml;
import com.escape_technology_llc.synder.handlers.opml.Outline;
import com.sun.syndication.feed.synd.impl.SyndEntryImpl;
import com.sun.syndication.feed.synd.impl.SyndFeedImpl;

/**
 * Top-level handler for OPML 2.0 document.
 * Captures head-&gt;feed.*, outline-&gt;add to feed.Entries.  No nesting is captured, outline nodes are flattened.
 * @author escape-llc
 *
 */
public class Opml20 extends HandlerImpl {
	public static final String OPML = "opml";
	public static final String HEAD = "head";
	public static final String TITLE = "title";
	public static final String DATE_CREATED = "dateCreated";
	public static final String DATE_MODIFIED = "dateModified";
	public static final String OWNER_NAME = "ownerName";
	public static final String OWNER_EMAIL = "ownerEmail";
	public static final String BODY = "body";
	public static final String OUTLINE = "outline";
	final String version = "2.0";
	public Opml20(ParseContext ctx) {
		super("opml_2.0", ctx);
	}

	@Override
	public boolean detect(String uri, String localName, String name, Attributes attributes) {
		final String nx = localName.length() == 0 ? name : localName;
		if(OPML.equalsIgnoreCase(nx)) {
			final String rv = attributes.getValue("version");
			if (rv != null && version.equals(rv))
				return true;
		}
		return false;
	}

	@Override
	public SubHandler<?> query(ParseContext pc, String[] path, SubHandler<?> tos,
			String uri, String localName, String name, Attributes attributes) {
		final String nx = localName.length() == 0 ? name : localName;
		if(OPML.equalsIgnoreCase(nx)) {
			final SyndFeedImpl sfi = new SyndFeedImpl();
			sfi.setFeedType(getTag());
			//sfi.setPublishedDate(pub);
			final Opml fx = new Opml(pc, sfi, fns);
			return fx;
		}
		else if(HEAD.equalsIgnoreCase(nx)) {
			return current.peek();
		}
		else if(BODY.equalsIgnoreCase(nx)) {
			return current.peek();
		}
		else if(OUTLINE.equalsIgnoreCase(nx)) {
			final SyndEntryImpl sie = new SyndEntryImpl();
			final SyndFeedImpl sfi = (SyndFeedImpl) current.peek().getRoot();
			sie.setPublishedDate(sfi.getPublishedDate());
			sie.setUpdatedDate(sfi.getUpdatedDate());
			sie.setSource(sfi);
			sfi.getEntries().add(sie);
			final Outline fx = new Outline(pc, sie, ins);
			return fx;
		}
		return null;
	}

}
