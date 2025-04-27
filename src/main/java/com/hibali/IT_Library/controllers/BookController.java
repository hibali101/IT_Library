package com.hibali.IT_Library.controllers;

import java.sql.SQLException;
import java.util.Optional;

import com.hibali.IT_Library.models.classes.Book;
import com.hibali.IT_Library.models.services.BookService;

public class BookController extends BaseController {
    private final BookService service;
    public BookController(BookService service){
        this.service = service;
    }
    public String getById(int id){
        String response = "no book found";
        try{
            Optional<Book> book = service.getById(id);
            if(book.isPresent())
                response = book.get().toString();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return response;
    }
}
