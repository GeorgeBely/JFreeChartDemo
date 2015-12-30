// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package demo;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.function.PowerFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.*;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

// Referenced classes of package demo:
//			DemoPanel

public class RegressionDemo1 extends ApplicationFrame
{
	static class MyDemoPanel extends DemoPanel
	{

		private XYDataset data1;

		private XYDataset createSampleData1()
		{
			XYSeries xyseries = new XYSeries("Series 1");
			xyseries.add(2D, 56.270000000000003D);
			xyseries.add(3D, 41.32D);
			xyseries.add(4D, 31.449999999999999D);
			xyseries.add(5D, 30.050000000000001D);
			xyseries.add(6D, 24.690000000000001D);
			xyseries.add(7D, 19.780000000000001D);
			xyseries.add(8D, 20.940000000000001D);
			xyseries.add(9D, 16.73D);
			xyseries.add(10D, 14.210000000000001D);
			xyseries.add(11D, 12.44D);
			XYSeriesCollection xyseriescollection = new XYSeriesCollection(xyseries);
			return xyseriescollection;
		}

		private JTabbedPane createContent()
		{
			JTabbedPane jtabbedpane = new JTabbedPane();
			jtabbedpane.add("Linear", createChartPanel1());
			jtabbedpane.add("Power", createChartPanel2());
			return jtabbedpane;
		}

		private ChartPanel createChartPanel1()
		{
			NumberAxis numberaxis = new NumberAxis("X");
			numberaxis.setAutoRangeIncludesZero(false);
			NumberAxis numberaxis1 = new NumberAxis("Y");
			numberaxis1.setAutoRangeIncludesZero(false);
			XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(false, true);
			XYPlot xyplot = new XYPlot(data1, numberaxis, numberaxis1, xylineandshaperenderer);
			double ad[] = Regression.getOLSRegression(data1, 0);
			LineFunction2D linefunction2d = new LineFunction2D(ad[0], ad[1]);
			XYDataset xydataset = DatasetUtilities.sampleFunction2D(linefunction2d, 2D, 11D, 100, "Fitted Regression Line");
			xyplot.setDataset(1, xydataset);
			XYLineAndShapeRenderer xylineandshaperenderer1 = new XYLineAndShapeRenderer(true, false);
			xylineandshaperenderer1.setSeriesPaint(0, Color.blue);
			xyplot.setRenderer(1, xylineandshaperenderer1);
			JFreeChart jfreechart = new JFreeChart("Linear Regression", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
			ChartUtilities.applyCurrentTheme(jfreechart);
			addChart(jfreechart);
			ChartPanel chartpanel = new ChartPanel(jfreechart);
			return chartpanel;
		}

		private ChartPanel createChartPanel2()
		{
			NumberAxis numberaxis = new NumberAxis("X");
			numberaxis.setAutoRangeIncludesZero(false);
			NumberAxis numberaxis1 = new NumberAxis("Y");
			numberaxis1.setAutoRangeIncludesZero(false);
			XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(false, true);
			XYPlot xyplot = new XYPlot(data1, numberaxis, numberaxis1, xylineandshaperenderer);
			double ad[] = Regression.getPowerRegression(data1, 0);
			PowerFunction2D powerfunction2d = new PowerFunction2D(ad[0], ad[1]);
			XYDataset xydataset = DatasetUtilities.sampleFunction2D(powerfunction2d, 2D, 11D, 100, "Fitted Regression Line");
			XYLineAndShapeRenderer xylineandshaperenderer1 = new XYLineAndShapeRenderer(true, false);
			xylineandshaperenderer1.setSeriesPaint(0, Color.blue);
			xyplot.setDataset(1, xydataset);
			xyplot.setRenderer(1, xylineandshaperenderer1);
			JFreeChart jfreechart = new JFreeChart("Power Regression", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
			ChartUtilities.applyCurrentTheme(jfreechart);
			addChart(jfreechart);
			ChartPanel chartpanel = new ChartPanel(jfreechart);
			return chartpanel;
		}

		public MyDemoPanel()
		{
			super(new BorderLayout());
			data1 = createSampleData1();
			add(createContent());
		}
	}


	public RegressionDemo1(String s)
	{
		super(s);
		JPanel jpanel = createDemoPanel();
		getContentPane().add(jpanel);
	}

	public static JPanel createDemoPanel()
	{
		return new MyDemoPanel();
	}

	public static void main(String args[])
	{
		RegressionDemo1 regressiondemo1 = new RegressionDemo1("JFreeChart: Regression Demo 1");
		regressiondemo1.pack();
		RefineryUtilities.centerFrameOnScreen(regressiondemo1);
		regressiondemo1.setVisible(true);
	}
}
