/*
 * Copyright 2004 Sun Microsystems, Inc.
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
 * Copyright 2010 eScape Technology LLC.
 * 
 * No changes to license.
 *
 */
package com.escape_technology_llc.synder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.Locale;


/**
 * A helper class that parses Dates out of Strings with date time in RFC822 and
 * W3C DateTime formats plus variants for Atom (0.3) and RSS (0.9, 0.91, 0.92,
 * 0.93, 0.94, 1.0 and 2.0).
 * </p>
 * Uses JDK java.text.SimpleDateFormat class for parsing, using a mask for each
 * one of the possible formats.
 * </p>
 * Creates only as many DateFormat instances as are required to parse
 * the strings passed in.  This results in memory and parse-time savings, if
 * parsed dates conform to the standard.
 * </p>
 * Not thread-safe, because SimpleDateFormat.parse() is not.  Meant to be owned by a single caller.
 * </p>
 * @author Alejandro Abdelnur
 * @author escape-llc
 * 
 */
public class DateParser {
	// order is like this because the SimpleDateFormat.parse does not fail with
	// exception if it can parse a valid date out of a substring of the full string given
	// the mask so we have to check the most complete format first, then it fails with
	// exception
	private static final String[] RFC822_MASKS = {
		"EEE, dd MMM yy HH:mm:ss z", "EEE, dd MMM yy HH:mm z",
		"dd MMM yy HH:mm:ss z", "dd MMM yy HH:mm z" };
	private static final String[] W3CDATETIME_MASKS = {
			"yyyy-MM-dd'T'HH:mm:ss.SSSz", "yyyy-MM-dd't'HH:mm:ss.SSSz",
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd't'HH:mm:ss.SSS'z'",
			"yyyy-MM-dd'T'HH:mm:ssz", "yyyy-MM-dd't'HH:mm:ssz",
			"yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd't'HH:mm:ssZ",
			"yyyy-MM-dd'T'HH:mm:ss'Z'",
			"yyyy-MM-dd't'HH:mm:ss'z'",
			"yyyy-MM-dd'T'HH:mmz", // together with logic in the parseW3CDateTime they
			"yyyy-MM'T'HH:mmz", // handle W3C dates without time forcing them to be
													// GMT
			"yyyy'T'HH:mmz", "yyyy-MM-dd't'HH:mmz", "yyyy-MM-dd'T'HH:mm'Z'",
			"yyyy-MM-dd't'HH:mm'z'", "yyyy-MM-dd", "yyyy-MM", "yyyy" };

	private final String[] ADDITIONAL_MASKS;
	private final DateFormat[] RFC822_FORMATS;
	private final DateFormat[] W3C_FORMATS;
	private final DateFormat[] ADDITIONAL_FORMATS;
	private final SimpleDateFormat dfrfc822;
	private final SimpleDateFormat dfw3c;
	private final SDFIterator rfc822;
	private final SDFIterator w3c;
	private final SDFIterator addl;
	/**
	 * Internal iterator to manage and allocate DateFormats.
	 * @author escape-llc
	 *
	 */
	private static class SDFIterator implements Iterator<DateFormat> {
		final String[] formats;
		final DateFormat[] sdfs;
		int cx;
		public SDFIterator(String[] formats, DateFormat[] sdfs) {
			if(sdfs.length < formats.length)
				throw new IllegalArgumentException("sfds too short");
			this.formats = formats;
			this.sdfs = sdfs;
		}
		public void reset() { cx = 0; }
		public boolean hasNext() {
			return cx < formats.length;
		}
		public DateFormat next() {
			if(cx == formats.length)
				throw new NoSuchElementException("past end of list");
			if(sdfs[cx] == null) {
				final DateFormat df = new SimpleDateFormat(formats[cx], Locale.US);
				df.setLenient(true);
				sdfs[cx] = df;
			}
			return sdfs[cx++];
		}
		public void remove() {
			throw new UnsupportedOperationException("remove() Not Supported");
		}
	}

	public DateParser(Context ctx) {
		final ArrayList<String> lx = new ArrayList<String>();
		ctx.getTokenizedProperty("datetime.extra.masks", "|", lx);
		ADDITIONAL_MASKS = lx.toArray(new String[0]);
		ADDITIONAL_FORMATS = new DateFormat[ADDITIONAL_MASKS.length];
		W3C_FORMATS = new DateFormat[W3CDATETIME_MASKS.length];
		RFC822_FORMATS = new DateFormat[RFC822_MASKS.length];
		dfrfc822 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		dfrfc822.setTimeZone(TimeZone.getTimeZone("GMT"));
		dfw3c = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		dfw3c.setTimeZone(TimeZone.getTimeZone("GMT"));
		rfc822 = new SDFIterator(RFC822_MASKS, RFC822_FORMATS);
		w3c = new SDFIterator(W3CDATETIME_MASKS, W3C_FORMATS);
		addl = new SDFIterator(ADDITIONAL_MASKS, ADDITIONAL_FORMATS);
	}

