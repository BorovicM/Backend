package com.iktpreobuka.eDnevnik.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LoggerFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		Date date = new Date(record.getMillis());
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(date) + " "
				+ "[" + record.getSourceClassName() + "::"
                + record.getSourceMethodName() + "] "                
                + record.getMessage() + "\n";
	}
}
