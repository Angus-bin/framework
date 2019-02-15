package com.romaway.common.protocol.coder;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: KJava ƽ̨
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class KeyParameter
{
    private byte[] key;

    public KeyParameter(byte[] key)
    {
        this(key, 0, key.length);
    }

    public KeyParameter(byte[] key, int keyOff, int keyLen)
    {
        this.key = new byte[keyLen];
        System.arraycopy(key, keyOff, this.key, 0, keyLen);
    }

    public byte[] getKey()
    {
        return key;
    }
}
