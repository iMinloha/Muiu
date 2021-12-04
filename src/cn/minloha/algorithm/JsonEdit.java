package cn.minloha.algorithm;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***操作Json算法
* @author Minloha
* @apiNote 混乱的算法,会很难懂
* */
public class JsonEdit {
    //创建一个新的json对象
    public JsonObject createJson(String name,String time){
        JsonObject object = new JsonObject();
        object.addProperty(name,time);
        return object;
    }
    //返回目标所在行
    public int readerJson(JsonObject jsonObject,String name){
        int k = -1;
        for(int i=0;i<jsonObject.size();i++){
            JsonObject readObject = (JsonObject)jsonObject.get(String.valueOf(i+1));
            if(readObject.get(name)!=null){
                k=i+1;
            }
        }
        return k;
    }
    /*** 获得json对象
     * @author Minloha
     * @param file 读取file并返回json对象
     */
    public JsonObject getFileJsonObject(File file){
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = new JsonObject();
        try {
            JsonElement jsonElements = parser.parse(new FileReader(file));
            if(!(jsonElements instanceof JsonNull)){
                jsonObject = (JsonObject) jsonElements;
            }
            return jsonObject;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*** 获得json数组
     * @author Minloha
     * @param s 将字符串包裹为json数组
     */
    public List<String> getArrayJson(String s){
        s = s.replaceAll("\\[","");
        s = s.replaceAll("]","");
        Pattern pattern = Pattern.compile("\\{.*?}+?");
        Matcher matcher = pattern.matcher(s);
        List<String> list = new ArrayList<>();
        while (matcher.find()){
            String s1 = matcher.group(0);
            s1 = s1.replaceAll(" ","");
            String s2 = Arrays.toString(s1.split("}"));
            s2 = s2.replaceAll("\\[","");
            s2 = s2.replaceAll("]","");
            list.add(s2+"}");
        }
        return list;
    }
    /***创建Json数组对象
    * @param list 传入Json对象列表
    * */
    public String toArrayJson(List<String> list){
        String backString;
        StringBuilder s = new StringBuilder();
        if(list.size()>2){
            for(int i=0;i<list.size();i++){
                if(i< list.size()-1){
                    s.append(list.get(i)).append(",\n");
                }else if(i >= list.size()-1){
                    s.append("\n").append(list.get(i));
                }
            }
        }else{
            if(!list.isEmpty()){
                for (String value : list) {
                    s.append(value);
                }
            }
        }
        backString = "[\n"+s+"]";
        return backString;
    }
}
