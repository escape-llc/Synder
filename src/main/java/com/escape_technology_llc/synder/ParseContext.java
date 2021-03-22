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
package com.escape_technology_llc.synder;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.escape_technology_llc.synder.handlers.HandlerImpl;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * Parse-specific context.
 * Reusable but only sharable by a single call to parse().
 * @author escape-llc
 *
 */
public interface ParseContext extends Context {
	/**
	 * Processing features.
	 * @author escape-llc
	 *
	 */
	public interface Features {
		/**
		 * Pass this for "value ignored" values.
		 * <p>
		 * e.g. <code>map.put(<i>option</i>, EMPTY)</code>.
		 */
		Object EMPTY = "";
		/**
		 * Capture foreign markup for SyndEntry.
		 * <p>
		 * Use EMPTY for the value, e.g. <code>map.put(<i>option</i>, EMPTY)</code>.
		 * <p>
		 * Key-present enables feature.
		 * Key-not-present disables feature.
		 */
		String FOREIGN_MARKUP_ENTRY = "com.escape.synder.SyndEntry.ForeignMarkup";
		/**
		 * Capture foreign markup for SyndFeed.
		 * <p>
		 * Use EMPTY for the value, e.g. <code>map.put(<i>option</i>, EMPTY)</code>.
		 * <p>
		 * Key-present enables feature.
		 * Key-not-present disables feature.
		 */
		String FOREIGN_MARKUP_FEED = "com.escape.synder.SyndFeed.ForeignMarkup";
	}
	/**
	 * Search the configuration for a handler to process the document.
	 * @param uri from DefaultHandler.startElement().
	 * @param localName from DefaultHandler.startElement().
	 * @param name from DefaultHandler.startElement().
	 * @param attributes from DefaultHandler.startElement().
	 * @return !NULL: handler; NULL: no handler.
	 */
	HandlerImpl detect(String uri, String localName, String name, Attributes attributes);
	/**
	 * Fill list with feed namespaces that match given tag.
	 * @param tag feed type tag, e.g. "atom_1.0".
	 * @param lx Target list.
	 */
	void feedNamespaceHandlers(String tag, List<HandlerImpl> lx);
	/**
	 * Fill list with item namespaces that match given tag.
	 * @param tag feed type tag, e.g. "atom_1.0".
	 * @param lx Target list.
	 */
	void itemNamespaceHandlers(String tag, List<HandlerImpl> lx);
	/**
	 * Get the DateParser instance.
	 * @return Cannot be NULL.
	 */
	DateParser getDateParser();
	/**
	 * Report error for examination after parse().
	 * @param ex Error to report.
	 */
	void report(SAXException ex);
	/**
	 * Get the list of errors, fatal errors, and warnings.
	 * @return Possibly empty list of errors.
	 */
	List<SAXException> getReport();
	/**
	 * Register namespace.
	 * @param pfx prefix.
	 * @param uri namespace URI.
	 */
	void addNamespace(String pfx, String uri);
	/**
	 * Unregister namespace.
	 * @param pfx prefix.
	 */
	void removeNamespace(String pfx);
	/**
	 * Fill the given set with namespace URIs in effect.
	 * @param sx Target set.
	 */
	void getNamespaceURIs(Set<String> sx);
	/**
	 * Get the complete map of prefix/uri mappings in effect.
	 * @param mx Target map.
	 */
	void getNamespaceMap(Map<String,String> mx);
	/**
	 * Return the requested option.
	 * @param <T> Desired cast.
	 * @param option Source option.
	 * @return !NULL: value; NULL: option not present.
	 */
	<T> T getOption(String option);
	/**
	 * Parse the given input.
	 * @param is Input source.
	 * @return new instance.
	 * @throws Exception  on errors.
	 */
	SyndFeed parse(InputSource is) throws Exception;
}
