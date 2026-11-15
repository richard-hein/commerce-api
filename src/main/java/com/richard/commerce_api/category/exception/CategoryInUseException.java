package com.richard.commerce_api.category.exception;

import java.util.UUID;

public class CategoryInUseException extends RuntimeException {
    public CategoryInUseException(UUID categoryId){
        super("Cannot delete category because products are assigned to it: "+ categoryId);
    }
}
