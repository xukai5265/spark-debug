package cn.xukai.spark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kaixu on 2017/12/29.
 */
public class TaobaoTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        pool.execute(new Taobao());
        pool.execute(new Taobao());
        pool.execute(new Taobao());
        pool.execute(new Taobao());
        pool.execute(new Taobao());
        pool.execute(new Taobao());
        pool.execute(new Taobao());
        pool.execute(new Taobao());
        pool.execute(new Taobao());
        pool.execute(new Taobao());
        pool.shutdown();
    }
}
