import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class TileImage extends JComponent {
    Image imgTemp;
    Image img;
    Image cell[][] = new Image[10][20];
    int tw, th;
    int x, y, w, h;

    public void init() {
        try {
            imgTemp = getToolkit().getImage(getClass().getResource("Images/img.jpg"));
            MediaTracker t = new MediaTracker(this);
            t.addImage(imgTemp, 0);
            t.waitForID(0);

            if (imgTemp.getWidth(null) * 2 >= imgTemp.getHeight(null)) {
                x = (imgTemp.getWidth(null) / 2) - (imgTemp.getHeight(null) / 4);
                y = 0;
                w = imgTemp.getHeight(null) / 2;
                h = imgTemp.getHeight(null);
            } else {
                x = 0;
                y = (imgTemp.getHeight(null) / 2) - imgTemp.getWidth(null);
                w = imgTemp.getWidth(null);
                h = imgTemp.getWidth(null) * 2;
            }
            img = createImage(new FilteredImageSource(imgTemp.getSource(), new CropImageFilter(x, y, w, h)));
            tw = w / 10;
            th = h / 20;
            CropImageFilter f;
            FilteredImageSource fis;
            t = new MediaTracker(this);

            for (int y = 0; y < 20; y++) {
                for (int x = 0; x < 10; x++) {
                    f = new CropImageFilter(tw * x, th * y, tw, th);
                    fis = new FilteredImageSource(img.getSource(), f);
                    cell[x][y] = createImage(fis);
                    t.addImage(cell[x][y], y * 10 + x);
                }
            }

            t.waitForAll();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }
    public Image[][] images (){
        return cell;
    }
}
