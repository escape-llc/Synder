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
package com.escape_technology_llc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;

import com.escape_technology_llc.synder.Context;
import com.escape_technology_llc.synder.DefaultContext;
import com.escape_technology_llc.synder.DefaultParseContext;
import com.escape_technology_llc.synder.generators.DefaultGenerateContext;
import com.escape_technology_llc.synder.generators.xmlpull.GenerateXmlPull;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndPerson;

public class GeneratorTests {
	private static String PATH;
	private static String OUTPUT;
	private static String PROPERTIES;

	static {
		try {
			PATH = new File(".").getCanonicalPath() + "\\target\\test-classes\\";
			OUTPUT = new File(".").getCanonicalPath() + "\\target\\test-output\\";
			final File fx = new File(OUTPUT);
			if(!fx.exists()) {
				fx.mkdirs();
			}
			PROPERTIES = PATH + "synder.properties";
		} catch (IOException e) {
		}
	}
	final Context ctx;
	public GeneratorTests() throws Exception {
		final InputStream px = new FileInputStream(PROPERTIES);
		ctx = new DefaultContext(px);
	}
	SyndFeed readFeed(String filename) throws Exception {
		final InputStream ss = new FileInputStream(filename);
		final DefaultParseContext dpc = new DefaultParseContext(ctx);
		final SyndFeed sf = dpc.parse(new InputSource(ss));
		Assert.assertSame("dpc.getReport.size", 0, dpc.getReport().size());
		return sf;
	}
	void compare(String tag, SyndContent sc1, SyndContent sc2) {
		Assert.assertTrue(tag, (sc1 == null) == (sc2 == null));
		if(sc1 != null && sc2 != null) {
			Assert.assertEquals(tag + ".mode", sc1.getMode(), sc2.getMode());
			Assert.assertEquals(tag + ".type", sc1.getType(), sc2.getType());
			Assert.assertEquals(tag + ".value", sc1.getValue(), sc2.getValue());
		}
	}
	void compare(String tag, SyndCategory sc1, SyndCategory sc2) {
		Assert.assertTrue(tag, (sc1 == null) == (sc2 == null));
		if(sc1 != null && sc2 != null) {
			Assert.assertEquals(tag + ".name", sc1.getName(), sc2.getName());
			Assert.assertEquals(tag + ".taxUri", sc1.getTaxonomyUri(), sc2.getTaxonomyUri());
		}
	}
	void compare(String tag, SyndPerson se1, SyndPerson se2) {
		Assert.assertTrue(tag, (se1 == null) == (se2 == null));
		if(se1 != null && se2 != null) {
			Assert.assertEquals(tag + ".name", se1.getName(), se2.getName());
			Assert.assertEquals(tag + ".email", se1.getEmail(), se2.getEmail());
			Assert.assertEquals(tag + ".uri", se1.getUri(), se2.getUri());
		}
	}
	void compare(String tag, SyndEntry se1, SyndEntry se2) {
		Assert.assertTrue(tag, (se1 == null) == (se2 == null));
		if(se1 != null && se2 != null) {
			compare(tag + ".titleEx", se1.getTitleEx(), se2.getTitleEx());
			Assert.assertTrue(tag + ".contents", (se1.getContents() == null) == (se2.getContents() == null));
			Assert.assertEquals(tag + ".contents.size", se1.getContents().size(), se2.getContents().size());
			for(int ix = 0; ix < se1.getContents().size(); ix++) {
				compare(tag + ".contents[" + ix + "]", se1.getContents().get(ix), se2.getContents().get(ix));
			}
			Assert.assertTrue(tag + ".categories", (se1.getCategories() == null) == (se2.getCategories() == null));
			Assert.assertEquals(tag + ".categories.size", se1.getCategories().size(), se2.getCategories().size());
			for(int ix = 0; ix < se1.getCategories().size(); ix++) {
				compare(tag + ".categories[" + ix + "]", se1.getCategories().get(ix), se2.getCategories().get(ix));
			}
			Assert.assertTrue(tag + ".authors", (se1.getAuthors() == null) == (se2.getAuthors() == null));
			Assert.assertEquals(tag + ".authors.size", se1.getAuthors().size(), se2.getAuthors().size());
			for(int ix = 0; ix < se1.getAuthors().size(); ix++) {
				compare(tag + ".authors[" + ix + "]", se1.getAuthors().get(ix), se2.getAuthors().get(ix));
			}
		}
	}
	private void compareFeeds(SyndFeed sf, SyndFeed sf2) {
		Assert.assertEquals("sf.feedType", sf.getFeedType(), sf2.getFeedType());
		compare("sf.titleEx", sf.getTitleEx(), sf2.getTitleEx());
		compare("sf.descriptionEx", sf.getDescriptionEx(), sf2.getDescriptionEx());
		Assert.assertTrue("sf.entries", (sf.getEntries() == null) == (sf2.getEntries() == null));
		Assert.assertEquals("sf.entries.size", sf.getEntries().size(), sf2.getEntries().size());
		for(int ix = 0; ix < sf.getEntries().size(); ix++) {
			final SyndEntry se1 = sf.getEntries().get(ix);
			final SyndEntry se2 = sf2.getEntries().get(ix);
			compare("se[" + ix + "]", se1, se2);
		}
		Assert.assertTrue("sf.categories", (sf.getCategories() == null) == (sf2.getCategories() == null));
		Assert.assertEquals("sf.categories.size", sf.getCategories().size(), sf2.getCategories().size());
		for(int ix = 0; ix < sf.getCategories().size(); ix++) {
			compare("sf.categories[" + ix + "]", sf.getCategories().get(ix), sf2.getCategories().get(ix));
		}
	}
	@Test
	public void rss() throws Exception {
		final SyndFeed sf = readFeed(PATH + "feed_1.xml");
		Assert.assertNotNull("sf.parse", sf);
		Assert.assertEquals("sf.feedType", ParserTests.RSS20, sf.getFeedType());
		final DefaultGenerateContext dgc = new DefaultGenerateContext(ctx, new GenerateXmlPull());
		dgc.generate(sf, sf.getFeedType(), new FileOutputStream(OUTPUT + "generate_1.xml"), "UTF-8");
	}
	@Test
	public void rssIntegrity() throws Exception {
		final SyndFeed sf = readFeed(PATH + "feed_3.xml");
		Assert.assertNotNull("sf.parse", sf);
		Assert.assertEquals("sf.feedType", ParserTests.RSS20, sf.getFeedType());
		final DefaultGenerateContext dgc = new DefaultGenerateContext(ctx, new GenerateXmlPull());
		dgc.generate(sf, sf.getFeedType(), new FileOutputStream(OUTPUT + "integrity_3.xml"), "UTF-8");
		final SyndFeed sf2 = readFeed(OUTPUT + "integrity_3.xml");
		Assert.assertNotNull("sf2.parse", sf2);
		compareFeeds(sf, sf2);
	}
	@Test
	public void atom() throws Exception {
		final SyndFeed sf = readFeed(PATH + "feed_7.xml");
		Assert.assertNotNull("sf.parse", sf);
		Assert.assertEquals("sf.feedType", ParserTests.ATOM10, sf.getFeedType());
		final DefaultGenerateContext dgc = new DefaultGenerateContext(ctx, new GenerateXmlPull());
		dgc.generate(sf, sf.getFeedType(), new FileOutputStream(OUTPUT + "generate_7.xml"), "UTF-8");
	}
	@Test
	public void atomIntegrity() throws Exception {
		final SyndFeed sf = readFeed(PATH + "feed_7.xml");
		Assert.assertNotNull("sf.parse", sf);
		Assert.assertEquals("sf.feedType", ParserTests.ATOM10, sf.getFeedType());
		final DefaultGenerateContext dgc = new DefaultGenerateContext(ctx, new GenerateXmlPull());
		dgc.generate(sf, sf.getFeedType(), new FileOutputStream(OUTPUT + "integrity_7.xml"), "UTF-8");
		final SyndFeed sf2 = readFeed(OUTPUT + "integrity_7.xml");
		Assert.assertNotNull("sf2.parse", sf2);
		compareFeeds(sf, sf2);
	}
	@Test
	public void dublinCoreAndSyndication() throws Exception {
		final SyndFeed sf = readFeed(PATH + "feed_3.xml");
		Assert.assertNotNull("sf.parse", sf);
		Assert.assertEquals("sf.feedType", ParserTests.RSS20, sf.getFeedType());
		final DefaultGenerateContext dgc = new DefaultGenerateContext(ctx, new GenerateXmlPull());
		dgc.generate(sf, sf.getFeedType(), new FileOutputStream(OUTPUT + "generate_3.xml"), "UTF-8");
	}
	@Test
	public void rdfContentAndItunes() throws Exception {
		final SyndFeed sf = readFeed(PATH + "feed_5.xml");
		Assert.assertNotNull("sf.parse", sf);
		Assert.assertEquals("sf.feedType", ParserTests.RSS20, sf.getFeedType());
		final DefaultGenerateContext dgc = new DefaultGenerateContext(ctx, new GenerateXmlPull());
		dgc.generate(sf, sf.getFeedType(), new FileOutputStream(OUTPUT + "generate_5.xml"), "UTF-8");
	}
}
