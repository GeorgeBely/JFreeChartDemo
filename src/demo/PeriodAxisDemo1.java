// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package demo;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.*;

public class PeriodAxisDemo1 extends ApplicationFrame
{

	public PeriodAxisDemo1(String s)
	{
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	private static JFreeChart createChart(XYDataset xydataset)
	{
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("Legal & General Unit Trust Prices", "Date", "Price Per Unit", xydataset, true, true, false);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot();
		xyplot.setDomainCrosshairVisible(true);
		xyplot.setRangeCrosshairVisible(true);
		org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer = xyplot.getRenderer();
		if (xyitemrenderer instanceof XYLineAndShapeRenderer)
		{
			XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyitemrenderer;
			xylineandshaperenderer.setBaseShapesVisible(true);
			xylineandshaperenderer.setBaseShapesFilled(true);
			xylineandshaperenderer.setBaseItemLabelsVisible(true);
		}
		PeriodAxis periodaxis = new PeriodAxis("Date");
		periodaxis.setTimeZone(TimeZone.getTimeZone("Pacific/Auckland"));
		periodaxis.setAutoRangeTimePeriodClass(org.jfree.data.time.Month.class);
		periodaxis.setMajorTickTimePeriodClass(org.jfree.data.time.Month.class);
		PeriodAxisLabelInfo aperiodaxislabelinfo[] = new PeriodAxisLabelInfo[2];
		aperiodaxislabelinfo[0] = new PeriodAxisLabelInfo(org.jfree.data.time.Month.class, new SimpleDateFormat("MMM"), new RectangleInsets(2D, 2D, 2D, 2D), new Font("SansSerif", 1, 10), Color.blue, false, new BasicStroke(0.0F), Color.lightGray);
		aperiodaxislabelinfo[1] = new PeriodAxisLabelInfo(org.jfree.data.time.Year.class, new SimpleDateFormat("yyyy"));
		periodaxis.setLabelInfo(aperiodaxislabelinfo);
		xyplot.setDomainAxis(periodaxis);
		ChartUtilities.applyCurrentTheme(jfreechart);
		return jfreechart;
	}

	private static XYDataset createDataset()
	{
		TimeSeries timeseries = new TimeSeries("L&G European Index Trust");
		timeseries.add(new Month(2, 2001), 181.80000000000001D);
		timeseries.add(new Month(3, 2001), 167.30000000000001D);
		timeseries.add(new Month(4, 2001), 153.80000000000001D);
		timeseries.add(new Month(5, 2001), 167.59999999999999D);
		timeseries.add(new Month(6, 2001), 158.80000000000001D);
		timeseries.add(new Month(7, 2001), 148.30000000000001D);
		timeseries.add(new Month(8, 2001), 153.90000000000001D);
		timeseries.add(new Month(9, 2001), 142.69999999999999D);
		timeseries.add(new Month(10, 2001), 123.2D);
		timeseries.add(new Month(11, 2001), 131.80000000000001D);
		timeseries.add(new Month(12, 2001), 139.59999999999999D);
		timeseries.add(new Month(1, 2002), 142.90000000000001D);
		timeseries.add(new Month(2, 2002), 138.69999999999999D);
		timeseries.add(new Month(3, 2002), 137.30000000000001D);
		timeseries.add(new Month(4, 2002), 143.90000000000001D);
		timeseries.add(new Month(5, 2002), 139.80000000000001D);
		timeseries.add(new Month(6, 2002), 137D);
		timeseries.add(new Month(7, 2002), 132.80000000000001D);
		TimeSeries timeseries1 = new TimeSeries("L&G UK Index Trust");
		timeseries1.add(new Month(2, 2001), 129.59999999999999D);
		timeseries1.add(new Month(3, 2001), 123.2D);
		timeseries1.add(new Month(4, 2001), 117.2D);
		timeseries1.add(new Month(5, 2001), 124.09999999999999D);
		timeseries1.add(new Month(6, 2001), 122.59999999999999D);
		timeseries1.add(new Month(7, 2001), 119.2D);
		timeseries1.add(new Month(8, 2001), 116.5D);
		timeseries1.add(new Month(9, 2001), 112.7D);
		timeseries1.add(new Month(10, 2001), 101.5D);
		timeseries1.add(new Month(11, 2001), 106.09999999999999D);
		timeseries1.add(new Month(12, 2001), 110.3D);
		timeseries1.add(new Month(1, 2002), 111.7D);
		timeseries1.add(new Month(2, 2002), 111D);
		timeseries1.add(new Month(3, 2002), 109.59999999999999D);
		timeseries1.add(new Month(4, 2002), 113.2D);
		timeseries1.add(new Month(5, 2002), 111.59999999999999D);
		timeseries1.add(new Month(6, 2002), 108.8D);
		timeseries1.add(new Month(7, 2002), 101.59999999999999D);
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		timeseriescollection.addSeries(timeseries1);
		timeseriescollection.setXPosition(TimePeriodAnchor.MIDDLE);
		return timeseriescollection;
	}

	public static JPanel createDemoPanel()
	{
		JFreeChart jfreechart = createChart(createDataset());
		return new ChartPanel(jfreechart);
	}

	public static void main(String args[])
	{
		PeriodAxisDemo1 periodaxisdemo1 = new PeriodAxisDemo1("JFreeChart: PeriodAxisDemo1.java");
		periodaxisdemo1.pack();
		RefineryUtilities.centerFrameOnScreen(periodaxisdemo1);
		periodaxisdemo1.setVisible(true);
	}
}
