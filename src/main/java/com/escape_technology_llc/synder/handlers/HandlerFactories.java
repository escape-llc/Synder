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
import com.escape_technology_llc.synder.handlers.search.ImageElement;
import com.escape_technology_llc.synder.setters.Setter;
import com.sun.syndication.feed.synd.impl.SyndEntryImpl;
import com.sun.syndication.feed.synd.impl.SyndFeedImpl;

/**
 * Commonly-required handler factory implementations.
 * @author escape-llc
 *
 */
public final class HandlerFactories {
	public static final class Feed_SimpleLinkElement extends HandlerFactory<SyndFeedImpl> {
		public Feed_SimpleLinkElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndFeedImpl root) {
			return new SimpleLinkElement<SyndFeedImpl>(pc, root, sx);
		}
	}
	public static final class Feed_SimpleImageElement extends HandlerFactory<SyndFeedImpl> {
		public Feed_SimpleImageElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndFeedImpl root) {
			return new SimpleImageElement<SyndFeedImpl>(pc, root, sx);
		}
	}
	public static final class Feed_SimpleElement extends HandlerFactory<SyndFeedImpl> {
		public Feed_SimpleElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndFeedImpl root) {
			return new SimpleElement<SyndFeedImpl>(pc, root, sx);
		}
	}
	public static final class Feed_ContentElement extends HandlerFactory<SyndFeedImpl> {
		public Feed_ContentElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndFeedImpl root) {
			return new ContentElement<SyndFeedImpl>(pc, root, sx);
		}
	}
	public static final class Feed_LinkElement extends HandlerFactory<SyndFeedImpl> {
		public Feed_LinkElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndFeedImpl root) {
			return new LinkElement<SyndFeedImpl>(pc, root, sx);
		}
	}
	public static final class Feed_GeneratorElement extends HandlerFactory<SyndFeedImpl> {
		public Feed_GeneratorElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndFeedImpl root) {
			return new GeneratorElement<SyndFeedImpl>(pc, root, sx);
		}
	}
	public static final class Feed_CategoryElement extends HandlerFactory<SyndFeedImpl> {
		public Feed_CategoryElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndFeedImpl root) {
			return new CategoryElement<SyndFeedImpl>(pc, root, sx);
		}
	}
	public static final class Entry_RssLinkElement extends HandlerFactory<SyndEntryImpl> {
		public Entry_RssLinkElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndEntryImpl root) {
			return new RssLinkElement<SyndEntryImpl>(pc, root, sx);
		}
	}
	public static final class Entry_SimpleLinkElement extends HandlerFactory<SyndEntryImpl> {
		public Entry_SimpleLinkElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndEntryImpl root) {
			return new SimpleLinkElement<SyndEntryImpl>(pc, root, sx);
		}
	}
	public static final class Entry_GuidElement extends HandlerFactory<SyndEntryImpl> {
		public Entry_GuidElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndEntryImpl root) {
			return new GuidElement<SyndEntryImpl>(pc, root, sx);
		}
	}
	public static final class Entry_SimpleElement extends HandlerFactory<SyndEntryImpl> {
		public Entry_SimpleElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndEntryImpl root) {
			return new SimpleElement<SyndEntryImpl>(pc, root, sx);
		}
	}
	public static final class Entry_ContentElement extends HandlerFactory<SyndEntryImpl> {
		public Entry_ContentElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndEntryImpl root) {
			return new ContentElement<SyndEntryImpl>(pc, root, sx);
		}
	}
	public static final class Entry_EnclosureElement extends HandlerFactory<SyndEntryImpl> {
		public Entry_EnclosureElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndEntryImpl root) {
			return new EnclosureElement(pc, root, sx);
		}
	}
	public static final class Entry_CategoryElement extends HandlerFactory<SyndEntryImpl> {
		public Entry_CategoryElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndEntryImpl root) {
			return new CategoryElement<SyndEntryImpl>(pc, root, sx);
		}
	}
	public static final class Entry_AtomLinkElement extends HandlerFactory<SyndEntryImpl> {
		final Setter esx;
		/**
		 * Ctor.
		 * Uses link[@rel='enclosure'] as the decision.
		 * @param sx Links setter.
		 * @param esx Enclosures setter.
		 */
		public Entry_AtomLinkElement(Setter sx, Setter esx) {
			super(sx);
			if(esx == null) throw new IllegalArgumentException("esx");
			this.esx = esx;
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndEntryImpl root) {
			final String rel = ax.getValue("rel");
			if ("enclosure".equalsIgnoreCase(rel)) {
				final EnclosureElement le = new EnclosureElement(pc, root, esx);
				return le;
			} else {
				final LinkElement<SyndEntryImpl> le = new LinkElement<SyndEntryImpl>(pc, root, sx);
				return le;
			}
		}
	}
	public static final class Entry_SearchImageElement extends HandlerFactory<SyndEntryImpl> {
		public Entry_SearchImageElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, SyndEntryImpl root) {
			return new ImageElement<SyndEntryImpl>(pc, root, sx);
		}
	}
}
