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

import java.util.ArrayList;
import java.util.List;

import com.escape.synder.ParseContext;
import com.sun.syndication.feed.synd.SyndPerson;
import com.sun.syndication.feed.synd.impl.SyndPersonImpl;

/**
 * Setter for type List<SyndPerson>.
 * @author escape-llc
 *
 */
public class SyndPersonListSetter extends ListSetter<SyndPerson> {
	public SyndPersonListSetter(Class<?> target, String method)
			throws SecurityException, NoSuchMethodException {
		super(target, method);
	}
	@Override
	protected List<SyndPerson> create() {
		return new ArrayList<SyndPerson>();
	}
	@Override
	protected <T> void setList(List<SyndPerson> list, T value, ParseContext ctx) {
		if(value instanceof String) {
			final SyndPersonImpl spi = new SyndPersonImpl();
			spi.setName((String)value);
			list.add(spi);
		}
		else if(value instanceof SyndPerson) {
			list.add((SyndPerson)value);
		}
	}
}
