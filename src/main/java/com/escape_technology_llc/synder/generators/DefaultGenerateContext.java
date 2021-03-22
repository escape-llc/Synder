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
package com.escape_technology_llc.synder.generators;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.escape_technology_llc.synder.Context;
import com.escape_technology_llc.synder.DefaultContext;
import com.sun.syndication.feed.module.Module;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * Default implementation of GenerateContext.
 * @author escape-llc
 *
 */
public class DefaultGenerateContext implements GenerateContext {
	/**
	 * Wrapper for the generation mechanism (e.g. javax.xml.Transformer, org.xmlpull.v1.XmlSerializer).
	 * Requires an entire heirarchy of generators that recognize the
	 * mechanism.
	 * @author escape-llc
	 *
	 */
	public static abstract class Generate {
		/**
		 * Generate output.
		 * @param sf Source feed.
		 * @param sgi Source generator.
		 * @param os Output stream.
		 * @param enc Stream encoding.
		 * @throws Exception on errors.
		 */
		public abstract void generate(SyndFeed sf, GeneratorImpl sgi, OutputStream os, String enc) throws Exception;
	}
	final Context parent;
	final Map<String,Object> options;
	final ArrayList<Class<?>> handlers;
	final Generate gen;
	final DateFormat dfw3c;
	final DateFormat dfrfc822;
	/**
	 * Create context with NULL options.
	 * @param ctx Source of services.
	 * @param gx Generator to bind.
	 * @throws ClassNotFoundException Generator class not found
	 */
	public DefaultGenerateContext(Context ctx, Generate gx) throws ClassNotFoundException {
		this(ctx, gx, null);
	}
	/**
	 * Create context with given options.
	 * Takes ownership of map.
	 * @param parent Parent context.
	 * @param gx Generator to bind.
	 * @param options Options map.
	 * @throws ClassNotFoundException Generator class not found
	 */
	public DefaultGenerateContext(Context parent, Generate gx, Map<String,Object> options) throws ClassNotFoundException {
		if(parent == null) throw new IllegalArgumentException("parent");
		if(gx == null) throw new IllegalArgumentException("gx");
		this.parent = parent;
		this.options = options;
		this.gen = gx;
		handlers = new ArrayList<Class<?>>();
		final TimeZone gmt = TimeZone.getTimeZone("GMT");
		dfw3c = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		dfw3c.setTimeZone(gmt);
		dfrfc822 = new SimpleDateFormat("EEE, dd MMM yy HH:mm:ss z");
		dfrfc822.setTimeZone(gmt);
		DefaultContext.loadClasses(this, "Generator.classes", handlers);
	}
	/**
	 * Use bound generator to make an output stream.
	 */
	public void generate(SyndFeed sf, String tag, OutputStream os, String enc) throws Exception {
		if(sf == null) throw new IllegalArgumentException("sf");
		if(os == null) throw new IllegalArgumentException("os");
		if(tag == null || tag.length() == 0) throw new IllegalArgumentException("tag");
		if(enc == null || enc.length() == 0) throw new IllegalArgumentException("enc");
		final GeneratorImpl sgi = detect(sf, tag);
		if(sgi == null) throw new IllegalArgumentException("cannot generate document type");
		gen.generate(sf, sgi, os, enc);
	}
	/**
	 * Copy list of all handlers to given.
	 */
	public void getHandlers(List<Class<?>> lx) {
		if(lx == null) throw new IllegalArgumentException("lx");
		parent.getHandlers(lx);
		lx.addAll(handlers);
	}
	public void getTokenizedProperty(String prop, String delim, List<String> lx) {
		parent.getTokenizedProperty(prop, delim, lx);
	}
	public GeneratorImpl detect(SyndFeed sf, String tag) {
		for(final Class<?> cx: handlers) {
			final GeneratorImpl sgi = DefaultContext.createWith(cx, GenerateContext.class, this);
			if(sgi == null) continue;
			final boolean dx = sgi.detect(tag);
			if(dx) {
				final ArrayList<ModuleGeneratorImpl> collect = new ArrayList<ModuleGeneratorImpl>(5);
				final ArrayList<ModuleGeneratorImpl> fns = new ArrayList<ModuleGeneratorImpl>(5);
				feedNamespaceGenerators(sgi.getTag(), collect);
				final List<Module> mods = sf.getModules();
				for(final Module mx: mods) {
					for(final ModuleGeneratorImpl sshi: collect) {
						if(sshi.detect(mx.getUri())) {
							fns.add(sshi);
							break;
						}
					}
				}
				final ArrayList<ModuleGeneratorImpl> ins = new ArrayList<ModuleGeneratorImpl>(5);
				collect.clear();
				itemNamespaceGenerators(sgi.getTag(), collect);
				for (final SyndEntry se : sf.getEntries()) {
					final List<Module> emods = se.getModules();
					for(final Module mx: emods) {
						for(final ModuleGeneratorImpl sshi: collect) {
							if(sshi.detect(mx.getUri()) && !ins.contains(sshi)) {
								ins.add(sshi);
								break;
							}
						}
					}
				}
				sgi.setNamespace(Collections.unmodifiableList(fns), Collections.unmodifiableList(ins));
				return sgi;
			}
		}
		return null;
	}
	protected static void namespaceHandlers(GenerateContext ctx, String tag, List<ModuleGeneratorImpl> lx) {
		try {
			final ArrayList<Class<?>> nss = new ArrayList<Class<?>>();
			DefaultContext.loadClasses(ctx, tag, nss);
			for(final Class<?> cx: nss) {
				final ModuleGeneratorImpl shi = DefaultContext.createWith(cx, GenerateContext.class, ctx);
				if(shi != null)
					lx.add(shi);
			}
		} catch (ClassNotFoundException e) {
		}
	}
	public void feedNamespaceGenerators(String tag, List<ModuleGeneratorImpl> lx) {
		if(tag == null || tag.length() == 0) throw new IllegalArgumentException("tag");
		if(lx == null) throw new IllegalArgumentException("lx");
		namespaceHandlers(this, tag + ".generate.feed.Namespace.classes", lx);
	}
	public void itemNamespaceGenerators(String tag, List<ModuleGeneratorImpl> lx) {
		if(tag == null || tag.length() == 0) throw new IllegalArgumentException("tag");
		if(lx == null) throw new IllegalArgumentException("lx");
		namespaceHandlers(this, tag + ".generate.item.Namespace.classes", lx);
	}
	@SuppressWarnings("unchecked")
	public <T> T getOption(String option) {
		return options == null ? null : (T)options.get(option);
	}
	public DateFormat getDateFormatRFC822() {
		return dfrfc822;
	}
	public DateFormat getDateFormatW3C() {
		return dfw3c;
	}
}
