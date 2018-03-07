package cn.xukai.spark.ml.bayes_src.src;

import java.util.*;

/**
 * Created by kaixu on 2018/3/7.
 * 贝叶斯实现类
 * 参考文献： https://yq.aliyun.com/articles/113512?t=t1
 */
public class BayesImpl {
    Map map = new HashMap<String,Integer>();

    // 先验概率
    private double game = 0.0;
    private double notGame = 0.0;
    // 将属于sport 类型的所有数据都放入集合中
    private List<String> gameList = new ArrayList<>();
    // 将不属于sport 类型的所有数据都放入集合中
    private List<String> notGameList = new ArrayList<>();
    // 将所有的单词去重后放入到dataSet中
    private Set dataSet = new HashSet<String>();
    /**
     * 初始化数据
     */
    public BayesImpl() {
        this.map.put("a great game",1);
        this.map.put("the election was over",0);
        this.map.put("very clean match",1);
        this.map.put("a clean but forgetable game",1);
        this.map.put("it was a close election",0);
        processData(map);
    }
    public BayesImpl(Map<String,Integer>map){
        this.map = map;
        processData(map);
    }

    /**
     * 将 数据写入对应的集合中
     * @param map
     */
    private void processData(Map<String,Integer> map){
        int g = 0;
        int n = 0;
        for(Map.Entry<String,Integer> entry :map.entrySet()){
            if(entry.getValue()==1){
                g++;
                for(String str :entry.getKey().split(" ")){
                    gameList.add(str);
                    // 将所有的单词放入 dataSet 中去重
                    dataSet.add(str);
                }
            }else {
                n++;
                for(String str :entry.getKey().split(" ")){
                    notGameList.add(str);
                    // 将所有的单词放入 dataSet 中去重
                    dataSet.add(str);
                }
            }
        }
        // 计算先验概率
        game=g*1.0/(g+n)*1.0;
        notGame= n*1.0/(g+n)*1.0;
        System.out.println(dataSet.size());
        System.out.println("game:"+game);
        System.out.println("notGame:"+notGame);
    }

    /**
     * 预测数据
     * @param line
     * @return
     */
    private Integer predictData(String line){
        double a1 = 1.0;
        double a2 = 1.0;
        /**
         * 计算line 中每个单词 出现在sport 的概率
         * P(sport|a) * P(sport|very) * P(sport|close) * P(sport|game) * P(game)
         */
        for(String str : line.split(" ")){
            a1 *= gameProcess(contins(str,gameList));
        }
        a1 = a1* game;
        System.out.println("a1  ="+a1);
        /**
         * 计算line 中每个单词 出现在notSport 的概率
         * P(notSport|a) * P(notSport|very) * P(notSport|close) * P(notSport|game) * P(Notgame)
         */
        for(String str : line.split(" ")){
            a2 *= notGameProcess(contins(str,notGameList));
        }
        a2 = a2*notGame;
        System.out.println("a2  ="+a2);
        double res = a1-a2;
        if(res>0){
            return 1;
        }else{
            return 0;
        }
    }

    /**
     * 拉普拉斯平滑方法
     * 防止出现 某个单词为在gameList or notGameList 中出现导致 计算结果为0
     * 如：close 单词就没有在gameList中出现。
     * 解决思路：
     *         P(sport|close)  0+1 / (dataSet.size() + gameList.size())
     *
     * @param num
     * @return
     */
    private double gameProcess(int num){
        double t = num*1.0;
        double t1 = (t+1);
        int t2 = dataSet.size()+gameList.size();
        double t3 = t1/t2;
        return t3;
    }
    private double notGameProcess(int num){
        double t = num*1.0;
        double t1 = (t+1);
        int t2 = dataSet.size()+notGameList.size();
        return t1/t2;
    }

    /**
     * 计算集合中包含多少个指定的单词
     * @param str
     * @param list
     * @return
     */
    private int contins(String str,List<String> list){
        int i = 0;
        Iterator<String> it = list.iterator();
        while(it.hasNext()){
            String tmp = it.next();
            if(tmp.equals(str)){
                i++;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        BayesImpl bayes = new BayesImpl();
        // step1. 将sport 与 not sport 类的单词放入Set 集合中
        String line = "xkai close the door";
        int res = bayes.predictData(line);
        System.out.println("最终结果"+line+"  属于:"+res);
    }

}
