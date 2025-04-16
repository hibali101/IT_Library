package com.hibali.IT_Library.utilities;

import com.hibali.IT_Library.models.classes.BaseModel;

public class TransactionsResultsMessages {
    private static final LibLogger logger = LibLogger.getInfoLogger();
    private TransactionsResultsMessages(){}
    public static void insertSuccess (BaseModel model){
        System.out.println(model.toString()+" inserted successfully");
        logger.log(model.toString()+" inserted successfully");
    }
    public static void updateSuccess (BaseModel model){
        System.out.println(model.toString()+" updated successfully");
        logger.log(model.toString()+" updated successfully");
    }
    public static void deleteSuccess (BaseModel model){
        System.out.println(model.toString()+" deleted successfully");
        logger.log(model.toString()+" deleted successfully");
    }
}
