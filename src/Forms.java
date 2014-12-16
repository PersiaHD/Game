import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Никита on 12.11.2014.
 */
public class Forms extends JComponent {
    CreateForms form = new CreateForms();
    int formsOnFieldNumb = 0;//количество фигур на поле
    int lastType = 18;//переменная для исключения последующего дублирования фигур
    int sumFields = 0;//количество фигур на поле
    int sequenceForms[] = new int[50];//окончательная последовательность фигур
    int[] rand10 = new int[10];//массив рандомно расположеннных чисел (0;9)
    int[] formType = new int[19];//рандомный ряд, содержащий значение типа фигуры
    int[][] fieldOfNumb = new int[10][20];//поле, содержащее номер фигур в виде фигур
    boolean error = false;//превращается в true, если поле не смогло разбиться идеально на фигуры

    int a = 0, b = 0, c = 0, d = 0, e = 0;//количество фигур разных типов

   void start (){ //превращаем поле 10х20 в false
       for (int i = 0; i < form.field.length; i++){
            for (int j = 0; j < form.field[i].length; j++){
                form.field[i][j] = false;
            }
        }
        for (int i = 0; i < form.field.length; i++){
            for (int j = 0; j < form.field[i].length; j++) {
                form.field[i][j] = true;
                if (j == 2) j = 22;
            }
        }
        for (int i = 0; i < form.field.length; i++){
            for (int j = 0; j < form.field[i].length; j++){
                form.field[i][j] = true;
                if (i == 1 && j ==23) i = 11;
            }
        }
       for (int i = 0; i < sequenceForms.length; i++){
           sequenceForms[i] = 50;
       }
    }

    private void sequence (){//метод, позволяющий держать в равновесие количество фигур разного типа
        if (sumFields <= 0){
            random(0);
            return;
        }
        for (int i = 0; i < formType.length; i++){
            formType[i] = i;
        }
        if (d < (a + b + c) / 9){
            random(1);
            return;
        }
        if (e < (a + b + c) / 9){
            formType[5] = 0;
            formType[6] = 1;
            formType[0] = new Random().nextInt(1) + 5;
            formType[1] = 11 - formType[0];
            random(2);
            return;
        }
        if (a - 3 < b && a - 3 < c){
            formType[0] = 3;
            formType[1] = 4;
            formType[3] = 1;
            formType[4] = 0;
            random(4);
            return;
        }
        if (b - 3 < a && b - 3 < c){
            for (int i = 8; i <= 14; i++){formType[i] = i - 7;}
            for (int i = 0; i <= 7; i++){formType[i] = i + 7;}
            for (int i = 0; i < 8 * 2; i++){
                int om = new Random().nextInt(8);
                int nm = new Random().nextInt(8);
                int temp = formType[om];
                formType[om] = formType[nm];
                formType[nm] = temp;
            }
            formType[13] = 18;
            formType[18] = 6;
            random(8);
            return;
        }
        if (c - 3 < a && c - 3 < b){
            formType[0] = 15;
            formType[1] = 17;
            formType[2] = 18;
            formType[3] = 16;
            for (int i = 15; i <= 18; i++){formType[i] = i - 15;}
            random(4);
            return;
        }
        random(0);
    }

    private void random(int x){//перемешивает массив значений типов фигур
        if (formType[6] == 6) {
            int temp = formType[18];
            formType[18] = 6;
            formType[6] = temp;
        }
        for (int i = x; i < (17 - x) * 2; i++){
            int om = new Random().nextInt(17 - x) + x;
            int nm = new Random().nextInt(17 - x) + x;
            int temp = formType[om];
            formType[om] = formType[nm];
            formType[nm] = temp;
        }
    }

    private void sum (int type) { //подсчитывает фигуры во время разбиения
        if (type >= 1 && type <= 4) a++;
        if (type >= 7 && type <= 14) b++;
        if (type >= 15 && type <= 18) c++;
        if (type == 0) d++;
        if (type == 5 || type == 6) e++;
        sumFields = a + b + c + d + e;
    }

