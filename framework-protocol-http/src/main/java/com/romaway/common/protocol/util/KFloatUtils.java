/**
 * 
 */
package com.romaway.common.protocol.util;

/**
 * @author duminghui
 * 
 */
public class KFloatUtils
{

	public final static void keepSame(KFloat start, KFloat end)
	{
		if (start.nUnit == end.nUnit)
			return;
		if (start.nUnit > end.nUnit)
		{
			start.nValue *= KUtils.pow(10, 4 * (start.nUnit - end.nUnit));// 有溢出可能
			start.nUnit = end.nUnit;
		} else
		{
			start.nValue /= KUtils.pow(10, 4 * (end.nUnit - start.nUnit) - 1);
			int round = start.nValue % 10;
			start.nValue /= 10;
			if (round >= 5)
				start.nValue += 1;
			start.nUnit = end.nUnit;
		}
		if (start.nDigit > end.nDigit)
		{
			start.nValue /= KUtils.pow(10, (start.nDigit - end.nDigit) - 1);
			int round = start.nValue % 10;
			start.nValue /= 10;
			if (round >= 5)
				start.nValue += 1;
			start.nDigit = end.nDigit;

		} else if (start.nDigit < end.nDigit)
		{
			start.nValue *= KUtils.pow(10, end.nDigit - start.nDigit);// 有溢出可能
			start.nDigit = end.nDigit;
		}
	}

	/**
	 * 两个浮点数比较(假设单位相等的情况下) 如果前一个值比后一天值大返回1<br>
	 * 小返回-1<br>
	 * 平返回0<br>
	 */
	public static final int compare(KFloat val_1, KFloat val_2)
	{
		int nRtn = 0;
		KFloat val1 = new KFloat(val_1);
		KFloat val2 = new KFloat(val_2);
		prepare(val1, val2, true);
		if (val1.nValue > val2.nValue)
			nRtn = 1;
		else if (val1.nValue < val2.nValue)
			nRtn = -1;
		return nRtn;
	}

	private final static void prepare(KFloat val1, KFloat val2, boolean enlarge)
	{
		while (val1.nDigit != val2.nDigit)
		{
			if (enlarge)
			{
				if (val1.nDigit < val2.nDigit)
				{
					val1.nValue = val1.nValue * 10;
					val1.nDigit++;
				} else if (val1.nDigit > val2.nDigit)
				{
					val2.nValue = val2.nValue * 10;
					val2.nDigit++;
				}
			} else
			{
				if (val1.nDigit > val2.nDigit)
				{
					val1.nValue = val1.nValue / 10;
					val1.nDigit--;
				} else if (val1.nDigit < val2.nDigit)
				{
					val2.nValue = val2.nValue / 10;
					val2.nDigit--;
				}
			}

		}
	}

	/**
	 * 加运算(假设单位相等的情况下),结果保存在val1中
	 */
	public static final void add(KFloat val1, KFloat val2)
	{
		prepare(val1, val2, true);
		val1.nValue += val2.nValue;
	}

	public static final void add(KFloat target, KFloat val1, KFloat val2)
	{
		prepare(val1, val2, true);
		target.init(val1.nValue + val2.nValue, val1.nDigit, val1.nUnit);
	}
    public static final void add(KFloat val1, int val2)
    {
        val1.nValue += val2;
        val1.longVlaue += val2;
    }
	/**
	 * 减
	 * 
	 * @param val1
	 * @param val2
	 */
	public static final void sub(KFloat val1, KFloat val2)
	{
		prepare(val1, val2, true);
		val1.nValue -= val2.nValue;
	}

	public static final void sub(KFloat target, KFloat val1, KFloat val2)
	{
		prepare(val1, val2, true);
		target.init(val1.nValue - val2.nValue, val1.nDigit, val1.nUnit);
	}

	/**
	 * 乘运算
	 */
	public static final void mul(KFloat val1, int val2)
	{
		val1.nValue *= val2;
		val1.longVlaue *= val2;
	}

	/**
	 * 乘运算
	 * 
	 * @param val1
	 * @param val2
	 */
	public final static void mul(KFloat val1, KFloat val2)
	{
		if (val2 == null)
			return;
		int maxDigit = Math.max(val1.nDigit, val2.nDigit);
		while (val1.nUnit != val2.nUnit && val1.nUnit > 0 && val2.nUnit > 0)
		{ // 统一单位,向大单位靠
			if (val1.nUnit > val2.nUnit)
			{
				val2.nUnit--;
				val2.nDigit -= 4;
			} else
			{
				val1.nUnit--;
				val1.nDigit -= 4;
			}
		}
		if (val1.nDigit < 0)
		{
			val1.nValue *= KUtils.pow(10, -val1.nDigit);
		}
		if (val2.nDigit < 0)
		{
			val2.nValue *= KUtils.pow(10, -val2.nDigit);
		}

		long lValue1 = val1.nValue;
		long lValue2 = val2.nValue;
		val1.longVlaue = lValue1 * lValue2;

		val1.nDigit += val2.nDigit;
		val1.nUnit = Math.max(val1.nUnit, val2.nUnit);
		while (val1.nDigit != maxDigit)
		{
			if (val1.nDigit > maxDigit)
			{
				val1.nDigit--;
				val1.longVlaue /= 10L;
			} else
			{
				val1.nDigit++;
				val1.longVlaue *= 10L;
			}
		}
		if (val1.longVlaue > (Integer.MAX_VALUE))
		{
			System.out.println("浮点计算溢出");
		}
		val1.nValue = (int) val1.longVlaue;
	}

