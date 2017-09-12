package com.defaulty.decisions.gui;

import javax.swing.*;
import java.awt.*;

public class ToolsClass {

    public static JPanel getPanel(int width, int height, Component... components) {
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(width, height));
        jPanel.setSize(new Dimension(width, height));
        jPanel.setMaximumSize(new Dimension(width, height));
        for (Component c : components) {
            jPanel.add(c);
        }
        return jPanel;
    }

    public static int getMaxFromRow(double[] row) {
        if (row.length > 0) {
            int pos = 0;
            double max = row[0];
            for (int i = 1; i < row.length; i++) {
                if (row[i] > max) {
                    pos = i;
                    max = row[i];
                }
            }
            return pos;
        } else
            throw new NullPointerException("Empty array");
    }

    public static int getMinFromRow(double[] row) {
        if (row.length > 0) {
            int pos = 0;
            double min = row[0];
            for (int i = 1; i < row.length; i++) {
                if (row[i] < min) {
                    pos = i;
                    min = row[i];
                }
            }
            return pos;
        } else
            throw new NullPointerException("Empty array");
    }


    public static int getMaxFromColumn(double[][] array, int column) {
        if (array.length > 0 && array[0].length > 0) {
            int pos = 0;
            double max = array[0][column];
            for (int i = 1; i < array.length; i++) {
                if (array[i][column] > max) {
                    pos = i;
                    max = array[i][column];
                }
            }
            return pos;
        } else
            throw new NullPointerException("Empty array or column");
    }

    public static int getMinFromColumn(double[][] array, int column) {
        if (array.length > 0 && array[0].length > 0) {
            int pos = 0;
            double min = array[0][column];
            for (int i = 1; i < array.length; i++) {
                if (array[i][column] < min) {
                    pos = i;
                    min = array[i][column];
                }
            }
            return pos;
        } else
            throw new NullPointerException("Empty array or column");
    }
}
