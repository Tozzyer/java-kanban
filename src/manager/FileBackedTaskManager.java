package manager;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Integer.parseInt;


public class FileBackedTaskManager extends InMemoryTaskManager {
    //Новые поля
    public Path taskFile;


    //Конструктор для тестов
    public FileBackedTaskManager(String test) {

        try {
            createTempFile();
        } catch (ManagerSaveException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public FileBackedTaskManager() {
        this.taskFile = Paths.get("csvTaskFile.csv");
        try {
            createFile();
        } catch (ManagerSaveException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Начало переписанных методов
    @Override
    public void addTask(Task task) {
        taskId++;
        tasks.put(taskId, task);
        task.setId(taskId);
        save();
    }

    @Override
    public void addEpic(Epic task) {
        taskId++;
        epics.put(taskId, task);
        task.setId(taskId);
        save();
    }

    @Override
    public void addSub(SubTask task) {
        taskId++;
        subs.put(taskId, task);
        task.setId(taskId);
        epics.get(task.getMasterId()).addSubTask(task);
        epics.get(task.getMasterId()).statusUpdate();
        save();
    }

    //Удаление по идентификатору
    @Override
    public void removeTask(Integer id) {
        tasks.remove(id);
        save();
    }

    //Удаление всех задач
    @Override
    public void eraseTaskHashMap() {
        tasks.clear();
        save();
    }

    //Обновление задачи
    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
        save();
    }

    //Удаление всех задач
    @Override
    public void eraseEpicHashMap() {
        eraseSubHashMap();
        epics.clear();
        save();
    }

    //Обновление задачи
    @Override
    public void updateEpic(Epic task) {
        epics.put(task.getId(), task);
        save();
    }

    //Удаление по идентификатору
    @Override
    public void removeEpic(Integer id) {
        for (Integer key : epics.get(id).getEpicSubs().keySet()) {
            subs.remove(key);
        }
        epics.remove(id);
        save();
    }

    //Удаление всех подзадач (без удаления эпиков. Эпики выставляются в статус NEW, так как подзадач нет.)
    @Override
    public void eraseSubHashMap() {
        subs.clear();
        for (Epic epic : epics.values()) {
            epic.clearSub();
            epic.statusUpdate();
        }
        save();
    }

    //Обновление задачи
    @Override
    public void updateSubTask(SubTask task) {
        subs.put(task.getId(), task);
        epics.get(task.getMasterId()).addSubTask(task);
        epics.get(task.getMasterId()).statusUpdate();
        save();
    }

    //Удаление по идентификатору
    @Override
    public void removeSubTask(Integer id) {
        subs.remove(id);
        for (Epic epic : epics.values()) {
            if (epic.getEpicSubs().containsKey(id)) {
                epic.getEpicSubs().remove(id);
                epic.statusUpdate();
            }
        }
        save();
    }

    //ДОПОЛНИТЕЛЬНЫЕ ОПЕРАЦИОННЫЕ МЕТОДЫ
    //Метод автосохранения. Содержимое мапы uniMap переносится в файл CSV
    private void save() {
        HashMap<Integer, Task> uniMap = createUniMap();
        StringBuilder sb = new StringBuilder("id,type,name,status,description,epic\n");
        for (int i = 1; i <= taskId; i++) {
            if (uniMap.containsKey(i)) {
                sb.append(lineFormater(uniMap.get(i)) + "\n");
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(taskFile)))) {
            writer.write(String.valueOf(sb));
        } catch (IOException e) {
            System.out.println("IOI save exception");
        }
    }

    //Метод формирует хэш-мапу для автосохранения в файл
    private HashMap<Integer, Task> createUniMap() {
        HashMap<Integer, Task> uniMap = new HashMap<>();
        for (int i = 1; i <= taskId; i++) {
            if (tasks.containsKey(i)) {
                uniMap.put(i, tasks.get(i));
            } else if (epics.containsKey(i)) {
                uniMap.put(i, epics.get(i));
            } else if (subs.containsKey(i)) {
                uniMap.put(i, subs.get(i));
            }
        }
        return uniMap;
    }

    //Метод формирует линию для записи формата id,type,name,status,description,epic
    private String lineFormater(Task task) {

        if (task instanceof SubTask) {
            SubTask subTask = (SubTask) task;

            return subTask.getId() + "," + getTaskType(task) + "," + subTask.getTaskName() + "," + subTask.getStatus() + "," + subTask.getContent() + "," + subTask.getMasterId();
        }
        return task.getId() + "," + getTaskType(task) + "," + task.getTaskName() + "," + task.getStatus() + "," + task.getContent();
    }

    //Метод определяет тип задачи и возвращает в виде строки
    private String getTaskType(Task task) {
        Class<?> o = task.getClass();
        return o.getSimpleName();
    }

    //Метод инициализирует содержимое CSV файла в память программы
    private void loadFromFile() {
        ArrayList<String> data = new ArrayList<>();
        try {
            data = (ArrayList<String>) Files.readAllLines(taskFile);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении из файла");
        }
        data.removeFirst();
        assert data != null;
        for (String line : data) {
            separateLines(line);
        }

    }

    //Вспомогателльный метод readFromFile. Метод бьёт на строки id,type,name,status,description,epic
    // 0 - ID, 1 - TYPE, 2 - NAME, 3 - STATUS, 4 - DESCRIPTION, 5 - EPIC
    private void separateLines(String initialString) {
        String[] lines = initialString.split(",");
        int resId = parseInt(lines[0]);
        String resType = lines[1];
        String resTaskName = lines[2];
        Status resTaskStatus = Status.valueOf(lines[3]);
        String resContent = lines[4];
        int resEpicNo = 0;
        if (resType.equals("SubTask")) resEpicNo = parseInt(lines[5]);
        switch (resType) {
            case "Task":
                Task task = new Task(resTaskName, resContent, resTaskStatus);
                taskId = resId - 1;
                addTask(task);
                task.setId(resId);
                taskId = resId;
                break;
            case "Epic":
                Epic epic = new Epic(resTaskName, resContent, resTaskStatus);
                taskId = resId - 1;
                addEpic(epic);
                epic.setId(resId);
                taskId = resId;
                break;
            case "SubTask":
                SubTask subTask = new SubTask(resTaskName, resContent, resTaskStatus, resEpicNo);
                taskId = resId - 1;
                addSub(subTask);
                subTask.setId(resId);
                taskId = resId;
                break;
        }
    }

    private void eraseFile() {
        try {
            Files.newBufferedWriter(taskFile).close();
        } catch (IOException e) {
            System.out.println("Ошибка при удалении данных");
        }

    }

    private void createTempFile() throws ManagerSaveException {
        try {
            taskFile = Files.createTempFile("testFile", ".csv");
            taskFile.toFile().deleteOnExit();
        } catch (IOException ex) {
            throw new ManagerSaveException("Ошибка при создании временного файла");
        }
    }

    private void createFile() throws ManagerSaveException {

        try {
            if (!Files.exists(taskFile)) {
                Files.createFile(taskFile);
            }
        } catch (IOException ex) {
            throw new ManagerSaveException("Ошибка при создании постоянного CSV файла");
        }
    }

}
