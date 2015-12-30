// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package demo;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.*;

public class LineChartDemo1 extends ApplicationFrame
{

	public LineChartDemo1(String s)
	{
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	private static CategoryDataset createDataset()
	{
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		defaultcategorydataset.addValue(212D, "Classes", "JDK 1.0");
		defaultcategorydataset.addValue(504D, "Classes", "JDK 1.1");
		defaultcategorydataset.addValue(1520D, "Classes", "JDK 1.2");
		defaultcategorydataset.addValue(1842D, "Classes", "JDK 1.3");
		defaultcategorydataset.addValue(2991D, "Classes", "JDK 1.4");
		defaultcategorydataset.addValue(3500D, "Classes", "JDK 1.5");
		return defaultcategorydataset;
	}

	private static JFreeChart createChart(CategoryDataset categorydataset)
	{
		JFreeChart jfreechart = ChartFactory.createLineChart("Java Standard Class Library", null, "Class Count", categorydataset, PlotOrientation.VERTICAL, false, true, false);
		jfreechart.addSubtitle(new TextTitle("Number of Classes By Release"));
		TextTitle texttitle = new TextTitle("Source: Java In A Nutshell (5th Edition) by David Flanagan (O'Reilly)");
		texttitle.setFont(new Font("SansSerif", 0, 10));
		texttitle.setPosition(RectangleEdge.BOTTOM);
		texttitle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
		jfreechart.addSubtitle(texttitle);
		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
		categoryplot.setRangePannable(true);
		categoryplot.setRangeGridlinesVisible(false);
		java.net.URL url = (demo.LineChartDemo1.class).getClassLoader().getResource("OnBridge11small.png");
		if (url != null)
		{
			ImageIcon imageicon = new ImageIcon(url);
			jfreechart.setBackgroundImage(imageicon.getImage());
			categoryplot.setBackgroundPaint(null);
		}
		NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		ChartUtilities.applyCurrentTheme(jfreechart);
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer)categoryplot.getRenderer();
		lineandshaperenderer.setBaseShapesVisible(true);
		lineandshaperenderer.setDrawOutlines(true);
		lineandshaperenderer.setUseFillPaint(true);
		lineandshaperenderer.setBaseFillPaint(Color.white);
		lineandshaperenderer.setSeriesStroke(0, new BasicStroke(3F));
		lineandshaperenderer.setSeriesOutlineStroke(0, new BasicStroke(2.0F));
		lineandshaperenderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
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
		LineChartDemo1 linechartdemo1 = new LineChartDemo1("JFreeChart: LineChartDemo1.java");
		linechartdemo1.pack();
		RefineryUtilities.centerFrameOnScreen(linechartdemo1);
		linechartdemo1.setVisible(true);
	}
}
