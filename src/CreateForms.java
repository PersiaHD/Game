import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Никита on 12.11.2014.
 * обработка фигур тетриса
 */
public class CreateForms {
    public boolean[][] field = new boolean[15][24];//создает поле для игры с учетом вылета фигур за его контуры
    public Form[] formsOnField = new Form[50];//массив, содержащий фигуры
    public Block[] block = new Block[4];//массив одной фигуры, состоящей из клеток

    public void whatAForm (int type, int x, int y) { //метод определения фигуры
        switch (type){
            case 0:
                block[0] = new Block(x, y);
                block[1] = new Block(x + 1, y);
                block[2] = new Block(x, y - 1);
                block[3] = new Block(x + 1, y - 1);
                return;
            case 1:
                block[0] = new Block(x, y);
                block[1] = new Block(x + 1, y);
                block[2] = new Block(x + 2, y);
                block[3] = new Block(x + 1, y - 1);
                return;
            case 2:
                block[0] = new Block(x, y);
                block[1] = new Block(x - 1, y - 1);
                block[2] = new Block(x, y - 1);
                block[3] = new Block(x, y - 2);
                return;
            case 3:
                block[0] = new Block(x, y);
                block[1] = new Block(x - 1, y - 1);
                block[2] = new Block(x, y - 1);
                block[3] = new Block(x + 1, y - 1);
                return;
            case 4:
                block[0] = new Block(x, y);
                block[1] = new Block(x, y - 1);
                block[2] = new Block(x + 1, y - 1);
                block[3] = new Block(x, y - 2);
                return;
            case 5:
                block[0] = new Block(x, y);
                block[1] = new Block(x + 1, y);
                block[2] = new Block(x + 2, y);
                block[3] = new Block(x + 3, y);
                return;
            case 6:
                block[0] = new Block(x, y);
                block[1] = new Block(x, y - 1);
                block[2] = new Block(x, y - 2);
                block[3] = new Block(x, y - 3);
                return;
            case 7:
                block[0] = new Block(x, y);
                block[1] = new Block(x + 1, y);
                block[2] = new Block(x + 2, y);
                block[3] = new Block(x, y - 1);
                return;
            case 8:
                block[0] = new Block(x, y);
                block[1] = new Block(x, y - 1);
                block[2] = new Block(x, y - 2);
                block[3] = new Block(x + 1, y - 2);
                return;
            case 9:
                block[0] = new Block(x, y);
                block[1] = new Block(x - 2, y - 1);
                block[2] = new Block(x - 1, y - 1);
                block[3] = new Block(x, y - 1);
                return;
            case 10:
                block[0] = new Block(x, y);
                block[1] = new Block(x + 1, y);
                block[2] = new Block(x + 1, y - 1);
                block[3] = new Block(x + 1, y - 2);
                return;
            case 11:
                block[0] = new Block(x, y);
                block[1] = new Block(x + 1, y);
                block[2] = new Block(x + 2, y);
                block[3] = new Block(x + 2, y - 1);
                return;
            case 12:
                block[0] = new Block(x, y);
                block[1] = new Block(x, y - 1);
                block[2] = new Block(x - 1, y - 2);
                block[3] = new Block(x, y - 2);
                return;
            case 13:
                block[0] = new Block(x, y);
                block[1] = new Block(x, y - 1);
                block[2] = new Block(x + 1, y - 1);
                block[3] = new Block(x + 2, y - 1);
                return;
            case 14:
                block[0] = new Block(x, y);
                block[1] = new Block(x + 1, y);
                block[2] = new Block(x, y - 1);
                block[3] = new Block(x, y - 2);
                return;
            case 15:
                block[0] = new Block(x, y);
                block[1] = new Block(x + 1, y);
                block[2] = new Block(x + 1, y - 1);
                block[3] = new Block(x + 2, y - 1);
                return;
            case 16:
                block[0] = new Block(x, y);
                block[1] = new Block(x - 1, y - 1);
                block[2] = new Block(x, y - 1);
                block[3] = new Block(x - 1, y - 2);
                return;
            case 17:
                block[0] = new Block(x, y);
                block[1] = new Block(x + 1, y);
                block[2] = new Block(x - 1, y - 1);
                block[3] = new Block(x, y - 1);
                return;
            case 18:
                block[0] = new Block(x, y);
                block[1] = new Block(x, y - 1);
                block[2] = new Block(x + 1, y - 1);
                block[3] = new Block(x + 1, y - 2);
                return;
            default:
                return;
        }
    }
    public boolean isEmpty(int type) { //может ли фиигура занять запрашиваемые клетки поля
        for (int i = 0; i < block.length; i++){
            if (field[block[i].x][block[i].y]) return false;
            //if (!field[block[i].x][block[i].y + 1] || !field[block[i].x][block[i].y + 2]) return false;
        }
        switch (type) { //исключение одиночных пустых клеток
            case 2:
                if (!field[block[0].x - 1][block[0].y]) return false;
                break;
            case 3:
                if (!field[block[0].x - 1][block[0].y] || !field[block[0].x + 1][block[0].y]) return false;
                break;
            case 4:
                if (!field[block[0].x + 1][block[0].y]) return false;
                break;
            case 8:
                if (!field[block[0].x + 1][block[0].y] || !field[block[0].x + 1][block[0].y - 1]) return false;
                break;
            case 9:
                if (!field[block[0].x - 1][block[0].y] || !field[block[0].x - 2][block[0].y]) return false;
                break;
            case 12:
                if (!field[block[0].x - 1][block[0].y] || !field[block[0].x - 1][block[0].y - 1]) return false;
                break;
            case 13:
                if (!field[block[0].x + 1][block[0].y] || !field[block[0].x + 2][block[0].y]) return false;
                break;
            case 15:
                if (!field[block[0].x + 1][block[0].y]) return false;
                break;
            case 16:
                if (!field[block[0].x - 1][block[0].y]) return false;
                break;
            case 17:
                if (!field[block[0].x - 1][block[0].y]) return false;
                break;
            case 18:
                if (!field[block[0].x + 1][block[0].y]) return false;
                break;
            default:
                break;
        }
        return true;
    }
    public void fieldTrue (){ //выставить фигуру на поле
        for (int i = 0; i < block.length; i++){
            field[block[i].x][block[i].y] = true;
        }
    }

