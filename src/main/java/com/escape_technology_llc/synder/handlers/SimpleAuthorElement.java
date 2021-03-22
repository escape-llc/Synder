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

import org.xml.sax.SAXException;

import com.escape_technology_llc.synder.ParseContext;
import com.escape_technology_llc.synder.setters.Setter;
import com.sun.syndication.feed.synd.impl.SyndPersonImpl;

/**
 * Handler for "link" with Simple Content as the HREF.
 * Marshals into SyndLinkImpl.
 * Captures localName->SyndLinkImpl.type.
 * Captures characters->Email, Name.
 * @author escape-llc
 *
 * @param <R>
 */
public class SimpleAuthorElement<R> extends SimpleElement<R> {
	final SyndPersonImpl item;
	public SimpleAuthorElement(ParseContext ctx, R root, Setter sx) {
		super(ctx, root, sx);
		item = new SyndPersonImpl();
	}
	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		item.setEmail(sb.toString());
		item.setName(sb.toString());
		sx.set(root, item, ctx);
	}
}
