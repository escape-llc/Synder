/*
 * Copyright 2014 eScape Technology LLC.
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
package com.escape.synder;

import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.synd.impl.SyndImageImpl;

/**
 * Extended Image implementation with dimensions.
 * @author escape-llc
 *
 */
public class SyndImageImpl2 extends SyndImageImpl implements SyndImage2 {
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private ObjectBean _objBean;
	public SyndImageImpl2() {
		super();
	}
	private ObjectBean getObjBean() {
		if (_objBean == null)
	        _objBean = new ObjectBean(SyndEntry2.class, this);
		return _objBean;
	}
  /**
   * Creates a deep 'bean' clone of the object.
   * <p>
   * @return a clone of the object.
   * @throws CloneNotSupportedException thrown if an element of the object cannot be cloned.
   *
   */
  public Object clone() throws CloneNotSupportedException {
      return getObjBean().clone();
  }

  /**
   * Indicates whether some other object is "equal to" this one as defined by the Object equals() method.
   * <p>
   * @param other he reference object with which to compare.
   * @return <b>true</b> if 'this' object is equal to the 'other' object.
   *
   */
  public boolean equals(Object other) {
      return getObjBean().equals(other);
  }

  /**
   * Returns a hashcode value for the object.
   * <p>
   * It follows the contract defined by the Object hashCode() method.
   * <p>
   * @return the hashcode of the bean object.
   *
   */
  public int hashCode() {
      return getObjBean().hashCode();
  }

  /**
   * Returns the String representation for the object.
   * <p>
   * @return String representation for the object.
   *
   */
  public String toString() {
      return getObjBean().toString();
  }
	public int getWidth() { return width; }
	public void setWidth(int vx) { width = vx; }
	public int getHeight() { return height; }
	public void setHeight(int vx) { height = vx; }
}
