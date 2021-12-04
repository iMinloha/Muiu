package cn.minloha.Command;

import cn.minloha.Server.Version;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Helpme implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            File file = new File("plugins/Muiu", "config.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            boolean isallows = config.getBoolean("isallows");
            if(isallows){
                player.sendMessage(ChatColor.AQUA + "=============================[MUIU]===============================");
                player.sendMessage(ChatColor.AQUA + "使用" + (new Version()).OpenName + "必须要知道的事情:");
                player.sendMessage(ChatColor.AQUA + (new Version()).OpenName + ",高级语言分析插件");
                player.sendMessage(ChatColor.AQUA + "具有对玩家语言进行分析如发送网址,可以判断网址是否存在证书,更安全");
                player.sendMessage(ChatColor.AQUA + "具有完全自主开发的禁言指令,完全隔离的环境,更放心");
                player.sendMessage(ChatColor.AQUA + "具有优化服务的操作,支持在线查看封禁列表与禁言列表,更方便");
                player.sendMessage(ChatColor.AQUA + (new Version()).Version );
                player.sendMessage(ChatColor.AQUA + "=============================[MUIU]===============================");
            }
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
