package com.navent.dao;

public interface GenericDaoI<T> {

    T insertOrUpdate(T t);

    void delete(Object id);

    T select(Object id);
}
