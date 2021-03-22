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
package com.escape_technology_llc.synder.handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.escape_technology_llc.synder.ParseContext;
import com.escape_technology_llc.synder.setters.Setter;
import com.sun.syndication.feed.synd.impl.SyndEnclosureImpl;
import com.sun.syndication.feed.synd.impl.SyndEntryImpl;

/**
 * Element handler for "enclosure".
 * Marshals into SyndEnclosureImpl.
 * Captures attributes url-&gt;Url else href-&gt;Url, type-&gt;Type, length-&gt;Length.
 * @author escape-llc
 *
 */
public class EnclosureElement extends ElementHandler<SyndEntryImpl> {
	protected final SyndEnclosureImpl item;
	public EnclosureElement(ParseContext ctx, SyndEntryImpl root, Setter sx) {
		super(ctx, root, sx);
		item = new SyndEnclosureImpl();
	}
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		final String rel = attributes.getValue("url");
		if(rel != null && rel.length() > 0) {
			item.setUrl(rel);
		}
		else {
			final String href = attributes.getValue("href");
			if(href != null && href.length() > 0) {
				item.setUrl(href);
			}
		}
		final String type = attributes.getValue("type");
		if(type != null && type.length() > 0) {
			item.setType(type);
		}
		final String hr = attributes.getValue("length");
		if(hr != null && hr.length() > 0) {
			try {
				item.setLength(Long.parseLong(hr));
			} catch (NumberFormatException nfe) {
			}
		}
	}
	/**
	 * Apply the value via the Setter.
	 */
	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		sx.set(root, item, ctx);
	}
}
