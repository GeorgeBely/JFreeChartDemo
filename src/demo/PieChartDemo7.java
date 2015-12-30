// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package demo;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.jfree.chart.*;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class PieChartDemo7 extends ApplicationFrame
{
	static class Rotator extends Timer
		implements ActionListener
	{

		private PiePlot plot;
		private int angle;

		public void actionPerformed(ActionEvent actionevent)
		{
			plot.setStartAngle(angle);
			angle = angle + 1;
			if (angle == 360)
				angle = 0;
		}

		Rotator(PiePlot pieplot)
		{
			super(50, null);
			angle = 270;
			plot = pieplot;
			addActionListener(this);
		}
	}


	public PieChartDemo7(String s)
	{
		super(s);
		setContentPane(createDemoPanel());
	}

	private static PieDataset createDataset(int i)
	{
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		for (int j = 0; j < i; j++)
		{
			double d = 100D * Math.random();
			defaultpiedataset.setValue("Section " + j, d);
		}

		return defaultpiedataset;
	}

	public static JPanel createDemoPanel()
	{
		PieDataset piedataset = createDataset(14);
		JFreeChart jfreechart = ChartFactory.createPieChart("Pie Chart Demo 7", piedataset, false, true, false);
		PiePlot pieplot = (PiePlot)jfreechart.getPlot();
		pieplot.setCircular(true);
		pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
		pieplot.setNoDataMessage("No data available");
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setPreferredSize(new Dimension(500, 270));
		Rotator rotator = new Rotator(pieplot);
		rotator.start();
		return chartpanel;
	}

	public static void main(String args[])
	{
		PieChartDemo7 piechartdemo7 = new PieChartDemo7("JFreeChart: PieChartDemo7.java");
		piechartdemo7.pack();
		RefineryUtilities.centerFrameOnScreen(piechartdemo7);
		piechartdemo7.setVisible(true);
	}
}
