import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MonthlyReport {
    public ArrayList<MonthlyLineRecord> records = new ArrayList<>();
    private final static boolean REVENUE = false;
    private final static boolean EXPENSE = true;

    public MonthlyReport(String path) {
        String content = readFileContentsOrNull(path);
        if (content != null) {
            String[] lines = content.split("\r?\n");
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                String[] parts = line.split(",");
                String itemName = parts[0];
                boolean isExpense = Boolean.parseBoolean(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                int sumOfOne = Integer.parseInt(parts[3]);
                MonthlyLineRecord record = new MonthlyLineRecord(itemName, isExpense, quantity, sumOfOne);
                records.add(record);
            }
            System.out.println("Месячный отчёт " + path + " успешно прочитан");
        }
    }

    public int countTotalSum(boolean isExpense) {
        int sum = 0;
        for (MonthlyLineRecord record : records) {
            if (isExpense == record.isExpense)
                sum += record.sumOfOne * record.quantity;
        }
        return sum;
    }

    public void printMonthInfo() {
        int maxRevenueIndex = getMaxItemIndex(REVENUE);
        System.out.println("Самый прибыльный товар: " + records.get(maxRevenueIndex).itemName + " - " +
                records.get(maxRevenueIndex).quantity * records.get(maxRevenueIndex).sumOfOne + " руб.");
        int maxExpenseIndex = getMaxItemIndex(EXPENSE);
        System.out.println("Самая большая трата: " + records.get(maxExpenseIndex).itemName + " - " +
                records.get(maxExpenseIndex).quantity * records.get(maxExpenseIndex).sumOfOne + " руб.");
    }

    private int getMaxItemIndex(boolean isExpense) {
        int sum;
        int maxSum = 0;
        int maxItemIndex = 0;

        for (MonthlyLineRecord record : records) {
            if (isExpense == record.isExpense) {
                sum = record.sumOfOne * record.quantity;
                if (sum > maxSum) {
                    maxSum = sum;
                    maxItemIndex = records.indexOf(record);
                }
            }
        }
        return maxItemIndex;
    }

    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории. \n");
            return null;
        }
    }
}
