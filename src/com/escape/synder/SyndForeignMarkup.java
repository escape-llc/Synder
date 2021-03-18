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

import java.util.Map;

/**
 * Represents foreign markup element.
 * @author escape-llc
 *
 */
public interface SyndForeignMarkup extends Cloneable {
	/**
	 * Get MIME Type, typically "text/xml".
	 * @return MIME Type or NULL.
	 */
	String getType();
	/**
	 * Set MIME Type.
	 * @param sx MIME Type.
	 */
	void setType(String sx);
	/**
	 * Get the Namespace URI of the root element.
	 * @return Namespace URI or NULL.
	 */
	String getNamespaceUri();
	/**
	 * Set the Namespace URI of the root element.
	 * @param sx Namespace URI.
	 */
	void setNamespaceUri(String sx);
	/**
	 * Get the foreign markup.
	 * @return Foreign markup as String.
	 */
	String getValue();
	/**
	 * Set the foreign markup.
	 * @param sx
	 */
	void setValue(String sx);
	/**
	 * Get the complete namespace-prefix/URI map in effect at
	 * this element.  May include getNamespaceUri() if not a local
	 * namespace declaration.
	 * @return !NULL: ns map; NULL: no map.
	 */
	Map<String,String> getNamespaces();
	/**
	 * Set the namespace-prefix/URI map.
	 * @param mx Map to populate with entries.
	 */
	void setNamespaces(Map<String,String> mx);
}
