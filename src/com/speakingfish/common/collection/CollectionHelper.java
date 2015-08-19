package com.speakingfish.common.collection;

import java.util.Collection;
import java.util.Iterator;

public class CollectionHelper {

    public static <
        T_Collection__T_ELEMENT extends Collection<T_ELEMENT>,
        T_ELEMENT
    > T_Collection__T_ELEMENT collect(
        T_Collection__T_ELEMENT dest,
        Iterator<T_ELEMENT> src
    ) {
        while(src.hasNext()) {
            dest.add(src.next());
        }
        return dest;
    }
    
}
