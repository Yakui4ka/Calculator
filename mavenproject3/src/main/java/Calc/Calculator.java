package Calc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private JLabel history;
    private StringBuilder currentInput;
    private double accumulator; // Поле для хранения накопленного результата
    private String operator;

    public Calculator() {
        currentInput = new StringBuilder();
        operator = "";
        accumulator = 0;

        // Set up the frame
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the display field
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);

        // Create the history label
        history = new JLabel("", SwingConstants.RIGHT);
        history.setFont(new Font("Arial", Font.PLAIN, 18));

        // Create a panel for display and history
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(history, BorderLayout.NORTH);
        displayPanel.add(display, BorderLayout.CENTER);

        add(displayPanel, BorderLayout.NORTH);

        // Create the panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4)); // Updated to 6 rows for additional buttons

        // Add buttons to the panel
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "exp", "log", "x²",
                "√"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("C")) {
            currentInput.setLength(0);
            display.setText("");
            history.setText("");
            accumulator = 0;
            operator = "";
        } else if (command.equals("=")) {
            if (!operator.isEmpty()) {
                accumulator = calculate(Double.parseDouble(currentInput.toString()));
                display.setText(String.valueOf(accumulator));
                history.setText(history.getText() + currentInput.toString() + " = " + accumulator);
                currentInput.setLength(0);
                operator = "";
            }
        } else if ("+-*/".contains(command)) {
            if (currentInput.length() > 0) {
                if (operator.isEmpty()) {
                    accumulator = Double.parseDouble(currentInput.toString());
                } else {
                    accumulator = calculate(Double.parseDouble(currentInput.toString()));
                }
                operator = command;
                history.setText(accumulator + " " + operator);
                currentInput.setLength(0);
            }
        } else if ("exp log x² √".contains(command)) {
            if (currentInput.length() > 0) {
                double input = Double.parseDouble(currentInput.toString());
                double result = 0;
                switch (command) {
                    case "exp":
                        result = Math.exp(input);
                        break;
                    case "log":
                        result = Math.log(input);
                        break;
                    case "x²":
                        result = Math.pow(input, 2);
                        break;
                    case "√":
                        result = Math.sqrt(input);
                        break;
                }
                display.setText(String.valueOf(result));
                history.setText(command + "(" + input + ") = " + result);
                currentInput.setLength(0);
            }
        } else {
            if (command.equals(".") && currentInput.toString().contains(".")) {
                // Не позволять добавлять больше одной десятичной точки
                return;
            }
            currentInput.append(command);
            display.setText(currentInput.toString());
        }
    }

    private double calculate(double secondOperand) {
        switch (operator) {
            case "+":
                return accumulator + secondOperand;
            case "-":
                return accumulator - secondOperand;
            case "*":
                return accumulator * secondOperand;
            case "/":
                return accumulator / secondOperand;
            default:
                return secondOperand;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
///zzzzz