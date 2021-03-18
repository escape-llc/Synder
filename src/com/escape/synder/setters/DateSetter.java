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
package com.escape.synder.setters;

import java.util.Date;

import com.escape.synder.ParseContext;

/**
 * Setter for type java.util.Date.
 * @author escape-llc
 *
 */
public class DateSetter extends Setter {
	public DateSetter(Class<?> target, String method) throws SecurityException,
			NoSuchMethodException {
		super(target, method, Date.class);
	}
	/**
	 * Set the value.
	 * String -> Date
	 */
	@Override
	public <T> void set(Object instance, T value, ParseContext ctx) {
		final Date dx = ctx.getDateParser().parseDate(value.toString());
		if(dx != null)
			super.set(instance, dx, ctx);
	}
}
