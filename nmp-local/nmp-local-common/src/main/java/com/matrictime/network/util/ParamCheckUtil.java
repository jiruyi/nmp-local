package com.matrictime.network.util;

import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数校验工具类
 */
public class ParamCheckUtil {

    /**
     * 验证日期参数不能早于今天
     *
     * @param dateStr
     * @param sdf          日期字符串格式， 可空，默认是yyyy-MM-dd HH:mm:ss
     * @param canNull      参数是否可空
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkDateStrBeforeToday(String dateStr, SimpleDateFormat sdf, boolean canNull, String exceptionMsg) throws SystemException {

        if (StringUtils.isBlank(dateStr)) {
            if (canNull) {
                return;
            } else {
                throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
            }
        }

        Date date = DateUtils.formatStringToDate(dateStr, sdf);
        checkDateStrBeforeToday(date, canNull, exceptionMsg);
    }

    /**
     * 验证日期参数不能早于今天
     *
     * @param date
     * @param canNull      参数是否可空
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkDateStrBeforeToday(Date date, boolean canNull, String exceptionMsg) throws SystemException {

//        if (date == null) {
//            if (canNull) {
//                return;
//            } else {
//                throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
//            }
//        }
//
//        DateTime queryDate = new DateTime(date);
//        if (date == null) {
//            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
//        }
//        DateTime nowDateTime = DateTime.now();
//        // 构造今天的日期（不包含时间）
//        DateTime nowDate = DateTime.parse(
//                nowDateTime.getYear() + "-" + nowDateTime.getMonthOfYear() + "-" + nowDateTime.getDayOfMonth());
//        if (queryDate.isBefore(nowDate)) { // 查询当天之前的时间
//            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
//        }
    }

    /**
     * 检查日期字符串格式是不是yyyy-MM-dd HH:mm:ss
     *
     * @param paramStr
     * @param canNull      参数是否可空
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkDateStrFormat(String paramStr, String format, boolean canNull, String exceptionMsg)
            throws SystemException {

        if (StringUtils.isBlank(paramStr)) {
            if (canNull) {
                return;
            } else {
                throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
            }
        }
        Date date;
        if (Objects.isNull(format)) {
            date = DateUtils.formatStringToDate(paramStr);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = DateUtils.formatStringToDate(paramStr, sdf);
        }
        if (date == null) {

            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }

    /**
     * 检查日期字符串格式是不是yyyy-MM-dd HH:mm:ss
     *
     * @param paramStr
     * @param canNull      参数是否可空
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkDateStr(String paramStr, boolean canNull, String exceptionMsg)
            throws SystemException {

        if (StringUtils.isBlank(paramStr)) {
            if (canNull) {
                return;
            } else {
                throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
            }
        }

        Date date = DateUtils.formatStringToDate(paramStr);
        if (date == null) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }


    /**
     * 验证字符串类型参数不能为空
     *
     * @param paramStr
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkStrBlank(String paramStr, String exceptionMsg) throws SystemException {

        if (StringUtils.isBlank(paramStr)) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }

    /**
     * 验证前端输入字符串类型参数不能为空
     *
     * @param paramStr
     */
    public static boolean checkVoStrBlank(String paramStr) throws SystemException {

        if (StringUtils.isBlank(paramStr) || "null".equals(paramStr.toLowerCase()) || "undefined".equals(paramStr.toLowerCase())) {
            return true;
        }
        return false;
    }

    /**
     * 验证date类型参数不能为空
     *
     * @param paramStr
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkDateBlank(Date paramStr, String exceptionMsg) throws SystemException {
        if (paramStr == null) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }

    /**
     * 验证long类型参数不能为空
     *
     * @param paramStr
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkLongBlank(Long paramStr, String exceptionMsg) throws SystemException {
        if (paramStr == null) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }

    /**
     * 验证long类型大于等于0
     *
     * @param paramStr
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkLongLEQZero(Long paramStr, String exceptionMsg) throws SystemException {
        if (paramStr == null) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
        BigDecimal longBig = new BigDecimal(paramStr);
        if (longBig.compareTo(new BigDecimal(0)) < 0) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }

    /**
     * 验证integer类型参数不能为空
     *
     * @param paramStr
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkIntegerBlank(Integer paramStr, String exceptionMsg) throws SystemException {
        if (paramStr == null) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }


    /**
     * 验证字符串是否全为数字
     *
     * @param paramStr
     * @param canNull      参数是否可空
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkDigitsParam(String paramStr, boolean canNull, String exceptionMsg) throws SystemException {

        if (StringUtils.isBlank(paramStr)) {
            if (canNull) {
                return;
            } else {
                throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
            }
        }

        if (!NumberUtils.isDigits(paramStr)) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }

    }

    /**
     * 验证字符串是否为long型
     *
     * @param paramStr
     * @param canNull      参数是否可空
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkLongParam(String paramStr, boolean canNull, String exceptionMsg) throws SystemException {

        if (StringUtils.isBlank(paramStr)) {
            if (canNull) {
                return;
            } else {
                throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
            }
        }

        try {
            Long.parseLong(paramStr);
        } catch (NumberFormatException e) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }

    }

    /**
     * 验证字符串是否为int型
     *
     * @param paramStr
     * @param canNull      参数是否可空
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkIntParam(String paramStr, boolean canNull, String exceptionMsg) throws SystemException {

        if (StringUtils.isBlank(paramStr)) {
            if (canNull) {
                return;
            } else {
                throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
            }
        }

        try {
            Integer.parseInt(paramStr);
        } catch (NumberFormatException e) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }

    }

    /**
     * 检查参数是不是数值型（int，double，float等）的
     *
     * @param paramStr
     * @param canNull
     * @param exceptionMsg
     * @throws SystemException
     */
    public static void checkNumbersParam(String paramStr, boolean canNull, String exceptionMsg) throws SystemException {

        if (StringUtils.isBlank(paramStr)) {
            if (canNull) {
                return;
            } else {
                throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
            }
        }

        String regex = "^[0-9]*\\.?[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        char c = paramStr.charAt(0);
        if (c == '+' || c == '-') {
            paramStr = paramStr.substring(1);
        }
        Matcher matcher = pattern.matcher(paramStr);
        if (!matcher.matches()) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }

