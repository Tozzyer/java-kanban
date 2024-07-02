import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @org.junit.jupiter.api.Test
    void addSubTask() {
        InMemoryTaskManager master = (InMemoryTaskManager) Managers.getDefault();
        Epic epic1 = new Epic("Name", "Content", Status.NEW);
        master.addEpic(epic1);//ID3

        //    epic1.addSubTask(epic1);//неверный тип данных


    }

    @org.junit.jupiter.api.Test
    void statusUpdate() {
    }

    @org.junit.jupiter.api.Test
    void clearSub() {
    }

    @org.junit.jupiter.api.Test
    void getEpicSubs() {
    }
}