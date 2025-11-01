package com.milos970.repository;

import com.milos970.model.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface Repository<T> {

    public void save(T entity);
    public void removeById(int id);
    public T findById(int id);
    public List<T> findByDistrictId(int i, LocalDateTime from, LocalDateTime to);
    public List<T> findByRegionId(int id);
    public List<T> findByDateBetween(LocalDateTime from, LocalDateTime to);

}
