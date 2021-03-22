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
import java.util.List;

import com.escape_technology_llc.synder.ParseContext;

/**
 * Base class for properties of type java.util.List.
 * @author escape-llc
 *
 * @param <X> Element class.
 */
public abstract class ListSetter<X> extends Setter {
	final Method getter;
	public ListSetter(Class<?> target, String method) throws SecurityException,
			NoSuchMethodException {
		super(target, method, List.class);
		getter = target.getMethod("get" + method, (Class<?>[])null);
	}
	/**
	 * Get the list from "property".
	 * @param instance Host instance.
	 * @return Value of getter.
	 * @throws IllegalArgumentException on errors.
	 * @throws IllegalAccessException on errors.
	 * @throws InvocationTargetException on errors.
	 */
	@SuppressWarnings("unchecked")
	protected List<X> get(Object instance) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return (List<X>)getter.invoke(instance, (Object[])null);
	}
	/**
	 * Create a new empty list.
	 * @return new instance.
	 */
	protected abstract List<X> create();
	/**
	 * Apply the value to the list.
	 * @param <T> Cast.
	 * @param list Source list.
	 * @param value Source value.
	 * @param ctx Source of all.
	 */
	protected abstract <T> void setList(List<X> list, T value, ParseContext ctx);
	/**
	 * Get list.  If NULL, create a new list. Add element to list.
	 * Set property if a List was created.
	 */
	@Override
	public <T> void set(Object instance, T value, ParseContext ctx) {
		List<X> list = null;
		boolean crx = false;
		try {
			list = get(instance);
		}
		catch(Exception ex) {
		}
		if(list == null) {
			list = create();
			crx = true;
		}
		//System.out.println("set.size=" + list.size() + ",value=" + value);
		setList(list, value, ctx);
		if(crx) {
			try {
				mx.invoke(instance, list);
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
	}
}
