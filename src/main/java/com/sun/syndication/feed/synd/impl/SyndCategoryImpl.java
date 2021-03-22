/*
 * Copyright 2004 Sun Microsystems, Inc.
 * Copyright 2010-12 eScape Technology LLC
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
 *
 */
package com.sun.syndication.feed.synd.impl;

import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.synd.SyndCategory;

import java.io.Serializable;

/**
 * Bean for categories of SyndFeedImpl feeds and entries.
 * @author Alejandro Abdelnur
 * @author escape-llc
 * 
 */
public class SyndCategoryImpl implements Serializable, SyndCategory {
	private static final long serialVersionUID = 1L;
	private ObjectBean _objBean;
	private String _name;
  private String _taxonomyUri;
  private String _label;

	private ObjectBean getObjBean() {
		if(_objBean == null) _objBean = new ObjectBean(SyndCategory.class, this);
		return _objBean;
	}

	/**
	 * Creates a deep 'bean' clone of the object.
	 * @return a clone of the object.
	 * @throws CloneNotSupportedException
	 *           thrown if an element of the object cannot be cloned.
	 * 
	 */
	public Object clone() throws CloneNotSupportedException {
		return getObjBean().clone();
	}

	/**
	 * Indicates whether some other object is "equal to" this one as defined by
	 * the Object equals() method.
	 * @param other
	 *          he reference object with which to compare.
	 * @return <b>true</b> if 'this' object is equal to the 'other' object.
	 * 
	 */
	public boolean equals(Object other) {
		return getObjBean().equals(other);
	}

	/**
	 * Returns a hashcode value for the object.
	 * It follows the contract defined by the Object hashCode() method.
	 * @return the hashcode of the bean object.
	 * 
	 */
	public int hashCode() {
		return getObjBean().hashCode();
	}

	/**
	 * Returns the String representation for the object.
	 * @return String representation for the object.
	 * 
	 */
	public String toString() {
		return getObjBean().toString();
	}

	/**
	 * Default constructor. All properties are set to <b>null</b>.
	 */
	public SyndCategoryImpl() {
	}

	/**
	 * Returns the category name.
	 * @return the category name, <b>null</b> if none.
	 * 
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Sets the category name.
	 * @param name
	 *          the category name to set, <b>null</b> if none.
	 * 
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * Returns the category taxonomy URI.
	 * @return the category taxonomy URI, <b>null</b> if none.
	 * 
	 */
	public String getTaxonomyUri() {
		return _taxonomyUri;
	}

	/**
	 * Sets the category taxonomy URI.
	 * @param taxonomyUri
	 *          the category taxonomy URI to set, <b>null</b> if none.
	 * 
	 */
	public void setTaxonomyUri(String taxonomyUri) {
		_taxonomyUri = taxonomyUri;
	}
	public String getLabel() { return _label; }
	public void setLabel(String label) { _label = label; }
}
