
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class Viewcourses extends javax.swing.JFrame {

    mytablemodel tm;
    ArrayList<course> al;

    public Viewcourses() {
        initComponents();
        setSize(600, 500);
        setLocation(175, 100);
        setVisible(true);
        tm = new mytablemodel();
        jt.setModel(tm);
        ResultSet rs = JdbcCommon.executeQuery("select * from course");
        al = new ArrayList<>();
        new Thread(new job()).start();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jt = new javax.swing.JTable();
        bt_delete = new javax.swing.JButton();

        jButton2.setText("jButton2");

        jButton4.setText("jButton4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jt);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 0, 530, 150);

        bt_delete.setText("Delete");
        bt_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_deleteActionPerformed(evt);
            }
        });
        getContentPane().add(bt_delete);
        bt_delete.setBounds(103, 180, 240, 90);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_deleteActionPerformed
        // TODO add your handling code here:
        int n = jt.getSelectedRow();
        if (n == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please select something");
        } else {
            int k = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to delete?");

            if (k == JOptionPane.YES_OPTION) {
                try {
                    String cname = al.get(n).name;
                    HttpResponse res = Unirest.get("http://" + GlobalData.host + "/DeleteCourse").queryString("coursed", cname).asString();
                    String result = res.getBody().toString();
                    System.out.println("Response =" + result);
                    if (result.equalsIgnoreCase("success")) {
                        new Thread(new job()).start();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Failed");
                    }

                    // String name=al.get(n).name;
                } catch (UnirestException ex) {
                    //Logger.getLogger(Viewcourses.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
        }

    }//GEN-LAST:event_bt_deleteActionPerformed

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
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Viewcourses().setVisible(true);
            }
        });
    }

    class mytablemodel extends AbstractTableModel {

        String names[] = {"names", "description", "category", "amount"
        };

        @Override
        public String getColumnName(int column) {
            return names[column];
        }

        @Override
        public int getRowCount() {
            return al.size();

        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int row, int col) {
            course c = al.get(row);
            if (col == 0) {
                return c.name;
            } else if (col == 1) {
                return c.description;
            } else if (col == 2) {
                return c.category;
            } else {
                return c.amount;
            }

        }

    }

    class job implements Runnable {

        @Override
        public void run() {
            try {
                HttpResponse<String> httpresponse;

                httpresponse = Unirest.get("http://" + GlobalData.host + "/viewcourses").asString();

                // System.out.println(httpresponse.getBody());
                StringTokenizer st = new StringTokenizer(httpresponse.getBody(), "~");
                al.clear();
                while (st.hasMoreTokens()) {
                    String course = st.nextToken();
                    // System.out.println(course);
                    StringTokenizer st2 = new StringTokenizer(course, ";");
                    String name = st2.nextToken();
                    //System.out.println(name);
                    String des = st2.nextToken();
                    String cat = st2.nextToken();
                    //System.out.println(des);
                    //System.out.println(cat);
                    String amount = st2.nextToken();
                    //System.out.println(amount);
                    al.add(new course(name, des, cat, amount));

                }
                tm.fireTableDataChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_delete;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jt;
    // End of variables declaration//GEN-END:variables
}
