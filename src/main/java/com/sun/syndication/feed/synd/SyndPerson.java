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
package com.sun.syndication.feed.synd;

import com.sun.syndication.feed.module.Extendable;

/**
 * Bean interface for authors and contributors of SyndFeedImpl feeds and entries.
 * <p>
 * @author Dave Johnson
 *
 */
public interface SyndPerson extends Cloneable, Extendable
{

    /**
     * Returns  name of person
		 * @return the value.
     */
    public String getName();
    
    /**
     * Sets name of person.
		 * @param name new value.
     */
    public void setName(String name);
    
    /**
     * Returns URI of person.
		 * @return the value.
     */
    public String getUri();
    
    /** 
     * Sets URI of person.
		 * @param uri new value.
     */
    public void setUri(String uri);
    
    /** 
     * Returns email of person.
		 * @return the value.
     */
    public String getEmail();
    
    /**
     * Sets email of person.
		 * @param email new value.
     */
    public void setEmail(String email);


    /**
     * Creates a deep clone of the object.
     * @return a clone of the object.
     * @throws CloneNotSupportedException thrown if an element of the object cannot be cloned.
     *
     */
    public Object clone() throws CloneNotSupportedException;

}
