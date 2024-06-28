import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PetSelector extends JFrame {
    private final JLabel imageLabel;
    private final JLabel label;

    public PetSelector() {
        // Set up the frame
        setTitle("Pet Selector");
        setSize(800, 600); // Adjust the size of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the radio buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        // Create the radio buttons
        JRadioButton dogButton = new JRadioButton("Dog");
        JRadioButton catButton = new JRadioButton("Cat");
        JRadioButton birdButton = new JRadioButton("Bird");
        JRadioButton rabbitButton = new JRadioButton("Rabbit");
        JRadioButton pigButton = new JRadioButton("Pig");

        // Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(dogButton);
        group.add(catButton);
        group.add(birdButton);
        group.add(rabbitButton);
        group.add(pigButton);

        // Add action listeners to the radio buttons
        dogButton.addActionListener(new PetActionListener("Dog", "dog.png"));
        catButton.addActionListener(new PetActionListener("Cat", "cat.png"));
        birdButton.addActionListener(new PetActionListener("Bird", "bird.png"));
        rabbitButton.addActionListener(new PetActionListener("Rabbit", "rabbit.png"));
        pigButton.addActionListener(new PetActionListener("Pig", "pig.png"));

        // Add the radio buttons to the panel
        panel.add(dogButton);
        panel.add(catButton);
        panel.add(birdButton);
        panel.add(rabbitButton);
        panel.add(pigButton);

        // Create a label to display the selected pet
        label = new JLabel("Select a pet", JLabel.CENTER);

        // Create a label to display the image
        imageLabel = new JLabel("", JLabel.CENTER);

        // Add the panel, image label, and text label to the frame
        add(panel, BorderLayout.WEST);
        add(imageLabel, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
    }

    // Action listener for the radio buttons
    private class PetActionListener implements ActionListener {
        private final String petType;
        private final String imagePath;

        public PetActionListener(String petType, String imagePath) {
            this.petType = petType;
            this.imagePath = imagePath;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            label.setText("Selected pet: " + petType);
            try {
                BufferedImage originalImage = ImageIO.read(new File(imagePath));
                Image scaledImage = getScaledImage(originalImage, imageLabel.getWidth(), imageLabel.getHeight());
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } catch (IOException ioException) {
                ioException.printStackTrace();
                imageLabel.setText("Image not found");
            }
        }

        private Image getScaledImage(BufferedImage srcImg, int w, int h) {
            int originalWidth = srcImg.getWidth();
            int originalHeight = srcImg.getHeight();
            int newWidth = w;
            int newHeight = (newWidth * originalHeight) / originalWidth;

            if (newHeight > h) {
                newHeight = h;
                newWidth = (newHeight * originalWidth) / originalHeight;
            }

            return srcImg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        }
    }

    public static void main(String[] args) {
        // Create and display the frame
        SwingUtilities.invokeLater(() -> new PetSelector().setVisible(true));
    }
}
