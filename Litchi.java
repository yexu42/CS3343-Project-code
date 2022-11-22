import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Litchi implements Food{
    private int marks=2000;
    // image
    private BufferedImage image2;
    protected Point pos;
    // current position of the ball
    //private Point pos;

    public Litchi(int x, int y) {
        
        loadImage();

        // initialize the state
        pos = new Point(x, y);
    }
    
    public void loadImage() {
        try {
            // you can change the filename;
            
            image2 = ImageIO.read(new File("images/Litchi.jpg"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
      
        // draw the ball in position(x,y);
        g.drawImage(
            image2, 
            pos.x * Board.TILE_SIZE, 
            pos.y * Board.TILE_SIZE, 
            observer
        );
    }

    public Point getPos() {
        return pos;
    }

    @Override
    public int getmarks() {
        // TODO Auto-generated method stub
        return marks;
    }

    @Override
    public void eat() {
        // TODO Auto-generated method stub
        Board.generateCoin();
        Sound s=new Sound();
            s.sound(2);
            
            
        
    }

}