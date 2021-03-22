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

import com.sun.syndication.feed.module.ContentModule;
import com.sun.syndication.feed.synd.SyndContent;

public class ContentModuleImpl extends ModuleImpl implements ContentModule {
	private static final long serialVersionUID = 1L;
	SyndContent encoded;
	public ContentModuleImpl() {
		super(ContentModule.class, ContentModule.URI);
	}
	public void copyFrom(Object obj) {
		throw new UnsupportedOperationException();
	}
	public Class<?> getInterface() {
		return ContentModule.class;
	}
	public SyndContent getEncoded() {
		return encoded;
	}
	public void setEncoded(SyndContent sc) {
		encoded = sc;
	}
}
