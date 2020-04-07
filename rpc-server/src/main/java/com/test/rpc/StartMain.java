package com.test.rpc;

import com.test.rpc.common.Request;
import com.test.rpc.common.Response;
import com.test.rpc.util.ClassUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yu
 * @since 2020/4/7
 */
public class StartMain {

    private static Map<String, Object> cache = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {
        ServerSocket serverSocket = new ServerSocket(9090);
        while (true) {
            Socket client = serverSocket.accept();
            Object obj = deserialization(client.getInputStream());
            try {
                if (obj instanceof Request) {
                    Request request = (Request) obj;
                    System.out.println(request);
                    String className = request.getClassName();
                    Object target;
                    if ((target = cache.get(className)) == null) {
                        Class<?> clazz = Class.forName(className, true, StartMain.class.getClassLoader());
                        Class<?>[] classes = ClassUtils.findInstantiate(clazz);
                        if(classes == null) {
                            throw new RuntimeException("远程服务不存在" + className + "的实现类");
                        }
                        target = classes[0].newInstance();
                    }
                    if(target != null) {
                        try {
                            Method method = target.getClass().getDeclaredMethod(request.getMethodName(), request.getParamTypes());
                            Object result = method.invoke(target, request.getParams());
                            Response response = new Response();
                            response.setResult(result);
                            serialization(response, client.getOutputStream());
                            cache.putIfAbsent(className, target);
                        } catch (NoSuchMethodException e) {
                            System.out.println("method name:" + request.getMethodName() + " is not found in " + request.getClassName());
                            throw new RuntimeException("execution failed");
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                            throw new RuntimeException("execution failed");
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 序列化
     * @param object
     * @param outputStream
     */
    public static void serialization(Object object, OutputStream outputStream){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化
     * @param inputStream
     * @return
     */
    public static Object deserialization(InputStream inputStream){
        //Dont use try-with-resource, because will close client-socket
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

}
