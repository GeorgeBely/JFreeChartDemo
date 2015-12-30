// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package demo;

import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Rotation;

// Referenced classes of package demo:
//			Rotator

public class PieChart3DDemo2 extends ApplicationFrame
{

	public PieChart3DDemo2(String s)
	{
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	private static JFreeChart createChart(PieDataset piedataset)
	{
		JFreeChart jfreechart = ChartFactory.createPieChart3D("Pie Chart 3D Demo 2", piedataset, true, false, false);
		PiePlot3D pieplot3d = (PiePlot3D)jfreechart.getPlot();
		pieplot3d.setStartAngle(270D);
		pieplot3d.setDirection(Rotation.ANTICLOCKWISE);
		pieplot3d.setForegroundAlpha(0.6F);
		return jfreechart;
	}

	private static PieDataset createDataset()
	{
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		defaultpiedataset.setValue("Java", new Double(43.200000000000003D));
		defaultpiedataset.setValue("Visual Basic", new Double(10D));
		defaultpiedataset.setValue("C/C++", new Double(17.5D));
		defaultpiedataset.setValue("PHP", new Double(32.5D));
		defaultpiedataset.setValue("Perl", new Double(12.5D));
		return defaultpiedataset;
	}

	public static JPanel createDemoPanel()
	{
		JFreeChart jfreechart = createChart(createDataset());
		Rotator rotator = new Rotator((PiePlot3D)jfreechart.getPlot());
		rotator.start();
		return new ChartPanel(jfreechart);
	}

	public static void main(String args[])
	{
		PieChart3DDemo2 piechart3ddemo2 = new PieChart3DDemo2("JFreeChart: PieChart3DDemo2.java");
		piechart3ddemo2.pack();
		RefineryUtilities.centerFrameOnScreen(piechart3ddemo2);
		piechart3ddemo2.setVisible(true);
	}
}
