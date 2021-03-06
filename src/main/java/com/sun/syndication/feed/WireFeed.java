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
package com.sun.syndication.feed;

import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.module.Module;
import com.sun.syndication.feed.module.impl.ModuleUtils;
import com.sun.syndication.feed.module.Extendable;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * <p>Parent class of the RSS (Channel) and Atom (Feed) feed beans.
 * </p>
 * <p><b>Not Implemented.</b>
 * </p>
 * <p>The format of the 'type' property must be [FEEDNAME]_[FEEDVERSION] with the FEEDNAME in lower case,
 * for example: rss_0.9, rss_0.93, atom_0.3
 * </p>
 * <p>Added generics and default serialVersionUID.
 * </p>
 * @author Alejandro Abdelnur
 * @author escape-llc
 *
 */
public abstract class WireFeed implements Cloneable, Serializable, Extendable {
	private static final long serialVersionUID = 1L;
	private ObjectBean _objBean;
    private String _feedType;
    private String _encoding;
    private List<Module> _modules;
    private List<Object> _foreignMarkup;

    /**
     * Default constructor, for bean cloning purposes only.
     */
    protected WireFeed() {
        _objBean = new ObjectBean(this.getClass(),this);
    }

    /**
     * Creates a feed for a given type.
     * @param type of the feed to create.
     *
     */
    protected WireFeed(String type) {
        this();
        _feedType = type;
    }

    /**
     * Creates a deep 'bean' clone of the object.
     * @return a clone of the object.
     * @throws CloneNotSupportedException thrown if an element of the object cannot be cloned.
     *
     */
    public Object clone() throws CloneNotSupportedException {
        return _objBean.clone();
    }

    /**
     * Indicates whether some other object is "equal to" this one as defined by the Object equals() method.
     * @param other he reference object with which to compare.
     * @return <b>true</b> if 'this' object is equal to the 'other' object.
     *
     */
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        boolean ret = _objBean.equals(other);
        return ret;
    }

    /**
     * Returns a hashcode value for the object.
     * It follows the contract defined by the Object hashCode() method.
     * @return the hashcode of the bean object.
     *
     */
    public int hashCode() {
        return _objBean.hashCode();
    }

    /**
     * Returns the String representation for the object.
     * @return String representation for the object.
     *
     */
    public String toString() {
        return _objBean.toString();
    }
    
    /**
     * Sets the feedType of a the feed. <b>Do not use</b>, for bean cloning purposes only.
     * @param feedType the feedType of the feed.
     *
     */
    public void setFeedType(String feedType) {
        _feedType = feedType;
    }

    /**
     * Returns the type of the feed.
     *
     * @return the type of the feed.
     */
    public String getFeedType() {
        return _feedType;
    }

    /**
     * Returns the charset encoding of a the feed.
     * This property is not set by feed parsers. But it is used by feed generators
     * to set the encoding in the XML prolog.
     * @return the charset encoding of the feed.
     *
     */
    public String getEncoding() {
        return _encoding;
    }

    /**
     * Sets the charset encoding of a the feed.
     * This property is not set by feed parsers. But it is used by feed generators
     * to set the encoding in the XML prolog.
     * @param encoding the charset encoding of the feed.
     *
     */
    public void setEncoding(String encoding) {
        _encoding = encoding;
    }

    /**
     * Returns the channel modules.
     * @return a list of ModuleImpl elements with the channel modules,
     *         an empty list if none.
     *
     */
    public List<Module> getModules() {
        return (_modules==null) ? (_modules=new ArrayList<Module>()) : _modules;
    }

    /**
     * Sets the channel modules.
     * @param modules the list of ModuleImpl elements with the channel modules to set,
     *        an empty list or <b>null</b> if none.
     *
     */
    public void setModules(List<Module> modules) {
        _modules = modules;
    }

    /**
     * Returns the module identified by a given URI.
     * @param uri the URI of the ModuleImpl.
     * @return The module with the given URI, <b>null</b> if none.
     */
    public Module getModule(String uri) {
        return ModuleUtils.getModule(_modules,uri);
    }

    /**
     * Returns foreign markup found at channel level.
     * @return Opaque object to discourage use
     *
     */
    public Object getForeignMarkup() {
        return (_foreignMarkup==null) ? (_foreignMarkup=new ArrayList<Object>()) : _foreignMarkup;
    }

    /**
     * Sets foreign markup found at channel level.
     * @param foreignMarkup Opaque object to discourage use
     *
     */
    @SuppressWarnings("unchecked")
	public void setForeignMarkup(Object foreignMarkup) {
        _foreignMarkup = (List<Object>)foreignMarkup;
    }
}
