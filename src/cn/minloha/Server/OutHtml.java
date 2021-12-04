package cn.minloha.Server;

import cn.minloha.algorithm.JsonEdit;
import cn.minloha.muiu;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;

import java.io.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class OutHtml{
    File f = null;
    Class<muiu> aClass = muiu.class;
    Method method;
    {
        try {
            method = aClass.getMethod("getFile");
            f = (File) method.getDefaultValue();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    File jsonFile = new File(f, "plugins/Muiu/stopspeak.json");
    File path = new File(f,"plugins/Muiu/out");
    File OutHtmlFile = new File(f,"plugins/Muiu/out/index.html");
    File templete = new File(f,"plugins/Muiu/index.tmp");
    File banList = new File(f,"banned-players.json");

    public void init(){
        if(!path.isDirectory()){
            path.mkdir();
        }else {
            if (OutHtmlFile.exists()) {
                OutHtmlFile.delete();
                try {
                    OutHtmlFile.createNewFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            } else {
                try {
                    OutHtmlFile.createNewFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public void outHtml() throws IOException {
        JsonEdit je = new JsonEdit();
        if(!templete.exists()){
            System.out.println("模板文件不存在,请前往Q群:907661938下载");
        }else {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(templete));
            LineNumberReader lnr = new LineNumberReader(new FileReader(templete));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OutHtmlFile),"gbk"));
            int linenum = 0;
            while (lnr.readLine() != null) {
                linenum++;
            }
            int banLnum = 0;
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(banList));
            while (lineNumberReader.readLine() != null) {
                banLnum++;
            }
            int finalLinenum = linenum;
            int finalBanLnum = banLnum;
                BufferedReader br = new BufferedReader(new FileReader(banList));
                StringBuilder BanString = new StringBuilder();
                for (int n = 1; n < finalBanLnum; n++) {
                    BanString.append(br.readLine());
                }
                String BanStringList = BanString.toString();
                List<String> list = je.getArrayJson(BanStringList);
                synchronized (new ReentrantLock()){
                    for (int i = 0; i < finalLinenum; i++) {
                        try {
                            String s = bufferedReader.readLine();
                            if (s.contains("<%ForbidSpeakList%>")) {
                                JsonObject jo = je.getFileJsonObject(jsonFile);
                                if(jo.size()==0){
                                    bw.append("<div class=\"alert alert-danger\">");
                                    bw.append("Muiu:Not have forbided speak Players");
                                    bw.append("</div>");
                                }else {
                                    for (int j = 0; j < jo.size(); j++) {
                                        JsonElement jsonElement = jo.get(String.valueOf(j+1));
                                        String jsonString = jsonElement.toString();
                                        String newJsonString = jsonString.substring(1, jsonString.length() - 1);
                                        newJsonString = newJsonString.replaceAll("\"", "");
                                        String name = StringUtils.substringBefore(newJsonString, ":");
                                        String date = StringUtils.substringAfter(newJsonString, ":");
                                        name = name.replaceAll("\"", "");
                                        date = date.replaceAll("\"", "");
                                        bw.append("<tr>");
                                        bw.append("<td>");
                                        bw.append(name);
                                        bw.append("</td>");
                                        bw.append("<td>");
                                        bw.append(date);
                                        bw.append("</td>");
                                        bw.append("<td>");
                                        bw.append("<button class=\"btn btn-danger frmv_btn\" name=\"frmv\" value=\"" + name + "\">remove</button>");
                                        bw.append("</td>");
                                        bw.append("</tr>");
                                    }
                                }
                                bw.flush();
                            } else if (s.contains("<%BanList%>")) {
                                for (String s3 : list) {
                                    JsonObject jsonObject = (JsonObject) (new JsonParser().parse(s3));
                                    String uuid = jsonObject.get("uuid").getAsString();
                                    String prvuuid = uuid;
                                    uuid = uuid.substring(0, 6) + "******" + uuid.substring(29);
                                    String name = jsonObject.get("name").getAsString();
                                    String created = jsonObject.get("created").getAsString();
                                    created = created.substring(0, 10) + " " + created.substring(10, 18);
                                    String source = jsonObject.get("source").getAsString();
                                    String expires = jsonObject.get("expires").getAsString();
                                    String reason = jsonObject.get("reason").getAsString();
                                    if (jsonObject.size()==0) {
                                        bw.append("<div class=\"alert alert-danger\">");
                                        bw.append("Muiu:Not have baned Players");
                                        bw.append("</div>");
                                    } else {
                                        bw.append("<tr>");
                                        bw.append("<td>");
                                        bw.append(uuid);
                                        bw.append("</td>");
                                        bw.append("<td>");
                                        bw.append(name);
                                        bw.append("</td>");
                                        bw.append("<td>");
                                        bw.append(created);
                                        bw.append("</td>");
                                        bw.append("<td>");
                                        bw.append(source);
                                        bw.append("</td>");
                                        bw.append("<td>");
                                        bw.append(expires);
                                        bw.append("</td>");
                                        bw.append("<td>");
                                        bw.append(reason);
                                        bw.append("</td>");
                                        bw.append("<td>");
                                        bw.append("<button class=\"btn btn-danger brmv_btn\" name=\"brmv\" value=\"" + name + "\">remove</button>");
                                        bw.append("</td>");
                                        bw.append("</tr>");
                                    }
                                    bw.flush();
                                }
                            } else {
                                bw.append(s);
                                bw.flush();
                            }
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                    try {
                        bw.close();
                        br.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
                Bukkit.getLogger().info("[Muiu]:成功导出Muiu/out/index.html");
            }
    }
}
