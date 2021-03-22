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
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.impl.SyndContentImpl;

/**
 * Setter for type List<SyndContent>.
 * @author escape-llc
 *
 */
public class SyndContentListSetter extends ListSetter<SyndContent> {
	public SyndContentListSetter(Class<?> target, String method)
			throws SecurityException, NoSuchMethodException {
		super(target, method);
	}
	@Override
	protected List<SyndContent> create() {
		return new ArrayList<SyndContent>();
	}
	@Override
	protected <T> void setList(List<SyndContent> list, T value, ParseContext ctx) {
		if(value instanceof SyndContent) {
			list.add((SyndContent)value);
		}
		else {
			final SyndContentImpl sci = new SyndContentImpl();
			sci.setType("text/*");
			sci.setValue((String) value);
			list.add(sci);
		}
	}
}
