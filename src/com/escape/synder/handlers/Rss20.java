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
package com.escape.synder.handlers;

import org.xml.sax.Attributes;

import com.escape.synder.ParseContext;

/**
 * Top-level Synder handler for RSS 2.0
 * Tag is "rss_2.0".
 * Enforces version-check.
 * @author escape-llc
 *
 */
public class Rss20 extends RssAny {
	final String version = "2.0";
	public Rss20(ParseContext ctx) {
		super("rss_2.0", ctx);
	}
	@Override
	public boolean detect(String uri, String localName, String name,
			Attributes attributes) {
		if("rss".equalsIgnoreCase(localName)) {
			final String rv = attributes.getValue("version");
			if (rv != null && rv.startsWith(version))
				return true;
		}
		return false;
	}
}
