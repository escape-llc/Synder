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
import com.escape_technology_llc.synder.SynderContentHandler;
import com.escape_technology_llc.synder.setters.Setter;

/**
 * Handler for "link" with Simple Content as the HREF.
 * Marshals into SyndLinkImpl.
 * Captures "{usuri}element"-&gt;SyndLinkImpl.Type.
 * Captures characters-&gt;Href.
 * @author escape-llc
 *
 * @param <R> host type.
 */
public class SimpleLinkElement<R> extends LinkElement<R> {
	final StringBuilder sb;
	public SimpleLinkElement(ParseContext ctx, R root, Setter sx) {
		super(ctx, root, sx);
		sb = new StringBuilder();
	}
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		if(link.getType() == null) {
			final String nx = SynderContentHandler.formatTag(RssAny.RSS10_NSURI == uri ? "" : uri, localName);
			link.setType(nx);
		}
	}
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}
	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		link.setHref(sb.toString());
		super.endElement(arg0, arg1, arg2);
	}
}
