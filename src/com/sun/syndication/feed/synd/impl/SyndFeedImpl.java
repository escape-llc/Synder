/*
 * Copyright 2004 Sun Microsystems, Inc.
 * Copyright 2010 eScape Technology LLC
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
 *
 */
package com.sun.syndication.feed.synd.impl;

import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.impl.CopyFromHelper;
import com.sun.syndication.feed.WireFeed;
import com.sun.syndication.feed.module.*;
import com.sun.syndication.feed.module.Module;
import com.sun.syndication.feed.module.impl.ContentModuleImpl;
import com.sun.syndication.feed.module.impl.DCModuleImpl;
import com.sun.syndication.feed.module.impl.DCTermsModuleImpl;
import com.sun.syndication.feed.module.impl.ItunesModuleImpl;
import com.sun.syndication.feed.module.impl.ModuleUtils;
import com.sun.syndication.feed.module.impl.SyModuleImpl;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndPerson;

import java.util.*;
import java.io.Serializable;

/**
 * Bean for all types of feeds.
 * <p>
 * @author Alejandro Abdelnur
 * @author escape-llc
 */
public class SyndFeedImpl implements Serializable, SyndFeed {
	static final int CAPACITY = 4;
	private static final long serialVersionUID = 1L;
	private String _encoding;
	private String _uri;
	private SyndContent _title;
	private SyndContent _description;
	private String _feedType;
	private List<SyndLink> _links;
	private SyndImage _image;
	private SyndImage _logo;
	private SyndLink _generator;
	private SyndContent _rights;
	private List<SyndEntry> _entries;
	private List<SyndCategory> _categories;
	private List<Module> _modules;
	private List<SyndPerson> _authors;
	private List<SyndPerson> _contributors;
	private List<Object> _foreignMarkup;
	private Date _pubDate;
	private Date _updateDate;
	private String _language;
	private String _copyright;

	private final Class<?> beanClass;
	private final Set<String> convenienceProperties;
	private ObjectBean _objBean;

	private static final Set<String> IGNORE_PROPERTIES;

