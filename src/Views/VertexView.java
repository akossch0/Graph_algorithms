package Views;

import Graphs.Vertex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VertexView implements IView{

    Vertex vertex;
    BufferedImage basicVertexImage;
    BufferedImage greenVertexImage;
    BufferedImage redVertexImage;
    BufferedImage startVertexImage;
    BufferedImage targetVertexImage;
    public VertexView(Vertex v){
        vertex = v;
        try {
            basicVertexImage = ImageIO.read(new File("src/images/node_basic.png"));
            greenVertexImage = ImageIO.read(new File("src/images/node_green.png"));;
            redVertexImage = ImageIO.read(new File("src/images/node_red.png"));;
            startVertexImage = ImageIO.read(new File("src/images/node_start.png"));;
            targetVertexImage = ImageIO.read(new File("src/images/node_target.png"));;
        } catch (IOException e) {
            System.out.println("Error while loading the vertex image!");
        }
    }

    @Override
    public void Draw(Graphics graphics) {
        if(!vertex.selected)
            graphics.drawImage(basicVertexImage,vertex.X*25+7,vertex.Y*25+5,null);
        else
            graphics.drawImage(redVertexImage,vertex.X*25+7,vertex.Y*25+5,null);
    }
}
