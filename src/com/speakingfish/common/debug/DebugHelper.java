package com.speakingfish.common.debug;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class DebugHelper {

    public static String stackTrace(Throwable e) {
        final StringWriter buffer = new StringWriter();
        final PrintWriter writer = new PrintWriter(buffer);
        e.printStackTrace(writer);
        writer.flush();
        return buffer.toString();
    }

    public static void debugError(PrintStream logWriter, final String message, final Throwable th) {
        final String logMessage = "[ERROR] - " + message;

        logWriter.println(logMessage);

        // dump exception stack if specified
        if (null != th) {
            final StackTraceElement[] traces = th.getStackTrace();
            if (null != traces && traces.length > 0) {
                logWriter.println(th.getClass() + ": " + th.getMessage());

                for (final StackTraceElement trace : traces) {
                    logWriter.println("    at " + trace.getClassName() + '.' + trace.getMethodName() + '(' + trace.getFileName() + ':' + trace.getLineNumber() + ')');
                }
            }

            Throwable cause = th.getCause();
            while (null != cause) {
                final StackTraceElement[] causeTraces = cause.getStackTrace();
                if (null != causeTraces && causeTraces.length > 0) {
                    logWriter.println("Caused By:");
                    logWriter.println(cause.getClass() + ": " + cause.getMessage());

                    for (final StackTraceElement causeTrace : causeTraces) {
                        logWriter.println("    at " + causeTrace.getClassName() + '.' + causeTrace.getMethodName() + '(' + causeTrace.getFileName() + ':' + causeTrace.getLineNumber() + ')');
                    }
                }

                // fetch next cause
                cause = cause.getCause();
            }
        }
    }

    public static String debugError(final String message, final Throwable th) {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            final PrintStream dest = new PrintStream(buffer, false, StandardCharsets.UTF_8.name());
            debugError(dest, message, th);
            dest.flush();
            return buffer.toString(StandardCharsets.UTF_8.name());
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
}
