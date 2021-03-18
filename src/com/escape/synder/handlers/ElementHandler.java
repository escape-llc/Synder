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

import com.escape.synder.ParseContext;
import com.escape.synder.setters.Setter;

/**
 * Base class for element handlers.
 * @author escape-llc
 *
 * @param <T> Class containing "properties" to manipulate.
 */
public class ElementHandler<T> extends SubHandler<T> {
	protected final Setter sx;
	protected ElementHandler(ParseContext ctx, T root, Setter sx) {
		super(ctx, root);
		if(sx == null) throw new IllegalArgumentException("sx");
		this.sx = sx;
	}
}
