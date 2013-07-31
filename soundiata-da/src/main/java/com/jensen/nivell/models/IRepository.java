package com.jensen.nivell.models;


public interface IRepository<T> {
    public void add(T entity) throws Exception;

    String get(String identifier);
}
