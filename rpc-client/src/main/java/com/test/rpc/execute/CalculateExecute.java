package com.test.rpc.execute;

import com.test.rpc.proxy.SocketProxyInvocationHandler;
import com.test.rpc.service.Calculate;


/**
 * @author Yu
 * @since 2020/4/7
 */
public class CalculateExecute {

    public static void main(String[] args) {
        Calculate calculate = (Calculate) SocketProxyInvocationHandler.createProxy(new Class[]{Calculate.class});
        System.out.println(calculate.sum(3,4));
    }

}
