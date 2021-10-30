import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Game extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private  int gameScore;

    public Game(){
        setBackground(Color.BLACK.brighter());
        getImage();
        initGame();
        addKeyListener(new Key());
        setFocusable(true);
    }
    public int dificultLevel(){
        int x = 300;
        if(gameScore >= 0 && gameScore < 10) x = 300;
        if(gameScore >= 10 && gameScore < 20) x = 275;
        if(gameScore >= 20 && gameScore < 30) x = 250;
        if(gameScore >= 30 && gameScore < 40) x = 225;
        if(gameScore >= 40 && gameScore < 50) x = 200;
        if(gameScore >= 50 && gameScore < 60) x = 175;
        if(gameScore >= 60 && gameScore < 70) x = 150;
        if(gameScore >= 70) x = 125;
        return x;
    }
    public void initGame(){
        createApple();
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(dificultLevel(), this);
        timer.start();
    }
    public void createApple(){
        for (int i = 0; i < dots; i++) {
            appleX = new Random().nextInt(20) * DOT_SIZE;
            if (appleX == x[i] * DOT_SIZE) continue;
            appleY = new Random().nextInt(20) * DOT_SIZE;
            if (appleY == y[i] * DOT_SIZE) continue;
        }
    }
    public void getImage(){
        ImageIcon iia = new ImageIcon("bombed.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("bomb.png");
        dot = iid.getImage();

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame){
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
                g.drawImage(apple, appleX, appleY, this);
                if(x[i] == appleX && y[i] == appleY) continue;
            }
        } else {
            String str = "GAME OVER UOUR SCORE: " + gameScore;
            g.setColor(Color.RED);
            g.drawString(str, 125, SIZE/2);
        }
    }
    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) x[0] -= DOT_SIZE;
        if (right) x[0] += DOT_SIZE;
        if (up) y[0] -= DOT_SIZE;
        if (down) y[0] += DOT_SIZE;
    }
    public void checkApple(){
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            gameScore++;
            createApple();
        }
    }
    public void checkRange(){
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) inGame = false;
            if (x[0] > SIZE) inGame = false;
            if (x[0] < 0) inGame = false;
            if (y[0] > SIZE) inGame = false;
            if (y[0] < 0) inGame = false;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkApple();
            checkRange();
            move();
        }
        repaint();
    }
    class Key extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down){
                up = true;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up){
                down = true;
                right = false;
                left = false;
            }
        }
    }
}
