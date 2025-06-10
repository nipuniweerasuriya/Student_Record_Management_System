import java.io.*;
import java.util.*;

public class StudentFileManager {
    private static final String FILE_NAME = "students.txt";

    public static void addStudent(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(student.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(Student.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Student searchStudent(int id) {
        for (Student s : getAllStudents()) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public static boolean updateStudent(Student updated) {
        List<Student> list = getAllStudents();
        boolean found = false;
        for (Student s : list) {
            if (s.getId() == updated.getId()) {
                s.setName(updated.getName());
                s.setAge(updated.getAge());
                s.setGrade(updated.getGrade());
                found = true;
                break;
            }
        }
        if (found) saveAll(list);
        return found;
    }

    public static boolean deleteStudent(int id) {
        List<Student> list = getAllStudents();
        boolean removed = list.removeIf(s -> s.getId() == id);
        if (removed) saveAll(list);
        return removed;
    }

    private static void saveAll(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.write(s.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
