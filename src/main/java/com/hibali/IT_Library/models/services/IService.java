package com.hibali.IT_Library.models.services;

import java.util.ArrayList;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;

public interface IService<Model,IDType> {
    Model add(Model model) throws FieldRequiredException, FieldUniqueException;
    ArrayList<Model> getAll();
    Model getById(IDType id);
    Model update(Model model) throws FieldUniqueException, FieldRequiredException;
    Model delete(Model model) throws FieldRequiredException;
}
