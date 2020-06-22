package Main;

import Graphs.Graph;
import Graphs.Vertex;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Mainframe {
    private String algo;
    private JPanel panel1;
    private JPanel panel2;
    private JComboBox algComboBox;
    private JButton clearButton;
    private JPanel drawPanel;
    private JButton startButton;
    private Graph graph;

    public Mainframe() {
        graph = new Graph();
        graph.generateGraph(24, 39);
        graph.setFrame(this);
        $$$setupUI$$$();
        initListeners();
    }

    public JPanel getDrawPanel() {
        return drawPanel;
    }

    public int countCoord(int mouseCoord) {
        double val = mouseCoord / 25.0;
        Double Value = val;
        return Value.intValue();
    }

    public void initListeners() {
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getX() - 7 < 39 * 25 && e.getY() - 5 < 24 * 25) {
                    int x = countCoord(e.getX() - 7);
                    int y = countCoord(e.getY() - 5);

                    int clickedId = 39 * y + x;
                    if (graph.getElement(clickedId) != null)
                        graph.getElement(clickedId).selected = !graph.getElement(clickedId).selected;
                    drawPanel.repaint();
                    System.out.println(x + ";" + y);
                }
            }
        });

        drawPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (e.getX() - 7 > 39 * 25 || e.getY() - 5 > 24 * 25 || e.getX() - 7 < 0 || e.getY() - 5 < 0)
                    return;
                int x = countCoord(e.getX() - 7);
                int y = countCoord(e.getY() - 5);

                int clickedId = 39 * y + x;

                if (graph.getElement(clickedId) != null && !graph.getElement(clickedId).selected)
                    graph.getElement(clickedId).selected = true;
                drawPanel.repaint();
                System.out.println(x + ";" + y);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Vertex v : graph.getVertices()) {
                    v.selected = false;
                }
                drawPanel.repaint();
            }
        });

        algComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algo = (String) algComboBox.getSelectedItem();
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vertex source = null;
                int size = graph.getVertices().size();
                int item = new Random().nextInt(size);
                int i = 0;
                for (Vertex obj : graph.getVertices()) {
                    if (i == item)
                        source = obj;
                    i++;
                }
                source = graph.getElement(0);

                //printRecursively(source);
                //graph.Dijkstra(source);
                //graph.printGraph();
                graph.calculateShortestPathFromSource(graph, source);
            }
        });
    }

    void printRecursively(Vertex vert) {
        Set<Vertex> visited = new HashSet<>();

        for (Vertex v : vert.getNeighbours().keySet()) {
            if (v != null && !visited.contains(v)) {
                visited.add(v);
                System.out.println("Vertex <" + v.getId() + "> visited");
            }
            printRecursively(v);
        }
    }

    public static void Run(String[] args) {
        JFrame frame = new JFrame("Algorithms");
        frame.setContentPane(new Mainframe().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        //opens in the center of the monitor
        frame.setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                graph.getView().setGraphics(g);
                graph.getView().Update();
            }
        };

        clearButton = new JButton();
        String[] algorithms = {"Dijkstra", "A-search", "BFS", "DFS"};
        algComboBox = new JComboBox(algorithms);
        startButton = new JButton();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setPreferredSize(new Dimension(1000, 700));
        panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setBackground(new Color(-1));
        panel2.setPreferredSize(new Dimension(1000, 700));
        panel1.add(panel2, BorderLayout.CENTER);
        panel2.setBorder(BorderFactory.createTitledBorder(null, "Graph algorithms", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("JetBrains Mono", Font.ITALIC, 28, panel2.getFont()), new Color(-16777216)));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel3.setPreferredSize(new Dimension(0, 50));
        panel2.add(panel3, BorderLayout.NORTH);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel4.setPreferredSize(new Dimension(200, 0));
        panel3.add(panel4, BorderLayout.WEST);
        algComboBox.setBackground(new Color(-12828607));
        Font algComboBoxFont = this.$$$getFont$$$("Fira Code Medium", -1, 16, algComboBox.getFont());
        if (algComboBoxFont != null) algComboBox.setFont(algComboBoxFont);
        algComboBox.setForeground(new Color(-1));
        algComboBox.setOpaque(false);
        algComboBox.setPopupVisible(false);
        panel4.add(algComboBox, BorderLayout.CENTER);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel5.setPreferredSize(new Dimension(200, 0));
        panel3.add(panel5, BorderLayout.EAST);
        clearButton.setBackground(new Color(-12828607));
        Font clearButtonFont = this.$$$getFont$$$("Fira Code Medium", -1, 16, clearButton.getFont());
        if (clearButtonFont != null) clearButton.setFont(clearButtonFont);
        clearButton.setForeground(new Color(-1));
        clearButton.setText("Clear");
        panel5.add(clearButton, BorderLayout.CENTER);
        startButton.setBackground(new Color(-12828607));
        Font startButtonFont = this.$$$getFont$$$("Fira Code Medium", -1, 16, startButton.getFont());
        if (startButtonFont != null) startButton.setFont(startButtonFont);
        startButton.setForeground(new Color(-1));
        startButton.setText("Start");
        panel3.add(startButton, BorderLayout.CENTER);
        drawPanel.setPreferredSize(new Dimension(1000, 650));
        panel2.add(drawPanel, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
