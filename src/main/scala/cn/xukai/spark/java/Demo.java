package cn.xukai.spark.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaixu on 2018/2/2.
 */
public class Demo {
    public static void main(String[] args) {
        List<Integer> aaa = new ArrayList<>();
        aaa.add(1);
        aaa.add(2);
        aaa.remove(new Integer("01"));
//        aaa.remove("01");
        System.out.println(aaa);
    }
}
