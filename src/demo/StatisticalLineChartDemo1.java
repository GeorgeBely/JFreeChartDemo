// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package demo;

import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StatisticalLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class StatisticalLineChartDemo1 extends ApplicationFrame
{

	public StatisticalLineChartDemo1(String s)
	{
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	private static CategoryDataset createDataset()
	{
		DefaultStatisticalCategoryDataset defaultstatisticalcategorydataset = new DefaultStatisticalCategoryDataset();
		defaultstatisticalcategorydataset.add(10D, 2.3999999999999999D, "Row 1", "Column 1");
		defaultstatisticalcategorydataset.add(15D, 4.4000000000000004D, "Row 1", "Column 2");
		defaultstatisticalcategorydataset.add(13D, 2.1000000000000001D, "Row 1", "Column 3");
		defaultstatisticalcategorydataset.add(7D, 1.3D, "Row 1", "Column 4");
		defaultstatisticalcategorydataset.add(22D, 2.3999999999999999D, "Row 2", "Column 1");
		defaultstatisticalcategorydataset.add(18D, 4.4000000000000004D, "Row 2", "Column 2");
		defaultstatisticalcategorydataset.add(28D, 2.1000000000000001D, "Row 2", "Column 3");
		defaultstatisticalcategorydataset.add(17D, 1.3D, "Row 2", "Column 4");
		return defaultstatisticalcategorydataset;
	}

	private static JFreeChart createChart(CategoryDataset categorydataset)
	{
		JFreeChart jfreechart = ChartFactory.createLineChart("Statistical Line Chart Demo 1", "Type", "Value", categorydataset, PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
		categoryplot.setRangePannable(true);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setUpperMargin(0.0D);
		categoryaxis.setLowerMargin(0.0D);
		NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);
		StatisticalLineAndShapeRenderer statisticallineandshaperenderer = new StatisticalLineAndShapeRenderer(true, false);
		statisticallineandshaperenderer.setUseSeriesOffset(true);
		categoryplot.setRenderer(statisticallineandshaperenderer);
		return jfreechart;
	}

	public static JPanel createDemoPanel()
	{
		JFreeChart jfreechart = createChart(createDataset());
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setMouseWheelEnabled(true);
		return chartpanel;
	}

	public static void main(String args[])
	{
		StatisticalLineChartDemo1 statisticallinechartdemo1 = new StatisticalLineChartDemo1("JFreeChart: StatisticalLineChartDemo1.java");
		statisticallinechartdemo1.pack();
		RefineryUtilities.centerFrameOnScreen(statisticallinechartdemo1);
		statisticallinechartdemo1.setVisible(true);
	}
}
