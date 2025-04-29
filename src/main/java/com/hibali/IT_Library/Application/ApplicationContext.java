package com.hibali.IT_Library.Application;

import com.hibali.IT_Library.controllers.BaseController;
import com.hibali.IT_Library.controllers.BookController;
import com.hibali.IT_Library.models.Dao.BookDao;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.services.BookService;

//this class here is responsible of creating the dependecies and provide methods to retreive these dependencies
public class ApplicationContext {
    private DbConnection dbConnection;
    private BookDao bookDao;
    private BookService bookService;
    private BookController bookController;

    public ApplicationContext(){
        this.dbConnection = DbConnection.getDbConnection();
        this.bookDao = new BookDao();
        this.bookService = new BookService(dbConnection, bookDao);
        this.bookController = new BookController(bookService);
    }

    public BaseController getController(Class<? extends BaseController> controllerClass){
        if(controllerClass.equals(BookController.class)){
            return this.bookController;
        }
        throw new IllegalArgumentException("Unknown controller class "+controllerClass.getName());
    }
}
