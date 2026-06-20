package com.project.smartcommunity.service;

import java.util.List;

public interface CrudService<T, R> {
    List<T> findAll();
    T findById(Long id);
    T create(R request);
    T update(Long id, R request);
    void delete(Long id);
}
