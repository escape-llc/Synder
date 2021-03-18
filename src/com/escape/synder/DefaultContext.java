/*
 * Copyright 2010 eScape Technology LLC.
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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Default Synder context.
 * @author escape-llc
 *
 */
public class DefaultContext implements Context {
	final Properties px;
	/**
	 * Ctor.
	 * @param is Stream to SYNDER.PROPERTIES.  This stream is closed.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public DefaultContext(InputStream is) throws IOException, ClassNotFoundException {
		this(loadProperties(is));
	}
	/**
	 * Ctor.
	 * @param px Properties instance to use.  Takes ownership.
	 * @throws ClassNotFoundException
	 */
	public DefaultContext(Properties px) throws ClassNotFoundException {
		if(px == null) throw new IllegalArgumentException("px");
		this.px = px;
	}
	/**
	 * Load handlers indicated in PROPERTIES.
	 * @param ctx Source context.
	 * @param prop Name of property.
	 * @param hx Possibly empty list of classes.
	 * @throws ClassNotFoundException
	 */
	public static void loadClasses(Context ctx, String prop, List<Class<?>> hx) throws ClassNotFoundException {
		final ArrayList<String> lx = new ArrayList<String>();
		ctx.getTokenizedProperty(prop, " ", lx);
		if (lx.size() > 0) {
			for (final String cx : lx) {
				final Class<?> mClass = Class.forName(cx);
				hx.add(mClass);
			}
		}
	}
	/**
	 * Create an instance, preferring a constructor that takes given parameter/type.
	 * @param <T> Desired cast.
	 * @param <U> Desired ctor parameter.
	 * @param mClass Source class to instantiate.
	 * @param ctp Ctor parameter type.
	 * @param ctx Source context.
	 * @return new instance.
	 */
	@SuppressWarnings("unchecked")
	public
	static <T,U> T createWith(Class<?> mClass, Class<?> ctp, U ctx) {
		try {
			final Constructor<?> ctor = mClass.getConstructor(ctp);
			return (T) ctor.newInstance(ctx);
		} catch (NoSuchMethodException nsme) {
			try {
				return (T) mClass.newInstance();
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		} catch (IllegalArgumentException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		return null;
	}
	/**
	 * Load the input stream into PROPERTIES.
	 * @param is input stream.
	 * @return new PROPERTIES.
	 * @throws IOException
	 */
	static Properties loadProperties(InputStream is) throws IOException {
		try {
			if(is == null) throw new IllegalArgumentException("is");
			final Properties px = new Properties();
			try {
				px.load(is);
			} finally {
				is.close();
			}
			return px;
		} catch (IOException ioex) {
			final IOException ex = new IOException(
					"could not load synder master plugins file [InputStream], "
							+ ioex.getMessage());
			ex.setStackTrace(ioex.getStackTrace());
			throw ex;
		}
	}
	public void getTokenizedProperty(String prop, String delim, List<String> lx) {
		if(prop == null || prop.length() == 0) throw new IllegalArgumentException("prop");
		if(delim == null || delim.length() == 0) throw new IllegalArgumentException ("delim");
		if(lx == null) throw new IllegalArgumentException("lx");
		final String prx = px.getProperty(prop);
		if (prx != null) {
			final StringTokenizer st = new StringTokenizer(prx, delim);
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				lx.add(token);
			}
		}
	}
	public void getHandlers(List<Class<?>> lx) {
		if(lx == null) throw new IllegalArgumentException("lx");
	}
}