	private static Date parseUsingFormat(SDFIterator fmts, String sDate) {
		sDate = (sDate != null) ? sDate.trim() : null;
		if(sDate == null) return null;
		ParsePosition pp = null;
		Date d = null;
		fmts.reset();
		while(d == null && fmts.hasNext()) {
			try {
				final DateFormat df = fmts.next();
				pp = new ParsePosition(0);
				d = df.parse(sDate, pp);
				if (pp.getIndex() != sDate.length()) {
					d = null;
				}
				// System.out.println("pp["+pp.getIndex()+"] s["+sDate+" m["+masks[i]+"] d["+d+"]");
			} catch (Exception ex1) {
				// System.out.println("s: "+sDate+" m: "+masks[i]+" d: "+null);
			}
		}
		return d;
	}

	/**
	 * Parse Date String in RFC822 format.
	 * </p>
	 * Refer to the java.text.SimpleDateFormat javadocs for details on the format
	 * of each element.
	 * </p>
	 * 
	 * @param sDate
	 *          string to parse for a date.
	 * @return the Date represented by the given RFC822 string. It returns
	 *         <b>null</b> if it was not possible to parse the given string into a
	 *         Date.
	 * 
	 */
	public Date parseRFC822(String sDate) {
		final int utIndex = sDate.indexOf(" UT");
		if (utIndex > -1) {
			final StringBuilder sb = new StringBuilder();
			sb.append(sDate.substring(0, utIndex));
			sb.append(" GMT");
			sb.append(sDate.substring(utIndex + 3));
			sDate = sb.toString();
		}
		return parseUsingFormat(rfc822, sDate);
	}

	/**
	 * Parse Date String in W3C format.
	 * </p>
	 * Refer to the java.text.SimpleDateFormat javadocs for details on the format
	 * of each element.
	 * </p>
	 * 
	 * @param sDate
	 *          string to parse for a date.
	 * @return the Date represented by the given W3C date-time string. It returns
	 *         <b>null</b> if it was not possible to parse the given string into a
	 *         Date.
	 * 
	 */
	public Date parseW3C(String sDate) {
		// if sDate has time on it, it injects 'GMT' before TZ displacement to
		// allow the SimpleDateFormat parser to parse it properly
		int tIndex = sDate.indexOf("T");
		if (tIndex > -1) {
			if (sDate.endsWith("Z")) {
				sDate = sDate.substring(0, sDate.length() - 1) + "+00:00";
			}
			int tzdIndex = sDate.indexOf("+", tIndex);
			if (tzdIndex == -1) {
				tzdIndex = sDate.indexOf("-", tIndex);
			}
			if (tzdIndex > -1) {
				final StringBuilder sb = new StringBuilder();
				String pre = sDate.substring(0, tzdIndex);
				int secFraction = pre.indexOf(",");
				if (secFraction > -1) {
					pre = pre.substring(0, secFraction);
				}
				sb.append(pre);
				sb.append("GMT");
				sb.append(sDate.substring(tzdIndex));
				sDate = sb.toString();
			}
		} else {
			sDate += "T00:00GMT";
		}
		return parseUsingFormat(w3c, sDate);
	}

	/**
	 * Parse Date String in W3C or RFC822 format.
	 * </p>
	 * 
	 * @param sDate
	 *          string to parse for a date.
	 * @return the Date represented by the given W3C date-time string. It returns
	 *         <b>null</b> if it was not possible to parse the given string into a
	 *         Date.
	 * 
	 * */
	public Date parseDate(String sDate) {
		Date d = parseW3C(sDate);
		if (d == null) {
			d = parseRFC822(sDate);
			if (d == null && ADDITIONAL_FORMATS.length > 0) {
				d = parseUsingFormat(addl, sDate);
			}
		}
		return d;
	}

	/**
	 * create a RFC822-formatted String.
	 * </p>
	 * Refer to the java.text.SimpleDateFormat javadocs for details on the format
	 * of each element.
	 * </p>
	 * 
	 * @param date
	 *          Date to parse
	 * @return the RFC822 represented by the given Date It returns <b>null</b> if
	 *         it was not possible to parse the date.
	 * 
	 */
	public String formatRFC822(Date date) {
		return dfrfc822.format(date);
	}

	/**
	 * Create a W3C-formatted String.
	 * </p>
	 * Refer to the java.text.SimpleDateFormat javadocs for details on the format
	 * of each element.
	 * </p>
	 * 
	 * @param date
	 *          Date to parse
	 * @return the W3C Date Time represented by the given Date It returns
	 *         <b>null</b> if it was not possible to parse the date.
	 * 
	 */
	public String formatW3CDateTime(Date date) {
		return dfw3c.format(date);
	}
}
