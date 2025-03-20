package com.hibali.IT_Library.utilities;

import java.sql.ResultSet;

import com.hibali.IT_Library.enums.BookLanguages;
import com.hibali.IT_Library.enums.BookStatus;
import com.hibali.IT_Library.models.classes.Author;
import com.hibali.IT_Library.models.classes.Book;
import com.hibali.IT_Library.models.classes.BookProgrammingLanguage;
import com.hibali.IT_Library.models.classes.BookTopic;
import com.hibali.IT_Library.models.classes.ProgrammingLanguage;
import com.hibali.IT_Library.models.classes.Topic;

public class ResultSetMaper {

    public static <T> T mapToModel(ResultSet result, Class<T> modelClass) throws RuntimeException {
        try {
            T model = modelClass.getDeclaredConstructor().newInstance();

            if (model instanceof Topic) {
                Topic topic = (Topic) model;
                topic.setId(result.getInt("topic_id"));
                topic.setName(result.getString("topic_name"));
                topic.setdeleted(result.getBoolean("topic_deleted"));
                topic.setCreated_at(result.getTimestamp("created_at"));
                topic.setUpdated_at(result.getTimestamp("updated_at"));
            }
            else if (model instanceof ProgrammingLanguage) {
                ProgrammingLanguage programmingLanguage = (ProgrammingLanguage) model;
                programmingLanguage.setId(result.getInt("prog_lang_id"));
                programmingLanguage.setName(result.getString("prog_lang_name"));
                programmingLanguage.setdeleted(result.getBoolean("prog_lang_deleted"));
                programmingLanguage.setCreated_at(result.getTimestamp("created_at"));
                programmingLanguage.setUpdated_at(result.getTimestamp("updated_at"));
            }
            else if(model instanceof Author){
                Author author = (Author) model;
                author.setId(result.getInt("author_id"));
                author.setName(result.getString("author_name"));
                author.setLink(result.getString("author_link"));
                author.setdeleted(result.getBoolean("author_deleted"));
                author.setCreated_at(result.getTimestamp("created_at"));
                author.setUpdated_at(result.getTimestamp("updated_at"));
            }
            else if(model instanceof Book){
                Book book = (Book) model;
                book.setId(result.getInt("book_id"));
                book.setAuthorId(result.getInt("author_id"));
                book.setName(result.getString("book_name"));
                book.setPublishDate(result.getDate("book_publish_date"));
                book.setDescription(result.getString("book_description"));
                book.setBookLanguage(BookLanguages.valueOf(result.getString("book_language").toUpperCase()));
                book.setFileUrI(result.getString("book_file_url"));
                book.setEdition(result.getInt("book_edition"));
                book.setNbrDownloads(result.getInt("book_nbr_downloads"));
                book.setStatus(BookStatus.valueOf(result.getString("book_status").toUpperCase()));
                book.setdeleted(result.getBoolean("book_deleted"));
                book.setCreated_at(result.getTimestamp("created_at"));
                book.setUpdated_at(result.getTimestamp("updated_at"));
            }
            else if (model instanceof BookTopic){
                BookTopic bookTopic = (BookTopic) model;
                bookTopic.setId(result.getInt("book_topic_id"));
                bookTopic.setBook_id(result.getInt("book_id"));
                bookTopic.setTopic_id(result.getInt("topic_id"));
                bookTopic.setCreated_at(result.getTimestamp("created_at"));
                bookTopic.setUpdated_at(result.getTimestamp("updated_at"));
            }
            else if (model instanceof BookProgrammingLanguage){
                BookProgrammingLanguage bookProgLang = (BookProgrammingLanguage) model;
                bookProgLang.setId(result.getInt("book_prog_lang_id"));
                bookProgLang.setBookId(result.getInt("book_id"));
                bookProgLang.setProgrammingLanguageId(result.getInt("prog_lang_id"));
                bookProgLang.setCreated_at(result.getTimestamp("created_at"));
                bookProgLang.setUpdated_at(result.getTimestamp("updated_at"));
            }
            return model;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping", e);
        }
    }
}
