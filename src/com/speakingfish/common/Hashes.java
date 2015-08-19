package com.speakingfish.common;

public class Hashes {
    
    protected static final int MAGIC_MULTIPLIER = 23;

    public static int hash(int     v, int hashCode) { return v | (MAGIC_MULTIPLIER * hashCode); }
    public static int hash(long    v, int hashCode) { return hash(hash(v), hashCode); }
    public static int hash(float   v, int hashCode) { return hash(hash(v), hashCode); }
    public static int hash(double  v, int hashCode) { return hash(hash(v), hashCode); }
    public static int hash(boolean v, int hashCode) { return hash(hash(v), hashCode); }
    public static int hash(Object  v, int hashCode) { return hash(hash(v), hashCode); }

    public static int hash(int     v) { return                                  v  ; } 
    public static int hash(long    v) { return       ((int) (v >> 32)) | ((int) v) ; }
    public static int hash(float   v) { return          Float .floatToIntBits  (v) ; }
    public static int hash(double  v) { return     hash(Double.doubleToLongBits(v)); }
    public static int hash(boolean v) { return          v  ? 1 : 0                 ; }
    public static int hash(Object  v) { return (null == v) ? 0 : v.hashCode()      ; }

}
