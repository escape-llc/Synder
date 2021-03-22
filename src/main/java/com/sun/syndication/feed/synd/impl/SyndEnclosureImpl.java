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
import com.sun.syndication.feed.synd.SyndEnclosure;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * @author Alejandro Abdelnur
 * @author escape-llc
 */
public class SyndEnclosureImpl implements Serializable,SyndEnclosure {
	private static final long serialVersionUID = 1L;
	private ObjectBean _objBean;
    private String _url;
    private String _type;
    private long   _length;

    /**
     * Default constructor. All properties are set to <b>null</b>.
     * <p>
     *
     */
    public SyndEnclosureImpl() {
    }
	private ObjectBean getObjBean() {
		if (_objBean == null)
	        _objBean = new ObjectBean(SyndEnclosure.class,this);
		return _objBean;
	}

    /**
     * Creates a deep 'bean' clone of the object.
     * <p>
     * @return a clone of the object.
     * @throws CloneNotSupportedException thrown if an element of the object cannot be cloned.
     *
     */
    public Object clone() throws CloneNotSupportedException {
        return getObjBean().clone();
    }

    /**
     * Indicates whether some other object is "equal to" this one as defined by the Object equals() method.
     * <p>
     * @param other he reference object with which to compare.
     * @return <b>true</b> if 'this' object is equal to the 'other' object.
     *
     */
    public boolean equals(Object other) {
        return getObjBean().equals(other);
    }

    /**
     * Returns a hashcode value for the object.
     * <p>
     * It follows the contract defined by the Object hashCode() method.
     * <p>
     * @return the hashcode of the bean object.
     *
     */
    public int hashCode() {
        return getObjBean().hashCode();
    }

    /**
     * Returns the String representation for the object.
     * <p>
     * @return String representation for the object.
     *
     */
    public String toString() {
        return getObjBean().toString();
    }

    /**
     * Returns the enclosure URL.
     * <p/>
     *
     * @return the enclosure URL, <b>null</b> if none.
     */
    public String getUrl() {
        return _url;
    }

    /**
     * Sets the enclosure URL.
     * <p/>
     *
     * @param url the enclosure URL to set, <b>null</b> if none.
     */
    public void setUrl(String url) {
        _url = url;
    }

    /**
     * Returns the enclosure length.
     * <p/>
     *
     * @return the enclosure length, <b>null</b> if none.
     */
    public long getLength() {
        return _length;
    }

    /**
     * Sets the enclosure length.
     * <p/>
     *
     * @param length the enclosure length to set, <b>null</b> if none.
     */
    public void setLength(long length) {
        _length = length;
    }

    /**
     * Returns the enclosure type.
     * <p/>
     *
     * @return the enclosure type, <b>null</b> if none.
     */
    public String getType() {
        return _type;
    }

    /**
     * Sets the enclosure type.
     * <p/>
     *
     * @param type the enclosure type to set, <b>null</b> if none.
     */
    public void setType(String type) {
        _type = type;
    }

    public Class<?> getInterface() {
        return SyndEnclosure.class;
    }

    public void copyFrom(Object obj) {
        createCopyFrom().copy(this,obj);
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
					basePropInterfaceMap.put("url", String.class);
					basePropInterfaceMap.put("type", String.class);
					basePropInterfaceMap.put("length", Long.TYPE);

					final Map<Class<?>, Class<?>> basePropClassImplMap = Collections.emptyMap();

					COPY_FROM_HELPER = new CopyFromHelper(SyndEnclosure.class, basePropInterfaceMap, basePropClassImplMap);
				}
			}
		}
		return COPY_FROM_HELPER;
    }

}
