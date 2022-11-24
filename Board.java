import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
//import java.awt.image.BufferedImage;
//import java.awt.image.ImageObserver;
//import java.awt.Point;
//import java.io.File;
//import java.io.IOException;
//import javax.imageio.ImageIO;

public class Board extends JPanel implements ActionListener, KeyListener {

    //control moving speed
    private static int DELAY = 300;
    public static String message ;
    // controls the size of the board
    private static int foodnum=1;
    public static final int TILE_SIZE = 50;
    public static final int ROWS = 18;
    public static final int COLUMNS = 30;
    // controls how many basketball appear on the board
    public static int NUM_COINS = 5;
   
    private ArrayList<Ball> collectedBalls = new ArrayList<>();
    //private BufferedImage image;
    private Timer timer;
    private int time =0;
    private static Player player;
    private static ArrayList<Ball> balls;
    private ArrayList<Food> foods;
    public Board() {
        // set the board size
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        // set the game board  color
        setBackground(new Color(255, 255, 254));

        // initialize the game state
        player = new Player();
        balls = populateCoins();
        foods = populateFoods();
        /*try{
        image = ImageIO.read(new File("images/boom.jpg"));
        }catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        } */

        message ="";

        // this timer will call  actionPerformed() method every DELAY ms
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public String getMessage(){
        return message;
    }
    
    
    public static Player getpPlayer(){
        return player;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // draw our game table.

        
        drawBackground(g);
        gameover(g);
        if((time*DELAY)%35000<DELAY) {
        	message="";
        	regenerateballs();
        }
        for (Ball ball : balls) {
            ball.draw(g, this);
        }
        if(time%30 < (1000/DELAY)*5){
            for(Food food: foods){
            food.draw(g, this);
            drawAlert(g);
            }
        }else{
            foods=populateFoods();
        }
        
        drawScore(g);
        drawtime(g);
        //drawLevel(g);
        player.draw(g, this);
        player.drawtail(g,this);

        drawBoard(g);
        Toolkit.getDefaultToolkit().sync();
    }

   public static void eatOilcake() {
	   for(int i=0; i<15;i++) {
	        generateCoin();
	    	}
	   foodnum+=1;
   }
    
    private void drawAlert(Graphics g) {
        int timeleft = 5-(time%30)/(1000/DELAY);
        String text ="Foods dispear:"+timeleft+" s!";
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
        g2d.setColor(new Color(255, 60, 60));
        g2d.setFont(new Font("Lato", Font.BOLD, 35));
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        Rectangle rect = new Rectangle(0, 0, TILE_SIZE * COLUMNS, TILE_SIZE);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(text, x, y);
    }
    
    public void drawtime(Graphics g) {
        //int level = player.getScoreInt()/1000;
        String text ="Steps:"+time+"      refresh time: "+(35000-DELAY*time%35000)/1000+"s"+"     Food Num:"+foodnum;
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
        g2d.setColor(new Color(255, 60, 60));
        g2d.setFont(new Font("Lato", Font.BOLD, 20));
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        Rectangle rect = new Rectangle(0, 50, TILE_SIZE * COLUMNS, TILE_SIZE);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(text, x, y);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        time += 1;
        //DELAY = 300 - player.getScoreInt()/150;
        timer.setDelay(300 - player.getScoreInt()/150);

        //moveforward() move the player by one step
        //tick prevent the player from disappearing off the board
        player.moveforward();
        player.tick();

        // give the player points for collecting balls
        collectCoins();
        if(time%30 < (1000/DELAY)*5){
        collectFood();}

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
        
        g.setColor(new Color(224, 224, 224));
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
            	
           
                if ((row + col) % 2 == 1) {
                    
                    /* g.fill3DRect(col * TILE_SIZE, 
                    row * TILE_SIZE, 
                    TILE_SIZE, 
                    TILE_SIZE,
                    true); */
                	 g.fillRect(
                             col * TILE_SIZE, 
                             row * TILE_SIZE, 
                             TILE_SIZE, 
                             TILE_SIZE
                         );
                }
            }    
        }
    }
        private void drawBoard(Graphics g) {
        g.setColor(new Color(100, 100, 100));
        for (int row = 0; row < ROWS; row++) {
        	g.fill3DRect(0, 
                    row * TILE_SIZE, 
                    TILE_SIZE/5, 
                    TILE_SIZE,
                    true);
        	g.fill3DRect(COLUMNS * TILE_SIZE-10, 
                    row * TILE_SIZE, 
                    TILE_SIZE/5, 
                    TILE_SIZE,
                    true);
            }
        for (int row = 0; row < COLUMNS; row++) {
        	g.fill3DRect(row * TILE_SIZE, 
                    0, 
                    TILE_SIZE, 
                    TILE_SIZE/5,
                    true);
        	g.fill3DRect(
                    row * TILE_SIZE,
                    ROWS * TILE_SIZE-10,
                    TILE_SIZE, 
                    TILE_SIZE/5,
                    true);
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
        g2d.setColor(new Color(10, 0,0));
        g2d.setFont(new Font("Lato", Font.BOLD, 30));       
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());       
        Rectangle rect = new Rectangle(0,TILE_SIZE * (ROWS - 2), TILE_SIZE * COLUMNS, TILE_SIZE);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;   
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(text, x, y);
    }
    public void regenerateballs() {
    	ArrayList<Ball> coinList = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < NUM_COINS; i++) {
            int coinX = rand.nextInt(COLUMNS);
            int coinY = rand.nextInt(ROWS);
            coinList.add(new Ball(coinX, coinY));
        }
        balls=coinList;
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
    private ArrayList<Food> populateFoods() {
        ArrayList<Food> coinList = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < foodnum; i++) {
            int seed = rand.nextInt(100);
            int coinX = rand.nextInt(COLUMNS);
            int coinY = rand.nextInt(ROWS);
            if(seed<85){
            coinList.add(new Litchi(coinX, coinY));}
            else{
            coinList.add(new OilChake(coinX, coinY));
            }
        }

        return coinList;
    }
    public static void generateCoin(){
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

                player.addScore(200);
                collectedBalls.add(ball);
                is_true=true;
                

            }
        }

        if(is_true){
            generateCoin();
            Sound s=new Sound();
            s.sound(1);
            
        }else{
            player.delete();
        }
        balls.removeAll(collectedBalls);
    }
    private void collectFood() {

        boolean is_true = false;
        for (Food food: foods) {
            // if the player is on the same tile as a balls, collect it
            if (player.getPos().equals(food.getPos())) {

                player.addScore(food.getmarks());
                //DELAY -= 50;
                is_true=true;
                food.eat();
                

            }
        }

        if(is_true){

            
            NUM_COINS+=1;
            //foods = populateFoods();
            
        }else{
            //player.delete();
        }
        
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
        g2d.setFont(new Font("Lato", Font.BOLD, 80));
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
