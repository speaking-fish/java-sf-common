package com.speakingfish.common.collection;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;

import com.speakingfish.common.function.ParamInvoker;

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
    
    public static <T_DEST, T_ELEMENT> T_DEST collect(
        T_DEST                          dest   ,
        ParamInvoker<T_DEST, T_ELEMENT> invoker,
        Iterator    <T_ELEMENT        > src
    ) {
        while(src.hasNext()) {
            invoker.invoke(dest, src.next());
        }
        return dest;
    }

    public static ParamInvoker<Collection<Object>, Object> INVOKER_COLLECTION_ADD = new ParamInvoker<Collection<Object>, Object>() {
        public void invoke(Collection<Object> dest, Object src) {
            dest.add(src);
        }
    };
    
    @SuppressWarnings("unchecked")
    public static <
        T_Collection__T_ELEMENT extends Collection<T_ELEMENT>,
        T_ELEMENT
    > ParamInvoker<T_Collection__T_ELEMENT, T_ELEMENT> invokerCollectionAdd() {
        return (ParamInvoker<T_Collection__T_ELEMENT, T_ELEMENT>) INVOKER_COLLECTION_ADD;
    }
    
    
    public static <V> Collection<V> defaultCollection(Collection<V> src) {
        return (null == src) ? Collections.<V>emptyList() : src;
    }
    
    public static <V> List<V> defaultList(List<V> src) {
        return (null == src) ? Collections.<V>emptyList() : src;
    }
    
    public static <V> Set<V> defaultSet(Set<V> src) {
        return (null == src) ? Collections.<V>emptySet() : src;
    }
    
    public static final SortedSet<?> EMPTY_SORTED_SET = new EmptySortedSet<Object>();
    
    protected static class EmptySortedSet<E> extends AbstractSet<E> implements SortedSet<E>, Serializable {
        private static final long serialVersionUID = 1582296315990362920L;
    
        public Iterator<E> iterator() { return Collections.<E>emptyList().iterator()/* 1.5 compatibility emptyIterator()*/; }
    
        public int size() { return 0; }
        public boolean isEmpty() { return true; }
    
        public boolean contains(Object obj) { return false; }
        public boolean containsAll(Collection<?> c) { return c.isEmpty(); }
    
        public Object[] toArray() { return new Object[0]; }
    
        public <T> T[] toArray(T[] a) {
            if (a.length > 0)
                a[0] = null;
            return a;
        }
    
        private Object readResolve() { return EMPTY_SORTED_SET; }

        //@Override 1.5 compat 
        public Comparator<? super E> comparator() { return null; }

        //@Override 1.5 compat 
        public SortedSet<E> subSet (E fromElement,
                                              E toElement  ) { return this; }
        //@Override 1.5 compat 
        public SortedSet<E> headSet(E toElement  ) { return this; }
        //@Override 1.5 compat 
        public SortedSet<E> tailSet(E fromElement) { return this; }

        //@Override 1.5 compat 
        public E first() { throw new NoSuchElementException("Set is empty."); }
        //@Override 1.5 compat 
        public E last() { throw new NoSuchElementException("Set is empty."); }
    }

    @SuppressWarnings("unchecked")
    public static <E> SortedSet<E> emptySortedSet() {
        return (SortedSet<E>) EMPTY_SORTED_SET;
    }
    
    public static <E> SortedSet<E> defaultSortedSet(SortedSet<E> src) {
        return (null == src) ? CollectionHelper.<E>emptySortedSet() : src;
    }
    
}
