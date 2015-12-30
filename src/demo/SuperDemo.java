// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package demo;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.FontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartTransferable;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

// Referenced classes of package demo:
//			MemoryUsageDemo, DemoPanel, DemoDescription

public class SuperDemo extends ApplicationFrame
	implements ActionListener, TreeSelectionListener
{
	static class DisplayDemo
		implements Runnable
	{

		private SuperDemo app;
		private DemoDescription demoDescription;

		public void run()
		{
			try
			{
				Class class1 = Class.forName(demoDescription.getClassName());
				Method method = class1.getDeclaredMethod("createDemoPanel", (Class[])null);
				JPanel jpanel = (JPanel)method.invoke(null, (Object[])null);
				app.chartContainer.removeAll();
				app.chartContainer.add(jpanel);
				app.displayPanel.validate();
				String s = class1.getName();
				String s1 = s;
				int i = s.lastIndexOf('.');
				if (i > 0)
					s1 = s.substring(i + 1);
				s1 = s1 + ".html";
				app.displayDescription(s1);
			}
			catch (ClassNotFoundException classnotfoundexception)
			{
				classnotfoundexception.printStackTrace();
			}
			catch (NoSuchMethodException nosuchmethodexception)
			{
				nosuchmethodexception.printStackTrace();
			}
			catch (InvocationTargetException invocationtargetexception)
			{
				invocationtargetexception.printStackTrace();
			}
			catch (IllegalAccessException illegalaccessexception)
			{
				illegalaccessexception.printStackTrace();
			}
		}

		public DisplayDemo(SuperDemo superdemo, DemoDescription demodescription)
		{
			app = superdemo;
			demoDescription = demodescription;
		}
	}

	static class PDFExportTask
		implements Runnable
	{

		JFreeChart chart;
		int width;
		int height;
		File file;

		public void run()
		{
			try
			{
				SuperDemo.saveChartAsPDF(file, chart, width, height, new DefaultFontMapper());
			}
			catch (IOException ioexception)
			{
				ioexception.printStackTrace();
			}
		}

		public PDFExportTask(JFreeChart jfreechart, int i, int j, File file1)
		{
			chart = jfreechart;
			file = file1;
			width = i;
			height = j;
			jfreechart.setBorderVisible(true);
			jfreechart.setPadding(new RectangleInsets(2D, 2D, 2D, 2D));
		}
	}


	public static final String EXIT_COMMAND = "EXIT";
	private JPanel displayPanel;
	private JPanel chartContainer;
	private JPanel descriptionContainer;
	private JTextPane descriptionPane;
	private JEditorPane editorPane;
	private TreePath defaultChartPath;

	public SuperDemo(String s)
	{
		super(s);
		setContentPane(createContent());
		setJMenuBar(createMenuBar());
	}

	private JComponent createContent()
	{
		JPanel jpanel = new JPanel(new BorderLayout());
		JTabbedPane jtabbedpane = new JTabbedPane();
		JPanel jpanel1 = new JPanel(new BorderLayout());
		jpanel1.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		JSplitPane jsplitpane = new JSplitPane(1);
		JTree jtree = new JTree(createTreeModel());
		jtree.addTreeSelectionListener(this);
		JScrollPane jscrollpane = new JScrollPane(jtree);
		jscrollpane.setPreferredSize(new Dimension(300, 100));
		jsplitpane.setLeftComponent(jscrollpane);
		jsplitpane.setRightComponent(createChartDisplayPanel());
		jpanel1.add(jsplitpane);
		jtabbedpane.add("Demos", jpanel1);
		MemoryUsageDemo memoryusagedemo = new MemoryUsageDemo(0x493e0);
//		(new MemoryUsageDemo.DataGenerator(memoryusagedemo, 1000)).start();
		jtabbedpane.add("Memory Usage", memoryusagedemo);
		jtabbedpane.add("Source Code", createSourceCodePanel());
		jtabbedpane.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		jpanel.add(jtabbedpane);
		jtree.setSelectionPath(defaultChartPath);
		return jpanel;
	}

	private JMenuBar createMenuBar()
	{
		JMenuBar jmenubar = new JMenuBar();
		JMenu jmenu = new JMenu("File", true);
		jmenu.setMnemonic('F');
		JMenuItem jmenuitem = new JMenuItem("Export to PDF...", 112);
		jmenuitem.setActionCommand("EXPORT_TO_PDF");
		jmenuitem.addActionListener(this);
		jmenu.add(jmenuitem);
		jmenu.addSeparator();
		JMenuItem jmenuitem1 = new JMenuItem("Exit", 120);
		jmenuitem1.setActionCommand("EXIT");
		jmenuitem1.addActionListener(this);
		jmenu.add(jmenuitem1);
		jmenubar.add(jmenu);
		JMenu jmenu1 = new JMenu("Edit", false);
		jmenubar.add(jmenu1);
		JMenuItem jmenuitem2 = new JMenuItem("Copy", 67);
		jmenuitem2.setActionCommand("COPY");
		jmenuitem2.addActionListener(this);
		jmenu1.add(jmenuitem2);
		JMenu jmenu2 = new JMenu("Theme", true);
		jmenu2.setMnemonic('T');
		JCheckBoxMenuItem jcheckboxmenuitem = new JCheckBoxMenuItem("JFree", true);
		jcheckboxmenuitem.setActionCommand("JFREE_THEME");
		jcheckboxmenuitem.addActionListener(this);
		jmenu2.add(jcheckboxmenuitem);
		JCheckBoxMenuItem jcheckboxmenuitem1 = new JCheckBoxMenuItem("Darkness", false);
		jcheckboxmenuitem1.setActionCommand("DARKNESS_THEME");
		jcheckboxmenuitem1.addActionListener(this);
		jmenu2.add(jcheckboxmenuitem1);
		JCheckBoxMenuItem jcheckboxmenuitem2 = new JCheckBoxMenuItem("Legacy", false);
		jcheckboxmenuitem2.setActionCommand("LEGACY_THEME");
		jcheckboxmenuitem2.addActionListener(this);
		jmenu2.add(jcheckboxmenuitem2);
		ButtonGroup buttongroup = new ButtonGroup();
		buttongroup.add(jcheckboxmenuitem);
		buttongroup.add(jcheckboxmenuitem1);
		buttongroup.add(jcheckboxmenuitem2);
		jmenubar.add(jmenu2);
		return jmenubar;
	}

	private JPanel createSourceCodePanel()
	{
		JPanel jpanel = new JPanel(new BorderLayout());
		jpanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setFont(new Font("Monospaced", 0, 12));
		updateSourceCodePanel("source.html");
		JScrollPane jscrollpane = new JScrollPane(editorPane);
		jscrollpane.setVerticalScrollBarPolicy(20);
		jscrollpane.setPreferredSize(new Dimension(250, 145));
		jscrollpane.setMinimumSize(new Dimension(10, 10));
		jpanel.add(jscrollpane);
		return jpanel;
	}

	private void updateSourceCodePanel(String s)
	{
		java.net.URL url = null;
		if (s != null)
			(demo.SuperDemo.class).getResource(s);
		if (url == null)
			url = (demo.SuperDemo.class).getResource("source.html");
		if (url != null)
			try
			{
				editorPane.setPage(url);
			}
			catch (IOException ioexception)
			{
				System.err.println("Attempted to read a bad URL: " + url);
			}
		else
			System.err.println("Couldn't find file: source.html");
	}

	private void copyToClipboard()
	{
		JFreeChart jfreechart = null;
		int i = 0;
		int j = 0;
		java.awt.Component component = chartContainer.getComponent(0);
		if (component instanceof ChartPanel)
		{
			ChartPanel chartpanel = (ChartPanel)component;
			jfreechart = chartpanel.getChart();
			i = chartpanel.getWidth();
			j = chartpanel.getHeight();
		} else
		if (component instanceof DemoPanel)
		{
			DemoPanel demopanel = (DemoPanel)component;
			jfreechart = (JFreeChart)demopanel.charts.get(0);
			i = demopanel.getWidth();
			j = demopanel.getHeight();
		}
		if (jfreechart != null)
		{
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			ChartTransferable charttransferable = new ChartTransferable(jfreechart, i, j);
			clipboard.setContents(charttransferable, null);
		}
	}

	public void actionPerformed(ActionEvent actionevent)
	{
		String s = actionevent.getActionCommand();
		if (s.equals("EXPORT_TO_PDF"))
			exportToPDF();
		else
		if (s.equals("COPY"))
			copyToClipboard();
		else
		if (s.equals("LEGACY_THEME"))
		{
			ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
			applyThemeToChart();
		} else
		if (s.equals("JFREE_THEME"))
		{
			ChartFactory.setChartTheme(StandardChartTheme.createJFreeTheme());
			applyThemeToChart();
		} else
		if (s.equals("DARKNESS_THEME"))
		{
			ChartFactory.setChartTheme(StandardChartTheme.createDarknessTheme());
			applyThemeToChart();
		} else
		if (s.equals("EXIT"))
			attemptExit();
	}

	private void applyThemeToChart()
	{
		java.awt.Component component = chartContainer.getComponent(0);
		if (component instanceof ChartPanel)
		{
			ChartPanel chartpanel = (ChartPanel)component;
			ChartUtilities.applyCurrentTheme(chartpanel.getChart());
		} else
		if (component instanceof DemoPanel)
		{
			DemoPanel demopanel = (DemoPanel)component;
			JFreeChart ajfreechart[] = demopanel.getCharts();
			for (int i = 0; i < ajfreechart.length; i++)
				ChartUtilities.applyCurrentTheme(ajfreechart[i]);

		}
	}

	private void exportToPDF()
	{
		JFreeChart jfreechart = null;
		int i = 0;
		int j = 0;
		java.awt.Component component = chartContainer.getComponent(0);
		if (component instanceof ChartPanel)
		{
			ChartPanel chartpanel = (ChartPanel)component;
			jfreechart = chartpanel.getChart();
			i = chartpanel.getWidth();
			j = chartpanel.getHeight();
		} else
		if (component instanceof DemoPanel)
		{
			DemoPanel demopanel = (DemoPanel)component;
			jfreechart = (JFreeChart)demopanel.charts.get(0);
			i = demopanel.getWidth();
			j = demopanel.getHeight();
		}
		if (jfreechart != null)
		{
			JFileChooser jfilechooser = new JFileChooser();
			jfilechooser.setName("untitled.pdf");
			jfilechooser.setFileFilter(new FileFilter() {

				public boolean accept(File file)
				{
					return file.isDirectory() || file.getName().endsWith(".pdf");
				}

				public String getDescription()
				{
					return "Portable Document Format (PDF)";
				}

			});
			int k = jfilechooser.showSaveDialog(this);
			if (k == 0)
				try
				{
					JFreeChart jfreechart1 = (JFreeChart)jfreechart.clone();
					PDFExportTask pdfexporttask = new PDFExportTask(jfreechart1, i, j, jfilechooser.getSelectedFile());
					Thread thread = new Thread(pdfexporttask);
					thread.start();
				}
				catch (CloneNotSupportedException clonenotsupportedexception)
				{
					clonenotsupportedexception.printStackTrace();
				}
		} else
		{
			String s = "Unable to export the selected item.  There is ";
			s = s + "either no chart selected,\nor else the chart is not ";
			s = s + "at the expected location in the component hierarchy\n";
			s = s + "(future versions of the demo may include code to ";
			s = s + "handle these special cases).";
			JOptionPane.showMessageDialog(this, s, "PDF Export", 1);
		}
	}

	public static void writeChartAsPDF(OutputStream outputstream, JFreeChart jfreechart, int i, int j, FontMapper fontmapper)
		throws IOException
	{
		Rectangle rectangle = new Rectangle(i, j);
		Document document = new Document(rectangle, 50F, 50F, 50F, 50F);
		try
		{
			PdfWriter pdfwriter = PdfWriter.getInstance(document, outputstream);
			document.addAuthor("JFreeChart");
			document.addSubject("Demonstration");
			document.open();
			PdfContentByte pdfcontentbyte = pdfwriter.getDirectContent();
			PdfTemplate pdftemplate = pdfcontentbyte.createTemplate(i, j);
			Graphics2D graphics2d = pdftemplate.createGraphics(i, j, fontmapper);
			java.awt.geom.Rectangle2D.Double double1 = new java.awt.geom.Rectangle2D.Double(0.0D, 0.0D, i, j);
			jfreechart.draw(graphics2d, double1);
			graphics2d.dispose();
			pdfcontentbyte.addTemplate(pdftemplate, 0.0F, 0.0F);
		}
		catch (DocumentException documentexception)
		{
			System.err.println(documentexception.getMessage());
		}
		document.close();
	}

	public static void saveChartAsPDF(File file, JFreeChart jfreechart, int i, int j, FontMapper fontmapper)
		throws IOException
	{
		BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(new FileOutputStream(file));
		writeChartAsPDF(bufferedoutputstream, jfreechart, i, j, fontmapper);
		bufferedoutputstream.close();
	}

	private void attemptExit()
	{
		String s = "Confirm";
		String s1 = "Are you sure you want to exit the demo?";
		int i = JOptionPane.showConfirmDialog(this, s1, s, 0, 3);
		if (i == 0)
		{
			dispose();
			System.exit(0);
		}
	}

	private JPanel createChartDisplayPanel()
	{
		displayPanel = new JPanel(new BorderLayout());
		chartContainer = new JPanel(new BorderLayout());
		chartContainer.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(Color.black)));
		chartContainer.add(createNoDemoSelectedPanel());
		descriptionContainer = new JPanel(new BorderLayout());
		descriptionContainer.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		descriptionContainer.setPreferredSize(new Dimension(600, 140));
		descriptionPane = new JTextPane();
		descriptionPane.setEditable(false);
		JScrollPane jscrollpane = new JScrollPane(descriptionPane, 20, 31);
		descriptionContainer.add(jscrollpane);
		displayDescription("select.html");
		JSplitPane jsplitpane = new JSplitPane(0);
		jsplitpane.setTopComponent(chartContainer);
		jsplitpane.setBottomComponent(descriptionContainer);
		displayPanel.add(jsplitpane);
		jsplitpane.setDividerLocation(0.75D);
		return displayPanel;
	}

	private TreeModel createTreeModel()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("JFreeChart");
		MutableTreeNode mutabletreenode = createShowcaseNode(defaultmutabletreenode);
		defaultmutabletreenode.add(mutabletreenode);
		defaultmutabletreenode.add(createAreaChartsNode());
		defaultmutabletreenode.add(createBarChartsNode());
		defaultmutabletreenode.add(createStackedBarChartsNode());
		defaultmutabletreenode.add(createCombinedAxisChartsNode());
		defaultmutabletreenode.add(createFinancialChartsNode());
		defaultmutabletreenode.add(createGanttChartsNode());
		defaultmutabletreenode.add(createLineChartsNode());
		defaultmutabletreenode.add(createMeterChartsNode());
		defaultmutabletreenode.add(createMultipleAxisChartsNode());
		defaultmutabletreenode.add(createOverlaidChartsNode());
		defaultmutabletreenode.add(createPieChartsNode());
		defaultmutabletreenode.add(createStatisticalChartsNode());
		defaultmutabletreenode.add(createTimeSeriesChartsNode());
		defaultmutabletreenode.add(createXYChartsNode());
		defaultmutabletreenode.add(createMiscellaneousChartsNode());
		return new DefaultTreeModel(defaultmutabletreenode);
	}

	private MutableTreeNode createPieChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Pie Charts");
		defaultmutabletreenode.add(createNode("PieChartDemo1", "PieChartDemo1.java"));
		defaultmutabletreenode.add(createNode("PieChartDemo2", "PieChartDemo2.java"));
		defaultmutabletreenode.add(createNode("PieChartDemo3", "PieChartDemo3.java"));
		defaultmutabletreenode.add(createNode("PieChartDemo4", "PieChartDemo4.java"));
		defaultmutabletreenode.add(createNode("PieChartDemo5", "PieChartDemo5.java"));
		defaultmutabletreenode.add(createNode("PieChartDemo6", "PieChartDemo6.java"));
		defaultmutabletreenode.add(createNode("PieChartDemo7", "PieChartDemo7.java"));
		defaultmutabletreenode.add(createNode("PieChartDemo8", "PieChartDemo8.java"));
		defaultmutabletreenode.add(createNode("PieChart3DDemo1", "PieChart3DDemo1.java"));
		defaultmutabletreenode.add(createNode("PieChart3DDemo2", "PieChart3DDemo2.java"));
		defaultmutabletreenode.add(createNode("PieChart3DDemo3", "PieChart3DDemo3.java"));
		defaultmutabletreenode.add(createNode("MultiplePieChartDemo1", "MultiplePieChartDemo1.java"));
		defaultmutabletreenode.add(createNode("MultiplePieChartDemo2", "MultiplePieChartDemo2.java"));
		defaultmutabletreenode.add(createNode("MultiplePieChartDemo3", "MultiplePieChartDemo3.java"));
		defaultmutabletreenode.add(createNode("MultiplePieChartDemo4", "MultiplePieChartDemo4.java"));
		defaultmutabletreenode.add(createNode("RingChartDemo1", "RingChartDemo1.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createOverlaidChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Overlaid Charts");
		defaultmutabletreenode.add(createNode("OverlaidBarChartDemo1", "OverlaidBarChartDemo1.java"));
		defaultmutabletreenode.add(createNode("OverlaidBarChartDemo2", "OverlaidBarChartDemo2.java"));
		defaultmutabletreenode.add(createNode("OverlaidXYPlotDemo1", "OverlaidXYPlotDemo1.java"));
		defaultmutabletreenode.add(createNode("OverlaidXYPlotDemo2", "OverlaidXYPlotDemo2.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createBarChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Bar Charts");
		defaultmutabletreenode.add(createCategoryBarChartsNode());
		defaultmutabletreenode.add(createXYBarChartsNode());
		return defaultmutabletreenode;
	}

	private MutableTreeNode createStackedBarChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Bar Charts - Stacked");
		defaultmutabletreenode.add(createNode("PopulationChartDemo1", "PopulationChartDemo1.java"));
		defaultmutabletreenode.add(createNode("StackedBarChartDemo1", "StackedBarChartDemo1.java"));
		defaultmutabletreenode.add(createNode("StackedBarChartDemo2", "StackedBarChartDemo2.java"));
		defaultmutabletreenode.add(createNode("StackedBarChartDemo3", "StackedBarChartDemo3.java"));
		defaultmutabletreenode.add(createNode("StackedBarChartDemo4", "StackedBarChartDemo4.java"));
		defaultmutabletreenode.add(createNode("StackedBarChartDemo5", "StackedBarChartDemo5.java"));
		defaultmutabletreenode.add(createNode("StackedBarChartDemo6", "StackedBarChartDemo6.java"));
		defaultmutabletreenode.add(createNode("StackedBarChartDemo7", "StackedBarChartDemo7.java"));
		defaultmutabletreenode.add(createNode("StackedBarChart3DDemo1", "StackedBarChart3DDemo1.java"));
		defaultmutabletreenode.add(createNode("StackedBarChart3DDemo2", "StackedBarChart3DDemo2.java"));
		defaultmutabletreenode.add(createNode("StackedBarChart3DDemo3", "StackedBarChart3DDemo3.java"));
		defaultmutabletreenode.add(createNode("StackedBarChart3DDemo4", "StackedBarChart3DDemo4.java"));
		defaultmutabletreenode.add(createNode("StackedBarChart3DDemo5", "StackedBarChart3DDemo5.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createCategoryBarChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("CategoryPlot");
		defaultmutabletreenode.add(createNode("BarChartDemo1", "BarChartDemo1.java"));
		defaultmutabletreenode.add(createNode("BarChartDemo2", "BarChartDemo2.java"));
		defaultmutabletreenode.add(createNode("BarChartDemo3", "BarChartDemo3.java"));
		defaultmutabletreenode.add(createNode("BarChartDemo4", "BarChartDemo4.java"));
		defaultmutabletreenode.add(createNode("BarChartDemo5", "BarChartDemo5.java"));
		defaultmutabletreenode.add(createNode("BarChartDemo6", "BarChartDemo6.java"));
		defaultmutabletreenode.add(createNode("BarChartDemo7", "BarChartDemo7.java"));
		defaultmutabletreenode.add(createNode("BarChartDemo8", "BarChartDemo8.java"));
		defaultmutabletreenode.add(createNode("BarChartDemo9", "BarChartDemo9.java"));
		defaultmutabletreenode.add(createNode("BarChartDemo10", "BarChartDemo10.java"));
		defaultmutabletreenode.add(createNode("BarChartDemo11", "BarChartDemo11.java"));
		defaultmutabletreenode.add(createNode("BarChart3DDemo1", "BarChart3DDemo1.java"));
		defaultmutabletreenode.add(createNode("BarChart3DDemo2", "BarChart3DDemo2.java"));
		defaultmutabletreenode.add(createNode("BarChart3DDemo3", "BarChart3DDemo3.java"));
		defaultmutabletreenode.add(createNode("BarChart3DDemo4", "BarChart3DDemo4.java"));
		defaultmutabletreenode.add(createNode("CylinderChartDemo1", "CylinderChartDemo1.java"));
		defaultmutabletreenode.add(createNode("CylinderChartDemo2", "CylinderChartDemo2.java"));
		defaultmutabletreenode.add(createNode("IntervalBarChartDemo1", "IntervalBarChartDemo1.java"));
		defaultmutabletreenode.add(createNode("LayeredBarChartDemo1", "LayeredBarChartDemo1.java"));
		defaultmutabletreenode.add(createNode("LayeredBarChartDemo2", "LayeredBarChartDemo2.java"));
		defaultmutabletreenode.add(createNode("SlidingCategoryDatasetDemo1", "SlidingCategoryDatasetDemo1.java"));
		defaultmutabletreenode.add(createNode("SlidingCategoryDatasetDemo2", "SlidingCategoryDatasetDemo2.java"));
		defaultmutabletreenode.add(createNode("StatisticalBarChartDemo1", "StatisticalBarChartDemo1.java"));
		defaultmutabletreenode.add(createNode("SurveyResultsDemo1", "SurveyResultsDemo1.java"));
		defaultmutabletreenode.add(createNode("SurveyResultsDemo2", "SurveyResultsDemo2.java"));
		defaultmutabletreenode.add(createNode("SurveyResultsDemo3", "SurveyResultsDemo3.java"));
		defaultmutabletreenode.add(createNode("WaterfallChartDemo1", "WaterfallChartDemo1.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createXYBarChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("XYPlot");
		defaultmutabletreenode.add(createNode("XYBarChartDemo1", "XYBarChartDemo1.java"));
		defaultmutabletreenode.add(createNode("XYBarChartDemo2", "XYBarChartDemo2.java"));
		defaultmutabletreenode.add(createNode("XYBarChartDemo3", "XYBarChartDemo3.java"));
		defaultmutabletreenode.add(createNode("XYBarChartDemo4", "XYBarChartDemo4.java"));
		defaultmutabletreenode.add(createNode("XYBarChartDemo5", "XYBarChartDemo5.java"));
		defaultmutabletreenode.add(createNode("XYBarChartDemo6", "XYBarChartDemo6.java"));
		defaultmutabletreenode.add(createNode("XYBarChartDemo7", "XYBarChartDemo7.java"));
		defaultmutabletreenode.add(createNode("ClusteredXYBarRendererDemo1", "ClusteredXYBarRendererDemo1.java"));
		defaultmutabletreenode.add(createNode("StackedXYBarChartDemo1", "StackedXYBarChartDemo1.java"));
		defaultmutabletreenode.add(createNode("StackedXYBarChartDemo2", "StackedXYBarChartDemo2.java"));
		defaultmutabletreenode.add(createNode("StackedXYBarChartDemo3", "StackedXYBarChartDemo3.java"));
		defaultmutabletreenode.add(createNode("RelativeDateFormatDemo1", "RelativeDateFormatDemo1.java"));
		defaultmutabletreenode.add(createNode("RelativeDateFormatDemo2", "RelativeDateFormatDemo2.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createLineChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Line Charts");
		DefaultMutableTreeNode defaultmutabletreenode1 = new DefaultMutableTreeNode(new DemoDescription("AnnotationDemo1", "AnnotationDemo1.java"));
		DefaultMutableTreeNode defaultmutabletreenode2 = new DefaultMutableTreeNode(new DemoDescription("LineChartDemo1", "LineChartDemo1.java"));
		DefaultMutableTreeNode defaultmutabletreenode3 = new DefaultMutableTreeNode(new DemoDescription("LineChartDemo2", "LineChartDemo2.java"));
		DefaultMutableTreeNode defaultmutabletreenode4 = new DefaultMutableTreeNode(new DemoDescription("LineChartDemo3", "LineChartDemo3.java"));
		DefaultMutableTreeNode defaultmutabletreenode5 = new DefaultMutableTreeNode(new DemoDescription("LineChartDemo4", "LineChartDemo4.java"));
		DefaultMutableTreeNode defaultmutabletreenode6 = new DefaultMutableTreeNode(new DemoDescription("LineChartDemo5", "LineChartDemo5.java"));
		DefaultMutableTreeNode defaultmutabletreenode7 = new DefaultMutableTreeNode(new DemoDescription("LineChartDemo6", "LineChartDemo6.java"));
		DefaultMutableTreeNode defaultmutabletreenode8 = new DefaultMutableTreeNode(new DemoDescription("LineChartDemo7", "LineChartDemo7.java"));
		DefaultMutableTreeNode defaultmutabletreenode9 = new DefaultMutableTreeNode(new DemoDescription("LineChartDemo8", "LineChartDemo8.java"));
		DefaultMutableTreeNode defaultmutabletreenode10 = new DefaultMutableTreeNode(new DemoDescription("LineChart3DDemo1", "LineChart3DDemo1.java"));
		DefaultMutableTreeNode defaultmutabletreenode11 = new DefaultMutableTreeNode(new DemoDescription("StatisticalLineChartDemo1", "StatisticalLineChartDemo1.java"));
		DefaultMutableTreeNode defaultmutabletreenode12 = new DefaultMutableTreeNode(new DemoDescription("XYSplineRendererDemo1", "XYSplineRendererDemo1.java"));
		DefaultMutableTreeNode defaultmutabletreenode13 = new DefaultMutableTreeNode(new DemoDescription("XYStepRendererDemo1", "XYStepRendererDemo1.java"));
		DefaultMutableTreeNode defaultmutabletreenode14 = new DefaultMutableTreeNode(new DemoDescription("XYStepRendererDemo2", "XYStepRendererDemo2.java"));
		defaultmutabletreenode.add(defaultmutabletreenode1);
		defaultmutabletreenode.add(defaultmutabletreenode2);
		defaultmutabletreenode.add(defaultmutabletreenode3);
		defaultmutabletreenode.add(defaultmutabletreenode4);
		defaultmutabletreenode.add(defaultmutabletreenode5);
		defaultmutabletreenode.add(defaultmutabletreenode6);
		defaultmutabletreenode.add(defaultmutabletreenode7);
		defaultmutabletreenode.add(defaultmutabletreenode8);
		defaultmutabletreenode.add(defaultmutabletreenode9);
		defaultmutabletreenode.add(defaultmutabletreenode10);
		defaultmutabletreenode.add(defaultmutabletreenode11);
		defaultmutabletreenode.add(defaultmutabletreenode12);
		defaultmutabletreenode.add(defaultmutabletreenode13);
		defaultmutabletreenode.add(defaultmutabletreenode14);
		return defaultmutabletreenode;
	}

	private MutableTreeNode createNode(String s, String s1)
	{
		return new DefaultMutableTreeNode(new DemoDescription(s, s1));
	}

	private MutableTreeNode createShowcaseNode(DefaultMutableTreeNode defaultmutabletreenode)
	{
		DefaultMutableTreeNode defaultmutabletreenode1 = new DefaultMutableTreeNode("*** Showcase Charts ***");
		MutableTreeNode mutabletreenode = createNode("PieChartDemo4", "PieChartDemo4.java");
		defaultmutabletreenode1.add(mutabletreenode);
		defaultmutabletreenode1.add(createNode("MultiplePieChartDemo1", "MultiplePieChartDemo1.java"));
		MutableTreeNode mutabletreenode1 = createNode("BarChart3DDemo1", "BarChart3DDemo1.java");
		defaultChartPath = new TreePath(new Object[] {
			defaultmutabletreenode, defaultmutabletreenode1, mutabletreenode1
		});
		defaultmutabletreenode1.add(mutabletreenode1);
		defaultmutabletreenode1.add(createNode("StatisticalBarChartDemo1", "StatisticalBarChartDemo1.java"));
		defaultmutabletreenode1.add(createNode("HistogramDemo1", "HistogramDemo1.java"));
		defaultmutabletreenode1.add(createNode("StackedBarChartDemo2", "StackedBarChartDemo2.java"));
		defaultmutabletreenode1.add(createNode("StackedXYBarChartDemo2", "StackedXYBarChartDemo2.java"));
		defaultmutabletreenode1.add(createNode("NormalDistributionDemo2", "NormalDistributionDemo2.java"));
		defaultmutabletreenode1.add(createNode("ParetoChartDemo1", "ParetoChartDemo1.java"));
		defaultmutabletreenode1.add(createNode("WaterfallChartDemo1", "WaterfallChartDemo1.java"));
		defaultmutabletreenode1.add(createNode("LineChartDemo1", "LineChartDemo1.java"));
		defaultmutabletreenode1.add(createNode("AnnotationDemo1", "AnnotationDemo1.java"));
		defaultmutabletreenode1.add(createNode("XYSplineRendererDemo1", "XYSplineRendererDemo1.java"));
		defaultmutabletreenode1.add(createNode("DualAxisDemo1", "DualAxisDemo1.java"));
		defaultmutabletreenode1.add(createNode("PriceVolumeDemo1", "PriceVolumeDemo1.java"));
		defaultmutabletreenode1.add(createNode("YieldCurveDemo1", "YieldCurveDemo1.java"));
		defaultmutabletreenode1.add(createNode("MultipleAxisDemo1", "MultipleAxisDemo1.java"));
		defaultmutabletreenode1.add(createNode("DifferenceChartDemo1", "DifferenceChartDemo1.java"));
		defaultmutabletreenode1.add(createNode("DifferenceChartDemo2", "DifferenceChartDemo2.java"));
		defaultmutabletreenode1.add(createNode("DeviationRendererDemo2", "DeviationRendererDemo2.java"));
		defaultmutabletreenode1.add(createNode("DialDemo2a", "DialDemo2a.java"));
		defaultmutabletreenode1.add(createNode("VectorPlotDemo1", "VectorPlotDemo1.java"));
		defaultmutabletreenode1.add(createNode("CrosshairDemo2", "CrosshairDemo2.java"));
		defaultmutabletreenode1.add(createNode("XYDrawableAnnotationDemo1", "XYDrawableAnnotationDemo1.java"));
		defaultmutabletreenode1.add(createNode("XYTaskDatasetDemo2", "XYTaskDatasetDemo2.java"));
		defaultmutabletreenode1.add(createNode("SlidingCategoryDatasetDemo2", "SlidingCategoryDatasetDemo2.java"));
		defaultmutabletreenode1.add(createNode("CrossSectionDemo1", "CrossSectionDemo1.java"));
		return defaultmutabletreenode1;
	}

	private MutableTreeNode createAreaChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Area Charts");
		defaultmutabletreenode.add(createNode("AreaChartDemo1", "AreaChartDemo1.java"));
		defaultmutabletreenode.add(createNode("StackedAreaChartDemo1", "StackedAreaChartDemo1.java"));
		defaultmutabletreenode.add(createNode("StackedXYAreaChartDemo1", "StackedXYAreaChartDemo1.java"));
		defaultmutabletreenode.add(createNode("StackedXYAreaChartDemo2", "StackedXYAreaChartDemo2.java"));
		defaultmutabletreenode.add(createNode("StackedXYAreaRendererDemo1", "StackedXYAreaRendererDemo1.java"));
		defaultmutabletreenode.add(createNode("XYAreaChartDemo1", "XYAreaChartDemo1.java"));
		defaultmutabletreenode.add(createNode("XYAreaChartDemo2", "XYAreaChartDemo2.java"));
		defaultmutabletreenode.add(createNode("XYAreaRenderer2Demo1", "XYAreaRenderer2Demo1.java"));
		defaultmutabletreenode.add(createNode("XYStepAreaRendererDemo1", "XYStepAreaRendererDemo1.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createStatisticalChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Statistical Charts");
		defaultmutabletreenode.add(createNode("BoxAndWhiskerChartDemo1", "BoxAndWhiskerChartDemo1.java"));
		defaultmutabletreenode.add(createNode("BoxAndWhiskerChartDemo2", "BoxAndWhiskerChartDemo2.java"));
		defaultmutabletreenode.add(createNode("HistogramDemo1", "HistogramDemo1.java"));
		defaultmutabletreenode.add(createNode("HistogramDemo2", "HistogramDemo2.java"));
		defaultmutabletreenode.add(createNode("MinMaxCategoryPlotDemo1", "MinMaxCategoryPlotDemo1.java"));
		defaultmutabletreenode.add(createNode("NormalDistributionDemo1", "NormalDistributionDemo1.java"));
		defaultmutabletreenode.add(createNode("NormalDistributionDemo2", "NormalDistributionDemo2.java"));
		defaultmutabletreenode.add(createNode("RegressionDemo1", "RegressionDemo1.java"));
		defaultmutabletreenode.add(createNode("ScatterPlotDemo1", "ScatterPlotDemo1.java"));
		defaultmutabletreenode.add(createNode("ScatterPlotDemo2", "ScatterPlotDemo2.java"));
		defaultmutabletreenode.add(createNode("ScatterPlotDemo3", "ScatterPlotDemo3.java"));
		defaultmutabletreenode.add(createNode("ScatterPlotDemo4", "ScatterPlotDemo4.java"));
		defaultmutabletreenode.add(createNode("XYErrorRendererDemo1", "XYErrorRendererDemo1.java"));
		defaultmutabletreenode.add(createNode("XYErrorRendererDemo2", "XYErrorRendererDemo2.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createTimeSeriesChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Time Series Charts");
		defaultmutabletreenode.add(createNode("TimeSeriesDemo1", "TimeSeriesDemo1.java"));
		defaultmutabletreenode.add(createNode("TimeSeriesDemo2", "TimeSeriesDemo2.java"));
		defaultmutabletreenode.add(createNode("TimeSeriesDemo3", "TimeSeriesDemo3.java"));
		defaultmutabletreenode.add(createNode("TimeSeriesDemo4", "TimeSeriesDemo4.java"));
		defaultmutabletreenode.add(createNode("TimeSeriesDemo5", "TimeSeriesDemo5.java"));
		defaultmutabletreenode.add(createNode("TimeSeriesDemo6", "TimeSeriesDemo6.java"));
		defaultmutabletreenode.add(createNode("TimeSeriesDemo7", "TimeSeriesDemo7.java"));
		defaultmutabletreenode.add(createNode("TimeSeriesDemo8", "TimeSeriesDemo8.java"));
		defaultmutabletreenode.add(createNode("TimeSeriesDemo9", "TimeSeriesDemo9.java"));
		defaultmutabletreenode.add(createNode("TimeSeriesDemo10", "TimeSeriesDemo10.java"));
		defaultmutabletreenode.add(createNode("TimeSeriesDemo11", "TimeSeriesDemo11.java"));
		defaultmutabletreenode.add(createNode("TimeSeriesDemo12", "TimeSeriesDemo12.java"));
		defaultmutabletreenode.add(createNode("TimeSeriesDemo13", "TimeSeriesDemo13.java"));
		defaultmutabletreenode.add(createNode("PeriodAxisDemo1", "PeriodAxisDemo1.java"));
		defaultmutabletreenode.add(createNode("PeriodAxisDemo2", "PeriodAxisDemo2.java"));
		defaultmutabletreenode.add(createNode("PeriodAxisDemo3", "PeriodAxisDemo3.java"));
		defaultmutabletreenode.add(createNode("RelativeDateFormatDemo1", "RelativeDateFormatDemo1.java"));
		defaultmutabletreenode.add(createNode("DeviationRendererDemo1", "DeviationRendererDemo1.java"));
		defaultmutabletreenode.add(createNode("DeviationRendererDemo2", "DeviationRendererDemo2.java"));
		defaultmutabletreenode.add(createNode("DifferenceChartDemo1", "DifferenceChartDemo1.java"));
		defaultmutabletreenode.add(createNode("DifferenceChartDemo2", "DifferenceChartDemo2.java"));
		defaultmutabletreenode.add(createNode("CompareToPreviousYearDemo", "CompareToPreviousYearDemo.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createFinancialChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Financial Charts");
		defaultmutabletreenode.add(createNode("CandlestickChartDemo1", "CandlestickChartDemo1.java"));
		defaultmutabletreenode.add(createNode("HighLowChartDemo1", "HighLowChartDemo1.java"));
		defaultmutabletreenode.add(createNode("HighLowChartDemo2", "HighLowChartDemo2.java"));
		defaultmutabletreenode.add(createNode("HighLowChartDemo3", "HighLowChartDemo3.java"));
		defaultmutabletreenode.add(createNode("MovingAverageDemo1", "MovingAverageDemo1.java"));
		defaultmutabletreenode.add(createNode("PriceVolumeDemo1", "PriceVolumeDemo1.java"));
		defaultmutabletreenode.add(createNode("PriceVolumeDemo2", "PriceVolumeDemo2.java"));
		defaultmutabletreenode.add(createNode("YieldCurveDemo1", "YieldCurveDemo1.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createXYChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("XY Charts");
		defaultmutabletreenode.add(createNode("ScatterPlotDemo1", "ScatterPlotDemo1.java"));
		defaultmutabletreenode.add(createNode("ScatterPlotDemo2", "ScatterPlotDemo2.java"));
		defaultmutabletreenode.add(createNode("ScatterPlotDemo3", "ScatterPlotDemo3.java"));
		defaultmutabletreenode.add(createNode("LogAxisDemo1", "LogAxisDemo1.java"));
		defaultmutabletreenode.add(createNode("Function2DDemo1", "Function2DDemo1.java"));
		defaultmutabletreenode.add(createNode("XYBlockChartDemo1", "XYBlockChartDemo1.java"));
		defaultmutabletreenode.add(createNode("XYBlockChartDemo2", "XYBlockChartDemo2.java"));
		defaultmutabletreenode.add(createNode("XYBlockChartDemo3", "XYBlockChartDemo3.java"));
		defaultmutabletreenode.add(createNode("XYLineAndShapeRendererDemo1", "XYLineAndShapeRendererDemo1.java"));
		defaultmutabletreenode.add(createNode("XYLineAndShapeRendererDemo2", "XYLineAndShapeRendererDemo2.java"));
		defaultmutabletreenode.add(createNode("XYSeriesDemo1", "XYSeriesDemo1.java"));
		defaultmutabletreenode.add(createNode("XYSeriesDemo2", "XYSeriesDemo2.java"));
		defaultmutabletreenode.add(createNode("XYSeriesDemo3", "XYSeriesDemo3.java"));
		defaultmutabletreenode.add(createNode("XYShapeRendererDemo1", "XYShapeRendererDemo1.java"));
		defaultmutabletreenode.add(createNode("VectorPlotDemo1", "VectorPlotDemo1.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createMeterChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Dial / Meter Charts");
		defaultmutabletreenode.add(createNode("DialDemo1", "DialDemo1.java"));
		defaultmutabletreenode.add(createNode("DialDemo2", "DialDemo2.java"));
		defaultmutabletreenode.add(createNode("DialDemo2a", "DialDemo2a.java"));
		defaultmutabletreenode.add(createNode("DialDemo3", "DialDemo3.java"));
		defaultmutabletreenode.add(createNode("DialDemo4", "DialDemo4.java"));
		defaultmutabletreenode.add(createNode("DialDemo5", "DialDemo5.java"));
		defaultmutabletreenode.add(createNode("MeterChartDemo1", "MeterChartDemo1.java"));
		defaultmutabletreenode.add(createNode("MeterChartDemo2", "MeterChartDemo2.java"));
		defaultmutabletreenode.add(createNode("MeterChartDemo3", "MeterChartDemo3.java"));
		defaultmutabletreenode.add(createNode("ThermometerDemo1", "ThermometerDemo1.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createMultipleAxisChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Multiple Axis Charts");
		defaultmutabletreenode.add(createNode("DualAxisDemo1", "DualAxisDemo1.java"));
		defaultmutabletreenode.add(createNode("DualAxisDemo2", "DualAxisDemo2.java"));
		defaultmutabletreenode.add(createNode("DualAxisDemo3", "DualAxisDemo3.java"));
		defaultmutabletreenode.add(createNode("DualAxisDemo4", "DualAxisDemo4.java"));
		defaultmutabletreenode.add(createNode("DualAxisDemo5", "DualAxisDemo5.java"));
		defaultmutabletreenode.add(createNode("MultipleAxisDemo1", "MultipleAxisDemo1.java"));
		defaultmutabletreenode.add(createNode("MultipleAxisDemo2", "MultipleAxisDemo2.java"));
		defaultmutabletreenode.add(createNode("MultipleAxisDemo3", "MultipleAxisDemo3.java"));
		defaultmutabletreenode.add(createNode("ParetoChartDemo1", "ParetoChartDemo1.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createCombinedAxisChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Combined Axis Charts");
		defaultmutabletreenode.add(createNode("CombinedCategoryPlotDemo1", "CombinedCategoryPlotDemo1.java"));
		defaultmutabletreenode.add(createNode("CombinedCategoryPlotDemo2", "CombinedCategoryPlotDemo2.java"));
		defaultmutabletreenode.add(createNode("CombinedTimeSeriesDemo1", "CombinedTimeSeriesDemo1.java"));
		defaultmutabletreenode.add(createNode("CombinedXYPlotDemo1", "CombinedXYPlotDemo1.java"));
		defaultmutabletreenode.add(createNode("CombinedXYPlotDemo2", "CombinedXYPlotDemo2.java"));
		defaultmutabletreenode.add(createNode("CombinedXYPlotDemo3", "CombinedXYPlotDemo3.java"));
		defaultmutabletreenode.add(createNode("CombinedXYPlotDemo4", "CombinedXYPlotDemo4.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createGanttChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Gantt Charts");
		defaultmutabletreenode.add(createNode("GanttDemo1", "GanttDemo1.java"));
		defaultmutabletreenode.add(createNode("GanttDemo2", "GanttDemo2.java"));
		defaultmutabletreenode.add(createNode("SlidingGanttDatasetDemo1", "SlidingGanttDatasetDemo1.java"));
		defaultmutabletreenode.add(createNode("XYTaskDatasetDemo1", "XYTaskDatasetDemo1"));
		defaultmutabletreenode.add(createNode("XYTaskDatasetDemo2", "XYTaskDatasetDemo2"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createMiscellaneousChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Miscellaneous");
		defaultmutabletreenode.add(createAnnotationsNode());
		defaultmutabletreenode.add(createCrosshairChartsNode());
		defaultmutabletreenode.add(createDynamicChartsNode());
		defaultmutabletreenode.add(createItemLabelsNode());
		defaultmutabletreenode.add(createLegendNode());
		defaultmutabletreenode.add(createMarkersNode());
		defaultmutabletreenode.add(createOrientationNode());
		defaultmutabletreenode.add(createNode("AxisOffsetsDemo1", "AxisOffsetsDemo1.java"));
		defaultmutabletreenode.add(createNode("BubbleChartDemo1", "BubbleChartDemo1.java"));
		defaultmutabletreenode.add(createNode("BubbleChartDemo2", "BubbleChartDemo2.java"));
		defaultmutabletreenode.add(createNode("CategoryLabelPositionsDemo1", "CategoryLabelPositionsDemo1.java"));
		defaultmutabletreenode.add(createNode("CategoryStepChartDemo1", "CategoryStepChartDemo1.java"));
		defaultmutabletreenode.add(createNode("CompassDemo1", "CompassDemo1.java"));
		defaultmutabletreenode.add(createNode("CompassFormatDemo1", "CompassFormatDemo1.java"));
		defaultmutabletreenode.add(createNode("CompassFormatDemo2", "CompassFormatDemo2.java"));
		defaultmutabletreenode.add(createNode("EventFrequencyDemo1", "EventFrequencyDemo1.java"));
		defaultmutabletreenode.add(createNode("GradientPaintTransformerDemo1", "GradientPaintTransformerDemo1.java"));
		defaultmutabletreenode.add(createNode("GridBandDemo1", "GridBandDemo1.java"));
		defaultmutabletreenode.add(createNode("HideSeriesDemo1", "HideSeriesDemo1.java"));
		defaultmutabletreenode.add(createNode("HideSeriesDemo2", "HideSeriesDemo2.java"));
		defaultmutabletreenode.add(createNode("HideSeriesDemo3", "HideSeriesDemo3.java"));
		defaultmutabletreenode.add(createNode("MultipleDatasetDemo1", "MultipleDatasetDemo1.java"));
		defaultmutabletreenode.add(createNode("PolarChartDemo1", "PolarChartDemo1.java"));
		defaultmutabletreenode.add(createNode("ScatterRendererDemo1", "ScatterRendererDemo1.java"));
		defaultmutabletreenode.add(createNode("SpiderWebChartDemo1", "SpiderWebChartDemo1.java"));
		defaultmutabletreenode.add(createNode("SymbolAxisDemo1", "SymbolAxisDemo1.java"));
		defaultmutabletreenode.add(createNode("ThermometerDemo1", "ThermometerDemo1.java"));
		defaultmutabletreenode.add(createNode("ThermometerDemo2", "ThermometerDemo2.java"));
		defaultmutabletreenode.add(createNode("ThumbnailDemo1", "ThumbnailDemo1.java"));
		defaultmutabletreenode.add(createNode("TranslateDemo1", "TranslateDemo1.java"));
		defaultmutabletreenode.add(createNode("WindChartDemo1", "WindChartDemo1.java"));
		defaultmutabletreenode.add(createNode("YIntervalChartDemo1", "YIntervalChartDemo1.java"));
		defaultmutabletreenode.add(createNode("YIntervalChartDemo2", "YIntervalChartDemo2.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createAnnotationsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Annotations");
		defaultmutabletreenode.add(createNode("AnnotationDemo1", "AnnotationDemo1.java"));
		defaultmutabletreenode.add(createNode("AnnotationDemo2", "AnnotationDemo2.java"));
		defaultmutabletreenode.add(createNode("CategoryPointerAnnotationDemo1", "CategoryPointerAnnotationDemo1.java"));
		defaultmutabletreenode.add(createNode("XYBoxAnnotationDemo1", "XYBoxAnnotationDemo1.java"));
		defaultmutabletreenode.add(createNode("XYPolygonAnnotationDemo1", "XYPolygonAnnotationDemo1.java"));
		defaultmutabletreenode.add(createNode("XYTitleAnnotationDemo1", "XYTitleAnnotationDemo1.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createCrosshairChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Crosshairs");
		defaultmutabletreenode.add(createNode("CrosshairDemo1", "CrosshairDemo1.java"));
		defaultmutabletreenode.add(createNode("CrosshairDemo2", "CrosshairDemo2.java"));
		defaultmutabletreenode.add(createNode("CrosshairDemo3", "CrosshairDemo3.java"));
		defaultmutabletreenode.add(createNode("CrosshairDemo4", "CrosshairDemo4.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createDynamicChartsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Dynamic Charts");
		defaultmutabletreenode.add(createNode("DynamicDataDemo1", "DynamicDataDemo1.java"));
		defaultmutabletreenode.add(createNode("DynamicDataDemo2", "DynamicDataDemo2.java"));
		defaultmutabletreenode.add(createNode("DynamicDataDemo3", "DynamicDataDemo3.java"));
		defaultmutabletreenode.add(createNode("MouseOverDemo1", "MouseOverDemo1.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createItemLabelsNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Item Labels");
		defaultmutabletreenode.add(createNode("ItemLabelDemo1", "ItemLabelDemo1.java"));
		defaultmutabletreenode.add(createNode("ItemLabelDemo2", "ItemLabelDemo2.java"));
		defaultmutabletreenode.add(createNode("ItemLabelDemo3", "ItemLabelDemo3.java"));
		defaultmutabletreenode.add(createNode("ItemLabelDemo4", "ItemLabelDemo4.java"));
		defaultmutabletreenode.add(createNode("ItemLabelDemo5", "ItemLabelDemo5.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createLegendNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Legends");
		defaultmutabletreenode.add(createNode("LegendWrapperDemo1", "LegendWrapperDemo1.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createMarkersNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Markers");
		defaultmutabletreenode.add(createNode("CategoryMarkerDemo1", "CategoryMarkerDemo1.java"));
		defaultmutabletreenode.add(createNode("CategoryMarkerDemo2", "CategoryMarkerDemo2.java"));
		defaultmutabletreenode.add(createNode("MarkerDemo1", "MarkerDemo1.java"));
		defaultmutabletreenode.add(createNode("MarkerDemo2", "MarkerDemo2.java"));
		return defaultmutabletreenode;
	}

	private MutableTreeNode createOrientationNode()
	{
		DefaultMutableTreeNode defaultmutabletreenode = new DefaultMutableTreeNode("Plot Orientation");
		defaultmutabletreenode.add(createNode("PlotOrientationDemo1", "PlotOrientationDemo1.java"));
		defaultmutabletreenode.add(createNode("PlotOrientationDemo2", "PlotOrientationDemo2.java"));
		return defaultmutabletreenode;
	}

	private void displayDescription(String s)
	{
		java.net.URL url = (demo.SuperDemo.class).getResource(s);
		if (url != null)
			try
			{
				descriptionPane.setPage(url);
			}
			catch (IOException ioexception)
			{
				System.err.println("Attempted to read a bad URL: " + url);
			}
		else
			System.err.println("Couldn't find file: " + s);
	}

	public void valueChanged(TreeSelectionEvent treeselectionevent)
	{
		Object obj = null;
		TreePath treepath = treeselectionevent.getPath();
		Object obj1 = treepath.getLastPathComponent();
		if (obj1 != null)
		{
			DefaultMutableTreeNode defaultmutabletreenode = (DefaultMutableTreeNode)obj1;
			Object obj2 = defaultmutabletreenode.getUserObject();
			if (obj2 instanceof DemoDescription)
			{
				DemoDescription demodescription = (DemoDescription)obj2;
				String s = demodescription.getDescription();
				updateSourceCodePanel(s);
				SwingUtilities.invokeLater(new DisplayDemo(this, demodescription));
			} else
			{
				chartContainer.removeAll();
				chartContainer.add(createNoDemoSelectedPanel());
				displayPanel.validate();
				displayDescription("select.html");
				updateSourceCodePanel(null);
			}
		}
		System.out.println(obj1);
	}

	private JPanel createNoDemoSelectedPanel()
	{
		JPanel jpanel = new JPanel(new FlowLayout()) {

			public String getToolTipText()
			{
				return "(" + getWidth() + ", " + getHeight() + ")";
			}

		};
		ToolTipManager.sharedInstance().registerComponent(jpanel);
		jpanel.add(new JLabel("No demo selected"));
		jpanel.setPreferredSize(new Dimension(600, 400));
		return jpanel;
	}

	public static void main(String args[])
	{
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception exception)
		{
			try
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			catch (Exception exception1)
			{
				exception1.printStackTrace();
			}
		}
		SuperDemo superdemo = new SuperDemo("JFreeChart 1.0.13 Demo Collection");
		superdemo.pack();
		RefineryUtilities.centerFrameOnScreen(superdemo);
		superdemo.setVisible(true);
	}



}
