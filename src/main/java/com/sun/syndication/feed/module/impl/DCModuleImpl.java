/*
 * Copyright 2004 Sun Microsystems, Inc.
 * Enhancements Copyright 2015 eScape Technology LLC.
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
package com.sun.syndication.feed.module.impl;

import com.sun.syndication.feed.impl.CopyFromHelper;
import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.module.DCSubject;

import java.util.*;

/**
 * DCMI Elements module implementation.
 * <p>
 * 
 * @see <a href="http://web.resource.org/rss/1.0/modules/dc/">Dublin Core Elements</a>
 * @author Alejandro Abdelnur
 * @author escape-llc
 *
 */
public class DCModuleImpl extends ModuleImpl implements DCModule {
	private static final long serialVersionUID = 1L;
	protected static final int CAPACITY = 2;
	private ObjectBean _objBean;
	private List<String> _title;
	private List<String> _creator;
	private List<DCSubject> _subject;
	private List<String> _description;
	private List<String> _publisher;
	private List<String> _contributors;
	private List<Date> _date;
	private List<String> _type;
	private List<String> _format;
	private List<String> _identifier;
	private List<String> _source;
	private List<String> _language;
	private List<String> _relation;
	private List<String> _coverage;
	private List<String> _rights;

	/**
	 * Properties to be ignored when cloning.
	 */
	private static final Set<String> IGNORE_PROPERTIES;

	/**
	 * Unmodifiable Set containing the convenience properties of this class.
	 * Convenience properties are mapped to Modules, for cloning the convenience
	 * properties can be ignored as the will be copied as part of the module
	 * cloning.
	 */
	public static final Set<String> CONVENIENCE_PROPERTIES;

	/**
	 * Default constructor. All properties are set to <b>null</b>.
	 *
	 */
	public DCModuleImpl() {
		super(DCModule.class, URI_ELEMENTS);
	}
	/**
	 * Ctor.
	 * Create subclass implementations.
	 * @param clx interface class.
	 * @param uri namespace URI.
	 */
	protected DCModuleImpl(Class<?> clx, String uri) {
		super(clx, uri);
	}
	private ObjectBean getObjBean() {
		if (_objBean == null)
			_objBean = new ObjectBean(beanClass, this, CONVENIENCE_PROPERTIES);
		return _objBean;
	}

	/**
	 * Returns the DublinCore module titles.
	 * @return a list of Strings representing the DublinCore module title, an
	 *         empty list if none.
	 *
	 */
	public List<String> getTitles() { return (_title == null) ? (_title = new ArrayList<String>(CAPACITY)) : _title; }

	/**
	 * Sets the DublinCore module titles.
	 * @param titles
	 *          the list of String representing the DublinCore module titles to
	 *          set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setTitles(List<String> titles) { _title = titles; }

	/**
	 * Gets the DublinCore module title. Convenience method that can be used to
	 * obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module title, <b>null</b> if none.
	 */
	public String getTitle() { return ((_title != null) && (_title.size() > 0)) ? _title.get(0) : null; }

	/**
	 * Sets the DublinCore module title. Convenience method that can be used when
	 * there is only one title to set.
	 * @param title
	 *          the DublinCore module title to set, <b>null</b> if none.
	 *
	 */
	public void setTitle(String title) { getTitles().add(title); }

	/**
	 * Returns the DublinCore module creator.
	 * @return a list of Strings representing the DublinCore module creator, an
	 *         empty list if none.
	 *
	 */
	public List<String> getCreators() { return (_creator == null) ? (_creator = new ArrayList<String>(CAPACITY)) : _creator; }

	/**
	 * Sets the DublinCore module creators.
	 * @param creators
	 *          the list of String representing the DublinCore module creators to
	 *          set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setCreators(List<String> creators) { _creator = creators; }

	/**
	 * Gets the DublinCore module title. Convenience method that can be used to
	 * obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module title, <b>null</b> if none.
	 */
	public String getCreator() { return ((_creator != null) && (_creator.size() > 0)) ? _creator.get(0) : null; }

	/**
	 * Sets the DublinCore module creator. Convenience method that can be used
	 * when there is only one creator to set.
	 * @param creator
	 *          the DublinCore module creator to set, <b>null</b> if none.
	 *
	 */
	public void setCreator(String creator) { getCreators().add(creator); }

