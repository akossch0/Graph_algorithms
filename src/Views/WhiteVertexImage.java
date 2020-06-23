package Views;

import Graphs.Vertex;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class WhiteVertexImage extends VertexImage {
    public WhiteVertexImage(Vertex v){
        vertex = v;
        try {
            image = ImageIO.read(new File("src/images/node_white.png"));;
        } catch (IOException e) {
            System.out.println("Error while loading the white vertex image!");
        }
    }
}
