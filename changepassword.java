
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author officeworks
 */
public class changepassword extends javax.swing.JFrame {

    String email, password, npassword;

    /**
     * Creates new form changepassword
     */
    public changepassword(String email) {
        initComponents();
        setSize(700, 700);
        setLocation(150,90);
        //tf1.getText();
        //tf2.getText();
        //tf3.getText();
        //tf4.getText();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tf1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        bt_ch = new javax.swing.JButton();
        tf2 = new javax.swing.JPasswordField();
        tf3 = new javax.swing.JPasswordField();
        tf4 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setText("Enter email");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(70, 10, 150, 70);

        jLabel2.setText("Enter Old password");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(70, 110, 220, 80);

        jLabel3.setText("Enter New password");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(70, 220, 220, 60);

        tf1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf1ActionPerformed(evt);
            }
        });
        getContentPane().add(tf1);
        tf1.setBounds(250, 20, 290, 60);

        jLabel4.setText("Confirm Password");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(70, 340, 160, 50);

        bt_ch.setText("Change");
        bt_ch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_chActionPerformed(evt);
            }
        });
        getContentPane().add(bt_ch);
        bt_ch.setBounds(200, 440, 250, 40);
        getContentPane().add(tf2);
        tf2.setBounds(250, 120, 290, 60);

        tf3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf3ActionPerformed(evt);
            }
        });
        getContentPane().add(tf3);
        tf3.setBounds(250, 220, 290, 70);
        getContentPane().add(tf4);
        tf4.setBounds(250, 340, 290, 70);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf1ActionPerformed

    private void bt_chActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_chActionPerformed
if (tf1.getText().equals("") || tf2.getText().equals("")||tf3.getText().equals("")||tf4.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Fill all details");

        }
else if(!tf3.getText().equals(tf4.getText()))
{
    JOptionPane.showMessageDialog(rootPane, "Enter correct password");
}
else
{
        new Thread(new job_change()).start();


    }//GEN-LAST:event_bt_chActionPerformed
    }
    private void tf3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf3ActionPerformed
    class job_change implements Runnable {

        @Override
        public void run() {
            try {
                HttpResponse<String> response = Unirest.get("http://"+GlobalData.host+"/ModifyPassword")
                        .queryString("email",tf1.getText())
                        .queryString("pass",tf2.getText())
                        .queryString("new",tf3.getText())
                        .asString();
                System.out.println(response.getBody());
                JOptionPane.showMessageDialog(rootPane,response.getBody());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(changepassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(changepassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(changepassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(changepassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
             //   new changepassword().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_ch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField tf1;
    private javax.swing.JPasswordField tf2;
    private javax.swing.JPasswordField tf3;
    private javax.swing.JPasswordField tf4;
    // End of variables declaration//GEN-END:variables
}