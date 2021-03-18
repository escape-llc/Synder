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
import com.escape.synder.handlers.SimpleElement;
import com.escape.synder.handlers.SubHandler;
import com.escape.synder.setters.Setter;
import com.escape.synder.setters.SyndContentSetter;
import com.sun.syndication.feed.module.impl.ItunesModuleImpl;

/**
 * Namespace handler for Itunes.
 * @author escape-llc
 *
 */
public class Itunes extends ModuleHandlerImpl<ItunesModuleImpl> {
	public static final String AUTHOR = "{http://www.itunes.com/dtds/podcast-1.0.dtd}author";
	public static final String DURATION = "{http://www.itunes.com/dtds/podcast-1.0.dtd}duration";
	public static final String EXPLICIT = "{http://www.itunes.com/dtds/podcast-1.0.dtd}explicit";
	public static final String KEYWORDS = "{http://www.itunes.com/dtds/podcast-1.0.dtd}keywords";
	public static final String SUBTITLE = "{http://www.itunes.com/dtds/podcast-1.0.dtd}subtitle";
	public static final String SUMMARY = "{http://www.itunes.com/dtds/podcast-1.0.dtd}summary";
	static final HashMap<String,HandlerFactory<ItunesModuleImpl>> props;
	static {
		props = new HashMap<String,HandlerFactory<ItunesModuleImpl>>();
		try {
			props.put(AUTHOR, new C_SimpleElement(
					new Setter(ItunesModuleImpl.class, "Author")));
			props.put(DURATION, new C_SimpleElement(
					new Setter(ItunesModuleImpl.class, "Duration")));
			props.put(EXPLICIT, new C_SimpleElement(
					new Setter(ItunesModuleImpl.class, "Explicit")));
			props.put(KEYWORDS, new C_SimpleElement(
					new Setter(ItunesModuleImpl.class, "Keywords")));
			props.put(SUBTITLE, new C_SimpleElement(
					new Setter(ItunesModuleImpl.class, "Subtitle")));
			props.put(SUMMARY, new C_ContentElement(
					new SyndContentSetter(ItunesModuleImpl.class, "Summary")));
		}
		catch(Exception ex) {
		}
	}
	static final class C_SimpleElement extends HandlerFactory<ItunesModuleImpl> {
		protected C_SimpleElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax,
				ItunesModuleImpl root) {
			return new SimpleElement<ItunesModuleImpl>(pc, root, sx);
		}
	}
	static final class C_ContentElement extends HandlerFactory<ItunesModuleImpl> {
		protected C_ContentElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax,
				ItunesModuleImpl root) {
			return new ContentElement<ItunesModuleImpl>(pc, root, sx);
		}
	}
	public Itunes(ParseContext ctx) {
		super(ItunesModuleImpl.URI, ctx);
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path,
			SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		final Object root = tos != null ? tos.getRoot() : null;
		if(root == null) return null;
		final String nx = SynderContentHandler.formatTag(uri, localName);
		if(props.containsKey(nx)) {
			final ItunesModuleImpl dc = getModule(root);
			if(dc != null) {
				return props.get(nx).create(pc, attributes, dc);
			}
		}
		return null;
	}
	@Override
	protected ItunesModuleImpl newInstance() {
		return new ItunesModuleImpl();
	}
}
