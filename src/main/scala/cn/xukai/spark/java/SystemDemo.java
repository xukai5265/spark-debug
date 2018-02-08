package cn.xukai.spark.java;

/**
 * Created by kaixu on 2017/8/2.
 * 回调函数
 */
public class SystemDemo {
    public void createTest(String table,CallbackAction callbackAction){
        callbackAction.create(table);
    }
    public void deleteTest(String table,CallbackAction callbackAction){
        callbackAction.delete(table);
    }

    public void createTestInter(String table,CallbackAction1 callbackAction){
        callbackAction.create(table);
    }

    public static void main(String[] args) {

        System.out.println(System.nanoTime());
        System.out.println(System.currentTimeMillis());


        SystemDemo systemDemo = new SystemDemo();

        systemDemo.createTest("name", new CallbackAction() {
            @Override
            public void create(String tableName) {
                System.out.println("create : "+tableName);
            }
        });

        systemDemo.deleteTest("name", new CallbackAction() {
            @Override
            public void delete(String tableName) {
                System.out.println("delete : "+tableName);
            }
        });

        systemDemo.createTestInter("xukai", new CallbackAction1() {
            @Override
            public void create(String name) {
                System.out.println("接口：create : " + name);
            }

            @Override
            public void delete(String name) {

            }
        });

    }
}
