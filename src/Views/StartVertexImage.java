package Views;

import Graphs.Vertex;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class StartVertexImage extends VertexImage{
    public StartVertexImage(Vertex v){
        vertex = v;
        try {
            image = ImageIO.read(new File("src/images/node_start.png"));;
        } catch (IOException e) {
            System.out.println("Error while loading the start vertex image!");
        }
    }
}
