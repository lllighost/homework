package treasure;


import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;


/**
 * @Author: ZhangXinLe
 * @CreateTime: 2018-12-06 20:50
 */
public class DigTreasure {


    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        byte[] bytes=Files.readAllBytes(Paths.get("D:\\IdeaProjects\\homework\\day20\\Treasure.class"));
        //自定义类加载器
        ClassLoader cl=new ClassLoader() {
            @Override
            public Class<?> findClass(String name)  {
                return super.defineClass(name,bytes,0,bytes.length);
            }
        };
        //获取类对象
        Class<?> clazz=cl.loadClass("com.westos.Treasure");
        Constructor<?> con=clazz.getDeclaredConstructor();
        con.setAccessible(true);
        Object obj=con.newInstance();
        Arrays.stream(clazz.getMethods()).filter(m->m.getAnnotations().length>0).findFirst().ifPresent((method ->{
            try {
                method.invoke(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } ));

        }
    }

