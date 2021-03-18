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
package com.escape.synder.handlers.rss;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.escape.synder.ParseContext;
import com.escape.synder.SynderContentHandler;
import com.escape.synder.handlers.ElementHandler;
import com.escape.synder.handlers.RssAny;
import com.escape.synder.handlers.SimpleElement;
import com.escape.synder.handlers.SubHandler;
import com.escape.synder.setters.Setter;
import com.sun.syndication.feed.synd.impl.SyndImageImpl;

/**
 * Handler for RSS Image element.
 * Captures Link, Url, Title.
 * @author escape-llc
 *
 * @param <R> Class to bind property.
 */
public class Image<R> extends ElementHandler<R> {
	static final HashMap<String,Setter> props;
	static {
		props = new HashMap<String,Setter>();
		try {
			props.put("link", new Setter(SyndImageImpl.class, "Link"));
			props.put("url", new Setter(SyndImageImpl.class, "Url"));
			props.put("title", new Setter(SyndImageImpl.class, "Title"));
		} catch (Exception e) {
		}
	}
	final SyndImageImpl item;
	public Image(ParseContext ctx, R root, Setter sx) {
		super(ctx, root, sx);
		item = new SyndImageImpl();
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path,
			SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		final String nx = SynderContentHandler.formatTag(RssAny.RSS10_NSURI.equals(uri) ? "" : uri, localName);
		if(props.containsKey(nx)) {
			final SimpleElement<SyndImageImpl> se = new SimpleElement<SyndImageImpl>(pc, item, props.get(nx));
			return se;
		}
		return super.query(pc, path, this, uri, localName, name, attributes);
	}
	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		sx.set(root, item, ctx);
	}
}
