import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        int machineMoney = 10000;
        int minAmount;
        User user = new User(500.00, new ArrayList<String>());
        ATM atm = new ATM(user, machineMoney);
        String input;
        double depositAmount;
        int withdrawAmount;

        System.out.println("Enter transaction, the choices are,\ndeposit\nbalance\nhistory\nwithdraw\nend");
        do {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter transaction");
            input = userInput.nextLine(); // Read user input

            switch (input.toLowerCase()) {
                case "history":
                    String history = "";
                    if (atm.getTransactionHistory(user).size() == 0)
                        System.out.println("No history found");
                    else {
                        System.out.println(atm.getTransactionHistory(user));
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
                        minAmount = Math.min(machineMoney, withdrawAmount);
                        if (withdrawAmount < 0 || withdrawAmount > 10000) {
                            System.out.println("invalid amount starting over");
                            break;
                        }
                        // withdraw amount has to be multiple of 20
                        if (withdrawAmount % 20 == 0) {
                            // if user has enough money
                            if (atm.getBalance(user) >= withdrawAmount && machineMoney != 0) {
                                atm.withdraw(minAmount);
                                machineMoney -= minAmount;
                                atm.addTrans(atm.getDateAndTime() + " -" + minAmount + " "
                                        + String.format("%1$,.2f", atm.getBalance(user)));

                                System.out.println("Current balance:" + String.format("%1$,.2f", atm.getBalance(user)));
                                System.out.println("Amount dispensed: $" + minAmount + "\nCurrent balance:"
                                        + String.format("%1$,.2f", atm.getBalance(user)));

                                System.out.println("machine money: " + machineMoney);
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
                                    atm.withdraw(5);
                                    // add overdraft to transactionHistory
                                    atm.addTrans(atm.getDateAndTime() + " -5 "
                                            + String.format("%1$,.2f", atm.getBalance(user)));

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
                    // checks to see if has anything thats not a double
                    if (userInput.hasNextDouble()) {
                        // if no random characters and is a double then
                        depositAmount = userInput.nextDouble(); // Read user input
                        if (depositAmount < 0) {
                            System.out.println("invalid amount starting over");
                            break;
                        } else {
                            // if its a valid deposit amount add to user acount
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
                case "end":
                    input = "end";
                    break;
                default:
                    System.out.println("invalid selection try again");
                    break;
            }

            userInput.close();
        } while (input != "end");
    }
}
