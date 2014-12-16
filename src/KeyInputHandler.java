import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInputHandler extends KeyAdapter{//обработка событий нажатия клавиш
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Scene.leftPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Scene.rightPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Action.speed = 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL){
            Scene.undo = true;
        }
    }
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Scene.leftPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Scene.rightPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Action.speed = 1;
        }
    }
}