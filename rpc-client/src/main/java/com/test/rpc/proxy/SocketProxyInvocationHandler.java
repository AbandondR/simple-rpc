package com.test.rpc.proxy;

import com.test.rpc.common.Request;
import com.test.rpc.common.Response;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @author Yu
 * @since 2020/4/7
 */
public class SocketProxyInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Socket client = new Socket("127.0.0.1", 9090);
        client.setSoTimeout(10000);
        InputStream inputStream = client.getInputStream();
        OutputStream outputStream = client.getOutputStream();
        ObjectOutputStream write = new ObjectOutputStream(outputStream);
        write.writeObject(requestObject(method, args));
        ObjectInputStream read = new ObjectInputStream(inputStream);
        Object object = read.readObject();
        if(object instanceof Response) {
            Response response = (Response)object;
            return response.getResult();
        }
        if(client != null) {
            client.close();
        }
        return null;
    }

    private Object requestObject(Method method, Object[] args){
        Request request = new Request();
        request.setMethodName(method.getName());
        request.setClassName(method.getDeclaringClass().getName());
        request.setParams(args);
        request.setParamTypes(method.getParameterTypes());
        return request;
    }

    public static Object createProxy(Class<?>[] interfaces) {
        return Proxy.newProxyInstance(SocketProxyInvocationHandler.class.getClassLoader(), interfaces, new SocketProxyInvocationHandler());
    }
}
