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
import java.util.ArrayList;
import java.util.List;

import com.escape_technology_llc.synder.ParseContext;
import com.escape_technology_llc.synder.SyndForeignMarkup;
import com.escape_technology_llc.synder.SyndForeignMarkupImpl;

/**
 * Setter for property ForeignMarkup, which has signature setForeignMarkup(Object)
 * but the return value is actually of type List&lt;Object>.
 * @author escape-llc
 *
 */
public class SyndForeignMarkupListSetter extends Setter {
	final Method getter;
	public SyndForeignMarkupListSetter(Class<?> target, String method)
			throws SecurityException, NoSuchMethodException {
		super(target, method, Object.class);
		getter = target.getMethod("get" + method, (Class<?>[])null);
	}
	@SuppressWarnings("unchecked")
	List<Object> get(Object instance)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		return (List<Object>)getter.invoke(instance, (Object[])null);
	}
	<T> void setList(List<Object> list, T value, ParseContext ctx) {
		if(value instanceof SyndForeignMarkup) {
			list.add(value);
		}
		else {
			final SyndForeignMarkupImpl sci = new SyndForeignMarkupImpl();
			sci.setType("text/*");
			sci.setValue((String) value);
			list.add(sci);
		}
	}
	@Override
	public <T> void set(Object instance, T value, ParseContext ctx) {
		List<Object> list = null;
		boolean crx = false;
		try {
			list = get(instance);
		}
		catch(Exception ex) {
		}
		if(list == null) {
			list = new ArrayList<Object>();
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
