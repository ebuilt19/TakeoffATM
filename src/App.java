import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws Exception {
        int machineMoney = 10000;
        User user = new User(500.00, new ArrayList<String>());
        ATM atm = new ATM(user, machineMoney);
        String input;
        double depositAmount;
        int withdrawAmount;

        System.out.println("Enter transaction, the choices are,\ndeposit\nbalance\nhistory\nwithdraw\nend");
        Scanner userInput = new Scanner(System.in); // Create a Scanner object
        do {
            userInput = new Scanner(System.in);
            System.out.println("Enter transaction");
            input = userInput.nextLine(); // Read user input

            switch (input.toLowerCase()) {
            case "history":
                String history = "";
                if (atm.getTransactionHistory(user).size() == 0)
                    System.out.println("No history found");
                else {
                    // could have used a queue
                    Collections.reverse(atm.getTransactionHistory(user));
                    for (String x : atm.getTransactionHistory(user)) {
                        history += x + "\n";
                    }
                    System.out.println(history);
                }
                break;
            case "withdraw":
                if (machineMoney == 0) {
                    System.out.println("Unable to process your withdrawal at this time");
                    break;
                }
                if (atm.getBalance(user) <= 0) {
                    System.out.println("Your account is overdrawn! You may not make withdrawals at this time.");
                    break;
                }
                System.out.println("How much would you like to withdraw");

                if (userInput.hasNextInt()) {
                    withdrawAmount = userInput.nextInt(); // Read user input
                    if (withdrawAmount < 0 || withdrawAmount > 10000) {
                        System.out.println("invalid amount starting over");
                        break;
                    }
                    // withdraw amount has to be multiple of 20
                    if (withdrawAmount % 20 == 0) {
                        // if user has enough money
                        if (atm.getBalance(user) >= withdrawAmount && machineMoney != 0) {
                            atm.withdraw(withdrawAmount);
                            atm.addTrans(atm.getDateAndTime() + " -" + withdrawAmount + " "
                                    + String.format("%1$,.2f", atm.getBalance(user)));

                            System.out.println("Current balance:" + String.format("%1$,.2f", atm.getBalance(user)));
                            System.out.println("Amount dispensed: $" + withdrawAmount + "\nCurrent balance:"
                                    + String.format("%1$,.2f", atm.getBalance(user)));
                        }

                        else if (atm.getBalance(user) > 0 && atm.getBalance(user) < withdrawAmount) {
                            atm.setBalance(atm.getBalance(user) - withdrawAmount);
                            machineMoney -= withdrawAmount;
                            if (machineMoney < 0) {
                                machineMoney *= -1;
                                withdrawAmount -= machineMoney;
                                atm.addTrans(atm.getDateAndTime() + " -" + withdrawAmount + " "
                                        + String.format("%1$,.2f", atm.getBalance(user)));
                                System.out.println("unable to dispense fill amount requested at this time");
                            } else {

                                atm.addTrans(atm.getDateAndTime() + " -" + withdrawAmount + " "
                                        + String.format("%1$,.2f", atm.getBalance(user)));
                                // overdraft fee applied;
                                atm.withdraw(5);
                                atm.addTrans(
                                        atm.getDateAndTime() + " -5 " + String.format("%1$,.2f", atm.getBalance(user)));

                                System.out.println("Amount dispensed: $" + withdrawAmount);
                                System.out.println("You have been charged an overdraft fee of $5. Current balance: "
                                        + String.format("%1$,.2f", atm.getBalance(user)));
                            }
                        }

                    } else {
                        System.out.println("Machine only contains $20 bills");
                        break;
                    }

                } else {
                    System.out.println("invalid amount starting over");
                    break;
                }
                break;
            case "deposit":
                System.out.println("Enter Deposit amount");
                if (userInput.hasNextDouble()) {
                    depositAmount = userInput.nextDouble(); // Read user input
                    if (depositAmount < 0) {
                        System.out.println("invalid amount starting over");
                        break;
                    } else {

                        atm.deposite(depositAmount);
                        atm.addTrans(atm.getDateAndTime() + " " + depositAmount + " "
                                + String.format("%1$,.2f", atm.getBalance(user)));
                        System.out.println("Current balance:" + String.format("%1$,.2f", atm.getBalance(user)));
                        break;
                    }

                } else {
                    System.out.println("invalid amount starting over");
                    break;
                }
            case "balance":
                System.out.println("Current balance:" + String.format("%1$,.2f", atm.getBalance(user)));
                break;
            case "atm":
                System.out.println(machineMoney);
                break;
            case "end":
                input = "end";
                break;
            default:
                System.out.println("invalid selection try again");
                break;
            }

        } while (input != "end");

        userInput.close();
    }
}