package com.speakingfish.common.debug;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DebugHelper {

    public static String stackTrace(Throwable e) {
        final StringWriter buffer = new StringWriter();
        final PrintWriter writer = new PrintWriter(buffer);
        e.printStackTrace(writer);
        writer.flush();
        return buffer.toString();
    }

}
