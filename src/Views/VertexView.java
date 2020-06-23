package Views;

import Graphs.Vertex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VertexView implements IView{
    Vertex vertex;
    VertexImage vertexImage;
    public VertexView(Vertex v){
        vertex = v;
        vertexImage = new WhiteVertexImage(vertex);
        vertex.setVertexView(this);
    }

    public void setVertexImage(VertexImage vi){
        vertexImage = vi;
    }

    public VertexImage getVertexImage() {
        return vertexImage;
    }

    @Override
    public void Draw(Graphics graphics) {
        vertexImage.Display(graphics);
    }
}
