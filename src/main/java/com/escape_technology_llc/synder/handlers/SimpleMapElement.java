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
package com.escape_technology_llc.synder.handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.escape_technology_llc.synder.ParseContext;
import com.escape_technology_llc.synder.setters.MapSetter;
import com.escape_technology_llc.synder.setters.Setter;

/**
 * Element handler for Map&lt;String,String&gt; property type with given key.
 * Uses element content as the Map value.
 * @author escape-llc
 *
 * @param <T> host type.
 */
public class SimpleMapElement<T> extends ElementHandler<T> {
	final StringBuilder sb;
	final String keyattr;
	String key;
	/**
	 * Ctor.
	 * @param ctx Context.
	 * @param root Instance to attach.
	 * @param ka Key value.
	 * @param sx Target Map property.
	 */
	public SimpleMapElement(ParseContext ctx, T root, String ka, Setter sx) {
		super(ctx, root, sx);
		sb = new StringBuilder();
		keyattr = ka;
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		key = attributes.getValue(keyattr);
	}
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}
	@Override
	public void endElement(String arg0, String arg1, String arg2) throws SAXException {
		sx.set(root, new MapSetter.SetValue<String, String>(key, sb.toString()), ctx);
	}
}
