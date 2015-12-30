// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package demo;

import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryStepRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.*;

public class CategoryStepChartDemo1 extends ApplicationFrame
{

	public CategoryStepChartDemo1(String s)
	{
		super(s);
		CategoryDataset categorydataset = createDataset();
		ChartPanel chartpanel = (ChartPanel)createDemoPanel();
		chartpanel.setPreferredSize(new Dimension(500, 270));
		chartpanel.setEnforceFileExtensions(false);
		setContentPane(chartpanel);
	}

	private static CategoryDataset createDataset()
	{
		double ad[][] = {
			{
				1.0D, 4D, 3D, 5D, 5D, 7D, 7D, 8D
			}, {
				5D, 7D, 6D, 8D, 4D, 4D, 2D, 1.0D
			}, {
				4D, 3D, 2D, 3D, 6D, 3D, 4D, 3D
			}
		};
		CategoryDataset categorydataset = DatasetUtilities.createCategoryDataset("Series ", "Type ", ad);
		return categorydataset;
	}

	private static JFreeChart createChart(CategoryDataset categorydataset)
	{
		CategoryStepRenderer categorysteprenderer = new CategoryStepRenderer(true);
		categorysteprenderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		CategoryAxis categoryaxis = new CategoryAxis("Category");
		NumberAxis numberaxis = new NumberAxis("Value");
		CategoryPlot categoryplot = new CategoryPlot(categorydataset, categoryaxis, numberaxis, categorysteprenderer);
		categoryplot.setRangePannable(true);
		JFreeChart jfreechart = new JFreeChart("Category Step Chart", categoryplot);
		categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
		categoryplot.setDomainGridlinesVisible(true);
		categoryplot.setRangeGridlinesVisible(true);
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		categoryaxis.setLowerMargin(0.0D);
		categoryaxis.setUpperMargin(0.0D);
		categoryaxis.addCategoryLabelToolTip("Type 1", "The first type.");
		categoryaxis.addCategoryLabelToolTip("Type 2", "The second type.");
		categoryaxis.addCategoryLabelToolTip("Type 3", "The third type.");
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setLabelAngle(0.0D);
		ChartUtilities.applyCurrentTheme(jfreechart);
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
		CategoryStepChartDemo1 categorystepchartdemo1 = new CategoryStepChartDemo1("JFreeChart : CategoryStepChartDemo1.java");
		categorystepchartdemo1.pack();
		RefineryUtilities.centerFrameOnScreen(categorystepchartdemo1);
		categorystepchartdemo1.setVisible(true);
	}
}
