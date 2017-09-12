package com.defaulty.decisions.gui;

import javax.swing.*;
import java.awt.*;

public class FirstWindow extends JFrame {

    private JPanel mainPanelsContainer = new JPanel();
    private JTextField fieldResult;
    private JComboBox<String> comboBox;
    private JLabel labelC;
    private JTextField fieldC;

    private final static double[][] matrix = {
            {15, 10, 0, -6, 17},
            {3, 14, 8, 9, 2},
            {1, 5, 14, 20, -3},
            {7, 19, 10, 2, 0}
    };
    private final static Dimension dimension = new Dimension(5, 4);
    private final static double c = 0.41d;

    public FirstWindow(int width, int height) {
        super("Первое");
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
        mainPanelsContainer.setLayout(new BoxLayout(mainPanelsContainer, BoxLayout.Y_AXIS));

        labelC = new JLabel("C: ");
        fieldC = new JTextField(c + "");
        labelC.setVisible(false);
        fieldC.setHorizontalAlignment(JTextField.CENTER);
        fieldC.setVisible(false);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(dimension.height + 1, dimension.width + 1));
        for (int i = 0; i < dimension.height + 1; i++) {
            for (int j = 0; j < dimension.width + 1; j++) {
                String text;
                Color color = Color.lightGray;
                if (i != 0 || j != 0) {
                    if (i == 0)
                        text = "r" + j;
                    else {
                        if (j == 0)
                            text = "x" + i;
                        else {
                            text = matrix[i - 1][j - 1] + "";
                            color = gridPanel.getBackground();
                        }
                    }
                } else
                    text = "";
                JTextField field = new JTextField(text);
                field.setBackground(color);
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setEditable(false);
                gridPanel.add(field);
            }
        }

        JLabel labelCombo = new JLabel("Method: ");
        comboBox = new JComboBox<>();
        comboBox.addItem("MM - Минимаксный");
        comboBox.addItem("S - Сэвиджа");
        comboBox.addItem("HW - Гурвица");
        comboBox.addActionListener(e -> {
            boolean visible = comboBox.getSelectedIndex() == 2;
            labelC.setVisible(visible);
            fieldC.setVisible(visible);
        });

        JButton buttonGet = new JButton("Get");
        buttonGet.addActionListener(e -> getResult());

        fieldResult = new JTextField();
        fieldResult.setHorizontalAlignment(JTextField.CENTER);

        mainPanelsContainer.add(gridPanel);
        mainPanelsContainer.add(ToolsClass.getPanel(getWidth(), 40, labelC, fieldC, labelCombo, comboBox, buttonGet));
        mainPanelsContainer.add(fieldResult);
    }

    private void getResult() {
        int methodId = comboBox.getSelectedIndex();
        String result = "";
        double[] dopCol = new double[dimension.height];
        int resX;
        switch (methodId) {
            case 0:
                //Создаем дополнительный столбец в который записываем минимум по строке
                //Что представляет из себя минимальные потери
                for (int i = 0; i < dimension.height; i++)
                    dopCol[i] = matrix[i][ToolsClass.getMinFromRow(matrix[i])];
                //Из полученного столбца выбираем строку с наибольшим значением
                //Что будет лучшим из худших результатов каждой стратегии
                resX = ToolsClass.getMaxFromRow(dopCol);
                result = "Вариант: x" + (resX + 1) + ", минимальные потери: " + dopCol[resX];
                break;
            case 1:
                double[][] matrixLosses = new double[dimension.height][dimension.width];
                for (int j = 0; j < dimension.width; j++) {
                    //Выбрать максимум по колонке
                    double max = matrix[ToolsClass.getMaxFromColumn(matrix, j)][j];
                    for (int i = 0; i < dimension.height; i++) {
                        //Вычесть из максимума текущее
                        matrixLosses[i][j] = max - matrix[i][j];
                    }
                }
                //Выбираем в отдельную колонку макимальное значение по строке
                //То есть максимальные потери по выбранной стратегии
                for (int i = 0; i < dimension.height; i++)
                    dopCol[i] = matrixLosses[i][ToolsClass.getMaxFromRow(matrixLosses[i])];
                //Выбираем наименьшее число
                //То есть наименьшие потери
                resX = ToolsClass.getMinFromRow(dopCol);
                result = "Вариант: x" + (resX + 1) + ", минимальные потери: " + dopCol[resX];
                break;
            case 2:
                double c = Double.parseDouble(fieldC.getText());

                for (int i = 0; i < dimension.height; i++) {
                    double max = matrix[i][ToolsClass.getMaxFromRow(matrix[i])];
                    double min = matrix[i][ToolsClass.getMinFromRow(matrix[i])];
                    dopCol[i] = c * min + (1 - c) * max;
                }

                resX = ToolsClass.getMaxFromRow(dopCol);
                result = "Вариант: x" + (resX + 1) + ", максимальная эффективность: " + dopCol[resX];

                break;
        }

        fieldResult.setText(result);

    }

}
