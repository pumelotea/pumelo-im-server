package io.pumelo.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtils {

    /**
     * 匹配带路径变量的url 【后面优化，将取消通过正则匹配的实现方式】
     * @param dbUrl 持久化的url格式为 {value1}/xxx/{value2}/yyy/{value3}/zzz
     * @param requestUrl 请求的路径是带有路径变量的URL
     *                   其中【rqRegex】这里的请求参数正则表达式支持字符 包括英文，数字，下划线与横线（uuid有横线）。
     * @return
     */
//    public static boolean matcherPathvariable(String dbUrl,String requestUrl){
//        String[] dbSplit = dbUrl.split("\\/"),
//                rqSplit = requestUrl.split("\\/");
//        if (dbSplit.length != rqSplit.length){
//            return false;
//        }
//        String dbRegex = "[\\{]{1}[\\w\\d_-]+[\\}]{1}";
//        String rqRegex = "[\\w\\d_-]+";
//        int count = 0;
//        for (int i = 0; i < dbSplit.length; i++) {
//            String dbStr = dbSplit[i];
//            String rqStr = rqSplit[i];
//            if ((dbStr.matches(dbRegex) && rqStr.matches(rqRegex))
//                    || dbStr.compareTo(rqStr)==0){
//                count++;
//            }
//        }
//        return count== dbSplit.length;
//    }

    /**
     * path中的变量只能出现uuid
     * @param dbUrl
     * @param requestUrl
     * @return
     */
    public static boolean matcherPathvariable(String dbUrl,String requestUrl){
        String dbRegex = "\\{[^\\}]+\\}";
        String rqRegex = "[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}";
        Pattern r1 = Pattern.compile(dbRegex);
        Pattern r2 = Pattern.compile(rqRegex);
        Matcher m1= r1.matcher(dbUrl);
        Matcher m2= r2.matcher(requestUrl);
        String string1 = m1.replaceAll("{#}");
        String string2 = m2.replaceAll("{#}");
        return string1.equals(string2);
    }

}
