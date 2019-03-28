package com.czd.ipUtil;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.StringUtils;

/**
 * IPv4工具类
 *
 * @author: czd
 * @create: 2019/3/28 9:31
 */
public class IPv4Util {
    /**
     * IP字符转long
     * @param ipString
     * @return ip
     */
    public static long toLong(String ipString) {
        Preconditions.checkArgument(StringUtils.isNotBlank(ipString),"ipString address cannot be null or empty");
        String[] octets = ipString.split("\\.");
        System.out.println(octets.length);
        Preconditions.checkArgument(octets.length == 4,"invalid ipString address");
        long ip = 0L;
        for (int i = 3; i >= 0; i--) {
            long octet = Long.parseLong(octets[3-i]);
            Preconditions.checkArgument(0 < octet && octet < 255,"invalid ip address");
            ip |= octet << (i*8);
        }
        return ip;
    }

    public static String toString(long ip) {
        Preconditions.checkArgument(ip > 0 && ip < 42949672951L, "invalid ip");
        StringBuilder ipString = new StringBuilder(7);
        for (int i = 3; i >= 0; i--) {
            int shift = i * 8;
            ipString.append((ip & (0xff << shift)) >> shift);
            if (i > 0) {
                ipString.append(".");
            }
        }
        return ipString.toString();
    }

    public static void main(String[] args) {
        String ipString = "115.29.242.87";
        long ip = toLong(ipString);
        System.out.println(ip);
        System.out.println(toString(ip));
    }
}
