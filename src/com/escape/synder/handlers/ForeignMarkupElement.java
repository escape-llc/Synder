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

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.escape.synder.ParseContext;
import com.escape.synder.SyndForeignMarkupImpl;
import com.escape.synder.setters.Setter;

/**
 * Foreign markup element.
 * All intervening markup is collected into a string, including
 * startElement() and endElement().
 * @author escape-llc
 *
 * @param <R> Hosting class.
 */
public class ForeignMarkupElement<R> extends ElementHandler<R> {
	final int FLAG = -1;
	final SyndForeignMarkupImpl item;
	final StringBuilder sb;
	int level;
	public ForeignMarkupElement(ParseContext ctx, R root, Setter sx) {
		super(ctx, root, sx);
		item = new SyndForeignMarkupImpl();
		final HashMap<String,String> hm = new HashMap<String,String>();
		ctx.getNamespaceMap(hm);
		item.setNamespaces(hm);
		sb = new StringBuilder();
		level = FLAG;
	}
	/**
	 * Once we are on the eval stack, keep collecting any nested elements.
	 */
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path,
			SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		return level > FLAG ? this : super.query(pc, path, tos, uri, localName, name, attributes);
	}
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		if (level == FLAG) {
			item.setType("text/xml");
			item.setNamespaceUri(uri);
		}
		level++;
		sb.append("<");
		sb.append(name);
		if(attributes.getLength() > 0) {
			for(int ix = 0; ix < attributes.getLength(); ix++) {
				final String ln = attributes.getLocalName(ix);
				final String vx = attributes.getValue(ix);
				sb.append(" ");
				sb.append(ln);
				sb.append("=\"");
				sb.append(vx);
				sb.append("\"");
			}
		}
		sb.append(">");
	}
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}
	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		sb.append("</");
		sb.append(arg2);
		sb.append(">");
		level--;
		if (level == FLAG) {
			item.setValue(sb.toString());
			sx.set(root, item, ctx);
		}
	}
}
