/**
 * Created by Никита on 04.11.2014.
 */
public class Action {
    static int speed = 1;//начальная скорость падения фигур
    int posY = 15;//начальная позиция по оси У
    public static boolean newFig = false;//превращается в true если можно выставить новую фигуру

    public void update() {//вызывается каждый такт и меняет положение фигуры
        if (newFig) {
            posY = speed;
            newFig = false;
            Scene.X = 25 * 5;
        }
        posY += speed;

    }
    public void undo(){//вызывается при отмене предыдущего действия(падает снова предыдущая фигура)
        posY = 15;
        Scene.X = 25 * 5;
    }
    public int getY() {
        return posY;
    }//возвращает положение фигуры по ОУ
    public boolean canNext (boolean[][] field, ImageForm img){//проверяется, приняла ли конечное положение падающая фигура
        for (int i = 0; i < img.block.length; i++){
            int deltaX = Scene.X / Scene.deltaX;
            int deltaY = posY / Scene.deltaX;
            if (deltaX + img.block[i].x >= 0 && deltaY + img.block[i].y + 1 >= 0) {
                if (field[deltaX + img.block[i].x][deltaY + img.block[i].y + 1]) return true;
            }
        }
        return false;
    }
    public boolean canLeft (boolean[][] field, ImageForm img) {//можно ли сдвинуть фигуру влево
        int deltaX = Scene.X / Scene.deltaX;
        int deltaY = posY / Scene.deltaX;
        for (int i = 0; i < 4; i++){
            if (deltaX + img.block[i].x - 1 >= 0 && deltaY + img.block[i].y >= 0) {
                if (field[deltaX + img.block[i].x - 1][deltaY + img.block[i].y]) return false;
            }
        }
        return true;
    }
    public boolean canRight (boolean[][] field, ImageForm img) {//можно ли сдвинуть фигуру вправо
        int deltaX = Scene.X / Scene.deltaX;
        int deltaY = posY / Scene.deltaX;
        for (int i = 0; i < 4; i++){
            if (deltaX + img.block[i].x + 1 >= 0 && deltaY + img.block[i].y >= 0) {
                if (field[deltaX + img.block[i].x + 1][deltaY + img.block[i].y]) return false;
            }
        }
        return true;
    }
}