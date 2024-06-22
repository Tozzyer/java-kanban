import java.util.Scanner;

public class Main {




    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); //создаём сканнер
        int menuInInt; // переменная для входных целочисленных данных ввода
        String menuInStr; // переменная для входных строковых данных ввода
        TaskManager master = new TaskManager(); // создаём управителя, который всем управляет и хранит

        while (true){
            PrintMenu.mainMenu();
            menuInInt = sc.nextInt();
            switch (menuInInt){
                case 1:
                    sc.nextLine();
                    System.out.println("Введите задачу:");
                    menuInStr = sc.nextLine();
                    TaskManager.addTask(menuInStr,TaskType.REGULAR);
                    break;
                case 2:
                    TaskManager.printAll();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 555:
                    TaskManager.printDebug();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Такой команды нет, повторите ввод");
                    break;
            }
            if(menuInInt==0) {
                System.out.println("Работа с программой завершена");
                break;
            }
        }
    }
}
