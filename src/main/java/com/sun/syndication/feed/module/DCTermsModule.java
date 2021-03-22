/*
 * Copyright 2015 eScape Technology LLC.
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
 */
package com.sun.syndication.feed.module;

import java.util.Date;
import java.util.List;

/**
 * Interface for DCMI Terms namespace.
 * This includes all the DCMI Elements items as well.
 * @author escape-llc
 *
 */
public interface DCTermsModule extends DCModule {
  /**
   * URI of the DCMI Terms module (http://purl.org/dc/terms/).
   *
   */
  String URI_TERMS = "http://purl.org/dc/terms/";
  /**
   * Get the list of created dates.
   * @return
   */
	List<Date> getCreatedList();
	/**
	 * Set the list of created dates.
	 * @param vx
	 */
	void setCreatedList(List<Date> vx);
	/**
	 * Get the first item in the list, or NULL.
	 * @return NULL: no items; !NULL: first item.
	 */
	Date getCreated();
	/**
	 * Add to the list of created dates.
	 * @param vx date to add.
	 */
	void setCreated(Date vx);
  /**
   * Get the list of issued dates.
   * @return
   */
	List<Date> getIssuedList();
	/**
	 * Set the list of issued dates.
	 * @param vx
	 */
	void setIssuedList(List<Date> vx);
	/**
	 * Get the first item in the list, or NULL.
	 * @return NULL: no items; !NULL: first item.
	 */
	Date getIssued();
	/**
	 * Add to the list of issued dates.
	 * @param vx date to add.
	 */
	void setIssued(Date vx);
  /**
   * Get the list of license strings.
   * @return
   */
	List<String> getLicenseList();
	/**
	 * Set the list of license strings.
	 * @param vx
	 */
	void setLicenseList(List<String> vx);
	/**
	 * Get the first item in the list, or NULL.
	 * @return NULL: no items; !NULL: first item.
	 */
	String getLicense();
	/**
	 * Add to the list of license strings.
	 * @param vx string to add.
	 */
	void setLicense(String vx);
  /**
   * Get the list of modified dates.
   * @return
   */
	List<Date> getModifiedList();
	/**
	 * Set the list of modified dates.
	 * @param vx
	 */
	void setModifiedList(List<Date> vx);
	/**
	 * Get the first item in the list, or NULL.
	 * @return NULL: no items; !NULL: first item.
	 */
	Date getModified();
	/**
	 * Add to the list of modified dates.
	 * @param vx date to add.
	 */
	void setModified(Date vx);
}
