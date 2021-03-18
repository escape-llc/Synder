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
package com.escape.synder.generators;

import java.util.List;

import com.sun.syndication.feed.synd.SyndFeed;

public abstract class GeneratorImpl {
	final String tag;
	protected List<ModuleGeneratorImpl> fns;
	protected List<ModuleGeneratorImpl> ins;
	protected final GenerateContext ctx;
	protected GeneratorImpl(String tag, GenerateContext ctx) {
		if(tag == null || tag.length() == 0) throw new IllegalArgumentException("tag");
		if(ctx == null) throw new IllegalArgumentException("ctx");
		this.tag = tag;
		this.ctx = ctx;
	}
	/**
	 * Set the namespace generators.
	 * @param fns Feed-level namespaces.
	 * @param ins Item-level namespaces.
	 */
	public void setNamespace(List<ModuleGeneratorImpl> fns, List<ModuleGeneratorImpl> ins) {
		this.fns = fns;
		this.ins = ins;
	}
	public String getTag() { return tag; }
	public boolean detect(String tag) { return this.tag.equals(tag); }
	public abstract <T> void generate(SyndFeed sf, String enc, T outputMedium) throws Exception;
}
