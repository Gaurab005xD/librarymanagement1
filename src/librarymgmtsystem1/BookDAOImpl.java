package librarymgmtsystem1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class BookDAOImpl implements BookDAO {

    @Override
    public void insert(Book book) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "INSERT INTO books(title, author, available) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setBoolean(3, book.isAvailable());

            ps.executeUpdate();

            System.out.println("✔ Book successfully added.");

        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    @Override
    public List<Book> fetchAll() {
        List<Book> books = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT * FROM books";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                Book b = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("available")
                );
                books.add(b);
            }

        } catch (Exception e) {
            System.out.println("Error fetching books: " + e.getMessage());
        }

        return books;
    }

    @Override
    public void update(Book book) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {

            // Check if book exists
            String checkSql = "SELECT * FROM books WHERE id=?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, book.getId());

            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                throw new BookNotFoundException("No book found with ID: " + book.getId());
            }

            // Update book
            String updateSql = "UPDATE books SET title=?, author=?, available=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(updateSql);

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setBoolean(3, book.isAvailable());
            ps.setInt(4, book.getId());

            ps.executeUpdate();

            System.out.println("✔ Book updated successfully.");
        }
    }

    @Override
    public void remove(int id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {

            // Check if book exists
            String checkSql = "SELECT * FROM books WHERE id=?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, id);

            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                throw new BookNotFoundException("Book ID not found.");
            }

            // Delete book
            String deleteSql = "DELETE FROM books WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(deleteSql);
            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("✔ Book deleted successfully.");
        }
    }
}