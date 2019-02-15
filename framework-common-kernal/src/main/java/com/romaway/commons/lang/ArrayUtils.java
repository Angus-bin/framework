/**
 * 
 */
package com.romaway.commons.lang;

/**
 * @author duminghui
 * 
 */
public class ArrayUtils
{
    /**
     * 拷贝数组 如果copyCount长度大于数组长度，将拷贝数组长度的数据
     * 
     * @param src
     * @param startIndex
     * @param copyCount
     * @return
     */
    public static int[][] copyArray(int[][] src, int startIndex, int copyCount)
    {
        int result[][] = null;
        if (src != null && src[0] != null)
        {
            result = new int[src.length][];
            if (startIndex > src[0].length)
            {
                return null;
            }
            if (startIndex + copyCount > src[0].length)
            {
                copyCount = src[0].length - startIndex;
            }
            for (int index = 0; index < src.length; index++)
            {
                result[index] = new int[copyCount];
                System.arraycopy(src[index], startIndex, result[index], 0,
                        copyCount);
            }
        }
        return result;
    }

    /**
     * 取数组值（检查数组边界，若越界就赋予默认值）
     * 
     * @param src
     * @param index
     * @param defaultValue
     */
    public static int getValue(int[] src, int index, int defaultValue)
    {

        if (src == null || index < 0 || index >= src.length)
        {
            return defaultValue;
        }

        return src[index];
    }

    /**
     * 取数组值（检查数组边界，若越界就赋予默认值）
     * 
     * @param src
     * @param index
     * @param defaultValue
     * @return
     */
    public static String getValue(String[] src, int index, String defaultValue)
    {

        if (src == null || index < 0 || index >= src.length)
        {
            return defaultValue;
        }

        return src[index];
    }

    /**
     * 取二维数组值（检查数组边界，若越界就赋予默认值）
     * 
     * @param src
     * @param index1
     * @param index2
     * @param defaultValue
     * @return
     */
    public static int getValue(int[][] src, int index1, int index2,
            int defaultValue)
    {

        if (src == null || index1 < 0 || index2 < 0 || index1 >= src.length
                || src[index1] == null || index2 >= src[index1].length)
        {
            return defaultValue;
        }

        return src[index1][index2];
    }
    
    
    /**
     * 取二维int数组的第1维数组，防止数组越界及空指针
     * @param src 源数组
     * @param index1 索引
     * @param defaultValue 默认值
     * @return
     */
    public static int[] getValue(int[][] src, int index1,
            int[] defaultValue)
    {

        if (src == null || index1 < 0 || index1 >= src.length
                || src[index1] == null)
        {
            return defaultValue;
        }

        return src[index1];
    }
    
    
}
