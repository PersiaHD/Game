import java.awt.*;

/**
 * Created by Никита on 20.11.2014.
 */
public class ImageForm {//фигура с изображеием
    Image[] img;
    Block[] block;
    public ImageForm(Image[] img, Block[] block){
        this.img = img;
        this.block = block;
    }
    public void print(Graphics g, int x, int y){
        g.drawImage(img[0], block[0].x * img[0].getWidth(null) + x, block[0].y * img[0].getHeight(null) + y, null);
        g.drawImage(img[1], block[1].x * img[1].getWidth(null) + x, block[1].y * img[1].getHeight(null) + y, null);
        g.drawImage(img[2], block[2].x * img[2].getWidth(null) + x, block[2].y * img[2].getHeight(null) + y, null);
        g.drawImage(img[3], block[3].x * img[3].getWidth(null) + x, block[3].y * img[3].getHeight(null) + y, null);
    }
}
