package com.sun.syndication.feed.module.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.escape.synder.SyndImage2;
import com.sun.syndication.feed.impl.CopyFromHelper;
import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.module.DCSubject;
import com.sun.syndication.feed.module.MediaContent;
import com.sun.syndication.feed.module.MediaGroup;
import com.sun.syndication.feed.module.MediaRssModule;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;

/**
 * Represents the "conditional" elements in Media RSS, because they may be applied at multiple elements:
 * channel, feed, media:group, media:content in order from weakest-to-strongest.
 * @author escape-llc
 *
 */
public class MediaRssModuleImpl extends ModuleImpl implements MediaRssModule {
	private static final long serialVersionUID = 1L;
	protected static final int CAPACITY = 2;
	/**
	 * Properties to be ignored when cloning.
	 */
	private static final Set<String> IGNORE_PROPERTIES;

	/**
	 * Unmodifiable Set containing the convenience properties of this class.
	 * <p>
	 * Convenience properties are mapped to Modules, for cloning the convenience
	 * properties can be ignored as they will be copied as part of the module
	 * cloning.
	 */
	public static final Set<String> CONVENIENCE_PROPERTIES;
	private ObjectBean _objBean;
	private SyndContent _title;
	private SyndContent _description;
	private String _keywords;
	private List<SyndCategory> _categories;
	private List<SyndImage2> _thumbnails;
	private SyndImage2 _player;
	private MediaContent _content;
	private MediaGroup _group;
	public MediaRssModuleImpl() {
		super(MediaRssModule.class, URI);
	}
	private ObjectBean getObjBean() {
		if (_objBean == null)
			_objBean = new ObjectBean(beanClass, this, CONVENIENCE_PROPERTIES);
		return _objBean;
	}
	public final String toString() { return getObjBean().toString(); }
	public Class<?> getInterface() { return beanClass; }
	public final void copyFrom(Object obj) { createCopyFrom().copy(this, obj); }
	
	private static volatile CopyFromHelper COPY_FROM_HELPER;
	private static final Object cflock;

	static {
		cflock = new Object();
		IGNORE_PROPERTIES = new HashSet<String>();
		CONVENIENCE_PROPERTIES = Collections.unmodifiableSet(IGNORE_PROPERTIES);
		// TODO make this match MRSS
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
					// TODO make this match MRSS
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
					// TODO make this match MRSS
					basePropClassImplMap.put(DCSubject.class, DCSubjectImpl.class);

					COPY_FROM_HELPER = new CopyFromHelper(DCModule.class, basePropInterfaceMap, basePropClassImplMap);
				}
			}
		}
		return COPY_FROM_HELPER;
	}
	public SyndContent getTitle() { return _title; }
	public void setTitle(SyndContent title) { _title = title; }
    public SyndContent getDescription() { return _description; }
	public void setDescription(SyndContent desc) { _description = desc; }
	public String getKeywords() { return _keywords; }
	public void setKeywords(String kws) { _keywords = kws; }
	public List<SyndCategory> getCategories() { return _categories == null ? (_categories = new ArrayList<SyndCategory>(CAPACITY)) : _categories; }
	public void setCategories(List<SyndCategory> list) { _categories = list; }
	public List<SyndImage2> getThumbnails() { return _thumbnails == null ? (_thumbnails = new ArrayList<SyndImage2>(CAPACITY)) : _thumbnails; }
	public void setThumbnails(List<SyndImage2> list) { _thumbnails = list; }
	public SyndImage2 getPlayer() { return _player; }
	public void setPlayer(SyndImage2 img) { _player = img; }
	public MediaContent getContent() { return _content; }
	public void setContent(MediaContent mc) { _content = mc; }
	public MediaGroup getGroup() { return _group; }
	public void setGroup(MediaGroup mg) { _group = mg; }
}
