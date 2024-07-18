import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessingGameGUI extends JFrame {
    private int numberToGuess;
    private int attemptsLeft;
    private int score;
    private final int maxAttempts = 10;

    private JTextField guessField;
    private JButton guessButton;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JButton playAgainButton;

    public GuessingGameGUI() {
        super("Guessing Game");
        setLayout(new GridLayout(6, 1));

        score = 0;

        add(new JLabel("Guess the number between 1 and 100:"));

        guessField = new JTextField();
        add(guessField);

        guessButton = new JButton("Guess");
        add(guessButton);

        feedbackLabel = new JLabel("");
        add(feedbackLabel);

        attemptsLabel = new JLabel("Attempts left: " + maxAttempts);
        add(attemptsLabel);

        scoreLabel = new JLabel("Score: " + score);
        add(scoreLabel);

        playAgainButton = new JButton("Play Again");
        playAgainButton.setVisible(false);
        add(playAgainButton);

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newRound();
            }
        });

        newRound();

        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void newRound() {
        Random random = new Random();
        numberToGuess = random.nextInt(100) + 1;
        attemptsLeft = maxAttempts;
        feedbackLabel.setText("");
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        guessField.setText("");
        guessField.setEditable(true);
        guessButton.setEnabled(true);
        playAgainButton.setVisible(false);
    }

    private void handleGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            attemptsLeft--;

            if (guess < numberToGuess) {
                feedbackLabel.setText("Too low!");
            } else if (guess > numberToGuess) {
                feedbackLabel.setText("Too high!");
            } else {
                feedbackLabel.setText("Correct! You've guessed the number.");
                score += attemptsLeft + 1;
                guessField.setEditable(false);
                guessButton.setEnabled(false);
                playAgainButton.setVisible(true);
            }

            if (attemptsLeft == 0 && guess != numberToGuess) {
                feedbackLabel.setText("Sorry, you've used all attempts. The number was " + numberToGuess + ".");
                guessField.setEditable(false);
                guessButton.setEnabled(false);
                playAgainButton.setVisible(true);
            }

            attemptsLabel.setText("Attempts left: " + attemptsLeft);
            scoreLabel.setText("Score: " + score);
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuessingGameGUI();
            }
        });
    }
}
