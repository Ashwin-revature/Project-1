package org.example.service;

import org.example.Model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.example.Controller.Main.options;


public class StudentServiceImpl implements StudentService {

    private  Scanner sc = new Scanner(System.in);
    private  List<Student> students = new ArrayList<>(List.of(
            new Student(1, "Alice", "CSE", 20),
            new Student(2, "Bob", "ECE", 21),
            new Student(3, "Charlie", "MECH", 22),
            new Student(4, "David", "CSE", 20),
            new Student(5, "Eve", "IT", 23)
    ));

    @Override
    public void add() {
        try {
            System.out.println("Available Students");
            students.forEach(System.out::println);
            int nextId = students.isEmpty() ? 1 : students.stream()
                    .mapToInt(Student::getId)
                    .max()
                    .getAsInt() + 1;

            System.out.println("Generated Student ID: " + nextId);
            String name;
            while (true) {
                System.out.print("Enter name: ");
                name = sc.nextLine().toUpperCase();
                if (name.matches("^[A-Z][A-Z .]*$")) break;
                else System.out.println("Invalid name! Only alphabets, spaces, and dots allowed.");
            }

            int age;
            while (true) {
                System.out.print("Enter age: ");
                String ageStr = sc.nextLine();
                try {
                    age = Integer.parseInt(ageStr);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid number for age.");
                }
            }

            String dept;
            List<String> validDepartments = List.of("CSE", "IT", "ECE", "EEE", "MECH", "CIVIL");
            while (true) {
                System.out.print("Enter department (CSE, IT, ECE, EEE, MECH, CIVIL): ");
                dept = sc.nextLine().toUpperCase();
                if (validDepartments.contains(dept)) break;
                else System.out.println("Invalid department! Try again.");
            }

            students.add(new Student(nextId, name, dept, age));
            System.out.println("Student added with ID: " + nextId);

        } catch (Exception e) {
            System.err.println("Something went wrong: " + e.getMessage());
        }
    }

