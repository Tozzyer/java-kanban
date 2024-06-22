public class PrintMenu {
    static void mainMenu(){
        System.out.println("Выберите действие:");
        System.out.println("1. Создать новую задачу;");
        System.out.println("2. Посмотреть список задач;");
        System.out.println("3. Редактировать задачу;");
        System.out.println("4. Очистить список задач;");
        System.out.println("0. Выход.");
        System.out.println("555. Отладка");
    }
    static void createMenu(){
        System.out.println("Выберите действие:");
        System.out.println("1. Создать задачу;");
        System.out.println("2. Создать эпик;");
        System.out.println("3. Создать подзадачу;");
        System.out.println("0. Выйти в меню.");
    }
    static void viewMenu(){
        System.out.println("Выберите действие:");
        System.out.println("1. Посмотреть все задачи;");
        System.out.println("2. Посмотреть новые задачи");
        System.out.println("3. Посмотреть активные задачи;");
        System.out.println("4. Посмотреть выполненные задачи;");
        System.out.println("0. Выйти в меню.");
    }
}
