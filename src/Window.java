import javax.swing.*;

public class Window extends JFrame {
    public Window(){
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320 + 20, 320 + 45);
        setLocation(400, 200);
        add(new Game());
        setVisible(true);
    }

    public static void main(String[] args) {
        new Window();
    }
}
