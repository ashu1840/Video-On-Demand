/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author officeworks
 */
public class SingleCoursePanel extends javax.swing.JPanel {

    /**
     * Creates new form SingleCoursePanel
     */
    public SingleCoursePanel() {
        initComponents();
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb1 = new javax.swing.JLabel();
        lb2 = new javax.swing.JLabel();
        lb3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(153, 255, 204));
        setLayout(null);

        lb1.setText("photo");
        lb1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(lb1);
        lb1.setBounds(0, 0, 150, 100);

        lb2.setText("Course name");
        add(lb2);
        lb2.setBounds(0, 100, 150, 30);

        lb3.setText("Amount");
        add(lb3);
        lb3.setBounds(0, 120, 150, 30);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel lb1;
    public javax.swing.JLabel lb2;
    public javax.swing.JLabel lb3;
    // End of variables declaration//GEN-END:variables
}