    private void lastForms(){ //заполнение последних 3 строк фигурами
        boolean[][] tempField = new boolean[15][6];
        int tempCounter = 0;
        for (int i = 0; i < tempField.length; i++){
            for (int j = 0; j < tempField[i].length; j++){
                tempField[i][j] = true;
            }
        }
        for (int i = 2; i < 12; i++){
            for (int j = 3; j < 6; j++){
                if (!form.field[i][j]) {
                    tempField[i][j] = false;
                    tempCounter++;
                }
                if (sumFields == 50) return;
                if (sumFields == 49 && tempCounter == 4){
                    sequence();
                    Form tempForm = form.theLastEmpty(tempField, formType);
                    if (tempForm != null) {
                        form.whatAForm(tempForm.type, tempForm.x, tempForm.y);
                        form.fieldTrue();
                        form.formsOnField[formsOnFieldNumb] = new Form(tempForm.x, tempForm.y, tempForm.type);
                        addToFieldNumb();
                        formsOnFieldNumb++;
                        sum(tempForm.type);
                    } else {
                        System.out.println("the last is null");
                        error = true;
                        return;
                    }
                    return;
                }
                if (tempCounter == 8){
                    sequence();
                    int[] rand_1 = formType;
                    sequence();
                    int[] rand_2 = formType;
                    Form[] tempForm = form.lastEmpty(tempField, rand_1, rand_2);
                    if (tempForm[0] != null && tempForm[1] != null){
                        form.whatAForm(tempForm[0].type, tempForm[0].x, tempForm[0].y);
                        form.fieldTrue();
                        for (int m = 0; m < form.block.length; m++){
                            tempField[form.block[m].x][form.block[m].y] = true;
                        }
                        form.formsOnField[formsOnFieldNumb] = new Form(tempForm[0].x, tempForm[0].y, tempForm[0].type);
                        addToFieldNumb();
                        formsOnFieldNumb++;
                        sum(tempForm[0].type);

                        form.whatAForm(tempForm[1].type, tempForm[1].x, tempForm[1].y);
                        form.fieldTrue();
                        for (int m = 0; m < form.block.length; m++){
                            tempField[form.block[m].x][form.block[m].y] = true;
                        }
                        form.formsOnField[formsOnFieldNumb] = new Form(tempForm[1].x, tempForm[1].y, tempForm[1].type);
                        addToFieldNumb();
                        formsOnFieldNumb++;
                        sum(tempForm[1].type);
                        tempCounter = 0;
                    } else {
                        System.out.println("one of two is null");
                        error = true;
                        return;
                    }
                }
            }
        }
    }

    private void newStart(){ //если не смог разбить поле идеально
        formsOnFieldNumb = 0;
        lastType = 18;
        sumFields = 0;
        a = b = c = d = e = 0;
        error = false;
        start();
        update();

    }

    private void addToFieldNumb () {//добавление в поле номеров фигур
        for (int k = 0; k < 4; k++) {
            fieldOfNumb[form.block[k].x - 2][form.block[k].y - 3] = formsOnFieldNumb;
        }
    }

