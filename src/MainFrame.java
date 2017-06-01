import graphics.CustomXYDataset;
import graphics.ScatterPlotService;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import graphics.Point;

public class MainFrame extends JFrame {

    private static final String FRAME_NAME = "TestGraphics";

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;


    public MainFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - WIDTH / 2, screenSize.height / 2 - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle(FRAME_NAME);

        JPanel panel = new JPanel() {{
            setFocusable(true);
            setLayout(null);
        }};
        add(panel);

        List<List<Point>> data = new ArrayList<>();
        List<Point> val = new ArrayList<>();
        List<Point> va3 = new ArrayList<>();
        for (double i = 0; i <= 1000; i = i + 1) {
            Double d = new Random().nextDouble() * 1500;
            if (d <= 200 || d >= 800)
                va3.add(new Point(d, i));
            else
                val.add(new Point(d, i));
        }
        data.add(val);
        List<Point> val2 = new ArrayList<>();
        for (double i = 0; i < 1000; i = i + 0.1) {
            val2.add(new Point(0, i));
            val2.add(new Point(200, i));
            val2.add(new Point(800, i));
            val2.add(new Point(1000, i));
        }
        data.add(val2);
        data.add(va3);

        JFreeChart sca = ScatterPlotService.createChart("lolka", "xz", "0_O", new CustomXYDataset(data));


        ChartPanel cp = new ChartPanel(sca) {{
            setLocation(5, 5);
            setSize(470, 450);
            setVisible(true);
        }};
        panel.add(cp);
    }

}
