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
package com.sun.syndication.feed.module;

import com.sun.syndication.feed.synd.SyndContent;

/**
 * RDF Content module.
 * @see <a href="http://web.resource.org/rss/1.0/modules/content/">RDF Site Summary Content Module</a>
 * @author escape-llc
 *
 */
public interface ContentModule extends Module {
    String URI = "http://purl.org/rss/1.0/modules/content/";
    /**
     * Get the entity-encoded/CDATA content.
     * @return NULL: no content; !NULL: content.
     */
    SyndContent getEncoded();
    /**
     * Set the encoded content.
     * @param sc
     */
    void setEncoded(SyndContent sc);
}
