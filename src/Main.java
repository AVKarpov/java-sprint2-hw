import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в приложение Автоматизация бухгалтерии!");
        Scanner scanner = new Scanner(System.in);
        YearlyReport yearlyReport = null;
        ArrayList<MonthlyReport> monthlyReports = new ArrayList<>();
        String[] monthTitle = new String[] {"ЯНВАРЬ","ФЕВРАЛЬ","МАРТ","АПРЕЛЬ","МАЙ","ИЮНЬ","ИЮЛЬ",
                "АВГУСТ","СЕНТЯБРЬ","ОКТЯБРЬ","НОЯБРЬ","ДЕКАБРЬ"};


        while (true) {
            printMenu();
            try {
                int userInput = scanner.nextInt();
                switch (userInput) {
                    //Считать все месячные отчёты
                    case 1:
                        for (int i = 1; i <= 3; i++) {
                            monthlyReports.add(new MonthlyReport("resources/m.20210" + i + ".csv"));
                        }
                        System.out.println("");
                        break;
                    //Считать годовой отчёт
                    case 2:
                        yearlyReport = new YearlyReport("resources/y.2021.csv");
                        break;
                    //Сверить отчёты
                    case 3:
                        boolean hasErrors = false;
                        if ((yearlyReport != null) && (monthlyReports.size() > 0)) {
                            for (int i = 0; i < monthlyReports.size(); i++) {
                                if ((monthlyReports.get(i).countTotalSum(true) != yearlyReport.countTotalSum(i+1,true))
                                        || (monthlyReports.get(i).countTotalSum(false) != yearlyReport.countTotalSum(i+1,false))) {
                                    System.out.println("В месяце " + monthTitle[i] + " обнаружено несоответствие.");
                                    hasErrors = true;
                                }
                            }
                            if (!hasErrors)
                                System.out.println("Сверка отчётов прошла успешно, несоответствий не выявлено.");
                            System.out.println("");
                        }
                        break;
                    //Вывести информацию о всех месячных отчётах
                    case 4:
                        if (monthlyReports.size() > 0) {
                            for (int i = 0; i < monthlyReports.size(); i++) {
                                System.out.println(monthTitle[i]);
                                monthlyReports.get(i).printMonthInfo();
                            }
                            System.out.println("");
                        }
                        break;
                    //Вывести информацию о годовом отчёте
                    case 5:
                        if (yearlyReport != null) {
                            yearlyReport.printYearInfo();
                        }
                        break;
                    //Выйти из приложения
                    case 6:
                        System.out.println("Выход из программы..");
                        return;

                    default:
                        System.out.println("Извините, такой команды пока нет.");
                }
            } catch (Exception e) {
                System.out.println("Необходимо ввести число \n");
                scanner.nextLine();
            }
        }
    }

    public static void printMenu() {
        System.out.println("Выберите одно из следующих действий: ");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("6 - Выйти из приложения");
    }
}

