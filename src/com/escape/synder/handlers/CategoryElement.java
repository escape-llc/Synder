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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.escape.synder.ParseContext;
import com.escape.synder.setters.Setter;
import com.sun.syndication.feed.synd.impl.SyndCategoryImpl;

/**
 * Handler for Category element for both RSS and Atom.
 * Creates and populates a SyndCategory.
 * Captures attributes: term->Name, scheme->TaxonomyUri, domain->TaxonomyUri, label->Name, name->Name.
 * Captures characters (if any) to Name if it was not set by an attribute.
 * @author escape-llc
 *
 * @param <R> Class to Attach.
 */
public class CategoryElement<R> extends ElementHandler<R> {
	final SyndCategoryImpl item;
	StringBuilder sb;
	public CategoryElement(ParseContext ctx, R root, Setter sx) {
		super(ctx, root, sx);
		item = new SyndCategoryImpl();
	}
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		final String term = attributes.getValue("term");
		if(term != null && term.length() > 0) {
			item.setName(term);
		}
		final String scheme = attributes.getValue("scheme");
		if (scheme != null && scheme.length() > 0) {
			// Atom
			item.setTaxonomyUri(scheme);
		} else {
			// RSS
			final String domain = attributes.getValue("domain");
			if (domain != null && domain.length() > 0) {
				item.setTaxonomyUri(domain);
			}
		}
		final String label = attributes.getValue("label");
		if(label != null && label.length() > 0) {
			item.setName(label);
		}
		else {
			final String namea = attributes.getValue("name");
			if(namea != null && namea.length() > 0) {
				item.setName(namea);
			}
		}
	}
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(sb == null) sb = new StringBuilder();
		sb.append(ch, start, length);
	}
	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		if (sb != null && item.getName() == null) {
			final String nx = sb.toString().trim();
			if (nx.length() > 0) {
				item.setName(sb.toString());
			}
		}
		sx.set(root, item, ctx);
	}
}
