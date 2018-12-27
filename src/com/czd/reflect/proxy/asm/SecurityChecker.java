package com.czd.reflect.proxy.asm;

/**
 * @author: czd
 * @create: 2018/12/26 16:41
 */
public class SecurityChecker {
    public static boolean checkSecurity() {
        System.out.println("SecurityChecker.checkSecurity...");
        if (System.currentTimeMillis()%2 == 0) {
            return false;
        } else {
            return true;
        }
    }
}
