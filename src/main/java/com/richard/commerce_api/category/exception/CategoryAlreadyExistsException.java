package com.richard.commerce_api.category.exception;

import java.util.UUID;

public class CategoryAlreadyExistsException extends RuntimeException{
    public CategoryAlreadyExistsException(String name){
        super("Category already exists: "+name);
    }
}
