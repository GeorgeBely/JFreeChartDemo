import graphics.CustomXYDataset;
import graphics.ScatterPlotService;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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
        for (double i = 0; i < 10; i = i + 0.1) {
            val.add(new Point(i, (13.5 - 1.5 * i)));
        }
        data.add(val);
        List<Point> val2 = new ArrayList<>();
        for (double i = 0; i < 10; i = i + 0.1) {
            val2.add(new Point(i, 7 - (0.5 * i)));
        }
        data.add(val2);
        List<Point> val3 = new ArrayList<>();
        for (double i = 0; i < 10; i = i + 0.1) {
            val3.add(new Point(i, 23.0/3.0 - 2.0/3.0 * i));
        }
        data.add(val3);


        JFreeChart sca = ScatterPlotService.createChart("lolka", "xz", "0_O", new CustomXYDataset(data));


        ChartPanel cp = new ChartPanel(sca) {{
            setLocation(5, 5);
            setSize(470, 450);
            setVisible(true);
        }};
        panel.add(cp);
    }

}
