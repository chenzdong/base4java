package com.czd.reflect.proxy.asm;


import jdk.internal.org.objectweb.asm.*;


/**
 * @author: czd
 * @create: 2018/12/27 9:31
 */
public class SecurityCheckMethodAdapter extends MethodVisitor {
    public SecurityCheckMethodAdapter(MethodVisitor methodVisitor) {
        super(Opcodes.ASM5, methodVisitor);
    }

    @Override
    public void visitCode() {
        Label continueLabel = new Label();
        visitMethodInsn(Opcodes.INVOKESTATIC, "com/czd/reflect/proxy/asm/SecurityChecker", "checkSecurity", "()Z");
        visitJumpInsn(Opcodes.IFNE, continueLabel);
        visitInsn(Opcodes.RETURN);
        visitLabel(continueLabel);
        super.visitCode();
    }
}
