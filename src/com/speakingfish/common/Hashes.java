package com.speakingfish.common;

public class Hashes {
    
    protected static final int MAGIC_MULTIPLIER = 31;

    // single value hash code
    
    public static int hash(int     v) { return                                  v  ; } 
    public static int hash(long    v) { return       ((int) (v >> 32)) | ((int) v) ; }
    public static int hash(float   v) { return          Float .floatToIntBits  (v) ; }
    public static int hash(double  v) { return     hash(Double.doubleToLongBits(v)); }
    public static int hash(boolean v) { return          v  ? 1 : 0                 ; }
    public static int hash(Object  v) { return (null == v) ? 0 : v.hashCode()      ; }

    // hash code chain with predefined MAGIC_MULTIPLIER
    
    public static int hash(int     v, int hashCode) { return v + (MAGIC_MULTIPLIER * hashCode); }
    public static int hash(long    v, int hashCode) { return hash(hash(v), hashCode); }
    public static int hash(float   v, int hashCode) { return hash(hash(v), hashCode); }
    public static int hash(double  v, int hashCode) { return hash(hash(v), hashCode); }
    public static int hash(boolean v, int hashCode) { return hash(hash(v), hashCode); }
    public static int hash(Object  v, int hashCode) { return hash(hash(v), hashCode); }

    // hash code chain with specified multiplier
    
    public static int hash(int     v, int multiplier, int hashCode) { return v + (multiplier * hashCode); }
    public static int hash(long    v, int multiplier, int hashCode) { return hash(hash(v), multiplier, hashCode); }
    public static int hash(float   v, int multiplier, int hashCode) { return hash(hash(v), multiplier, hashCode); }
    public static int hash(double  v, int multiplier, int hashCode) { return hash(hash(v), multiplier, hashCode); }
    public static int hash(boolean v, int multiplier, int hashCode) { return hash(hash(v), multiplier, hashCode); }
    public static int hash(Object  v, int multiplier, int hashCode) { return hash(hash(v), multiplier, hashCode); }

    // hash code and multiplier chain with specified hashCode (low part of long) and multiplier (high part of long)
    
    public static long hash(int     v, long hashCodeAndMultiplier) { return hashEncode(v, hashMultiplier(hashCodeAndMultiplier), hashResult(hashCodeAndMultiplier)); }
    public static long hash(long    v, long hashCodeAndMultiplier) { return hashEncode(v, hashMultiplier(hashCodeAndMultiplier), hashResult(hashCodeAndMultiplier)); }
    public static long hash(float   v, long hashCodeAndMultiplier) { return hashEncode(v, hashMultiplier(hashCodeAndMultiplier), hashResult(hashCodeAndMultiplier)); }
    public static long hash(double  v, long hashCodeAndMultiplier) { return hashEncode(v, hashMultiplier(hashCodeAndMultiplier), hashResult(hashCodeAndMultiplier)); }
    public static long hash(boolean v, long hashCodeAndMultiplier) { return hashEncode(v, hashMultiplier(hashCodeAndMultiplier), hashResult(hashCodeAndMultiplier)); }
    public static long hash(Object  v, long hashCodeAndMultiplier) { return hashEncode(v, hashMultiplier(hashCodeAndMultiplier), hashResult(hashCodeAndMultiplier)); }
    
    // hash chain with specified multiplier
    
    protected static long hashEncode(int     v, int multiplier, int hashCode) { return v + (multiplier * hashCode); }
    protected static long hashEncode(long    v, int multiplier, int hashCode) { return hash(hash(v), multiplier, hashCode); }
    protected static long hashEncode(float   v, int multiplier, int hashCode) { return hash(hash(v), multiplier, hashCode); }
    protected static long hashEncode(double  v, int multiplier, int hashCode) { return hash(hash(v), multiplier, hashCode); }
    protected static long hashEncode(boolean v, int multiplier, int hashCode) { return hash(hash(v), multiplier, hashCode); }
    protected static long hashEncode(Object  v, int multiplier, int hashCode) { return hash(hash(v), multiplier, hashCode); }
    
    protected static long hashEncode(int multiplier, int hashCode) { return (((long) multiplier) << 32) | (0xFFFFFFFF & ((long) hashCode)); }
    
    protected static int hashMultiplier(long hashCode) { return (int) (hashCode >>> 32); }
    
    public static int hashResult(long hashCode) { return (int) hashCode; }

    public static long hashInit(int init, int multiplier) { return hashEncode(multiplier, init); }
    
}
