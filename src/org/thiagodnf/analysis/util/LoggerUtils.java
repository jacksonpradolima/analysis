package org.thiagodnf.analysis.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import org.thiagodnf.analysis.custom.ConsoleFormatter;

/**
 * Logger Utils Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class LoggerUtils {

	public static Logger getLogger(String name) {
		Logger logger = Logger.getLogger(name);

		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new ConsoleFormatter());

		logger.setUseParentHandlers(false);
		logger.addHandler(handler);

		return logger;
	}
}
