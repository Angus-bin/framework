/**
 * 
 */
package com.romaway.activity.basephone;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

/**
 * @author duminghui
 * 
 */
public class ViewPostFlag
{
	private List<PostFlag> flags = new ArrayList<PostFlag>();

	public ViewPostFlag()
	{

	}

	/**
	 * 添加运行标记
	 * 
	 * @param view
	 * @param run
	 */
	public synchronized void addFlag(View view, Runnable run)
	{
		PostFlag info = new PostFlag();
		info.view = view;
		info.run = run;
		flags.add(info);
	}

	/**
	 * 所有标记运行
	 */
	public synchronized void allPost()
	{
		for (PostFlag flag : flags)
		{
			if (!flag.isRun)
			{
				flag.view.post(flag.run);
				flag.isRun = true;
			}
		}
	}

	/**
	 * 标记运行
	 * 
	 * @param action
	 */
	public synchronized void post(Runnable action)
	{
		for (PostFlag flag : flags)
		{
			if (!flag.isRun && flag.run.equals(action))
			{
				flag.view.post(flag.run);
				flag.isRun = true;
			}
		}
	}

	/**
	 * 删除下次的运行内容
	 */
	public synchronized void removeCallbacks()
	{
		for (PostFlag flag : flags)
		{
			flag.view.removeCallbacks(flag.run);
			flag.isRun = false;
		}
	}

	/**
	 * 删除特定的action
	 * 
	 * @param action
	 */
	public synchronized void removeCallBacks(Runnable action)
	{
		for (PostFlag flag : flags)
		{
			if (flag.run.equals(action))
			{
				flag.view.removeCallbacks(flag.run);
				flag.isRun = false;
			}
		}
	}

	/**
	 * 销毁
	 */
	public synchronized void destory()
	{
		int size = flags.size();
		// PostFlag flag;
		for (int index = 0; index < size; index++)
		{
			flags.remove(0);
			// flag = flags.remove(0);
			// flag.view.removeCallbacks(flag.run);
			// flag.isRun = false;
		}
	}

	private static class PostFlag
	{
		public View view;
		public Runnable run;
		public boolean isRun;
	}
}
