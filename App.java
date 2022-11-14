import javax.swing.*;

class App {
   // this is the token for github
   //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
    private static void initWindow() {
        
        JFrame window = new JFrame("Puzzle game");
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        Board board = new Board();
        
        window.add(board);
        
        window.addKeyListener(board);
        
        
    
        window.pack();
        
        window.setLocationRelativeTo(null);
        
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initWindow();
            }
        });
    }
}