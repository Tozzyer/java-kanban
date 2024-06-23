import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); //создаём сканнер
        int menuInInt; // переменная для входных целочисленных данных ввода
        String menuInStr; // переменная для входных строковых данных ввода
        TaskManager master = new TaskManager(); // создаём управителя, который всем управляет и хранит

        while (true) {
            PrintMenu.mainMenu();
            menuInInt = sc.nextInt();
            sc.nextLine();
            switch (menuInInt) {
                case 1:
                    PrintMenu.createMenu();
                    menuInInt = sc.nextInt();
                    sc.nextLine();
                    switch (menuInInt) {
                        case 1:
                            System.out.println("Введите обычную задачу:");
                            menuInStr = sc.nextLine();

                            master.addTask(menuInStr, TaskType.REGULAR);
                            break;
                        case 2:
                            System.out.println("Введите ЭПИЧЕСКУЮ задачу:");
                            menuInStr = sc.nextLine();

                            master.addTask(menuInStr, TaskType.EPIC);
                            break;
                        case 3:
                            master.printEpic();
                            System.out.println("Введите ID ЭПИЧЕСКОЙ задачи:");
                            menuInInt = sc.nextInt();
                            sc.nextLine();
                            if (master.keyList.containsKey(menuInInt)) {
                                Task support = master.dataBase.get(master.keyList.get(menuInInt));
                                if (support.getType() == TaskType.EPIC) {
                                    System.out.println("Введите подзадачу:");
                                    menuInStr = sc.nextLine();
                                    support.addSubTask(menuInStr, TaskType.SUBTASK, master);
                                } else {
                                    System.out.println("Это не эпик-задача. Создайте эпическую задачу.");
                                }
                            } else {
                                System.out.println("Такой ЭПИК задачи не существует..");
                            }
                            break;
                        default:
                            System.out.println("Такой команды нет..");
                    }

                    break;
                case 2:
                    master.printAll();
                    break;
                case 3:
                    System.out.println("Введите ID задачи:");
                    menuInInt = sc.nextInt();
                    sc.nextLine();
                    master.inProgress(menuInInt);
                    break;
                case 4:
                    System.out.println("Введите ID задачи:");
                    menuInInt = sc.nextInt();
                    sc.nextLine();
                    master.complete(menuInInt);
                    break;
                case 5:
                    System.out.println("Введите ID задачи для удаления:");
                    menuInInt = sc.nextInt();
                    sc.nextLine();
                    master.deletePos(menuInInt);
                    break;
                case 555:
                    master.printDebug();
                    break;
                case 6:
                    master.eraseAll();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Такой команды нет, повторите ввод");
                    break;
            }
            if (menuInInt == 0) {
                System.out.println("Работа с программой завершена");
                break;
            }
        }
    }
}
