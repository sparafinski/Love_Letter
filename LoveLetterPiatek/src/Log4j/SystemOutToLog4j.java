package Log4j;

import java.io.PrintStream;

public class SystemOutToLog4j extends PrintStream {

	private static final PrintStream originalSystemOut = System.out;
	private static SystemOutToLog4j systemOutToLogger;	

	@SuppressWarnings("rawtypes")
	public static void enableForClass(Class className) {
		systemOutToLogger = new SystemOutToLog4j(originalSystemOut, className.getName());
		System.setOut(systemOutToLogger);
	}

	public static void enableForPackage(String packageToLog) {
		systemOutToLogger = new SystemOutToLog4j(originalSystemOut, packageToLog);
		System.setOut(systemOutToLogger);
	}

	public static void disable() {
		System.setOut(originalSystemOut);
		systemOutToLogger = null;
	}

	private String packageOrClassToLog;
	private SystemOutToLog4j(PrintStream original, String packageOrClassToLog) {
		super(original);
		this.packageOrClassToLog = packageOrClassToLog;
	}
	
	@Override	
	public void println(String line) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		StackTraceElement caller = findCallerToLog(stack);
		if (caller == null) {
			super.println(line);
			return;
		}

		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(caller.getClass());
		logger.debug("Code Line No.: " + stack[2].getLineNumber() + ", Class Name: " + caller.getClassName() + ", Text: " + line);
	}

	public StackTraceElement findCallerToLog(StackTraceElement[] stack) {
		for (StackTraceElement element : stack) {
			if (element.getClassName().startsWith(packageOrClassToLog)) {
				return element;
			}			
		}
		return null;
	}
}