    @Override
    public void showAll() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        students.forEach(System.out::println);
        System.out.println("\nClick on enter to exit");
        sc.nextLine();
    }

    @Override
    public void update() {
        try {
            if (students.isEmpty()) {
                System.out.println("No students available to update.");
                return;
            }

            students.forEach(System.out::println);
            int id;
            while (true) {
                System.out.print("Enter roll no: ");
                String idStr = sc.nextLine();
                try {
                    id = Integer.parseInt(idStr);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                }
            }

            int tempId = searchById(id);
            if (tempId == -1) {
                System.out.println("ID not found ... Enter valid ID");
                update();
            }

            Student s = students.get(tempId);
            System.out.print("New name (current: " + s.getName() + ") [press Enter to skip]: ");
            String nameInput = sc.nextLine().trim();
            if (!nameInput.isEmpty()) {
                String name = nameInput.toUpperCase();
                if (name.matches("^[A-Z][A-Z .]*$")) {
                    s.setName(name);
                } else {
                    System.out.println("Invalid name! Skipping name update.");
                }
            }

            System.out.print("New age (current: " + s.getAge() + ") [press Enter to skip]: ");
            String ageInput = sc.nextLine().trim();
            if (!ageInput.isEmpty()) {
                try {
                    int age = Integer.parseInt(ageInput);
                    s.setAge(age);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age! Skipping age update.");
                }
            }

            List<String> validDepartments = List.of("CSE", "IT", "ECE", "EEE", "MECH", "CIVIL");
            System.out.print("New department (current: " + s.getDepartment() + ") [press Enter to skip]: ");
            String deptInput = sc.nextLine().trim().toUpperCase();
            if (!deptInput.isEmpty()) {
                if (validDepartments.contains(deptInput)) {
                    s.setDepartment(deptInput);
                } else {
                    System.out.println("Invalid department! Skipping department update.");
                }
            }

            System.out.println("Student updated successfully.");

        } catch (Exception e) {
            System.err.println("Something went wrong: " + e.getMessage());
        }
    }

    @Override
    public void delete() {
        if (students.isEmpty()) {
            System.out.println("No students available to delete.");
            return;
        }
        students.forEach(System.out::println);
        int id;
        while (true) {
            System.out.print("Enter roll no: ");
            String idStr = sc.nextLine();
            try {
                id = Integer.parseInt(idStr);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }

        int tempId = searchById(id);
        if (tempId == -1) {
            System.out.println("ID not found ... Enter valid ID");
            delete();
        } else {
            students.remove(tempId);
            System.out.println("Student deleted!");
        }
    }

    @Override
    public void search() {
        try {
            if (students.isEmpty()) {
                System.out.println("No students available.");
                return;
            }

            System.out.println("Search by:\n1. ID\n2. Name\n3. Department\n4. Age");

            int choice;
            while (true) {
                System.out.print("Enter choice: ");
                String choiceStr = sc.nextLine();
                try {
                    choice = Integer.parseInt(choiceStr);
                    if (choice >= 1 && choice <= 4) break;
                    else System.out.println("Invalid choice! Enter a number between 1 and 4.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                }
            }

            List<Student> results = new ArrayList<>();

            switch (choice) {
                case 1 -> {
                    int id;
                    while (true) {
                        System.out.print("Enter ID: ");
                        String idStr = sc.nextLine();
                        try {
                            id = Integer.parseInt(idStr);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input! Please enter a valid number for ID.");
                        }
                    }
                    int finalId = id;
                    results = students.stream()
                            .filter(s -> s.getId() == finalId)
                            .collect(Collectors.toList());
                }
                case 2 -> {
                    String name;
                    while (true) {
                        System.out.print("Enter Name: ");
                        name = sc.nextLine().toUpperCase();
                        if (name.matches("^[A-Z][A-Z .]*$")) break;
                        else System.out.println("Invalid Format! Enter name ");
                    }
                    String finalName = name;
                    results = students.stream()
                            .filter(s -> s.getName().equalsIgnoreCase(finalName))
                            .collect(Collectors.toList());
                }
                case 3 -> {
                    List<String> validDepartments = List.of("CSE", "IT", "ECE", "EEE", "MECH", "CIVIL");
                    String department;
                    while (true) {
                        System.out.print("Enter Department (CSE, IT, ECE, EEE, MECH, CIVIL): ");
                        department = sc.nextLine().toUpperCase();
                        if (validDepartments.contains(department)) break;
                        else System.out.println("Invalid department! Try again.");
                    }
                    String finalDept = department;
                    results = students.stream()
                            .filter(s -> s.getDepartment().equalsIgnoreCase(finalDept))
                            .collect(Collectors.toList());
                }
                case 4 -> {
                    int age;
                    while (true) {
                        System.out.print("Enter Age: ");
                        String ageStr = sc.nextLine();
                        try {
                            age = Integer.parseInt(ageStr);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input! Please enter a valid number for Age.");
                        }
                    }
                    int finalAge = age;
                    results = students.stream()
                            .filter(s -> s.getAge() == finalAge)
                            .collect(Collectors.toList());
                }
            }

            if (results.isEmpty()) {
                System.out.println("No matching students found.");
                System.out.println("Do you want continue search\nYes-1\nNo-2");
                int opt;
                while (true) {
                    System.out.print("Enter options: ");
                    String optStr = sc.nextLine();
                    try {
                        opt = Integer.parseInt(optStr);
                        if (opt > 2) {
                            System.out.println("Invalid input! Please enter a valid number for options(1 or 2).");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please enter a valid number for options.");
                    }
                }
                switch (opt) {
                    case 1 -> search();
                    case 2 -> options();
                }
            } else {
                System.out.println("Matching students:");
                results.forEach(System.out::println);
            }

        } catch (Exception e) {
            System.err.println("Something went wrong: " + e.getMessage());
        }
    }

    @Override
    public int searchById(int id) {
        return IntStream.range(0, students.size())
                .filter(i -> students.get(i).getId() == id)
                .findFirst()
                .orElse(-1);
    }
}