package cn.minloha.Command;

import cn.minloha.algorithm.JsonEdit;
import cn.minloha.muiu;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;

public class StopSpeak implements CommandExecutor{
    //File folder = (new Bridge()).folder;
    //File file = new File(folder.getAbsolutePath() + "config.yml");
    //FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            System.out.println("此命令仅允许客户端执行");
        }else {
            File f = null;
            Class aClass = muiu.class;
            Method method;
            {
                try {
                    method = aClass.getMethod("getConfig");
                    f = (File) method.getDefaultValue();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            Player player = (Player) commandSender;
            if (player.isOp() || player.hasPermission("muiu.morbid")) {
                if (strings.length != 3) {
                    player.sendMessage(ChatColor.AQUA + "使用格式:/morbid 玩家名 截至日期(yyyy-MM-dd HH:mm:ss)");
                } else {
                    String forbid_name = strings[0];
                    String time = strings[1]+" " + strings[2];
                    //在配置文件夹内创建stopspeak.json，使用json格式进行存储
                    File file = new File(f, "plugins/Muiu/stopspeak.json");

                    JsonEdit jsonedit = new JsonEdit();
                    JsonObject jo = jsonedit.getFileJsonObject(file);
                    jo.add(String.valueOf(jo.size() + 1), jsonedit.createJson(forbid_name, time));

                    try {
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write("");
                        fileWriter.flush();
                        fileWriter.close();
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
                        bw.write(jo.toString());
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for(int m=0;m<=10;m++){
                        player.sendMessage("");
                    }
                    player.sendMessage(ChatColor.RED + "==========" + ChatColor.AQUA + "[Muiu]" + ChatColor.RED + "==========");
                    player.sendMessage("已经对" + ChatColor.AQUA + forbid_name + "禁言");
                    player.sendMessage(ChatColor.RED + "==========" + ChatColor.AQUA + "[Muiu]" + ChatColor.RED + "==========");
                }
            } else {
                player.sendMessage(ChatColor.RED + "你不具有权限:muiu.morbid");
            }
        }

        return false;
    }
}