	/**
	 * Returns the DublinCore module subjects.
	 * @return a list of DCSubject elements with the DublinCore module subjects,
	 *         an empty list if none.
	 *
	 */
	public List<DCSubject> getSubjects() { return (_subject == null) ? (_subject = new ArrayList<DCSubject>(CAPACITY)) : _subject; }

	/**
	 * Sets the DublinCore module subjects.
	 * @param subjects
	 *          the list of DCSubject elements with the DublinCore module subjects
	 *          to set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setSubjects(List<DCSubject> subjects) { _subject = subjects; }

	/**
	 * Gets the DublinCore module subject. Convenience method that can be used to
	 * obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module subject, <b>null</b> if none.
	 */
	public DCSubject getSubject() { return ((_subject != null) && (_subject.size() > 0)) ? _subject.get(0) : null; }

	/**
	 * Sets the DCSubject element. Convenience method that can be used when there
	 * is only one subject to set.
	 * @param subject
	 *          the DublinCore module subject to set, <b>null</b> if none.
	 *
	 */
	public void setSubject(DCSubject subject) { getSubjects().add(subject); }

	/**
	 * Returns the DublinCore module description.
	 * @return a list of Strings representing the DublinCore module description,
	 *         an empty list if none.
	 *
	 */
	public List<String> getDescriptions() { return (_description == null) ? (_description = new ArrayList<String>(CAPACITY)) : _description; }

	/**
	 * Sets the DublinCore module descriptions.
	 * @param descriptions
	 *          the list of String representing the DublinCore module descriptions
	 *          to set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setDescriptions(List<String> descriptions) { _description = descriptions; }

	/**
	 * Gets the DublinCore module description. Convenience method that can be used
	 * to obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module description, <b>null</b> if none.
	 */
	public String getDescription() { return ((_description != null) && (_description.size() > 0)) ? _description.get(0) : null; }

	/**
	 * Sets the DublinCore module description. Convenience method that can be used
	 * when there is only one description to set.
	 * @param description
	 *          the DublinCore module description to set, <b>null</b> if none.
	 *
	 */
	public void setDescription(String description) { getDescriptions().add(description); }

	/**
	 * Returns the DublinCore module publisher.
	 * @return a list of Strings representing the DublinCore module publisher, an
	 *         empty list if none.
	 *
	 */
	public List<String> getPublishers() { return (_publisher == null) ? (_publisher = new ArrayList<String>(CAPACITY)) : _publisher; }

	/**
	 * Sets the DublinCore module publishers.
	 * @param publishers
	 *          the list of String representing the DublinCore module publishers
	 *          to set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setPublishers(List<String> publishers) { _publisher = publishers; }

	/**
	 * Gets the DublinCore module title. Convenience method that can be used to
	 * obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module title, <b>null</b> if none.
	 */
	public String getPublisher() { return ((_publisher != null) && (_publisher.size() > 0)) ? _publisher.get(0) : null; }

	/**
	 * Sets the DublinCore module publisher. Convenience method that can be used
	 * when there is only one publisher to set.
	 * @param publisher
	 *          the DublinCore module publisher to set, <b>null</b> if none.
	 *
	 */
	public void setPublisher(String publisher) { getPublishers().add(publisher); }

	/**
	 * Returns the DublinCore module contributor.
	 * @return a list of Strings representing the DublinCore module contributor,
	 *         an empty list if none.
	 *
	 */
	public List<String> getContributors() { return (_contributors == null) ? (_contributors = new ArrayList<String>(CAPACITY)) : _contributors; }

	/**
	 * Sets the DublinCore module contributors.
	 * @param contributors
	 *          the list of String representing the DublinCore module contributors
	 *          to set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setContributors(List<String> contributors) { _contributors = contributors; }

	/**
	 * Gets the DublinCore module contributor. Convenience method that can be used
	 * to obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module contributor, <b>null</b> if none.
	 */
	public String getContributor() { return ((_contributors != null) && (_contributors.size() > 0)) ? _contributors.get(0) : null; }

	/**
	 * Sets the DublinCore module contributor. Convenience method that can be used
	 * when there is only one contributor to set.
	 * @param contributor
	 *          the DublinCore module contributor to set, <b>null</b> if none.
	 *
	 */
	public void setContributor(String contributor) { getContributors().add(contributor); }

