package cn.xukai.spark.distributed;

import java.io.Serializable;

/**
 * Created by kaixu on 2017/8/4.
 */
public abstract class Task implements Serializable {
    public void run() {
        System.out.println("run task!");
    }
}
