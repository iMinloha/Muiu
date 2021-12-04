package cn.minloha.Command;

import cn.minloha.ChatCheck.SendTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/***弹框方法
 * @author Minloha
 * */
public class Alert implements CommandExecutor{
    SendTitle st = new SendTitle();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (player.isOp()||player.hasPermission("muiu.alert")) {
                if (strings.length==3) {
                    Player luckdog = Bukkit.getPlayer(strings[0]);
                    if(luckdog==null){
                        player.sendMessage("玩家不在线");
                    }else {
                        String title = ChatColor.translateAlternateColorCodes('&',strings[1]);
                        String message = ChatColor.translateAlternateColorCodes('&',strings[2]);
                        int time = 10;
                        try {
                            st.sendTitle(luckdog,title,message,1,time,1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        for(int i =0;i<=20;i++){
                            luckdog.sendMessage(" ");
                        }
                        luckdog.sendMessage(ChatColor.GREEN + "---------->[" + ChatColor.RED + title + ChatColor.GREEN + "]<----------");
                        luckdog.sendMessage(ChatColor.AQUA + message);
                        luckdog.sendMessage("==========处理者:" + ChatColor.RED + player.getName());
                    }
                }else{
                    player.sendMessage(ChatColor.RED + "请使用: /motile 玩家名 标题 消息");
                }
            }else{
                player.sendMessage(ChatColor.DARK_BLUE + "你不具有权限");
            }
        }else{
            System.out.println("不允许控制台执行");
        }
        return false;
    }
}
