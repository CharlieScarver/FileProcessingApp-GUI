package com.sap;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneUI.BasicVerticalLayoutManager;

public class Main implements ActionListener {

    public static final String ERR_FILE_READ_FAILED = "Couldn't read file";
    public static final String ERR_FILE_WRITE_FAILED = "Couldn't write to file";
    public static final String ERR_INVALID_INTEGER = "Invalid index input. Indexes should be valid integers";
    public static final String ERR_NON_POSITIVE_INDEX = "Invalid index input. Indexes should be positive";
    public static final String ERR_INDEX_OUT_OF_BOUNDS = "Invalid index input. Index is out of bounds";
    public static final String ERR_IDENTICAL_INDEXES = "Indexes are identical";

    public static JFrame frame;
    public static JPanel screens;
    public static CardLayout screensLayout;

    // File select screen (fs_ prefix)
    public static JPanel fileSelectScreen;
    public static JButton fs_button;
    public static JFileChooser fs_fileChooser;

    // Actions Screen (a_ prefix)
    public static JPanel actionsScreen;
    public static JLabel a_filePathLabel;
    public static JButton a_swapLinesButton;
    public static JButton a_swapWordsButton;
    public static JButton a_changeFileButton;
    public static JButton a_exitButton;

    // Swap Lines Screen (sl_ prefix)
    public static JPanel swapLinesScreen;
    public static JTextField sl_firstIndexField;
    public static JTextField sl_secondIndexField;
    public static JButton sl_confirmButton;

    // Swap Words Screen (sw_ prefix)
    public static JPanel swapWordsScreen;
    public static JTextField sw_firstRowIndexField;
    public static JTextField sw_firstWordIndexField;
    public static JTextField sw_secondRowIndexField;
    public static JTextField sw_secondWordIndexField;
    public static JButton sw_confirmButton;

    // Feedback Screen (f_ prefix)
    public static JPanel feedbackScreen;
    public static JLabel f_label;
    public static JButton homeButton;

    public static String path;
    public static List<String> lines;

