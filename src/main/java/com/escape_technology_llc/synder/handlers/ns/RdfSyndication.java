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
package com.escape_technology_llc.synder.handlers.ns;

import java.util.HashMap;

import org.xml.sax.Attributes;

import com.escape_technology_llc.synder.ParseContext;
import com.escape_technology_llc.synder.SynderContentHandler;
import com.escape_technology_llc.synder.handlers.HandlerFactory;
import com.escape_technology_llc.synder.handlers.ModuleHandlerImpl;
import com.escape_technology_llc.synder.handlers.SimpleElement;
import com.escape_technology_llc.synder.handlers.SubHandler;
import com.escape_technology_llc.synder.setters.DateSetter;
import com.escape_technology_llc.synder.setters.IntSetter;
import com.escape_technology_llc.synder.setters.Setter;
import com.sun.syndication.feed.module.SyModule;
import com.sun.syndication.feed.module.impl.SyModuleImpl;

/**
 * Namespace handler for RDF Syndication.
 * @author escape-llc
 *
 */
public class RdfSyndication extends ModuleHandlerImpl<SyModuleImpl> {
	public static final String UPDATE_PERIOD = "{http://purl.org/rss/1.0/modules/syndication/}updatePeriod";
	public static final String UPDATE_FREQ = "{http://purl.org/rss/1.0/modules/syndication/}updateFrequency";
	public static final String UPDATE_BASE = "{http://purl.org/rss/1.0/modules/syndication/}updateBase";
	static final HashMap<String,HandlerFactory<SyModuleImpl>> props;
	static {
		props = new HashMap<String,HandlerFactory<SyModuleImpl>>();
		try {
			props.put(UPDATE_BASE, new SY_SimpleElement(
					new DateSetter(SyModuleImpl.class, "UpdateBase")));
			props.put(UPDATE_FREQ, new SY_SimpleElement(
					new IntSetter(SyModuleImpl.class, "UpdateFrequency")));
			props.put(UPDATE_PERIOD, new SY_SimpleElement(
					new Setter(SyModuleImpl.class, "UpdatePeriod")));
		}
		catch(Exception ex) {
		}
	}
	static final class SY_SimpleElement extends HandlerFactory<SyModuleImpl> {
		protected SY_SimpleElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax,
				SyModuleImpl root) {
			return new SimpleElement<SyModuleImpl>(pc, root, sx);
		}
	}
	public RdfSyndication(ParseContext ctx) {
		super(SyModule.URI, ctx);
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path,
			SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		final Object root = tos != null ? tos.getRoot() : null;
		if(root == null) return null;
		final String nx = SynderContentHandler.formatTag(uri, localName);
		if(props.containsKey(nx)) {
			final SyModuleImpl dc = getModule(root);
			if(dc != null) {
				return props.get(nx).create(pc, attributes, dc);
			}
		}
		return null;
	}
	@Override
	protected SyModuleImpl newInstance() {
		return new SyModuleImpl();
	}
}
