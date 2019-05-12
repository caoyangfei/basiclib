package com.flyang.util.data;

import android.content.res.Resources;
import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;

import com.flyang.util.app.ApplicationUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.TextUtils.substring;

/**
 * @author caoyangfei
 * @ClassName StringUtils
 * @date 2019/4/20
 * ------------- Description -------------
 * 字符串相关
 */
public final class StringUtils {

    private static final char[] BR_TAG = "<BR>".toCharArray();
    public static final String EMPTY = "";
    private static final char[] HexChars = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] IncertitudeChars = {'*', '\\', '/', '\r',
            '\n', '|', '$', '&', '@', '(', ')', '&', '#', ' '};

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为 null 或长度为 0
     *
     * @param s The string.
     * @return {@code true}: yes<br> {@code false}: no
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为 null 或全为空格
     *
     * @param s The string.
     * @return {@code true}: yes<br> {@code false}: no
     */
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     *
     * @param s The string.
     * @return {@code true}: yes<br> {@code false}: no
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两字符串是否相等
     *
     * @param s1 The first string.
     * @param s2 The second string.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean equals(final CharSequence s1, final CharSequence s2) {
        if (s1 == s2) return true;
        int length;
        if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
            if (s1 instanceof String && s2 instanceof String) {
                return s1.equals(s2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param s1 The first string.
     * @param s2 The second string.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean equalsIgnoreCase(final String s1, final String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    /**
     * null 转为长度为 0 的字符串
     *
     * @param s The string.
     * @return {@code ""} if string equals null
     */
    public static String null2Length0(final String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s The string.
     * @return the length of string
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s The string.
     * @return the string with first letter upper.
     */
    public static String upperFirstLetter(final String s) {
        if (s == null || s.length() == 0) return "";
        if (!Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s The string.
     * @return the string with first letter lower.
     */
    public static String lowerFirstLetter(final String s) {
        if (s == null || s.length() == 0) return "";
        if (!Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s The string.
     * @return the reverse string.
     */
    public static String reverse(final String s) {
        if (s == null) return "";
        int len = s.length();
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s The string.
     * @return the DBC string
     */
    public static String toDBC(final String s) {
        if (s == null || s.length() == 0) return "";
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s The string.
     * @return the SBC string
     */
    public static String toSBC(final String s) {
        if (s == null || s.length() == 0) return "";
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * Return the string value associated with a particular resource ID.
     *
     * @param id The desired resource identifier.
     * @return the string value associated with a particular resource ID.
     */
    public static String getString(@StringRes int id) {
        try {
            return ApplicationUtils.getApp().getResources().getString(id);
        } catch (Resources.NotFoundException ignore) {
            return "";
        }
    }

    /**
     * 根据资源id获取String
     *
     * @param id         The desired resource identifier.
     * @param formatArgs The format arguments that will be used for substitution.
     * @return the string value associated with a particular resource ID.
     */
    public static String getString(@StringRes int id, Object... formatArgs) {
        try {
            return ApplicationUtils.getApp().getString(id, formatArgs);
        } catch (Resources.NotFoundException ignore) {
            return "";
        }
    }

    /**
     * 根据资源id获取String[]
     *
     * @param id The desired resource identifier.
     * @return The string array associated with the resource.
     */
    public static String[] getStringArray(@ArrayRes int id) {
        try {
            return ApplicationUtils.getApp().getResources().getStringArray(id);
        } catch (Resources.NotFoundException ignore) {
            return new String[0];
        }
    }

    /**
     * 格式化银行卡卡号为4个数字+空格
     *
     * @param cardNo
     * @return
     */
    public static String formatBankCardNo(String cardNo) {
        if (isNULL(cardNo)) {
            return cardNo;
        }
        String c = cardNo.replaceAll(" ", "");
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < c.length(); i++) {
            formatted.append(c.charAt(i));
            if ((i + 1) % 4 == 0 && i < c.length() - 1) {
                //3、7、11..的时候后面加空格
                formatted.append(" ");
            }
        }
        return formatted.toString();
    }

    /**
     * 截取字符串,从第一个位置开始到第一个separator之间的字符
     *
     * @param str       要处理的字符串
     * @param separator 要截取到的字符所在的位置
     * @return separator之前的字符串
     * <pre>
     *   str="abcddddeefh" separator="d" return="abc"
     *   str="abcddddeefh" separator="null" return="abcddddeefh"
     *   str="abcddddeefh" separator="" return=""
     *  </pre>
     */
    public static String substringBefore(String str, String separator) {
        if (isNULL(str) || separator == null) {
            return str;
        }
        if (separator.length() == 0) {
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 截取字符串,第一个separator之后的字符串
     *
     * @param str       要处理的字符串
     * @param separator 要截取到的字符所在的位置
     * @return separator之后的字符串
     * <pre>
     *   str="abcddddeefh" separator="d" return="dddeefh"
     *   str="abcddddeefh" separator="null" return=""
     *   str="abcddddeefh" separator="" return="abcddddeefh"
     *  </pre>
     */
    public static String substringAfter(String str, String separator) {
        if (isNULL(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 截取字符串,从第一个位置开始到最后一个separator之间的字符
     *
     * @param str       要处理的字符串
     * @param separator 要截取到的字符所在的位置
     * @return separator之前的字符串
     * <pre>
     *   str="abcddddeefh" separator="d" return="abcddd"
     *   str="abcddddeefh" separator="null" return="abcddddeefh"
     *   str="abcddddeefh" separator="" return="abcddddeefh"
     *  </pre>
     */
    public static String substringBeforeLast(String str, String separator) {
        if (isNULL(str) || isNULL(separator)) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 截取字符串,最后一个separator之后的字符串
     *
     * @param str       要处理的字符串
     * @param separator 要截取到的字符所在的位置
     * @return separator之后的字符串
     * <pre>
     *   str="abcddddeefh" separator="d" return="eefh"
     *   str="abcddddeefh" separator="null" return=""
     *   str="abcddddeefh" separator="" return=""
     *  </pre>
     */
    public static String substringAfterLast(String str, String separator) {
        if (isNULL(str)) {
            return str;
        }
        if (isNULL(separator)) {
            return EMPTY;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1 || pos == (str.length() - separator.length())) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 截取str字符串中在open和close之间的字符串
     *
     * @param str   要处理的字符串
     * @param open  开始的位置
     * @param close 结束的位置
     * @return 参数为空返回null
     * <pre>
     * str="abcdefg" open="c" close="k" return=null
     * str="abcdefg" open=null close=null return=null
     * </pre>
     */
    public static String substringBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

    /**
     * @param str   要处理的字符串
     * @param open  开始位置 空返回null
     * @param close 结束位置 空返回null
     * @return 数组
     * <pre>
     *     str="[a][b][c]", open="[" close= "]" return= ["a","b","c"]
     *     str="", open="a" close= "b" return= []
     * </pre>
     */
    public static String[] substringsBetween(String str, String open,
                                             String close) {
        if (str == null || isNULL(open) || isNULL(close)) {
            return null;
        }
        int strLen = str.length();
        if (strLen == 0) {
            return new String[0];
        }
        int closeLen = close.length();
        int openLen = open.length();
        List<String> list = new ArrayList<String>();
        int pos = 0;
        while (pos < (strLen - closeLen)) {
            int start = str.indexOf(open, pos);
            if (start < 0) {
                break;
            }
            start += openLen;
            int end = str.indexOf(close, start);
            if (end < 0) {
                break;
            }
            list.add(str.substring(start, end));
            pos = end + closeLen;
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 用一个字符串替换字符串中的另一个
     *
     * @param text 要处理的字符串
     * @param repl 被替换的字符
     * @param with 替换的字符
     * @param max  替换的个数 -1:全部替换
     * @return 任何字符为空返回text
     * <pre>
     * StringUtils.replace(null, *, *, *)         = null
     * StringUtils.replace("", *, *, *)           = ""
     * StringUtils.replace("any", null, *, *)     = "any"
     * StringUtils.replace("any", *, null, *)     = "any"
     * StringUtils.replace("any", "", *, *)       = "any"
     * StringUtils.replace("any", *, *, 0)        = "any"
     * StringUtils.replace("abaa", "a", null, -1) = "abaa"
     * StringUtils.replace("abaa", "a", "", -1)   = "b"
     * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
     * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
     * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
     * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
     * </pre>
     */
    public static String replace(String text, String repl, String with, int max) {
        if (isNULL(text) || isNULL(repl) || with == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(repl, start);
        if (end == -1) {
            return text;
        }
        int replLength = repl.length();
        int increase = with.length() - replLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(repl, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }


    /**
     * 交换大小写
     *
     * @param str 字符串
     * @return
     */
    public static String swapCase(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        StringBuilder buffer = new StringBuilder(strLen);
        for (int i = 0; i < strLen; i++) {
            char ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                ch = Character.toLowerCase(ch);
            } else if (Character.isTitleCase(ch)) {
                ch = Character.toLowerCase(ch);
            } else if (Character.isLowerCase(ch)) {
                ch = Character.toUpperCase(ch);
            }
            buffer.append(ch);
        }
        return buffer.toString();
    }


    /**
     * 处理Android 中的换行在Unix或者windows上也换行显示
     *
     * @param input
     * @return
     */
    public static String convertNewlines(String input) {
        char[] chars = input.toCharArray();
        int cur = 0;
        int len = chars.length;
        StringBuilder buf = new StringBuilder(len);
        // Loop through each zhex lookin for newlines.
        for (int i = 0; i < len; i++) {
            // If we've found a Unix newline, add BR tag.
            if (chars[i] == '\n') {
                buf.append(chars, cur, i - cur).append(BR_TAG);
                cur = i + 1;
            }
            // If we've found a Windows newline, add BR tag.
            else if (chars[i] == '\r' && i < len - 1 && chars[i + 1] == '\n') {
                buf.append(chars, cur, i - cur).append(BR_TAG);
                i++;
                cur = i + 1;
            }
        }
        // Add whatever chars are left to buffer.
        buf.append(chars, cur, len - cur);
        return buf.toString();
    }

    /**
     * 固定长度插入字符
     *
     * @param input
     * @param length
     * @param fen
     * @return
     */
    public static String formatMachineCode(String input, int length, String fen) {
        if (isNULL(input)) {
            return input;
        }
        String c = input.replaceAll(fen, "");
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < c.length(); i++) {
            formatted.append(c.charAt(i));
            if ((i + 1) % length == 0 && i < c.length() - 1) {
                //3、7、11..的时候后面加空格
                formatted.append("-");
            }
        }
        return formatted.toString();
    }

    /*
     * 判断一字段值是否数字都是字符
     */
    public static boolean isNumber(String numStr) {
        if (isNULL(numStr))
            return false;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(numStr);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 字符串中提出数字
     *
     * @param numStr 字符串
     * @return
     */
    public static String getNumber(String numStr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numStr.length(); i++) {
            char c = numStr.charAt(i);
            if (c != ' ' && isNumber(c + "")) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 获取字符串中数字长度
     *
     * @param string
     * @return
     */
    public static int countNumber(String string) {
        int count = 0;
        if (isNULL(string))
            return 0;
        StringBuilder buff = new StringBuilder(string);
        for (int i = 0; i < buff.length(); i++) {
            if (buff.substring(i, i + 1).matches("[0-9;]+"))
                count++;
        }
        return count;
    }


    /**
     * 判定不是中文的字符数
     *
     * @param string
     * @return
     */
    public static int countNoneChinese(String string) {
        int count = 0;
        if (isNULL(string))
            return 0;
        StringBuilder buff = new StringBuilder(string);
        for (int i = 0; i < buff.length(); i++) {
            if (!(buff.substring(i, i + 1).matches("[\\u4E00-\\u9FA5]+"))) {
                count++;
            }
        }
        return count;
    }

    /**
     * UTF编码转换为真实的字符串
     *
     * @param str UTF 编码列表 \\u格式,支持中英文格式
     * @return UTF编码专字符串
     */
    public static String UTFToString(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            while (str.length() > 0) {
                // 4位长度
                if (str.startsWith("\\u") && str.length() >= 6
                        && str.substring(2, 6).indexOf("\\") == -1) {
                    sb.append((char) Integer.parseInt(str.substring(2, 6), 16));
                    str = str.substring(6);
                    continue;
                }
                // 2位长度
                if (str.startsWith("\\u") && str.length() >= 4
                        && str.substring(2, 4).indexOf("\\") == -1) {
                    sb.append((char) Integer.parseInt(str.substring(2, 4), 16));
                    str = str.substring(4);
                    continue;
                }
                sb.append(str.charAt(0));
                str = str.substring(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return sb.toString();
    }

    /**
     * 字符串数组中是否包含指定的字符串。
     *
     * @param strings       字符串数组
     * @param string        字符串
     * @param caseSensitive 是否大小写敏感
     * @return 包含时返回true，否则返回false
     * @since 0.4
     */
    public static boolean contains(String[] strings, String string,
                                   boolean caseSensitive) {
        for (String string1 : strings) {
            if (caseSensitive) {
                if (string1.equals(string)) {
                    return true;
                }
            } else {
                if (string1.equalsIgnoreCase(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将数组以delim进行连接
     *
     * @param array
     * @param delim
     * @return
     */
    public static String combineArray(Object[] array, String delim) {
        if (array == null)
            return "";
        if (delim == null)
            delim = "";
        StringBuilder result = new StringBuilder();
        for (Object aArray : array)
            result.append(aArray).append(delim);
        if (result.length() > 0 && result.toString().endsWith(delim) && delim.length() > 0) {
            result.delete(result.length() - delim.length(), result.length());
        }
        return result.toString();
    }

    /**
     * 以指定的字符和长度生成一个该字符的指定长度的字符串。
     *
     * @param c      指定的字符
     * @param length 指定的长度
     * @return 最终生成的字符串
     * @since 0.6
     */
    public static String fillString(char c, int length) {
        String ret = "";
        for (int i = 0; i < length; i++) {
            ret += c;
        }
        return ret;
    }

    /**
     * 包括\t tab 键一起清除
     *
     * @param value
     * @return
     */
    public static String trim(String value) {
        if (isNULL(value)) {
            return "";
        } else {
            return replace(value.trim(), "\t", "", -1);
        }
    }

    /**
     * 根据转义列表对字符串进行转义。
     *
     * @param source        待转义的字符串
     * @param escapeCharMap 转义列表
     * @return 转义后的字符串
     * @since 0.6
     */
    public static String escapeCharacter(String source, Map escapeCharMap) {
        if (source == null || source.length() == 0)
            return source;
        if (escapeCharMap.size() == 0)
            return source;
        StringBuilder sb = new StringBuilder();
        StringCharacterIterator sci = new StringCharacterIterator(source);
        for (char c = sci.first(); c != StringCharacterIterator.DONE; c = sci
                .next()) {
            String character = String.valueOf(c);
            if (escapeCharMap.containsKey(character)) {
                character = (String) escapeCharMap.get(character);
            }
            sb.append(character);
        }
        return sb.toString();
    }

    /**
     * 得到字符串的字节长度
     *
     * @param source 字符串
     * @return 字符串的字节长度
     * @since 0.6
     */
    public static int getByteLength(String source) {
        int len = 0;
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            int highByte = c >>> 8;
            len += highByte == 0 ? 1 : 2;
        }
        return len;
    }

    /*
     * 得到字符的 16 位编码 注意 encodeHex (ss.getBytes("GBK")) 转时候 getBytes（参数）不同， 得到对应的
     * GBK 16 位编码
     */
    public static String encodeHex(byte[] bytes, String fen) {
        StringBuilder sb = new StringBuilder();
        int i;
        for (i = 0; i < bytes.length; i++) {
            sb.append(HexChars[(bytes[i] >> 4) & 0xf]);
            sb.append(HexChars[bytes[i] & 0xf]);
            if (fen != null)
                sb.append(fen);
        }
        return sb.toString();
    }

    /*
     * 得到字符的 编码 注意 encodeBytes (ss.getBytes("GBK")) 转时候 getBytes（参数）不同， 得到对应的 编码
     * 会不同
     */
    public static String encodeBytes(byte[] bytes) {
        if (bytes == null)
            return "";
        StringBuilder buff = new StringBuilder();
        for (byte aByte : bytes) {
            buff.append(aByte).append(" ");
        }
        return buff.toString();
    }

    /*
     * 将String 转为 Unicode 编码
     */
    public static String toUTFString(String value) {
        StringBuilder result = new StringBuilder();
        StringBuilder buff = new StringBuilder(value);
        for (int i = 0; i < buff.length(); i++) {
            result.append("\\u").append(toHexString(buff.charAt(i), 4, '0'));
        }
        return result.toString();
    }

    /**
     * @param value char 基本类型 得到的int
     * @param len   返回保持的长度
     * @param pad   不够填补
     * @return 得到字符的 16 位编码
     */
    public static String toHexString(long value, int len, char pad) {
        StringBuilder sb = new StringBuilder(Long.toHexString(value));
        int npad = len - sb.length();
        while (npad-- > 0) {
            sb.insert(0, pad);
        }
        return new String(sb);
    }

    /*
     * 判断是否为空
     */
    public static boolean isNULL(String value) {
        return value == null || value.equals("") || value.length() < 1;
    }


    /**
     * 修复HTTP 判断用户是否输入了 http:// 如果没有就增加 http://
     *
     * @param http url
     * @return 修复HTTP nurl
     */
    public static String mendHttp(String http) {
        if (isNULL(http) || http.length() < 2)
            return "http://";
        if (http.toLowerCase().startsWith("http://")) {
            return http;
        } else {
            return "http://" + http;
        }
    }

    /**
     * 生成一个引用
     *
     * @param s 生成一个引用
     * @return String 生成一个引用
     */
    public static String Quote(String s) {
        if (s == null)
            s = "''";
        return "\'" + s + "\'";
    }

    /**
     * String转boolean
     *
     * @param str
     * @return
     */
    public static boolean toBoolean(String str) {
        return !isNULL(str)
                && (str.equalsIgnoreCase("TRUE") || str.equals("1")
                || str.equalsIgnoreCase("yes")
                || str.equalsIgnoreCase("y")
                || str.equalsIgnoreCase("T")
                || str.equalsIgnoreCase("是") || str
                .equalsIgnoreCase("ok"));
    }

    /**
     * 将double格式化为价格字符串如200,000,000.00
     *
     * @param d
     * @return
     */
    public static String doubleToPriceString(double d) {
        DecimalFormat df = new DecimalFormat("###,###,###.##");
        return df.format(d);
    }


    /**
     * @param sdate      日期字符串
     * @param dateformat 日期格式
     * @return Date 转换为日期
     * @throws ParseException 转换错误
     */
    public static Date toDate(String sdate, String dateformat)
            throws ParseException {
        if (dateformat == null) {
            throw new ParseException("format is null for dateStr:" + sdate, 0);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
        return dateFormat.parse(sdate);
    }

    /**
     * @param sdate      日期字符串
     * @param dateformat 日期格式
     * @return 标准时间Date转换成本地时区对应的Date
     * @throws ParseException 转换错误
     */
    public static Date toLocalDate(String sdate, String dateformat)
            throws ParseException {
        if (dateformat == null) {
            throw new ParseException("format is null for dateStr:" + sdate, 0);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date fromDate = dateFormat.parse(sdate);
        Calendar nowCal = Calendar.getInstance();
        TimeZone localZone = nowCal.getTimeZone();
        dateFormat.setTimeZone(localZone);

        String localTime = dateFormat.format(fromDate);
        return dateFormat.parse(localTime);
    }

    /**
     * 编码是否有效
     *
     * @param text
     * @return boolean
     */
    static public boolean UTF8CodeCheck(String text) {
        String sign = "";
        if (text.startsWith("%e")) {
            for (int p = 0; p != -1; ) {
                p = text.indexOf("%", p);
                if (p != -1) {
                    p++;
                }
                sign += p;
            }
        }
        return sign.equals("147-1");
    }

    /**
     * @param text url
     * @return boolean 是否Utf8Url编码
     */
    static public boolean isUtf8Url(String text) {
        text = text.toLowerCase();
        int p = text.indexOf("%");
        if (p != -1 && text.length() - p > 9) {
            text = text.substring(p, p + 9);
        }
        return UTF8CodeCheck(text);
    }

    /**
     * 判断 子字符串在字符串中出现的次数
     *
     * @param str
     * @param sub
     * @return
     */
    public static int countMatches(String str, String sub) {
        if (isNULL(str) || isNULL(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    /**
     * 判断都是半角
     *
     * @param str 字符串
     * @return 判断都是半角 0:半角 1:混合 2:全部是全角
     */
    public static int getCompareHalf(String str) {
        if (isNULL(str))
            return 0;
        if (str.getBytes().length == str.length()) {
            return 0;
        }
        if (str.getBytes().length == str.length() * 2) {
            return 2;
        }
        if (str.getBytes().length < str.length() * 2
                && str.getBytes().length > str.length()) {
            return 1;
        }
        return 0;
    }

    public static String getPhoneEnd(String phoneNumber) {
        int size = phoneNumber.length();
        String phoneEnd = substring(phoneNumber, size - 4, size);
        return phoneEnd;
    }


}
