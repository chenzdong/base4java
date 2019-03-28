package com.czd.annotations;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;
import java.util.EnumSet;

/**
 * ���������
 * �������ƹ淶�ı��������
 * ����������������Ϲ淶���������һ����������WARNING��Ϣ
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
         * ���Java��
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
         * ��鷽�������Ƿ�Ϸ�
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if (e.getKind() == ElementKind.METHOD) {
                Name name = e.getSimpleName();
                if (name.contentEquals(e.getEnclosingElement().getSimpleName())) {
                    messager.printMessage(Diagnostic.Kind.WARNING,"һ����ͨ����"+name+"��Ӧ���������ظ�,�����빹�캯������", e);
                }
                checkCamelCase(e, false);
            }
            super.visitExecutable(e, aVoid);
            return null;
        }

        /**
         * ���������Ƿ�Ϸ�
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            // �����������ǳ�������ö���� �򰴴�д�������
            if (e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null || heuristicallyConstant(e)) {
                checkAllCaps(e);
            } else {
                checkCamelCase(e, false);
            }
            return null;
        }

        /**
         * �жϱ����Ƿ�Ϊ����
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
         * �жϴ����Element�Ƿ������ʽ����������������ϣ������������Ϣ
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
                    messager.printMessage(Diagnostic.Kind.WARNING,"����"+name+"Ӧ����Сд��ĸ��ͷ", e);
                    return;
                }
            } else if (Character.isLowerCase(fisrtCodePoint)) {
                if (initalCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING,"����"+name+"Ӧ���Դ�д��ĸ��ͷ", e);
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
                messager.printMessage(Diagnostic.Kind.WARNING,"����"+name+"Ӧ�÷�����ʽ������", e);
            }

        }

        /**
         * ����������飬Ҫ���һ����ĸ�����Ǵ�д��Ӣ����ĸ���������ֿ������»��߻��ߴ�д��ĸ
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
                messager.printMessage(Diagnostic.Kind.WARNING,"����"+name+"Ӧ���Դ�д��ĸ��ͷ������Ϊ��д��ĸ�����»���", e);
            }
        }
    }

}
