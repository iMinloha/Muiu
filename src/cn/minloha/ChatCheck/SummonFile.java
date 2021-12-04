package cn.minloha.ChatCheck;

import cn.minloha.muiu;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SummonFile {

    /*** 生成必要的配置文件
     * @author Minloha
     */
    public void CreateFile(){
        File f = null;
        Class<muiu> aClass = muiu.class;
        Method method;
        {
            try {
                method = aClass.getMethod("getFile");
                f = (File) method.getDefaultValue();
                File configFile = new File(f, "plugins/Muiu/config.yml");
                FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                File proFile = new File(f,"plugins/Muiu/http.properties");
                if(!proFile.exists()){
                    proFile.createNewFile();
                }
                Properties properties = new Properties();
                properties.load(new FileInputStream(proFile));
                properties.setProperty("time", String.valueOf(config.getInt("time")));
                properties.setProperty("port", String.valueOf(config.getInt("port")));
                List<?> list = config.getList("serverAdmins");
                int i = 0;
                if(list!=null){
                    for(Object o : list){
                        String s1 = (String) o;
                        for(Map<?,?> m : config.getMapList(s1)){
                            properties.setProperty("name"+i,(String) m.get("name"));
                            properties.setProperty("password"+i,(String) m.get("password"));
                        }
                        i++;
                    }
                }
                properties.list(new PrintStream(new FileOutputStream(proFile)));
            } catch (NoSuchMethodException | IOException e) {
                System.out.println("请先启动服务器");
            }
        }
    }
}
