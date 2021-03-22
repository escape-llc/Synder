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
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;

import com.escape_technology_llc.synder.Context;
import com.escape_technology_llc.synder.DefaultParseContext;
import com.escape_technology_llc.synder.DefaultContext;
import com.escape_technology_llc.synder.ParseContext;
import com.escape_technology_llc.synder.SyndEntry2;
import com.escape_technology_llc.synder.SyndForeignMarkup;
import com.escape_technology_llc.synder.SyndImage2;
import com.escape_technology_llc.synder.handlers.ContentElement;
import com.sun.syndication.feed.module.ContentModule;
import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.module.DCTermsModule;
import com.sun.syndication.feed.module.ItunesModule;
import com.sun.syndication.feed.module.MediaRssModule;
import com.sun.syndication.feed.module.Module;
import com.sun.syndication.feed.module.SyModule;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndPerson;

public class ParserTests {
	private static String PATH;
	private static String PROPERTIES;

	static {
		try {
			PATH = new File(".").getCanonicalPath() + "\\target\\test-classes\\";
			PROPERTIES = PATH + "synder.properties";
		} catch (IOException e) {
		}
	}
	final Context ctx;
	public ParserTests() throws Exception {
		final InputStream px = new FileInputStream(PROPERTIES);
		ctx = new DefaultContext(px);
	}
	void verifyFeedResults(SyndFeed sf, String ft) {
		Assert.assertNotNull("sfi.parse", sf);
		Assert.assertEquals("sfi.feedType", ft, sf.getFeedType());
		Assert.assertNotNull("sf.title", sf.getTitle());
		Assert.assertNotNull("sf.publishedDate", sf.getPublishedDate());
		Assert.assertTrue("sf.link", sf.getLink() != null || (sf.getLinks() != null && sf.getLinks().size() > 0));
	}
	void verifyResults(SyndFeed sf, String ft) {
		verifyFeedResults(sf, ft);
		verifyEntries(sf);
	}
	void verifyRFC4287(SyndFeed sf) {
		Assert.assertNotNull("sf.image", sf.getImage());
		Assert.assertNotNull("sf.logo", sf.getLogo());
		Assert.assertNotNull("sf.generator", sf.getGenerator());
		Assert.assertNotNull("sf.generator.title", sf.getGenerator().getTitle());
		Assert.assertNotNull("sf.generator.type", sf.getGenerator().getType());
		Assert.assertNotNull("sf.generator.href", sf.getGenerator().getHref());
		Assert.assertNotNull("sf.rights", sf.getRights());
		Assert.assertNotNull("sf.rights.value", sf.getRights().getValue());
		Assert.assertEquals("sf.rights.mode", "rights", sf.getRights().getMode());
		final List<SyndEntry> entries = sf.getEntries();
		for (final SyndEntry se : entries) {
			Assert.assertNotNull("se.rights", se.getRights());
			Assert.assertNotNull("se.rights.value", se.getRights().getValue());
			Assert.assertEquals("se.rights.mode", "rights", se.getRights().getMode());
		}
	}
	void verifyEntries(SyndFeed sf) {
		final List<SyndEntry> entries = sf.getEntries();
		Assert.assertNotNull("sf.getEntries", entries);
		Assert.assertNotSame("entries.size", 0, entries.size());
		for (final SyndEntry se : entries) {
			Assert.assertNotNull("se.title", se.getTitle());
			Assert.assertNotNull("se.publishedDate", se.getPublishedDate());
			Assert.assertTrue("se.link", se.getLink() != null || (se.getLinks() != null && se.getLinks().size() > 0));
			Assert.assertNotNull("se.contents", se.getContents());
			Assert.assertSame("se.contents.size", 1, se.getContents().size());
		}
	}
	void verifyEnclosures(SyndFeed sf) {
		final List<SyndEntry> entries = sf.getEntries();
		for (final SyndEntry se : entries) {
			final List<SyndEnclosure> enc = se.getEnclosures();
			Assert.assertNotNull("se.enclosures", enc);
			Assert.assertNotSame("se.enclosures.size", 0, enc.size());
			for (final SyndEnclosure en : enc) {
				Assert.assertNotNull("en.url", en.getUrl());
				Assert.assertNotNull("en.type", en.getType());
			}
		}
	}
	void verifyCategories(SyndFeed sf, boolean vfeed) {
		if (vfeed) {
			Assert.assertTrue("sf.categories", sf.getCategories() != null
					&& sf.getCategories().size() > 0);
			for (final SyndCategory sc : sf.getCategories()) {
				Assert.assertNotNull("sc.feed.name", sc.getName());
			}
		}
		final List<SyndEntry> entries = sf.getEntries();
		for (final SyndEntry se : entries) {
			Assert.assertNotSame("se.categories", se.getCategories() != null && se.getCategories().size() > 0);
			for(final SyndCategory sc: se.getCategories()) {
				Assert.assertNotNull("sc.item.name", sc.getName());
			}
		}
	}
	void verifyImage(SyndFeed sf) {
		final SyndImage si = sf.getImage();
		Assert.assertNotNull("sf.getImage", si);
		Assert.assertTrue("image.url", si.getUrl() != null && si.getUrl().length() > 0);
		Assert.assertTrue("image.title", si.getTitle() != null && si.getTitle().length() > 0);
		Assert.assertTrue("image.link", si.getLink() != null && si.getLink().length() > 0);
	}
	void verifyAuthors(SyndFeed sf) {
		final List<SyndPerson> authors = sf.getAuthors();
		Assert.assertTrue("sf.authors", authors != null && authors.size() > 0);
		for (final SyndPerson se : authors) {
			Assert.assertTrue("author.name", se.getName() != null && se.getName().length() > 0);
			Assert.assertTrue("author.email", se.getEmail() != null && se.getEmail().length() > 0);
			Assert.assertTrue("author.uri", se.getUri() != null && se.getUri().length() > 0);
		}
		final List<SyndEntry> entries = sf.getEntries();
		for (final SyndEntry se : entries) {
			final List<SyndPerson> eauthors = se.getAuthors();
			Assert.assertNotNull("item.author", se.getAuthor());
			for (final SyndPerson sse : eauthors) {
				Assert.assertTrue("author.name", sse.getName() != null && sse.getName().length() > 0);
				Assert.assertTrue("author.email", sse.getEmail() != null && sse.getEmail().length() > 0);
				Assert.assertTrue("author.uri", sse.getUri() != null && sse.getUri().length() > 0);
			}
		}
	}
	void verifyDCModule(SyndFeed sf) {
		final Module mx = sf.getModule(DCModule.URI_ELEMENTS);
		Assert.assertNotNull("module.feed", mx);
		Assert.assertTrue("module.feed is DCModule", mx instanceof DCModule);
		final DCModule dc = (DCModule)mx;
		Assert.assertTrue("module.feed.lang", dc.getLanguage() != null && dc.getLanguage().length() > 0);
		Assert.assertTrue("module.feed.creator", dc.getCreator() != null && dc.getCreator().length() > 0);
		Assert.assertTrue("module.feed.title", dc.getTitle() != null && dc.getTitle().length() > 0);
		final List<SyndEntry> entries = sf.getEntries();
		for (final SyndEntry se : entries) {
			final Module mxx = se.getModule(DCModule.URI_ELEMENTS);
			Assert.assertNotNull("module.item", mxx);
			Assert.assertTrue("module.item is DCModule", mxx instanceof DCModule);
			final DCModule dcc = (DCModule)mxx;
			Assert.assertTrue("module.item.creator", dcc.getCreator() != null && dcc.getCreator().length() > 0);
			//Assert.assertTrue("dcc.title", dcc.getTitle() != null && dcc.getTitle().length() > 0);
		}
	}
	void verifyDCTermsModule(SyndFeed sf) {
		//final Module mx = sf.getModule(DCModule.URI_TERMS);
		//Assert.assertNotNull("module.feed", mx);
		//Assert.assertTrue("module.feed is DCModule", mx instanceof DCModule);
		//final DCModule dc = (DCModule)mx;
		//Assert.assertTrue("module.feed.lang", dc.getLanguage() != null && dc.getLanguage().length() > 0);
		final List<SyndEntry> entries = sf.getEntries();
		for (final SyndEntry se : entries) {
			final Module mxx = se.getModule(DCTermsModule.URI_TERMS);
			Assert.assertNotNull("module.item", mxx);
			Assert.assertTrue("module.item is DCModule", mxx instanceof DCModule);
			Assert.assertTrue("module.item is DCTermsModule", mxx instanceof DCTermsModule);
			final DCModule dcc = (DCModule)mxx;
			Assert.assertTrue("module.item.publisher", dcc.getPublisher() != null && dcc.getPublisher().length() > 0);
			Assert.assertTrue("module.item.language", dcc.getLanguage() != null && dcc.getLanguage().length() > 0);
			final DCTermsModule dt = (DCTermsModule)mxx;
			Assert.assertTrue("module.item.issued", dt.getIssued() != null);
		}
	}
	void verifySYModule(SyndFeed sf) {
		final Module mx = sf.getModule(SyModule.URI);
		Assert.assertNotNull("module.feed", mx);
		Assert.assertTrue("module.feed is SyModule", mx instanceof SyModule);
		final SyModule dc = (SyModule)mx;
		Assert.assertTrue("module.feed.updateBase", dc.getUpdateBase() != null);
		Assert.assertTrue("module.feed.updateFreq", dc.getUpdateFrequency() > 0);
		Assert.assertTrue("module.feed.updatePeriod", dc.getUpdatePeriod() != null && dc.getUpdatePeriod().length() > 0);
		final List<SyndEntry> entries = sf.getEntries();
		for (final SyndEntry se : entries) {
			final Module mxx = se.getModule(SyModule.URI);
			Assert.assertNull("module.item", mxx);
		}
	}
	void verifyContentModule(SyndFeed sf) {
		final Module mx = sf.getModule(ContentModule.URI);
		Assert.assertNull("module.feed", mx);
		final List<SyndEntry> entries = sf.getEntries();
		for (final SyndEntry se : entries) {
			final Module mxx = se.getModule(ContentModule.URI);
			Assert.assertNotNull("module.item", mxx);
			Assert.assertTrue("module.item is ContentModule", mxx instanceof ContentModule);
			final ContentModule cm = (ContentModule)mxx;
			final SyndContent sc = cm.getEncoded();
			Assert.assertNotNull("module.item.encoded", sc);
			Assert.assertEquals("module.item.encoded.type", ContentElement.DEFAULT_TYPE, sc.getType());
			Assert.assertNotNull("module.item.encoded.value", sc.getValue());
		}
	}
	void verifyMediaRssModule(SyndFeed sf) {
		final List<SyndEntry> entries = sf.getEntries();
		for (final SyndEntry se : entries) {
			final Module mxx = se.getModule(MediaRssModule.URI);
			Assert.assertNotNull("module.item", mxx);
			Assert.assertTrue("module.item is MediaRssModule", mxx instanceof MediaRssModule);
			final MediaRssModule cm = (MediaRssModule)mxx;
			final SyndContent sc = cm.getTitle();
			Assert.assertNotNull("module.item.title", sc);
			Assert.assertNotNull("module.item.encoded.value", sc.getValue());
			final String keywords = cm.getKeywords();
			Assert.assertNotNull("module.item.keywords", keywords);
		}
	}
	void verifyContentType(SyndFeed sf, String ct) {
		final List<SyndEntry> entries = sf.getEntries();
		for (final SyndEntry se : entries) {
			for(final SyndContent sc: se.getContents()) {
				Assert.assertEquals("module.item.encoded.type", ct, sc.getType());
			}
		}
	}
	void verifyItunesModule(SyndFeed sf) {
		final Module mx = sf.getModule(ItunesModule.URI);
		Assert.assertNotNull("module.feed", mx);
		Assert.assertTrue("module.feed is ItunesModule", mx instanceof ItunesModule);
		final ItunesModule fcm = (ItunesModule)mx;
		Assert.assertNotNull("module.feed.subtitle", fcm.getSubtitle());
		final String[] fkwd = fcm.parseKeywords();
		Assert.assertNotNull("module.feed.keywords", fkwd);
		final List<SyndEntry> entries = sf.getEntries();
		for (final SyndEntry se : entries) {
			final Module mxx = se.getModule(ItunesModule.URI);
			Assert.assertNotNull("module.item", mxx);
			Assert.assertTrue("module.item is ItunesModule", mxx instanceof ItunesModule);
			final ItunesModule cm = (ItunesModule)mxx;
			Assert.assertNotNull("module.item.subtitle", cm.getSubtitle());
			Assert.assertNotNull("module.item.author", cm.getAuthor());
			final SyndContent sc = cm.getSummary();
			Assert.assertNotNull("module.item.summary", sc);
			Assert.assertNotNull("module.item.summary.value", sc.getValue());
			final String[] ikwd = cm.parseKeywords();
			Assert.assertNotNull("module.item.keywords", ikwd);
		}
	}
	@SuppressWarnings("unchecked")
	void verifyForeignMarkup(SyndFeed sf, boolean enabled) {
		final List<SyndForeignMarkup> fscs = (List<SyndForeignMarkup>) sf.getForeignMarkup();
		if(enabled)
			Assert.assertNotSame("foreign.feed.size", fscs.size(), 0);
		else
			Assert.assertSame("foreign.feed.size", fscs.size(), 0);
		final List<SyndEntry> entries = sf.getEntries();
		for (final SyndEntry se : entries) {
			final List<SyndForeignMarkup> scs = (List<SyndForeignMarkup>) se.getForeignMarkup();
			if(enabled)
				Assert.assertNotSame("foreign.item.size", scs.size(), 0);
			else
				Assert.assertSame("foreign.item.size", scs.size(), 0);
		}
	}
	SyndFeed coreCase(String filename) throws Exception {
		final InputStream ss = new FileInputStream(filename);
		final DefaultParseContext dpc = new DefaultParseContext(ctx);
		final SyndFeed sf = dpc.parse(new InputSource(ss));
		Assert.assertSame("dpc.getReport.size", 0, dpc.getReport().size());
		return sf;
	}
	SyndFeed coreCase(String filename, DefaultParseContext.Parse px) throws Exception {
		final InputStream ss = new FileInputStream(filename);
		final DefaultParseContext dpc = new DefaultParseContext(ctx, px);
		final SyndFeed sf = dpc.parse(new InputSource(ss));
		Assert.assertSame("dpc.getReport.size", 0, dpc.getReport().size());
		return sf;
	}
	SyndFeed coreCase(String filename, HashMap<String,Object> fx) throws Exception {
		final InputStream ss = new FileInputStream(filename);
		final DefaultParseContext dpc = new DefaultParseContext(ctx, fx);
		final SyndFeed sf = dpc.parse(new InputSource(ss));
		Assert.assertSame("dpc.getReport.size", 0, dpc.getReport().size());
		return sf;
	}
	static final String RSS20 = "rss_2.0";
	static final String RSSANY = "rss_any";
	static final String ATOM10 = "atom_1.0";
	static final String ATOM10ENTRY = ATOM10 + ";type=entry";
	static final String SEARCHSUGGESTION20 = "ss_2.0";
	static final String OPML20 = "opml_2.0";
	@Test
	public void rss20() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_1.xml");
		verifyResults(sf, RSS20);
		Assert.assertNotNull("sf.updatedDate", sf.getUpdatedDate());
		verifyImage(sf);
		final SyndFeed sf2 = coreCase(PATH + "feed_2.xml");
		verifyResults(sf2, RSS20);
		Assert.assertNotNull("sf.updatedDate", sf.getUpdatedDate());
		verifyImage(sf);
	}
	@Test
	public void rssAny() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_8.xml");
		verifyFeedResults(sf, RSSANY + "/0.91");
		verifyImage(sf);
	}
	@Test
	public void rssAnyRdf() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_10.xml");
		verifyResults(sf, RSSANY + "/1.0.rdf");
		verifyImage(sf);
		final SyndFeed sf2 = coreCase(PATH + "feed_11.xml");
		verifyResults(sf2, RSSANY + "/1.0.rdf");
	}
	@Test
	public void rssEnclosures() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_6.xml");
		verifyResults(sf, RSS20);
		verifyEnclosures(sf);
	}
	@Test
	public void rssXR() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_1.xml", new DefaultParseContext.ParseXMLReader());
		verifyResults(sf, RSS20);
		Assert.assertNotNull("sf.updatedDate", sf.getUpdatedDate());
	}
	@Test
	public void rssXPP() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_1.xml", new DefaultParseContext.ParseXMLPullParser());
		verifyResults(sf, RSS20);
		Assert.assertNotNull("sf.updatedDate", sf.getUpdatedDate());
	}
	@Test
	public void rssEnclosuresAndCategories() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_4.xml");
		verifyResults(sf, RSS20);
		Assert.assertTrue("sf.description", sf.getDescription() != null || sf.getDescriptionEx() != null);
		verifyEnclosures(sf);
		verifyCategories(sf, false);
		verifyImage(sf);
	}
	@Test
	public void atom() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_7.xml");
		verifyResults(sf, ATOM10);
		verifyCategories(sf, false);
		verifyContentType(sf, "html");
		verifyAuthors(sf);
	}
	@Test
	public void atom2() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_9.xml");
		verifyResults(sf, ATOM10);
		verifyCategories(sf, false);
		verifyContentType(sf, "html");
		verifyEnclosures(sf);
	}
	@Test
	public void atomDCModule() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_13.xml");
		verifyResults(sf, ATOM10);
		verifyDCModule(sf);
	}
	@Test
	public void atomDCModuleTerms() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_14.xml");
		verifyResults(sf, ATOM10);
		verifyDCTermsModule(sf);
	}
	@Test
	public void atomRFC4287() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_14.xml");
		verifyResults(sf, ATOM10);
		verifyRFC4287(sf);
	}
	@Test
	public void atomEntryDocument() throws Exception {
		final SyndFeed sf = coreCase(PATH + "atomentry_1.xml");
		//Assert.assertNotNull("sfi.parse", sf);
		// entry feed does not populate the SyndFeed other than Feed Type
		//Assert.assertEquals("sfi.feedType", ATOM10ENTRY, sf.getFeedType());
		//verifyEntries(sf);
		verifyResults(sf, ATOM10ENTRY);
		Assert.assertEquals("entries.size", 1, sf.getEntries().size());
	}
	@Test
	public void atomSummary() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_12.xml");
		verifyResults(sf, ATOM10);
		verifyCategories(sf, false);
		verifyContentType(sf, "html");
	}
	@Test
	public void DCSYModules() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_3.xml");
		verifyResults(sf, RSS20);
		verifyDCModule(sf);
		verifySYModule(sf);
	}
	@Test
	public void ForeignMarkupEnabled() throws Exception {
		final HashMap<String,Object> fx = new HashMap<String,Object>();
		fx.put(ParseContext.Features.FOREIGN_MARKUP_ENTRY, ParseContext.Features.EMPTY);
		fx.put(ParseContext.Features.FOREIGN_MARKUP_FEED, ParseContext.Features.EMPTY);
		final SyndFeed sf = coreCase(PATH + "feed_3.xml", fx);
		verifyForeignMarkup(sf, true);
	}
	@Test
	public void ForeignMarkupDisabled() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_3.xml");
		verifyForeignMarkup(sf, false);
	}
	@Test
	public void ContentModule() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_5.xml");
		verifyResults(sf, RSS20);
		verifyContentModule(sf);
	}
	@Test
	public void ItunesModule() throws Exception {
		final SyndFeed sf = coreCase(PATH + "feed_5.xml");
		verifyResults(sf, RSS20);
		verifyItunesModule(sf);
	}
	@Test
	public void searchSuggestions20() throws Exception {
		final SyndFeed sf = coreCase(PATH + "searchsuggestion_1.xml");
		Assert.assertEquals("tag", SEARCHSUGGESTION20, sf.getFeedType());
		Assert.assertNotSame("entries.size()", 0, sf.getEntries().size());
		int ix = 0;
		for (final SyndEntry se : sf.getEntries()) {
			Assert.assertNotNull("se.title" + ix, se.getTitle());
			Assert.assertNotNull("se.description" + ix, se.getDescription());
			Assert.assertTrue("se.link" + ix, se.getLink() != null);
			Assert.assertTrue("se is SyndEntry2", se instanceof SyndEntry2);
			final SyndEntry2 se2 = (SyndEntry2)se;
			Assert.assertTrue("se2.image" + ix, se2.getImage() != null);
			final SyndImage si = se2.getImage();
			Assert.assertTrue("si.url" + ix, si.getUrl() != null);
			Assert.assertTrue("si is SyndImage2", si instanceof SyndImage2);
			final SyndImage2 si2 = (SyndImage2)si;
			Assert.assertTrue("si2.width" + ix, si2.getWidth() > 0);
			Assert.assertTrue("si2.height" + ix, si2.getHeight() > 0);
			ix++;
		}
	}
	@Test
	public void searchSuggestionsMS() throws Exception {
		final SyndFeed sf = coreCase(PATH + "searchsuggestion_2.xml");
		Assert.assertEquals("tag", SEARCHSUGGESTION20, sf.getFeedType());
		Assert.assertNotSame("entries.size()", 0, sf.getEntries().size());
		int ix = 0;
		for (final SyndEntry se : sf.getEntries()) {
			Assert.assertNotNull("se.title" + ix, se.getTitle());
			Assert.assertNotNull("se.description" + ix, se.getDescription());
			Assert.assertTrue("se.link" + ix, se.getLink() != null);
			Assert.assertTrue("se is SyndEntry2", se instanceof SyndEntry2);
			final SyndEntry2 se2 = (SyndEntry2)se;
			Assert.assertTrue("se2.image" + ix, se2.getImage() != null);
			final SyndImage si = se2.getImage();
			Assert.assertTrue("si.url" + ix, si.getUrl() != null);
			Assert.assertTrue("si is SyndImage2", si instanceof SyndImage2);
			final SyndImage2 si2 = (SyndImage2)si;
			Assert.assertTrue("si2.width" + ix, si2.getWidth() > 0);
			Assert.assertTrue("si2.height" + ix, si2.getHeight() > 0);
			ix++;
		}
	}
	@Test
	public void opml20() throws Exception {
		final SyndFeed sf = coreCase(PATH + "opml_1.opml");
		Assert.assertEquals("tag", OPML20, sf.getFeedType());
		Assert.assertNotSame("entries.size()", 0, sf.getEntries().size());
		Assert.assertTrue("sf.titleEx", sf.getTitleEx() != null);
		Assert.assertNotNull("sf.publishedDate", sf.getPublishedDate());
		Assert.assertNotNull("sf.updatedDate", sf.getUpdatedDate());
		int ix = 0;
		for (final SyndEntry se : sf.getEntries()) {
			Assert.assertNotNull("se.title" + ix, se.getTitle());
			Assert.assertNotNull("se.description" + ix, se.getDescription());
			Assert.assertTrue("se.links" + ix, se.getLinks() != null && se.getLinks().size() > 0);
			final SyndLink sl = se.getLinks().get(0);
			Assert.assertEquals("se.links[0].rel" + ix, "outline", sl.getRel());
			Assert.assertNotNull("se.links[0].href" + ix, sl.getHref());
			Assert.assertNotNull("se.links[0].type" + ix, sl.getType());
			ix++;
		}
	}
	@Test
	public void opml20Gpodder() throws Exception {
		final SyndFeed sf = coreCase(PATH + "opml_2.opml");
		Assert.assertEquals("tag", OPML20, sf.getFeedType());
		Assert.assertNotSame("entries.size()", 0, sf.getEntries().size());
		Assert.assertTrue("sf.titleEx", sf.getTitleEx() != null);
		Assert.assertNotNull("sf.publishedDate", sf.getPublishedDate());
		Assert.assertNotNull("sf.updatedDate", sf.getUpdatedDate());
		int ix = 0;
		for (final SyndEntry se : sf.getEntries()) {
			Assert.assertNotNull("se.title" + ix, se.getTitle());
			Assert.assertNotNull("se.description" + ix, se.getDescription());
			Assert.assertTrue("se.links" + ix, se.getLinks() != null && se.getLinks().size() > 0);
			final SyndLink sl = se.getLinks().get(0);
			Assert.assertEquals("se.links[0].rel" + ix, "outline", sl.getRel());
			Assert.assertNotNull("se.links[0].href" + ix, sl.getHref());
			Assert.assertNotNull("se.links[0].type" + ix, sl.getType());
			ix++;
		}
	}
	@Test
	public void mediaRssArchiveDotOrg() throws Exception {
		final SyndFeed sf = coreCase(PATH + "mediarss_1.xml");
		verifyResults(sf, RSS20);
		verifyMediaRssModule(sf);
	}
}
