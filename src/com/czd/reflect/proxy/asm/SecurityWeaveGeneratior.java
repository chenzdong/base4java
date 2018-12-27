package com.czd.reflect.proxy.asm;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: czd
 * @create: 2018/12/27 9:37
 */
public class SecurityWeaveGeneratior {
    public static void main(String[] args) throws IOException {
        String className = Base.class.getName();
        ClassReader classReader = new ClassReader(className);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        AddSecurityCheckClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
        classReader.accept(classAdapter, ClassReader.SKIP_DEBUG);
        byte[] data = cw.toByteArray();
        File file = new File("E:\\mygithub\\base4java\\out\\production\\base4java\\"+className.replaceAll("\\.", "/") + ".class");
        if (file.exists()) {
            System.out.println("file already exist");
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(data);
        fileOutputStream.close();
    }
}
