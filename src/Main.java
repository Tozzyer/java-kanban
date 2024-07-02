public class Main {

    public static void main(String[] args) {
        System.out.println("Testing..");
        InMemoryTaskManager master = (InMemoryTaskManager) Managers.getDefault();
        HistoryManager historian = Managers.getDefaultHistory();
        Task task1 = new Task("1", "проверка DONE", Status.NEW);
        Task task2 = new Task("2", "Не трогать", Status.NEW);
        Epic epic1 = new Epic("3", "проверка in progress, New после удаления всех сабов", Status.NEW);
        SubTask sub1 = new SubTask("6", "Sub 1", Status.NEW, 3);
        SubTask sub2 = new SubTask("7", "Sub 2", Status.NEW, 3);
        SubTask sub3 = new SubTask("8", "Sub 3", Status.NEW, 3);
        SubTask sub4 = new SubTask("9", "Sub 4", Status.NEW, 3);
        Epic epic2 = new Epic("4", "проверка DONE", Status.NEW);
        SubTask sub5 = new SubTask("10", "Заправить 98", Status.NEW, 4);
        SubTask sub6 = new SubTask("11", "Подать питание", Status.NEW, 4);
        SubTask sub7 = new SubTask("12", "Включить", Status.NEW, 4);
        SubTask sub8 = new SubTask("13", "Колесо 8", Status.NEW, 4);
        Epic epic3 = new Epic("5", "Пустой", Status.NEW);
        master.addTask(task1);//ID1
        //master.addTask(task2);//ID2
        master.addEpic(epic1);//ID3
        //master.addEpic(epic2);//ID4
        //master.addEpic(epic3);//ID5
        //master.addSub(sub1);//6
        //master.addSub(sub2);//7
//        master.addSub(sub3);//8
//        master.addSub(sub4);//9
//        master.addSub(sub5);//10
//        master.addSub(sub6);//11
//        master.addSub(sub7);//12
//        master.addSub(sub8);//13
//        master.getTask(1);
//        master.getEpic(3);
//        master.getSubtask(6);
//        master.getTask(1);
//        master.getEpic(3);
//        master.getSubtask(6);
//        master.getTask(1);
//        master.getEpic(3);
//        master.getSubtask(6);

        System.out.println(master.getAllTasks());
        System.out.println(master.getAllEpics());
        System.out.println(master.getAllSubs());
//        System.out.println("====EPIC STATUS CHECK====");
//        task1.setStatus(Status.DONE);
//        task1.setContent("Изменение состояния");
//        master.updateTask(task1);
//        sub1.setStatus(Status.IN_PROGRESS);
//        master.updateSubTask(sub1);
//        sub2.setStatus(Status.DONE);
//        master.updateSubTask(sub2);
//        sub5.setStatus(Status.DONE);
//        master.updateSubTask(sub5);
//        sub6.setStatus(Status.DONE);
//        master.updateSubTask(sub6);
//        sub7.setStatus(Status.DONE);
//        master.updateSubTask(sub7);
//        sub8.setStatus(Status.DONE);
//        master.updateSubTask(sub8);
//        master.removeSubTask(13);
//
//        System.out.println("History");
//        System.out.println(master.historian.getHistory());
//        System.out.println("History end");
//        System.out.println(master.getAllTasks());
//        System.out.println(master.getAllEpics());
//        System.out.println(master.getAllSubs());
//        System.out.println("ТЕСТ НА УДАЛЕНИЕ ОДОГО САБТАСКА (Удален id7)");
//        master.removeSubTask(7);
//        System.out.println(master.getAllSubs());
//        System.out.println("ТЕСТ НА УДАЛЕНИЕ ВСЕХ САБТАСКОВ В ЭПИКЕ 3. СТАТУС ID3 ДОЛЖЕН СМЕНИТЬСЯ НА NEW");
//
//        master.removeSubTask(6);
//        master.removeSubTask(8);
//        master.removeSubTask(9);
//        System.out.println(master.getAllEpics());
//
//        System.out.println("Удаляем эпик целиком, проверяем сабы");
//        System.out.println("До:");
//        System.out.println("Эпик");
//        System.out.println(master.getAllEpics());
//        System.out.println("Саб");
//        System.out.println(master.getAllSubs());
//
//        System.out.println("После: (удалён эпик id 4, сабов не должно остаться)");
//
//        master.removeEpic(4);
//        System.out.println("Эпик (должны остаться id 3, 5)");
//        System.out.println(master.getAllEpics());
//        System.out.println("Саб (должен ничего не вывести)");
//        System.out.println(master.getAllSubs());
//        master.getTask(1);
//        master.getEpic(3);
//        master.getSubtask(6);
//        master.getTask(1);
//        master.getEpic(3);
//        master.getSubtask(6);
//        master.getTask(1);
//        master.getEpic(3);
//        master.getSubtask(6);
//        master.getTask(1);
//        master.getEpic(3);
//        master.getSubtask(6);
//        master.getTask(1);
//        master.getEpic(3);
//        master.getSubtask(6);
//        master.getTask(1);
//        master.getEpic(3);
//        master.getSubtask(6);
//        System.out.println("History");
//        System.out.println(master.historian.getHistory());
    }
}
