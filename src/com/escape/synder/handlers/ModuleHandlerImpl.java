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
package com.escape.synder.handlers;

import java.util.HashSet;

import org.xml.sax.Attributes;

import com.escape.synder.ParseContext;
import com.sun.syndication.feed.module.Module;
import com.sun.syndication.feed.module.impl.ModuleImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * Core implementation for namespace modules.
 * @author escape-llc
 *
 * @param <T> Module implementation class.
 */
public abstract class ModuleHandlerImpl<T extends ModuleImpl> extends HandlerImpl {
	protected ModuleHandlerImpl(String tag, ParseContext ctx) {
		super(tag, ctx);
	}
	/**
	 * Create a new instance of the module.
	 * @return new instance.
	 */
	protected abstract T newInstance();
	/**
	 * Obtain or create the module, and make sure it is added to the root
	 * instance, if possible.
	 * @param root SyndFeed or SyndEntry
	 * @return !NULL: module; NULL: could not add module.
	 */
	@SuppressWarnings("unchecked")
	protected T getModule(Object root) {
		T dc = null;
		if(root instanceof SyndFeed) {
			final Module mx = ((SyndFeed)root).getModule(getTag());
			if(mx == null) {
				dc = newInstance();
				((SyndFeed)root).getModules().add(dc);
			}
			else {
				dc = (T)mx;
			}
		}
		else if(root instanceof SyndEntry) {
			final Module mx = ((SyndEntry)root).getModule(getTag());
			if(mx == null) {
				dc = newInstance();
				((SyndEntry)root).getModules().add(dc);
			}
			else {
				dc = (T)mx;
			}
		}
		return dc;
	}
	@Override
	public boolean detect(String uri, String localName, String name,
			Attributes attributes) {
		final HashSet<String> sx = new HashSet<String>();
		ctx.getNamespaceURIs(sx);
		return sx.contains(getTag());
	}
}
