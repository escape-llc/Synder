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
import com.sun.syndication.feed.synd.impl.SyndContentImpl;

/**
 * Intented to capture entity-escaped or CDATA-formatted content.
 * Captures attributes: type-&gt;SyndContent.Type, else DEFAULT_TYPE.
 * Captures element localName/Name-&gt;SyndContent.Mode.
 * Captures characters-&gt;Value.
 * @author escape-llc
 *
 * @param <R> Hosting class.
 */
public class ContentElement<R> extends ElementHandler<R> {
	public static String DEFAULT_TYPE = "text/content";
	final SyndContentImpl item;
	final StringBuilder sb;
	public ContentElement(ParseContext ctx, R root, Setter sx) {
		super(ctx, root, sx);
		item = new SyndContentImpl();
		sb = new StringBuilder();
	}
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		final String nx = localName.length() == 0 ? name : localName;
		item.setMode(nx);
		final String type = attributes.getValue("type");
		if(type != null && type.length() > 0) {
			item.setType(type);
		}
		else {
			item.setType(DEFAULT_TYPE);
		}
	}
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}
	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		item.setValue(sb.toString());
		sx.set(root, item, ctx);
	}
}
