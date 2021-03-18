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
package com.escape.synder;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.escape.synder.handlers.HandlerImpl;

/**
 * Top-level Synder SAX2 handler.
 * Delegates everything to a single handler, which is determined during the
 * first call to DefaultHandler.startElement().
 * @author escape-llc
 *
 */
public class SynderContentHandler extends DefaultHandler {
	final ParseContext ctx;
	final Stack<String> path;
	HandlerImpl handler;
	boolean start;
	/**
	 * Ctor.
	 * @param ctx source of resources.
	 */
	public SynderContentHandler(ParseContext ctx) {
		if(ctx == null) throw new IllegalArgumentException("ctx");
		this.ctx = ctx;
		path = new Stack<String>();
	}
	/**
	 * Return the instance top-of-stack.
	 * @return instance or NULL if initialization failed and <code>ctx.getReport().size() != 0</code>.
	 * @throws IllegalArgumentException if initialization failed and <code>ctx.getReport().size() == 0</code>.
	 */
	public Object getInstance() {
		if(handler == null) {
			if(ctx.getReport().size() == 0) {
				// no errors were reported
				throw new IllegalArgumentException("startDocument/startElement not called");
			}
			// return NULL so caller can examine errors.
			return null;
		}
		return handler.getInstance();
	}
	/**
	 * Return the instance top-of-stack.
	 * @param <T> Desired cast.
	 * @return instance or NULL if initialization failed and <code>ctx.getReport().size() != 0</code>.
	 * @throws IllegalArgumentException if initialization failed and <code>ctx.getReport().size() == 0</code>.
	 */
	public <T> T getTypedInstance() {
		if(handler == null) {
			if(ctx.getReport().size() == 0) {
				// no errors were reported
				throw new IllegalArgumentException("startDocument/startElement not called");
			}
			// return NULL so caller can examine errors.
			return null;
		}
		return handler.getInstance();
	}
	/**
	 * Standard tag formatting.
	 * @param uri Namespace URI.
	 * @param localName Element local name (no nsprefix).
	 * @return Formatted string "{nsuri}localName".
	 */
	public static String formatTag(String uri, String localName) {
		final int len = (uri != null ? 2 + uri.length() : 0) +
				(localName != null ? localName.length() : 0);
		final StringBuilder sb = new StringBuilder(len);
		if (uri != null && uri.length() > 0) {
			sb.append("{");
			sb.append(uri);
			sb.append("}");
		}
		sb.append(localName);
		return sb.toString();
	}
	/**
	 * Re-initialize structures for parsing.
	 */
	@Override
	public void startDocument() throws SAXException {
		path.clear();
		start = true;
	}
	@Override
	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		//System.out.println("NS.start: " + arg0 + "=" + arg1);
		ctx.addNamespace(arg0, arg1);
	}
	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
		//System.out.println("NS.end: " + arg0);
		ctx.removeNamespace(arg0);
	}
	@Override
	public void error(SAXParseException e) throws SAXException {
		//System.out.println("Error: " + e.getMessage());
		ctx.report(e);
	}
	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		//System.out.println("Fatal: " + e.getMessage());
		ctx.report(e);
	}
	@Override
	public void warning(SAXParseException e) throws SAXException {
		//System.out.println("Warn: " + e.getMessage());
		ctx.report(e);
	}
	/**
	 * Pass through to handler top-of-stack.
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		//System.out.println("Chars: " + path.toString());
		handler.characters(path.toArray(new String[0]), ch, start, length);
	}
	/**
	 * Manage the handler/instance stacks via the top-level handler.
	 */
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		path.push(formatTag(uri, localName));
		//System.out.println("Start: " + path.toString());
		if(start) {
			// first-time-through: auto-sense document type
			try {
				handler = ctx.detect(uri, localName, name, attributes);
				if (handler == null)
					throw new IllegalArgumentException("Cannot detect document type");
				handler.initialize(uri, localName, name, attributes);
			} finally {
				start = false;
			}
		}
		handler.startElement(path.toArray(new String[0]), uri, localName, name, attributes);
	}
	/**
	 * Manage the handler/instance stacks via the top-level handler.
	 */
	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		//System.out.println("End: " + path.toString());
		handler.endElement(path.toArray(new String[0]), uri, localName, name);
		path.pop();
	}
}
