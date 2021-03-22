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
 * Itunes module.
 * @author escape-llc
 *
 */
public interface ItunesModule extends Module {
	String URI = "http://www.itunes.com/dtds/podcast-1.0.dtd";
	SyndContent getSummary();
	void setSummary(SyndContent sc);
	String getSubtitle();
	void setSubtitle(String sx);
	String getKeywords();
	void setKeywords(String sx);
	String getExplicit();
	void setExplicit(String sx);
	String getDuration();
	void setDuration(String sx);
	String getAuthor();
	void setAuthor(String sx);
	/**
	 * Parse the keyword string (comma-delimited) and trim each entry.
	 * @return NULL: no keywords; !NULL: array of keyword strings.
	 */
	String[] parseKeywords();
}
