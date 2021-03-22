/*
 * Copyright 2004 Sun Microsystems, Inc.
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
package com.sun.syndication.feed.module.impl;

import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.impl.CopyFromHelper;
import com.sun.syndication.feed.module.DCSubject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/**
 * Subject of the Dublin Core ModuleImpl, default implementation.
 * @see <a href="http://web.resource.org/rss/1.0/modules/dc/">Dublin Core module</a>
 * @author Alejandro Abdelnur
 * @author escape-llc
 *
 */
public class DCSubjectImpl implements Serializable, DCSubject {
	private static final long serialVersionUID = 1L;
	private ObjectBean _objBean;
    private String _taxonomyUri;
    private String _value;

    /**
     * Default constructor. All properties are set to <b>null</b>.
     */
    public DCSubjectImpl() {
    }
    private ObjectBean getObjBean() {
    	if(_objBean == null)
            _objBean = new ObjectBean(this.getClass(),this);
    	return _objBean;
    }

    /**
     * Creates a deep 'bean' clone of the object.
     * @return a clone of the object.
     * @throws CloneNotSupportedException thrown if an element of the object cannot be cloned.
     *
     */
    public Object clone() throws CloneNotSupportedException {
        return getObjBean().clone();
    }

    /**
     * Indicates whether some other object is "equal to" this one as defined by the Object equals() method.
     * @param other he reference object with which to compare.
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
     * Returns the DublinCore subject taxonomy URI.
     * @return the DublinCore subject taxonomy URI, <b>null</b> if none.
     *
     */
    public String getTaxonomyUri() {
        return _taxonomyUri;
    }

    /**
     * Sets the DublinCore subject taxonomy URI.
     * @param taxonomyUri the DublinCore subject taxonomy URI to set, <b>null</b> if none.
     *
     */
    public void setTaxonomyUri(String taxonomyUri) {
        _taxonomyUri = taxonomyUri;
    }

    /**
     * Returns the DublinCore subject value.
     * @return the DublinCore subject value, <b>null</b> if none.
     *
     */
    public String getValue() {
        return _value;
    }

    /**
     * Sets the DublinCore subject value.
     * @param value the DublinCore subject value to set, <b>null</b> if none.
     *
     */
    public void setValue(String value) {
        _value = value;
    }

    public Class<?> getInterface() {
        return DCSubject.class;
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
					basePropInterfaceMap.put("taxonomyUri", String.class);
					basePropInterfaceMap.put("value", String.class);

					final Map<Class<?>, Class<?>> basePropClassImplMap = Collections.emptyMap();

					COPY_FROM_HELPER = new CopyFromHelper(DCSubject.class, basePropInterfaceMap, basePropClassImplMap);
				}
			}
		}
		return COPY_FROM_HELPER;
    }

}
