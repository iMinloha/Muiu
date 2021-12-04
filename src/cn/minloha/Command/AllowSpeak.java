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

/***解出禁言
* @author Minloha
* */
public class AllowSpeak implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            System.out.println("此命令仅允许客户端执行");
        } else {
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
            Player player = (Player) commandSender;
            if (player.isOp() || player.hasPermission("muiu.mospk")) {
                if (strings.length != 1) {
                    player.sendMessage(ChatColor.AQUA + "使用格式:/mospk 玩家名");
                } else {
                    String player_name = strings[0];
                    JsonEdit je = new JsonEdit();
                    JsonObject jo = je.getFileJsonObject(new File(f, "plugins/Muiu/stopspeak.json"));
                    int i = je.readerJson(jo, player_name);
                    if (i == -1) {
                        player.sendMessage("玩家没有被禁言");
                    } else {
                        jo.remove(String.valueOf(i));
                        try {
                            FileWriter fileWriter = new FileWriter(new File(f, "plugins/Muiu/stopspeak.json"));
                            fileWriter.write("");
                            fileWriter.flush();
                            fileWriter.close();
                            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(f, "plugins/Muiu/stopspeak.json").getAbsolutePath()));
                            bw.write(jo.toString());
                            bw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        player.sendMessage(ChatColor.RED + "==========" + ChatColor.AQUA + "[Muiu]" + ChatColor.RED + "==========");
                        player.sendMessage("已经对" + ChatColor.AQUA + player_name + "解除禁言");
                        player.sendMessage(ChatColor.RED + "==========" + ChatColor.AQUA + "[Muiu]" + ChatColor.RED + "==========");
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "你不具有权限:muiu.mospk");
            }
        }
        return false;
    }
}
