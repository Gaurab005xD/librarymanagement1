package librarymgmtsystem1;

import java.util.List;

public interface BookDAO {
    void insert(Book book);
    List<Book> fetchAll();
    void update(Book book) throws Exception;
    void remove(int id) throws Exception;
}