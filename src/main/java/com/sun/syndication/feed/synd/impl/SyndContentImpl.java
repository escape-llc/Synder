/*
 * Copyright 2004 Sun Microsystems, Inc.
 * Copyright 2010 eScape Technology LLC
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
import com.sun.syndication.feed.impl.CopyFromHelper;
import com.sun.syndication.feed.synd.SyndContent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/**
 * Bean for content of SyndFeedImpl entries.
 * 
 * @author Alejandro Abdelnur
 * @author escape-llc
 * 
 */
public class SyndContentImpl implements Serializable, SyndContent {
	private static final long serialVersionUID = 1L;
	private ObjectBean _objBean;
	private String _type;
	private String _value;
	private String _mode;

	/**
	 * Default constructor. All properties are set to <b>null</b>.
	 * 
	 */
	public SyndContentImpl() {
	}

	private ObjectBean getObjBean() {
		if (_objBean == null)
			_objBean = new ObjectBean(SyndContent.class, this);
		return _objBean;
	}

	/**
	 * Creates a deep 'bean' clone of the object.
	 * @return a clone of the object.
	 * @throws CloneNotSupportedException
	 *             thrown if an element of the object cannot be cloned.
	 * 
	 */
	public Object clone() throws CloneNotSupportedException {
		return getObjBean().clone();
	}

	/**
	 * Indicates whether some other object is "equal to" this one as defined by
	 * the Object equals() method.
	 * @param other
	 *            he reference object with which to compare.
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
	 * Returns the content type.
	 * When used for the description of an entry, if <b>null</b> 'text/plain'
	 * must be assumed.
	 * @return the content type, <b>null</b> if none.
	 * 
	 */
	public String getType() {
		return _type;
	}

	/**
	 * Sets the content type.
	 * When used for the description of an entry, if <b>null</b> 'text/plain'
	 * must be assumed.
	 * @param type
	 *            the content type to set, <b>null</b> if none.
	 * 
	 */
	public void setType(String type) {
		_type = type;
	}

	/**
	 * Returns the content mode.
	 * 
	 * @return the content mode, <b>null</b> if none.
	 * 
	 */
	public String getMode() {
		return _mode;
	}

	/**
	 * Sets the content mode.
	 * 
	 * @param mode
	 *            the content mode to set, <b>null</b> if none.
	 * 
	 */
	public void setMode(String mode) {
		_mode = mode;
	}

	/**
	 * Returns the content value.
	 * @return the content value, <b>null</b> if none.
	 * 
	 */
	public String getValue() {
		return _value;
	}

	/**
	 * Sets the content value.
	 * @param value
	 *            the content value to set, <b>null</b> if none.
	 * 
	 */
	public void setValue(String value) {
		_value = value;
	}

	public Class<?> getInterface() {
		return SyndContent.class;
	}

	public void copyFrom(Object obj) {
		createCopyFrom().copy(this, obj);
	}

	private static volatile CopyFromHelper COPY_FROM_HELPER;
	private static final Object cflock;

	static {
		cflock = new Object();
	}

	static CopyFromHelper createCopyFrom() {
		if (COPY_FROM_HELPER == null) {
			synchronized (cflock) {
				if (COPY_FROM_HELPER == null) {
					final Map<String, Class<?>> basePropInterfaceMap = new HashMap<String, Class<?>>();
					basePropInterfaceMap.put("type", String.class);
					basePropInterfaceMap.put("value", String.class);
					basePropInterfaceMap.put("mode", String.class);

					final Map<Class<?>, Class<?>> basePropClassImplMap = Collections
							.emptyMap();

					COPY_FROM_HELPER = new CopyFromHelper(SyndContent.class,
							basePropInterfaceMap, basePropClassImplMap);
				}
			}
		}
		return COPY_FROM_HELPER;
	}

}
