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

import java.util.Map;

/**
 * Implementation of SyndForeignMarkup
 * @author escape-llc
 *
 */
public class SyndForeignMarkupImpl implements SyndForeignMarkup {
	String nsuri;
	Map<String,String> nss;
	String type;
	String value;
    /**
     * Creates a deep clone of the object.
     * <p>
     * @return a clone of the object.
     * @throws CloneNotSupportedException thrown if an element of the object cannot be cloned.
     *
     */
    public Object clone() throws CloneNotSupportedException {
    	throw new CloneNotSupportedException();
    }
	public String getNamespaceUri() { return nsuri; }
	public Map<String, String> getNamespaces() { return nss; }
	public String getType() { return type; }
	public String getValue() { return value; }
	public void setNamespaceUri(String sx) { nsuri = sx; }
	public void setNamespaces(Map<String, String> mx) { nss = mx; }
	public void setType(String sx) { type = sx; }
	public void setValue(String sx) { value = sx; }
}
