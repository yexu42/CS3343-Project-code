import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Queue;
import java.util.Iterator;
import java.util.LinkedList;
public class Player {
    public static Queue<Point> queue = new LinkedList<Point>();
    // image that represents the player's position on the board
    private BufferedImage image;
    private BufferedImage image2;
    // current position of the player on the board grid
    private static Point pos;
    private static Point old;
    // keep track of the player's score
    private int score;
    private char direction;
    public Player() {
        loadImage();
        pos = new Point(0, 0);
        score = 0;
        direction = 'E';
        //queue.add(new Point(0, 0));
    }

    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            image = ImageIO.read(new File("images/player.jpg"));
            image2 = ImageIO.read(new File("images/player2.jpg"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(
            image, 
            pos.x * Board.TILE_SIZE, 
            pos.y * Board.TILE_SIZE, 
            observer
        );
    }
    
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) {
            direction = 'N';
        }
        if (key == KeyEvent.VK_RIGHT) {
            direction = 'E';
        }
        if (key == KeyEvent.VK_DOWN) {
            direction = 'S';
        }
        if (key == KeyEvent.VK_LEFT) {
            direction = 'W';
        }
    }

    public void tick() {
        if (pos.x < 0) {
            pos.x = 0;
            Board.setMessage("Game Over");

        } else if (pos.x >= Board.COLUMNS) {
            pos.x = Board.COLUMNS - 1;
            Board.setMessage("Game Over");
        }
        // prevent the player from moving off the edge of the board.
        if (pos.y < 0) {
            pos.y = 0;
            Board.setMessage("Game Over");
        } else if (pos.y >= Board.ROWS) {
            pos.y = Board.ROWS - 1;
            Board.setMessage("Game Over");
        }
        for( Iterator<Point> it=queue.iterator(); it.hasNext();){
            Point p=it.next();
            if(pos.x==p.x && pos.y==p.y){
                Board.setMessage("Game Over");
            }
        }
    }

    public String getScore() {
        return String.valueOf(score);
    }

    public void addScore(int amount) {
        score += amount;
    }

    public Point getPos() {
        return pos;
    }

    public void moveforward() {
        old=new Point(pos.x,pos.y);
        queue.add(old);
        //queue.remove();
        if(direction == 'E'){

            pos.translate(1, 0);
        }else if(direction == 'W'){
            pos.translate(-1, 0);
        }else if (direction == 'N'){
            pos.translate(0, -1);
        }else{
            pos.translate(0, 1);
        }
    }

    

    public void drawtail(Graphics g, ImageObserver observer) {
        int i = queue.size();
        if(i == 0){   
        }else{
            //queue.remove();
            //queue.add(new Point(pos.x,pos.y));
            for( Iterator<Point> it=queue.iterator(); it.hasNext();){
                Point p=it.next();
                g.drawImage(
            image2, 
            p.x * Board.TILE_SIZE, 
            p.y * Board.TILE_SIZE, 
            observer
        );
        
    
            }
            

            }
        }

    public void delete() {
        queue.remove();
    }
    }

