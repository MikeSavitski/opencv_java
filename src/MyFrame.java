import org.opencv.core.Core;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyFrame extends JFrame {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    private JPanel contentPane;
    private VideoCap videoCap = new VideoCap();

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            try {
                MyFrame frame = new MyFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    public MyFrame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1280, 720);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        new MyThread().start();

    }

    public void paint(Graphics g) {
        g = contentPane.getGraphics();
        BufferedImage image = videoCap.getOneFrame();
        g.drawImage(image, 0, 0, this);
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            for(;;) {
                repaint();
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
