package com.czd.reflect.proxy.asm;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * 增加SecurityCheck类的字节码
 *
 * @author: czd
 * @create: 2018/12/26 16:44
 */
public class AddSecurityCheckClassAdapter extends ClassVisitor {
    public AddSecurityCheckClassAdapter(ClassVisitor classVisitor) {
        super(Opcodes.ASM5,classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        MethodVisitor mv = super.visitMethod(i, s, s1, s2, strings);

        MethodVisitor wrappedMv = mv;

        if (mv != null) {
            if ("operation".equals(s)) {
                wrappedMv = new SecurityCheckMethodAdapter(mv);
            }
        }
        return wrappedMv;
    }
}
