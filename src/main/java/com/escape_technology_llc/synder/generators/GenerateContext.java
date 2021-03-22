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
import java.util.List;

import com.escape_technology_llc.synder.Context;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * Context used for generation.
 * @author escape-llc
 *
 */
public interface GenerateContext extends Context {
	public static interface Features {
		/**
		 * Set the default namespace prefix.
		 * The default value is empty string "".
		 * Key-present uses given value.
		 * Key-not-present uses default "".
		 */
		String DEFAULT_NAMESPACE_PREFIX = "com.escape.synder.generators.DefaultNamespacePrefix";
		/**
		 * Set the default namespace prefix to NULL.
		 * The default value is empty string "".
		 * This option overrides DEFAULT_NAMESPACE_PREFIX if both are present.
		 * <p>
		 * Use EMPTY for the value, e.g. <code>map.put(<i>option</i>, EMPTY)</code>.
		 * <p>
		 * Key-present uses NULL.
		 * Key-not-present no-effect.
		 */
		String DEFAULT_NAMESPACE_PREFIX_NULL = "com.escape.synder.generators.DefaultNamespacePrefixNull";
	}
	/**
	 * Return a generator that can process the given tag.
	 * @param sf Source feed.
	 * @param tag Feed version tag, e.g. "rss_2.0".
	 * @return !NULL: generator; NULL: cannot generate.
	 */
	GeneratorImpl detect(SyndFeed sf, String tag);
	/**
	 * Fill list with feed namespaces that match given tag.
	 * @param tag feed type tag, e.g. "atom_1.0".
	 * @param lx Target list.
	 */
	void feedNamespaceGenerators(String tag, List<ModuleGeneratorImpl> lx);
	/**
	 * Fill list with item namespaces that match given tag.
	 * @param tag feed type tag, e.g. "atom_1.0".
	 * @param lx Target list.
	 */
	void itemNamespaceGenerators(String tag, List<ModuleGeneratorImpl> lx);
	/**
	 * Return the requested option.
	 * @param <T> Desired cast.
	 * @param option Source option.
	 * @return !NULL: value; NULL: option not present.
	 */
	<T> T getOption(String option);
	/**
	 * Return the date formatter for W3C format.
	 * @return Cannot be NULL.
	 */
	DateFormat getDateFormatW3C();
	/**
	 * Return the date formatter for RFC822 format.
	 * @return Cannot be NULL.
	 */
	DateFormat getDateFormatRFC822();
	/**
	 * Generate given feed to given output.
	 * @param sf Source feed.
	 * @param tag Generator tag.
	 * @param wx Output stream.
	 * @param enc Stream encoding.
	 * @throws Exception
	 */
	void generate(SyndFeed sf, String tag, OutputStream wx, String enc) throws Exception;
}
