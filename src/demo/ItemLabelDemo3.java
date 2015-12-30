// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package demo;

import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.*;

public class ItemLabelDemo3 extends ApplicationFrame
{

	public ItemLabelDemo3(String s)
	{
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	private static CategoryDataset createDataset()
	{
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		defaultcategorydataset.addValue(51D, "Series 1", "Apples");
		defaultcategorydataset.addValue(44.299999999999997D, "Series 1", "Bananas");
		defaultcategorydataset.addValue(93D, "Series 1", "Oranges");
		defaultcategorydataset.addValue(35.600000000000001D, "Series 1", "Pears");
		defaultcategorydataset.addValue(75.099999999999994D, "Series 1", "Plums");
		return defaultcategorydataset;
	}

	private static JFreeChart createChart(CategoryDataset categorydataset)
	{
		JFreeChart jfreechart = ChartFactory.createBarChart("Item Label Demo 3", "Category", "Value", categorydataset, PlotOrientation.VERTICAL, false, true, false);
		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
		categoryplot.setRangePannable(true);
		categoryplot.setRangeZeroBaselineVisible(true);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setVisible(false);
		NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
		numberaxis.setUpperMargin(0.14999999999999999D);
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		StandardCategoryItemLabelGenerator standardcategoryitemlabelgenerator = new StandardCategoryItemLabelGenerator("{1}", NumberFormat.getInstance());
		categoryitemrenderer.setBaseItemLabelGenerator(standardcategoryitemlabelgenerator);
		categoryitemrenderer.setBaseItemLabelFont(new Font("SansSerif", 0, 12));
		categoryitemrenderer.setBaseItemLabelsVisible(true);
		categoryitemrenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER, -1.5707963267948966D));
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
		ItemLabelDemo3 itemlabeldemo3 = new ItemLabelDemo3("JFreeChart: ItemLabelDemo3.java");
		itemlabeldemo3.pack();
		RefineryUtilities.centerFrameOnScreen(itemlabeldemo3);
		itemlabeldemo3.setVisible(true);
	}
}
