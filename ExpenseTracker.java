import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpenseTracker {

    static final int MAX_EXPENSES = 100;
    static String[] categories = new String[MAX_EXPENSES];
    static String[] descriptions = new String[MAX_EXPENSES];
    static double[] amounts = new double[MAX_EXPENSES];
    static int expenseCount = 0;

    private JFrame frame;
    private JTextField categoryField;
    private JTextField descriptionField;
    private JTextField amountField;
    private JTextArea expensesArea;
    private JTextField totalExpensesField;
    private ImageIcon checkIcon;

    public ExpenseTracker() {
        checkIcon = new ImageIcon("checkmark.png");

        frame = new JFrame("Stylish Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Expense Tracker", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        frame.add(titleLabel, BorderLayout.NORTH);

        JLabel categoryLabel = new JLabel("Category:");
        categoryField = new JTextField(15);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(15);
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(categoryLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        inputPanel.add(categoryField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(descriptionField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(amountLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        inputPanel.add(amountField, gbc);

        frame.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Expense");
        addButton.setBackground(Color.GREEN);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.addActionListener(new AddExpenseListener());

        JButton viewButton = new JButton("View Expenses");
        viewButton.setBackground(Color.CYAN);
        viewButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewButton.addActionListener(new ViewExpensesListener());

        JButton totalButton = new JButton("Calculate Total");
        totalButton.setBackground(Color.ORANGE);
        totalButton.setFont(new Font("Arial", Font.BOLD, 14));
        totalButton.addActionListener(new TotalExpensesListener());

        buttonsPanel.add(addButton);
        buttonsPanel.add(viewButton);
        buttonsPanel.add(totalButton);
        frame.add(buttonsPanel, BorderLayout.SOUTH);

        expensesArea = new JTextArea(15, 40);
        expensesArea.setEditable(false);
        expensesArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        expensesArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(expensesArea);
        scrollPane.setPreferredSize(new Dimension(450, 300));

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(new JLabel("Expenses Log:", SwingConstants.LEFT), BorderLayout.NORTH);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        totalExpensesField = new JTextField();
        totalExpensesField.setEditable(false);
        totalExpensesField.setFont(new Font("Arial", Font.BOLD, 14));
        outputPanel.add(totalExpensesField, BorderLayout.SOUTH);

        frame.add(outputPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }

    private void addExpense(String category, String description, double amount) {
        if (expenseCount < MAX_EXPENSES) {
            categories[expenseCount] = category;
            descriptions[expenseCount] = description;
            amounts[expenseCount] = amount;
            expenseCount++;
            JOptionPane.showMessageDialog(frame, "Expense added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE, checkIcon);
        } else {
            JOptionPane.showMessageDialog(frame, "Expense list is full.");
        }
    }

    private void viewExpenses() {
        expensesArea.setText("");
        if (expenseCount == 0) {
            expensesArea.setText("No expenses recorded.");
        } else {
            StringBuilder allExpenses = new StringBuilder();
            for (int i = 0; i < expenseCount; i++) {
                allExpenses.append("Category: ").append(categories[i])
                        .append(", Description: ").append(descriptions[i])
                        .append(", Amount: ").append(amounts[i]).append("\n");
            }
            expensesArea.setText(allExpenses.toString());
        }
    }

    private double calculateTotal() {
        double total = 0;
        for (int i = 0; i < expenseCount; i++) {
            total += amounts[i];
        }
        return total;
    }

    private class AddExpenseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String category = categoryField.getText();
            String description = descriptionField.getText();
            try {
                double amount = Double.parseDouble(amountField.getText());
                addExpense(category, description, amount);
                categoryField.setText("");
                descriptionField.setText("");
                amountField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid amount.");
            }
        }
    }

    private class ViewExpensesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            viewExpenses();
        }
    }

    private class TotalExpensesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double total = calculateTotal();
            totalExpensesField.setText("Total Expenses: ₹" + total);
        }
    }

    public static void main(String[] args) {
        new ExpenseTracker();
    }
}
