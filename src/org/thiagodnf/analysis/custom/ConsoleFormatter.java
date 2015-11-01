package org.thiagodnf.analysis.custom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * This class is used to format the logs generate by this tool.
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class ConsoleFormatter extends Formatter {

	/**
	 * Create a DateFormat to format the logger timestamp.
	 */
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

	public String format(LogRecord record) {
		StringBuilder builder = new StringBuilder(1000);
		
		builder.append(df.format(new Date(record.getMillis()))).append(" ");
		builder.append("[").append(record.getLevel()).append("] ");		
		builder.append(formatMessage(record));
		builder.append("\n");
		
		return builder.toString();
	}

	public String getHead(Handler h) {
		return super.getHead(h);
	}

	public String getTail(Handler h) {
		return super.getTail(h);
	}
}
