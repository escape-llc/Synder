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
package com.escape.synder.handlers.ns;

import java.util.HashMap;

import org.xml.sax.Attributes;

import com.escape.synder.ParseContext;
import com.escape.synder.SynderContentHandler;
import com.escape.synder.handlers.ContentElement;
import com.escape.synder.handlers.HandlerFactory;
import com.escape.synder.handlers.ModuleHandlerImpl;
import com.escape.synder.handlers.SubHandler;
import com.escape.synder.setters.Setter;
import com.escape.synder.setters.SyndContentSetter;
import com.sun.syndication.feed.module.ContentModule;
import com.sun.syndication.feed.module.impl.ContentModuleImpl;

/**
 * Namespace handler for RDF Content.
 * @author escape-llc
 *
 */
public class RdfContent extends ModuleHandlerImpl<ContentModuleImpl> {
	public static final String ENCODED = "{http://purl.org/rss/1.0/modules/content/}encoded";
	static final HashMap<String,HandlerFactory<ContentModuleImpl>> props;
	static {
		props = new HashMap<String,HandlerFactory<ContentModuleImpl>>();
		try {
			props.put(ENCODED, new C_ContentElement(
					new SyndContentSetter(ContentModuleImpl.class, "Encoded")));
		}
		catch(Exception ex) {
		}
	}
	static final class C_ContentElement extends HandlerFactory<ContentModuleImpl> {
		protected C_ContentElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax,
				ContentModuleImpl root) {
			return new ContentElement<ContentModuleImpl>(pc, root, sx);
		}
	}
	public RdfContent(ParseContext ctx) {
		super(ContentModule.URI, ctx);
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path,
			SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		final Object root = tos != null ? tos.getRoot() : null;
		if(root == null) return null;
		final String nx = SynderContentHandler.formatTag(uri, localName);
		if(props.containsKey(nx)) {
			final ContentModuleImpl dc = getModule(root);
			if(dc != null) {
				return props.get(nx).create(pc, attributes, dc);
			}
		}
		return null;
	}
	@Override
	protected ContentModuleImpl newInstance() {
		return new ContentModuleImpl();
	}
}
