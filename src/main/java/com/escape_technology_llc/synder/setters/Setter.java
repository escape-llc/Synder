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
package com.escape_technology_llc.synder.setters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.escape_technology_llc.synder.ParseContext;

/**
 * Apply value to "property" via reflection.
 * @author escape-llc
 *
 */
public class Setter {
	final Method mx;
	/**
	 * Ctor.
	 * Bind to method of type String.
	 * @param target Source class.
	 * @param method Method name, minus "set".
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public Setter(Class<?> target, String method) throws SecurityException, NoSuchMethodException {
		mx = target.getMethod("set" + method, String.class);
	}
	/**
	 * Ctor.
	 * Bind to method of specific parameter type.
	 * @param target Source class.
	 * @param method Method name, minus "set".
	 * @param pt Parameter class.
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public Setter(Class<?> target, String method, Class<?> pt) throws SecurityException, NoSuchMethodException {
		mx = target.getMethod("set" + method, pt);
	}
	/**
	 * Set the value.
	 * @param <T> Cast.
	 * @param instance Instance to apply value to.
	 * @param value Value to apply.
	 * @param ctx Source of all.
	 */
	public <T> void set(Object instance, T value, ParseContext ctx) {
		try {
			mx.invoke(instance, value);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}
}