	/**
	 * Returns the DublinCore module date.
	 * @return a list of Strings representing the DublinCore module date, an empty
	 *         list if none.
	 *
	 */
	public List<Date> getDates() { return (_date == null) ? (_date = new ArrayList<Date>(CAPACITY)) : _date; }

	/**
	 * Sets the DublinCore module dates.
	 * @param dates
	 *          the list of Date representing the DublinCore module dates to set,
	 *          an empty list or <b>null</b> if none.
	 *
	 */
	public void setDates(List<Date> dates) { _date = dates; }

	/**
	 * Gets the DublinCore module date. Convenience method that can be used to
	 * obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module date, <b>null</b> if none.
	 */
	public Date getDate() { return ((_date != null) && (_date.size() > 0)) ? _date.get(0) : null; }

	/**
	 * Sets the DublinCore module date. Convenience method that can be used when
	 * there is only one date to set.
	 * @param date
	 *          the DublinCore module date to set, <b>null</b> if none.
	 *
	 */
	public void setDate(Date date) { getDates().add(date); }

	/**
	 * Returns the DublinCore module type.
	 * @return a list of Strings representing the DublinCore module type, an empty
	 *         list if none.
	 *
	 */
	public List<String> getTypes() { return (_type == null) ? (_type = new ArrayList<String>(CAPACITY)) : _type; }

	/**
	 * Sets the DublinCore module types.
	 * @param types
	 *          the list of String representing the DublinCore module types to
	 *          set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setTypes(List<String> types) { _type = types; }

	/**
	 * Gets the DublinCore module type. Convenience method that can be used to
	 * obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module type, <b>null</b> if none.
	 */
	public String getType() { return ((_type != null) && (_type.size() > 0)) ? _type.get(0) : null; }

	/**
	 * Sets the DublinCore module type. Convenience method that can be used when
	 * there is only one type to set.
	 * @param type
	 *          the DublinCore module type to set, <b>null</b> if none.
	 *
	 */
	public void setType(String type) { getTypes().add(type); }

	/**
	 * Returns the DublinCore module format.
	 * @return a list of Strings representing the DublinCore module format, an
	 *         empty list if none.
	 *
	 */
	public List<String> getFormats() { return (_format == null) ? (_format = new ArrayList<String>(CAPACITY)) : _format; }

	/**
	 * Sets the DublinCore module formats.
	 * @param formats
	 *          the list of String representing the DublinCore module formats to
	 *          set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setFormats(List<String> formats) { _format = formats; }

	/**
	 * Gets the DublinCore module format. Convenience method that can be used to
	 * obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module format, <b>null</b> if none.
	 */
	public String getFormat() { return ((_format != null) && (_format.size() > 0)) ? _format.get(0) : null; }

	/**
	 * Sets the DublinCore module format. Convenience method that can be used when
	 * there is only one format to set.
	 * @param format
	 *          the DublinCore module format to set, <b>null</b> if none.
	 *
	 */
	public void setFormat(String format) { getFormats().add(format); }

	/**
	 * Returns the DublinCore module identifier.
	 * @return a list of Strings representing the DublinCore module identifier, an
	 *         empty list if none.
	 *
	 */
	public List<String> getIdentifiers() { return (_identifier == null) ? (_identifier = new ArrayList<String>(CAPACITY)) : _identifier; }

	/**
	 * Sets the DublinCore module identifiers.
	 * @param identifiers
	 *          the list of String representing the DublinCore module identifiers
	 *          to set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setIdentifiers(List<String> identifiers) { _identifier = identifiers; }

	/**
	 * Gets the DublinCore module identifier. Convenience method that can be used
	 * to obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module identifier, <b>null</b> if none.
	 */
	public String getIdentifier() { return ((_identifier != null) && (_identifier.size() > 0)) ? _identifier.get(0) : null; }

	/**
	 * Sets the DublinCore module identifier. Convenience method that can be used
	 * when there is only one identifier to set.
	 * @param identifier
	 *          the DublinCore module identifier to set, <b>null</b> if none.
	 *
	 */
	public void setIdentifier(String identifier) { getIdentifiers().add(identifier); }

	/**
	 * Returns the DublinCore module source.
	 * @return a list of Strings representing the DublinCore module source, an
	 *         empty list if none.
	 *
	 */
	public List<String> getSources() { return (_source == null) ? (_source = new ArrayList<String>(CAPACITY)) : _source; }

