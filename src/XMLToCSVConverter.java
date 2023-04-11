import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class XMLToCSVConverter {

    private JFrame frame;
    private JPanel mainPanel;
    private JLabel inputLabel;
    private JFileChooser inputChooser;
    private JButton convertButton;

    public XMLToCSVConverter() {
        inputChooser = new JFileChooser();
        inputChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        inputChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                return extension.equals("xml");
            }

            @Override
            public String getDescription() {
                return "XML files";
            }
        });
        frame = new JFrame("XML to CSV Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        inputLabel = new JLabel("Input file:");
        mainPanel.add(inputLabel);
        mainPanel.add(inputChooser);
        convertButton = new JButton("CONVERT2CSV");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File inputFile = inputChooser.getSelectedFile();
                try {
                    XMLToCSVConverter converter = new XMLToCSVConverter();
                    converter.convert(inputFile, "data.csv");
                    JOptionPane.showMessageDialog(frame, "CSV file written!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error converting file: " + ex.getMessage());
                }
            }
        });
        mainPanel.add(convertButton);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new XMLToCSVConverter();
            }
        });
    }

    private void convert(File inputFile, String outputFilename) throws IOException {
        Scanner scanner = new Scanner(inputFile);
        FileWriter writer = new FileWriter(outputFilename);

        writer.write("");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(" ");

            writer.write(values[0] + ",");
            writer.write(values[1] + "\n");
        }

        writer.close();
    }
}