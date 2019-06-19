package com.flyang.util.constant;

/**
 * @author caoyangfei
 * @ClassName RegexConstant
 * @date 2019/4/20
 * ------------- Description -------------
 * 正则表达式
 */
public final class RegexConstant {

    //    简单匹配手机号
    public static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";
    /**
     * 精确匹配手机号
     * <p>china mobile: 134(0-8), 135, 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159, 178, 182, 183, 184, 187, 188, 198</p>
     * <p>china unicom: 130, 131, 132, 145, 155, 156, 166, 171, 175, 176, 185, 186</p>
     * <p>china telecom: 133, 153, 173, 177, 180, 181, 189, 199, 191</p>
     * <p>global star: 1349</p>
     * <p>virtual operator: 170</p>
     */
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[0,1,3,5-8])|(18[0-9])|(19[8,9]))\\d{8}$";
    //    匹配电话号码
    public static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";
    //    匹配身份证号码 15 位
    public static final String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    //    简单匹配身份证号码 18 位
    public static final String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    //    匹配邮箱
    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    //    匹配 URL
    public static final String REGEX_URL = "[a-zA-z]+://[^\\s]*";
    //    匹配汉字
    public static final String REGEX_ZH = "^[\\u4e00-\\u9fa5]+$";
    //    匹配用户名
    public static final String REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";
    //    匹配 yyyy-MM-dd 格式的日期校验，已考虑平闰年
    public static final String REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    //    匹配 IP 地址
    public static final String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    //    匹配双字节字符
    public static final String REGEX_DOUBLE_BYTE_CHAR = "[^\\x00-\\xff]";
    //    匹配空白行
    public static final String REGEX_BLANK_LINE = "\\n\\s*\\r";
    //    匹配 QQ .
    public static final String REGEX_QQ_NUM = "[1-9][0-9]{4,}";
    //    匹配中国邮编
    public static final String REGEX_CHINA_POSTAL_CODE = "[1-9]\\d{5}(?!\\d)";
    //    匹配正整数
    public static final String REGEX_POSITIVE_INTEGER = "^[1-9]\\d*$";
    //    匹配负整数
    public static final String REGEX_NEGATIVE_INTEGER = "^-[1-9]\\d*$";
    //    匹配整数
    public static final String REGEX_INTEGER = "^-?[1-9]\\d*$";
    //    匹配非负整数
    public static final String REGEX_NOT_NEGATIVE_INTEGER = "^[1-9]\\d*|0$";
    //    匹配非正整数
    public static final String REGEX_NOT_POSITIVE_INTEGER = "^-[1-9]\\d*|0$";
    //    匹配正浮点数
    public static final String REGEX_POSITIVE_FLOAT = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
    //    匹配负浮点数
    public static final String REGEX_NEGATIVE_FLOAT = "^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";
    // 正则表达式: 验证汉字(含双角符号)
    public static final String REGEX_CHINESE_ALL2 = "[\u0391-\uFFE5]";
    // 正则表达式: 验证汉字
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]+$";
    // 正则表达式: 验证汉字(含双角符号)
    public static final String REGEX_CHINESE_ALL = "^[\u0391-\uFFE5]+$";
    // 正则表达式: 验证 IP 地址
    public static final String REGEX_IP_ADDR = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
    // 正则表达式: 验证密码 (不包含特殊字符)
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,18}$";
    // 正则表达式: 验证昵称
    public static final String REGEX_NICKNAME = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$";
    // 正则表达式: 验证真实姓名 ^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+)*$
    public static final String REGEX_REALNAME = "^[\\u4e00-\\u9fa5]+(•[\\u4e00-\\u9fa5]*)*$|^[\\u4e00-\\u9fa5]+(·[\\u4e00-\\u9fa5]*)*$";
    // 正则表达式: 验证微信号 ^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$
    public static final String REGEX_WX = "^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$";
    // 正则表达式: 不能输入特殊字符 ^[\u4E00-\u9FA5A-Za-z0-9]+$ 或 ^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$
    public static final String REGEX_SPECIAL = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$";
    // 正则表达式: 验证是否数字或者字母
    public static final String REGEX_NUMBER_OR_LETTER = "^[A-Za-z0-9]+$";
    // 正则表达式: 验证是否包含数字
    public static final String REGEX_CONTAIN_NUMBER = ".*\\d+.*";
    // 正则表达式: 验证数字
    public static final String REGEX_NUMBER = "^[0-9]*$";
    // 正则表达式: 验证数字或包含小数点
    public static final String REGEX_NUMBER_OR_DECIMAL = "^[0-9]*[.]?[0-9]*$";
    // 正则表达式: 验证是否全是字母
    public static final String REGEX_LETTER = "^[A-Za-z]+$";
    /**
     * <p> 数字：^[0-9]*$<p>
     * <p> n位的数字：^d{n}$<p>
     * <p> 至少n位的数字：^d{n,}$<p>
     * <p> m-n位的数字：^d{m,n}$<p>
     * <p> 零和非零开头的数字：^(0|[1-9][0-9]*)$<p>
     * <p> 非零开头的最多带两位小数的数字：^([1-9][0-9]*)+(.[0-9]{1,2})?$<p>
     * <p> 带1-2位小数的正数或负数：^(-)?d+(.d{1,2})?$<p>
     * <p> 正数、负数、和小数：^(-|+)?d+(.d+)?$<p>
     * <p> 有两位小数的正实数：^[0-9]+(.[0-9]{2})?$<p>
     * <p> 有1~3位小数的正实数：^[0-9]+(.[0-9]{1,3})?$<p>
     * <p> 非零的正整数：^[1-9]d*$ 或 ^([1-9][0-9]*){1,3}$ 或 ^+?[1-9][0-9]*$<p>
     * <p> 非零的负整数：^-[1-9][]0-9"*$ 或 ^-[1-9]d*$<p>
     * <p> 非负整数：^d+$ 或 ^[1-9]d*|0$<p>
     * <p> 非正整数：^-[1-9]d*|0$ 或 ^((-d+)|(0+))$<p>
     * <p> 非负浮点数：^d+(.d+)?$ 或 ^[1-9]d*.d*|0.d*[1-9]d*|0?.0+|0$<p>
     * <p> 非正浮点数：^((-d+(.d+)?)|(0+(.0+)?))$ 或 ^(-([1-9]d*.d*|0.d*[1-9]d*))|0?.0+|0$<p>
     * <p> 正浮点数：^[1-9]d*.d*|0.d*[1-9]d*$ 或 ^(([0-9]+.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*.[0-9]+)|([0-9]*[1-9][0-9]*))$<p>
     * <p> 负浮点数：^-([1-9]d*.d*|0.d*[1-9]d*)$ 或 ^(-(([0-9]+.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*.[0-9]+)|([0-9]*[1-9][0-9]*)))$<p>
     * <p> 浮点数：^(-?d+)(.d+)?$ 或 ^-?([1-9]d*.d*|0.d*[1-9]d*|0?.0+|0)$<p>
     * <p> 汉字：^[\u4e00-\u9fa5]{0,}$
     * <p>英文和数字：^[A-Za-z0-9]+$ 或 ^[A-Za-z0-9]{4,40}$<p>
     * <p>长度为3-20的所有字符：^.{3,20}$<p>
     * <p>由26个英文字母组成的字符串：^[A-Za-z]+$<p>
     * <p>由26个大写英文字母组成的字符串：^[A-Z]+$<p>
     * <p>由26个小写英文字母组成的字符串：^[a-z]+$<p>
     * <p>由数字和26个英文字母组成的字符串：^[A-Za-z0-9]+$<p>
     * <p>由数字、26个英文字母或者下划线组成的字符串：^w+$ 或 ^w{3,20}$<p>
     * <p>中文、英文、数字包括下划线：^[\u4E00-\u9FA5A-Za-z0-9_]+$<p>
     * <p>中文、英文、数字但不包括下划线等符号：^[\u4E00-\u9FA5A-Za-z0-9]+$ 或 ^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$<p>
     * <p>可以输入含有^%&',;=?$"等字符：[^%&',;=?$\x22]+<p>
     * <p>禁止输入含有~的字符：[^~\x22]+   <p>
     * <p>Email地址：^w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$<p>
     * <p>域名：[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.?<p>
     * <p>InternetURL：[a-zA-z]+://[^s]* 或 ^http://([w-]+.)+[w-]+(/[w-./?%&=]*)?$<p>
     * <p>手机号码：^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])d{8}$<p>
     * <p>电话号码("XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX)：^((d{3,4}-)|d{3.4}-)?d{7,8}$<p>
     * <p>国内电话号码(0511-4405222、021-87888822)：d{3}-d{8}|d{4}-d{7}<p>
     * <p>身份证号(15位、18位数字)：^d{15}|d{18}$<p>
     * <p>短身份证号码(数字、字母x结尾)：^([0-9]){7,18}(x|X)?$ 或 ^d{8,18}|[0-9x]{8,18}|[0-9X]{8,18}?$<p>
     * <p>帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)：^[a-zA-Z][a-zA-Z0-9_]{4,15}$<p>
     * <p>密码(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)：^[a-zA-Z]w{5,17}$<p>
     * <p>强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间)：^(?=.*d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$<p>
     * <p>日期格式：^d{4}-d{1,2}-d{1,2}<p>
     * <p>一年的12个月(01～09和1～12)：^(0?[1-9]|1[0-2])$<p>
     * <p>一个月的31天(01～09和1～31)：^((0?[1-9])|((1|2)[0-9])|30|31)$<p>
     * <p>钱的输入格式：^([0-9]+|[0-9]{1,3}(,[0-9]{3})*)(.[0-9]{1,2})?$<p>
     * <p>xml文件：^([a-zA-Z]+-?)+[a-zA-Z0-9]+\.[x|X][m|M][l|L]$<p>
     * <p>中文字符的正则表达式：[\u4e00-\u9fa5]<p>
     * <p>双字节字符：[^\x00-\xff] (包括汉字在内，可以用来计算字符串的长度(一个双字节字符长度计2，ASCII字符计1))<p>
     * <p>空白行的正则表达式： s* (可以用来删除空白行)<p>
     * <p>首尾空白字符的正则表达式：^s*|s*$或(^s*)|(s*$) (可以用来删除行首行尾的空白字符(包括空格、制表符、换页符等等)，非常有用的表达式)<p>
     * <p>腾讯QQ号：[1-9][0-9]{4,} (腾讯QQ号从10000开始)<p>
     * <p>中国邮政编码：[1-9]d{5}(?!d) (中国邮政编码为6位数字)<p>
     * <p>IP地址：d+.d+.d+.d+ (提取IP地址时有用)<p>
     * <p>IP地址：((?:(?:25[0-5]|2[0-4]\d|[01]?\d?\d)\.){3}(?:25[0-5]|2[0-4]\d|[01]?\d?\d))<p>
     */
}
