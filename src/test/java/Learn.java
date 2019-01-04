import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Learn {
    public static void main(String[] args) {
//        String regEx = "\\{({^\\}+)\\}";
//        // 编译正则表达式
//        Pattern pattern = Pattern.compile(regEx);
//        // 忽略大小写的写法
//        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher("检验编号：{{检验编号}}； 报告日期：{{报告日期}}");
//        while (matcher.find()) {
//            System.out.println(matcher.group(1));
//        }

        Object[] aa = new Object[2];
        System.out.println(aa.getClass().getName());
        String re = "\\{\\{([^\\}]+)\\}\\}";
        String str = "{{您好}}，abcdefg，{{abc}}";

        Pattern p = Pattern.compile(re);
        Matcher m = p.matcher(str);
        while(m.find()){
            System.out.println(m.group(1));
        }
        m.replaceFirst("a");
        System.out.println(str);
    }
}
