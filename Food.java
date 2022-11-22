import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
public interface Food {
    public void loadImage();
    public void draw(Graphics g, ImageObserver observer);
    public Point getPos();
    public int getmarks();
    public void eat(); 

    
}
