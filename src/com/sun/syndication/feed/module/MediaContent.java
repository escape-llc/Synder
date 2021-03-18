package com.sun.syndication.feed.module;

/**
 * Representation of Media RSS content element.
 * May contain optional elements that apply directly to this element, and override similar element at "higher" levels.
 * @author escape-llc
 *
 */
public interface MediaContent extends Cloneable {
	String getUrl();
	int getFilesize();
	String getType();
	String getMedium();
	boolean getDefault();
	String getExpression();
	String getBitrate();
	String getFramerate();
	String getSamplingrate();
	int getChannels();
	String getDuration();
	int getHeight();
	int getWidth();
	String getLang();
	MediaRssOptional getOptionalElements();
}
