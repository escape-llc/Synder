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

import com.sun.syndication.feed.synd.SyndContent;

/**
 * Setter for type SyndContent.
 * @author escape-llc
 *
 */
public class SyndContentSetter extends Setter {
	public SyndContentSetter(Class<?> target, String method) throws SecurityException,
			NoSuchMethodException {
		super(target, method, SyndContent.class);
	}
}
