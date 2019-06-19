package com.flyang.util.data;

import android.support.v4.util.SimpleArrayMap;

import com.flyang.util.constant.RegexConstant;
import com.flyang.util.log.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.flyang.util.constant.RegexConstant.REGEX_CHINESE;
import static com.flyang.util.constant.RegexConstant.REGEX_CHINESE_ALL;
import static com.flyang.util.constant.RegexConstant.REGEX_CHINESE_ALL2;
import static com.flyang.util.constant.RegexConstant.REGEX_CONTAIN_NUMBER;
import static com.flyang.util.constant.RegexConstant.REGEX_EMAIL;
import static com.flyang.util.constant.RegexConstant.REGEX_LETTER;
import static com.flyang.util.constant.RegexConstant.REGEX_NICKNAME;
import static com.flyang.util.constant.RegexConstant.REGEX_NUMBER;
import static com.flyang.util.constant.RegexConstant.REGEX_NUMBER_OR_DECIMAL;
import static com.flyang.util.constant.RegexConstant.REGEX_NUMBER_OR_LETTER;
import static com.flyang.util.constant.RegexConstant.REGEX_PASSWORD;
import static com.flyang.util.constant.RegexConstant.REGEX_REALNAME;
import static com.flyang.util.constant.RegexConstant.REGEX_SPECIAL;
import static com.flyang.util.constant.RegexConstant.REGEX_URL;
import static com.flyang.util.constant.RegexConstant.REGEX_WX;
import static com.flyang.util.data.StringUtils.isEmpty;

/**
 * @author caoyangfei
 * @ClassName RegexUtils
 * @date 2019/4/20
 * ------------- Description -------------
 * 正则相关
 */
public final class RegexUtils {

    private final static SimpleArrayMap<String, String> CITY_MAP = new SimpleArrayMap<>();

    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 简单验证手机号
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMobileSimple(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 精确验证手机号
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMobileExact(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_MOBILE_EXACT, input);
    }

