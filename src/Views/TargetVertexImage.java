package Views;

import Graphs.Vertex;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TargetVertexImage extends VertexImage{
    public TargetVertexImage(Vertex v){
    vertex = v;
    try {
        image = ImageIO.read(new File("src/images/node_target.png"));;
    } catch (IOException e) {
        System.out.println("Error while loading the target vertex image!");
    }
}

}