	/**
	 * Unmodifiable Set containing the convenience properties of this class.
	 * <p>
	 * Convenience properties are mapped to Modules, for cloning the convenience
	 * properties can be ignored as they will be copied as part of the module
	 * cloning.
	 */
	public static final Set<String> CONVENIENCE_PROPERTIES;
	private static volatile CopyFromHelper COPY_FROM_HELPER;
	private static final Object cflock;
	static {
		cflock = new Object();
		IGNORE_PROPERTIES = new HashSet<String>();
		CONVENIENCE_PROPERTIES = Collections.unmodifiableSet(IGNORE_PROPERTIES);
	}
	/**
	 * For implementations extending SyndFeedImpl to be able to use the ObjectBean
	 * functionality with extended interfaces.
	 * <p>
	 * @param beanClass
	 * @param convenienceProperties
	 *          set containing the convenience properties of the SyndEntryImpl
	 *          (the are ignored during cloning, check CloneableBean for details).
	 */
	protected SyndFeedImpl(Class<?> beanClass, Set<String> convenienceProperties) {
		this.beanClass = beanClass;
		this.convenienceProperties = convenienceProperties;
	}
	/**
	 * Default constructor. All properties are set to <b>null</b>.
	 * <p>
	 */
	public SyndFeedImpl() {
		this(SyndFeed.class, IGNORE_PROPERTIES);
	}
	private ObjectBean getObjBean() {
		if (_objBean == null)
			_objBean = new ObjectBean(beanClass, this, convenienceProperties);
		return _objBean;
	}
	/**
	 * Creates a deep 'bean' clone of the object.
	 * <p>
	 * @return a clone of the object.
	 * @throws CloneNotSupportedException
	 *           thrown if an element of the object cannot be cloned.
	 */
	public Object clone() throws CloneNotSupportedException {
		return getObjBean().clone();
	}
	/**
	 * Indicates whether some other object is "equal to" this one as defined by
	 * the Object equals() method.
	 * <p>
	 * @param other
	 *          he reference object with which to compare.
	 * @return <b>true</b> if 'this' object is equal to the 'other' object.
	 */
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		boolean ret = getObjBean().equals(other);
		return ret;
	}
	/**
	 * Returns a hashcode value for the object.
	 * <p>
	 * It follows the contract defined by the Object hashCode() method.
	 * <p>
	 * @return the hashcode of the bean object.
	 */
	public int hashCode() {
		return getObjBean().hashCode();
	}
	/**
	 * Returns the String representation for the object.
	 * <p>
	 * @return String representation for the object.
	 */
	public String toString() {
		return getObjBean().toString();
	}
	public boolean isPreservingWireFeed() {
		return false;
	}
	/**
	 * Creates a feed containing the information of the SyndFeedImpl.
	 * <p>
	 * The feed type of the created WireFeed is taken from the SyndFeedImpl
	 * feedType property.
	 * <p>
	 * <b>Not Implemented</b>
	 * <p>
	 * @return NULL.
	 */
	public WireFeed createWireFeed() {
		return null;
	}
	/**
	 * Creates a feed containing the information of the SyndFeedImpl.
	 * <p>
	 * <b>Not Implemented</b>
	 * <p>
	 * @param feedType
	 *          the feed type for the WireFeed to be created.
	 * @return NULL.
	 */
	public WireFeed createWireFeed(String feedType) {
		if (feedType == null) {
			throw new IllegalArgumentException("Feed type cannot be null");
		}
		return null;
	}
	/**
	 * Returns the WireFeed this SyndFeed was created from. Will return null if
	 * the original feed is not stored or if this SyndFeed was not created from a
	 * WireFeed.
	 * <p>
	 * Note: The wire feed returned here will NOT contain any modifications done
	 * on this SyndFeed since it was created.
	 * <p>
	 * <b>Not Implemented</b>
	 * <p>
	 * @return NULL
	 */
	public WireFeed originalWireFeed() {
		return null;
	}
	/**
	 * Returns the feed type.
	 * <p>
	 * @return the feed type, <b>null</b> if none.
	 */
	public String getFeedType() {
		return _feedType;
	}
	/**
	 * Sets the wire feed type the feed.
	 * <p>
	 * @param feedType
	 *          the feed type to set, <b>null</b> if none.
	 */
	public void setFeedType(String feedType) {
		_feedType = feedType;
	}
	/**
	 * Returns the charset encoding of a the feed. This is not set by parsers.
	 * <p>
	 * @return the charset encoding of the feed.
	 */
	public String getEncoding() {
		return _encoding;
	}
	/**
	 * Sets the charset encoding of a the feed. This is not set by parsers.
	 * <p>
	 * @param encoding
	 *          the charset encoding of the feed.
	 */
	public void setEncoding(String encoding) {
		_encoding = encoding;
	}
	/**
	 * Returns the feed URI.
	 * <p>
	 * How the feed URI maps to a concrete feed type (RSS or Atom) depends on the
	 * concrete feed type. This is explained in detail in Rome documentation, <a
	 * href="http://wiki.java.net/bin/edit/Javawsxml/Rome04URIMapping">Feed and
	 * entry URI mapping</a>.
	 * <p>
	 * The returned URI is a normalized URI as specified in RFC 2396bis.
	 * <p>
	 * Note: The URI is the unique identifier, in the RSS 2.0/atom case this is
	 * the GUID, for RSS 1.0 this is the URI attribute of the item. The Link is
	 * the URL that the item is accessible under, the URI is the permanent
	 * identifier which the aggregator should use to reference this item. Often
	 * the URI will use some standardized identifier scheme such as DOI's so that
	 * items can be identified even if they appear in multiple feeds with
	 * different "links" (they might be on different hosting platforms but be the
	 * same item). Also, though rare, there could be multiple items with the same
	 * link but a different URI and associated metadata which need to be treated
	 * as distinct entities. In the RSS 1.0 case the URI must be a valid RDF URI
	 * reference.
	 * <p>
	 * @return the feed URI, <b>null</b> if none.
	 */
	public String getUri() {
		return _uri;
	}
	/**
	 * Sets the feed URI.
	 * <p>
	 * How the feed URI maps to a concrete feed type (RSS or Atom) depends on the
	 * concrete feed type. This is explained in detail in Rome documentation, <a
	 * href="http://wiki.java.net/bin/edit/Javawsxml/Rome04URIMapping">Feed and
	 * entry URI mapping</a>.
	 * <p>
	 * Note: The URI is the unique identifier, in the RSS 2.0/atom case this is
	 * the GUID, for RSS 1.0 this is the URI attribute of the item. The Link is
	 * the URL that the item is accessible under, the URI is the permanent
	 * identifier which the aggregator should use to reference this item. Often
	 * the URI will use some standardized identifier scheme such as DOI's so that
	 * items can be identified even if they appear in multiple feeds with
	 * different "links" (they might be on different hosting platforms but be the
	 * same item). Also, though rare, there could be multiple items with the same
	 * link but a different URI and associated metadata which need to be treated
	 * as distinct entities. In the RSS 1.0 case the URI must be a valid RDF URI
	 * reference.
	 * <p>
	 * @param uri
	 *          the feed URI to set, <b>null</b> if none.
	 */
	public void setUri(String uri) {
		_uri = URINormalizer.normalize(uri);
	}

	/**
	 * Returns the feed title.
	 * <p>
	 * @return the feed title, <b>null</b> if none.
	 */
	public String getTitle() {
		if (_title != null)
			return _title.getValue();
		return null;
	}
	/**
	 * Sets the feed title.  Creates <b>SyndContent</b> if necessary.
	 * <p>
	 * @param title
	 *          the feed title to set, <b>null</b> if none.
	 */
	public void setTitle(String title) {
		if (_title == null) {
			_title = new SyndContentImpl();
			_title.setType("text/*");
		}
		_title.setValue(title);
	}
	/**
	 * Returns the feed title as a text construct.
	 * <p>
	 * @return the feed title, <b>null</b> if none.
	 */
	public SyndContent getTitleEx() {
		return _title;
	}
	/**
	 * Sets the feed title as a text construct.
	 * <p>
	 * @param title
	 *          the feed title to set, <b>null</b> if none.
	 */
	public void setTitleEx(SyndContent title) {
		_title = title;
	}
	/**
	 * Returns the feed link.  If multiple, returns the first one.
	 * <p>
	 * Note: The URI is the unique identifier, in the RSS 2.0/atom case this is
	 * the GUID, for RSS 1.0 this is the URI attribute of the item. The Link is
	 * the URL that the item is accessible under, the URI is the permanent
	 * identifier which the aggregator should use to reference this item. Often
	 * the URI will use some standardized identifier scheme such as DOI's so that
	 * items can be identified even if they appear in multiple feeds with
	 * different "links" (they might be on different hosting platforms but be the
	 * same item). Also, though rare, there could be multiple items with the same
	 * link but a different URI and associated metadata which need to be treated
	 * as distinct entities. In the RSS 1.0 case the URI must be a valid RDF URI
	 * reference.
	 * <p>
	 * @return the feed link, <b>null</b> if none.
	 */
	public String getLink() {
		return _links != null && _links.size() > 0 ? _links.get(0).getHref() : null;
	}
	/**
	 * Sets the feed link.
	 * <p>
	 * Note: The URI is the unique identifier, in the RSS 2.0/atom case this is
	 * the GUID, for RSS 1.0 this is the URI attribute of the item. The Link is
	 * the URL that the item is accessible under, the URI is the permanent
	 * identifier which the aggregator should use to reference this item. Often
	 * the URI will use some standardized identifier scheme such as DOI's so that
	 * items can be identified even if they appear in multiple feeds with
	 * different "links" (they might be on different hosting platforms but be the
	 * same item). Also, though rare, there could be multiple items with the same
	 * link but a different URI and associated metadata which need to be treated
	 * as distinct entities. In the RSS 1.0 case the URI must be a valid RDF URI
	 * reference.
	 * <p>
	 * @param link
	 *          the feed link to set, <b>null</b> if none.
	 */
	public void setLink(String link) {
		final SyndLinkImpl sli = new SyndLinkImpl();
		sli.setHref(link);
		getLinks().add(sli);
	}
	/**
	 * Returns the feed description.
	 * <p>
	 * @return the feed description, <b>null</b> if none.
	 */
	public String getDescription() {
		if (_description != null)
			return _description.getValue();
		return null;
	}
	/**
	 * Sets the feed description.  Creates <b>SyndContent</b> if necessary.
	 * <p>
	 * @param description
	 *          the feed description to set, <b>null</b> if none.
	 */
	public void setDescription(String description) {
		if (_description == null) {
			_description = new SyndContentImpl();
			_description.setType("text/*");
		}
		_description.setValue(description);
	}
	/**
	 * Returns the feed description as a text construct.
	 * <p>
	 * @return the feed description, <b>null</b> if none.
	 */
	public SyndContent getDescriptionEx() {
		return _description;
	}
	/**
	 * Sets the feed description as a text construct.
	 * <p>
	 * @param description
	 *          the feed description to set, <b>null</b> if none.
	 */
	public void setDescriptionEx(SyndContent description) {
		_description = description;
	}
	/**
	 * Returns the feed published date.
	 * <p>
	 * @return the feed published date, <b>null</b> if none.
	 */
	public Date getPublishedDate() {
		return _pubDate;
	}
	/**
	 * Sets the feed published date.
	 * <p>
	 * @param publishedDate
	 *          the feed published date to set, <b>null</b> if none.
	 */
	public void setPublishedDate(Date publishedDate) {
		_pubDate = publishedDate;
	}
	public Date getUpdatedDate() { return _updateDate; }
	public void setUpdatedDate(Date updatedDate) { _updateDate = updatedDate; }
	/**
	 * Returns the feed copyright.
	 * <p>
	 * @return the feed copyright, <b>null</b> if none.
	 */
	public String getCopyright() {
		return _copyright;
	}
	/**
	 * Sets the feed copyright.
	 * <p>
	 * @param copyright
	 *          the feed copyright to set, <b>null</b> if none.
	 */
	public void setCopyright(String copyright) {
		_copyright = copyright;
	}
	/**
	 * Returns the feed image.
	 * <p>
	 * @return the feed image, <b>null</b> if none.
	 */
	public SyndImage getImage() {
		return _image;
	}
	/**
	 * Sets the feed image.
	 * <p>
	 * @param image
	 *          the feed image to set, <b>null</b> if none.
	 */
	public void setImage(SyndImage image) {
		_image = image;
	}
	/**
	 * Returns the feed categories.
	 * <p>
	 * @return a list of SyndCategoryImpl elements with the feed categories, an
	 *         empty list if none.
	 */
	public List<SyndCategory> getCategories() {
		return _categories == null ? (_categories = new ArrayList<SyndCategory>(CAPACITY)) : _categories;
	}
	/**
	 * Sets the feed categories.
	 * <p>
	 * @param categories
	 *          the list of SyndCategoryImpl elements with the feed categories to
	 *          set, an empty list or <b>null</b> if none.
	 */
	public void setCategories(List<SyndCategory> categories) {
		_categories = categories;
	}
	/**
	 * Returns the feed entries.
	 * <p>
	 * @return a list of SyndEntryImpl elements with the feed entries, an empty
	 *         list if none.
	 */
	public List<SyndEntry> getEntries() {
		return (_entries == null) ? (_entries = new ArrayList<SyndEntry>(CAPACITY*2)) : _entries;
	}
	/**
	 * Sets the feed entries.
	 * <p>
	 * @param entries
	 *          the list of SyndEntryImpl elements with the feed entries to set,
	 *          an empty list or <b>null</b> if none.
	 */
	public void setEntries(List<SyndEntry> entries) {
		_entries = entries;
	}
	/**
	 * Returns the feed language.
	 * <p>
	 * @return the feed language, <b>null</b> if none.
	 */
	public String getLanguage() {
		return _language;
	}
	/**
	 * Sets the feed language.
	 * <p>
	 * @param language
	 *          the feed language to set, <b>null</b> if none.
	 */
	public void setLanguage(String language) {
		_language = language;
	}
	/**
	 * Returns the feed modules.
	 * <p>
	 * @return a list of ModuleImpl elements with the feed modules, an empty list
	 *         if none.
	 */
	public List<Module> getModules() {
		if (_modules == null) {
			_modules = new ArrayList<Module>(CAPACITY);
		}
		return _modules;
	}
	/**
	 * Sets the feed modules.
	 * <p>
	 * @param modules
	 *          the list of ModuleImpl elements with the feed modules to set, an
	 *          empty list or <b>null</b> if none.
	 */
	public void setModules(List<Module> modules) {
		_modules = modules;
	}
	/**
	 * Returns the module identified by a given URI.
	 * <p>
	 * @param uri
	 *          the URI of the ModuleImpl.
	 * @return The module with the given URI, <b>null</b> if none.
	 */
	public Module getModule(String uri) {
		return ModuleUtils.getModule(getModules(), uri);
	}
	/**
	 * Returns the links
	 * <p>
	 * @return Returns the links.
	 */
	public List<SyndLink> getLinks() {
		return (_links == null) ? (_links = new ArrayList<SyndLink>(CAPACITY)) : _links;
	}
	/**
	 * Set the links.
	 * <p>
	 * @param links
	 *          The links to set.
	 */
	public void setLinks(List<SyndLink> links) {
		_links = links;
	}
	public List<SyndPerson> getAuthors() {
		return (_authors == null) ? (_authors = new ArrayList<SyndPerson>(CAPACITY)) : _authors;
	}
	public void setAuthors(List<SyndPerson> authors) {
		this._authors = authors;
	}
	/**
	 * Returns the feed author.  If multiple, returns the first one.
	 * @return the feed author, <b>null</b> if none.
	 */
	public String getAuthor() {
		return getAuthors().size() == 0 ? null : getAuthors().get(0).getName();
	}
	/**
	 * Sets the feed author.
	 * <p>
	 * @param author
	 *          the feed author to set, <b>null</b> if none.
	 */
	public void setAuthor(String author) {
		final SyndPersonImpl sp = new SyndPersonImpl();
		sp.setName(author);
		getAuthors().add(sp);
	}
	public List<SyndPerson> getContributors() {
		return (_contributors == null) ? (_contributors = new ArrayList<SyndPerson>(CAPACITY)) : _contributors;
	}
	public void setContributors(List<SyndPerson> contributors) {
		this._contributors = contributors;
	}
	public SyndContent getRights() {
		return _rights;
	}
	public void setRights(SyndContent rights) {
		_rights = rights;
	}
	public SyndLink getGenerator() {
		return _generator;
	}
	public void setGenerator(SyndLink generator) {
		_generator = generator;
	}
	public SyndImage getLogo() {
		return _logo;
	}
	public void setLogo(SyndImage logo) {
		_logo = logo;
	}
	/**
	 * Returns foreign markup found at channel level.
	 * <p>
	 * @return Opaque object
	 */
	public Object getForeignMarkup() {
		return (_foreignMarkup == null) ? (_foreignMarkup = new ArrayList<Object>(CAPACITY)) : _foreignMarkup;
	}
	/**
	 * Sets foreign markup found at channel level.
	 * <p>
	 * @param foreignMarkup
	 *          Opaque object
	 */
	@SuppressWarnings("unchecked")
	public void setForeignMarkup(Object foreignMarkup) {
		_foreignMarkup = (List<Object>) foreignMarkup;
	}
	public Class<?> getInterface() {
		return SyndFeed.class;
	}
	public void copyFrom(Object obj) {
		createCopyFrom().copy(this, obj);
	}
	static CopyFromHelper createCopyFrom() {
		if (COPY_FROM_HELPER == null) {
			synchronized (cflock) {
				if (COPY_FROM_HELPER == null) {
					final Map<String, Class<?>> basePropInterfaceMap = new HashMap<String, Class<?>>();
					basePropInterfaceMap.put("feedType", String.class);
					basePropInterfaceMap.put("encoding", String.class);
					basePropInterfaceMap.put("uri", String.class);
					basePropInterfaceMap.put("title", String.class);
					basePropInterfaceMap.put("link", String.class);
					basePropInterfaceMap.put("description", String.class);
					basePropInterfaceMap.put("image", SyndImage.class);
					basePropInterfaceMap.put("logo", SyndImage.class);
					basePropInterfaceMap.put("entries", SyndEntry.class);
					basePropInterfaceMap.put("modules", Module.class);
					basePropInterfaceMap.put("publishedDate", Date.class);
					basePropInterfaceMap.put("updatedDate", Date.class);
					basePropInterfaceMap.put("author", String.class);
					basePropInterfaceMap.put("copyright", String.class);
					basePropInterfaceMap.put("language", String.class);
					basePropInterfaceMap.put("generator", SyndLink.class);
					basePropInterfaceMap.put("rights", SyndContent.class);

					final Map<Class<?>, Class<?>> basePropClassImplMap = new HashMap<Class<?>, Class<?>>();
					basePropClassImplMap.put(SyndContent.class, SyndContentImpl.class);
					basePropClassImplMap.put(SyndEntry.class, SyndEntryImpl.class);
					basePropClassImplMap.put(SyndImage.class, SyndImageImpl.class);
					basePropClassImplMap.put(SyndLink.class, SyndLinkImpl.class);
					basePropClassImplMap.put(DCModule.class, DCModuleImpl.class);
					basePropClassImplMap.put(DCTermsModule.class, DCTermsModuleImpl.class);
					basePropClassImplMap.put(SyModule.class, SyModuleImpl.class);
					basePropClassImplMap.put(ContentModule.class, ContentModuleImpl.class);
					basePropClassImplMap.put(ItunesModule.class, ItunesModuleImpl.class);

					COPY_FROM_HELPER = new CopyFromHelper(SyndFeed.class, basePropInterfaceMap, basePropClassImplMap);
				}
			}
		}
		return COPY_FROM_HELPER;
	}
}