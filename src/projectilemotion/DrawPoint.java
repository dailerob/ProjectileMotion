package projectilemotion;

/**
 * Created by Roberto on 12/17/2014.
 */
import java.awt.Graphics;
public interface DrawPoint {



    public int compareTo(DrawPoint point);

    public void draw(Graphics window, double xradian, double yradian, double zradian, double zoom);

    public double getZdepth();


}
