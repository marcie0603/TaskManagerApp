import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class TaskManager {
    static class Task{
        String description;
        boolean isDone;

        Task(String description){
            this.description = description;
            this.isDone = false;
        }

        @Override
        public String toString(){
            return (isDone ? "[✓] " : "[ ] ") + description;
        }
    }

    static ArrayList<Task> tasks = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    static void loadTasksFromFile() {
        File file = new File("tasks.txt");
        if(!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                boolean isDone = line.startsWith("[✓]");
                String description = line.substring(3).trim();
                Task task = new Task(description);
                task.isDone = isDone;
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("Could not load tasks");
        }
    }

    public static void main(String[] args){
        loadTasksFromFile();
        while (true) {
            System.out.println("\n--- Task Manager ---");
            System.out.println("1. Add Task");
            System.out.println("2. View Task");
            System.out.println("3. Mark Task as Done");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.println("Choose an option: ");

            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1 -> addTask();
                case 2 -> viewTasks();
                case 3 -> markTaskDone();
                case 4 -> deleteTask();
                case 5 -> {
                    saveTaskToFile();
                    System.out.println("Tasks saved. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    static void addTask(){
        System.out.println("Enter task description: ");
        String desc = scanner.nextLine();
        tasks.add(new Task(desc));
        System.out.println("Task added");
    }

    static void viewTasks(){
        if (tasks.isEmpty()) {
            System.out.println("No tasks found");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i+1) + ". " + tasks.get(i));
        }
    }

    static void markTaskDone(){
        viewTasks();
        System.out.println("Enter task number to mark as done: ");
        try{
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < tasks.size() ){
                tasks.get(index).isDone = true;
                System.out.println("Task marked as done.");
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number. ");
        }
    }

    static void deleteTask(){
        viewTasks();
        System.out.println("Enter task number to delete: ");
        try{
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < tasks.size() ){
                tasks.remove(index);
                System.out.println("Task deleted.");
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number. ");
        }
    }

    static void saveTaskToFile() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks){
                writer.write(task.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save tasks.");
        }
    }
}
