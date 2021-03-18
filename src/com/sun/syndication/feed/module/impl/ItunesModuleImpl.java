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
package com.sun.syndication.feed.module.impl;

import com.sun.syndication.feed.module.ItunesModule;
import com.sun.syndication.feed.synd.SyndContent;

/**
 * Itunes module implementation.
 * @author escape-llc
 *
 */
public class ItunesModuleImpl extends ModuleImpl implements ItunesModule {
	private static final long serialVersionUID = 1L;
	String author;
	String duration;
	String explicit;
	String keywords;
	String subtitle;
	SyndContent summary;
	public ItunesModuleImpl() {
		super(ItunesModule.class, ItunesModule.URI);
	}
	public String getAuthor() { return author; }
	public String getDuration() { return duration; }
	public String getExplicit() { return explicit; }
	public String getKeywords() { return keywords; }
	public String getSubtitle() { return subtitle; }
	public SyndContent getSummary() { return summary; }
	public void setAuthor(String sx) { author = sx; }
	public void setDuration(String sx) { duration = sx; }
	public void setExplicit(String sx) { explicit = sx; }
	public void setKeywords(String sx) { keywords = sx; }
	public void setSubtitle(String sx) {subtitle = sx; }
	public void setSummary(SyndContent sc) { summary = sc; }
	public void copyFrom(Object obj) {
		throw new UnsupportedOperationException();
	}
	public Class<?> getInterface() {
		return ItunesModule.class;
	}
	public String[] parseKeywords() {
		if(keywords != null && keywords.length() > 0) {
			final String[] kwds = keywords.split(",");
			for(int ix = 0; ix < kwds.length; ix++) {
				kwds[ix] = kwds[ix].trim();
			}
			return kwds;
		}
		return null;
	}
}
