/*
 * Copyright 2012 eScape Technology LLC.
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
import java.util.Map;

import com.escape_technology_llc.synder.ParseContext;

/**
 * Setter for map property types.
 * @author escape-llc
 *
 * @param <K> Key value type.
 * @param <V> Value value type.
 */
public abstract class MapSetter<K,V> extends Setter {
	public static final class SetValue<K,V> {
		public final K key;
		public final V value;
		public SetValue(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
	final Method getter;
	public MapSetter(Class<?> target, String method) throws SecurityException,
			NoSuchMethodException {
		super(target, method, Map.class);
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
	protected Map<K,V> get(Object instance) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return (Map<K,V>)getter.invoke(instance, (Object[])null);
	}
	/**
	 * Create a new empty map.
	 * @return new instance.
	 */
	protected abstract Map<K,V> create();
	/**
	 * Apply the value to the list.
	 * @param <T> Cast.
	 * @param map Source map.
	 * @param value Source value.
	 * @param ctx Source of all.
	 */
	protected abstract <T> void setMap(Map<K,V> map, T value, ParseContext ctx);
	/**
	 * Get list.  If NULL, create a new list. Add element to list.
	 * Set property if a List was created.
	 */
	@Override
	public <T> void set(Object instance, T value, ParseContext ctx) {
		Map<K,V> map = null;
		boolean crx = false;
		try {
			map = get(instance);
		}
		catch(Exception ex) {
		}
		if(map == null) {
			map = create();
			crx = true;
		}
		//System.out.println("set.size=" + list.size() + ",value=" + value);
		setMap(map, value, ctx);
		if(crx) {
			try {
				mx.invoke(instance, map);
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
	}
}
