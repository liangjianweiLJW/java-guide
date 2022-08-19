package com.ljw.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author WeiC
 * @date 2020/7/28 15:35
 */
public class CnNumberUtils {
    private final static Logger logger = LoggerFactory.getLogger(CnNumberUtils.class);
    //阿拉伯数字对应大写中文数字
    private final static String[] CN_NUMERALS = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    //低单位
    private final static String[] CN_NUM_UNIT = {"", "拾", "佰", "仟"};
    //万元以上高单位
    private final static String[] CN_NUM_HIGHT_UNIT = {"", "万", "亿", "万亿"};
    //小数位单位
    private final static String[] CN_NUM_DECIMAL_UNIT = {"", "角", "分"};


    /**
     * 转换成中文大写金额（最高支持万亿和两位小数）
     * @param num
     * @return
     */
    public static String toUppercase(Double num){
        return toUppercase(String.valueOf(num));
    }

    /**
     * 转换成中文大写金额（最高支持万亿和两位小数）
     * @param num
     * @return
     */
    public static String toUppercase(String num){
        String[] strNumArray = num.split("\\.");
        int strNumArrayLength = strNumArray.length;
        if(strNumArrayLength > 2 || (strNumArrayLength == 2 && strNumArray[1].length() > 2)){
            logger.error("金额格式异常，不能有多个小数点，小数位最多只能两位！");
        }
        BigDecimal bigDecimal = new BigDecimal(strNumArray[0]);
        BigInteger bigInteger = bigDecimal.toBigInteger();

        char[] chars = bigInteger.toString().toCharArray();
        int intLength = chars.length;

        StringBuilder strBuilder = new StringBuilder();
        for(int i=0; i<intLength; i++){
            String curCnNum = CN_NUMERALS[charNumToInt(chars[i])];
            int ui = ((intLength-1)-i)%4;
            int hui = ((intLength-1)-i)/4;
            strBuilder.append(curCnNum);
            if(!isZero(curCnNum)){
                strBuilder.append(CN_NUM_UNIT[ui]);
                String heightUnit = CN_NUM_HIGHT_UNIT[hui];
                //如果剩余没有低金额单位或者后续都为零则加上高单位
                if(ui == 0 || isZeroInResidue(chars, i, ui)){
                    strBuilder.append(heightUnit);
                    //万单位以后的千单位如果不能为零则跳过零
                    if(heightUnit.equals(CN_NUM_HIGHT_UNIT[1]) && !isZeroOfIndex(chars, (ui+1)+i)){
                        i = i+skipZero(chars, i);
                    }
                }
            }
        }
        clearZero(0, strBuilder);
        //小数位转换
        strBuilder.append("圆");
        if(strNumArrayLength == 1 || strNumArray[1].matches("^[0]*$")){
            strBuilder.append("整");
            return strBuilder.toString();
        }
        char[] decimalChars = strNumArray[1].toCharArray();
        int decimalCharLength = decimalChars.length;
        for(int i=1; i<=decimalCharLength; i++){
            String cnNum = CN_NUMERALS[charNumToInt(decimalChars[i-1])];
            if(!isZero(cnNum)){
                strBuilder.append(cnNum).append(CN_NUM_DECIMAL_UNIT[i]);
            }else if(i == decimalCharLength){
                return strBuilder.toString();
            }else{
                strBuilder.append(cnNum);
            }
        }
        return strBuilder.toString();
    }

    /**
     * 递归清除多余的零
     * @param startIndex
     * @param strBuilder
     */
    private static void clearZero(int startIndex, StringBuilder strBuilder){
        int strLength = strBuilder.length();
        int zeroIndex = strBuilder.indexOf(CN_NUMERALS[0], startIndex);
        if(zeroIndex <= -1){
            return;
        }
        if(strLength <= zeroIndex+1){
            strBuilder.deleteCharAt(zeroIndex);
            return;
        }
        if(zeroIndex > -1 && isZero(strBuilder.charAt(zeroIndex+1))){
            strBuilder.deleteCharAt(zeroIndex);
            clearZero(startIndex, strBuilder);
            return;
        }
        if(strLength > zeroIndex+1){
            clearZero(zeroIndex+1, strBuilder);
            return;
        }
    }

    private static boolean isZero(String cnNum){
        return cnNum.equals(CN_NUMERALS[0]);
    }

    private static boolean isZero(char cnNum){
        return isZero(String.valueOf(cnNum));
    }

    /**
     * 跳过为零的下标
     * @param chars
     * @param beginIndex
     * @return
     */
    private static int skipZero(char[] chars, int beginIndex){
        int newIndex = 0;
        int charLength = chars.length;
        for(int i=beginIndex+1; i<charLength; i++){
            if(isZero(CN_NUMERALS[charNumToInt(chars[i])])){
                newIndex++;
            }else{
                break;
            }
        }
        return newIndex;
    }

    /**
     * 指定下标值是否是为零
     * @param chars
     * @param index
     * @return boolean
     */
    private static boolean isZeroOfIndex(char[] chars, int index){
        int numInex = Integer.parseInt(String.valueOf(chars[index]), 10);
        String cnNum = CN_NUMERALS[numInex];
        return isZero(cnNum);
    }

    /**
     * 从begin开始至end结束是否都是零
     * @param chars
     * @param begin
     * @param end
     * @return
     */
    private static boolean isZeroInResidue(char[] chars, int begin, int end){
        for(int i=1; i<=end; i++){
            boolean r = isZeroOfIndex(chars, begin+i);
            if(!r){
                return false;
            }
        }
        return true;
    }

    /**
     * char类型数值转换为int
     * @param charNum
     * @return
     */
    private static int charNumToInt(char charNum){
        String ns = String.valueOf(charNum);
        return Integer.parseInt(ns, 10);
    }
}
