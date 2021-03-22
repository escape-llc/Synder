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

import java.util.HashMap;
import java.util.Map;

import com.escape_technology_llc.synder.ParseContext;

/**
 * Setter fixed to Map<String,String> type.
 * @author escape-llc
 *
 */
public class MapStringStringSetter extends MapSetter<String, String> {
	public MapStringStringSetter(Class<?> target, String method) throws SecurityException, NoSuchMethodException {
		super(target, method);
	}
	@Override
	protected Map<String, String> create() { return new HashMap<String,String>(); }
	@Override
	protected <T> void setMap(Map<String, String> map, T value, ParseContext ctx) {
		if(value instanceof MapSetter.SetValue) {
			@SuppressWarnings("unchecked")
			final MapSetter.SetValue<String,String> sv = (MapSetter.SetValue<String,String>)value;
			map.put(sv.key, sv.value);
		}
		else if(value instanceof Map.Entry) {
			@SuppressWarnings("unchecked")
			final Map.Entry<String,String> sv = (Map.Entry<String,String>)value;
			map.put(sv.getKey(), sv.getValue());
		}
	}
}
