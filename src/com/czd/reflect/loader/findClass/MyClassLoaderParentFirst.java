package com.czd.reflect.loader.findClass;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: czd
 * @create: 2020/12/30 14:35
 */
public class MyClassLoaderParentFirst extends ClassLoader {
    private Map<String, String> classPathMap = new HashMap<>();

    public MyClassLoaderParentFirst() {
        classPathMap.put("com.czd.reflect.loader.findClass.TestA", "E:\\mygithub\\base4java\\out\\production\\base4java\\com\\czd\\reflect\\loader\\findClass\\TestA.class");
        classPathMap.put("com.czd.reflect.loader.findClass.TestB", "E:\\mygithub\\base4java\\out\\production\\base4java\\com\\czd\\reflect\\loader\findClass\\TestB.class");
    }
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
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
