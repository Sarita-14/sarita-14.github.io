import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.net.URLEncoder;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class QRCodeGUI {

    static BufferedImage qrImage = null;

    public static void main(String[] args) {

        JFrame frame = new JFrame("QR Code Generator");
        frame.setSize(400, 500);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton generateBtn = new JButton("Generate QR");
        JButton downloadBtn = new JButton("Download QR");

        JLabel qrLabel = new JLabel();

        frame.add(generateBtn);
        frame.add(downloadBtn);
        frame.add(qrLabel);

        // ✅ Generate QR (ONLY WEBSITE LINK)
        generateBtn.addActionListener(e -> {
            try {
                String data = "https://sarita-14.github.io/";

                String encoded = URLEncoder.encode(data, "UTF-8");

                String url = "https://api.qrserver.com/v1/create-qr-code/?size=250x250&data=" + encoded;

                qrImage = ImageIO.read(new URL(url));

                qrLabel.setIcon(new ImageIcon(qrImage));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // ✅ Download QR
        downloadBtn.addActionListener(e -> {
            try {
                if (qrImage == null) {
                    JOptionPane.showMessageDialog(frame, "Generate QR first!");
                    return;
                }

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save QR Code");

                int userSelection = fileChooser.showSaveDialog(frame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();

                    if (!fileToSave.getAbsolutePath().endsWith(".png")) {
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
                    }

                    ImageIO.write(qrImage, "png", fileToSave);

                    JOptionPane.showMessageDialog(frame, "QR saved successfully!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        frame.setVisible(true);
    }
}