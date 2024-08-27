package com.briup.smart.env.server;

import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.util.JDBCUtil;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用线程池的技术实现服务器端程序
 */
public class ServerImpl implements Server {
    private ServerSocket server;
    private ExecutorService threadPool;//线程池对象
    private boolean flag = true;
    public ServerImpl() {
        //创建一个线程池对象:有固定的5个线程对象解析客户端发送数据
        threadPool = Executors.newFixedThreadPool(5);

        //1.创建一个服务器对象
        try{
            server = new ServerSocket(9999);
            System.out.println("接收数据服务器开启");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void receive() throws Exception {
        while (flag){
            System.out.println("等待客户端连接");
            //阻塞的特点：有客户端连接，继续执行
            Socket socket = server.accept();
            System.out.println("有客户端连接成功");
            Runnable r = () ->{
                try {
                    //获取处理发送数据的线程名字
                    String name = Thread.currentThread().getName();
                    //3.通过socket对象获取输入流
                    InputStream is = socket.getInputStream();
                    Thread.sleep(10000);//模拟执行10s 在10s内只能有一个客户端连接，其他客户端等待
                    //4.包装流读操作
                    ObjectInputStream ois = new ObjectInputStream(is);
                    Object o = ois.readObject();
                    //5.类型转化 o指向的对象类型 是否是Collection类型
                    if (o instanceof Collection){
                        Collection<Environment> coll = (Collection<Environment>) o;
                        //System.out.println(name+" 接收到数据大小："+coll.size());
                        DBStoreImpl dbStore = new DBStoreImpl();
                        dbStore.saveDB(coll);





                        //接收到客户端采集的集合对象数据后，保存到数据库中
                        /*coll.forEach(e->{
                            try {
                                //获取集合中的日期属性值中的天 gatherDate=2018-01-19 08:59:56.029
                                Timestamp date = e.getGatherDate();
                                String[] split = date.toString().split("[ ]");
                                String[] split1 = split[0].split("-");
                                String day = split1[2];
                                //获取连接对象
                                Connection conn = dataSourceUtils.createDataSource();
                                PreparedStatement ps1=null;
                                switch (day){
                                    case "1":
                                        //关闭自动提交事务
                                        conn.setAutoCommit(false);
                                        //同构sql
                                        String sql1="INSERT INTO env_detail_1 VALUES" +
                                                "(?,?,?,?,?,?,?,?,?,?)";
                                        //获取预编译对象ps
                                        ps1 = conn.prepareStatement(sql1);
                                        ps1.setString(1,e.getName());
                                        ps1.setString(2,e.getSrcId());
                                        ps1.setString(3,e.getDesId());
                                        ps1.setString(4,e.getDevId());
                                        ps1.setString(5,e.getSensorAddress());
                                        ps1.setInt(6,e.getCount());
                                        ps1.setString(7,e.getCmd());
                                        ps1.setFloat(8,e.getData());
                                        ps1.setInt(9,e.getStatus());
                                        ps1.setTimestamp(10,e.getGatherDate());
                                        ps1.addBatch();
                                        break;
                                    case "2":
                                        //关闭自动提交事务
                                        conn.setAutoCommit(false);
                                        //同构sql
                                        String sql2="INSERT INTO env_detail_2 VALUES" +
                                                "(?,?,?,?,?,?,?,?,?,?)";
                                        //获取预编译对象ps
                                        PreparedStatement ps2 = conn.prepareStatement(sql2);
                                        ps2.setString(1,e.getName());
                                        ps2.setString(2,e.getSrcId());
                                        ps2.setString(3,e.getDesId());
                                        ps2.setString(4,e.getDevId());
                                        ps2.setString(5,e.getSensorAddress());
                                        ps2.setInt(6,e.getCount());
                                        ps2.setString(7,e.getCmd());
                                        ps2.setFloat(8,e.getData());
                                        ps2.setInt(9,e.getStatus());
                                        ps2.setTimestamp(10,e.getGatherDate());
                                        ps2.addBatch();
                                        ps1.executeBatch();
                                        break;
                                    case "3":break;
                                    case "4":break;
                                    case "5":break;
                                    case "6":break;
                                    case "7":break;
                                    case "8":break;
                                    case "9":break;
                                    case "10":break;
                                    case "11":break;
                                    case "12":break;
                                    case "13":break;
                                    case "14":break;
                                    case "15":break;
                                    case "16":break;
                                    case "17":break;
                                    case "18":break;
                                    case "19":break;
                                    case "20":break;
                                    case "21":break;
                                    case "22":break;
                                    case "23":break;
                                    case "24":break;
                                    case "25":break;
                                    case "26":break;
                                    case "27":break;
                                    case "28":break;
                                    case "29":break;
                                    case "30":break;
                                    case "31":break;
                                    default:throw new RuntimeException("日期有误异常");
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }


                        });*/
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            };
            //使用线程池中线程对象每次执行每次客户端的连接处理
            threadPool.execute(r);
        }
    }

    @Override
    public void shutdown() throws Exception {
        //启动一个关闭服务器，当有客户端（浏览器）发送一个关闭指令后，调用close方法关闭接收服务器
        //浏览器访问 localhost:8899  accpet被执行，执行剩余代码（就是关闭接收数据的服务器）
        Runnable r = ()->{
            try {
                ServerSocket shutdownServer = new ServerSocket(8899);
                System.out.println("开启关闭服务器");
                System.out.println("等待关闭信号");
                shutdownServer.accept();
                System.out.println("接收到关闭信息");
                System.out.println("即将关闭接收服务器");
                this.flag = false;
                server.close();//关闭服务器
                threadPool.shutdown();//关闭线程池
                System.out.println("接收服务器关闭");
                shutdownServer.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        //新建一个启动关闭服务器线程。开启线程
        new Thread(r).start();
    }
}
