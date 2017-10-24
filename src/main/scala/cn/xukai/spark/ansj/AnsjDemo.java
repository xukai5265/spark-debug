package cn.xukai.spark.ansj;

import org.ansj.recognition.impl.StopRecognition;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.FileNotFoundException;

/**
 * Created by kaixu on 2017/8/23.
 */
public class AnsjDemo {
    public static void main(String[] args) throws FileNotFoundException {
        String str = "在这个世界上，无论是何种优秀的产品，有一条亘古不变的真理：“木秀于林风必摧之”。在我天朝车市，同样有着许多这样的例子，就比如今年3月末刚刚上市的广汽本田冠道240TURBO，上市以来外界便有着许多有关动力的质疑，甚至有不少“小马拉大车”的声音。但这些，真的就是事实么？或许用一组数据便能反驳，上市短短不到三个月，冠道240TURBO便帮助冠道全系达成订单5万辆的壮举，而7月冠道全系销量更是达到了7500辆！很难想象，这是一款刚刚上市的中大型豪华SUV的成绩。如此佳绩，却仍在被质疑动力性能明显不足，难道说所有消费者都被蒙蔽、毫无理智可言？！答案自然是否定的，能够受到市场的广泛认可，便足以证明冠道240TURBO自身的优秀。" ;
        StopRecognition stopRecognition  = new StopRecognition();
        stopRecognition.insertStopRegexes("w");
//        // 精准分词
//        System.out.println("精准分词： "+ToAnalysis.parse(str));
//        // 基础分词
//        System.out.println("基础分词： "+BaseAnalysis.parse(str));
//        // NLP 分词
//        Result nlpRes = NlpAnalysis.parse(str);
//        String res = nlpRes.toStringWithOutNature(" ");
//        System.out.println("NLP分词1： "+res);
//        String res1 = nlpRes.toStringWithOutNature("-");
//        System.out.println("NLP分词2： "+res1);
        String res = ToAnalysis.parse(str).recognition(stopRecognition).toStringWithOutNature(" ");
        System.out.println(res);
    }
}