    public Form[] lastEmpty (boolean[][] newField, int[] type_1, int[] type_2){ //работа с новым полем
        Form[] tempForm = new Form[2];
        Block tempBlock = findEmpty(newField);
        for (int i = 0; i < type_1.length; i++){
            whatAForm(type_1[i], tempBlock.x, tempBlock.y);
            if (!newField[block[0].x][block[0].y] && !newField[block[1].x][block[1].y] &&
                !newField[block[2].x][block[2].y] && !newField[block[3].x][block[3].y]){
                tempForm[0] = new Form(tempBlock.x, tempBlock.y, type_1[i]);
                for (int m = 0; m < block.length; m++){
                    newField[block[m].x][block[m].y] = true;
                }
                tempBlock = findEmpty(newField);
                for (int j = 0; j < type_2.length; j++){
                    whatAForm(type_2[j], tempBlock.x, tempBlock.y);
                    if (!newField[block[0].x][block[0].y] && !newField[block[1].x][block[1].y] &&
                        !newField[block[2].x][block[2].y] && !newField[block[3].x][block[3].y]){
                        tempForm[1] = new Form(tempBlock.x, tempBlock.y, type_2[j]);
                        return tempForm;
                    }
                }
                whatAForm(tempForm[0].type, tempForm[0].x, tempForm[0].y);
                for (int m = 0; m < block.length; m++){
                    newField[block[m].x][block[m].y] = false;
                }
                tempBlock = findEmpty(newField);
            }
        }
        tempForm[0] = null;
        tempForm[1] = null;
        return tempForm;
    }

    public Form theLastEmpty (boolean[][] newField, int[] type){ //если осталось место только на 1 фигуру
        Block tempBlock = findEmpty(newField);
        for (int i = 0; i < type.length; i++){
            whatAForm(type[i], tempBlock.x, tempBlock.y);
            if (!newField[block[0].x][block[0].y] && !newField[block[1].x][block[1].y] &&
                !newField[block[2].x][block[2].y] && !newField[block[3].x][block[3].y]){
                return new Form(tempBlock.x, tempBlock.y, type[i]);
            }
        }
        return null;
    }

    private Block findEmpty (boolean[][] field){ //ищет пустую клетку на поле
        Block temp = new Block(0, 0);
        for (int j = 5; j > 2; j--){
            for (int i = 1; i < 11; i++){
                if (!field[i][j]){
                    temp = new Block(i, j);
                    return temp;
                }
            }
        }
        return temp;
    }

    public void paint (Graphics g, int type, int x, int y){
        whatAForm(type, x, y);
        for (int i = 0; i < block.length; i++){
            g.fillRect(block[i].x * 10, block[i].y * 10, 9, 9);
        }
    }
}