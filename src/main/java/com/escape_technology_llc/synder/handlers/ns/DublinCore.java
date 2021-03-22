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
import com.escape_technology_llc.synder.setters.Setter;
import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.module.impl.DCModuleImpl;

/**
 * Namespace handler for DCMI Elements 1.1.
 * @author escape-llc
 *
 */
public class DublinCore extends ModuleHandlerImpl<DCModuleImpl> {
	// these are literals for efficiency
	public static final String TITLE = "{http://purl.org/dc/elements/1.1/}title";
	public static final String CREATOR = "{http://purl.org/dc/elements/1.1/}creator";
	public static final String SUBJECT = "{http://purl.org/dc/elements/1.1/}subject";
	public static final String DESCRIPTION = "{http://purl.org/dc/elements/1.1/}description";
	public static final String PUBLISHER = "{http://purl.org/dc/elements/1.1/}publisher";
	public static final String CONTRIBUTOR = "{http://purl.org/dc/elements/1.1/}contributor";
	public static final String DATE = "{http://purl.org/dc/elements/1.1/}date";
	public static final String TYPE = "{http://purl.org/dc/elements/1.1/}type";
	public static final String FORMAT = "{http://purl.org/dc/elements/1.1/}format";
	public static final String IDENTIFIER = "{http://purl.org/dc/elements/1.1/}identifier";
	public static final String SOURCE = "{http://purl.org/dc/elements/1.1/}source";
	public static final String LANGUAGE = "{http://purl.org/dc/elements/1.1/}language";
	public static final String RELATION = "{http://purl.org/dc/elements/1.1/}relation";
	public static final String COVERAGE = "{http://purl.org/dc/elements/1.1/}coverage";
	public static final String RIGHTS = "{http://purl.org/dc/elements/1.1/}rights";
	static final HashMap<String,HandlerFactory<DCModuleImpl>> props;
	static {
		props = new HashMap<String,HandlerFactory<DCModuleImpl>>();
		try {
			props.put(TITLE, new DC_SimpleElement(
					new Setter(DCModuleImpl.class, "Title")));
			props.put(CREATOR, new DC_SimpleElement(
					new Setter(DCModuleImpl.class, "Creator")));
			//props.put(SUBJECT, new DC_SimpleElement(
			//		new Setter(DCModuleImpl.class, "Subject")));
			props.put(DESCRIPTION, new DC_SimpleElement(
					new Setter(DCModuleImpl.class, "Description")));
			props.put(PUBLISHER, new DC_SimpleElement(
					new Setter(DCModuleImpl.class, "Publisher")));
			props.put(CONTRIBUTOR, new DC_SimpleElement(
					new Setter(DCModuleImpl.class, "Contributor")));
			props.put(DATE, new DC_SimpleElement(
					new DateSetter(DCModuleImpl.class, "Date")));
			props.put(TYPE, new DC_SimpleElement(
					new Setter(DCModuleImpl.class, "Type")));
			props.put(FORMAT, new DC_SimpleElement(
					new Setter(DCModuleImpl.class, "Format")));
			props.put(IDENTIFIER, new DC_SimpleElement(
					new Setter(DCModuleImpl.class, "Identifier")));
			props.put(SOURCE, new DC_SimpleElement(
					new Setter(DCModuleImpl.class, "Source")));
			props.put(LANGUAGE, new DC_SimpleElement(
					new Setter(DCModuleImpl.class, "Language")));
			props.put(RELATION, new DC_SimpleElement(
					new Setter(DCModuleImpl.class, "Relation")));
			props.put(COVERAGE, new DC_SimpleElement(
					new Setter(DCModuleImpl.class, "Coverage")));
			props.put(RIGHTS, new DC_SimpleElement(
					new Setter(DCModuleImpl.class, "Rights")));
		}
		catch(Exception ex) {
		}
	}
	static final class DC_SimpleElement extends HandlerFactory<DCModuleImpl> {
		protected DC_SimpleElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax,
				DCModuleImpl root) {
			return new SimpleElement<DCModuleImpl>(pc, root, sx);
		}
	}
	public DublinCore(ParseContext ctx) {
		super(DCModule.URI_ELEMENTS, ctx);
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path,
			SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		final Object root = tos != null ? tos.getRoot() : null;
		if(root == null) return null;
		final String nx = SynderContentHandler.formatTag(uri, localName);
		if(props.containsKey(nx)) {
			final DCModuleImpl dc = getModule(root);
			if(dc != null) {
				return props.get(nx).create(pc, attributes, dc);
			}
		}
		return null;
	}
	@Override
	protected DCModuleImpl newInstance() {
		return new DCModuleImpl();
	}
}
