import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
public class Player {
    public static Queue<Point> queue = new LinkedList<Point>();
    // image that represents the player's position on the board
    private BufferedImage image;
    private BufferedImage image2;
    private ArrayList<BufferedImage> images = new ArrayList<>();
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
            //images.add(image);
            //images.add(image2);
            for(int i=1;i<8;i++){
                String file="images/Lv"+i*5+".jpg";
                BufferedImage image3 = ImageIO.read(new File(file));
                images.add(image3);


            }
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
        
        if (key == KeyEvent.VK_UP&&direction != 'S') {
            direction = 'N';
        }
        if (key == KeyEvent.VK_RIGHT&&direction != 'W') {
            direction = 'E';
        }
        if (key == KeyEvent.VK_DOWN&&direction != 'N') {
            direction = 'S';
        }
        if (key == KeyEvent.VK_LEFT&&direction != 'E') {
            direction = 'W';
        }
    }
    private void gameover(){
    	score =0;
        Board.setMessage("Game Over");
        Sound s=new Sound();
            s.sound(3);
    }
    public void tick() {
        if(score > 39000){
            Board.setMessage("You win,Congratulations");
            score =0;
            Sound s=new Sound();
            s.sound(4);

        }
        if (pos.x < 0) {
            pos.x = Board.COLUMNS;
            gameover();

        } else if (pos.x >= Board.COLUMNS) {
            pos.x = 0;
            gameover();
        }
        // prevent the player from moving off the edge of the board.
        if (pos.y < 0) {
            pos.y = Board.ROWS;
            gameover();
        } else if (pos.y >= Board.ROWS) {
            pos.y = 0;
            gameover();
        }
        for( Iterator<Point> it=queue.iterator(); it.hasNext();){
            Point p=it.next();
            if(pos.x==p.x && pos.y==p.y){
                gameover();
            }
        }
    }

    public String getScore() {
        return String.valueOf(score);
    }
    public int getScoreInt() {
        return score;
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
        checkLv();
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

    private void checkLv() {
        int i=score/5000;
        //String file="images/Lv"+i*5+"jpg";
        if(i !=0&&i<9){
        image2=images.get(i-1);
    }
    }



    public void delete() {
        if(queue.size()!=0){
        queue.remove();}
    }
    }

