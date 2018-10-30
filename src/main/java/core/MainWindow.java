package core;

import math.IterationSolver;
import math.LinearSolver;
import math.Matrix;
import math.SolutionException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainWindow {
    
    
    private JPanel rootPanel;
    private JTable matrixTable;
    private JTextArea log;
    private JTextField sizeField;
    private JButton generateButton;
    private JButton solveButton;
    
    private static final int MAX_SIZE = 15;
    
    MainWindow() {
        generateButton.addActionListener(a -> generateMatrixTable());
        solveButton.addActionListener(a -> solve());
        generateMatrixTable();
    }
    
    private void solve() {
        LinearSolver solver = new IterationSolver();
        log.setText("");
        
        try {
            solver.setInput(extractMatrix());
            
            log.append("\nResult matrix: ");
            log.append("\n" + solver.solve() + "\n");
            
        }
        catch (SolutionException e) {
            log.append("\nError: ");
            log.append("\n" + e.getMsg());
        }
        finally {
            log.append("\nLog:");
            log.append("\n" + solver.getLog());
        }
    }
    
    private void generateMatrixTable() {
        int size;
        try {
            size = Integer.parseInt(sizeField.getText());
            size = size > MAX_SIZE ? MAX_SIZE : size;
            size = size < 0 ? 0 : size;
        }
        catch (NumberFormatException e) {
            size = 1;
            sizeField.setText("1");
        }
        updateMatrix(size);
    }
    
    private Matrix extractMatrix() {
        double[][] data = new double[matrixTable.getModel().getColumnCount()][];
    
        for (int i = 0; i < data.length; i++) {
            data[i] = new double[matrixTable.getModel().getRowCount()];
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = Double.parseDouble((String)matrixTable.getModel().getValueAt(j, i));
            }
        }
        return Matrix.makeEmptyMatrix(matrixTable.getModel().getColumnCount(), matrixTable.getModel().getRowCount()).fill(data);
    }
    
    private void updateMatrix(int size) {
        matrixTable.setModel(new DefaultTableModel(size, size + 1));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                matrixTable.getModel().setValueAt(Double.toString(0.0), i, j);
            }
        }
    }
    
    
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Equation system solver");
        MainWindow gui = new MainWindow();
        frame.setContentPane(gui.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    
}
