package com.richard.commerce_api.catalog.exception;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException(String sku){
        super("Product with SKU already exists: "+sku);
    }
}
