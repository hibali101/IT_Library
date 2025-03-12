package com.hibali.IT_Library.models.services;

import java.util.ArrayList;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;

public interface IService<Model,IDType> {
    void add(Model model) throws FieldRequiredException, FieldUniqueException;
    ArrayList<Model> getAll();
    Model getById(IDType id);
   /*  public void update(Model model);
    public void delete(Model model); */

}
