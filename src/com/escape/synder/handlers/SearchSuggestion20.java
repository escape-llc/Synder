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
package com.escape.synder.handlers;

import java.util.Date;

import org.xml.sax.Attributes;

import com.escape.synder.ParseContext;
import com.escape.synder.SyndEntryImpl2;
import com.escape.synder.handlers.search.Item;
import com.escape.synder.handlers.search.SearchSuggestion;
import com.sun.syndication.feed.synd.impl.SyndFeedImpl;

/**
 * Top-level handler for Open Search SearchSuggestion 2.0 XML document.
 * Tag is "ss_2.0".
 * Both the Open Search and Microsoft namespaces are supported.
 * Open Search NS requires version attribute "2.0".
 * Microsoft NS does not check for a version attribute.
 * @author escape-llc
 *
 */
public class SearchSuggestion20 extends HandlerImpl {
	// support both namespaces
	public static final String SS_20_URI = "http://opensearch.org/searchsuggest2";
	public static final String SS_20_URIMS = "http://schemas.microsoft.com/Search/2008/suggestions";
	
	public static final String DESCRIPTION = "{http://opensearch.org/searchsuggest2}Description";
	public static final String IMAGE = "{http://opensearch.org/searchsuggest2}Image";
	public static final String ITEM = "{http://opensearch.org/searchsuggest2}Item";
	public static final String QUERY = "{http://opensearch.org/searchsuggest2}Query";
	public static final String SECTION = "{http://opensearch.org/searchsuggest2}Section";
	public static final String TEXT = "{http://opensearch.org/searchsuggest2}Text";
	public static final String URL = "{http://opensearch.org/searchsuggest2}Url";
	// GAK! Thanks Microsoft!
	public static final String MS_DESCRIPTION = "{http://schemas.microsoft.com/Search/2008/suggestions}Description";
	public static final String MS_IMAGE = "{http://schemas.microsoft.com/Search/2008/suggestions}Image";
	public static final String MS_ITEM = "{http://schemas.microsoft.com/Search/2008/suggestions}Item";
	public static final String MS_QUERY = "{http://schemas.microsoft.com/Search/2008/suggestions}Query";
	public static final String MS_SECTION = "{http://schemas.microsoft.com/Search/2008/suggestions}Section";
	public static final String MS_TEXT = "{http://schemas.microsoft.com/Search/2008/suggestions}Text";
	public static final String MS_URL = "{http://schemas.microsoft.com/Search/2008/suggestions}Url";
	final String version = "2.0";
	final Date pub = new Date();
	public SearchSuggestion20(ParseContext ctx) {
		super("ss_2.0", ctx);
	}

	@Override
	public boolean detect(String uri, String localName, String name, Attributes attributes) {
		if(SS_20_URI.equalsIgnoreCase(uri)) {
			if("SearchSuggestion".equalsIgnoreCase(localName)) {
				final String rv = attributes.getValue("version");
				if (rv != null && rv.startsWith(version))
					return true;
			}
		}
		else if(SS_20_URIMS.equalsIgnoreCase(uri)) {
			// MS has no version requirement
			if("SearchSuggestion".equalsIgnoreCase(localName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public SubHandler<?> query(ParseContext pc, String[] path, SubHandler<?> tos,
			String uri, String localName, String name, Attributes attributes) {
		final String nx = localName.length() == 0 ? name : localName;
		if (SS_20_URI.equals(uri) || SS_20_URIMS.equalsIgnoreCase(uri)) {
			if ("SearchSuggestion".equalsIgnoreCase(nx)) {
				final SyndFeedImpl sfi = new SyndFeedImpl();
				sfi.setFeedType(getTag());
				sfi.setPublishedDate(pub);
				final SearchSuggestion fx = new SearchSuggestion(pc, sfi, fns);
				return fx;
			}
			else if("Section".equals(nx)) {
				return current.peek();
			}
			else if("Item".equals(nx)) {
				final SyndEntryImpl2 sie = new SyndEntryImpl2();
				sie.setPublishedDate(pub);
				final SyndFeedImpl sfi = (SyndFeedImpl) current.peek().getRoot();
				sie.setSource(sfi);
				sfi.getEntries().add(sie);
				final Item fx = new Item(pc, sie, ins);
				return fx;
			}
		}
		return null;
	}

}
