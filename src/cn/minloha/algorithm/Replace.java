package cn.minloha.algorithm;

import java.util.ArrayList;
import java.util.List;

/***文本重复部分替换
* @author Minloha
* @apiNote 开发困难,求点星星
* */
public class Replace {
    public String getResult(String str){
        List<String> data = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            String s = str.substring(i, i + 1);
            if (!data.contains(s)) {
                data.add(s);
            }
        }

        StringBuilder result = new StringBuilder();
        for (String s : data) {
            result.append(s);
        }
        return result.toString();
    }
}
