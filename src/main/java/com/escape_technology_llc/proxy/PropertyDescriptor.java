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
package com.escape_technology_llc.proxy;

import java.lang.reflect.Method;

/**
 * Proxy for JavaBeans PropertyDescriptor.
 * @author escape-llc
 *
 */
public class PropertyDescriptor {
	final Method _rm;
	final Method _wm;
	final String _name;

	public PropertyDescriptor(String name, Method getter, Method setter) {
		_name = name;
		_rm = getter;
		_wm = setter;
	}
	public String getName() {
		return _name;
	}
	public Method getReadMethod() {
		return _rm;
	}
	public Method getWriteMethod() {
		return _wm;
	}
}
