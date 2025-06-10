import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentManagementGUI extends JFrame {
    private JTextField idField, nameField, ageField, gradeField;
    private JTextArea outputArea;

    public StudentManagementGUI() {
        setTitle("Student Management System");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        formPanel.add(ageField);

        formPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        formPanel.add(gradeField);

        add(formPanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add");
        JButton viewBtn = new JButton("View All");
        JButton searchBtn = new JButton("Search");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, BorderLayout.CENTER);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        // Button Actions
        addBtn.addActionListener(e -> addStudent());
        viewBtn.addActionListener(e -> viewAllStudents());
        searchBtn.addActionListener(e -> searchStudent());
        updateBtn.addActionListener(e -> updateStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
    }

    private void addStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String grade = gradeField.getText();
            Student student = new Student(id, name, age, grade);
            StudentFileManager.addStudent(student);
            outputArea.setText("Student added successfully!\n");
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid input. Please check ID and Age.\n");
        }
    }

    private void viewAllStudents() {
        List<Student> students = StudentFileManager.getAllStudents();
        StringBuilder sb = new StringBuilder("All Students:\n");
        for (Student s : students) {
            sb.append("ID: ").append(s.getId()).append(", Name: ").append(s.getName())
                    .append(", Age: ").append(s.getAge()).append(", Grade: ").append(s.getGrade()).append("\n");
        }
        outputArea.setText(sb.toString());
    }

    private void searchStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            Student s = StudentFileManager.searchStudent(id);
            if (s != null) {
                outputArea.setText("Found: " + s.getName() + ", Age: " + s.getAge() + ", Grade: " + s.getGrade() + "\n");
            } else {
                outputArea.setText("Student not found.\n");
            }
        } catch (NumberFormatException e) {
            outputArea.setText("Please enter a valid ID.\n");
        }
    }

    private void updateStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String grade = gradeField.getText();
            Student updated = new Student(id, name, age, grade);
            boolean success = StudentFileManager.updateStudent(updated);
            outputArea.setText(success ? "Student updated.\n" : "Student not found.\n");
        } catch (NumberFormatException e) {
            outputArea.setText("Invalid input.\n");
        }
    }

    private void deleteStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            boolean success = StudentFileManager.deleteStudent(id);
            outputArea.setText(success ? "Student deleted.\n" : "Student not found.\n");
        } catch (NumberFormatException e) {
            outputArea.setText("Please enter a valid ID.\n");
        }
    }


    // Main class
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentManagementGUI().setVisible(true);
        });
    }
}
