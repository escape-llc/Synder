/*
 * Copyright 2004 Sun Microsystems, Inc.
 * Enhancements Copyright 2015 eScape Technology LLC
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
 *
 */
package com.sun.syndication.feed.impl;

import com.escape.proxy.PropertyDescriptor;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.io.Serializable;

/**
 * Provides deep <b>Bean</b> toString support.
 * <p>
 * It works on all read/write properties, recursively. It support all primitive
 * types, Strings, Collections, ToString objects and multi-dimensional arrays of
 * any of them.
 * <p>
 * 
 * @author Alejandro Abdelnur
 * @author escape-llc
 *
 */
public class ToStringBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Object[] NO_PARAMS = new Object[0];
	/**
	 * This class is used to communicate the "prefix" between the different implementations of toString().
	 */
	private static final ThreadLocal<Stack<String[]>> PREFIX_TL = new ThreadLocal<Stack<String[]>>() {
    public Stack<String[]> get() {
        Stack<String[]> o = super.get();
        if (o == null) {
            o = new Stack<String[]>();
            set(o);
        }
        return o;
    }
	};

	private Class<?> _beanClass;
	private Object _obj;
	private Set<String> _ignore;

	/**
	 * Default constructor.
	 * <p>
	 * To be used by classes extending ToStringBean only.
	 * <p>
	 * 
	 * @param beanClass
	 *          indicates the class to scan for properties, normally an interface
	 *          class.
	 *
	 */
	protected ToStringBean(Class<?> beanClass) {
		_beanClass = beanClass;
		_obj = this;
	}

	/**
	 * Creates a ToStringBean to be used in a delegation pattern.
	 * <p>
	 * For example:
	 * <p>
	 * <code>
	 *   public class Foo implements ToString {
	 * 
	 *       public String toString(String prefix) {
	 *           ToStringBean tsb = new ToStringBean(this);
	 *           return tsb.toString(prefix);
	 *       }
	 * 
	 *       public String toString() {
	 *           return toString("Foo");
	 *       }
	 * 
	 *   }
	 * </code>
	 * <p>
	 * 
	 * @param beanClass
	 *          indicates the class to scan for properties, normally an interface
	 *          class.
	 * @param obj
	 *          object bean to create String representation.
	 *
	 */
	public ToStringBean(Class<?> beanClass, Object obj) {
		this(beanClass, null, obj);
	}
	/**
	 * Ctor.
	 * Accepts explicit set of ignore properties.  These are not followed or printed.
	 * @param beanClass
	 * @param ignore
	 * @param obj
	 */
	public ToStringBean(Class<?> beanClass, Set<String> ignore, Object obj) {
		_beanClass = beanClass;
		_obj = obj;
		if(ignore != null)
			_ignore = ignore;
		else
			_ignore = Collections.emptySet();
	}

	/**
	 * Returns the String representation of the bean given in the constructor.
	 * <p>
	 * It uses the Class name as the prefix.
	 * <p>
	 * 
	 * @return bean object String representation.
	 *
	 */
	public String toString() {
		final Stack<String[]> stack = PREFIX_TL.get();
    final String[] tsInfo = (String[]) ((stack.isEmpty()) ? null : stack.peek());
    String prefix;
    if (tsInfo == null) {
        prefix = _obj.getClass().getSimpleName();
    }
    else {
        prefix = tsInfo[0];
        tsInfo[1] = prefix;
    }
		return toString(prefix, _obj, _beanClass, _ignore);
	}

	/**
	 * Returns the String representation of the bean given in the constructor.
	 * <p>
	 * 
	 * @param obj top-level object.
	 * @return bean object String representation.
	 *
	 */
	private String toString(String prefix, Object obj, Class<?> bc, Set<String> ignore) {
		if(obj == null) return bc.getName() + ": null";
		final StringBuffer sb = new StringBuffer(128);
		try {
			final PropertyDescriptor[] pds = BeanIntrospector.getPropertyDescriptors(bc);
			if (pds != null) {
				for (int ix = 0; ix < pds.length; ix++) {
					final String pName = pds[ix].getName();
					if (ignore.contains(pName)) continue;
					final Method pReadMethod = pds[ix].getReadMethod();
					if (pReadMethod != null &&
							pReadMethod.getDeclaringClass() != Object.class &&
							pReadMethod.getParameterTypes().length == 0) {
						final String ppfx = prefix + "." + pName;
						try {
							final Object value = pReadMethod.invoke(obj, NO_PARAMS);
							try {
								printProperty(sb, ppfx, value);
							}
							catch(Exception ex) {
								sb.append("\n\t").append(ppfx).append(": ").append(ex.toString()).append("\n");
							}
						} catch (Exception eex) {
							sb.append(ppfx).append(": ").append(eex.toString()).append("\n");
						}
					}
				}
			}
		} catch (Exception ex) {
			sb.append("\n\t").append(prefix).append(": ").append(ex.toString()).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Manage the prefix stack around a call to Object.toString(), and write result to buffer.
	 * @param sb Buffer.
	 * @param prefix Current prefix.
	 * @param value Value to write to buffer.
	 */
	private void printValue(StringBuffer sb, String prefix, Object value) {
		if(value == null) {
			sb.append(prefix).append("=null\n");
		}
		else {
			final String[] tsInfo = new String[2];
			tsInfo[0] = prefix;
			final Stack<String[]> stack = PREFIX_TL.get();
			stack.push(tsInfo);
			final String s = value.toString();
			stack.pop();
			if (tsInfo[1] == null) {
				sb.append(prefix).append("=").append(s).append("\n");
			}
			else {
				sb.append(s);
			}
		}
	}
	/**
	 * Handle the following cases:
	 * 		NULL					fixed string "null"
	 * 		Array					recursive printProperty() on each element
	 * 		Map						printValue() on each Map.Entry.value
	 * 		Collection		printValue() on each element from Iterator
	 * 		Object				printValue()
	 * @param sb output buffer
	 * @param prefix current prefix
	 * @param value value to output
	 */
	private void printProperty(StringBuffer sb, String prefix, Object value) {
		if (value == null) {
			sb.append(prefix).append("=null\n");
		} else if (value.getClass().isArray()) {
			final int length = Array.getLength(value);
			for (int ix = 0; ix < length; ix++) {
				final Object obj = Array.get(value, ix);
				printProperty(sb, prefix + "[" + ix + "]", obj);
			}
		} else if (value instanceof Map) {
			final Map<?, ?> map = (Map<?, ?>) value;
			final Iterator<?> i = map.entrySet().iterator();
			if (i.hasNext()) {
				while (i.hasNext()) {
					final Map.Entry<?, ?> me = (Map.Entry<?, ?>) i.next();
					printValue(sb, prefix + "[" + me.getKey() + "]", me.getValue());
				}
			} else {
				sb.append(prefix).append("=[]\n");
			}
		} else if (value instanceof Collection) {
			final Collection<?> collection = (Collection<?>) value;
			final Iterator<?> i = collection.iterator();
			if (i.hasNext()) {
				int c = 0;
				while (i.hasNext()) {
					printValue(sb, prefix + "[" + c + "]", i.next());
					c++;
				}
			} else {
				sb.append(prefix).append("=[]\n");
			}
		} else {
			printValue(sb, prefix, value);
		}
	}
}