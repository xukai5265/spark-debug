package cn.xukai.spark;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaixu on 2018/1/2.
 */
public class Demo4 {
    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        List<String> b = new ArrayList<>();

        System.out.println(a.equals(b)); // true 判断的是类型
        System.out.println(a==b); // false 判断的是否引用相同内存地址
    }
}