    private void sequenceForms(){//создает правильную последовательность фигур
        boolean useForm = false, useFormLeft = false, useFormRight = false;
        boolean searchLeft = false, searchRight = false;
        boolean add = false;
        int form_numb = 0;
        int count = 0;
        int y = 0, x = 0;
        int randCount = 0;
        sequenceRandom();
        while (true){
            if (sequenceForms[49] != 50) return;
            for (int i = fieldOfNumb[0].length - 1; i >= 0; i--){
                useForm = false;
                for (int j = 0; j < sequenceForms.length; j++){
                    if (fieldOfNumb[rand10[randCount]][i] == sequenceForms[j]) useForm = true;
                    if (sequenceForms[j] == 50 && !useForm) {
                        x = rand10[randCount];
                        y = i;
                        form_numb = fieldOfNumb[x][y];
                        break;
                    }
                }
                if (!useForm) break;
            }
            if (useForm) {
                randCount++;
                continue;
            }

            if (x != 0){
                for (int i = 1; i < 3; i++){
                    if (fieldOfNumb[x - 1][y] == form_numb) {
                        for (int j = 1; j < 4; j++) {
                            boolean stop = false;
                            for (int k = 0; k < sequenceForms.length; k++) {
                                if (y + j < 20) {
                                    if (fieldOfNumb[x - 1][y + j] != form_numb) {
                                        if (fieldOfNumb[x - 1][y + j] == sequenceForms[k]) {
                                            stop = true;
                                            useFormLeft = false;
                                            break;
                                        } else useFormLeft = true;
                                        if (k == sequenceForms.length - 1 || sequenceForms[k] == 50) {
                                            stop = true;
                                            break;
                                        }
                                    } else break;
                                }
                            }
                            if (stop) break;
                        }
                        break;
                    }
                    if (y - i >= 0 && fieldOfNumb[x - 1][y] != form_numb) {
                        if (fieldOfNumb[x - 1][y - i] == form_numb) {
                            searchLeft = true;
                        }
                    }
                }
                if (x > 1) {
                    if (fieldOfNumb[x - 2][y] == form_numb) {
                        for (int i = 1; i < 3; i++) {
                            boolean stop = false;
                            for (int j = 0; j < sequenceForms.length; j++) {
                                if (y + i < 20){
                                    if (fieldOfNumb[x - 2][y + i] == form_numb) break;
                                    if (fieldOfNumb[x - 2][y + i] == sequenceForms[j]){
                                        stop = true;
                                        useFormLeft = false;
                                        break;
                                    } else useFormLeft = true;
                                    if (sequenceForms[j] == 50 || j == 49) {
                                        stop = true;
                                        break;
                                    }
                                }
                            }
                            if (stop) break;
                        }
                    }
                    if (x != 2 && y < 19 && fieldOfNumb[x - 3][y] == form_numb){
                        for (int i = 0; i < sequenceForms.length; i++){
                            if (fieldOfNumb[x - 3][y + 1] == sequenceForms[i]){
                                useFormLeft = false;
                                break;
                            } else useFormLeft = true;
                        }
                    }
                    if (y > 0) {
                        if (fieldOfNumb[x - 2][y - 1] == form_numb && fieldOfNumb[x - 2][y] != form_numb) {
                            searchLeft = true;
                            x--;
                        }
                    }
                }
            }
            if (x != 9) {
                for (int i = 1; i < 3; i++){
                    if (fieldOfNumb[x + 1][y] == form_numb) {
                        for (int j = 1; j < 4; j++) {
                            boolean stop = false;
                            for (int k = 0; k < sequenceForms.length; k++) {
                                if (y + j < 20) {
                                    if (fieldOfNumb[x + 1][y + j] != form_numb) {
                                        if (fieldOfNumb[x + 1][y + j] == sequenceForms[k]) {
                                            stop = true;
                                            useFormRight = false;
                                            break;
                                        } else useFormRight = true;
                                        if (k == sequenceForms.length - 1 || sequenceForms[k] == 50) stop = true;
                                    }
                                }
                            }
                            if (stop) break;
                        }
                        break;
                    }
                    if (y - i >= 0 && fieldOfNumb[x + 1][y] != form_numb) {
                        if (fieldOfNumb[x + 1][y - i] == form_numb) {
                            searchRight = true;
                        }
                    }
                }
                if (x < 8) {
                    if (fieldOfNumb[x + 2][y] == form_numb) {
                        for (int i = 1; i < 3; i++) {
                            boolean stop = false;
                            for (int j = 0; j < sequenceForms.length; j++) {
                                if (y + i < 20){
                                    if (fieldOfNumb[x + 2][y + i] == form_numb) break;
                                    if (fieldOfNumb[x + 2][y + i] == sequenceForms[j]){
                                        stop = true;
                                        useFormRight = false;
                                        break;
                                    } else useFormRight = true;
                                    if (sequenceForms[j] == 50 || j == 49){
                                        stop = true;
                                        break;
                                    }
                                }
                            }
                            if (stop) break;
                        }
                    }
                    if (x != 7 && y < 19 && fieldOfNumb[x + 3][y] == form_numb){
                        for (int i = 0; i < sequenceForms.length; i++){
                            if (fieldOfNumb[x + 3][y + 1] == sequenceForms[i]){
                                useFormRight = false;
                                break;
                            } else useFormRight = true;
                        }
                    }
                    if (y > 0) {
                        if (fieldOfNumb[x + 2][y - 1] == form_numb && fieldOfNumb[x + 2][y] != form_numb) {
                            searchRight = true;
                            x++;
                        }
                    }
                }
            }
            if (!searchLeft && !searchRight && !useFormLeft && !useFormRight){
                add = true;
            } else {
                if (searchLeft) {
                    int tempFig = fieldOfNumb[x - 1][y];
                    for (int i = 0; i < sequenceForms.length; i++) {
                        if (tempFig == sequenceForms[i]) {
                            if (fieldOfNumb[x - 1][y - 1] == form_numb || fieldOfNumb[x - 1][y - 1] == tempFig) {
                                add = true;
                                break;
                            }
                        } else {
                            useFormLeft = true;
                            add = false;
                        }
                        if (sequenceForms[i] == 50) break;
                    }
                }
                if (searchRight) {
                    int tempFig = fieldOfNumb[x + 1][y];
                    for (int i = 0; i < sequenceForms.length; i++) {
                        if (tempFig == sequenceForms[i]) {
                            if (fieldOfNumb[x + 1][y - 1] == form_numb || fieldOfNumb[x + 1][y - 1] == tempFig) {
                                add = true;
                                break;
                            }
                        } else {
                            useFormRight = true;
                            add = false;
                        }
                        if (sequenceForms[i] == 50) break;
                    }
                }
            }
            if (useFormRight || useFormLeft) {
                randCount++;
                add = searchLeft = searchRight = false;
                useFormLeft = useFormRight = false;
                continue;
            }
            if (add){
                sequenceForms[count] = form_numb;
                count++;
                x = y = 0;
                searchLeft = searchRight = false;
                useFormLeft = useFormRight = false;
                add = false;
                randCount++;
                sequenceRandom();
                randCount = 0;
                continue;
            }
        }
    }
    private void sequenceRandom (){//рандом для правильной последовательности фигур
        for (int i = 0; i < rand10.length; i++){
            rand10[i] = i;
        }
        for (int i = 0; i < 2 * rand10.length; i++){
            int rand1 = new Random().nextInt(10);
            int rand2 = new Random().nextInt(10);
            int temp = rand10[rand1];
            rand10[rand1] = rand10[rand2];
            rand10[rand2] = temp;
        }
    }

