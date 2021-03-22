package com.escape_technology_llc.synder.handlers.ns;

import java.util.HashMap;

import org.xml.sax.Attributes;

import com.escape_technology_llc.synder.ParseContext;
import com.escape_technology_llc.synder.SynderContentHandler;
import com.escape_technology_llc.synder.handlers.CategoryElement;
import com.escape_technology_llc.synder.handlers.ContentElement;
import com.escape_technology_llc.synder.handlers.HandlerFactory;
import com.escape_technology_llc.synder.handlers.ModuleHandlerImpl;
import com.escape_technology_llc.synder.handlers.SimpleElement;
import com.escape_technology_llc.synder.handlers.SubHandler;
import com.escape_technology_llc.synder.setters.Setter;
import com.escape_technology_llc.synder.setters.SyndCategoryListSetter;
import com.escape_technology_llc.synder.setters.SyndContentSetter;
import com.sun.syndication.feed.module.MediaRssModule;
import com.sun.syndication.feed.module.impl.MediaRssModuleImpl;

/**
 * Namespace handler for Media RSS
 * @author escape-llc
 *
 */
public class MediaRss extends ModuleHandlerImpl<MediaRssModuleImpl> {
	// these are literals for efficiency
	public static final String TITLE = "{http://search.yahoo.com/mrss/}title";
	public static final String DESCRIPTION = "{http://search.yahoo.com/mrss/}description";
	public static final String KEYWORDS = "{http://search.yahoo.com/mrss/}keywords";
	public static final String CATEGORY = "{http://search.yahoo.com/mrss/}category";
	public static final String CONTENT = "{http://search.yahoo.com/mrss/}content";
	public static final String GROUP = "{http://search.yahoo.com/mrss/}group";
	static final HashMap<String,HandlerFactory<MediaRssModuleImpl>> props;
	static {
		props = new HashMap<String,HandlerFactory<MediaRssModuleImpl>>();
		try {
			props.put(TITLE, new Entry_ContentElement(new SyndContentSetter(MediaRssModuleImpl.class, "Title")));
			props.put(CATEGORY, new Entry_CategoryElement(new SyndCategoryListSetter(MediaRssModuleImpl.class, "Categories")));
			props.put(DESCRIPTION, new Entry_ContentElement(new SyndContentSetter(MediaRssModuleImpl.class, "Description")));
			props.put(KEYWORDS, new Entry_SimpleElement(new Setter(MediaRssModuleImpl.class, "Keywords")));
		}
		catch(Exception ex) {
			// eat it
		}
	}
	static final class Entry_ContentElement extends HandlerFactory<MediaRssModuleImpl> {
		public Entry_ContentElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, MediaRssModuleImpl root) {
			return new ContentElement<MediaRssModuleImpl>(pc, root, sx);
		}
	}
	static final class Entry_SimpleElement extends HandlerFactory<MediaRssModuleImpl> {
		public Entry_SimpleElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, MediaRssModuleImpl root) {
			return new SimpleElement<MediaRssModuleImpl>(pc, root, sx);
		}
	}
	public static final class Entry_CategoryElement extends HandlerFactory<MediaRssModuleImpl> {
		public Entry_CategoryElement(Setter sx) {
			super(sx);
		}
		@Override
		public SubHandler<?> create(ParseContext pc, Attributes ax, MediaRssModuleImpl root) {
			return new CategoryElement<MediaRssModuleImpl>(pc, root, sx);
		}
	}
	public MediaRss(ParseContext ctx) {
		super(MediaRssModule.URI, ctx);
	}
	@Override
	protected MediaRssModuleImpl newInstance() {
		return new MediaRssModuleImpl();
	}
	@Override
	public SubHandler<?> query(ParseContext pc, String[] path, SubHandler<?> tos, String uri, String localName,
			String name, Attributes attributes) {
		final Object root = tos != null ? tos.getRoot() : null;
		if(root == null) return null;
		final String nx = SynderContentHandler.formatTag(uri, localName);
		if(props.containsKey(nx)) {
			final MediaRssModuleImpl dc = getModule(root);
			if(dc != null) {
				return props.get(nx).create(pc, attributes, dc);
			}
		}
		return null;
	}
}
