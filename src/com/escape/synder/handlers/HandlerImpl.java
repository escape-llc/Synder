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
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.escape.synder.Finalization;
import com.escape.synder.ParseContext;

/**
 * Base class of all Synder handler implementations.
 * @author escape-llc
 *
 */
public abstract class HandlerImpl {
	final String tag;
	protected final ParseContext ctx;
	protected List<HandlerImpl> fns;
	protected List<HandlerImpl> ins;
	protected final Stack<SubHandler<?>> current;
	/**
	 * Ctor.
	 * @param tag properties tag.
	 * @param ctx context.
	 */
	protected HandlerImpl(String tag, ParseContext ctx) {
		if(tag == null || tag.length() == 0) throw new IllegalArgumentException("tag");
		if(ctx == null) throw new IllegalArgumentException("ctx");
		this.tag = tag;
		this.ctx = ctx;
		current = new Stack<SubHandler<?>>();
	}
	/**
	 * Return the registration tag.
	 * @return Cannot be NULL.
	 */
	public String getTag() { return tag; }
	/**
	 * Return the top-of-stack root.
	 * @param <T> Cast.
	 * @return Instance.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getInstance() {
		final SubHandler<?> hx = current.peek();
		if(hx instanceof Finalization) {
			((Finalization)hx).end();
		}
		final Object ox = hx.getRoot();
		return (T)ox;
	}
	/**
	 * Set the namespace handlers.
	 * @param fns Feed-level namespaces.
	 * @param ins Item-level namespaces.
	 */
	public void setNamespace(List<HandlerImpl> fns, List<HandlerImpl> ins) {
		this.fns = fns;
		this.ins = ins;
	}
	/**
	 * Called on the Document Element startElement().
	 * Determine whether the handler can process this document.
	 * @param uri from DefaultHandler.startElement().
	 * @param localName from DefaultHandler.startElement().
	 * @param name from DefaultHandler.startElement().
	 * @param attributes from DefaultHandler.startElement().
	 * @return true: can process; false: cannot process.
	 */
	public abstract boolean detect(String uri, String localName, String name,
			Attributes attributes);
	/**
	 * Called when this handler is selected after detect(), and before any
	 * other ContentHandler callbacks.
	 */
	public void initialize(String uri, String localName, String name,
			Attributes attributes) { current.clear(); }
	/**
	 * Return whether a handler exists for given startElement().
	 * @param pc Source context.
	 * @param path Document element path.
	 * @param tos Current top-of-stack.
	 * @param uri from ContentHandler.startElement().
	 * @param localName from ContentHandler.startElement().
	 * @param name from ContentHandler.startElement().
	 * @param attributes from ContentHandler.startElement().
	 * @return !NULL: handler to stack; NULL: no handler.
	 */
	public abstract SubHandler<?> query(ParseContext pc, String[] path, SubHandler<?> tos, String uri, String localName, String name,
			Attributes attributes);
	/**
	 * Called on ContentHandler.startElement().
	 * @param path Element path to this node; document element at [0].
	 * @param uri from DefaultHandler.startElement().
	 * @param localName from DefaultHandler.startElement().
	 * @param name from DefaultHandler.startElement().
	 * @param attributes from DefaultHandler.startElement().
	 */
	public void startElement(String[] path, String uri, String localName, String name, Attributes attributes) throws SAXException {
		final SubHandler<?> tos = current.isEmpty() ? null : current.peek();
		SubHandler<?> ntos = query(ctx, path, tos, uri, localName, name, attributes);
		if(ntos == null && tos != null) {
			ntos = tos.query(ctx, path, ntos, uri, localName, name, attributes);
		}
		current.push(ntos);
		final SubHandler<?> sh = current.peek();
		if(sh != null) sh.startElement(uri, localName, name, attributes);
	}
	/**
	 * Called on ContentHandler.endElement().
	 * @param path Element path to this node; document element at [0].
	 * @param uri from DefaultHandler.endElement().
	 * @param localName from DefaultHandler.endElement().
	 * @param name from DefaultHandler.endElement().
	 */
	public void endElement(String[] path, String uri, String localName, String name) throws SAXException {
		if(current.isEmpty()) return;
		final SubHandler<?> sh = current.peek();
		if(sh != null) sh.endElement(uri, localName, name);
		if(current.size() > 1)
			current.pop();
	}
	/**
	 * Called on ContentHandler.characters().
	 * @param path Element path to this node; document element at [0].
	 * @param ch from DefaultHandler.characters().
	 * @param start from DefaultHandler.characters().
	 * @param length from DefaultHandler.characters().
	 */
	public void characters(String[] path, char[] ch, int start, int length) throws SAXException {
		if(current.isEmpty()) return;
		final SubHandler<?> sh = current.peek();
		if(sh != null) sh.characters(ch, start, length);
	}
}
