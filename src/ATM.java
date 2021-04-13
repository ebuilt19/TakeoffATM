import java.util.*;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

class User {
    double balance;
    List<String> transaction;

    User() {
    };

    User(double balance, List<String> transactionHistory) {
        this.balance = balance;
        this.transaction = transactionHistory;
    }
}

public class ATM extends User {
    User user;
    int machineBal;

    ATM(User user, int machineBal) {
        this.user = user;
        this.machineBal = machineBal;
    }

    public List<String> getTransactionHistory(User user) {
        return user.transaction;
    }

    public void addTrans(String transaction) {
        user.transaction.add(transaction);
    }

    public double getBalance(User user) {
        return user.balance;
    }

    public void deposite(double amount) {
        user.balance += amount;
    }

    public void withdraw(int amount) {
        user.balance -= amount;
    }

    public void setBalance(double amount) {
        user.balance = amount;
    }

    public String getDateAndTime() {
        LocalDateTime currDateObj = LocalDateTime.now();
        DateTimeFormatter currDateFormatedObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // stores formatted day and time
        String currDate = currDateObj.format(currDateFormatedObj);
        return currDate;
    }

}
