public class Manager {
    public static HistoryManager getDefaultHistoryManager() {
        return new InMemoryHistoryManager(); // Используем конструктор без аргументов
    }

    public static TaskManager getDefaultTaskManager() {
        HistoryManager historyManager = getDefaultHistoryManager();
        return new InMemoryTaskManager(historyManager);
    }
}