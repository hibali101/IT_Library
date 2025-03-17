package com.hibali.IT_Library.utilities;

import com.hibali.IT_Library.models.classes.BaseModel;

public class TransactionsResultsMessages {
    private TransactionsResultsMessages(){}
    public static void insertSuccess (BaseModel model){
        System.out.println(model.toString()+" inserted successfully");
    }
    public static void updateSuccess (BaseModel model){
        System.out.println(model.toString()+" updated successfully");
    }
    public static void deleteSuccess (BaseModel model){
        System.out.println(model.toString()+" deleted successfully");
    }
}
