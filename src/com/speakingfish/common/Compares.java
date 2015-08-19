package com.speakingfish.common;

import java.util.Iterator;

public class Compares {

    public static <T> int compareTo(Comparable<T> a, T b) {
        if(null == a) {
            if(null == b) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if(null == b) {
                return 1;
            } else {
                return a.compareTo(b);
            }
        }
    }

    
    @SuppressWarnings("unchecked")
    public static int compareObjects(Object a, Object b) {
        return compareTo((Comparable<Object>) a, b);
    }
    
    public static int ordinal(boolean value) {
        return value ? 1 : 0;
    }

    public static int compareTo(boolean a, boolean b) {
        //return Boolean.compare(a, b);
        if(!a) {
            if(!b) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if(!b) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static int compareTo(int a, int b) {
        //return Integer.compare(a, b);
        if(a < b) {
            return -1;
        } else if(a > b) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int compareTo(long a, long b) {
        //return Long.compare(a, b);
        if(a < b) {
            return -1;
        } else if(a > b) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int compareTo(byte a, byte b) {
        //return Byte.compare(a, b);
        if(a < b) {
            return -1;
        } else if(a > b) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int compareTo(char a, char b) {
        //return Character.compare(a, b);
        if(a < b) {
            return -1;
        } else if(a > b) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int compareTo(float a, float b) {
        return Float.compare(a, b);
        /*
        if(a < b) {
            return -1;
        } else if(a > b) {
            return 1;
        } else {
            return 0;
        }
        */
    }

    public static int compareTo(double a, double b) {
        return Double.compare(a, b);
        /*
        if(a < b) {
            return -1;
        } else if(a > b) {
            return 1;
        } else {
            return 0;
        }
        */
    }

    @SuppressWarnings("unchecked")
    public static <T> T max(Comparable<T> a, T b) {
        if(null == a) {
            return b;
        } else if(null == b) {
            return (T) a;
        } else if(compareTo(a, b) < 0) {
            return b;
        } else {
            return (T) a;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T min(Comparable<T> a, T b) {
        if(null == a) {
            return b;
        } else if(null == b) {
            return (T) a;
        } else if(compareTo(a, b) > 0) {
            return b;
        } else {
            return (T) a;
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T maxAllowNull(Comparable<T> a, T b) {
        if(compareTo(a, b) < 0) {
            return b;
        } else {
            return (T) a;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T minAllowNull(Comparable<T> a, T b) {
        if(compareTo(a, b) > 0) {
            return b;
        } else {
            return (T) a;
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> T min(Iterator<T> src) {
        Comparable<T> result = null;
        if(src.hasNext()) {
            result = src.next();
            while(src.hasNext()) {
                result = min(result, src.next());
            }
        }
        return (T) result;
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> T max(Iterator<T> src) {
        Comparable<T> result = null;
        if(src.hasNext()) {
            result = src.next();
            while(src.hasNext()) {
                result = max(result, src.next());
            }
        }
        return (T) result;
    }
    
    public static <T extends Comparable<T>> T min(Iterable<T> src) {
        return min(src.iterator());
    }
    
    public static <T extends Comparable<T>> T max(Iterable<T> src) {
        return max(src.iterator());
    }
    
    public static <T> T firstAssigned(T a, T b) {
        return (null != a) ? a : b;
    }
    
    @SafeVarargs public static <T> T firstAssigned(T... list) {
        for(final T v : list) {
            if(null != v) {
                return v;
            }
        }
        return null;
    }
    
}
