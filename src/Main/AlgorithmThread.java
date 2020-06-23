package Main;

import Graphs.Vertex;
import Views.GreenVertexImage;
import Views.RedVertexImage;
import Views.StartVertexImage;
import Views.TargetVertexImage;

import javax.swing.*;
import java.util.List;

public class AlgorithmThread implements Runnable {
    private List<Vertex> vertices;
    private Vertex target;
    private JPanel drawPanel;
    private Mainframe mainframe;
    Thread t;

    public AlgorithmThread(JPanel drawPanel, Mainframe mf){
        this.drawPanel = drawPanel;
        t = new Thread(this,"My thread");
        mainframe = mf;
    }

    public void start(){
        t.start();
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void setTarget(Vertex t){
        target = t;
    }

    @Override
    public void run() {
        try {
            for (Vertex v : vertices) {
                if(v.equals(target))
                    break;
                if (!(v.getVertexView().getVertexImage() instanceof StartVertexImage) && !(v.getVertexView().getVertexImage() instanceof TargetVertexImage))
                    v.setVertexImage(new RedVertexImage(v));
                drawPanel.repaint();
                synchronized (this) {
                    this.wait(15);
                }
            }
            for(Vertex v : target.getShortestPath()){
                if(!(v.getVertexView().getVertexImage() instanceof StartVertexImage)) {
                    v.setVertexImage(new GreenVertexImage(v));
                    drawPanel.repaint();
                }
                synchronized (this) {
                    this.wait(15);
                }
            }
            mainframe.getClearButton().setEnabled(true);
            System.out.println("Thread exited: " + t.getName());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
