package cn.minloha.Command;

import cn.minloha.algorithm.JsonEdit;
import cn.minloha.muiu;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.lang.reflect.Method;

public class GetList implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        File f = null;
        Class<muiu> aClass = muiu.class;
        Method method;
        {
            try {
                method = aClass.getMethod("getConfig");
                f = (File) method.getDefaultValue();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        JsonEdit je = new JsonEdit();

        File file = new File(f, "plugins/Muiu/stopspeak.json");

        JsonObject jo = je.getFileJsonObject(file);

        if(!(commandSender instanceof Player)){
            Bukkit.getLogger().info("指令只允许在客户端执行");
        }else{
            Player player = (Player) commandSender;
            if(player.isOp()||player.hasPermission("muiu.seelist")){
                if(strings.length != 1){
                    player.sendMessage("使用:/moralist 页码");
                }else{
                    int pages = Integer.parseInt(strings[0]);
                    player.sendMessage("========" + ChatColor.AQUA + "[Muiu禁言列表第" + pages + "页]" + ChatColor.WHITE + "========");
                    if(jo.size()>pages*10){
                        // 0 1 2 3 4 5 6 7 8 9 (1)
                        // 10 11 12 13 14 15 16 17 18 19 (2)
                        // 20 21 22 23 24 25 26 27 28 29 (3)
                        for(int i = (pages*10)-10;i<=pages*10-1;i++){
                            player.sendMessage(ChatColor.GOLD + String.valueOf(jo.get(String.valueOf(i+1))));
                        }
                    }else if(jo.size()==0){
                        player.sendMessage("还没有人被禁言");
                    }else{
                        for(int i = (pages - 1) * 10; i<jo.size(); i++){
                            player.sendMessage(ChatColor.GOLD + String.valueOf(jo.get(String.valueOf(i+1))));
                        }
                    }
                }
            }else{
                player.sendMessage(ChatColor.RED + "权限不足");
            }
        }
        return false;
    }
}
