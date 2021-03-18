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

import com.sun.syndication.feed.module.Module;

/**
 * Base implementation for generator modules
 * @author escape-llc
 *
 */
public abstract class ModuleGeneratorImpl {
	final String tag;
	final String pfx;
	protected final GenerateContext ctx;
	/**
	 * Ctor.
	 * @param tag Namespace URI.  Not NULL/empty.
	 * @param pfx Namespace prefix. Not NULL/empty.
	 * @param ctx Generate context. Not NULL.
	 */
	public ModuleGeneratorImpl(String tag, String pfx, GenerateContext ctx) {
		if(tag == null || tag.length() == 0) throw new IllegalArgumentException("tag");
		if(pfx == null || pfx.length() == 0) throw new IllegalArgumentException("pfx");
		if(ctx == null) throw new IllegalArgumentException("ctx");
		this.tag = tag;
		this.pfx = pfx;
		this.ctx = ctx;
	}
	/**
	 * Get the namespace URI.
	 * @return
	 */
	public String getTag() { return tag; }
	/**
	 * Get the namespace prefix.
	 * @return
	 */
	public String getPrefix() { return pfx; }
	/**
	 * Return whether NSURI is a match for this module.
	 * @param tag NSURI to check.
	 * @return true: exact match; false: not match.
	 */
	public boolean detect(String tag) { return this.tag.equals(tag); }
	/**
	 * Generate output for given module.
	 * @param mx Source module; must match the type the generator implementation expects.
	 * @param outputMedium Output medium; e.g. XmlSerializer.
	 * @throws Exception
	 */
	public abstract <T> void generate(Module mx, T outputMedium) throws Exception;
}
