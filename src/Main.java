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

                            TaskManager.addTask(menuInStr, TaskType.REGULAR);
                            break;
                        case 2:
                            System.out.println("Введите ЭПИЧЕСКУЮ задачу:");
                            menuInStr = sc.nextLine();

                            TaskManager.addTask(menuInStr, TaskType.EPIC);
                            break;
                        case 3:
                            TaskManager.printEpic();
                            System.out.println("Введите ID ЭПИЧЕСКОЙ задачи:");
                            menuInInt = sc.nextInt();
                            sc.nextLine();
                            if (TaskManager.keyList.containsKey(menuInInt)) {
                                Task support = TaskManager.dataBase.get(TaskManager.keyList.get(menuInInt));
                                if (support.getType() == TaskType.EPIC) {
                                    System.out.println("Введите подзадачу:");
                                    menuInStr = sc.nextLine();
                                    support.addSubTask(menuInStr, TaskType.SUBTASK);
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
                    TaskManager.printAll();
                    break;
                case 3:
                    System.out.println("Введите ID задачи:");
                    menuInInt = sc.nextInt();
                    sc.nextLine();
                    TaskManager.inProgress(menuInInt);
                    break;
                case 4:
                    System.out.println("Введите ID задачи:");
                    menuInInt = sc.nextInt();
                    sc.nextLine();
                    TaskManager.complete(menuInInt);
                    break;
                case 5:
                    System.out.println("Введите ID задачи для удаления:");
                    menuInInt = sc.nextInt();
                    sc.nextLine();
                    TaskManager.deletePos(menuInInt);
                    break;
                case 555:
                    TaskManager.printDebug();
                    break;
                case 6:
                    TaskManager.eraseAll();
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
