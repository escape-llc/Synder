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

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.escape.synder.ParseContext;

/**
 * Base class for modular content handlers.
 * @author escape-llc
 *
 * @param <T> Hosting class.
 */
public class SubHandler<T> extends DefaultHandler {
	protected final ParseContext ctx;
	protected final T root;
	protected final List<HandlerImpl> nsx;
	/**
	 * Ctor.
	 * @param ctx  Source of all.
	 * @param root Hosting instance for this handler.
	 * @param nsx  Namespace handlers.
	 */
	protected SubHandler(ParseContext ctx, T root, List<HandlerImpl> nsx) {
		if(ctx == null) throw new IllegalArgumentException("ctx");
		if(root == null) throw new IllegalArgumentException("root");
		this.root = root;
		this.ctx = ctx;
		this.nsx = nsx;
	}
	protected SubHandler(ParseContext ctx, T root) {
		this(ctx, root, null);
	}
	/**
	 * Return the hosting instance.
	 * @return Cannot be NULL.
	 */
	public T getRoot() { return root; }
	/**
	 * Query for the handler to push on the stack for the given state.
	 * @param pc Parse context to use.
	 * @param path Path to this node.
	 * @param tos Current top-of-stack handler.
	 * @param uri from ContentHandler.startElement().
	 * @param localName from ContentHandler.startElement().
	 * @param name from ContentHandler.startElement().
	 * @param attributes from ContentHandler.startElement().
	 * @return !NULL: Handler to stack; NULL: no action.
	 */
	public SubHandler<?> query(ParseContext pc, String[] path, SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes) {
		if(nsx != null && nsx.size() > 0) {
			for(final HandlerImpl shi: nsx) {
				final SubHandler<?> sh = shi.query(pc, path, tos, uri, localName, name, attributes);
				if(sh != null) return sh;
			}
		}
		return null;
	}
}
