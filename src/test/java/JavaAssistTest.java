import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class JavaAssistTest {
    public static void main(String[] args) throws Exception {
        changeMethod();
    }
    public static void changeMethod() throws Exception {
        ClassPool.getDefault().insertClassPath(
                "C:/java/work/scsy/lib/aspose-words.jar");
        CtClass c2 = ClassPool.getDefault()
                .getCtClass("com.aspose.words.zzZLR");
        CtMethod[] ms = c2.getDeclaredMethods();
        for (CtMethod c : ms) {
            System.out.println(c.getName());
            CtClass[] ps = c.getParameterTypes();
            for (CtClass cx : ps) {
                System.out.println("\t" + cx.getName());
            }

            if (c.getName().equals("zzZ") && ps.length == 4
                    && "byte[]".equals(ps[0].getName())
                    && "byte[]".equals(ps[1].getName())
                    && "java.lang.String".equals(ps[2].getName())
                    && "java.lang.String".equals(ps[3].getName())) {
                System.out.println("find it!" );
                c.insertBefore("{return;}");
            }

        }
        c2.writeFile();

    }
}
