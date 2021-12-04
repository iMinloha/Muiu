package cn.minloha;

import cn.minloha.ChatCheck.SummonFile;
import cn.minloha.ChatCheck.TalkWithMuiu;
import cn.minloha.ChatCheck.UrlSSL;
import cn.minloha.Command.*;
import cn.minloha.Server.Version;
import cn.minloha.algorithm.Compere;
import cn.minloha.algorithm.JsonEdit;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*** 主类
 * @author Minloha
 * @version 1.0.0
 */

public class muiu extends JavaPlugin implements Listener{
    //全局变量
    private double Similary;
    private String LibText;
    private static String REGEX_CHINESE = "[\u4e00-\u9fa5]";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    //全局变量定义结束
    //载入
    @Override
    public void onLoad(){
        /*
        * 初始化配置文件以及相关文件
        * */
        (new SummonFile()).CreateFile();
        String Hsy_9s = getDataFolder().getAbsolutePath().replaceAll("Muiu","");
        String Path = getDataFolder().getAbsolutePath().replaceAll("Muiu",(new Version()).MyName);
        File cmdFile = new File(getDataFolder(),"/start.bat");
        BufferedWriter bufferedWriter = null;
        try {
            //当前try内是生成web服务启动的bat脚本
            bufferedWriter = new BufferedWriter(new FileWriter(cmdFile));
            bufferedWriter.write("java -jar " + Path + "\n");
            bufferedWriter.write("pause");
            bufferedWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        //机器人功能
        File robot = new File(getDataFolder(),"robot.yml");
        if(!robot.exists()){
            try {
                //机器人配置文件
                robot.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        //禁言列表
        File file = new File(getDataFolder(),"stopspeak.json");
        File OutHtmlFileFolder = new File(getDataFolder(),"out");
        if(!file.exists()&&!OutHtmlFileFolder.isDirectory()){
            try {
                file.createNewFile();
                OutHtmlFileFolder.mkdir();
            } catch (IOException e) {
                Bukkit.getLogger().info("请输入  reload");
            }
        }
    }
    //运行
    @Override
    public void onEnable(){
        saveDefaultConfig();
        reloadConfig();
        //指令集
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("unban").setExecutor(new unban());
        getCommand("muiu").setExecutor(new Helpme());
        getCommand("morbid").setExecutor(new StopSpeak());
        getCommand("mospk").setExecutor(new AllowSpeak());
        getCommand("moralist").setExecutor(new GetList());
        getCommand("outhit").setExecutor(new OutConsoleInfo());
        getCommand("motile").setExecutor(new Alert());
        getCommand("spirit").setExecutor(new TalkWithMuiu());
        getLogger().info(":" + ChatColor.RED + "插件运行");
    }
    @Override
    public void onDisable(){
        this.getLogger().info(ChatColor.BLUE + "[Muiu]" + ChatColor.RED + "插件卸载");
    }
    /*** 消息处理方法
     * @author Minloha
     * @param message 传递需要同配置文件中的违禁词比较的句子
     */
    public boolean cpr(String message){
        Compere c = new Compere();
        double Similarity01 = 0;
        double Similarity02 = 0;
        List<String> list = getConfig().getStringList("anti");
        boolean can = false;
        for(String str : list){
            String message01 = message.replaceAll(REGEX_CHINESE,"");
            if(!message01.isEmpty()){
                Similarity01 = c.getSimilarity(message01, str);
            }
            String message02 = message.replaceAll("[a-zA-Z]","");
            if(!message02.isEmpty()){
                Similarity02 = c.getSimilarity(message02, str);
            }
            if(Similarity01 > getConfig().getDouble("contain")){
                this.Similary = Similarity01;
                this.LibText = str;
                return true;
            }else if(Similarity02 > getConfig().getDouble("contain")){
                this.Similary = Similarity02;
                this.LibText = str;
                return true;
            }else{
                can = false;
            }
        }
        return can;
    }

    //玩家说话事件
    @EventHandler
    public void PlayerSpeak(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        JsonEdit jsonEdit = new JsonEdit();
        File f = new File(getDataFolder(),"stopspeak.json");
        JsonObject jsonObject = jsonEdit.getFileJsonObject(f);
        int i = jsonEdit.readerJson(jsonObject,player.getName());
            if(i != -1){
                try {
                    Date d2 = sdf.parse(((JsonObject) jsonObject.get(String.valueOf(i))).get(player.getName()).toString().replaceAll("\"",""));
                    if(d2.after(date)){
                        //禁言期限没过，闭嘴吧！
                        event.setCancelled(true);
                        for(int m=0;m<=20;m++){
                            player.sendMessage(" ");
                        }
                        player.sendMessage(ChatColor.RED + "===============" + ChatColor.AQUA + "[禁言]" + ChatColor.RED + "===============");
                        player.sendMessage(ChatColor.RED + "您已经被禁言至" + ((JsonObject) jsonObject.get(String.valueOf(i))).get(player.getName()).toString().replaceAll("\"",""));
                        player.sendMessage(ChatColor.RED + "===============" + ChatColor.AQUA + "[禁言]" + ChatColor.RED + "===============");
                    }else{
                        String new_json = jsonObject.remove(String.valueOf(i)).toString();
                        File file = new File(getDataFolder(),"stopspeak.json");
                        try {
                            FileWriter fileWriter =new FileWriter(file);
                            fileWriter.write("");
                            fileWriter.close();
                            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
                            bw.write(new_json);
                            bw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }//禁言期限是否已过
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else{
                String message = event.getMessage();
                message = message.replace(" ", "");
                boolean can = cpr(message);
                if (can) {
                    player.sendMessage(ChatColor.RED + "敏感词相似度超过阈值");
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.3f, 1.7f);
                    event.setCancelled(true);
                    DecimalFormat df = new DecimalFormat("0.0000");
                    String log = "<" + player.getName() + ">说:|" + message + "|与字符库的文本:"
                            + this.LibText + ",相似度为" + this.Similary + "\n";
                    Bukkit.getLogger().info(log);
                } else {
                    if (getConfig().getBoolean("cerKey")) {
                        UrlSSL u = new UrlSSL();
                        String url = u.getURL(message);
                        if (url != "") {
                            String res = u.isSafe(url);
                            event.setMessage(ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', res) + ChatColor.WHITE + "]" + message);
                        }//拼如安全标签
                    }//文本是否存在Url
                }//判断是否取消说话
            }//判断是否是被封禁
    }
    //反射方法,获得相关内容
    public File getFile(){
        return getDataFolder();
    }
    public FileConfiguration getFileConfiguration(){ return getConfig(); }
}