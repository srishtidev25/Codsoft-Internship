import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// BankAccount class to represent the user's bank account
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
}

// ATM class to represent the ATM machine
public class ATM extends JFrame {
    private BankAccount account;
    private JTextField amountField;
    private JLabel balanceLabel;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton checkBalanceButton;
    private JTextArea messageArea;

    public ATM(BankAccount account) {
        super("ATM Machine");
        this.account = account;

        // Set layout to BorderLayout for better organization
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Balance label
        balanceLabel = new JLabel("Current Balance: $" + account.getBalance());
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(balanceLabel);

        // Amount input panel
        JPanel amountPanel = new JPanel(new BorderLayout());
        amountPanel.add(new JLabel("Enter Amount:"), BorderLayout.NORTH);
        amountField = new JTextField(10);
        amountPanel.add(amountField, BorderLayout.CENTER);
        mainPanel.add(amountPanel);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        checkBalanceButton = new JButton("Check Balance");

        buttonsPanel.add(withdrawButton);
        buttonsPanel.add(depositButton);
        buttonsPanel.add(checkBalanceButton);
        mainPanel.add(buttonsPanel);

        // Message area
        messageArea = new JTextArea(5, 20);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        mainPanel.add(scrollPane);

        add(mainPanel, BorderLayout.CENTER);

        // Action listeners
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = parseAmount();
                if (amount > 0) {
                    if (account.withdraw(amount)) {
                        showMessage("Successfully withdrew $" + amount);
                    } else {
                        showMessage("Insufficient balance.");
                    }
                    updateBalance();
                } else {
                    showMessage("Please enter a valid amount.");
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = parseAmount();
                if (amount > 0) {
                    account.deposit(amount);
                    showMessage("Successfully deposited $" + amount);
                    updateBalance();
                } else {
                    showMessage("Please enter a valid amount.");
                }
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMessage("Current balance: $" + account.getBalance());
            }
        });

        // Frame settings
        pack(); // Adjusts frame size to fit components
        setLocationRelativeTo(null); // Centers the frame on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private double parseAmount() {
        try {
            return Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void updateBalance() {
        balanceLabel.setText("Current Balance: $" + account.getBalance());
    }

    private void showMessage(String message) {
        messageArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ATM(new BankAccount(1000)); // Initial balance of $1000
            }
        });
    }
}