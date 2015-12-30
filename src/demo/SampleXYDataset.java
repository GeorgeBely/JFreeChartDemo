// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package demo;

import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.XYDataset;

public class SampleXYDataset extends AbstractXYDataset
	implements XYDataset
{

	private double translate;

	public SampleXYDataset()
	{
		translate = 0.0D;
	}

	public double getTranslate()
	{
		return translate;
	}

	public void setTranslate(double d)
	{
		translate = d;
		notifyListeners(new DatasetChangeEvent(this, this));
	}

	public Number getX(int i, int j)
	{
		return new Double(-10D + translate + (double)j / 10D);
	}

	public Number getY(int i, int j)
	{
		if (i == 0)
			return new Double(Math.cos(-10D + translate + (double)j / 10D));
		else
			return new Double(2D * Math.sin(-10D + translate + (double)j / 10D));
	}

	public int getSeriesCount()
	{
		return 2;
	}

	public Comparable getSeriesKey(int i)
	{
		if (i == 0)
			return "y = cosine(x)";
		if (i == 1)
			return "y = 2*sine(x)";
		else
			return "Error";
	}

	public int getItemCount(int i)
	{
		return 200;
	}
}
