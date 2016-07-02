package main.java.servlets.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Servlet implementation class StorageServlet
 */
public class StorageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static ServletContext sc;
	private static final String PROPERTIES_PATH = "/WEB-INF/app.properties";
	private Properties properties;

	@Override
	public void init() throws ServletException {
		super.init();
		if (sc == null)
			sc = getServletContext();
		try {
			loadProperties();
		} catch (IOException e) {
			throw new RuntimeException("Can't load properties file", e);
		}
	}

	private void loadProperties() throws IOException {
		try (InputStream is = sc.getResourceAsStream(PROPERTIES_PATH)) {
			if (is == null)
				throw new RuntimeException("Can't locate properties file");
			properties = new Properties();
			properties.load(is);
		}
	}

	/*
	 * Returns property value by given property name.
	 */
	protected String property(final String key) {
		return properties.getProperty(key);
	}

}
