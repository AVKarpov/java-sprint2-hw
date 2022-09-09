import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    public ArrayList<YearlyLineRecord> records = new ArrayList<>();
    private final static boolean REVENUE = false;
    private final static boolean EXPENSE = true;
    private HashMap<Integer, Integer> monthlyProfit = new HashMap<>();

    public YearlyReport(String path) {
        String content = readFileContentsOrNull(path);
        if (content != null) {
            String[] lines = content.split("\r?\n");
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                String[] parts = line.split(",");
                int month = Integer.parseInt(parts[0]);
                int amount = Integer.parseInt(parts[1]);
                boolean isExpense = Boolean.parseBoolean(parts[2]);
                YearlyLineRecord record = new YearlyLineRecord(month, amount, isExpense);
                records.add(record);
            }
            System.out.println("Годовой отчёт успешно прочитан \n");
        }
    }

    public int countTotalSum(int month, boolean isExpense) {
        int sum = 0;
        for (YearlyLineRecord record : records) {
            if ((month == record.month) && (isExpense == record.isExpense))
                sum += record.amount;
        }
        return sum;
    }

    public void printYearInfo() {
        System.out.println("2021 год");
        countProfit();
        if (monthlyProfit.size() > 0) {
            System.out.print("Прибыль по кажджому месяцу: ");
            System.out.println(monthlyProfit.entrySet());
        }
        System.out.println("Средний расход за все месяцы в году: " + countAvgSum(EXPENSE));
        System.out.println("Средний доход за все месяцы в году: " + countAvgSum(REVENUE));
        System.out.println("");
    }

    private void countProfit() {
        int revenue = -1;
        int expense = -1;

        for (YearlyLineRecord record : records) {
            if (record.isExpense)
                expense = record.amount;
            else
                revenue = record.amount;
            if (expense >= 0 && revenue >= 0) {
                monthlyProfit.put(record.month, revenue - expense);
                revenue = -1;
                expense = -1;
            }
        }
    }

    private int countAvgSum(boolean isExpense) {
        int sum = 0;
        for (YearlyLineRecord record : records) {
            if (record.isExpense == isExpense)
                sum += record.amount;
        }
        return sum;
    }

    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно, файл не находится в нужной директории. \n");
            return null;
        }
    }
}
