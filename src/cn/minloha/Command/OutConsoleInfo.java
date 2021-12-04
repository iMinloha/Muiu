package cn.minloha.Command;

import cn.minloha.Server.OutHtml;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class OutConsoleInfo implements CommandExecutor {
    OutHtml outHtml = new OutHtml();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            outHtml.init();
            try {
                outHtml.outHtml();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }else{
            Player p = (Player) commandSender;
            p.sendMessage("指令只允许命令行执行");
        }
        return false;
    }
}
