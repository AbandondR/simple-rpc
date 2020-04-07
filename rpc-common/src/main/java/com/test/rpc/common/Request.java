package com.test.rpc.common;

import java.io.Serializable;

/**
 *
 * a method signature object to ensure method is only
 * @author Yu
 * @since 2020/4/7
 */
public class Request implements Serializable {

    public static final long serialVersionUID = 3933918042687238629L;

    private String className;

    private String methodName;

    private Class<?>[] paramTypes;

    private Object[] params;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Request{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}
