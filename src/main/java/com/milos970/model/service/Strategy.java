package com.milos970.model.service;

import com.milos970.model.entity.Patient;

import java.util.List;

public interface Strategy<T> {
    public void exportData(List<T> list);
    public List<T> importData();
}
