package com.speakingfish.common.mapper;

import java.util.Map.Entry;

import com.speakingfish.common.function.Acceptor;
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
            @Override public C apply(A value) {
                return second.apply(first.apply(value));
            }
        };
    }
    
    public static <
        KEY, RESULT, SOURCE
    > Mapper<Entry<KEY, RESULT>, Entry<KEY, SOURCE>> mapperEntryValue(
        final Mapper<RESULT, SOURCE> valueMapper
    ) {
        return new Mapper<Entry<KEY, RESULT>, Entry<KEY, SOURCE>>() {
            @Override public Entry<KEY, RESULT> apply(Entry<KEY, SOURCE> value) {
                return keyValue(value.getKey(), valueMapper.apply(value.getValue()));
            }};
    }
    
    public static final Acceptor<Object> ACCEPTOR_IS_ASSIGNED = new Acceptor<Object>() {
        @Override public boolean test(Object value) { return null != value; }
    };
    
    @SuppressWarnings("unchecked")
    public static <T> Acceptor<T> acceptAssigned() {
        return (Acceptor<T>) ACCEPTOR_IS_ASSIGNED;
    }
    
    public static <T> Acceptor<T> acceptor(final Mapper<Boolean, T> mapper) {
        return new Acceptor<T>() {
            @Override public boolean test(T value) {
                return mapper.apply(value);
            }};
    }
    
    public static <T> Mapper<Boolean, T> mapper(final Acceptor<T> acceptor) {
        return new Mapper<Boolean, T>() {
            @Override public Boolean apply(T value) {
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
            @Override public RESULT apply(SOURCE value) {
                return catchDefault(value, defaultValue, mapper);
            }};
    }
    
    public static <RESULT, SOURCE> Mapper<RESULT, SOURCE> mapperInvoker(
        final RESULT defaultValue,
        final Invoker<SOURCE> invoker
    ) {
        return new Mapper<RESULT, SOURCE>() {
            @Override public RESULT apply(SOURCE value) {
                invoker.invoke(value);
                return defaultValue;
            }};
    }

}
