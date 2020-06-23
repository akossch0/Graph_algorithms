package Views;

import Graphs.Graph;
import Graphs.Vertex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class RedVertexImage extends VertexImage {
    public RedVertexImage(Vertex v){
        vertex = v;
        try {
            image = ImageIO.read(new File("src/images/node_red.png"));;
        } catch (IOException e) {
            System.out.println("Error while loading the red vertex image!");
        }
    }
}
