package cn.xukai.spark;

import java.util.*;

/**
 * 模拟用户行为
 * Created by kaixu on 2017/12/29.
 * 用户时时刻刻都在浏览商品，将其浏览行为记录，供机器学习预测回头客
 */
public class Taobao implements Runnable{
    public static void main(String[] args) throws InterruptedException {
        /** 最终字段
         * 1. user_id     | 买家id
         * 2. item_id     | 商品id
         * 3. cat_id      | 商品类别id
         * 4. merchant_id | 卖家id
         * 5. brand_id    | 品牌id
         * 6. month       | 交易时间:月
         * 7. day         | 交易事件:日
         * 8. action      | 行为,取值范围{0,1,2,3},0表示点击，1表示加入购物车，2表示购买，3表示关注商品
         * 9. age_range   | 买家年龄分段：1表示年龄<18,2表示年龄在[18,24]，3表示年龄在[25,29]，4表示年龄在[30,34]，5表示年龄在[35,39]，6表示年龄在[40,49]，7和8表示年龄>=50,0和NULL则表示未知
         * 10. gender     | 性别:0表示女性，1表示男性，2和NULL表示未知
         * 11. province   | 收获地址省份
         */

        /**
         * 设想 ：
         *       1. 创建1万个商家Id
         *       2. 创建10个商品分类
         *       3. 用户Id 使用uuid 生成，每次不重复
         *       4. 交易月 和 日 都随机生成
         */
    }





    /**
     * 商家列表
     */
    private static Map<Integer,String> merchant_map = new HashMap<>();

    /**
     * 每个商家拥有的商品 id
     * k = 商家id
     * v = 商品id列表
     */
    private static Map<String,List<String>> item_map = new HashMap<>();

    /**
     * 初始化
     */
    static {
        //1万个商家
        for(int i=0;i<10000;i++){
            String sj_id = UUID.randomUUID().toString().replace("-","");
            merchant_map.put(i,sj_id);
            List<String> items = new ArrayList<>();
            for(int j=0;j<500;j++){
                items.add(UUID.randomUUID().toString().replace("-",""));
            }
            item_map.put(sj_id,items);
        }
    }


    /**
     * 获取消费者所在的省份
     * @return
     */
    private int getProvince(){
        return (int)(28*Math.random());
    }

    /**
     * gender     | 性别:0表示女性，1表示男性，2和NULL表示未知
     * @return
     */
    private int getGender(){
        return (int)(3*Math.random());
    }

    /**
     * 买家年龄分段：
     * 1表示年龄<18,
     * 2表示年龄在[18,24]，
     * 3表示年龄在[25,29]，
     * 4表示年龄在[30,34]，
     * 5表示年龄在[35,39]，
     * 6表示年龄在[40,49]，
     * 7和8表示年龄>=50,
     * 0和NULL则表示未知
     * @return
     */
    private Integer getAges(){
        return (int)(9*Math.random());
    }

    /**
     * 获取用户行为
     * 取值范围{0,1,2,3},0表示点击，1表示加入购物车，2表示购买，3表示关注商品
     * @return
     */
    private int getAction(){
        return (int)(4*Math.random());
    }
    /**
     * 生成用户Id
     * @return
     */
    private String getUserId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 获取月份
     * @return
     */
    private int getMonth(){
        return (int)(12*Math.random()+1);
    }

    /**
     * 根据月份获取天
     * @param month
     * @return
     */
    private int getDay(int month){
        switch (month){
            case 2:
                return (int)(28*Math.random()+1);
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return  (int)(31*Math.random()+1);
            default:
                return (int)(30*Math.random()+1);

        }
    }

    @Override
    public void run() {
        while (true) {
            Taobao taobao = new Taobao();
            int month = taobao.getMonth();
            int day = taobao.getDay(month);
            System.out.println("userId:"+taobao.getUserId());
            System.out.println("月："+month);
            System.out.println("日："+day);

            System.out.println("商家Id：" + merchant_map.get((int)(10000*Math.random())));
            System.out.println("商品列表："+item_map.size());
            System.out.println("用户行为："+taobao.getAction());
            System.out.println("用户年龄段："+taobao.getAges());
            System.out.println("用户性别："+taobao.getGender());
            System.out.println("收货省份："+taobao.getProvince());
            
            System.out.println("====================");
        }
    }
}
