import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ball implements Food{
    
    // image
    private BufferedImage image;
    // current position of the ball
    protected Point pos;

    public Ball(int x, int y) {
        
        loadImage();

        // initialize the state
        pos = new Point(x, y);
    }

    public void loadImage() {
        try {
            // you can change the filename;
            
            image = ImageIO.read(new File("images/ball.jpg"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
      
        // draw the ball in position(x,y);
        g.drawImage(
            image, 
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
        return 0;
    }

    @Override
    public void eat() {
        // TODO Auto-generated method stub
        
    }

}