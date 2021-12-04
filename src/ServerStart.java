import cn.minloha.MuiuServlet.HtmlHttpServer;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

public class ServerStart {
    public static void load(int time,HttpServer httpServer) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                httpServer.stop(0);
                httpServer.start();
            }
        }, time* 1000L);// 设定指定的时间time,此处为2000毫秒
    }
    public static void main(String[] args) {
        File file = new File("http.properties");
        File HtmlFile = new File("out/index.html");
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
            int port = Integer.parseInt((String) properties.get("port"));
            int time = Integer.parseInt((String) properties.get("time"));
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            httpServer.createContext("/", new HtmlHttpServer(HtmlFile.getAbsolutePath()));
            httpServer.setExecutor(Executors.newFixedThreadPool(20));
            httpServer.start();
            load(time,httpServer);
            System.out.println("已经在->| http://127.0.0.1:" + port + " |<-启动http服务,暂不支持HTTPS");
        } catch (IOException exception) {
            System.out.println("没有http配置文件,请启动服务器或更新");
            exception.printStackTrace();
        }
        System.out.println("非动态加载,不要太过依赖");
    }
}
