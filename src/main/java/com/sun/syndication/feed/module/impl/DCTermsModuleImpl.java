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
package com.sun.syndication.feed.module.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.syndication.feed.module.DCTermsModule;

/**
 * Implementation of DCMI Terms module.
 * @author escape-llc
 *
 */
public class DCTermsModuleImpl extends DCModuleImpl implements DCTermsModule {
	private static final long serialVersionUID = 1L;
	private List<Date> _created;
	private List<Date> _issued;
	private List<String> _license;
	private List<Date> _modified;

	public DCTermsModuleImpl() {
		super(DCTermsModule.class, URI_TERMS);
	}

	public List<Date> getCreatedList() { return (_created == null) ? (_created = new ArrayList<Date>(CAPACITY)) : _created; }
	public void setCreatedList(List<Date> vx) { _created = vx; }
	public Date getCreated() { return (_created != null && _created.size() > 0) ? _created.get(0) : null; }
	public void setCreated(Date vx) { getCreatedList().add(vx); }
	public List<Date> getIssuedList() { return (_issued == null) ? (_issued = new ArrayList<Date>(CAPACITY)) : _issued; }
	public void setIssuedList(List<Date> vx) { _issued = vx; }
	public Date getIssued() { return (_issued != null && _issued.size() > 0) ? _issued.get(0) : null; }
	public void setIssued(Date vx) { getIssuedList().add(vx); }
	public List<String> getLicenseList() {return (_license == null) ? (_license = new ArrayList<String>(CAPACITY)) : _license; }
	public void setLicenseList(List<String> vx) { _license = vx; }
	public String getLicense() { return (_license != null && _license.size() > 0) ? _license.get(0) : null; }
	public void setLicense(String vx) { getLicenseList().add(vx); }
	public List<Date> getModifiedList() { return (_modified == null) ? (_modified = new ArrayList<Date>(CAPACITY)) : _modified; }
	public void setModifiedList(List<Date> vx) { _modified = vx; }
	public Date getModified() { return (_modified != null && _modified.size() > 0) ? _modified.get(0) : null; }
	public void setModified(Date vx) { getModifiedList().add(vx); }
}