    /**
     * 验证对象参数不能为空
     *
     * @param param
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkObjectBlank(Object param, String exceptionMsg) throws SystemException {

        if (null == param) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }

    /**
     * 验证Collection类型参数不能为空
     *
     * @param param
     * @param exceptionMsg 验证不通过时异常文案
     * @throws SystemException
     */
    public static void checkCollectionEmpty(Collection param, String exceptionMsg) throws SystemException {

        if (CollectionUtils.isEmpty(param)) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }

    /**
     * 范围类型字符串参数检验
     * eg：格式为 1.0-2.0 的Double 范围字符串格式
     *
     * @param rangeParam   范围类型字符串参数
     * @param canNull      参数是否可空
     * @param exceptionMsg 验证不通过时异常文案
     * @return
     */
    public static void validateRangeParam(String rangeParam, boolean canNull, String exceptionMsg) {
        if (StringUtils.isBlank(rangeParam)) {
            if (canNull) {
                return;
            } else {
                throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
            }
        }
        Pattern P_INTEGER = RegexUtil.P_HORIZONTAL_LINE;
        String[] rangeParams = P_INTEGER.split(rangeParam);
        if (rangeParams.length != 2) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }

        String min = rangeParams[0];
        String max = rangeParams[1];
        if (StringUtils.isEmpty(min) || StringUtils.isEmpty(max)
                || Double.parseDouble(min) > Double.parseDouble(max)) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }

    /**
     * 校验手机号,目前只是做了简单的数字校验
     *
     * @param moblie       手机号
     * @param canNull      是否可以为空
     * @param exceptionMsg 验证不通过时异常文案
     */
    public static void checkMoblie(String moblie, boolean canNull, String exceptionMsg) {
        if (StringUtils.isBlank(moblie)) {
            if (canNull) {
                return;
            } else {
                throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
            }
        }
        Matcher matcher = RegexUtil.P_MOBILE_TEL.matcher(moblie);
        if (!matcher.find()) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }

    /**
     * 是否手机号码校验，目前只有基本的11位数字校验
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        boolean isMobile = false;
        Matcher matcher = RegexUtil.P_MOBILE_TEL.matcher(mobile);
        if (matcher.find()) {
            isMobile = true;
        }
        return isMobile;
    }

    /**
     * 正整数校验
     *
     * @param param        需要校验的字符串
     * @param canNull      是否可以为空
     * @param exceptionMsg 验证不通过时异常文案
     */
    public static void checkPositiveInteger(String param, boolean canNull, String exceptionMsg) {
        if (StringUtils.isBlank(param)) {
            if (canNull) {
                return;
            } else {
                throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
            }
        }
        Matcher matcher = RegexUtil.P_POSITIVE_INTEGER.matcher(param);
        if (!matcher.find()) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }

    /**
     * 0和正整数校验
     *
     * @param param        需要校验的字符串
     * @param canNull      是否可以为空
     * @param exceptionMsg 验证不通过时异常文案
     */
    public static void checkInteger(String param, boolean canNull, String exceptionMsg) {
        if (StringUtils.isBlank(param)) {
            if (canNull) {
                return;
            } else {
                throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
            }
        }
        Matcher matcher = RegexUtil.P_INTEGER.matcher(param);
        if (!matcher.find()) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }

//    /**
//     * Object参数校验
//     *
//     * @param object 参数
//     * @return
//     */
//    public static void checkObject(Object object) {
//        Validator validator = new Validator();
//        List<ConstraintViolation> validateResult = validator.validate(object);
//        if (CollectionUtils.isNotEmpty(validateResult)) {
//            throw new SystemException(ErrorCode.PARAM_EXCEPTION, validateResult.get(0).getMessage());
//        }
//    }

    /**
     * 校验表达式
     *
     * @param exp
     * @param exceptionMsg
     * @throws SystemException
     */
    public static void checkBoolean(Boolean exp, String exceptionMsg) throws SystemException {

        if (exp) {
            throw new SystemException(ErrorCode.PARAM_EXCEPTION, exceptionMsg);
        }
    }

    public static void checkListParmas(List<?> list, int i, String errMsg) {
        if (ObjectUtils.isEmpty(list) || list.size() < i) {
            throw new SystemException(errMsg);
        }
    }
}
