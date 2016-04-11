package com.speakingfish.common.mapper;

import java.util.Map.Entry;

import com.speakingfish.common.function.Acceptor;
import com.speakingfish.common.function.Getter;
import com.speakingfish.common.function.Mapper;
import com.speakingfish.common.function.Invoker;

import static com.speakingfish.common.Maps.*;

public class Mappers {
    
    @SuppressWarnings("unchecked")
    public static <RESULT, SOURCE> Mapper<RESULT, SOURCE> constMapper(RESULT value) {
        return (Mapper<RESULT, SOURCE>) new ConstMapper<RESULT>(value);
    }

    public static <C, B, A> Mapper<C, A> mapperChain(final Mapper<B, A> first, final Mapper<C, B> second) {
        return new Mapper<C, A>() {
            public C apply(A value) {
                return second.apply(first.apply(value));
            }
        };
    }

    public static <T> Mapper<T, T> passthrough(final Invoker<T> invoker) {
        return new Mapper<T, T>() {
            public T apply(T value) {
                invoker.invoke(value);
                return value;
            }
        };
    }
    
    public static <
        KEY, RESULT, SOURCE
    > Mapper<Entry<KEY, RESULT>, Entry<KEY, SOURCE>> mapperEntryValue(
        final Mapper<RESULT, SOURCE> valueMapper
    ) {
        return new Mapper<Entry<KEY, RESULT>, Entry<KEY, SOURCE>>() {
            public Entry<KEY, RESULT> apply(Entry<KEY, SOURCE> value) {
                return keyValue(value.getKey(), valueMapper.apply(value.getValue()));
            }};
    }
    
    public static final Acceptor<Object> ACCEPTOR_IS_ASSIGNED = new Acceptor<Object>() {
        public boolean test(Object value) { return null != value; }
    };
    
    @SuppressWarnings("unchecked")
    public static <T> Acceptor<T> acceptAssigned() {
        return (Acceptor<T>) ACCEPTOR_IS_ASSIGNED;
    }
    
    public static final Acceptor<Entry<Object, Object>> ACCEPTOR_ENTRY_VALUE_IS_ASSIGNED = new Acceptor<Entry<Object, Object>>() {
        public boolean test(Entry<Object, Object> value) { return null != value.getValue(); }
    };

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <K, V> Acceptor<Entry<K, V>> acceptEntryValueAssigned() {
        return (Acceptor<Entry<K, V>>) (Acceptor) ACCEPTOR_ENTRY_VALUE_IS_ASSIGNED;
    }
    
    public static <T> Acceptor<T> acceptor(final Mapper<Boolean, T> mapper) {
        return new Acceptor<T>() {
            public boolean test(T value) {
                return mapper.apply(value);
            }};
    }

    public static <R, S> Acceptor<S> acceptor(final Acceptor<R> acceptor, final Mapper<R, S> mapper) {
        return new Acceptor<S>() {
            public boolean test(S value) {
                return acceptor.test(mapper.apply(value));
            }};
    }
    
    public static <T> Mapper<Boolean, T> mapper(final Acceptor<T> acceptor) {
        return new Mapper<Boolean, T>() {
            public Boolean apply(T value) {
                return acceptor.test(value);
            }};
    }
    
    public static <RESULT, SOURCE> RESULT catchDefault(SOURCE value, RESULT defaultValue, Mapper <RESULT, SOURCE> mapper) {
        try {
            return mapper.apply(value);
        } catch(Exception e) {
            return defaultValue;
        }
    }
    
    public static <RESULT, SOURCE> Mapper<RESULT, SOURCE> mapperCatchDefault(
        final RESULT defaultValue,
        final Mapper <RESULT, SOURCE> mapper
    ) {
        return new Mapper<RESULT, SOURCE>() {
            public RESULT apply(SOURCE value) {
                return catchDefault(value, defaultValue, mapper);
            }};
    }
    
    public static <RESULT, SOURCE> Mapper<RESULT, SOURCE> mapperInvoker(
        final RESULT defaultValue,
        final Invoker<SOURCE> invoker
    ) {
        return new Mapper<RESULT, SOURCE>() {
            public RESULT apply(SOURCE value) {
                invoker.invoke(value);
                return defaultValue;
            }};
    }

    public static final Mapper<Object, Entry<?, ?>> MAPPER_ENTRY_KEY = new Mapper<Object, Entry<?, ?>>() {
        public Object apply(Entry<?, ?> src) { return src.getKey  (); }
    }; 
    
    public static final Mapper<Object, Entry<?, ?>> MAPPER_ENTRY_VALUE = new Mapper<Object, Entry<?, ?>>() {
        public Object apply(Entry<?, ?> src) { return src.getValue(); }
    }; 
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <K, V> Mapper<K, Entry<K, V>> mapperEntryKey() {
        return (Mapper<K, Entry<K, V>>) (Mapper) MAPPER_ENTRY_KEY;
    };
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <K, V> Mapper<V, Entry<K, V>> mapperEntryValue() {
        return (Mapper<V, Entry<K, V>>) (Mapper) MAPPER_ENTRY_VALUE;
    };

    public static <DEST, SRC> Mapper<DEST, Mapper<DEST, SRC>> mapperApplier(final SRC value) {
        return new Mapper<DEST, Mapper<DEST, SRC>>() {
            public DEST apply(Mapper<DEST, SRC> mapper) {
                return mapper.apply(value);
            }
        };
    }

    public static <DEST, SRC> Mapper<DEST, Mapper<DEST, SRC>> lazyMapperApplier(final Getter<SRC> value) {
        return new Mapper<DEST, Mapper<DEST, SRC>>() {
            public DEST apply(Mapper<DEST, SRC> mapper) {
                return mapper.apply(value.get());
            }
        };
    }
    
}
