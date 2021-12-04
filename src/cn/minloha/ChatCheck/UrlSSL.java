package cn.minloha.ChatCheck;

import org.bukkit.configuration.file.YamlConfiguration;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***证书判断
* @author Minloha
* */

public class UrlSSL {
    File file = new File("plugins/Muiu", "config.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    /*** 机器人主类
     * @author Minloha
     * @param message 获取字符串中的域名
     */
    public String getURL(String message){
        Pattern pattern = Pattern.compile("[A-Za-z0-9]+." + "[A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|].(com|net|cn|afk|xyz|org|club|top|com.cn|net.cn|gov.cn|org\\.nz|org.cn|" +
                "io|gov|cc|biz|info|co|abc|site|work|group|name|tv|wang|stop|vip|fun|me|tk)");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String s = matcher.group();
            if(!s.contains("https")||!s.contains("http")){
                s = "https://"+s;
            }
            return s;
        }
        return "";
    }
    /*** 机器人主类
     * @author Minloha
     * @param Url 获取url的SSL证书
     */
    public String isSafe(String Url) {
        if (Url != null) {
            try {
                URL url = new URL(Url);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setConnectTimeout(config.getInt("timeout"));
                connection.setReadTimeout(config.getInt("timeout"));
                if (connection.getResponseCode() != 200) {
                    return "&e" + connection.getResponseCode() + "网址";
                } else {
                    connection.connect();
                    for (Certificate certificate : connection.getServerCertificates()) {
                            X509Certificate x509Certificate = (X509Certificate) certificate;
                            Date date = x509Certificate.getNotAfter();
                            Date now = new Date();
                            if (date.getTime() < now.getTime()) {
                                return "&5危险网址";
                            } else {
                                return "&b安全网址";
                            }
                        }
                        connection.disconnect();
                }
                return "&4未知网址";
            } catch (Exception e) {
                return "&4连接超时";
            }
        }else{
            return "&e无效地址";
        }
    }
}