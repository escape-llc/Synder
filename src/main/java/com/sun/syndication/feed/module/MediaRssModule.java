package com.sun.syndication.feed.module;

/**
 * Represents the Media RSS namespace module.
 * This also represents the "optional" elements that may be attached to the media:group and/or media:content elements.
 * @author escape-llc
 *
 */
public interface MediaRssModule extends MediaRssOptional, Module {
    String URI = "http://search.yahoo.com/mrss/";
    String TYPE_DEFAULT = "plain";
    String TPYE_HTML = "html";
    /**
     * If the entry has a media:content element, it appears here.
     * If group and content are both NULL, refer to the enclosure element.
     * @return the content.
     */
    MediaContent getContent();
    /**
     * If the entry has a media:group element, it appears here.
     * If group and content are both NULL, refer to the enclosure element.
     * @return the group.
     */
    MediaGroup getGroup();
}