    public static void main(String[] args) {
        // Initialize JFrame
        frame = new JFrame("File Processing App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add screens panel
        screens = new JPanel();
        screensLayout = new CardLayout();
        screens.setLayout(screensLayout);
        Border padding = BorderFactory.createEmptyBorder(15, 25, 15, 25);
        screens.setBorder(padding);
        frame.add(screens);

        Main mainInstance = new Main();

        homeButton = new JButton("Home");
        homeButton.addActionListener(mainInstance);

        // File select screen (fs_ prefix)
        fileSelectScreen = new JPanel();

        fs_fileChooser = new JFileChooser();
        File currentWorkingDir = new File(System.getProperty("user.dir"));
        fs_fileChooser.setCurrentDirectory(currentWorkingDir);

        fs_button = new JButton("Select a file (.txt)");
        fs_button.addActionListener(mainInstance);

        // For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); // use FlowLayout
        buttonPanel.add(fs_button);
        fileSelectScreen.add(buttonPanel, BorderLayout.CENTER);

        screens.add("File Select Screen", fileSelectScreen);

        // Actions Screen (a_ prefix)
        actionsScreen = new JPanel(new BorderLayout());

        JPanel a_titlePanel = new JPanel();
        a_filePathLabel = new JLabel();
        a_titlePanel.add(a_filePathLabel);
        actionsScreen.add(a_titlePanel, BorderLayout.NORTH);

        JPanel a_buttons = new JPanel();
        GridLayout a_buttonsLayout = new GridLayout(4, 1);
        a_buttonsLayout.setVgap(25);
        a_buttonsLayout.setHgap(15);
        a_buttons.setLayout(a_buttonsLayout);
        a_swapLinesButton = new JButton("Swap lines");
        a_swapLinesButton.addActionListener(mainInstance);
        a_buttons.add(a_swapLinesButton);

        a_swapWordsButton = new JButton("Swap words");
        a_swapWordsButton.addActionListener(mainInstance);
        a_buttons.add(a_swapWordsButton);

        a_changeFileButton = new JButton("Change file");
        a_changeFileButton.addActionListener(mainInstance);
        a_buttons.add(a_changeFileButton);

        a_exitButton = new JButton("Exit");
        a_exitButton.addActionListener(mainInstance);
        a_buttons.add(a_exitButton);

        actionsScreen.add(a_buttons, BorderLayout.CENTER);

        screens.add("Actions Screen", actionsScreen);

        // Swap Lines Screen (sl_ prefix)
        swapLinesScreen = new JPanel();
        GridLayout sl_layout = new GridLayout(2, 1);
        sl_layout.setVgap(15);
        sl_layout.setHgap(15);
        swapLinesScreen.setLayout(sl_layout);
        // Container that divides the screen horizontally in two equal parts
        JPanel sl_container = new JPanel();
        GridLayout sl_containerLayout = new GridLayout(2, 1);
        sl_containerLayout.setVgap(15);
        sl_containerLayout.setHgap(15);
        sl_container.setLayout(sl_containerLayout);

        // Inputs
        JPanel sl_inputs = new JPanel();
        GridLayout sl_inputsLayout = new GridLayout(2, 2);
        sl_inputsLayout.setVgap(15);
        sl_inputsLayout.setHgap(15);
        sl_inputs.setLayout(sl_inputsLayout);

        JLabel sl_firstIndexLabel = new JLabel("First row index:");
        sl_firstIndexField = new JTextField(5);
        sl_inputs.add(sl_firstIndexLabel);
        sl_inputs.add(sl_firstIndexField);
        JLabel sl_secondIndexLabel = new JLabel("Second row index:");
        sl_secondIndexField = new JTextField(5);
        sl_inputs.add(sl_secondIndexLabel);
        sl_inputs.add(sl_secondIndexField);

        sl_container.add(sl_inputs);

        // Buttons
        JPanel sl_buttons = new JPanel();
        sl_confirmButton = new JButton("Swap");
        sl_confirmButton.addActionListener(mainInstance);
        sl_buttons.add(homeButton);
        sl_buttons.add(sl_confirmButton);

        sl_container.add(sl_buttons);

        swapLinesScreen.add(sl_container);

        screens.add("Swap Lines Screen", swapLinesScreen);

        // Swap Words Screen (sw_ prefix)
        swapWordsScreen = new JPanel();
        GridLayout sw_layout = new GridLayout(2, 1);
        sw_layout.setVgap(15);
        sw_layout.setHgap(15);
        swapWordsScreen.setLayout(sw_layout);
        // Inputs
        JPanel sw_inputs = new JPanel();
        GridLayout sw_inputsLayout = new GridLayout(4, 2);
        sw_inputsLayout.setVgap(15);
        sw_inputsLayout.setHgap(15);
        sw_inputs.setLayout(sw_inputsLayout);

        JLabel sw_firstRowIndexLabel = new JLabel("First row index:");
        sw_firstRowIndexField = new JTextField(5);
        sw_inputs.add(sw_firstRowIndexLabel);
        sw_inputs.add(sw_firstRowIndexField);
        JLabel sw_firstWordIndexLabel = new JLabel("First word index:");
        sw_firstWordIndexField = new JTextField(5);
        sw_inputs.add(sw_firstWordIndexLabel);
        sw_inputs.add(sw_firstWordIndexField);

        JLabel sw_secondRowIndexLabel = new JLabel("Second row index:");
        sw_secondRowIndexField = new JTextField(5);
        sw_inputs.add(sw_secondRowIndexLabel);
        sw_inputs.add(sw_secondRowIndexField);
        JLabel sw_secondWordIndexLabel = new JLabel("Second word index:");
        sw_secondWordIndexField = new JTextField(5);
        sw_inputs.add(sw_secondWordIndexLabel);
        sw_inputs.add(sw_secondWordIndexField);
        // Buttons
        JPanel sw_buttons = new JPanel();
        sw_confirmButton = new JButton("Swap");
        sw_confirmButton.addActionListener(mainInstance);
        sw_buttons.add(homeButton);
        sw_buttons.add(sw_confirmButton);

        swapWordsScreen.add(sw_inputs);
        swapWordsScreen.add(sw_buttons);

        screens.add("Swap Words Screen", swapWordsScreen);

        // Feedback Screen (f_ prefix)
        feedbackScreen = new JPanel();
        GridLayout f_layout = new GridLayout(2, 1);
        f_layout.setVgap(15);
        f_layout.setHgap(15);
        feedbackScreen.setLayout(f_layout);

        f_label = new JLabel("", SwingConstants.CENTER);
        feedbackScreen.add(f_label);
        JPanel f_buttons = new JPanel();
        f_buttons.add(homeButton);
        feedbackScreen.add(f_buttons);

        screens.add("Feedback Screen", feedbackScreen);

        // Resize and make visible
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fs_button) {
            int returnVal = fs_fileChooser.showOpenDialog(fileSelectScreen);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fs_fileChooser.getSelectedFile();
                path = file.getPath();
                a_filePathLabel.setText(path);

                try {
                    // Read lines
                    lines = Files.readAllLines(Paths.get(path));
                } catch (IOException ex) {
                    f_label.setText(ERR_FILE_READ_FAILED);
                }

                // Resize elements
                frame.pack();
                // Go to the actions screen
                screensLayout.show(screens, "Actions Screen");
            }
        } else if (e.getSource() == a_changeFileButton) {
            screensLayout.show(screens, "File Select Screen");
        } else if (e.getSource() == a_swapLinesButton) {
            screensLayout.show(screens, "Swap Lines Screen");
        } else if (e.getSource() == a_swapWordsButton) {
            screensLayout.show(screens, "Swap Words Screen");
        } else if (e.getSource() == sl_confirmButton) {
            int firstIndex;
            int secondIndex;
            try {
                firstIndex = Integer.parseInt(sl_firstIndexField.getText()) - 1;
                secondIndex = Integer.parseInt(sl_secondIndexField.getText()) - 1;
            } catch (NumberFormatException ex) {
                f_label.setText(ERR_INVALID_INTEGER);
                frame.pack();
                // Go to the feedback screen
                screensLayout.show(screens, "Feedback Screen");
                frame.pack();
                return;
            } finally {
                sl_firstIndexField.setText("");
                sl_secondIndexField.setText("");
            }
            // Swap the lines
            swapLines(firstIndex, secondIndex);
            // Resize elements
            frame.pack();
            // Go to the feedback screen
            screensLayout.show(screens, "Feedback Screen");
        } else if (e.getSource() == sw_confirmButton) {
            int firstRowIndex;
            int firstWordIndex;
            int secondRowIndex;
            int secondWordIndex;
            try {
                firstRowIndex = Integer.parseInt(sw_firstRowIndexField.getText()) - 1;
                firstWordIndex = Integer.parseInt(sw_firstWordIndexField.getText()) - 1;
                secondRowIndex = Integer.parseInt(sw_secondRowIndexField.getText()) - 1;
                secondWordIndex = Integer.parseInt(sw_secondWordIndexField.getText()) - 1;
            } catch (NumberFormatException ex) {
                f_label.setText(ERR_INVALID_INTEGER);
                frame.pack();
                // Go to the feedback screen
                screensLayout.show(screens, "Feedback Screen");
                return;
            } finally {
                sw_firstRowIndexField.setText("");
                sw_firstWordIndexField.setText("");
                sw_secondRowIndexField.setText("");
                sw_secondWordIndexField.setText("");
            }
            // Swap the words
            swapWords(firstRowIndex, firstWordIndex, secondRowIndex, secondWordIndex);
            // Resize elements
            frame.pack();
            // Go to the feedback screen
            screensLayout.show(screens, "Feedback Screen");
        } else if (e.getSource() == homeButton) {
            screensLayout.show(screens, "Actions Screen");
        } else if (e.getSource() == a_exitButton) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    public static void swapLines(int firstRowIndex, int secondRowIndex) {
        // Validate input
        if (firstRowIndex < 0 || secondRowIndex < 0) {
            f_label.setText(ERR_NON_POSITIVE_INDEX);
            return;
        }

        // Check if the indexes are out of bounds
        if (firstRowIndex > lines.size() || secondRowIndex > lines.size()) {
            f_label.setText(ERR_INDEX_OUT_OF_BOUNDS);
            return;
        }

        // If the two line numbers are the same we don't have to do anything
        if (firstRowIndex == secondRowIndex) {
            f_label.setText(ERR_IDENTICAL_INDEXES);
            return;
        }

        // Get the lines
        String firstRow = lines.get(firstRowIndex);
        String secondRow = lines.get(secondRowIndex);

        // Swap the lines
        lines.set(firstRowIndex, secondRow);
        lines.set(secondRowIndex, firstRow);

        try {
            // Write the changes to the file
            // Files.write(Paths.get(path), lines, StandardOpenOption.TRUNCATE_EXISTING);
            Files.writeString(Paths.get(path), String.join(System.lineSeparator(), lines), StandardOpenOption.TRUNCATE_EXISTING);
            f_label.setText("The changes were written to the file successfully");
        } catch (IOException e) {
            f_label.setText(ERR_FILE_WRITE_FAILED);
        }
    }

