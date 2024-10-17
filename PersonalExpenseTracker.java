import java.io.*;
import java.util.*;

// Class to represent an expense
class Expense {
    String category;
    String description;
    double amount;

    public Expense(String category, String description, double amount) {
        this.category = category;
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Category: " + category + ", Description: " + description + ", Amount: $" + amount;
    }
}

public class PersonalExpenseTracker {

    private static final String FILE_NAME = "expenses.txt";
    private static List<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        loadExpenses();  // Load saved expenses from file
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nPersonal Expense Tracker");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Delete Expense");
            System.out.println("4. Show Summary by Category");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    deleteExpense(scanner);
                    break;
                case 4:
                    showSummary();
                    break;
                case 5:
                    saveExpenses();
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);
    }

    // Method to add an expense
    private static void addExpense(Scanner scanner) {
        System.out.print("Enter category (e.g., Food, Transport, Entertainment): ");
        String category = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        expenses.add(new Expense(category, description, amount));
        System.out.println("Expense added successfully.");
    }

    // Method to view all expenses
    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            System.out.println("Expenses:");
            for (int i = 0; i < expenses.size(); i++) {
                System.out.println((i + 1) + ". " + expenses.get(i));
            }
        }
    }

    // Method to delete an expense
    private static void deleteExpense(Scanner scanner) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to delete.");
            return;
        }

        viewExpenses();
        System.out.print("Enter the number of the expense to delete: ");
        int index = scanner.nextInt() - 1;

        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
            System.out.println("Expense deleted successfully.");
        } else {
            System.out.println("Invalid expense number.");
        }
    }

    // Method to show a summary of expenses by category
    private static void showSummary() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        Map<String, Double> categoryTotals = new HashMap<>();
        for (Expense expense : expenses) {
            categoryTotals.put(expense.category, categoryTotals.getOrDefault(expense.category, 0.0) + expense.amount);
        }

        System.out.println("Expense Summary by Category:");
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.println("Category: " + entry.getKey() + ", Total Amount: $" + entry.getValue());
        }
    }

    // Method to save expenses to a file
    private static void saveExpenses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Expense expense : expenses) {
                writer.println(expense.category + "," + expense.description + "," + expense.amount);
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    // Method to load expenses from a file
    private static void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    expenses.add(new Expense(parts[0], parts[1], Double.parseDouble(parts[2])));
                }
            }
        } catch (FileNotFoundException e) {
            // File not found, start with an empty list
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }
}
