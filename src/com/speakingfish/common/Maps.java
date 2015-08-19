package com.speakingfish.common;

import java.util.Iterator;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.Map.Entry;

import com.speakingfish.common.function.Mapper;

public class Maps {
    
    public static <K, V> Entry<K, V> keyValue(K key, V value) {
        return new SimpleImmutableEntry<K, V>(key, value);
    }

    public static <
        T_Map__T_KEY_T_VALUE extends Map<T_KEY, T_VALUE>,
        T_KEY                                           ,
        T_VALUE
    > T_Map__T_KEY_T_VALUE collectMap(
        T_Map__T_KEY_T_VALUE dest,
        Iterator<Entry<T_KEY, T_VALUE>> src
    ) {
        while(src.hasNext()) {
            final Entry<T_KEY, T_VALUE> entry = src.next(); 
            dest.put(entry.getKey(), entry.getValue());
        }
        return dest;
    }

    public static <
        T_KEY   ,
        T_VALUE ,
        T_SOURCE
    > Mapper<Entry<T_KEY, T_VALUE>, T_SOURCE> makeEntryMapper(
        final Mapper<T_KEY  , T_SOURCE> mapperKey  ,
        final Mapper<T_VALUE, T_SOURCE> mapperValue
    ) {
        return new Mapper<Entry<T_KEY, T_VALUE>, T_SOURCE>() {
            @Override public Entry<T_KEY, T_VALUE> apply(T_SOURCE src) {
                return keyValue(mapperKey.apply(src), mapperValue.apply(src));
            }
        };
    }
    
    public static <
        T_KEY   ,
        T_SOURCE
    > Mapper<Entry<T_KEY, T_SOURCE>, T_SOURCE> makeEntryKeyMapper(
        final Mapper<T_KEY  , T_SOURCE> mapperKey
    ) {
        return new Mapper<Entry<T_KEY, T_SOURCE>, T_SOURCE>() {
            @Override public Entry<T_KEY, T_SOURCE> apply(T_SOURCE src) {
                return keyValue(mapperKey.apply(src), src);
            }
        };
    }
    
    public static <
        T_KEY   ,
        T_DEST  ,
        T_SOURCE
    > Mapper<Entry<T_KEY, T_DEST>, Entry<T_KEY, T_SOURCE>> makeEntryValueMapper(
        final Mapper<T_DEST, T_SOURCE> mapperValue
    ) {
        return new Mapper<Entry<T_KEY, T_DEST>, Entry<T_KEY, T_SOURCE>>() {
            @Override public Entry<T_KEY, T_DEST> apply(Entry<T_KEY, T_SOURCE> src) {
                return keyValue(src.getKey(), mapperValue.apply(src.getValue()));
            }
        };
    }
    
}
