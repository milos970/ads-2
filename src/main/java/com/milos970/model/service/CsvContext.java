package com.milos970.model.service;

import java.util.List;

public class CsvContext
{
    private final Strategy strategy;

    public CsvContext(Strategy strategy) {
        this.strategy = strategy;
    }

    public void export(List<?> data) {
        this.strategy.exportData(data);
    }

    public List<?>  importFrom() {
          return this.strategy.importData();
    }
}
