/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.gui.task;

import java.util.Objects;

/**
 *
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 * @version 1.0
 */
public enum ExportType {
    FunAllGnuPlot("FUNALL Files to Gnuplot"),
    RowToLatex("Selected Rows to Latex Table"),
    RowToCSV("Selected Rows to CSV"),
    RowHVToFriedmanCSV("HV from Selected Rows to input data for Friedman"),
    RowHVToFriedmanLatex("HV from Selected Rows to input data for Friedman in Latex Table"),
    RowHVToKruskalWallisCSV("HV from Selected Rows to input data for Kruskal-Wallis");

    /**
     *
     * @param value
     * @return
     */
    public static ExportType getEnum(String value) {
        for (ExportType v : values()) {
            if (Objects.equals(v.getValue(), value)) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }

    private final String value;

    ExportType(String value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
