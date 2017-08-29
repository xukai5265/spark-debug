package cn.xukai.spark.ansj

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.ansj.splitWord.analysis.{BaseAnalysis, NlpAnalysis, ToAnalysis}
import org.ansj.splitWord.analysis.DicAnalysis
import org.ansj.splitWord.analysis.IndexAnalysis
import org.ansj.library.DicLibrary
import org.ansj.library.StopLibrary

/*
 * 使用Ansj 5.0.2版本
 * Ansj 5.1.0版本去掉了UserDefineLibrary
 */
object TestAnsj {

  def main(args: Array[String]): Unit = {
    //屏蔽不必要的日志显示在终端上
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.apache.eclipse.jetty.server").setLevel(Level.OFF)

    //1构建spark对象
    val conf = new SparkConf().setAppName("ansj").setMaster("local")
    val sc = new SparkContext(conf)
    val data =  sc.textFile("/Users/syswin/Documents/sparkdata/ansjSource.txt")


    /*
     * 自定义词库
     */
    //添加字典 5.0.2 写法
    //UserDefineLibrary.insertWord("时间戳")
    //移除字典 5.0.2 写法
    //UserDefineLibrary.removeWord("时间戳")

    //添加字典5.1.0  写法
    //DicLibrary.insert(DicLibrary.DEFAULT, "同一份")
    DicLibrary.insert(DicLibrary.DEFAULT, "时间戳")
    //DicLibrary.insert(DicLibrary.DEFAULT, "hbase中")


    /*
     * 加载自定义词库
     * 默认路径 ./library/default.dic
     * 词库格式（"自动义词"[tab]键"词性"[tab]键"词频"）
     * 第一个参数直接默认为 UserDefineLibrary.FOREST
     * 第二个参数词库路径
     * address2.dic 格式
     * 艾泽拉斯    n    1000
     * 雷霆图腾    n    1000
     * 至高岭      n    1000
     *
     * 5.0.2 写法
     * UserDefineLibrary.loadLibrary(UserDefineLibrary.FOREST,"/home/spark/opt/hadoop-2.7/bin/address2.dic")
     *
     * 5.1.0  写法 直接在 默认路径 ./library/default.dic 添加自定义内容
     */

    /*
     * 去停用词
     * 1.根据自定义词去停用词   停用词以后分词以后停用的词不在会出现
     */

    //获取默认路径./library/stop.dic 中的停用词 返回值是StopRecognition对象
    val stopRecognition1 = StopLibrary.get()
    stopRecognition1.insertStopNatures("w") //去除标点符号

    //手动添加停用词
    //val stopRecognition = new StopRecognition()
    //stopRecognition.insertStopWords("通过","hbase")

    /**
    val filter = new FilterRecognition()
    val stopWord = "冠军"
    filter.insertStopWord(stopWord)
    val str = "湖人获得了冠军"           recognition
    val parse6 = ToAnalysis.parse(str).recognition(filter).toStringWithOutNature(" ")
    //println(parse6)

    //2.根据词性去停用词
    val filter1 = new FilterRecognition()
    filter1.insertStopNatures("n")
    val str1 = "湖人获得了冠军"
    val parse7 = ToAnalysis.parse(str1).recognition(filter).toStringWithOutNature(" ")
    //println(parse7)

    //3.根据正则表达式去停用词
    val filter2 = new FilterRecognition()
    //filter2.insertStopRegex()//正则表达式
      **/

    /* 精准分词 (店长推荐款)
     * 它在易用性,稳定性.准确性.以及分词效率上.都取得了一个不错的平衡.
     * 如果你初次尝试Ansj如果你想开箱即用.那么就用这个分词方式是不会错的.
     */
    val parse1 = ToAnalysis.parse("我在艾泽拉斯")

    /* 基础分词 最小颗粒度的分词
     * 基本就是保证了最基本的分词.词语颗粒度最非常小的..所涉及到的词大约是10万左右.
     * 基本分词速度非常快.在macAir上.能到每秒300w字每秒.同时准确率也很高.但是对于新词他的功能十分有限.
     */
    val parse2 = BaseAnalysis.parse("我在艾泽拉斯")

    /* NLP分词 带有新词发现功能的分词
     * nlp分词是总能给你惊喜的一种分词方式.它可以识别出未登录词.
     * 但是它也有它的缺点.速度比较慢.稳定性差.ps:我这里说的慢仅仅是和自己的其他方式比较.应该是40w字每秒的速度吧.
     * 个人觉得nlp的适用方式.1.语法实体名抽取.未登录词整理.只要是对文本进行发现分析等工作
     */
    val parse3 = NlpAnalysis.parse("我在艾泽拉斯")

    /* 用户自定义词典优先策略的分词
     * 用户自定义词典优先策略的分词,如果你的用户自定义词典足够好,
     * 或者你的需求对用户自定义词典的要求比较高,那么强烈建议你使用DicAnalysis的分词方式.
     * 可以说在很多方面Dic优于ToAnalysis的结果
     */
    val parse4 = DicAnalysis.parse("我在艾泽拉斯")

    /* 面向索引的分词
     * 面向索引的分词。顾名思义就是适合在lucene等文本检索中用到的分词。主要考虑以下两点
     * 召回率
     * 召回率是对分词结果尽可能的涵盖。比如对“上海虹桥机场南路” 召回结果是[上海/ns, 上海虹桥机场/nt, 虹桥/ns, 虹桥机场/nz, 机场/n, 南路/nr]
     * 准确率
     * 其实这和召回本身是具有一定矛盾性的Ansj的强大之处是很巧妙的避开了这两个的冲突 。比如我们常见的歧义句“旅游和服务”->对于一般保证召回 。
     * 大家会给出的结果是“旅游 和服 服务” 对于ansj不存在跨term的分词。意思就是。召回的词只是针对精准分词之后的结果的一个细分。比较好的解决了这个问题
     */
    val parse5 = IndexAnalysis.parse("我在艾泽拉斯")


    /************************spark测试**********************************/
    //toStringWithOutNature 去掉词性 可以加参数指定分隔符
    //基础分词
    //val data1 = data.map(text => BaseAnalysis.parse(text))    //recognition  过滤停用词
    val data1 = data.map(text => ToAnalysis.parse(text).recognition(stopRecognition1).toStringWithOutNature(" "))
    data1.foreach { x => println(x) }

    //精准分词
    val data2 = data.map(text => NlpAnalysis.parse(text))
    //data2.foreach { x => println(x.toStringWithOutNature()) }

    //NLP分词
    val data3 = data.map(text => NlpAnalysis.parse(text).toStringWithOutNature(" "))
    //data3.foreach { x => println(x) }

  }
}

