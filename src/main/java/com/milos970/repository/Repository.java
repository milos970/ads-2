package com.milos970.repository;

import com.milos970.model.Patient;

import java.time.LocalDate;
import java.util.List;

public interface Repository<T> {

    public void save(T entity);
    public void removeById(int id);
    public T findById(int id);
    public List<T> findByDistrictId(int id);
    public List<T> findByRegionId(int id);
    public List<T> findByDateBetween(LocalDate from, LocalDate to);

}
