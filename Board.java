import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Board extends JPanel implements ActionListener, KeyListener {

    //control moving speed
    private final int DELAY = 150;
    public static String message ;
    // controls the size of the board
    public static final int TILE_SIZE = 50;
    public static final int ROWS = 20;
    public static final int COLUMNS = 20;
    // controls how many basketball appear on the board
    public static final int NUM_COINS = 5;
   
    private ArrayList<Ball> collectedBalls = new ArrayList<>();
    
    private Timer timer;

    private Player player;
    private ArrayList<Ball> balls;

    public Board() {
        // set the board size
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        // set the game board  color
        setBackground(new Color(132, 132, 132));

        // initialize the game state
        player = new Player();
        balls = populateCoins();
        message ="";

        // this timer will call  actionPerformed() method every DELAY ms
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public String getMessage(){
        return message;
    }
    
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // draw our game table.

        drawBackground(g);
        drawScore(g);
        gameover(g);
        for (Ball ball : balls) {
            ball.draw(g, this);
        }
        player.draw(g, this);
        player.drawtail(g,this);

       
        Toolkit.getDefaultToolkit().sync();
    }

   
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //moveforward() move the player by one step
        //tick prevent the player from disappearing off the board
        player.moveforward();
        player.tick();

        // give the player points for collecting balls
        collectCoins();

        // calling repaint() will refresh the board content,
       
        repaint();
    }
    @Override
    public void keyPressed(KeyEvent e) {
       
        player.keyPressed(e);
    }
    @Override
    public void keyTyped(KeyEvent e) {
       
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void drawBackground(Graphics g) {
        // draw background
        g.setColor(new Color(115, 115, 115));
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
           
                if ((row + col) % 2 == 1) {
                    
                    g.fill3DRect(col * TILE_SIZE, 
                    row * TILE_SIZE, 
                    TILE_SIZE, 
                    TILE_SIZE,
                    true);
                    
                }
            }    
        }
    }

    private void drawScore(Graphics g) {
        String text = "$" + player.getScore();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setColor(new Color(30, 201,139));
        g2d.setFont(new Font("Lato", Font.BOLD, 25));       
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());       
        Rectangle rect = new Rectangle(0,TILE_SIZE * (ROWS - 1), TILE_SIZE * COLUMNS, TILE_SIZE);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;   
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(text, x, y);
    }

    private ArrayList<Ball> populateCoins() {
        ArrayList<Ball> coinList = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < NUM_COINS; i++) {
            int coinX = rand.nextInt(COLUMNS);
            int coinY = rand.nextInt(ROWS);
            coinList.add(new Ball(coinX, coinY));
        }

        return coinList;
    }
    private void generateCoin(){
        Random rand = new Random();
        int coinX = rand.nextInt(COLUMNS);
        int coinY = rand.nextInt(ROWS);
        balls.add(new Ball(coinX, coinY));
    }
    private void collectCoins() {
        // allow player to pickup balls
        boolean is_true = false;
        for (Ball ball : balls) {
            // if the player is on the same tile as a balls, collect it
            if (player.getPos().equals(ball.getPos())) {

                player.addScore(100);
                collectedBalls.add(ball);
                is_true=true;
                

            }
        }

        if(is_true){
            generateCoin();
            
        }else{
            player.delete();
        }
        balls.removeAll(collectedBalls);
    }

    public static void gameover(Graphics g) {
        String text = message;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setColor(new Color(255, 0, 0));
        g2d.setFont(new Font("Lato", Font.BOLD, 100));
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        Rectangle rect = new Rectangle(0, TILE_SIZE * (ROWS/2), TILE_SIZE * COLUMNS, TILE_SIZE);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(text, x, y);
    }
    public static void setMessage(String m) {
        message = m;
    }

}