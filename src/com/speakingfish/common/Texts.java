package com.speakingfish.common;

import static java.lang.Math.*;

public class Texts {
    
    public static String left(String src, int count) {
        return src.substring(0, min(src.length(), count));
    }
    
    public static String right(String src, int count) {
        return src.substring(max(0, src.length() - count), src.length());
    }
    
    public static boolean endsWithIgnoreCase(String src, String match) {
        return (match.length() <= src.length()) && match.equalsIgnoreCase(right(src, match.length()));
    }

    public static boolean startsWithIgnoreCase(String src, String match) {
        return (match.length() <= src.length()) && match.equalsIgnoreCase(left (src, match.length()));
    }
    
    public static String removeLeft(String src, int count) {
        return (count < src.length()) ? src.substring(count) : "";
    }
    
    public static String removeRight(String src, int count) {
        return src.substring(0, max(0, src.length() - count));
    }
    
    public static String removeLeftIgnoreCase(String src, String match) {
        return startsWithIgnoreCase(src, match) ? removeLeft(src, match.length()) : src;
    }
    
    public static String removeRightIgnoreCase(String src, String match) {
        return endsWithIgnoreCase(src, match) ? removeRight(src, match.length()) : src;
    }
    
    public static String needStartsWithIgnoreCase(String src, String match) {
        return startsWithIgnoreCase(src, match) ? src : src + match;
    }
    
    public static String needEndsWithIgnoreCase(String src, String match) {
        return endsWithIgnoreCase(src, match) ? src : src + match;
    }
    
    public static String replaceLeftIgnoreCase(String src, String match, String replacement) {
        return 
            startsWithIgnoreCase(src, match)
            ? replacement + removeLeftIgnoreCase(src, match)
            : src;
        
    }
    
    public static String replaceRightIgnoreCase(String src, String match, String replacement) {
        return 
            endsWithIgnoreCase(src, match)
            ? removeRightIgnoreCase(src, match) + replacement
            : src;
        
    }
    
    public static boolean filled(String value) {
        return (null != value) && !value.isEmpty();
    }
    
    public static String firstFilled(String a, String b) {
        return filled(a) ? a : b;
    }
    
    @SafeVarargs public static String firstFilled(String... list) {
        for(final String v : list) {
            if(filled(v)) {
                return v;
            }
        }
        return null;
    }
    
}