package librarymgmtsystem1;


import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
    	new LibraryGUI();
        Scanner sc = new Scanner(System.in);
        BookDAO dao = new BookDAOImpl();

        while (true) {
            System.out.println("\n===== Library Menu =====");
            System.out.println("1. Insert Book");
            System.out.println("2. Display All Books");
            System.out.println("3. Modify Book");
            System.out.println("4. Remove Book");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter title: ");
                        String title = sc.nextLine();

                        if (title.isEmpty()) {
                            System.out.println("Title cannot be empty!");
                            break;
                        }

                        System.out.print("Enter author: ");
                        String author = sc.nextLine();

                        dao.insert(new Book(0, title, author, true));
                        break;

                    case 2:
                        List<Book> books = dao.fetchAll();
                        for (Book b : books) {
                            System.out.println(b.getId() + " | " + b.getTitle() + " | " + b.getAuthor());
                        }
                        break;

                    case 3:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("New title: ");
                        String newTitle = sc.nextLine();

                        System.out.print("New author: ");
                        String newAuthor = sc.nextLine();

                        dao.update(new Book(id, newTitle, newAuthor, true));
                        break;

                    case 4:
                        System.out.print("Enter ID: ");
                        int deleteId = sc.nextInt();

                        dao.remove(deleteId);
                        break;

                    case 5:
                        System.out.println("Exiting program...");
                        System.exit(0);
                }

            } catch (BookNotFoundException e) {
                System.out.println("⚠ " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error occurred.");
            }
        }
    }
}