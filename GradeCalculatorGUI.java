import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradeCalculatorGUI extends JFrame {
    private JTextField[] subjectFields;
    private JLabel totalMarksLabel;
    private JLabel averagePercentageLabel;
    private JLabel gradeLabel;
    private JButton calculateButton;

    public GradeCalculatorGUI(int numberOfSubjects) {
        super("Grade Calculator");
        setLayout(new GridLayout(numberOfSubjects + 5, 2));

        subjectFields = new JTextField[numberOfSubjects];

        for (int i = 0; i < numberOfSubjects; i++) {
            add(new JLabel("Marks for Subject " + (i + 1) + ":"));
            subjectFields[i] = new JTextField();
            add(subjectFields[i]);
        }

        calculateButton = new JButton("Calculate");
        add(calculateButton);

        totalMarksLabel = new JLabel("Total Marks: ");
        add(totalMarksLabel);

        averagePercentageLabel = new JLabel("Average Percentage: ");
        add(averagePercentageLabel);

        gradeLabel = new JLabel("Grade: ");
        add(gradeLabel);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateResults();
            }
        });

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void calculateResults() {
        try {
            int totalMarks = 0;
            int numberOfSubjects = subjectFields.length;

            for (JTextField field : subjectFields) {
                int marks = Integer.parseInt(field.getText());
                totalMarks += marks;
            }

            double averagePercentage = (double) totalMarks / numberOfSubjects;
            String grade = calculateGrade(averagePercentage);

            totalMarksLabel.setText("Total Marks: " + totalMarks);
            averagePercentageLabel.setText(String.format("Average Percentage: %.2f%%", averagePercentage));
            gradeLabel.setText("Grade: " + grade);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid marks for all subjects.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return "A+";
        } else if (averagePercentage >= 80) {
            return "A";
        } else if (averagePercentage >= 70) {
            return "B";
        } else if (averagePercentage >= 60) {
            return "C";
        } else if (averagePercentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        int numberOfSubjects = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of subjects:"));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GradeCalculatorGUI(numberOfSubjects);
            }
        });
    }
}
