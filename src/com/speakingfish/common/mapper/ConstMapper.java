package com.speakingfish.common.mapper;

import com.speakingfish.common.function.Mapper;


public class ConstMapper<RESULT> implements Mapper<RESULT, Object> {
    
    protected final RESULT _value;
    
    public ConstMapper(RESULT value) {
        super();
        _value = value;
    }

    public RESULT apply(Object value) { return _value; }

}
