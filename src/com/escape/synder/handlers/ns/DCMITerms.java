/*
 * Copyright 2015 eScape Technology LLC.
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
import com.escape.synder.handlers.HandlerFactory;
import com.escape.synder.handlers.ModuleHandlerImpl;
import com.escape.synder.handlers.SimpleElement;
import com.escape.synder.handlers.SubHandler;
import com.escape.synder.setters.DateSetter;
import com.escape.synder.setters.Setter;
import com.sun.syndication.feed.module.DCTermsModule;
import com.sun.syndication.feed.module.impl.DCTermsModuleImpl;

/**
 * Namespace handler for DCMI Terms.
 * Also includes the items from DCMI Elements under the DCMI Terms namespace.
 * <b>Does not include</b> items using the DCMI Elements namespace; those are captured by the <b>DublinCore</b> module handler.
 * @author escape-llc
 *
 */
public class DCMITerms extends ModuleHandlerImpl<DCTermsModuleImpl> {
	// these are literals for efficiency
	public static final String TITLE = "{http://purl.org/dc/terms/}title";
	public static final String CREATOR = "{http://purl.org/dc/terms/}creator";
	public static final String SUBJECT = "{http://purl.org/dc/terms/}subject";
	public static final String DESCRIPTION = "{http://purl.org/dc/terms/}description";
	public static final String PUBLISHER = "{http://purl.org/dc/terms/}publisher";
	public static final String CONTRIBUTOR = "{http://purl.org/dc/terms/}contributor";
	public static final String DATE = "{http://purl.org/dc/terms/}date";
	public static final String CREATED = "{http://purl.org/dc/terms/}created";
	public static final String ISSUED = "{http://purl.org/dc/terms/}issued";
	public static final String MODIFIED = "{http://purl.org/dc/terms/}modified";
	public static final String TYPE = "{http://purl.org/dc/terms/}type";
	public static final String FORMAT = "{http://purl.org/dc/terms/}format";
	public static final String IDENTIFIER = "{http://purl.org/dc/terms/}identifier";
	public static final String SOURCE = "{http://purl.org/dc/terms/}source";
	public static final String LANGUAGE = "{http://purl.org/dc/terms/}language";
	public static final String LICENSE = "{http://purl.org/dc/terms/}license";
	public static final String RELATION = "{http://purl.org/dc/terms/}relation";
	public static final String COVERAGE = "{http://purl.org/dc/terms/}coverage";
	public static final String RIGHTS = "{http://purl.org/dc/terms/}rights";
	static final HashMap<String, HandlerFactory<DCTermsModuleImpl>> props;
	static {
		props = new HashMap<String, HandlerFactory<DCTermsModuleImpl>>();
		try {
			props.put(CONTRIBUTOR, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Contributor")));
			props.put(COVERAGE, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Coverage")));
			props.put(CREATED, new DC_SimpleElement(new DateSetter(DCTermsModuleImpl.class, "Created")));
			props.put(CREATOR, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Creator")));
			props.put(DATE, new DC_SimpleElement(new DateSetter(DCTermsModuleImpl.class, "Date")));
			props.put(DESCRIPTION, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Description")));
			props.put(FORMAT, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Format")));
			props.put(IDENTIFIER, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Identifier")));
			props.put(ISSUED, new DC_SimpleElement(new DateSetter(DCTermsModuleImpl.class, "Issued")));
			props.put(LANGUAGE, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Language")));
			props.put(LICENSE, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "License")));
			props.put(MODIFIED, new DC_SimpleElement(new DateSetter(DCTermsModuleImpl.class, "Modified")));
			props.put(PUBLISHER, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Publisher")));
			props.put(RELATION, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Relation")));
			props.put(RIGHTS, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Rights")));
			props.put(SOURCE, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Source")));
			//props.put(SUBJECT, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Subject")));
			props.put(TITLE, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Title")));
			props.put(TYPE, new DC_SimpleElement(new Setter(DCTermsModuleImpl.class, "Type")));
		}
		catch(Exception ex) {
		}
	}
	static final class DC_SimpleElement extends HandlerFactory<DCTermsModuleImpl> {
		protected DC_SimpleElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, DCTermsModuleImpl root) {
			return new SimpleElement<DCTermsModuleImpl>(pc, root, sx);
		}
	}
	public DCMITerms(ParseContext ctx) {
		super(DCTermsModule.URI_TERMS, ctx);
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path,
			SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		final Object root = tos != null ? tos.getRoot() : null;
		if(root == null) return null;
		final String nx = SynderContentHandler.formatTag(uri, localName);
		if(props.containsKey(nx)) {
			final DCTermsModuleImpl dc = getModule(root);
			if(dc != null) {
				return props.get(nx).create(pc, attributes, dc);
			}
		}
		return null;
	}
	@Override
	protected DCTermsModuleImpl newInstance() {
		return new DCTermsModuleImpl();
	}
}
