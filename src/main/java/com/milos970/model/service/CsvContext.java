package com.milos970.model.service;

import java.io.InputStream;
import java.io.OutputStream;

public class CsvContext
{
    private final Strategy strategy;

    public CsvContext(Strategy strategy) {
        this.strategy = strategy;
    }

    public void export(OutputStream out) {
        this.strategy.exportData(out);
    }

    public void importFrom(InputStream in) {
          this.strategy.importData(in);
    }
}
