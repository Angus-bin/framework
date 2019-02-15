package android.support.v4.app;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by dell on 2016/3/9.
 * 反射工具类:
 */
public class ReflectionUtils {
    private static final String TAG = "ReflectionUtils";

    /**
     * 反射创建实例化对象:
     * @param className
     * @param constructorSignature
     * @param arguments
     * @param <T>
     * @return
     */
    public static <T> T newInstance(String className, Class<?>[] constructorSignature,
                              Object[] arguments) throws Exception{
        // 1, Class的装载分了三个阶段: loading，linking和initializing: forName装载Class已初始化, loadClass装载未被link;
        // 2, forName支持数组类型，loadClass不支持数组;
        // 3, loadClass为更底层方法, forName等同于Class.forName("className"，false，loader);
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor(constructorSignature);
        return (T) constructor.newInstance(arguments);
    }

    /**
     * 反射创建实例化对象:
     * @param context
     * @param className
     * @param constructorSignature
     * @param arguments
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Context context, String className, Class<?>[] constructorSignature,
                              Object[] arguments) {
        try {
            Class<?> clazz = context.getClassLoader().loadClass(className);
            Constructor<?> constructor = clazz.getConstructor(constructorSignature);
            return (T) constructor.newInstance(arguments);
        } catch (Exception e) {
            Log.w(TAG, "Cannot instantiate class: " + className, e);
        }
        return null;
    }

    /**
     * 反射修改指定Class类静态变量值(int类型)
     * @param className
     * @param fieldName
     * @param index
     */
    public static void setCurrentFragmentIndex(String className, String fieldName, int index){
        try {
            Class userMainAct = Class.forName(className);
            Field jyIndex = userMainAct.getField(fieldName);
            jyIndex.setInt(null, index);
        }catch (Exception e){

        }
    }
}
