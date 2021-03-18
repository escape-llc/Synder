/*
 * Copyright 2015 eScape Technology LLC.
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
package com.escape.synder.generators.xmlpull;

import org.xmlpull.v1.XmlSerializer;

import com.escape.synder.generators.GenerateContext;
import com.escape.synder.generators.ModuleGeneratorImpl;
import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.module.Module;

/**
 * DCMI Elements module generator bound to prefix "dc".
 * @author escape-llc
 *
 */
public final class DublinCore extends ModuleGeneratorImpl {
	public DublinCore(GenerateContext ctx) {
		super(DCModule.URI_ELEMENTS, "dc", ctx);
	}
	@Override
	public <T> void generate(Module mx, T outputMedium) throws Exception {
		if (!(outputMedium instanceof XmlSerializer))
			throw new IllegalArgumentException("outputMedium");
		if (!(mx instanceof DCModule))
			throw new IllegalArgumentException("mx");
		final XmlSerializer xs = (XmlSerializer) outputMedium;
		final DCModule mod = (DCModule)mx;
		final String nsuri = getTag();
		XmlPullGeneratorImpl.list(xs, mod.getContributors(), nsuri, "contributor");
		XmlPullGeneratorImpl.list(xs, mod.getCoverages(), nsuri, "coverage");
		XmlPullGeneratorImpl.list(xs, mod.getCreators(), nsuri, "creator");
		XmlPullGeneratorImpl.listDates(ctx, xs, mod.getDates(), nsuri, "date");
		XmlPullGeneratorImpl.list(xs, mod.getDescriptions(), nsuri, "description");
		XmlPullGeneratorImpl.list(xs, mod.getFormats(), nsuri, "format");
		XmlPullGeneratorImpl.list(xs, mod.getIdentifiers(), nsuri, "identifier");
		XmlPullGeneratorImpl.list(xs, mod.getLanguages(), nsuri, "language");
		XmlPullGeneratorImpl.list(xs, mod.getPublishers(), nsuri, "publisher");
		XmlPullGeneratorImpl.list(xs, mod.getRelations(), nsuri, "relation");
		XmlPullGeneratorImpl.list(xs, mod.getRightsList(), nsuri, "rights");
		XmlPullGeneratorImpl.list(xs, mod.getSources(), nsuri, "source");
		XmlPullGeneratorImpl.list(xs, mod.getTitles(), nsuri, "title");
		XmlPullGeneratorImpl.list(xs, mod.getTypes(), nsuri, "type");
	}
}
