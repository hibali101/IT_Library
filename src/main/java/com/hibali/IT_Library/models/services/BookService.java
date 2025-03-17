package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.Dao.BookDao;
import com.hibali.IT_Library.models.classes.Book;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.utilities.ExecuteInTransaction;
import com.hibali.IT_Library.utilities.TransactionsResultsMessages;

public class BookService implements IService<Book,Integer> {
    private final DbConnection dbConnection;
    private final BookDao dao;

    public BookService(DbConnection dbConnection, BookDao dao) {
        this.dbConnection = dbConnection;
        this.dao = dao;
    }

    public Optional<Book> add(Book book) throws FieldRequiredException, FieldUniqueException, BuisnessRulesException {
        if (book.getName() == null || book.getAuthorId() <= 0 || book.getFileUrI() == null) {
            throw new FieldRequiredException(new String[] { "book_name", "author_id", "book_file_url" });
        }
        return ExecuteInTransaction.execute(cnx -> {
            try {
                checkEdition(book, cnx);
            } catch (BuisnessRulesException e) {
                e.printStackTrace();
            }
            dao.insert(book, cnx);
            TransactionsResultsMessages.insertSuccess(book);
            return book;
        }, dbConnection);
    }

    public Optional<ArrayList<Book>> getAll() {
        try (Connection cnx = dbConnection.create()) {
            return dao.findAll(cnx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Book> getById(Integer id) {
        try (Connection cnx = dbConnection.create()) {
            return dao.findById(id, cnx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<Book> update(Book book)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException {
        if (book.getId() <= 0) {
            throw new FieldRequiredException("book_id");
        }
        return ExecuteInTransaction.execute(cnx -> {
            try {
                checkEdition(book, cnx);
            } catch (BuisnessRulesException e) {
                e.printStackTrace();
            }
            dao.update(book, cnx);
            TransactionsResultsMessages.updateSuccess(book);
            return book;
        }, dbConnection);

    }

    public Optional<Book> delete(Book book) throws FieldRequiredException {
        if (book.getId() <= 0) {
            throw new FieldRequiredException("book_id");
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.delete(book, cnx);
            TransactionsResultsMessages.deleteSuccess(book);
            return book;
        }, dbConnection);
    }

    private void checkEdition(Book book, Connection cnx) throws BuisnessRulesException, SQLException {
        // check if a book with the same name exists, then it must have a recent edition
        ArrayList<Book> sameNameBooks = dao.findByName(book.getName(), cnx);
        if (sameNameBooks.size() > 0) {
            for (Book bk : sameNameBooks) {
                if (bk.getEdition() == book.getEdition()) {
                    throw new BuisnessRulesException(
                            "A book with the same name and same edition exists, you must provide a different edition!");
                }
                // cehck if the books have the name and edition then the publish date of the
                // latest edition must be superior
                if (book.getPublishDate() != null && bk.getPublishDate() != null) {
                    if ((book.getEdition() > bk.getEdition()
                            && book.getPublishDate().compareTo(bk.getPublishDate()) <= 0)
                            || (book.getEdition() < bk.getEdition()
                                    && book.getPublishDate().compareTo(bk.getPublishDate()) >= 0)) {
                        throw new BuisnessRulesException(
                                "recent edition publish date must be superior to an older edition publish date");
                    }
                }
            }
        }
    }
}
