/*
 * Copyright 2014 eScape Technology LLC.
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

import java.util.HashSet;
import java.util.Set;

import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.feed.synd.impl.SyndEntryImpl;

/**
 * Extension of SyndEntryImpl to implement SyndEntry2.
 * @author escape-llc
 *
 */
public class SyndEntryImpl2 extends SyndEntryImpl implements SyndEntry2 {
	private static final long serialVersionUID = 1L;
	private SyndImage image;
	private ObjectBean _objBean;
	private final Class<?> beanClass;
	private final Set<String> convenienceProperties;
	private static final Set<String> IGNORE_PROPERTIES;
	static {
		IGNORE_PROPERTIES = new HashSet<String>();
	}
	protected SyndEntryImpl2(Class<?> beanClass, Set<String> convenienceProperties) {
		super(beanClass, convenienceProperties);
		this.beanClass = beanClass;
		this.convenienceProperties = convenienceProperties;
	}

	public SyndEntryImpl2() {
		this(SyndEntry2.class, IGNORE_PROPERTIES);
	}

	private ObjectBean getObjBean() {
		if (_objBean == null)
			_objBean = new ObjectBean(beanClass, this, convenienceProperties);
		return _objBean;
	}
	/**
	 * Creates a deep 'bean' clone of the object.
	 * <p>
	 * @return a clone of the object.
	 * @throws CloneNotSupportedException
	 *           thrown if an element of the object cannot be cloned.
	 */
	public Object clone() throws CloneNotSupportedException {
		return getObjBean().clone();
	}
	/**
	 * Indicates whether some other object is "equal to" this one as defined by
	 * the Object equals() method.
	 * <p>
	 * @param other
	 *          he reference object with which to compare.
	 * @return <b>true</b> if 'this' object is equal to the 'other' object.
	 */
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!(other instanceof SyndEntryImpl)) {
			return false;
		}
		boolean ret = getObjBean().equals(other);
		return ret;
	}
	/**
	 * Returns a hashcode value for the object.
	 * <p>
	 * It follows the contract defined by the Object hashCode() method.
	 * <p>
	 * @return the hashcode of the bean object.
	 */
	public int hashCode() {
		return getObjBean().hashCode();
	}
	/**
	 * Returns the String representation for the object.
	 * <p>
	 * @return String representation for the object.
	 */
	public String toString() {
		return getObjBean().toString();
	}
	public SyndImage getImage() { return image; }
	public void setImage(SyndImage image) {	this.image = image;	}
}