    /**
     * 验证电话号码
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isTel(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_TEL, input);
    }

    /**
     * 验证身份证号码 15 位
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIDCard15(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_ID_CARD15, input);
    }

    /**
     * 简单验证身份证号码 18 位
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIDCard18(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_ID_CARD18, input);
    }

    /**
     * 精确验证身份证号码 18 位
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIDCard18Exact(final CharSequence input) {
        if (isIDCard18(input)) {
            int[] factor = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
            char[] suffix = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
            if (CITY_MAP.isEmpty()) {
                CITY_MAP.put("11", "北京");
                CITY_MAP.put("12", "天津");
                CITY_MAP.put("13", "河北");
                CITY_MAP.put("14", "山西");
                CITY_MAP.put("15", "内蒙古");

                CITY_MAP.put("21", "辽宁");
                CITY_MAP.put("22", "吉林");
                CITY_MAP.put("23", "黑龙江");

                CITY_MAP.put("31", "上海");
                CITY_MAP.put("32", "江苏");
                CITY_MAP.put("33", "浙江");
                CITY_MAP.put("34", "安徽");
                CITY_MAP.put("35", "福建");
                CITY_MAP.put("36", "江西");
                CITY_MAP.put("37", "山东");

                CITY_MAP.put("41", "河南");
                CITY_MAP.put("42", "湖北");
                CITY_MAP.put("43", "湖南");
                CITY_MAP.put("44", "广东");
                CITY_MAP.put("45", "广西");
                CITY_MAP.put("46", "海南");

                CITY_MAP.put("50", "重庆");
                CITY_MAP.put("51", "四川");
                CITY_MAP.put("52", "贵州");
                CITY_MAP.put("53", "云南");
                CITY_MAP.put("54", "西藏");

                CITY_MAP.put("61", "陕西");
                CITY_MAP.put("62", "甘肃");
                CITY_MAP.put("63", "青海");
                CITY_MAP.put("64", "宁夏");
                CITY_MAP.put("65", "新疆");

                CITY_MAP.put("71", "台湾");
                CITY_MAP.put("81", "香港");
                CITY_MAP.put("82", "澳门");
                CITY_MAP.put("91", "国外");
            }
            if (CITY_MAP.get(input.subSequence(0, 2).toString()) != null) {
                int weightSum = 0;
                for (int i = 0; i < 17; ++i) {
                    weightSum += (input.charAt(i) - '0') * factor[i];
                }
                int idCardMod = weightSum % 11;
                char idCardLast = input.charAt(17);
                return idCardLast == suffix[idCardMod];
            }
        }
        return false;
    }

    /**
     * 验证邮箱
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isEmail(final CharSequence input) {
        return isMatch(REGEX_EMAIL, input);
    }

    /**
     * 验证 URL
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isURL(final CharSequence input) {
        return isMatch(REGEX_URL, input);
    }

    /**
     * 验证汉字
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isZh(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_ZH, input);
    }

    /**
     * 验证用户名
     * <p>scope for "a-z", "A-Z", "0-9", "_", "Chinese character"</p>
     * <p>can't end with "_"</p>
     * <p>length is between 6 to 20</p>.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isUsername(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_USERNAME, input);
    }

    /**
     * 验证 yyyy-MM-dd 格式的日期校验，已考虑平闰年
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isDate(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_DATE, input);
    }


    /**
     * 判断是否匹配正则
     *
     * @param regex The regex.
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 获取正则匹配的部分
     *
     * @param regex The regex.
     * @param input The input.
     * @return the list of input matches the regex
     */
    public static List<String> getMatches(final String regex, final CharSequence input) {
        if (input == null) return Collections.emptyList();
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 获取正则匹配分组
     *
     * @param input The input.
     * @param regex The regex.
     * @return the array of strings computed by splitting input around matches of regex
     */
    public static String[] getSplits(final String input, final String regex) {
        if (input == null) return new String[0];
        return input.split(regex);
    }

    /**
     * 替换正则匹配的第一部分
     *
     * @param input       The input.
     * @param regex       The regex.
     * @param replacement The replacement string.
     * @return the string constructed by replacing the first matching
     * subsequence by the replacement string, substituting captured
     * subsequences as needed
     */
    public static String getReplaceFirst(final String input,
                                         final String regex,
                                         final String replacement) {
        if (input == null) return "";
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 替换所有正则匹配的部分
     *
     * @param input       The input.
     * @param regex       The regex.
     * @param replacement The replacement string.
     * @return the string constructed by replacing each matching subsequence
     * by the replacement string, substituting captured subsequences
     * as needed
     */
    public static String getReplaceAll(final String input,
                                       final String regex,
                                       final String replacement) {
        if (input == null) return "";
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }

    /**
     * 通用匹配函数
     *
     * @param regex 正则表达式
     * @param input 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean match(final String regex, final String input) {
        if (!isEmpty(input)) {
            try {
                return Pattern.matches(regex, input);
            } catch (Exception e) {
                LogUtils.e("match", e);
            }
        }
        return false;
    }

    /**
     * 检验数字
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNumber(final String str) {
        return match(REGEX_NUMBER, str);
    }

    /**
     * 检验数字或包含小数点
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNumberDecimal(final String str) {
        return match(REGEX_NUMBER_OR_DECIMAL, str);
    }

    /**
     * 判断字符串是不是全是字母
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLetter(final String str) {
        return match(REGEX_LETTER, str);
    }

    /**
     * 判断字符串是不是包含数字
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContainNumber(final String str) {
        return match(REGEX_CONTAIN_NUMBER, str);
    }

    /**
     * 判断字符串是不是只含字母和数字
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNumberLetter(final String str) {
        return match(REGEX_NUMBER_OR_LETTER, str);
    }

    /**
     * 检验特殊符号
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSpec(final String str) {
        return match(REGEX_SPECIAL, str);
    }

    /**
     * 检验微信号
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isWx(final String str) {
        return match(REGEX_WX, str);
    }

    /**
     * 检验真实姓名
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isRealName(final String str) {
        return match(REGEX_REALNAME, str);
    }

    /**
     * 校验昵称
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNickName(final String str) {
        return match(REGEX_NICKNAME, str);
    }

    /**
     * 校验密码
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPassword(final String str) {
        return match(REGEX_PASSWORD, str);
    }

    /**
     * 校验 IP 地址
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isIPAddress(final String str) {
        return match(RegexConstant.REGEX_IP_ADDR, str);
    }

    /**
     * 校验汉字(无符号, 纯汉字)
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isChinese(final String str) {
        return match(REGEX_CHINESE, str);
    }

    /**
     * 判断字符串是不是全是中文
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isChineseAll(final String str) {
        return match(REGEX_CHINESE_ALL, str);
    }

    /**
     * 判断字符串中包含中文、包括中文字符标点等
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContainChinese(final String str) {
        if (!isEmpty(str)) {
            try {
                int length;
                if (str != null && (length = str.length()) != 0) {
                    char[] dChar = str.toCharArray();
                    for (int i = 0; i < length; i++) {
                        boolean flag = String.valueOf(dChar[i]).matches(REGEX_CHINESE_ALL2);
                        if (flag) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                LogUtils.e("isContainChinese", e);
            }
        }
        return false;
    }

}
