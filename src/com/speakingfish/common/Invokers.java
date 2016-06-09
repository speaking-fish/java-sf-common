package com.speakingfish.common;

import com.speakingfish.common.function.Callback;
import com.speakingfish.common.function.Invoker;
import com.speakingfish.common.function.Mapper;


public class Invokers {
    
    public static final Invoker<?> INVOKER_NOTHING = new Invoker<Object>() {
        public void invoke(Object value) {
            //do nothing
        }};
        
    public static class ExceptionInvoker<T> implements Invoker<T> {
        
        protected final Mapper<RuntimeException, T> _mapper;
        
        public ExceptionInvoker(Mapper<RuntimeException, T> mapper) {
            super();
            _mapper = mapper;
        }

        public void invoke(T value) {
            RuntimeException e = _mapper.apply(value);
            if(null != e)
                throw e;
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> Invoker<T> nothingInvoker() { return (Invoker<T>) INVOKER_NOTHING; }

    public static <T> Invoker<T> exceptionInvoker(Mapper<RuntimeException, T> mapper) { return new ExceptionInvoker<T>(mapper); }
    
    public static <T> Callback<T> callbackOf(final Invoker<T> invoker) {
        return new Callback<T>() {
            public void callback(T value) throws Exception {
                invoker.invoke(value);
            }
        };
    }
    
    public static <T> Invoker<T> rethrowableInvokerOf(final Callback<T> callback) {
        return new Invoker<T>() {
            public void invoke(T value) {
                invokeAndRethrowAsRuntimeException(value, callback);
            }
        };
    }
    
    public static <T> Invoker<T> fork(final Invoker<T> a, final Invoker<T> b) {
        return new Invoker<T>() {
            public void invoke(T value) {
                a.invoke(value);
                b.invoke(value);
            }};
    }

    public static <T> Invoker<T> fork(final Invoker<T>... invokers) {
        return new Invoker<T>() {
            public void invoke(T value) {
                for(final Invoker<T> invoker : invokers) {
                    invoker.invoke(value);
                }
            }};
    }
    
    public static <DEST, SRC> Callback<SRC> callback(final Mapper<DEST, SRC> mapper, final Callback<DEST> callback) {
        return new Callback<SRC>() {
            public void callback(SRC value) throws Exception {
                callback.callback(mapper.apply(value));
            }};
    }
    
    public static <DEST, SRC> Invoker<SRC> invoker(final Mapper<DEST, SRC> mapper, final Invoker<DEST> invoker) {
        return new Invoker<SRC>() {
            public void invoke(SRC value) {
                invoker.invoke(mapper.apply(value));
            }};
    }
    
    public static <T> void invokeAndRethrowAsRuntimeException(T value, final Callback<T> callback) {
        try {
            callback.callback(value);
        } catch(RuntimeException e) {
            throw e;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