	/**
	 * Sets the DublinCore module sources.
	 * @param sources
	 *          the list of String representing the DublinCore module sources to
	 *          set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setSources(List<String> sources) { _source = sources; }

	/**
	 * Gets the DublinCore module source. Convenience method that can be used to
	 * obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module source, <b>null</b> if none.
	 */
	public String getSource() { return ((_source != null) && (_source.size() > 0)) ? _source.get(0) : null; }

	/**
	 * Sets the DublinCore module source. Convenience method that can be used when
	 * there is only one source to set.
	 * @param source
	 *          the DublinCore module source to set, <b>null</b> if none.
	 *
	 */
	public void setSource(String source) { getSources().add(source); }

	/**
	 * Returns the DublinCore module language.
	 * @return a list of Strings representing the DublinCore module language, an
	 *         empty list if none.
	 *
	 */
	public List<String> getLanguages() { return (_language == null) ? (_language = new ArrayList<String>(CAPACITY)) : _language; }

	/**
	 * Sets the DublinCore module languages.
	 * @param languages
	 *          the list of String representing the DublinCore module languages to
	 *          set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setLanguages(List<String> languages) { _language = languages; }

	/**
	 * Gets the DublinCore module language. Convenience method that can be used to
	 * obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module langauge, <b>null</b> if none.
	 */
	public String getLanguage() { return ((_language != null) && (_language.size() > 0)) ? _language.get(0) : null; }

	/**
	 * Sets the DublinCore module language. Convenience method that can be used
	 * when there is only one language to set.
	 * @param language
	 *          the DublinCore module language to set, <b>null</b> if none.
	 *
	 */
	public void setLanguage(String language) { getLanguages().add(language); }

	/**
	 * Returns the DublinCore module relation.
	 * @return a list of Strings representing the DublinCore module relation, an
	 *         empty list if none.
	 *
	 */
	public List<String> getRelations() { return (_relation == null) ? (_relation = new ArrayList<String>(CAPACITY)) : _relation; }

	/**
	 * Sets the DublinCore module relations.
	 * @param relations
	 *          the list of String representing the DublinCore module relations to
	 *          set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setRelations(List<String> relations) { _relation = relations; }

	/**
	 * Gets the DublinCore module relation. Convenience method that can be used to
	 * obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module relation, <b>null</b> if none.
	 */
	public String getRelation() { return ((_relation != null) && (_relation.size() > 0)) ? _relation.get(0) : null; }

	/**
	 * Sets the DublinCore module relation. Convenience method that can be used
	 * when there is only one relation to set.
	 * @param relation
	 *          the DublinCore module relation to set, <b>null</b> if none.
	 *
	 */
	public void setRelation(String relation) { getRelations().add(relation); }

	/**
	 * Returns the DublinCore module coverage.
	 * @return a list of Strings representing the DublinCore module coverage, an
	 *         empty list if none.
	 *
	 */
	public List<String> getCoverages() { return (_coverage == null) ? (_coverage = new ArrayList<String>(CAPACITY)) : _coverage; }

	/**
	 * Sets the DublinCore module coverages.
	 * @param coverages
	 *          the list of String representing the DublinCore module coverages to
	 *          set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setCoverages(List<String> coverages) { _coverage = coverages; }

	/**
	 * Gets the DublinCore module coverage. Convenience method that can be used to
	 * obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module coverage, <b>null</b> if none.
	 */
	public String getCoverage() { return ((_coverage != null) && (_coverage.size() > 0)) ? _coverage.get(0) : null; }

	/**
	 * Sets the DublinCore module coverage. Convenience method that can be used
	 * when there is only one coverage to set.
	 * @param coverage
	 *          the DublinCore module coverage to set, <b>null</b> if none.
	 *
	 */
	public void setCoverage(String coverage) { getCoverages().add(coverage); }

	/**
	 * Returns the DublinCore module rights.
	 * @return a list of Strings representing the DublinCore module rights, an
	 *         empty list if none.
	 *
	 */
	public List<String> getRightsList() { return (_rights == null) ? (_rights = new ArrayList<String>(CAPACITY)) : _rights; }

	/**
	 * Sets the DublinCore module rights.
	 * @param rights
	 *          the list of String representing the DublinCore module rights to
	 *          set, an empty list or <b>null</b> if none.
	 *
	 */
	public void setRightsList(List<String> rights) { _rights = rights; }

