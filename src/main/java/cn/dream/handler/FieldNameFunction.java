package cn.dream.handler;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FieldNameFunction<T> implements Serializable {

    /**
     * Getter方法前缀
     */
    private static final String GETTER_PREFIX = "get";
    /**
     * Setter方法前缀
     */
    private static final String SETTER_PREFIX = "set";
    /**
     * boolean才会使用IS
     */
    private static final String IS_PREFIX = "is";

    private static final String WRITE_REPLACE = "writeReplace";

    /**
     * JNI的基本布尔值的类型
     */
    private static final String JNI_BASE_BOOLEAN = "()Z";



    @FunctionalInterface
    public interface SFunction<T,R> extends Function<T,R>,Serializable {
    }

    @FunctionalInterface
    public interface SSupplier extends Supplier<String>,Serializable {
        default String toPropertyName(){
            String fieldName = get();
            return fieldName.replaceFirst(String.valueOf(fieldName.charAt(0)),String.valueOf(fieldName.charAt(0)).toLowerCase());
        }

        default String toColumnName(){
            String fieldName = get();
            String newFieldName = fieldName.chars().mapToObj(c -> {
                String name = (char)c + "";
                if (Pattern.matches("[A-Z]", name)) {
                    return "_" + name.toLowerCase();
                }
                return name;
            }).collect(Collectors.joining());

            if(newFieldName.startsWith("_")){
                newFieldName = newFieldName.substring(1);
            }
            return newFieldName;
        }

    }

    private final List<SSupplier> FieldSupplierList = new ArrayList<>();

    public static <Entity> FieldNameFunction<Entity> newInstance(){
        return new FieldNameFunction<>();
    }
    public static <Entity> FieldNameFunction<Entity> newInstance(Class<Entity> typeCls){
        return new FieldNameFunction<>();
    }

    /**
     * 添加字段的Getter方法
     * @param sFunction
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public FieldNameFunction<T> addFieldGetMethod(SFunction<T,?> sFunction) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method declaredMethod = sFunction.getClass().getDeclaredMethod(WRITE_REPLACE);
        declaredMethod.setAccessible(true);
        Object invoke = declaredMethod.invoke(sFunction);
        SerializedLambda serializedLambda = (SerializedLambda) invoke;
        FieldSupplierList.add(()->{
            String implMethodName = serializedLambda.getImplMethodName();
            String implMethodSignature = serializedLambda.getImplMethodSignature();
            if(JNI_BASE_BOOLEAN.equals(implMethodSignature)){
                return implMethodName.substring(IS_PREFIX.length());
            }
            return implMethodName.substring(GETTER_PREFIX.length());
        });
        return this;
    }


    /**
     * 获取字段Getter方法列表
     * @return
     */
    public List<SSupplier> getFieldSupplierList(){
        return FieldSupplierList;
    }

}
