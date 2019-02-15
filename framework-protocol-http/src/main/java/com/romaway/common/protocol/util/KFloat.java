package com.romaway.common.protocol.util;

/**
 * @author not attributable
 * @version 1.0 备忘：当把KFloat作为计算器时，不能在KFloat变量之间进行赋值‘=’，切记切记！
 */
public class KFloat
{
	public final static int NUMBER = 0; // 单位(个)
	/**
	 * 单位(万)
	 */
	public final static int TEN_THOUSAND = 1; // 单位(万)
	/**
	 * 单位(亿)
	 */
	public final static int HUNDRED_MILLION = 2; // 单位(亿)
	// public static KFloat sub = new KFloat();
	public long longVlaue;
	public int nValue; // 实际数值
	public int nDigit; // 小数点位数
	public int nUnit; // 单位
	private int format;//

	public KFloat()
	{
		this.nValue = 0;
		this.nDigit = 0;
		this.nUnit = NUMBER;
	}

	/**
	 * 构造函数  
	 * @param nFloat  模拟浮点数值  从右边数，0，1位即后两位表示单位。 2,3位表示小数位数。余下的表示具体的值
	 */
	public KFloat(int nFloat)
	{
		this.nUnit = (nFloat & 0x00000003);
		this.nDigit = ((nFloat & 0x0000000C) >> 2);
		this.nValue = (nFloat >> 4);
	}

	public KFloat init(int nFloat)
	{
		this.nUnit = (nFloat & 0x00000003);
		this.nDigit = ((nFloat & 0x0000000C) >> 2);
		this.nValue = (nFloat >> 4);
		return this;
	}

	/**
	 * 模拟浮点数转回int显示格式
	 * @return
	 */
	public int float2int()
	{
		int v = (this.nValue << 4) & 0xFFFFFFF0;
		v = v | (this.nDigit << 2) | this.nUnit;
		return v;
	}

	/**
	 * 
	 * @param nValue
	 * @param nDigit
	 *            小数点位数
	 * @param nUnit
	 *            单位
	 */
	public KFloat(int nValue, int nDigit, int nUnit)
	{
		this.nValue = nValue;
		this.nDigit = nDigit;
		this.nUnit = nUnit;
	}

	public void init(int nValue, int nDigit, int nUnit)
	{
		this.nValue = nValue;
		this.nDigit = nDigit;
		this.nUnit = nUnit;
		// return this;
	}

	public KFloat(KFloat kFloat)
	{
		this.nValue = kFloat.nValue;
		this.nDigit = kFloat.nDigit;
		this.nUnit = kFloat.nUnit;
	}

	public int getDigitBase()
	{
		int nBase = 1;
		for (int i = 0; i < this.nDigit; i++)
		{
			nBase *= 10;
		}
		return nBase;
	}

	public String toString(String ch)
	{
		format = ch != null ? 1 : 0;
		String s = toString() + ch;
		format = 0;
		return s;
	}

	@Override
	public String toString()
	{
		if (format == 0 && nValue == 0)
			 return "0.00";
		StringBuffer strRtn = new StringBuffer(12);
		strRtn.append(this.nValue);
		int nLen = strRtn.length();
		int nPos = 0;
		if (this.nValue < 0)
		{
			nPos = 1;
		}
		if (this.nDigit > 0)
		{
			if ((nLen - nPos) > this.nDigit)
				strRtn.insert(nLen - this.nDigit, '.');
			else
			{
				strRtn.insert(nPos, "0.");
				while ((nLen - nPos) < this.nDigit)
				{
					strRtn.insert(nPos + 2, '0');
					nLen++;
				}
			}
		}
		switch (this.nUnit)
		{
			case TEN_THOUSAND: // 单位(万)
				strRtn.append("万");
				break;
			case HUNDRED_MILLION: // 单位(亿)
				strRtn.append("亿");
				break;
			default:
				break;
		}
		return strRtn.toString();
	}

