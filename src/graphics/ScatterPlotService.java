package graphics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

public class ScatterPlotService {

    public static JFreeChart createChart(String title, String titleX, String titleY, CustomXYDataset data) {
        JFreeChart jfreechart = ChartFactory.createScatterPlot(title, titleX, titleY, data, PlotOrientation.VERTICAL, true, true, false);

        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setDomainCrosshairLockedOnData(true);
        xyplot.setRangeCrosshairVisible(true);
        xyplot.setRangeCrosshairLockedOnData(true);
        xyplot.setDomainZeroBaselineVisible(true);
        xyplot.setRangeZeroBaselineVisible(true);
        xyplot.setDomainPannable(true);
        xyplot.setRangePannable(true);

        NumberAxis numberaxis = (NumberAxis) xyplot.getDomainAxis();
        numberaxis.setAutoRangeIncludesZero(false);

        return jfreechart;
    }


}

