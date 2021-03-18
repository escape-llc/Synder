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
import com.escape.synder.SynderContentHandler;
import com.escape.synder.setters.Setter;

/**
 * Handler for Guid element.
 * Marshals into SyndLinkImpl.
 * Captres "{usuri}element"->SyndLinkImpl.type, isPermaLink->SyndLinkImpl.rel.
 * @author escape-llc
 *
 * @param <R> Hosting class.
 */
public class GuidElement<R> extends LinkElement<R> {
	final StringBuilder sb;
	public GuidElement(ParseContext ctx, R root, Setter sx) {
		super(ctx, root, sx);
		sb = new StringBuilder();
	}
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		link.setType(SynderContentHandler.formatTag(uri, localName));
		link.setRel(attributes.getValue("isPermaLink"));
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
