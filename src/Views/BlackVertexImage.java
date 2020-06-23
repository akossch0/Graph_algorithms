package Views;

import Graphs.Vertex;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BlackVertexImage extends VertexImage {
    public BlackVertexImage(Vertex v){
        vertex = v;
        try {
            image = ImageIO.read(new File("src/images/node_black.png"));;
        } catch (IOException e) {
            System.out.println("Error while loading the black vertex image!");
        }
    }
}
