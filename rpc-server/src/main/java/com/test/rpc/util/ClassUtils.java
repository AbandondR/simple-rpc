package com.test.rpc.util;

import com.test.rpc.service.Calculate;

import javax.activation.UnsupportedDataTypeException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static java.util.Arrays.asList;

/**
 * @author Yu
 * @since 2020/4/7
 */
public abstract class ClassUtils {


    /**
     * Interface realizes unified configuration URL
     */
    private static final String RESOURCE_PATH = "META-INF/service.properties";

    /**
     * 根据接口查找classpath下的实现类
     * @param interfaceClazz
     * @param <T>
     * @return
     */
    public static <T> Class<? extends T>[] findInstantiate(Class<T> interfaceClazz) {
        //判定参数是否为interface
        if(!interfaceClazz.isInterface()) {
            throw new RuntimeException(new UnsupportedDataTypeException(interfaceClazz.getName() + " 不是接口"));
        }
        try {
            ClassLoader classLoader = interfaceClazz.getClassLoader();
            Enumeration<URL> urls = classLoader != null
                    ? classLoader.getResources(RESOURCE_PATH)
                    : ClassLoader.getSystemResources(RESOURCE_PATH);

            while(urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String fileName = url.getFile();
                String fileExtensions = fileName.substring(fileName.lastIndexOf("."));
                Properties properties = new Properties();
                if(fileExtensions.equals(".xml")) {
                    properties.loadFromXML(url.openStream());
                }
                else {
                    properties.load(url.openStream());
                }
                for(Map.Entry<?, ?> entry : properties.entrySet()) {
                    if(entry.getKey().equals(interfaceClazz.getName())) {
                        String content = (String)entry.getValue();
                        List<String> list = Arrays.asList(content.split(","));
                        return (Class<? extends T>[]) resolveClass(list);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ClassUtils.findInstantiate(Calculate.class);
    }

    public static Class<?>[] resolveClass(List<String> clazzs) {
        List<Class<?>> clazzList = new ArrayList<>();
        clazzs.stream().forEach(str -> {
            try {
                clazzList.add(Class.forName(str));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return clazzList.toArray(new Class<?>[0]);
    }



}
