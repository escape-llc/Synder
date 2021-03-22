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

import java.util.Date;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.escape_technology_llc.synder.ParseContext;
import com.escape_technology_llc.synder.handlers.HandlerImpl;
import com.escape_technology_llc.synder.handlers.SubHandler;
import com.sun.syndication.feed.synd.impl.SyndCategoryImpl;
import com.sun.syndication.feed.synd.impl.SyndContentImpl;
import com.sun.syndication.feed.synd.impl.SyndEntryImpl;
import com.sun.syndication.feed.synd.impl.SyndLinkImpl;

/**
 * Handler for OPML 2.0 outline Element.
 * Captures attributes text-&gt;Description, xmlUrl+type+title-&gt;add to Links, created-&gt;PublishedDate+CreatedDate, category-&gt;add to Categories,
 * description-&gt;Title, title-&gt;Title
 * Prefer title to description.
 * @author escape-llc
 *
 */
public class Outline extends SubHandler<SyndEntryImpl> {
	public Outline(ParseContext ctx, SyndEntryImpl root, List<HandlerImpl> nsx) {
		super(ctx, root, nsx);
	}
	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		final String text = attributes.getValue("text");
		if(text != null && text.length() > 0) {
			final SyndContentImpl sc = new SyndContentImpl();
			sc.setMode(localName);
			sc.setType("text/plain");
			sc.setValue(text);
			root.setDescription(sc);
		}
		final String created = attributes.getValue("created");
		if(created != null && created.length() > 0) {
			final Date dx = ctx.getDateParser().parseRFC822(created);
			root.setPublishedDate(dx);
			root.setUpdatedDate(dx);
		}
		final String category = attributes.getValue("category");
		if(category != null && category.length() > 0) {
			final String[] cats = category.split("\\,");
			for(int ix = 0; ix < cats.length; ix++) {
				final SyndCategoryImpl sc = new SyndCategoryImpl();
				sc.setLabel(cats[ix]);
				sc.setName(cats[ix]);
				root.getCategories().add(sc);
			}
		}
		String title = attributes.getValue("title");
		if (title == null || title.length() == 0) {
			final String desc = attributes.getValue("description");
			if (desc != null && desc.length() > 0) {
				title = desc;
			}
		}
		root.setTitle(title);
		String type = attributes.getValue("type");
		if(type == null || type.length() == 0) {
			type = "text/*";
		}
		final String xmlUrl = attributes.getValue("xmlUrl");
		if(xmlUrl != null && xmlUrl.length() > 0) {
			final SyndLinkImpl sli = new SyndLinkImpl();
			sli.setRel(localName);
			sli.setHref(xmlUrl);
			sli.setType(type);
			sli.setTitle(title);
			root.getLinks().add(sli);
		}
	}
}
