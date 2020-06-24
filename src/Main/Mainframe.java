package Main;

import Graphs.Graph;
import Graphs.Vertex;
import Views.BlackVertexImage;
import Views.RedVertexImage;
import Views.StartVertexImage;
import Views.WhiteVertexImage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Mainframe {
    private String algo = "Dijkstra";
    private JPanel panel1;
    private JPanel panel2;
    private JComboBox algComboBox;
    private JButton clearButton;
    private JPanel drawPanel;
    private JButton startButton;
    private JComboBox wallComboBox;
    private Graph graph;
    private AlgorithmThread algThread;
    private Vertex source;
    private Vertex target;

    public Mainframe() {
        graph = new Graph();
        graph.generateGraph(24, 39);
        graph.setFrame(this);
        $$$setupUI$$$();
        initListeners();

        source = graph.RandomSource();
        target = graph.RandomTarget();
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getStartButton() {
        return startButton;
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
                    if (graph.getElement(clickedId) != null && graph.getElement(clickedId).getVertexView().getVertexImage() instanceof WhiteVertexImage) {
                        graph.getElement(clickedId).setVertexImage(new BlackVertexImage(graph.getElement(clickedId)));
                        //graph.getElement(clickedId).getNeighbours().clear();
                    } else if (graph.getElement(clickedId) != null && graph.getElement(clickedId).getVertexView().getVertexImage() instanceof BlackVertexImage) {
                        graph.getElement(clickedId).setVertexImage(new WhiteVertexImage(graph.getElement(clickedId)));
                        //graph.RestoreNeighbours(clickedId);
                    }
                    drawPanel.repaint();
                    //System.out.println(x + ";" + y);
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

                if (graph.getElement(clickedId) != null && graph.getElement(clickedId).getVertexView().getVertexImage() instanceof WhiteVertexImage) {
                    graph.getElement(clickedId).setVertexImage(new BlackVertexImage(graph.getElement(clickedId)));
                    //graph.getElement(clickedId).getNeighbours().clear();
                }
                drawPanel.repaint();
                //System.out.println(x + ";" + y);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.clear();
                drawPanel.repaint();
                startButton.setEnabled(true);
                wallComboBox.setEnabled(true);
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
                List<Vertex> nodes = new ArrayList<>();
                switch (algo) {
                    case "Dijkstra":
                        nodes = graph.Dijkstra(source);
                        break;
                    case "DFS":
                        Map<Vertex, Boolean> visited = new HashMap<>();
                        for (Vertex v : graph.getVertices()) {
                            visited.put(v, false);
                        }
                        graph.dfs(source, visited, nodes);
                        List<Vertex> list = new LinkedList<>(nodes);
                        List<Vertex> list2 = new LinkedList<>();
                        for (Vertex v : list) {
                            list2.add(v);
                            if (v.equals(target))
                                break;

                        }
                        target.setShortestPath(new LinkedList<>(list2));
                        break;
                    case "BFS":

                        break;
                    default:

                        break;
                }

                algThread = new AlgorithmThread(drawPanel, Mainframe.this);
                algThread.setVertices(nodes);
                algThread.setTarget(target);
                algThread.start();

                //graph.connectedComponents();
                //System.out.println();
                graph.InTheSameComponent(source, target);

                startButton.setEnabled(false);
                clearButton.setEnabled(false);
                wallComboBox.setEnabled(false);
            }
        });

        wallComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.clear();
                startButton.setEnabled(true);
                switch (wallComboBox.getSelectedItem().toString()) {
                    case "Random walls":
                        graph.RandomWalls(0.2);
                        break;
                    case "Easy maze":
                        graph.EasyMaze(24, 39);
                        //graph.connectedComponents();
                        //TODO: vertex törlése ténylegesen, ne csak grafikusan
                        /*List<Vertex> vert = graph.Dijkstra(source);
                        while (!TargetIsFindable(vert)) {
                            graph.EasyMaze(24, 39);
                            drawPanel.repaint();
                            vert = graph.Dijkstra(source);
                            System.out.println(vert.size());
                        }*/

                        break;
                    case "Random maze":
                        //TODO: implementation
                        break;
                    default:
                        break;
                }
                drawPanel.repaint();
            }
        });

    }

    private boolean TargetIsFindable(List<Vertex> nodes) {
        return nodes.contains(target);
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

        String[] wallTypes = {"Random walls", "Random maze", "Easy maze"};
        wallComboBox = new JComboBox(wallTypes);
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
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel3.add(panel6, BorderLayout.CENTER);
        startButton.setBackground(new Color(-12828607));
        Font startButtonFont = this.$$$getFont$$$("Fira Code Medium", -1, 16, startButton.getFont());
        if (startButtonFont != null) startButton.setFont(startButtonFont);
        startButton.setForeground(new Color(-1));
        startButton.setPreferredSize(new Dimension(200, 30));
        startButton.setText("Start");
        panel6.add(startButton, BorderLayout.WEST);
        wallComboBox.setBackground(new Color(-12828607));
        Font wallComboBoxFont = this.$$$getFont$$$("Fira Code Medium", -1, 16, wallComboBox.getFont());
        if (wallComboBoxFont != null) wallComboBox.setFont(wallComboBoxFont);
        wallComboBox.setForeground(new Color(-1));
        wallComboBox.setOpaque(false);
        wallComboBox.setPopupVisible(false);
        wallComboBox.setPreferredSize(new Dimension(200, 30));
        panel6.add(wallComboBox, BorderLayout.EAST);
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
