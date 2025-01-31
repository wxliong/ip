package batman.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import batman.tasks.Deadline;
import batman.tasks.Event;
import batman.tasks.Task;
import batman.tasks.Todo;

public class Storage {

    private static Path path;

    /**
     * Stores and loads tasks in specified file.
     *
     * @param filePath Path to indicate where the
     *                 storage of the tasks will be located at.
     */
    public Storage(String filePath) {
        String dataPath = System.getProperty("user.dir") + filePath;
        path = Paths.get(dataPath);
        createFile();
    }

    /**
     * Creates file to store tasks.
     */
    private static void createFile() {
        File dataDir = new File("./data");
        dataDir.mkdirs();
        try {
            Files.createFile(path);
            assert Files.exists(path) : "File does not exists";
            System.out.println("File has been created...");

        } catch (IOException e) {
            System.out.println("Loading content...");
        }
    }

    /**
     * Writes existing tasks to file.
     *
     * @param tasks An arrayList of existing tasks.
     */
    public void writeToFile(ArrayList<Task> tasks) throws IOException {
        Files.write(path, "".getBytes());
        for (Task t : tasks) {
            Files.write(path, t.appendToFile().getBytes(), StandardOpenOption.APPEND);
        }
    }

    /**
     * Returns tasks from specified file.
     *
     * @return ArrayList of existing tasks.
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            String[] item = line.split("\\|");
            switch (item[0]) {
            case "D":
                tasks.add(new Deadline(item[1].equals("1"), item[2], item[3]));
                break;
            case "E":
                tasks.add(new Event(item[1].equals("1"), item[2], item[3]));
                break;
            default:
                String date = item[3].split("Date Added: ")[1];
                tasks.add(new Todo(item[1].equals("1"), item[2], date));
            }
        }
        assert tasks != null;
        return tasks;
    }
}
