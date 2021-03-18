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

import org.xml.sax.SAXException;

import com.escape.synder.ParseContext;
import com.escape.synder.setters.Setter;
import com.sun.syndication.feed.synd.impl.SyndImageImpl;

/**
 * Handler for Image element with Url as content.
 * Marshals to SyndImage.
 * Captures characters->Url.
 * @author escape-llc
 *
 * @param <R>
 */
public class SimpleImageElement<R> extends ElementHandler<R> {
	final SyndImageImpl item;
	final StringBuilder sb;
	public SimpleImageElement(ParseContext ctx, R root, Setter sx) {
		super(ctx, root, sx);
		item = new SyndImageImpl();
		sb = new StringBuilder();
	}
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}
	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		item.setUrl(sb.toString());
		sx.set(root, item, ctx);
	}
}
