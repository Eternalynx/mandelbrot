
import java.awt.image.BufferedImage;

public class CallableSet implements Runnable {

    private BufferedImage I;
    private int no;
    private int chunks;

    public CallableSet(BufferedImage I, int no, int chunks) {
        this.I = I;
        this.no = no;
        this.chunks = chunks;
    }
    public void run() {

        double zx, zy, cX, cY, tmp;
        int MAX_ITER = 5700;
        double ZOOM = 150;

        for (int y = (no)*I.getHeight()/chunks; y < (no+1)*I.getHeight()/chunks; y++) {
            for (int x = 0; x < I.getWidth(); x++) {
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                I.setRGB(x, y, iter | (iter << 8));
            }
        }


    }
}