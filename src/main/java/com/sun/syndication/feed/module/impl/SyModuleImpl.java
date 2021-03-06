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
 */
package com.sun.syndication.feed.module.impl;

import com.sun.syndication.feed.impl.CopyFromHelper;
import com.sun.syndication.feed.module.SyModule;

import java.util.*;

/**
 * Syndication ModuleImpl, default implementation.
 * 
 * @see <a href="http://web.resource.org/rss/1.0/modules/syndication/">Syndication module</a>
 * @author Alejandro Abdelnur
 * @author escape-llc
 * 
 */
public class SyModuleImpl extends ModuleImpl implements SyModule {
	private static final long serialVersionUID = 1L;
	private static final Set<String> PERIODS;
	private String _updatePeriod;
	private int _updateFrequency;
	private Date _updateBase;

	/**
	 * Default constructor. All properties are set to <b>null</b>.
	 * 
	 */
	public SyModuleImpl() {
		super(SyModule.class, URI);
	}

	/**
	 * Returns the Syndication module update period.
	 * 
	 * @return the Syndication module update period, <b>null</b> if none.
	 * 
	 */
	public String getUpdatePeriod() {
		return _updatePeriod;
	}

	/**
	 * Sets the Syndication module update period.
	 * 
	 * @param updatePeriod
	 *          the Syndication module update period to set, <b>null</b> if none.
	 * 
	 */
	public void setUpdatePeriod(String updatePeriod) {
		if (!PERIODS.contains(updatePeriod)) {
			throw new IllegalArgumentException("Invalid period [" + updatePeriod
					+ "]");
		}
		_updatePeriod = updatePeriod;
	}

	/**
	 * Returns the Syndication module update frequency.
	 * 
	 * @return the Syndication module update frequency, <b>null</b> if none.
	 * 
	 */
	public int getUpdateFrequency() {
		return _updateFrequency;
	}

	/**
	 * Sets the Syndication module update frequency.
	 * 
	 * @param updateFrequency
	 *          the Syndication module update frequency to set, <b>null</b> if
	 *          none.
	 * 
	 */
	public void setUpdateFrequency(int updateFrequency) {
		_updateFrequency = updateFrequency;
	}

	/**
	 * Returns the Syndication module update base date.
	 * 
	 * @return the Syndication module update base date, <b>null</b> if none.
	 * 
	 */
	public Date getUpdateBase() {
		return _updateBase;
	}

	/**
	 * Sets the Syndication module update base date.
	 * 
	 * @param updateBase
	 *          the Syndication module update base date to set, <b>null</b> if
	 *          none.
	 * 
	 */
	public void setUpdateBase(Date updateBase) {
		_updateBase = updateBase;
	}

	public Class<?> getInterface() {
		return SyModule.class;
	}

	public void copyFrom(Object obj) {
		createCopyFrom().copy(this, obj);
	}

	private static volatile CopyFromHelper COPY_FROM_HELPER;
	private static final Object cflock;

	static {
		cflock = new Object();
		PERIODS = new HashSet<String>();
		PERIODS.add(HOURLY);
		PERIODS.add(DAILY);
		PERIODS.add(WEEKLY);
		PERIODS.add(MONTHLY);
		PERIODS.add(YEARLY);
	}

	static CopyFromHelper createCopyFrom() {
		if (COPY_FROM_HELPER == null) {
			synchronized (cflock) {
				if (COPY_FROM_HELPER == null) {
					final Map<String, Class<?>> basePropInterfaceMap = new HashMap<String, Class<?>>();
					basePropInterfaceMap.put("updatePeriod", String.class);
					basePropInterfaceMap.put("updateFrequency", Integer.TYPE);
					basePropInterfaceMap.put("updateBase", Date.class);

					final Map<Class<?>, Class<?>> basePropClassImplMap = Collections
							.emptyMap();

					COPY_FROM_HELPER = new CopyFromHelper(SyModule.class,
							basePropInterfaceMap, basePropClassImplMap);
				}
			}
		}
		return COPY_FROM_HELPER;
	}

}
