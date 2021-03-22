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

import org.xml.sax.Attributes;

import com.escape_technology_llc.synder.ParseContext;
import com.escape_technology_llc.synder.handlers.rss.Channel;
import com.escape_technology_llc.synder.handlers.rss.Image;
import com.escape_technology_llc.synder.handlers.rss.Item;
import com.escape_technology_llc.synder.setters.Setter;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.feed.synd.impl.SyndEntryImpl;
import com.sun.syndication.feed.synd.impl.SyndFeedImpl;

/**
 * Top-level Synder handler for RSS Any Version including RSS/RDF namespace.
 * Enforces version attribute, but does not check the value (except for NULL).
 * Defaults to RSS 2.0 semantics.
 * Tag is "rss_any".
 * @author escape-llc
 *
 */
public class RssAny extends HandlerImpl {
	public static String RDF_URI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public static String RSS10_NSURI = "http://purl.org/rss/1.0/";
	public static String TAG = "rss_any";
	public static String RDF_RSS10 = "1.0.rdf";
	public RssAny(ParseContext ctx) {
		super(TAG, ctx);
	}
	protected RssAny(String tag, ParseContext ctx) {
		super(tag, ctx);
	}
	@Override
	public boolean detect(String uri, String localName, String name,
			Attributes attributes) {
		if("rss".equalsIgnoreCase(localName)) {
			final String rv = attributes.getValue("version");
			if (rv != null && rv.length() > 0)
				return true;
		}
		else if("RDF".equalsIgnoreCase(localName) && RDF_URI.equalsIgnoreCase(uri)) {
			// for some reason, attributes.getLength() is Zero, even though debug inspection
			// shows there are attributes for the XMLNS attributes!
			// we should be checking for XMLNS=RSS10_NSURI attribute.
			return true;
		}
		return false;
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path,
			SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		final String nx = localName.length() == 0 ? name : localName;
		if ("rss".equals(localName) || "RDF".equals(localName)) {
			final SyndFeedImpl sfi = new SyndFeedImpl();
			final String rv = "rss".equals(localName) ? attributes.getValue("version") : RDF_RSS10;
			String ft = getTag();
			if(TAG.equals(ft))
				ft += (rv != null && rv.length() > 0 ? "/" + rv : "");
			sfi.setFeedType(ft);
			final SubHandler<SyndFeedImpl> sh = new SubHandler<SyndFeedImpl>(
					pc, sfi);
			return sh;
		}
		else if ("channel".equalsIgnoreCase(nx)) {
			final SyndFeedImpl sfi = (SyndFeedImpl) tos.getRoot();
			final Channel fx = new Channel(pc, sfi, fns);
			return fx;
		}
		else if (RSS10_NSURI.equals(uri) && "image".equalsIgnoreCase(nx)) {
			// this is for RDF/RSS1.0
			final SyndFeedImpl sfi = (SyndFeedImpl) tos.getRoot();
			try {
				return new Image<SyndFeedImpl>(pc, sfi, new Setter(SyndFeedImpl.class, "Image", SyndImage.class));
			} catch (Exception e) {
				return null;
			}
		}
		else if("item".equalsIgnoreCase(nx)) {
			final SyndFeedImpl sfi = (SyndFeedImpl) tos.getRoot();
			final SyndEntryImpl sie = new SyndEntryImpl();
			sie.setSource(sfi);
			sfi.getEntries().add(sie);
			final Item fx = new Item(pc, sie, ins);
			return fx;
		}
		return null;
	}
}
