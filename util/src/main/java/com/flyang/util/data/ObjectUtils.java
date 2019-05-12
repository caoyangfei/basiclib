package com.flyang.util.data;

import android.os.Build;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SimpleArrayMap;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import com.flyang.util.log.parser.Parser;
import com.flyang.util.log.parser.ParserManager;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author caoyangfei
 * @ClassName ObjectUtils
 * @date 2019/4/20
 * ------------- Description -------------
 * 对象相关
 */
public final class ObjectUtils {

    // 递归解析子类最大深度
    private static final int MAX_CHILD_LEVEL = 2;
    // 对象为空时展示内容
    private static final String STRING_OBJECT_NULL = "Object[object is null]";
    // 每行最大日志长度 (Android Studio3.1最多2902字符)
    public static final int LINE_MAX = 2800;

    private ObjectUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断对象是否为空
     *
     * @param t The object.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static <T> boolean isBlank(final T t) {
        if (t == null) {
            return true;
        }
        if (t.getClass().isArray() && Array.getLength(t) == 0) {
            return true;
        }
        if (t instanceof CharSequence && t.toString().length() == 0) {
            return true;
        }
        if (t instanceof Collection && ((Collection) t).isEmpty()) {
            return true;
        }
        if (t instanceof Map && ((Map) t).isEmpty()) {
            return true;
        }
        if (t instanceof SimpleArrayMap && ((SimpleArrayMap) t).isEmpty()) {
            return true;
        }
        if (t instanceof SparseArray && ((SparseArray) t).size() == 0) {
            return true;
        }
        if (t instanceof SparseBooleanArray && ((SparseBooleanArray) t).size() == 0) {
            return true;
        }
        if (t instanceof SparseIntArray && ((SparseIntArray) t).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (t instanceof SparseLongArray && ((SparseLongArray) t).size() == 0) {
                return true;
            }
        }
        if (t instanceof LongSparseArray && ((LongSparseArray) t).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (t instanceof LongSparseArray
                    && ((LongSparseArray) t).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否非空
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isNotBlank(T t) {
        return !isBlank(t);
    }


    /**
     * 判断对象是否相等
     *
     * @param o1 The first object.
     * @param o2 The second object.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean equals(final Object o1, final Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }

    /**
     * 检查对象非空
     *
     * @param objects The object.
     * @throws NullPointerException if any object is null in objects
     */
    public static void requireNonNull(final Object... objects) {
        if (objects == null) throw new NullPointerException();
        for (Object object : objects) {
            if (object == null) throw new NullPointerException();
        }
    }

    /**
     * 获取非空或默认对象
     *
     * @param object        The object.
     * @param defaultObject The default object to use with the object is null.
     * @param <T>           The value type.
     * @return the nonnull object or default object
     */
    public static <T> T getOrDefault(final T object, final T defaultObject) {
        if (object == null) {
            return defaultObject;
        }
        return object;
    }

    /**
     * 获取对象哈希值
     *
     * @param o The object.
     * @return the hash code of object
     */
    public static int hashCode(final Object o) {
        return o != null ? o.hashCode() : 0;
    }

    /**
     * 将对象转化为String
     *
     * @param object
     * @return
     */
    public static String objectToString(Object object) {
        return objectToString(object, 0);
    }

    /**
     * 将对象转化为String
     *
     * @param object
     * @return
     */
    public static String objectToString(Object object, int childLevel) {
        if (object == null) {
            return STRING_OBJECT_NULL;
        }
        if (childLevel > MAX_CHILD_LEVEL) {
            return object.toString();
        }
        List<Parser> parserList = ParserManager.getInstance().getParseList();
        if (parserList != null) {
            for (Parser parser : parserList) {
                if (parser.parseClassType().isAssignableFrom(object.getClass())) {
                    return parser.parseString(object);
                }
            }
        }
        if (isArray(object)) {
            return parseArray(object);
        }
        if (object.toString().startsWith(object.getClass().getName() + "@")) {
            StringBuilder builder = new StringBuilder();
            getClassFields(object.getClass(), builder, object, false, childLevel);
            Class superClass = object.getClass().getSuperclass();
            if (superClass != null) {
                while (!superClass.equals(Object.class)) {
                    getClassFields(superClass, builder, object, true, childLevel);
                    superClass = superClass.getSuperclass();
                }
            } else {
                builder.append(object.toString());
            }
            return builder.toString();
        } else {
            // 若对象重写toString()方法默认走toString()
            return object.toString();
        }
    }

    /**
     * 拼接class的字段和值
     *
     * @param cla
     * @param builder
     * @param o           对象
     * @param isSubClass  死否为子class
     * @param childOffset 递归解析属性的层级
     */
    private static void getClassFields(Class cla, StringBuilder builder, Object o, boolean isSubClass,
                                       int childOffset) {
        if (cla.equals(Object.class)) {
            return;
        }
        if (isSubClass) {
            builder.append(Parser.LINE_SEPARATOR).append(Parser.LINE_SEPARATOR).append("=> ");
        }
        String breakLine = "";
        builder.append(cla.getSimpleName() + " {");
        Field[] fields = cla.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            field.setAccessible(true);
            if (cla.isMemberClass() && !isStaticInnerClass(cla) && i == 0) {
                continue;
            }
            Object subObject = null;
            try {
                subObject = field.get(o);
            } catch (IllegalAccessException e) {
                subObject = e;
            } finally {
                if (subObject != null) {
                    // 解决Instant Run情况下内部类死循环的问题
                    if (!isStaticInnerClass(cla) && (field.getName().equals("$change") || field.getName().equalsIgnoreCase("this$0"))) {
                        continue;
                    }
                    if (subObject instanceof String) {
                        subObject = "\"" + subObject + "\"";
                    } else if (subObject instanceof Character) {
                        subObject = "\'" + subObject + "\'";
                    }
                    if (childOffset < MAX_CHILD_LEVEL) {
                        subObject = objectToString(subObject, childOffset + 1);
                    }
                }
                String formatString = breakLine + "%s = %s, ";
                builder.append(String.format(formatString, field.getName(),
                        subObject == null ? "null" : subObject.toString()));
            }
        }
        if (builder.toString().endsWith("{")) {
            builder.append("}");
        } else {
            builder.replace(builder.length() - 2, builder.length() - 1, breakLine + "}");
        }
    }

    /**
     * 是否为静态内部类
     *
     * @param cla class
     * @return bool
     */
    private static boolean isStaticInnerClass(Class cla) {
        if (cla != null && cla.isMemberClass()) {
            int modifiers = cla.getModifiers();
            return (modifiers & Modifier.STATIC) == Modifier.STATIC;
        }
        return false;
    }

    /**
     * 长字符串转化为短字符串List
     *
     * @param msg 长字符串 (长度 > 4K)
     * @return List
     */
    public static List<String> largeStringToList(String msg) {
        List<String> stringList = new ArrayList<>();
        int index = 0;
        int maxLength = LINE_MAX;
        int countOfSub = msg.length() / maxLength;
        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + maxLength);
                stringList.add(sub);
                index += maxLength;
            }
            stringList.add(msg.substring(index));
        } else {
            stringList.add(msg);
        }
        return stringList;
    }

    /**
     * 将数组内容转化为字符串
     *
     * @param array
     * @return
     */
    public static String parseArray(Object array) {
        StringBuilder result = new StringBuilder();
        traverseArray(result, array);
        return result.toString();
    }


    /**
     * 判断对象是否为数组
     *
     * @param object object
     * @return bool
     */
    public static boolean isArray(Object object) {
        return object.getClass().isArray();
    }

    /**
     * 获取数组类型
     *
     * @param object 如L为int型
     * @return I/D/...
     */
    private static char getType(Object object) {
        if (isArray(object)) {
            String str = object.toString();
            return str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("[") + 2).charAt(0);
        }
        return 0;
    }

    /**
     * 遍历数组
     *
     * @param result
     * @param array
     */
    private static void traverseArray(StringBuilder result, Object array) {
        if (isArray(array)) {
            if (getArrayDimension(array) == 1) {
                switch (getType(array)) {
                    case 'I':
                        result.append(Arrays.toString((int[]) array));
                        break;
                    case 'D':
                        result.append(Arrays.toString((double[]) array));
                        break;
                    case 'Z':
                        result.append(Arrays.toString((boolean[]) array));
                        break;
                    case 'B':
                        result.append(Arrays.toString((byte[]) array));
                        break;
                    case 'S':
                        result.append(Arrays.toString((short[]) array));
                        break;
                    case 'J':
                        result.append(Arrays.toString((long[]) array));
                        break;
                    case 'F':
                        result.append(Arrays.toString((float[]) array));
                        break;
                    case 'C':
                        result.append(Arrays.toString((char[]) array));
                        break;
                    case 'L':
                        Object[] objects = (Object[]) array;
                        result.append("[");
                        for (int i = 0; i < objects.length; ++i) {
                            result.append(ObjectUtils.objectToString(objects[i]));
                            if (i != objects.length - 1) {
                                result.append(",");
                            }
                        }
                        result.append("]");
                        break;
                    default:
                        result.append(Arrays.toString((Object[]) array));
                        break;
                }
            } else {
                result.append("[");
                for (int i = 0; i < ((Object[]) array).length; i++) {
                    traverseArray(result, ((Object[]) array)[i]);
                    if (i != ((Object[]) array).length - 1) {
                        result.append(",");
                    }
                }
                result.append("]");
            }
        } else {
            result.append("not a array!!");
        }
    }

    /**
     * 获取数组的维度, 非数组为0
     *
     * @param object 对象
     * @return
     */
    private static int getArrayDimension(Object object) {
        int dim = 0;
        for (int i = 0; i < object.toString().length(); ++i) {
            if (object.toString().charAt(i) == '[') {
                ++dim;
            } else {
                break;
            }
        }
        return dim;
    }
}
