package cn.minloha.Command;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class unban implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(strings.length==1){
                if(player.hasPermission("muiu.unban")||player.isOp()){
                    String player_name = strings[0];
                    if(Bukkit.getBanList(BanList.Type.NAME).isBanned(player_name)){
                        //Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"pardon "+player_name);
                        Bukkit.getBanList(BanList.Type.NAME).pardon(player_name);
                        player.sendMessage(ChatColor.AQUA + "解除成功");
                    }else{
                        player.sendMessage(ChatColor.AQUA + "玩家没有被封禁");
                    }
                }else{
                    player.sendMessage("你不具有解封权限:muiu.unban");
                }
            }else{
                player.sendMessage(ChatColor.RED + "格式不正确:" + ChatColor.AQUA + " /unban 玩家名");
            }
        }else {
            if(strings.length==1){
                String player_name = strings[0];
                if(Bukkit.getBanList(BanList.Type.NAME).isBanned(player_name)){
                    Bukkit.getBanList(BanList.Type.NAME).pardon(player_name);
                    System.out.println("解除成功");
                }else{
                    System.out.println("玩家没有被封禁");
                }
            }else{
                System.out.println("格式不正确:/unban 玩家名");
            }
        }
        return false;
    }
}
