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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.sax2.Driver;

import com.escape.synder.handlers.HandlerImpl;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * Default implementation of ParseContext.
 * @author escape-llc
 *
 */
public class DefaultParseContext implements ParseContext {
	/**
	 * Wraps the construction, initialization, and invocation of the
	 * XMLReader/SAXParser implementation.
	 * Create a subclass if the pre-defined implementations are not suitable.
	 * @author escape-llc
	 *
	 */
	public static abstract class Parse {
		/**
		 * Parse the given input with the given handler.
		 * @param is input to parse.
		 * @param ch handler to parse with.
		 * @throws Exception
		 */
		public abstract void parse(InputSource is, DefaultHandler ch) throws Exception;
		/**
		 * Common final setup and invocation of the parser.
		 * Turns on feature "namespaces".
		 * @param xr Parser.
		 * @param is Input.
		 * @param ch Handler.
		 * @throws SAXException
		 * @throws IOException
		 */
		protected void parseCommon(XMLReader xr, InputSource is, DefaultHandler ch) throws SAXException, IOException {
			xr.setFeature("http://xml.org/sax/features/namespaces", true);
			xr.setContentHandler(ch);
			xr.setErrorHandler(ch);
			xr.parse(is);
		}
	}
	/**
	 * Use XMLReaderFactory.createXMLReader().
	 * @author escape-llc
	 *
	 */
	public static class ParseXMLReader extends Parse {
		@Override
		public void parse(InputSource is, DefaultHandler ch) throws Exception {
			final XMLReader xr = XMLReaderFactory.createXMLReader();
			parseCommon(xr, is, ch);
		}
	}
	/**
	 * Use XMLReaderFactory.createXMLReader(String).
	 * @author escape-llc
	 *
	 */
	public static class ParseXMLReader2 extends Parse {
		final String cx;
		/**
		 * Ctor.
		 * @param cx Class name of XMLReader implementation.
		 */
		public ParseXMLReader2(String cx) {
			this.cx = cx;
		}
		@Override
		public void parse(InputSource is, DefaultHandler ch) throws Exception {
			final XMLReader xr = XMLReaderFactory.createXMLReader(cx);
			parseCommon(xr, is, ch);
		}
	}
	/**
	 * Use XmlPullParserFactory.newInstance().newPullParser() wrapped in
	 * an instance of org.xmlpull.v1.sax2.Driver().
	 * @author escape-llc
	 *
	 */
	public static class ParseXMLPullParser extends Parse {
		final XmlPullParserFactory xppf;
		public ParseXMLPullParser() {
			try {
				xppf = XmlPullParserFactory.newInstance();
				xppf.setNamespaceAware(true);
			} catch (XmlPullParserException e) {
				throw new RuntimeException(e);
			}
		}
		@Override
		public void parse(InputSource is, DefaultHandler ch) throws Exception {
			final XmlPullParser xpp = xppf.newPullParser();
			final XMLReader xr = new Driver(xpp);
			parseCommon(xr, is, ch);
		}
	}
	/**
	 * Use SAXParserFactory.newSAXParser().getXMLReader().
	 * @author escape-llc
	 *
	 */
	public static class ParseSAXParser extends Parse {
		final SAXParserFactory pf;
		public ParseSAXParser() {
			try {
				pf = SAXParserFactory.newInstance();
				pf.setFeature("http://xml.org/sax/features/namespaces", true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		@Override
		public void parse(InputSource is, DefaultHandler ch) throws Exception {
			try {
				final SAXParser sp = pf.newSAXParser();
				parseCommon(sp.getXMLReader(), is, ch);
			} catch (ParserConfigurationException e) {
				throw new SAXException(e);
			}
		}
	}
	final Context parent;
	final DateParser dp;
	final ArrayList<SAXException> rpx;
	final Parse px;
	final HashMap<String,String> nsmap;
	final Map<String,Object> options;
	final ArrayList<Class<?>> handlers;
	/**
	 * Ctor.
	 * Uses ParseSAXParser.
	 * @param parent
	 * @throws ClassNotFoundException 
	 */
	public DefaultParseContext(Context parent) throws ClassNotFoundException {
		this(parent, new ParseSAXParser(), null);
	}
	/**
	 * Ctor.
	 * Uses the given parser.
	 * @param parent
	 * @param px Parser implementation.
	 * @throws ClassNotFoundException 
	 */
	public DefaultParseContext(Context parent, Parse px) throws ClassNotFoundException {
		this(parent, px, null);
	}
	/**
	 * Ctor.
	 * Uses the given options.
	 * @param parent
	 * @param options Options map.
	 * @throws ClassNotFoundException 
	 */
	public DefaultParseContext(Context parent, Map<String,Object> options) throws ClassNotFoundException {
		this(parent, new ParseSAXParser(), options);
	}
	/**
	 * Ctor.
	 * Uses the given parser and options.
	 * @param parent
	 * @param px Parser implementation.
	 * @param options Options map.
	 * @throws ClassNotFoundException 
	 */
	public DefaultParseContext(Context parent, Parse px, Map<String,Object> options) throws ClassNotFoundException {
		if(parent == null) throw new IllegalArgumentException("parent");
		if(px == null) throw new IllegalArgumentException("px");
		this.parent = parent;
		this.px = px;
		this.options = options;
		dp = new DateParser(this.parent);
		rpx = new ArrayList<SAXException>();
		nsmap = new HashMap<String,String>();
		handlers = new ArrayList<Class<?>>();
		DefaultContext.loadClasses(this, "Parser.classes", handlers);
	}
	/**
	 * Run the detection sequence.
	 * If successful, the returned handler is namespace-configured.
	 * @param ctx Source context.
	 * @param handlers List of handler classes.
	 * @param uri from ContentHandler.startElement().
	 * @param localName from ContentHandler.startElement().
	 * @param name from ContentHandler.startElement().
	 * @param attributes from ContentHandler.startElement().
	 * @return !NULL: handler; NULL: no handler.
	 */
	protected static HandlerImpl detect(ParseContext ctx,
			List<Class<?>> handlers, String uri, String localName, String name,
			Attributes attributes) {
		for(final Class<?> cx: handlers) {
			final HandlerImpl shi = DefaultContext.createWith(cx, ParseContext.class, ctx);
			if(shi == null) continue;
			final boolean dx = shi.detect(uri, localName, name, attributes);
			if(dx) {
				final ArrayList<HandlerImpl> fns = new ArrayList<HandlerImpl>(4);
				ctx.feedNamespaceHandlers(shi.getTag(), fns);
				final Iterator<HandlerImpl> itx = fns.iterator();
				while(itx.hasNext()) {
					final HandlerImpl sshi = itx.next();
					if(!sshi.detect(uri, localName, name, attributes)) {
						itx.remove();
					}
				}
				final ArrayList<HandlerImpl> ins = new ArrayList<HandlerImpl>(4);
				ctx.itemNamespaceHandlers(shi.getTag(), ins);
				final Iterator<HandlerImpl> itx2 = ins.iterator();
				while(itx2.hasNext()) {
					final HandlerImpl sshi = itx2.next();
					if(!sshi.detect(uri, localName, name, attributes)) {
						itx2.remove();
					}
				}
				shi.setNamespace(Collections.unmodifiableList(fns), Collections.unmodifiableList(ins));
				return shi;
			}
		}
		return null;
	}
	public DateParser getDateParser() {
		return dp;
	}
	public void report(SAXException ex) {
		rpx.add(ex);
	}
	public HandlerImpl detect(String uri, String localName, String name,
			Attributes attributes) {
		final ArrayList<Class<?>> hx = new ArrayList<Class<?>>();
		getHandlers(hx);
		if(hx.size() == 0) return null;
		return detect(this, hx, uri, localName, name, attributes);
	}
	public void getTokenizedProperty(String prop, String delim, List<String> lx) {
		parent.getTokenizedProperty(prop, delim, lx);
	}
	public List<SAXException> getReport() {
		return Collections.unmodifiableList(rpx);
	}
	/**
	 * Load namespace handler classes and instantiate them into given list.
	 * @param ctx Source context.
	 * @param tag Class list property.
	 * @param lx Result list.
	 */
	protected static void namespaceHandlers(ParseContext ctx, String tag, List<HandlerImpl> lx) {
		try {
			final ArrayList<Class<?>> nss = new ArrayList<Class<?>>();
			DefaultContext.loadClasses(ctx, tag, nss);
			for(final Class<?> cx: nss) {
				final HandlerImpl shi = DefaultContext.createWith(cx, ParseContext.class, ctx);
				if(shi != null)
					lx.add(shi);
			}
		} catch (ClassNotFoundException e) {
		}
	}
	public void feedNamespaceHandlers(String tag, List<HandlerImpl> lx) {
		if(tag == null || tag.length() == 0) throw new IllegalArgumentException("tag");
		if(lx == null) throw new IllegalArgumentException("lx");
		namespaceHandlers(this, tag + ".feed.Namespace.classes", lx);
	}
	public void itemNamespaceHandlers(String tag, List<HandlerImpl> lx) {
		if(tag == null || tag.length() == 0) throw new IllegalArgumentException("tag");
		if(lx == null) throw new IllegalArgumentException("lx");
		namespaceHandlers(this, tag + ".item.Namespace.classes", lx);
	}
	public void getHandlers(List<Class<?>> lx) {
		if(lx == null) throw new IllegalArgumentException("lx");
		parent.getHandlers(lx);
		lx.addAll(handlers);
	}
	public SyndFeed parse(InputSource is) throws Exception {
		if(is == null) throw new IllegalArgumentException("is");
		final SynderContentHandler gs = new SynderContentHandler(this);
		px.parse(is, gs);
		return gs.getTypedInstance();
	}
	public void addNamespace(String pfx, String uri) {
		if(uri == null || uri.length() == 0) throw new IllegalArgumentException("uri");
		nsmap.put(pfx, uri);
	}
	public void removeNamespace(String pfx) {
		nsmap.remove(pfx);
	}
	public void getNamespaceURIs(Set<String> sx) {
		if(sx == null) throw new IllegalArgumentException("sx");
		sx.addAll(nsmap.values());
	}
	public void getNamespaceMap(Map<String, String> mx) {
		if(mx == null) throw new IllegalArgumentException("mx");
		mx.putAll(nsmap);
	}
	@SuppressWarnings("unchecked")
	public <T> T getOption(String option) {
		return options == null ? null : (T)options.get(option);
	}
}