	/**
	 * 相除
	 * @param val1
	 * @param val2
	 */
	public final static void div(KFloat val1, int val2)
	{
		if (val1 == null){
			val1 = new KFloat();
		}
		div(val1, val1, val2);
	}

	/**
	 * 除法
	 * @param target  结果值
	 * @param val1  被除数
	 * @param val2 除数
	 */
	public final static void div(KFloat target, KFloat val1, int val2)
	{
		target.init(val1.nValue / val2, val1.nDigit, val1.nUnit);
		target.longVlaue = val1.longVlaue / val2;
	}

	/**
	 * 除法
	 * @param target 结果值
	 * @param val1 被除数
	 * @param val2 除数
	 * @param digit 结果值小数位数
	 */
	public final static void div(KFloat target, KFloat val1, int val2, int digit){
		float temp = val1.toFloat();
		float result = temp / val2;
		result = result * (float)Math.pow(10, digit);
		target .init((int)result, digit, 0);
	}

	/**
	 * 相除
	 * @param val1
	 * @param val2
	 */
	public static final void div(KFloat val1, KFloat val2)
	{
		div(val1, val1, val2);
	}

	/**
	 * 除法
	 * @param target
	 * @param val1
	 * @param val2
	 */
	public static final void div(KFloat target, KFloat val1, KFloat val2)
	{
		if (val2.nValue == 0)
			return;
		prepare(val1, val2, false);
		int i = val1.nValue / val2.nValue;
		int iMod = val1.nValue % val2.nValue;
		for (int t = 0; t < val1.nDigit; t++)
		{
			i = i * 10 + (iMod * 10 / val2.nValue);
			iMod = iMod * 10 % val2.nValue;
		}
		target.init(i, val1.nDigit, val1.nUnit);
	}
	
	
	

	/**
	 * 绝对值
	 */
	public static final KFloat abs(KFloat ftValue)
	{
		if (ftValue.nValue < 0)
			return new KFloat((0 - ftValue.nValue), ftValue.nDigit,
			        ftValue.nUnit);
		else
			return new KFloat(ftValue.nValue, ftValue.nDigit, ftValue.nUnit);
	}

	/**
	 * 最大值(假设单位相等的情况下)
	 */
	public static final KFloat max(KFloat val1, KFloat val2)
	{
		if (compare(val1, val2) == 1)
			return new KFloat(val1.nValue, val1.nDigit, val1.nUnit);
		else
			return new KFloat(val2.nValue, val2.nDigit, val2.nUnit);
	}

	public static final KFloat max(int[] vals)
	{
		KFloat kf = new KFloat(vals[0]);
		for (int i : vals)
		{
			kf = max(kf, new KFloat(i));
		}
		return kf;
	}

	/**
	 * 最小值(假设小数点和单位都相等的情况下)暂时没用到关闭
	 */
	public static final KFloat min(KFloat val1, KFloat val2)
	{
		if (compare(val1, val2) == -1)
			return new KFloat(val1.nValue, val1.nDigit, val1.nUnit);
		else
			return new KFloat(val2.nValue, val2.nDigit, val2.nUnit);
	}

	public static final KFloat min(int[] vals)
	{
		KFloat kf = new KFloat(vals[0]);
		KFloat kfTmp;
		for (int i : vals)
		{
			kfTmp = new KFloat(i);
			if (kf.toString().equals("---"))
			{
				kf = kfTmp;
			} else
			{
				if (!kfTmp.toString().equals("---"))
				{
					kf = min(kf, kfTmp);
				}
			}
		}
		return kf;
	}

	/**
	 * 四舍五入
	 * 
	 * @param val
	 *            原始值
	 * @param nDigit
	 *            小数点后需保留的位数
	 * @return
	 */
	public static final KFloat round(KFloat val, int nDigit)
	{
		if ((val.nDigit <= nDigit) || (nDigit < 0))
		{
			return val;
		}
		int nHalfBase = 5;
		int nDigitBase = 10;
		for (int i = 1; i < (val.nDigit - nDigit); i++)
		{
			nHalfBase *= 10;
			nDigitBase *= 10;
		}
		if (val.nValue < 0)
			nHalfBase *= -1;
		return new KFloat((val.nValue + nHalfBase) / nDigitBase, nDigit,
		        val.nUnit);

	}

	public final static int tenPow(int n)
	{
		int v = 1;
		for (int i = 0; i < n; i++)
			v *= 10;
		return v;
	}
	
	

}
