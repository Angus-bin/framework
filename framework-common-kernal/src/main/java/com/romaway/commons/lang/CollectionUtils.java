/**
 * 
 */
package com.romaway.commons.lang;

import java.util.Collection;

/**
 * @author duminghui
 * 
 */
public class CollectionUtils
{
    /**
     * 添加src里的数据到target中<br>
     * 如果target中已经存在则不添加
     * 
     * @param target
     * @param src
     */
    public final static <T> void addOnly(Collection<T> target, Collection<T> src)
    {
        for (T t : src)
        {
            if (!target.contains(t))
            {
                target.add(t);
            }
        }
    }
}
