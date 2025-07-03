package org.example.Controller;

import org.example.service.StudentService;
import org.example.service.StudentServiceFactory;
import java.io.Console;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {


    public static int options() {
        int option = 0;
        try {
            Console console = System.console();
            if (console != null) {
                console.writer().println("Student Management");
            } else {
                System.out.println("Student Management");
            }
            System.out.println("\n 1. Add Student \n 2. Show All Student \n 3. Update student \n 4. Delete Student \n 5. Search Student \n 6. Exit");
            System.out.println("\n What would you like to do");
            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Please enter only between 1 to 6.");
            return 0; // Return 0 to continue the loop
        }
        return option;
    }


    public static void main(String[] args) {
        int opt = 0;

        StudentService studentService = StudentServiceFactory.createStudentService();

        System.out.println("Welcome to Student Management System!");

        while (opt != 6) {
            opt = options();

            switch (opt) {
                case 1:
                    studentService.add();
                    break;

                case 2:
                    studentService.showAll();
                    break;

                case 3:
                    studentService.update();
                    break;

                case 4:
                    studentService.delete();
                    break;

                case 5:
                    studentService.search();
                    break;

                case 6:
                    System.out.println("Exiting... Thank you for using Student Management System!");
                    break;

                default:
                    System.out.println("Please enter a valid option (1-6)");
                    break;
            }
        }
    }
}