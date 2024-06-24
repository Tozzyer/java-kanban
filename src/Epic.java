import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    HashMap<Integer, SubTask> epicSubList = new HashMap<>();

    Epic(String taskName, String content,  Status status) {
        super(taskName, content, status);
    }

    void addSubTask(SubTask subTask) {
        epicSubList.put(subTask.getId(), subTask);
    }
    void printAllSubList(){
        for (SubTask subTask : epicSubList.values()) {
            System.out.println("Obj: " + subTask);
        }
    }
    void statusUpdate (){
        if(epicSubList.isEmpty()){
            setStatus(Status.NEW);
            return;
        }else{
            for (SubTask subTask : epicSubList.values()){
                if(subTask.getStatus()==Status.IN_PROGRESS){//Хотя бы один In Progress
                    setStatus(Status.IN_PROGRESS);
                    return;
                } else if (subTask.getStatus()==Status.NEW){
                   return;
                } else {
                    setStatus(Status.DONE);
                }
            }
        }

    }
    void clearSub(){
        epicSubList.clear();
        setStatus(Status.NEW);
    }
}