    public void update() { //главный метод разбития поля на фигуры
        for (int j = 22; j > 5; j--) {
            for (int i = 2; i < 12; i++) {
                if (!form.field[i][j]) {
                    int l = -1;
                    int type;
                    sequence();
                    do {
                        l++;
                        if (l == 19) l = 0;
                        type = formType[l];
                        form.whatAForm(type, i, j);
                    } while (!form.isEmpty(type) || (lastType == type && type != 6));
                    lastType = type;
                    sum(type);
                    addToFieldNumb();
                    form.fieldTrue();
                    form.formsOnField[formsOnFieldNumb] = new Form(i, j, type);
                    formsOnFieldNumb++;
                }
            }
        }
        lastForms();
        if (error) newStart();
        else {
            System.out.println("T " + a + "\nГ " + b + "\nZ " + c + "\nКвадрат " + d + "\nПалка " + e + "\nSum " + sumFields);
            paintComponent(getGraphics());
            sequenceForms();
            endSequence();
        }

    }
    private void endSequence(){//массив фигур в порядки их появления
        Form[] temp = new Form[50];
        for (int i = 0; i < temp.length; i++){
            temp[i] = form.formsOnField[i];
        }
        for (int i = 0; i < temp.length; i++){
            form.formsOnField[i] = temp[sequenceForms[i]];
        }
    }
    public Form[] allForms(){
        return form.formsOnField;
    }
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.green);
        for (int i = 0; i < formsOnFieldNumb; i++){
            int a = new Random().nextInt(255);
            int b = new Random().nextInt(255);
            int c = new Random().nextInt(255);
            Color color = new Color(a, b, c);
            g.setXORMode(color);
            int k = form.formsOnField[i].type;
            form.paint(g, k, form.formsOnField[i].x, form.formsOnField[i].y);
        }
    }
}
