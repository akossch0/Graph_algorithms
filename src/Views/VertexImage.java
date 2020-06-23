package Views;

import Graphs.Vertex;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class VertexImage {
    BufferedImage image;
    Vertex vertex;

    public void Display(Graphics g) {
        g.drawImage(image,vertex.X*25+7,vertex.Y*25+5,null);
    }
}