	/**
	 * Gets the DublinCore module rights. Convenience method that can be used to
	 * obtain the first item, <b>null</b> if none.
	 * @return the first DublinCore module rights, <b>null</b> if none.
	 */
	public String getRights() { return ((_rights != null) && (_rights.size() > 0)) ? _rights.get(0) : null; }

	/**
	 * Sets the DublinCore module rights. Convenience method that can be used when
	 * there is only one rights to set.
	 * @param rights
	 *          the DublinCore module rights to set, <b>null</b> if none.
	 *
	 */
	public void setRights(String rights) { getRightsList().add(rights); }

	/**
	 * Creates a deep 'bean' clone of the object.
	 * @return a clone of the object.
	 * @throws CloneNotSupportedException
	 *           thrown if an element of the object cannot be cloned.
	 *
	 */
	public final Object clone() throws CloneNotSupportedException { return getObjBean().clone(); }

	/**
	 * Indicates whether some other object is "equal to" this one as defined by
	 * the Object equals() method.
	 * @param other
	 *          he reference object with which to compare.
	 * @return <b>true</b> if 'this' object is equal to the 'other' object.
	 *
	 */
	public final boolean equals(Object other) { return getObjBean().equals(other); }

	/**
	 * Returns a hashcode value for the object.
	 * It follows the contract defined by the Object hashCode() method.
	 * @return the hashcode of the bean object.
	 *
	 */
	public final int hashCode() { return getObjBean().hashCode(); }

	/**
	 * Returns the String representation for the object.
	 * @return String representation for the object.
	 *
	 */
	public final String toString() { return getObjBean().toString(); }

	public final Class<?> getInterface() { return beanClass; }

	public final void copyFrom(Object obj) { createCopyFrom().copy(this, obj); }

	private static volatile CopyFromHelper COPY_FROM_HELPER;
	private static final Object cflock;

	static {
		cflock = new Object();
		IGNORE_PROPERTIES = new HashSet<String>();
		CONVENIENCE_PROPERTIES = Collections.unmodifiableSet(IGNORE_PROPERTIES);
		IGNORE_PROPERTIES.add("title");
		IGNORE_PROPERTIES.add("creator");
		IGNORE_PROPERTIES.add("subject");
		IGNORE_PROPERTIES.add("description");
		IGNORE_PROPERTIES.add("publisher");
		IGNORE_PROPERTIES.add("contributor");
		IGNORE_PROPERTIES.add("date");
		IGNORE_PROPERTIES.add("type");
		IGNORE_PROPERTIES.add("format");
		IGNORE_PROPERTIES.add("identifier");
		IGNORE_PROPERTIES.add("source");
		IGNORE_PROPERTIES.add("language");
		IGNORE_PROPERTIES.add("relation");
		IGNORE_PROPERTIES.add("coverage");
		IGNORE_PROPERTIES.add("rights");
	}

	static CopyFromHelper createCopyFrom() {
		if (COPY_FROM_HELPER == null) {
			synchronized (cflock) {
				if (COPY_FROM_HELPER == null) {
					final Map<String, Class<?>> basePropInterfaceMap = new HashMap<String, Class<?>>();
					basePropInterfaceMap.put("titles", String.class);
					basePropInterfaceMap.put("creators", String.class);
					basePropInterfaceMap.put("subjects", DCSubject.class);
					basePropInterfaceMap.put("descriptions", String.class);
					basePropInterfaceMap.put("publishers", String.class);
					basePropInterfaceMap.put("contributors", String.class);
					basePropInterfaceMap.put("dates", Date.class);
					basePropInterfaceMap.put("types", String.class);
					basePropInterfaceMap.put("formats", String.class);
					basePropInterfaceMap.put("identifiers", String.class);
					basePropInterfaceMap.put("sources", String.class);
					basePropInterfaceMap.put("languages", String.class);
					basePropInterfaceMap.put("relations", String.class);
					basePropInterfaceMap.put("coverages", String.class);
					basePropInterfaceMap.put("rightsList", String.class);

					final Map<Class<?>, Class<?>> basePropClassImplMap = new HashMap<Class<?>, Class<?>>();
					basePropClassImplMap.put(DCSubject.class, DCSubjectImpl.class);

					COPY_FROM_HELPER = new CopyFromHelper(DCModule.class, basePropInterfaceMap, basePropClassImplMap);
				}
			}
		}
		return COPY_FROM_HELPER;
	}
}