package com.sun.syndication.feed.module;

import java.util.List;

/**
 * Represents Media RSS group element.
 * May contain multiple content elements plus any optional elements, that should apply to all entries in the list, unless overridden in one.
 * @author escape-llc
 *
 */
public interface MediaGroup {
	List<MediaContent> getContentList();
	MediaRssOptional getOptionalElements();
}
