package com.test.rpc.common;

import java.io.Serializable;

/**
 * define a normal response object as a target for RMI deserializate
 * @author Yu
 * @since 2020/4/7
 */
public class Response implements Serializable {
    private static final long serialVersionUID = -1549099960609347408L;

    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