    public static void swapWords(int firstRowIndex, int firstWordIndex, int secondRowIndex, int secondWordIndex) {
        // Validate input
        if (firstRowIndex < 0 || firstWordIndex < 0 || secondRowIndex < 0 || secondWordIndex < 0) {
            f_label.setText(ERR_NON_POSITIVE_INDEX);
            return;
        }

        // Check if the indexes are out of bounds
        if (firstRowIndex > lines.size() || secondRowIndex > lines.size()) {
            f_label.setText(ERR_INDEX_OUT_OF_BOUNDS);
            return;
        }

        // We want to preserve the tabs or spaces between the words so we split using the following positive lookahead
        // Matches an empty match if followed by spaces or tabs: "(?=[ \\t]+)"
        // This way nothing is removed from the original string after the split
        String splitRegex = "(?=[ \t]+)";

        // Check if we're swapping words on different lines or on the same one
        if (firstRowIndex != secondRowIndex) {
            // Split the lines
            String[] firstRow = lines.get(firstRowIndex).split(splitRegex);
            String[] secondRow = lines.get(secondRowIndex).split(splitRegex);

            // Check if the indexes are out of bounds
            if (firstWordIndex > firstRow.length || secondWordIndex > secondRow.length) {
                f_label.setText(ERR_INDEX_OUT_OF_BOUNDS);
                return;
            }

            // Extract the words without their prefixing whitespace
            String firstWord = firstRow[firstWordIndex].replaceAll("\\s+", "");
            String secondWord = secondRow[secondWordIndex].replaceAll("\\s+", "");

            // Swap the words while maintaining the corresponding whitespace
            secondRow[secondWordIndex] = secondRow[secondWordIndex].replaceAll("\\S+", firstWord);
            firstRow[firstWordIndex] = firstRow[firstWordIndex].replaceAll("\\S+", secondWord);

            // Construct the new lines
            lines.set(firstRowIndex, String.join("", firstRow));
            lines.set(secondRowIndex, String.join("", secondRow));
        } else {
            // Split the line
            String[] row = lines.get(firstRowIndex).split(splitRegex);

            // Check if the indexes are out of bounds
            if (firstWordIndex > row.length || secondWordIndex > row.length) {
                f_label.setText(ERR_INDEX_OUT_OF_BOUNDS);
                return;
            }

            // If the two word indexes are the same we don't have to do anything
            if (firstWordIndex == secondWordIndex) {
                f_label.setText(ERR_IDENTICAL_INDEXES);
                return;
            }

            // Extract the words without their prefixing whitespace
            String firstWord = row[firstWordIndex].replaceAll("\\s+", "");
            String secondWord = row[secondWordIndex].replaceAll("\\s+", "");

            // Swap the words while maintaining the corresponding whitespace
            row[secondWordIndex] = row[secondWordIndex].replaceAll("\\S+", firstWord);
            row[firstWordIndex] = row[firstWordIndex].replaceAll("\\S+", secondWord);

            // Construct the new line
            lines.set(firstRowIndex, String.join("", row));
        }

        try {
            // Write the changes to the file
            Files.writeString(Paths.get(path), String.join(System.lineSeparator(), lines), StandardOpenOption.TRUNCATE_EXISTING);
            f_label.setText("The changes were written to the file successfully");
        } catch (IOException e) {
            f_label.setText(ERR_FILE_WRITE_FAILED);
        }
    }
}