	/**
	 * 两个浮点数比较(假设小数点和单位都相等的情况下)
	 */
	public static final int compare(KFloat val1, KFloat val2)
	{
		int nRtn = 0;
		// prepare(val1,val2,true);
		if (val1.nValue > val2.nValue)
			nRtn = 1;
		else if (val1.nValue < val2.nValue)
			nRtn = -1;
		return nRtn;
	}

	/**
	 * 两个浮点数比较,先转换成double类型，再进行比较 如果前一个值，大于后一个值，返回1 如果小于，返回-1 如果相等，返回0
	 * 
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static final int compare4Double(KFloat val1, KFloat val2)
	{
		System.out.println(String.format("\n比较，原始值：%s,%s", val1.toString(),
		        val2.toString()));
		double a1 = toDouble(val1);
		double a2 = toDouble(val2);
		System.out.println(String.format("比较，转换前：%s,%s\n", a1, a2));

		if (a1 > a2)
			return 1;
		else if (a1 < a2)
			return -1;
		else
			return 0;
	}

	public static final double toDouble(KFloat val1)
	{
		double d = val1.nValue;
		switch (val1.nUnit)
		{
			case TEN_THOUSAND:
				d *= 10000;
				break;
			case HUNDRED_MILLION:
				d *= 100000000;
				break;
			default:
				break;
		}
		// 计算小数点

		for (int i = 0; i < val1.nDigit; i++)
		{
			d /= 10;
		}

		return d;

	}

	public final KFloat singleSub(KFloat val1, KFloat val2)
	{
		prepare(val1, val2, true);
		this.nValue = val1.nValue - val2.nValue;
		this.nDigit = val1.nDigit;
		this.nUnit = val1.nUnit;
		return this;
	}

	public static void prepare(KFloat val1, KFloat val2, boolean enlarge)
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

	public double toDouble()
	{
		if (this.nValue == 0)
			return 0.0;

		double result = this.nValue;

		// 转换小数点
		if (this.nDigit > 0)
			result /= (this.nDigit * 10);

		return 0.0;
	}

	/**
	 * 模拟浮点转真正的浮点数据,未考虑单位
	 * @return
	 */
	public float toFloat()
	{
		if (this.nValue == 0)
			return 0.0f;

		float result = this.nValue;

		// 转换小数点
		if (this.nDigit > 0)
			result /= (int) Math.pow(10, this.nDigit);

		return result;
	}

	/**
	 * 浮点数转回模拟浮点类型，未考虑单位
	 * @param value
	 * @return
	 */
	public KFloat init(float value)
	{
		int n = (int) Math.pow(10, this.nDigit);
		this.nValue = (int) Math.round(value * n);
		return this;
	}

    //取单位
    public static int getUnit(double v) {
        if (v > 100000000)
            return HUNDRED_MILLION;
        else if  (v > 10000 * 10)
            return TEN_THOUSAND;
        else
            return NUMBER;
    }

	/**
	 * 转换成模拟浮点数
	 * @param n 原数值
	 * @param f 小数位数
	 * @param u 单位
	 * @return
	 */
    public int toNFloat(double n,int f, int u) {
        int data;
        switch (u) {
            case TEN_THOUSAND: // 单位(万)
                n = n / 10000;
                break;
            case HUNDRED_MILLION: //单位亿
                n = n / 100000000;
                break;
        }
        // 小数位处理
        switch (f) {
            case 1: // 1位小数

                data = n > 0 ? ((int)(n * 10 + 0.5) * 4 + f) * 4 + u : (-(int)(-n * 10 + 0.5) * 4 + f) * 4 + u;
                break;
            case 2: // 2位小数
                data = n > 0 ? ((int)(n * 100 + 0.5) * 4 + f) * 4 + u : (-(int)(-n * 100 + 0.5) * 4 + f) * 4 + u;
                break;
//            case 3: // 3位小数
//                data = n > 0 ? ((int)(n * 1000 + 0.5) * 4 + f) * 4 + u : (-(int)(-n * 1000 + 0.5) * 4 + f) * 4 + u;
//                break;

            default: // 无小数位
                data = n > 0 ? ((int)(n + 0.5) * 4 + f) * 4 + u
                        : (-(int)(-n + 0.5) * 4 + f) * 4 + u;
                break;
        }
        return data;

    }

}
