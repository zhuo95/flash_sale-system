package com.zz.miaosha.exception;

import com.zz.miaosha.result.CodeMsg;

public class GlobalException extends RuntimeException {

    CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
