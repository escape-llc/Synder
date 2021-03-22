package com.sun.syndication.feed.module;

import java.util.List;

import com.escape_technology_llc.synder.SyndImage2;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;

public interface MediaRssOptional {
    /**
     * Typically a duplicate of the item.titleEx element.
     * Default type is "text".
     * @return title.
     */
    SyndContent getTitle();
    /**
     * If found, a duplicate of the item.descriptionEx element.
     * @return description.
     */
    SyndContent getDescription();
    /**
     * Spec claims max 10 comma-delimited.
     * @return keywords.
     */
    String getKeywords();
    /**
     * Get the media:category elements.
     * Spec does not claim cardinality here; we assume list so it's consistent with the rest of the library.
     * @return the possibly empty list.
     */
    List<SyndCategory> getCategories();
    /**
     * Spec claims order-of-importance when time-coding is not at play, e.g. audio content.
     * @return possibly empty list.
     */
    List<SyndImage2> getThumbnails();
    /**
     * Supposed to point to a "direct" url for media player, if content.url does not specify one.
     * SyndImage2 is used as convenience because the attributes match up.
     * @return the image.
     */
    SyndImage2 getPlayer();
}
