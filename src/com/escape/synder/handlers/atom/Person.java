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
package com.escape.synder.handlers.atom;

import java.util.HashMap;

import org.xml.sax.Attributes;

import com.escape.synder.ParseContext;
import com.escape.synder.SynderContentHandler;
import com.escape.synder.handlers.Atom10;
import com.escape.synder.handlers.SimpleElement;
import com.escape.synder.handlers.SubHandler;
import com.escape.synder.setters.Setter;
import com.sun.syndication.feed.synd.impl.SyndPersonImpl;

/**
 * Handler for Atom Person element.
 * Captures Name, Uri, Email.
 * @author escape-llc
 *
 * @param <R> Class to attach.
 */
public class Person<R> extends SubHandler<SyndPersonImpl> {
	static final HashMap<String,Setter> props;
	static {
		props = new HashMap<String,Setter>();
		try {
			props.put(Atom10.NAME, new Setter(SyndPersonImpl.class, "Name"));
			props.put(Atom10.URI, new Setter(SyndPersonImpl.class, "Uri"));
			props.put(Atom10.EMAIL, new Setter(SyndPersonImpl.class, "Email"));
		} catch (Exception e) {
		}
	}
	public Person(ParseContext ctx, SyndPersonImpl root) {
		super(ctx, root);
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path,
			SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		final String nx = SynderContentHandler.formatTag(uri, localName);
		if(props.containsKey(nx)) {
			final SimpleElement<SyndPersonImpl> se = new SimpleElement<SyndPersonImpl>(pc, root, props.get(nx));
			return se;
		}
		return super.query(pc, path, this, uri, localName, name, attributes);
	}
}
