package cn.minloha.ChatCheck;

import cn.minloha.algorithm.Compere;
import cn.minloha.algorithm.English;
import cn.minloha.muiu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/*** 机器人主类
 * @author Minloha
 */
public class TalkWithMuiu implements CommandExecutor {
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
    File robot = new File(f, "plugins/Muiu/robot.yml");
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            // /spirit
            Player player = (Player) commandSender;
            if(strings.length!=0){
                StringBuilder Question = new StringBuilder();
                for(String t : strings){
                    Question.append(t);
                }
                FileConfiguration config = YamlConfiguration.loadConfiguration(robot);
                int thesaurusize = config.getInt("thesaurusize");
                int t = 0;
                synchronized (new ReentrantLock()) {
                    if (thesaurusize != 0) {
                        for (int i = 1; i < thesaurusize; i++) {
                            List<Map<String,String>> list = (List<Map<String,String>>) config.getList("text" + i);
                            for(Map<String,String> m : list){
                                if(m.get("question")!=null){
                                    double sim = Compere.getSimilarity(m.get("question"), String.valueOf(Question));
                                    if (sim - config.getDouble("similar") > 0) {
                                        t = i;
                                        break;
                                    }
                                }
                            }
                        }
                        String str = Question.toString().replaceAll("[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]","");
                        boolean hanzi = true;
                        for(char c : str.toCharArray()){
                            if (!Compere.isHanZi(c)) {
                                hanzi = false;
                                break;
                            }
                        }
                        for(Map<String,String> m : (List<Map<String,String>>) config.getList("text" + t)){
                            if(m.get("back")!=null){
                                for (Player value : Bukkit.getServer().getOnlinePlayers()) {
                                    value.sendMessage("<" + player.getName() + "> " + Question);
                                }
                                if(hanzi){
                                    for (Player value : Bukkit.getServer().getOnlinePlayers()) {
                                        value.sendMessage("<" + ChatColor.RED +"Mspirit" + ChatColor.WHITE + "> " + ChatColor.translateAlternateColorCodes('&', m.get("back")));
                                    }
                                }else{
                                    for (Player value : Bukkit.getServer().getOnlinePlayers()) {
                                        value.sendMessage("<" + ChatColor.RED +"Mspirit" + ChatColor.WHITE + "> " + "对不起,原谅我不会汉语之外的语种");
                                    }
                                }
                            }
                        }
                    } else {
                        player.sendMessage("不存在应答字库");
                    }
                }
            }else{
                player.sendMessage("使用:/spirit [你所问的问题](仅中文)");
            }
        }else{
            System.out.println("指令只允许玩家执行");
        }

        return false;
    }
}
