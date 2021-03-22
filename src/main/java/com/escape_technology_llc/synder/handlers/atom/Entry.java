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
package com.escape_technology_llc.synder.handlers.atom;

import java.util.HashMap;
import java.util.List;

import org.xml.sax.Attributes;

import com.escape_technology_llc.synder.SynderContentHandler;
import com.escape_technology_llc.synder.handlers.Atom10;
import com.escape_technology_llc.synder.handlers.ForeignMarkupElement;
import com.escape_technology_llc.synder.handlers.HandlerFactories;
import com.escape_technology_llc.synder.handlers.HandlerFactory;
import com.escape_technology_llc.synder.handlers.HandlerImpl;
import com.escape_technology_llc.synder.handlers.SubHandler;
import com.escape_technology_llc.synder.setters.Setter;
import com.escape_technology_llc.synder.setters.SyndCategoryListSetter;
import com.escape_technology_llc.synder.setters.SyndContentListSetter;
import com.escape_technology_llc.synder.setters.SyndContentSetter;
import com.escape_technology_llc.synder.setters.SyndEnclosureListSetter;
import com.escape_technology_llc.synder.setters.SyndForeignMarkupListSetter;
import com.escape_technology_llc.synder.setters.SyndLinkListSetter;
import com.escape_technology_llc.synder.setters.W3CDateSetter;
import com.escape_technology_llc.synder.ParseContext;
import com.sun.syndication.feed.synd.impl.SyndEntryImpl;

/**
 * Handler for Atom Entry element.
 * Captures title-&gt;TitleEx, id-&gt;Link, content-&gt;add to Contents, published-&gt;PublishedDate, updated-&gt;UpdatedDate, rights-&gt;Rights,
 * link-&gt;add to Links or Enclosures depending on REL attribute, category-&gt;add to Categories, summary-&gt;add to Contents.
 * Authors and Contributors are captured by the Atom10 implementation.
 * @author escape-llc
 *
 */
public class Entry extends SubHandler<SyndEntryImpl> {
	static final HashMap<String,HandlerFactory<SyndEntryImpl>> props;
	static {
		props = new HashMap<String,HandlerFactory<SyndEntryImpl>>();
		try {
			props.put(Atom10.TITLE, new HandlerFactories.Entry_ContentElement(new SyndContentSetter(SyndEntryImpl.class, "TitleEx")));
			props.put(Atom10.ID, new HandlerFactories.Entry_SimpleElement(new Setter(SyndEntryImpl.class, "Link")));
			props.put(Atom10.CONTENT, new HandlerFactories.Entry_ContentElement(new SyndContentListSetter(SyndEntryImpl.class, "Contents")));
			props.put(Atom10.PUBLISHED, new HandlerFactories.Entry_SimpleElement(new W3CDateSetter(SyndEntryImpl.class, "PublishedDate")));
			props.put(Atom10.UPDATED, new HandlerFactories.Entry_SimpleElement(new W3CDateSetter(SyndEntryImpl.class, "UpdatedDate")));
			props.put(Atom10.LINK, new HandlerFactories.Entry_AtomLinkElement(
					new SyndLinkListSetter(SyndEntryImpl.class, "Links"),
					new SyndEnclosureListSetter(SyndEntryImpl.class, "Enclosures")));
			props.put(Atom10.CATEGORY, new HandlerFactories.Entry_CategoryElement(new SyndCategoryListSetter(SyndEntryImpl.class, "Categories")));
			props.put(Atom10.SUMMARY, new HandlerFactories.Entry_ContentElement(new SyndContentListSetter(SyndEntryImpl.class, "Contents")));
			props.put(Atom10.RIGHTS, new HandlerFactories.Entry_ContentElement(new SyndContentSetter(SyndEntryImpl.class, "Rights")));
		} catch (Exception e) {
			//System.err.println(e);
		}
	}
	final boolean fm;
	public Entry(ParseContext ctx, SyndEntryImpl root, List<HandlerImpl> nsx) {
		super(ctx, root, nsx);
		fm = ctx.getOption(ParseContext.Features.FOREIGN_MARKUP_ENTRY) == null;
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path,
			SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		final String nx = SynderContentHandler.formatTag(uri, localName);
		if(props.containsKey(nx)) {
			return props.get(nx).create(pc, attributes, root);
		}
		else {
			final SubHandler<?> qx = super.query(pc, path, this, uri, localName, name, attributes);
			if(fm) return qx;
			if(qx != null) return qx;
			if(tos instanceof ForeignMarkupElement<?>)
				return tos;
			try {
				final ForeignMarkupElement<SyndEntryImpl> fme = new ForeignMarkupElement<SyndEntryImpl>(
						pc, root, new SyndForeignMarkupListSetter(SyndEntryImpl.class, "ForeignMarkup"));
				return fme;
			} catch (Exception e) {
				return qx;
			}
		}
	}
}
