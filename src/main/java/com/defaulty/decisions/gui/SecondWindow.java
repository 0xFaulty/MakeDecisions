package com.defaulty.decisions.gui;

import javax.swing.*;
import java.awt.*;

public class SecondWindow extends JFrame {

    private JPanel mainPanelsContainer = new JPanel();

    private final static double[] required = {100, 150, 200, 250, 300};
    private final static double[] order = {100, 150, 200, 250, 300};
    private final static double[] coefP = {0.25, 0.15, 0.3, 0.1, 0.2};
    private final static Dimension dimension = new Dimension(5, 5);

    public SecondWindow(int width, int height) {
        super("Второе");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setSize(new Dimension(width, height));
        createGUI();
        getContentPane().add(mainPanelsContainer);
        super.pack();
        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }

    private void createGUI() {
        mainPanelsContainer.setLayout(new BoxLayout(mainPanelsContainer, BoxLayout.X_AXIS));

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(dimension.height + 2, dimension.width + 1));

        //Верхняя строка коэффициентов
        gridPanel.add(getField("", getBackground()));
        for (double aCoefP : coefP) {
            JTextField field = new JTextField(aCoefP + "");
            field.setBackground(Color.PINK);
            field.setHorizontalAlignment(JTextField.CENTER);
            field.setEditable(false);
            gridPanel.add(field);
        }

        //Строка чисел спроса
        gridPanel.add(getField("", Color.lightGray));
        for (int i = 0; i < dimension.width; i++) {
            gridPanel.add(getField(required[i] + "", Color.lightGray));
        }

        double[][] result = new double[dimension.height][dimension.width];

        for (int i = 0; i < dimension.height; i++) {
            gridPanel.add(getField(order[i] + "", Color.lightGray));
            for (int j = 0; j < dimension.width; j++) {
                double a1;
                double a2;
                //Если заказ больше чем спроса
                if (required[i] > order[j]) {
                    a1 = required[j] * 49;
                    a2 = (required[i] - order[j]) * 15;
                } else {
                    a1 = required[i] * 49;
                    a2 = 0;
                }
                double a3 = order[i] * 25;
                result[i][j] = a1 + a2 - a3;

                gridPanel.add(getField(result[i][j] + "", getBackground()));
            }
        }

        //Добавляем столбец эффективности
        JPanel gridPanel2 = new JPanel();
        gridPanel2.setLayout(new GridLayout(dimension.height + 2, 1));
        gridPanel2.add(getField("", getBackground()));
        gridPanel2.add(getField("Sg*e", Color.lightGray));

        //Создаём массим сумм по строке
        double[] dopCol = new double[dimension.height];
        for (int i = 0; i < dimension.height; i++) {
            for (int j = 0; j < dimension.width; j++) {
                dopCol[i] += result[i][j] * coefP[j];
            }
            gridPanel2.add(getField(dopCol[i] + "", getBackground()));
        }

        System.out.println(dopCol[ToolsClass.getMaxFromRow(dopCol)]);

        mainPanelsContainer.add(gridPanel);
        mainPanelsContainer.add(gridPanel2);

    }

    private JTextField getField(String text, Color color) {
        JTextField field = new JTextField(text);
        field.setBackground(color);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setEditable(false);
        return field;
    }

}