/*
 HBase中通过row和columns确定的为一个存贮单元称为cell。显示每个元素，每个 cell都保存着同一份数据的多个版本。版本通过时间戳来索引。
 */


/**
# 1. 名词  (1个一类，7个二类，5个三类)
名词分为以下子类：
n 名词
nr 人名
nr1 汉语姓氏
nr2 汉语名字
nrj 日语人名
nrf 音译人名
ns 地名
nsf 音译地名
nt 机构团体名
nz 其它专名
nl 名词性惯用语
ng 名词性语素
nw 新词
# 2. 时间词(1个一类，1个二类)
t 时间词
tg 时间词性语素
# 3. 处所词(1个一类)
s 处所词
# 4. 方位词(1个一类)
f 方位词
# 5. 动词(1个一类，9个二类)
v 动词
vd 副动词
vn 名动词
vshi 动词“是”
vyou 动词“有”
vf 趋向动词
vx 形式动词
vi 不及物动词（内动词）
vl 动词性惯用语
vg 动词性语素
# 6. 形容词(1个一类，4个二类)
a 形容词
ad 副形词
an 名形词
ag 形容词性语素
al 形容词性惯用语
# 7. 区别词(1个一类，2个二类)
b 区别词
bl 区别词性惯用语
# 8. 状态词(1个一类)
z 状态词
# 9. 代词(1个一类，4个二类，6个三类)
r 代词
rr 人称代词
rz 指示代词
rzt 时间指示代词
rzs 处所指示代词
rzv 谓词性指示代词
ry 疑问代词
ryt 时间疑问代词
rys 处所疑问代词
ryv 谓词性疑问代词
rg 代词性语素
# 10. 数词(1个一类，1个二类)
m 数词
mq 数量词
# 11. 量词(1个一类，2个二类)
q 量词
qv 动量词
qt 时量词
# 12. 副词(1个一类)
d 副词
# 13. 介词(1个一类，2个二类)
p 介词
pba 介词“把”
pbei 介词“被”
# 14. 连词(1个一类，1个二类)
c 连词
 cc 并列连词
# 15. 助词(1个一类，15个二类)
u 助词
uzhe 着
ule 了 喽
uguo 过
ude1 的 底
ude2 地
ude3 得
usuo 所
udeng 等 等等 云云
uyy 一样 一般 似的 般
udh 的话
uls 来讲 来说 而言 说来
uzhi 之
ulian 连 （“连小学生都会”）
# 16. 叹词(1个一类)
e 叹词
# 17. 语气词(1个一类)
y 语气词(delete yg)
# 18. 拟声词(1个一类)
o 拟声词
# 19. 前缀(1个一类)
h 前缀
# 20. 后缀(1个一类)
k 后缀
# 21. 字符串(1个一类，2个二类)
x 字符串
 xx 非语素字
 xu 网址URL
# 22. 标点符号(1个一类，16个二类)
w 标点符号
wkz 左括号，全角：（ 〔  ［  ｛  《 【  〖〈   半角：( [ { <
wky 右括号，全角：） 〕  ］ ｝ 》  】 〗 〉 半角： ) ] { >
wyz 左引号，全角：“ ‘ 『
wyy 右引号，全角：” ’ 』
wj 句号，全角：。
ww 问号，全角：？ 半角：?
wt 叹号，全角：！ 半角：!
wd 逗号，全角：， 半角：,
wf 分号，全角：； 半角： ;
wn 顿号，全角：、
wm 冒号，全角：： 半角： :
ws 省略号，全角：……  …
wp 破折号，全角：——   －－   ——－   半角：---  ----
wb 百分号千分号，全角：％ ‰   半角：%
wh 单位符号，全角：￥ ＄ ￡  °  ℃  半角：$

  **/
