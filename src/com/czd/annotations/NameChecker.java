package com.czd.annotations;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;
import java.util.EnumSet;

/**
 * 命名检查器
 * 程序名称规范的编译器插件
 * 如果程序命名不符合规范，将会输出一个编译器的WARNING信息
 * @author: czd
 * @create: 2019/3/28 10:45
 */
public class NameChecker {
    private final Messager messager;
    NameCheckScanner nameCheckScanner = new NameCheckScanner();
    NameChecker(ProcessingEnvironment processingEnvironment) {
        this.messager = processingEnvironment.getMessager();
    }
    public void checkName(Element element) {
        nameCheckScanner.scan(element);
    }
    private class NameCheckScanner extends ElementScanner8<Void, Void> {


        /**
         * 检查Java类
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitType(TypeElement e, Void aVoid) {
            scan(e.getTypeParameters(), aVoid);
            checkCamelCase(e, true);
            super.visitType(e, aVoid);
            return null;
        }

        /**
         * 检查方法命名是否合法
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if (e.getKind() == ElementKind.METHOD) {
                Name name = e.getSimpleName();
                if (name.contentEquals(e.getEnclosingElement().getSimpleName())) {
                    messager.printMessage(Diagnostic.Kind.WARNING,"一个普通方法"+name+"不应当与类名重复,避免与构造函数混淆", e);
                }
                checkCamelCase(e, false);
            }
            super.visitExecutable(e, aVoid);
            return null;
        }

        /**
         * 检查变量名是否合法
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            // 如果这个变量是常量或者枚举类 则按大写命名检查
            if (e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null || heuristicallyConstant(e)) {
                checkAllCaps(e);
            } else {
                checkCamelCase(e, false);
            }
            return null;
        }

        /**
         * 判断变量是否为常量
         */
        private boolean heuristicallyConstant(VariableElement e) {
            if (e.getEnclosingElement().getKind() == ElementKind.INTERFACE) {
                return true;
            } else if (e.getKind() == ElementKind.FIELD && e.getModifiers().containsAll(EnumSet
                    .of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL))) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * 判断传入的Element是否符合驼式命名法，如果不符合，则输出警告信息
         * @param e
         * @param initalCaps
         */
        private void checkCamelCase(Element e, boolean initalCaps) {
            String name = e.getSimpleName().toString();
            boolean previousUpper = false;
            boolean conventional = true;
            int fisrtCodePoint = name.codePointAt(0);
            if (Character.isUpperCase(fisrtCodePoint)) {
                previousUpper = true;
                if (!initalCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING,"名称"+name+"应该以小写字母开头", e);
                    return;
                }
            } else if (Character.isLowerCase(fisrtCodePoint)) {
                if (initalCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING,"名称"+name+"应该以大写字母开头", e);
                    return;
                }
            } else {
                conventional = false;
            }
            if (conventional) {
                int cp = fisrtCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (Character.isUpperCase(cp)) {
                        if (previousUpper) {
                            conventional = false;
                            break;
                        }
                        previousUpper = true;
                    } else {
                        previousUpper = false;
                    }
                }
            }
            if (!conventional) {
                messager.printMessage(Diagnostic.Kind.WARNING,"名称"+name+"应该符合驼式命名法", e);
            }

        }

        /**
         * 常量命名检查，要求第一个字母必须是大写的英文字母，其他部分可以是下划线或者大写字母
         * @param e
         */
        private void checkAllCaps(Element e) {
            String name = e.getSimpleName().toString();
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            if (!Character.isUpperCase(firstCodePoint)) {
                conventional = false;
            } else {
                boolean previousUnderscore =false;
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (cp == (int)'_') {
                        if(previousUnderscore) {
                            conventional = false;
                            break;
                        }
                        previousUnderscore = true;
                    } else {
                        previousUnderscore = false;
                        if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
                            conventional = false;
                            break;
                        }
                    }
                }

            }
            if (!conventional) {
                messager.printMessage(Diagnostic.Kind.WARNING,"常量"+name+"应该以大写字母开头，其他为大写字母或者下划线", e);
            }
        }
    }

}
