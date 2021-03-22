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

import java.util.ArrayList;
import java.util.List;

import com.escape_technology_llc.synder.ParseContext;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.impl.SyndEnclosureImpl;

/**
 * Setter for type List&lt;SyndEnclosure&gt;.
 * @author escape-llc
 *
 */
public class SyndEnclosureListSetter extends ListSetter<SyndEnclosure> {
	public SyndEnclosureListSetter(Class<?> target, String method)
			throws SecurityException, NoSuchMethodException {
		super(target, method);
	}
	@Override
	protected List<SyndEnclosure> create() {
		return new ArrayList<SyndEnclosure>();
	}
	@Override
	protected <T> void setList(List<SyndEnclosure> list, T value, ParseContext ctx) {
		list.add((SyndEnclosureImpl)value);
	}
}
