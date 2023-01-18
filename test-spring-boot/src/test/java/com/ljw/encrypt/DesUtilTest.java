package com.ljw.encrypt;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2023/1/18 9:59
 */
class DesUtilTest {

    public static String encrypt = "u/L2LILgZ3c=";
    public static String encrypt1 = "97BFiYzh7d+ayratHaOXJw==";

    @org.junit.jupiter.api.Test
    void encrypt() {
        Long aLong = 64L;
        Long aLong1 = 1999999999L;
        encrypt = DesUtil.encrypt(String.valueOf(aLong));
        encrypt1 = DesUtil.encrypt(String.valueOf(aLong1));
        System.out.println(encrypt);
        System.out.println(encrypt1);
    }

    @org.junit.jupiter.api.Test
    void decrypt() {
        System.out.println(DesUtil.decrypt(encrypt));
        System.out.println(DesUtil.decrypt(encrypt1));
    }
}
