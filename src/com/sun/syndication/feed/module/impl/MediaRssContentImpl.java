package com.sun.syndication.feed.module.impl;

import com.sun.syndication.feed.module.MediaContent;
import com.sun.syndication.feed.module.MediaRssOptional;

public class MediaRssContentImpl implements MediaContent {
	private String _url;
	private int _filesize;
	private String _type;
	private String _medium;
	private boolean _default;
	private String _expression;
	private String _bitrate;
	private String _framerate;
	private String _samplingrate;
	private int _channels;
	private String _duration;
	private int _height;
	private int _width;
	private String _lang;
	private MediaRssOptional _optional;

	public String getUrl() { return _url; }
	public void setUrl(String _url) { this._url = _url; }
	public int getFilesize() { return _filesize; }
	public void setFilesize(int _filesize) { this._filesize = _filesize; }
	public String getType() { return _type; }
	public void setType(String _type) { this._type = _type; }
	public String getMedium() { return _medium; }
	public void setMedium(String _medium) { this._medium = _medium; }
	public boolean getDefault() { return _default; }
	public void setDefault(boolean _default) { this._default = _default; }
	public String getExpression() { return _expression; }
	public void setExpression(String _expression) { this._expression = _expression; }
	public String getBitrate() { return _bitrate; }
	public void setBitrate(String _bitrate) { this._bitrate = _bitrate; }
	public String getFramerate() { return _framerate; }
	public void setFramerate(String _framerate) { this._framerate = _framerate; }
	public String getSamplingrate() { return _samplingrate; }
	public void setSamplingrate(String _samplingrate) { this._samplingrate = _samplingrate; }
	public int getChannels() { return _channels; }
	public void setChannels(int _channels) { this._channels = _channels; }
	public String getDuration() { return _duration; }
	public void setDuration(String _duration) { this._duration = _duration; }
	public int getHeight() { return _height; }
	public void setHeight(int _height) { this._height = _height; }
	public int getWidth() { return _width; }
	public void setWidth(int _width) { this._width = _width; }
	public String getLang() { return _lang; }
	public void setLang(String _lang) { this._lang = _lang; }
	public MediaRssOptional getOptionalElements() { return _optional;	}
	public void setOptional(MediaRssOptional _optional) { this._optional = _optional; }
}
