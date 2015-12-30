// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package demo;

import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.*;

public class StackedBarChart3DDemo3 extends ApplicationFrame
{

	public StackedBarChart3DDemo3(String s)
	{
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	public static CategoryDataset createDataset()
	{
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		defaultcategorydataset.addValue(10D, "Series 1", "C1");
		defaultcategorydataset.addValue(5D, "Series 1", "C2");
		defaultcategorydataset.addValue(6D, "Series 1", "C3");
		defaultcategorydataset.addValue(7D, "Series 1", "C4");
		defaultcategorydataset.addValue(8D, "Series 1", "C5");
		defaultcategorydataset.addValue(9D, "Series 1", "C6");
		defaultcategorydataset.addValue(10D, "Series 1", "C7");
		defaultcategorydataset.addValue(11D, "Series 1", "C8");
		defaultcategorydataset.addValue(3D, "Series 1", "C9");
		defaultcategorydataset.addValue(4D, "Series 2", "C1");
		defaultcategorydataset.addValue(7D, "Series 2", "C2");
		defaultcategorydataset.addValue(17D, "Series 2", "C3");
		defaultcategorydataset.addValue(-15D, "Series 2", "C4");
		defaultcategorydataset.addValue(6D, "Series 2", "C5");
		defaultcategorydataset.addValue(8D, "Series 2", "C6");
		defaultcategorydataset.addValue(9D, "Series 2", "C7");
		defaultcategorydataset.addValue(13D, "Series 2", "C8");
		defaultcategorydataset.addValue(7D, "Series 2", "C9");
		defaultcategorydataset.addValue(15D, "Series 3", "C1");
		defaultcategorydataset.addValue(-14D, "Series 3", "C2");
		defaultcategorydataset.addValue(12D, "Series 3", "C3");
		defaultcategorydataset.addValue(11D, "Series 3", "C4");
		defaultcategorydataset.addValue(10D, "Series 3", "C5");
		defaultcategorydataset.addValue(0.0D, "Series 3", "C6");
		defaultcategorydataset.addValue(7D, "Series 3", "C7");
		defaultcategorydataset.addValue(9D, "Series 3", "C8");
		defaultcategorydataset.addValue(11D, "Series 3", "C9");
		defaultcategorydataset.addValue(14D, "Series 4", "C1");
		defaultcategorydataset.addValue(3D, "Series 4", "C2");
		defaultcategorydataset.addValue(7D, "Series 4", "C3");
		defaultcategorydataset.addValue(0.0D, "Series 4", "C4");
		defaultcategorydataset.addValue(9D, "Series 4", "C5");
		defaultcategorydataset.addValue(6D, "Series 4", "C6");
		defaultcategorydataset.addValue(7D, "Series 4", "C7");
		defaultcategorydataset.addValue(9D, "Series 4", "C8");
		defaultcategorydataset.addValue(10D, "Series 4", "C9");
		return defaultcategorydataset;
	}

	private static JFreeChart createChart(CategoryDataset categorydataset)
	{
		JFreeChart jfreechart = ChartFactory.createStackedBarChart3D("Stacked Bar Chart 3D Demo 3", "Category", "Value", categorydataset, PlotOrientation.HORIZONTAL, true, true, false);
		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
		BarRenderer barrenderer = (BarRenderer)categoryplot.getRenderer();
		barrenderer.setDrawBarOutline(false);
		barrenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		barrenderer.setBaseItemLabelsVisible(true);
		barrenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER));
		barrenderer.setBaseNegativeItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER));
		return jfreechart;
	}

	public static JPanel createDemoPanel()
	{
		JFreeChart jfreechart = createChart(createDataset());
		return new ChartPanel(jfreechart);
	}

	public static void main(String args[])
	{
		StackedBarChart3DDemo3 stackedbarchart3ddemo3 = new StackedBarChart3DDemo3("Stacked Bar Chart 3D Demo 3");
		stackedbarchart3ddemo3.pack();
		RefineryUtilities.centerFrameOnScreen(stackedbarchart3ddemo3);
		stackedbarchart3ddemo3.setVisible(true);
	}
}
