/*
 * Copyright 2014 eScape Technology LLC.
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
package com.escape.synder.handlers.search;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.escape.synder.ParseContext;
import com.escape.synder.SyndImageImpl2;
import com.escape.synder.handlers.ElementHandler;
import com.escape.synder.setters.Setter;

/**
 * Handler for Open Search Image element.
 * Marshals to SyndImageImpl2.
 * Creates a SyndLink: "Image"->Rel, source->Href, alt->Title, width->Width, height->Height.
 * @author escape-llc
 *
 * @param <R> Hosting class.
 */
public class ImageElement<R> extends ElementHandler<R> {
	protected final SyndImageImpl2 link;
	public ImageElement(ParseContext ctx, R root, Setter sx) {
		super(ctx, root, sx);
		link = new SyndImageImpl2();
	}
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		//link.setRel("Image");
		final String source = attributes.getValue("source");
		if(source != null && source.length() > 0) {
			link.setUrl(source);
		}
		final String alt = attributes.getValue("alt");
		if(alt != null && alt.length() > 0) {
			link.setTitle(alt);
		}
		final String width = attributes.getValue("width");
		if (width != null && width.length() > 0) {
			try {
				link.setWidth(Integer.parseInt(width));
			} catch (NumberFormatException nfe) {
			}
		}
		final String height = attributes.getValue("height");
		if (height != null && height.length() > 0) {
			try {
				link.setHeight(Integer.parseInt(height));
			} catch (NumberFormatException nfe) {
			}
		}
	}
	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		sx.set(root, link, ctx);
	}
}
