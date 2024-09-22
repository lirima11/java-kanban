public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        Task task1 = manager.createTask("Task 1", "Description 1");
        Epic epic1 = manager.createEpic("Epic 1", "Description Epic 1");
        Subtask subtask1 = manager.createSubtask("Subtask 1", "Description Subtask 1", epic1.getId());
        Subtask subtask2 = manager.createSubtask("Subtask 2", "Description Subtask 2", epic1.getId());

        System.out.println("Tasks:");
        System.out.println(manager.getAllTasks());

        System.out.println("Epics:");
        System.out.println(manager.getAllEpics());

        System.out.println("Subtasks:");
        System.out.println(manager.getAllSubtasks());

        // Изменение статуса подзадачи
        subtask1.setStatus(TaskStatus.DONE);
        manager.updateSubtask(subtask1);

        System.out.println("Updated Epic status:");
        System.out.println(epic1.getStatus());
    }
}
