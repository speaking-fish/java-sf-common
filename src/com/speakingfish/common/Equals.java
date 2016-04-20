package com.speakingfish.common;

public class Equals {

    public static boolean equalsOf(boolean a, boolean b) {
        return a == b;
    }

    public static boolean equalsOf(double a, double b) {
        return Double.doubleToRawLongBits(a) == Double.doubleToRawLongBits(b);
    }
    
    public static boolean equalsOf(float a, float b) {
        return Float.floatToRawIntBits(a) == Float.floatToRawIntBits(b);
    }

    public static <A, B extends A> boolean equalsOf(A a, B b) {
        if(null == a) {
            return null == b;
        } else if(null == b) {
            return false;
        } else {
            return a.equals(b);
        }
    }

    public static <C, A extends C, B extends C> boolean equalsOf(A a, B b, Class<C> c) {
       return Equals.<C, B>equalsOf(a, b);
    }

}
