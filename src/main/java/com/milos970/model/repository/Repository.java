package com.milos970.model.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T,K>
{
    void save(T entity);
    Optional<T> findById(K id);
    void deleteById(K id);
    List<T> findAll();
}
