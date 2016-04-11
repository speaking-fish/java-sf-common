package com.speakingfish.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import com.speakingfish.common.function.Mapper;

import com.speakingfish.common.map.AbstractMap;

import static com.speakingfish.common.collection.CollectionHelper.*;

public class Maps {
    
    public static <K, V> Entry<K, V> keyValue(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
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
            public Entry<T_KEY, T_VALUE> apply(T_SOURCE src) {
                return keyValue(mapperKey.apply(src), mapperValue.apply(src));
            }
        };
    }

    public static <
                      T_DEST_KEY  , T_DEST_VALUE,
                      T_SRC_KEY   , T_SRC_VALUE
    > Mapper<
               Entry <T_DEST_KEY  , T_DEST_VALUE>,
               Entry <T_SRC_KEY   , T_SRC_VALUE >
    > makeEntryKeyValueMapper(
         final Mapper<T_DEST_KEY  , T_SRC_KEY   > mapperKey  ,
         final Mapper<T_DEST_VALUE, T_SRC_VALUE > mapperValue
    ) { return new Mapper<
               Entry <T_DEST_KEY  , T_DEST_VALUE>,
               Entry <T_SRC_KEY   , T_SRC_VALUE >
        >() {
        public Entry <T_DEST_KEY  , T_DEST_VALUE> apply(
               Entry <T_SRC_KEY   , T_SRC_VALUE > src
        ) {
            return keyValue(
                mapperKey  .apply(src.getKey  ()),
                mapperValue.apply(src.getValue())
                );
        }};
    }
               
    public static <
        T_KEY   ,
        T_SOURCE
    > Mapper<Entry<T_KEY, T_SOURCE>, T_SOURCE> makeEntryKeyMapper(
        final Mapper<T_KEY  , T_SOURCE> mapperKey
    ) {
        return new Mapper<Entry<T_KEY, T_SOURCE>, T_SOURCE>() {
            public Entry<T_KEY, T_SOURCE> apply(T_SOURCE src) {
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
            public Entry<T_KEY, T_DEST> apply(Entry<T_KEY, T_SOURCE> src) {
                return keyValue(src.getKey(), mapperValue.apply(src.getValue()));
            }
        };
    }
    
    public static <K, V> Set<Entry<K, V>> mapEntrySet(Map<K, V> src) {
        return (null == src) ? null : src.entrySet();
    }
    
    public static <K> Set<K> mapKeySet(Map<K, ?> src) {
        return (null == src) ? null : src.keySet();
    }

    public static <V> Collection<V> mapValues(Map<?, V> src) {
        return (null == src) ? null : src.values();
    }
    
    public static <K, V, M extends Map<K, V>> V mapGet(M src, K key) {
        return src.get(key);
    }
    
    public static final SortedMap<?, ?> EMPTY_SORTED_MAP = new EmptySortedMap<Object, Object>();
    
    private static class EmptySortedMap<K, V> extends AbstractMap<K,V> implements SortedMap<K, V>, Serializable {
        private static final long serialVersionUID = 6428348081105594320L;
    
        public int size()                          { return 0; }
        public boolean isEmpty()                   { return true; }
        public boolean containsKey(Object key)     { return false; }
        public boolean containsValue(Object value) { return false; }
        public V get(Object key)                   { return null; }
        public Set<K> keySet()                     { return emptySortedSet(); }
        public Collection<V> values()              { return Collections.emptySet(); }
        public Set<Map.Entry<K,V>> entrySet()      { return emptySortedSet(); }
    
        public boolean equals(Object o) {
            return (o instanceof SortedMap) && ((SortedMap<?,?>)o).isEmpty();
        }
    
        public int hashCode() { return 0; }
    
        private Object readResolve() { return EMPTY_SORTED_MAP; }
        
        public Comparator<? super K> comparator() { return null; }
        
        public SortedMap<K, V> subMap (K fromKey,
                                                 K toKey  ) { return this; }
        public SortedMap<K, V> headMap(K toKey  ) { return this; }
        public SortedMap<K, V> tailMap(K fromKey) { return this; }
        
        public K firstKey() { throw new NoSuchElementException("Map is empty."); }
        public K lastKey () { throw new NoSuchElementException("Map is empty."); }
    }
    
    @SuppressWarnings("unchecked")
    public static <K, V> SortedMap<K, V> emptySortedMap() {
        return (SortedMap<K, V>) EMPTY_SORTED_MAP;
    }
    
}
