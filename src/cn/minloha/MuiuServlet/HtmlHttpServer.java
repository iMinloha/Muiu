package cn.minloha.MuiuServlet;

import cn.minloha.Server.OutHtml;
import cn.minloha.algorithm.JsonEdit;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class HtmlHttpServer implements HttpHandler {
    public File path = null;
    File file = new File(System.getProperty("user.dir")+"/http.properties");
    File root = new File(System.getProperty("user.dir").replaceAll("Muiu","").replaceAll("plugins",""));
    Properties properties = new Properties();
    public HtmlHttpServer(String path1){
        path = new File(path1);
    }
    @Override
    public void handle(HttpExchange httpExchange) {
        try {
            StringBuilder responseText = new StringBuilder();
            InputStreamReader isr = new InputStreamReader(new FileInputStream(path), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            int num = 0;
            LineNumberReader lineNumberReader = null;
            try {
                lineNumberReader = new LineNumberReader(new FileReader(path));
                while (lineNumberReader.readLine() != null) {
                    num++;
                }
                for(int i =0;i<num;i++){
                    responseText.append(bufferedReader.readLine()).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            handleResponse(httpExchange, responseText.toString());
            String s = getRequestParam(httpExchange);
            if(s!=null){
                List<String> list = getList(s);
                properties.load(new FileInputStream(file));
                Set<Object> set = properties.keySet();
                boolean k1 = false,k2 = false;
                String playername = list.get(1);
                for(Object o : set){
                    String s2 = (String) o;
                    if(s2.contains("name")){
                        String name = (String) properties.get(s2);
                        if(name.equals(list.get(2))){
                            k1=true;
                        }
                    }else if (s2.contains("password")){
                        String password = (String) properties.get(s2);
                        if(password.equals(list.get(3))){
                            k2=true;
                        }
                    }
                }
                if(k1&k2){
                    if(list.get(0).equals("brmv")){
                        System.out.println("一个使用用户名:" + list.get(2) + "的人删除" + playername + "的封禁,注意保存");
                        unban(root,playername);
                    }else{
                        System.out.println("一个使用用户名:" + list.get(2) + "的人取消" + playername + "的禁言");
                        allowApeak(root,playername);
                    }
                }else{
                    System.out.println("密码不正确");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private List<String> getList(String str){
        List<String> list = new ArrayList<>();
        str = str.replaceAll("\\+","");
        String[] over = str.split("&");
        String method = over[0].split("=")[0];
        String playerName = over[0].split("=")[1];
        list.add(method);
        list.add(playerName);
        String LogName= over[1].replaceAll("name=", "").equals("") ?" ":over[1].replaceAll("name=","");
        String LogPass=over[2].replaceAll("password=","").equals("") ?" ":over[2].replaceAll("password=","");
        list.add(LogName);
        list.add(LogPass);
        return list;
    }
    private String getRequestHeader(HttpExchange httpExchange) {
        Headers headers = httpExchange.getRequestHeaders();
        return headers.entrySet().stream()
                .map((Map.Entry<String, List<String>> entry) -> entry.getKey() + ":" + entry.getValue().toString())
                .collect(Collectors.joining("<br/>"));
    }
    public String getRequestParam(HttpExchange httpExchange) throws Exception {
        String paramStr = "";
        if (httpExchange.getRequestMethod().equals("GET")) {
            paramStr = httpExchange.getRequestURI().getQuery();
        }
        return paramStr;
    }
    private void handleResponse(HttpExchange httpExchange, String responsetext) throws Exception {
        StringBuilder responseContent = new StringBuilder();
        responseContent.append(responsetext);
        String responseContentStr = responseContent.toString();
        byte[] responseContentByte = responseContentStr.getBytes(StandardCharsets.UTF_8);
        httpExchange.getResponseHeaders().add("Content-Type:", "text/html;charset=utf-8");
        httpExchange.sendResponseHeaders(200, responseContentByte.length);
        OutputStream out = httpExchange.getResponseBody();
        out.write(responseContentByte);
        out.flush();
        out.close();
    }
    private void allowApeak(File root,String playername){
        File f = new File(root,"plugins/Muiu/stopspeak.json");
        JsonEdit je2 = new JsonEdit();
        JsonObject jsonObject = je2.getFileJsonObject(f);
        for(int i=0;i<jsonObject.size();i++){
            JsonObject jo2 = (JsonObject) jsonObject.get(String.valueOf(i+1));
            if(jo2.get(playername)!=null){
                jsonObject.remove(String.valueOf(i+1));
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f));
            bufferedWriter.write(jsonObject.toString());
            bufferedWriter.close();
            File jsonFile = new File(root, "/plugins/Muiu/stopspeak.json");
            File OutHtmlFile = new File(root,"/plugins/Muiu/out/index.html");
            File templete = new File(root,"/plugins/Muiu/index.tmp");
            File banList = new File(root,"/banned-players.json");
            (new ReLoadHtml()).Re_write(banList,jsonFile,OutHtmlFile,templete);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void unban(File root,String playername){
        File f = new File(root,"/banned-players.json");
        String banJsonString = "";
        synchronized (new ReentrantLock()){
            int banLnum = 0;
            LineNumberReader lineNumberReader = null;
            try {
                lineNumberReader = new LineNumberReader(new FileReader(f));
                while (lineNumberReader.readLine() != null) {
                    banLnum++;
                }
                BufferedReader br = new BufferedReader(new FileReader(f));
                StringBuilder BanString = new StringBuilder();
                for (int n = 0; n < banLnum; n++) {
                    BanString.append(br.readLine());
                }
                banJsonString = BanString.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JsonEdit je = new JsonEdit();
        List<String> lastList = new ArrayList<>();
        List<String> list = je.getArrayJson(banJsonString);
        for(String s:list){
            if(!s.contains(playername)){
                lastList.add(s);
            }
        }
        String lastString = je.toArrayJson(lastList);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f));
            bufferedWriter.write(lastString);
            bufferedWriter.close();
            File jsonFile = new File(root, "/plugins/Muiu/stopspeak.json");
            File OutHtmlFile = new File(root,"/plugins/Muiu/out/index.html");
            File templete = new File(root,"/plugins/Muiu/index.tmp");
            File banList = new File(root,"/banned-players.json");
            (new ReLoadHtml()).Re_write(banList,jsonFile,OutHtmlFile,templete);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}