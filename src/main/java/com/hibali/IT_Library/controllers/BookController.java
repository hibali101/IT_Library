package com.hibali.IT_Library.controllers;

import java.sql.SQLException;
import java.util.Optional;

import com.hibali.IT_Library.http.server.responses.BaseResponse;
import com.hibali.IT_Library.http.server.responses.JsonResponse;
import com.hibali.IT_Library.models.classes.Book;
import com.hibali.IT_Library.models.services.BookService;

public class BookController extends BaseController {
    private final BookService service;
    public BookController(BookService service){
        this.service = service;
    }
    public BaseResponse getById(Integer id){
        try{
            Optional<Book> book = service.getById(id);
            if(book.isPresent()){
                return JsonResponse.ok(book.get());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
