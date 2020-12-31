package com.czd.reflect.loader.loadClass;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: czd
 * @create: 2020/12/30 14:35
 */
public class MyClassLoaderCustom extends ClassLoader {

    private ClassLoader jdkClassLoader;

    private Map<String, String> classPathMap = new HashMap<>();

    public MyClassLoaderCustom(ClassLoader jdkClassLoader) {
        this.jdkClassLoader = jdkClassLoader;
        classPathMap.put("com.czd.reflect.loader.loadClass.TestA", "E:\\mygithub\\base4java\\out\\production\\base4java\\com\\czd\\reflect\\loader\\loadClass\\TestA.class");
        classPathMap.put("com.czd.reflect.loader.loadClass.TestB", "E:\\mygithub\\base4java\\out\\production\\base4java\\com\\czd\\reflect\\loader\\loadClass\\TestB.class");
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class result = null;
        // 主要是为了加载jdk bootstrap的类 java.lang这些
        try {
            result = jdkClassLoader.loadClass(name);
        } catch (Exception e) {

        }
        if (result != null) {
            return result;
        }
        String classPath = classPathMap.get(name);
        File file = new File(classPath);
        if (!file.exists()) {
            throw new ClassNotFoundException();
        }
        byte[] classData = getClassData(file);
        if (classData == null || classData.length == 0) {
            throw new ClassNotFoundException();
        }
        return defineClass(classData, 0 , classData.length);
    }

    private byte[] getClassData(File file) {
        try (InputStream ins = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesNumRead = 0;
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }
}
