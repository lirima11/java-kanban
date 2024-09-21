public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Task task1 = new Task(manager.generateId(), "Закупить материалы", "Купить материалы для проекта", Status.NEW);
        Task task2 = new Task(manager.generateId(), "Завершить отчёт", "Подготовить финансовый отчёт", Status.IN_PROGRESS);
        manager.addTask(task1);
        manager.addTask(task2);

        Epic epic1 = new Epic(manager.generateId(), "Переезд", "Переехать в новый офис");
        Subtask subtask1 = new Subtask(manager.generateId(), "Упаковка вещей", "Упаковать офисные вещи", Status.NEW, epic1);
        Subtask subtask2 = new Subtask(manager.generateId(), "Перевозка", "Перевезти вещи", Status.NEW, epic1);
        manager.addEpic(epic1);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        System.out.println("Все задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        subtask1.setStatus(Status.DONE);
        epic1.updateStatus();
        System.out.println("Обновлённый эпик: " + epic1);

        manager.deleteTaskById(task1.getId());
        System.out.println("После удаления задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
    }
}
