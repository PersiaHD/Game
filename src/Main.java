import javax.swing.*;

/**
 * Created by Никита on 20.11.2014.
 */
public class Main {
    public static void main(String[] args) {
        Scene scene = new Scene();
        scene.start();
        JFrame frame = new JFrame("Puzzle Block");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.add(scene);
        frame.setVisible(true);
        frame.addKeyListener(new KeyInputHandler());
    }
}

/* управление реализовано стрелками Left, Right
    Down - ускорить падение
    Left Control - отменить последнее действие
 */