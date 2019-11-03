import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

import static java.lang.Thread.sleep;

public class Mandelbrot extends JFrame {


    private BufferedImage I;

    public Mandelbrot(int lw) {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService pool = Executors.newFixedThreadPool(8);


        for(int i = 0; i < lw;i++)
        pool.execute(new CallableSet(I,i,lw));

        pool.shutdown();
        try {
            if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {

        int liczba_watkow = 8;

        new Mandelbrot(liczba_watkow).setVisible(true);
    }
}