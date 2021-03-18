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
package com.escape.synder.handlers;

import java.util.List;

import org.xml.sax.Attributes;

import com.escape.synder.ParseContext;
import com.escape.synder.handlers.atom.Entry;
import com.escape.synder.handlers.atom.Feed;
import com.escape.synder.handlers.atom.FeedEntryDocument;
import com.escape.synder.handlers.atom.Person;
import com.sun.syndication.feed.synd.SyndPerson;
import com.sun.syndication.feed.synd.impl.SyndEntryImpl;
import com.sun.syndication.feed.synd.impl.SyndFeedImpl;
import com.sun.syndication.feed.synd.impl.SyndPersonImpl;

/**
 * Top-level Synder handler for Atom 1.0.
 * Captures Feed, Entry, Author, Contributor, Link, Id, Title, Subtitle, Icon, Published, Updated, Category, Content, Name, Uri, Email, Summary.
 * If the document is an Atom Entry document, the detected feed.FeedType will have ";type=entry" appended to it.
 * Tag is "atom_1.0".
 * @author escape-llc
 *
 */
public class Atom10 extends HandlerImpl {
	public static final String ATOM_10_URI = "http://www.w3.org/2005/Atom";
	// these are 100% literals for efficiency
	// items without a NS prefix are handled by Atom10 directly, because they can appear in either FEED or ENTRY elements.
	public static final String AUTHOR = "author";
	public static final String CATEGORY = "{http://www.w3.org/2005/Atom}category";
	public static final String CONTENT = "{http://www.w3.org/2005/Atom}content";
	public static final String CONTRIBUTOR = "contributor";
	public static final String EMAIL = "{http://www.w3.org/2005/Atom}email";
	public static final String ENTRY = "entry";
	public static final String FEED = "feed";
	public static final String GENERATOR = "{http://www.w3.org/2005/Atom}generator";
	public static final String ICON = "{http://www.w3.org/2005/Atom}icon";
	public static final String ID = "{http://www.w3.org/2005/Atom}id";
	public static final String LINK = "{http://www.w3.org/2005/Atom}link";
	public static final String LOGO = "{http://www.w3.org/2005/Atom}logo";
	public static final String NAME = "{http://www.w3.org/2005/Atom}name";
	public static final String PUBLISHED = "{http://www.w3.org/2005/Atom}published";
	public static final String RIGHTS = "{http://www.w3.org/2005/Atom}rights";
	public static final String SUBTITLE = "{http://www.w3.org/2005/Atom}subtitle";
	public static final String SUMMARY = "{http://www.w3.org/2005/Atom}summary";
	public static final String TITLE = "{http://www.w3.org/2005/Atom}title";
	public static final String UPDATED = "{http://www.w3.org/2005/Atom}updated";
	public static final String URI = "{http://www.w3.org/2005/Atom}uri";
	public Atom10(ParseContext ctx) {
		super("atom_1.0", ctx);
	}
	@Override
	public boolean detect(String uri, String localName, String name,
			Attributes attributes) {
		if(ATOM_10_URI.equalsIgnoreCase(uri)) return true;
		return false;
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path,
			SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		final String nx = localName.length() == 0 ? name : localName;
		if (ATOM_10_URI.equals(uri)) {
			if (FEED.equalsIgnoreCase(nx)) {
				final SyndFeedImpl sfi = new SyndFeedImpl();
				sfi.setFeedType(getTag());
				final Feed fx = new Feed(pc, sfi, fns);
				return fx;
			} else if (ENTRY.equalsIgnoreCase(nx)) {
				final SyndEntryImpl sie = new SyndEntryImpl();
				if(current.isEmpty()) {
					// atom entry document
					final SyndFeedImpl sfi = new SyndFeedImpl();
					sfi.setFeedType(getTag() + ";type=entry");
					final Feed fx = new FeedEntryDocument(pc, sfi, ins, sie);
					current.push(fx);
				}
				final SyndFeedImpl sfi = (SyndFeedImpl) current.peek().getRoot();
				sie.setSource(sfi);
				sfi.getEntries().add(sie);
				final Entry fx = new Entry(pc, sie, ins);
				return fx;
			}
			else if (AUTHOR.equalsIgnoreCase(nx)) {
				final Object ox = current.peek().getRoot();
				List<SyndPerson> lx = null;
				if(ox instanceof SyndFeedImpl) {
					lx = ((SyndFeedImpl)ox).getAuthors();
				}
				else if(ox instanceof SyndEntryImpl) {
					lx = ((SyndEntryImpl)ox).getAuthors();
				}
				final SyndPersonImpl sfp = new SyndPersonImpl();
				if(lx != null) lx.add(sfp);
				final Person<SyndPersonImpl> px = new Person<SyndPersonImpl>(pc, sfp);
				return px;
			}
			else if (CONTRIBUTOR.equalsIgnoreCase(nx)) {
				final Object ox = current.peek().getRoot();
				List<SyndPerson> lx = null;
				if(ox instanceof SyndFeedImpl) {
					lx = ((SyndFeedImpl)ox).getContributors();
				}
				else if(ox instanceof SyndEntryImpl) {
					lx = ((SyndEntryImpl)ox).getContributors();
				}
				final SyndPersonImpl sfp = new SyndPersonImpl();
				if(lx != null) lx.add(sfp);
				final Person<SyndPersonImpl> px = new Person<SyndPersonImpl>(pc, sfp);
				return px;
			}
		}
		return null;
	}
}
