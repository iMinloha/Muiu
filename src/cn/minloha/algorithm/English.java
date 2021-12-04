package cn.minloha.algorithm;

public class English {
    public static double levenshtein(String str1, String str2) {
        //矩阵向量相似度
        String regList="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        str1 = str1.replaceAll(regList,"");
        str2 = str2.replaceAll(regList,"");
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        Replace rep = new Replace();
        str1 = rep.getResult(str1);
        str2 = rep.getResult(str2);
        int len1 = str1.length();
        int len2 = str2.length();
        int[][] matrix = new int[len1 + 1][len2 + 1];
        for (int a = 0; a <= len1; a++) {
            matrix[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            matrix[0][a] = a;
        }
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                matrix[i][j] = min(matrix[i - 1][j - 1] + temp, matrix[i][j - 1] + 1,
                        matrix[i - 1][j] + 1);
            }
        }
        double similarity = 1 - (double) matrix[len1][len2]
                / Math.max(str1.length(), str2.length());
        return similarity;
    }

    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }
}
