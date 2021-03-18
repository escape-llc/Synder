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
package com.escape.synder.generators.xmlpull;

import org.xmlpull.v1.XmlSerializer;

import com.escape.synder.generators.GenerateContext;
import com.escape.synder.generators.ModuleGeneratorImpl;
import com.sun.syndication.feed.module.Module;
import com.sun.syndication.feed.module.SyModule;

public class RdfSyndication extends ModuleGeneratorImpl {
	public RdfSyndication(GenerateContext ctx) {
		super(SyModule.URI, "sy", ctx);
	}
	@Override
	public <T> void generate(Module mx, T outputMedium) throws Exception {
		if (!(outputMedium instanceof XmlSerializer))
			throw new IllegalArgumentException("outputMedium");
		if (!(mx instanceof SyModule))
			throw new IllegalArgumentException("mx");
		final XmlSerializer xs = (XmlSerializer) outputMedium;
		final SyModule mod = (SyModule)mx;
		if(mod.getUpdateFrequency() != 0) {
			XmlPullGeneratorImpl.textElement(xs, getTag(), "updateFrequency",
					Integer.toString(mod.getUpdateFrequency()));
		}
		if(mod.getUpdateBase() != null) {
			XmlPullGeneratorImpl.dateElement(xs, getTag(), "updateBase", mod.getUpdateBase(), ctx.getDateFormatW3C());
		}
		if(mod.getUpdatePeriod() != null) {
			XmlPullGeneratorImpl.textElement(xs, getTag(), "updatePeriod", mod.getUpdatePeriod());
		}
	}
}
