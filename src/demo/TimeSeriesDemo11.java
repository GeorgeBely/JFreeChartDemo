// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package demo;

import java.awt.*;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.date.SerialDate;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

// Referenced classes of package demo:
//			DemoPanel

public class TimeSeriesDemo11 extends ApplicationFrame
{

	public TimeSeriesDemo11(String s)
	{
		super(s);
		setContentPane(createDemoPanel());
	}

	private static JFreeChart createChart(String s, XYDataset xydataset)
	{
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(s, "Date", "Price", xydataset, true, true, false);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot();
		xyplot.setOrientation(PlotOrientation.VERTICAL);
		XYItemRenderer xyitemrenderer = xyplot.getRenderer();
		xyitemrenderer.setSeriesPaint(0, Color.blue);
		return jfreechart;
	}

	private static XYDataset createDataset(String s, double d, RegularTimePeriod regulartimeperiod, int i)
	{
		TimeSeries timeseries = new TimeSeries(s);
		RegularTimePeriod regulartimeperiod1 = regulartimeperiod;
		double d1 = d;
		for (int j = 0; j < i; j++)
		{
			timeseries.add(regulartimeperiod1, d1);
			regulartimeperiod1 = regulartimeperiod1.previous();
			d1 *= 1.0D + (Math.random() - 0.495D) / 10D;
		}

		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		return timeseriescollection;
	}

	public static JPanel createDemoPanel()
	{
		DemoPanel demopanel = new DemoPanel(new GridLayout(2, 2));
		demopanel.setPreferredSize(new Dimension(800, 600));
		Day day = new Day();
		XYDataset xydataset = createDataset("Series 1", 100D, day, 365);
		JFreeChart jfreechart = createChart("Chart 1 : 1 Year", xydataset);
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		demopanel.add(chartpanel);
		JFreeChart jfreechart1 = createChart("Chart 2 : 6 Months", xydataset);
		SerialDate serialdate = day.getSerialDate();
		SerialDate serialdate1 = SerialDate.addMonths(-6, serialdate);
		Day day1 = new Day(serialdate1);
		XYPlot xyplot = (XYPlot)jfreechart1.getPlot();
		DateAxis dateaxis = (DateAxis)xyplot.getDomainAxis();
		dateaxis.setRange(day1.getStart(), day.getEnd());
		ChartPanel chartpanel1 = new ChartPanel(jfreechart1);
		demopanel.add(chartpanel1);
		JFreeChart jfreechart2 = createChart("Chart 3 : 3 Months", xydataset);
		SerialDate serialdate2 = SerialDate.addMonths(-3, serialdate);
		Day day2 = new Day(serialdate2);
		XYPlot xyplot1 = (XYPlot)jfreechart2.getPlot();
		DateAxis dateaxis1 = (DateAxis)xyplot1.getDomainAxis();
		dateaxis1.setRange(day2.getStart(), day.getEnd());
		ChartPanel chartpanel2 = new ChartPanel(jfreechart2);
		demopanel.add(chartpanel2);
		JFreeChart jfreechart3 = createChart("Chart 4 : 1 Month", xydataset);
		SerialDate serialdate3 = SerialDate.addMonths(-1, serialdate);
		Day day3 = new Day(serialdate3);
		XYPlot xyplot2 = (XYPlot)jfreechart3.getPlot();
		DateAxis dateaxis2 = (DateAxis)xyplot2.getDomainAxis();
		dateaxis2.setRange(day3.getStart(), day.getEnd());
		ChartPanel chartpanel3 = new ChartPanel(jfreechart3);
		demopanel.add(chartpanel3);
		demopanel.addChart(jfreechart);
		demopanel.addChart(jfreechart1);
		demopanel.addChart(jfreechart2);
		demopanel.addChart(jfreechart3);
		return demopanel;
	}

	public static void main(String args[])
	{
		TimeSeriesDemo11 timeseriesdemo11 = new TimeSeriesDemo11("Time Series Demo 11");
		timeseriesdemo11.pack();
		RefineryUtilities.centerFrameOnScreen(timeseriesdemo11);
		timeseriesdemo11.setVisible(true);
	}
}
