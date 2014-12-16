import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Scene extends JComponent implements ActionListener{
    private Timer timer;
    static int X = 25 * 5;
    static int deltaX;
    static int delay = 20;
    int numb = 0;
    int count_fon = 0;//счетчик formsOnField
    boolean win = false;//превращается в true в случае выйгрыша раунда
    boolean lose = false;//превращается в true в случае проигрыша
    static boolean leftPressed = false;//нажата ли левая клавиша для смещения фигуры влево
    static boolean rightPressed = false;//нажата ли правая клавиша для смещения фигуры вправл
    static boolean undo = false;//для возвращения к предыдущей фигуре
    boolean[][] field = new boolean[12][26];//булево поле для игры
    int[][] formsOnField_X = new int[50][4];//все квадраты на поле вмести с координатами по Х
    int[][] formsOnField_Y = new int[50][4];//все квадраты на поле вмести с координатами по У
    Form[] forms;//массив всех фигур на уровне
    ImageForm[] imgForm = new ImageForm[50];
    Image[][] images;
    Image[][] gameField = new Image[field.length][field[0].length];
    CreateForms createBlock = new CreateForms();
    Action action = new Action();

    public void start() {
        trueField();
        timer = new Timer(delay, this);

        Forms form = new Forms();
        form.start();
        form.update();
        forms = form.allForms();

        TileImage img = new TileImage();
        img.init();
        images = img.images();

        formToImg();
        timer.start();
    }
    void formToImg(){
        for (int i = 0; i < forms.length; i++){
            createBlock.whatAForm(forms[i].type, forms[i].x, forms[i].y);
            Image[] tempImg = new Image[4];
            Block[] tempBlock = new Block[4];
            for (int j = 0; j < createBlock.block.length; j++){
                tempImg[j] = images[createBlock.block[j].x - 2][createBlock.block[j].y - 3];
                if (j != 0) tempBlock[j] = new Block(createBlock.block[j].x - createBlock.block[0].x, createBlock.block[j].y - createBlock.block[0].y);
                else tempBlock[j] = new Block(0, 0);
            }
            imgForm[i] = new ImageForm(tempImg, tempBlock);
        }
        deltaX = imgForm[0].img[0].getWidth(null);
    }
    private void trueField(){
        for (int x = 0; x < field.length; x++){
            for (int y = 0; y < field[x].length; y++){
                if (x != 0 && x != field.length -1) y = field[x].length -1;
                field[x][y] = true;
            }
        }
    }

    public void actionPerformed(ActionEvent arg0) {
        if (action.newFig && numb < imgForm.length - 1) numb++;
        action.update();
        if (leftPressed && action.canLeft(field, imgForm[numb])) {
            X -= deltaX;
            leftPressed = false;
        }
        if (rightPressed && action.canRight(field, imgForm[numb])) {
            X += deltaX;
            rightPressed = false;
        }
        if (action.canNext(field, imgForm[numb])) {
            for (int i = 0; i < 4; i++){
                if ((action.getY() + deltaX * imgForm[numb].block[i].y) / deltaX >= 0) {
                    field[(X / deltaX) + imgForm[numb].block[i].x][(action.getY() + deltaX * imgForm[numb].block[i].y) / deltaX] = true;
                    gameField[(X / deltaX) + imgForm[numb].block[i].x][(action.getY() + deltaX * imgForm[numb].block[i].y) / deltaX] = imgForm[numb].img[i];
                    formsOnField_X[count_fon][i] = (X / deltaX) + imgForm[numb].block[i].x;
                    formsOnField_Y[count_fon][i] = (action.getY() + deltaX * imgForm[numb].block[i].y) / deltaX;
                }
            }
            Action.newFig = true;
            count_fon++;
        }
        if (numb != 0 && count_fon != 0 && undo){
            for (int i = 0; i < 4; i++){
                field[formsOnField_X[count_fon - 1][i]][formsOnField_Y[count_fon - 1][i]] = false;
                gameField[formsOnField_X[count_fon - 1][i]][formsOnField_Y[count_fon - 1][i]] = null;
            }
            action.undo();
            numb--;
            count_fon--;
            undo = false;
        }
        if (undo && numb == 0) undo = false;
        if (numb == 49){
            win = true;
            for (int i = 1; i < gameField.length - 1; i++){
                if (gameField[i][gameField[i].length - 20] == null) {
                    win = false;
                    break;
                }
            }
            if (win) {
                for (int i = 0; i < forms.length; i++) {
                    if (forms[i].x - 1 != formsOnField_X[i][0] || forms[i].y + 2 != formsOnField_Y[i][0]) {
                        win = false;
                        lose = true;
                        break;
                    }
                }
            }
        }
        for (int i = 1; i < gameField.length - 1; i++){
            if (gameField[i][0] != null) {
                lose = true;
                break;
            }
        }
        if (win) System.out.println("WIN");
        if (lose) System.out.println("LOSE");
        repaint();
    }

    protected void paintComponent (Graphics g){
        Graphics2D gr = (Graphics2D) g;
        for (int x = 0; x < gameField.length; x++){
            for (int y = 0; y < gameField[x].length; y++){
                if(gameField[x][y] != null) gr.drawImage(gameField[x][y], x * deltaX, y * deltaX, null);
            }
        }
        if (win) {
            g.drawString("You Win!", 135, 650);
            timer.stop();
            return;
        }
        if (lose){
            g.drawString("You Lose!", 135, 650);
            timer.stop();
            return;
        }
        imgForm[numb].print(g, X, action.getY());
        if (numb != 49) {
            for (int i = 0; i < 4; i++) {
                gr.drawImage(imgForm[numb + 1].img[i], 350 + (imgForm[numb + 1].block[i].x * deltaX), 120 + (imgForm[numb + 1].block[i].y * deltaX), null);
            }
        }
    }
}