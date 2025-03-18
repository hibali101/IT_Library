package com.hibali.IT_Library.models.services;

import java.lang.classfile.ClassFile.Option;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;

public interface IService<Model, IDType> {

    Optional<Model> add(Model model) throws FieldRequiredException, FieldUniqueException, BuisnessRulesException;

    ArrayList<Model> getAll();

    Optional<Model> getById(IDType id);

    Optional<Model> update(Model model) throws FieldUniqueException, FieldRequiredException, BuisnessRulesException;

    Optional<Model> delete(Model model) throws FieldRequiredException;
}
