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
import com.escape.synder.setters.Setter;

/**
 * Means to create handlers bound to a specific Setter.
 * @author escape-llc
 *
 * @param <T> Hosting instance type.
 */
public abstract class HandlerFactory<T> {
	protected Setter sx;
	protected HandlerFactory(Setter sx) {
		if(sx == null) throw new IllegalArgumentException("sx");
		this.sx = sx;
	}
	/**
	 * Create the handler.
	 * @param pc Source of all.
	 * @param root Hosting instance.
	 * @return New handler.
	 */
	public abstract SubHandler<?> create(ParseContext pc, Attributes ax, T root);
}
