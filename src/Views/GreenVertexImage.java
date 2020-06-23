package Views;

import Graphs.Vertex;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GreenVertexImage extends VertexImage{
    public GreenVertexImage(Vertex v){
        vertex = v;
        try {
            image = ImageIO.read(new File("src/images/node_green.png"));;
        } catch (IOException e) {
            System.out.println("Error while loading the green vertex image!");
        }
    }
}
