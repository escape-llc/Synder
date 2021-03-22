/*
 * Copyright 2012 eScape Technology LLC.
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
package com.escape_technology_llc.synder.handlers.atom;

import java.util.List;

import com.escape_technology_llc.synder.Finalization;
import com.escape_technology_llc.synder.ParseContext;
import com.escape_technology_llc.synder.handlers.HandlerImpl;
import com.sun.syndication.feed.synd.impl.SyndEntryImpl;
import com.sun.syndication.feed.synd.impl.SyndFeedImpl;

/**
 * Handler for Atom Feed Entry document.
 * This is a document where the root element is Entry.
 * Copies fields from the single Entry to the Feed: TitleEx, PublishedDate, UpdatedDate, Links, Authors.
 * @author escape-llc
 *
 */
public class FeedEntryDocument extends Feed implements Finalization {
	final SyndEntryImpl entry;
	public FeedEntryDocument(ParseContext ctx, SyndFeedImpl root,  List<HandlerImpl> nsx, SyndEntryImpl entry) {
		super(ctx, root, nsx);
		this.entry = entry;
	}
	public void end() {
		// copy matching properties from the entry
		root.setTitleEx(entry.getTitleEx());
		root.setPublishedDate(entry.getPublishedDate());
		root.setUpdatedDate(entry.getUpdatedDate());
		root.setLinks(entry.getLinks());
		root.setAuthors(entry.getAuthors());
	}
}
