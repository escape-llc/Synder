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
import com.sun.syndication.feed.synd.impl.SyndLinkImpl;

/**
 * Element handler for "{Atom}link".
 * Marshals into SyndLinkImpl.
 * Captures attributes rel-&gt;Rel, type-&gt;Type, href-&gt;Href, hreflang-&gt;Hreflang, title-&gt;Title, length-&gt;Length
 * @author escape-llc
 *
 * @param <R> Hosting class.
 */
public class LinkElement<R> extends ElementHandler<R> {
	protected final SyndLinkImpl link;
	public LinkElement(ParseContext ctx, R root, Setter sx) {
		super(ctx, root, sx);
		link = new SyndLinkImpl();
	}
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		final String rel = attributes.getValue("rel");
		if(rel != null && rel.length() > 0) {
			link.setRel(rel);
		}
		final String type = attributes.getValue("type");
		if(type != null && type.length() > 0) {
			link.setType(type);
		}
		final String hr = attributes.getValue("href");
		if(hr != null && hr.length() > 0) {
			link.setHref(hr);
		}
		final String hrl = attributes.getValue("hreflang");
		if(hrl != null && hrl.length() > 0) {
			link.setHreflang(hrl);
		}
		final String title = attributes.getValue("title");
		if(title != null && title.length() > 0) {
			link.setTitle(title);
		}
		final String lg = attributes.getValue("length");
		if(lg != null && lg.length() > 0) {
			try {
				link.setLength(Long.parseLong(lg));
			} catch (NumberFormatException nfe) {
			}
		}
	}
	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		sx.set(root, link, ctx);
	}
}
