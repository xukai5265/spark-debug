package cn.xukai.spark.streaming.attention2commodity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * 实时商品关注度 - 数据生成端 （socket）
 * Created by kaixu on 2018/2/5.
 */
public class SimulatorSocket {
    public static void main(String[] args) {
        new Thread(new SimulatorSocketLog()).start();
    }
}
class SimulatorSocketLog implements Runnable{
    //假设一共有200个商品
    private int GOODSID = 200;
    //随机发送消息的条数
    private int MSG_NUM = 30;
    //假设用户浏览该商品的次数
    private int BROWSE_NUM = 5;
    //假设用户浏览商品停留的时间
    private int STAY_TIME = 10;
    //用来体现用户是否收藏，收藏为1，不收藏为0，差评为-1
    int[] COLLECTION = new int[]{-1,0,1};
    //用来模拟用户购买商品的件数，0比较多是为了增加没有买的概率，毕竟不买的还是很多的，很多用户都只是看看
    private int[] BUY_NUM = new int[]{0,1,0,2,0,0,0,1,0};
    public void run() {
        // TODO Auto-generated method stub
        Random r = new Random();

        try {
            /**
             *创建一个服务器端，监听9999端口，客户端就是Streaming，通过看源码才知道，Streaming *socketTextStream 其实就是相当于一个客户端
             */
            ServerSocket sScoket = new ServerSocket(9999);
            System.out.println("成功开启数据模拟模块，去运行Streaming程序把！");
            while(true){
                //随机消息数
                int msgNum = r.nextInt(MSG_NUM)+1;
                //开始监听
                Socket socket = sScoket.accept();
                //创建输出流
                OutputStream os = socket.getOutputStream();
                //包装输出流
                PrintWriter pw = new PrintWriter(os);
                for (int i = 0; i < msgNum; i++) {
                    //消息格式：商品ID::浏览次数::停留时间::是否收藏::购买件数
                    StringBuffer sb = new StringBuffer();
                    sb.append("goodsID-"+(r.nextInt(GOODSID)+1));
                    sb.append("::");
                    sb.append(r.nextInt(BROWSE_NUM)+1);
                    sb.append("::");
                    sb.append(r.nextInt(STAY_TIME)+r.nextFloat());
                    sb.append("::");
                    sb.append(COLLECTION[r.nextInt(2)]);
                    sb.append("::");
                    sb.append(BUY_NUM[r.nextInt(9)]);
                    System.out.println(sb.toString());
                    //发送消息
                    pw.write(sb.toString()+"\n");
                }
                pw.flush();
                pw.close();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    System.out.println("thread sleep failed");
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("port used");
        }

    }
}
