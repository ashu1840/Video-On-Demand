/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author officeworks
 */
public class WelcomeUser extends javax.swing.JFrame {

    String email, cname;

    /**
     * Creates new form WelcomeUser
     */
    public WelcomeUser(String email, String coursename) {
        initComponents();
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setSize(width, height);

        this.email = email;
        this.cname = coursename;
        lb_em.setText("Welcome  " + email);
        final PanelSlider42<JPanel> slider = new PanelSlider42<JPanel>(jPanel2);
        JPanel basePanel = slider.getBasePanel();
        String images[] = {"/userphotos/d.lc.jpg", "/userphotos/download.e.jpeg", "/userphotos/download.f.jpeg", "/userphotos/download.p.jpeg"};

        for (int i = 0; i < images.length; i++) {
            try {
                JLabel lb = new JLabel();

                BufferedImage image = ImageIO.read(getClass().getResource(images[i]));
                BufferedImage img = resize(image, 1360, 270);
                lb.setIcon(new ImageIcon(img));
                slider.addComponent(lb);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        slider.test();
        jPanel2.add(basePanel);
        new Thread(new job()).start();
    }

    class job implements Runnable {

        @Override
        public void run() {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

            jScrollPane1.setPreferredSize(new Dimension(700, 500));
            p3.setPreferredSize(new Dimension(700, 270 * GlobalData.category.length));
            SingleCategory sp[] = new SingleCategory[GlobalData.category.length];
            int y = 10;
            for (int i = 0; i < GlobalData.category.length; i++) {
                sp[i] = new SingleCategory();
                sp[i].setBounds(10, y, 1200, 230);
                sp[i].jLabel1.setText("Courses Under " + GlobalData.category[i]);
                p3.add(sp[i]);
                y = y + 270;
                repaint();
                try {
                    HttpResponse<String> res = Unirest.get("http://" + GlobalData.host + "/fetchcoursesfromcat").queryString("category", GlobalData.category[i]).asString();
                    StringTokenizer st = new StringTokenizer(res.getBody(), "~");
                    System.out.println("response from server" + res.getBody());
                    int count = st.countTokens();
                    SingleCoursePanel sp2[] = new SingleCoursePanel[count];
                    int x = 50;
                    for (int j = 0; j < count; j++) {
                        String course = st.nextToken();
                        StringTokenizer st2 = new StringTokenizer(course, ";");
                        //String photo = st2.nextToken();
                        String coursename = st2.nextToken();
                        String photo = st2.nextToken();

                        int amount = Integer.parseInt(st2.nextToken());
                        sp2[j] = new SingleCoursePanel();
                        sp2[j].addMouseListener(new MouseAdapter() {
                            public void mouseClicked(MouseEvent e) {
                                FullCourseView obj = new FullCourseView(coursename, email);
                                obj.setVisible(true);
                            }

                        });
                        try {
                            BufferedImage myimage = ImageIO.read(new URL("http://" + GlobalData.host + "/GetSource/" + photo));
                            BufferedImage newimg = resize(myimage, sp2[j].lb1.getWidth(), sp2[j].lb1.getHeight());
                            sp2[j].lb1.setIcon(new ImageIcon(newimg));
                        } catch (MalformedURLException ex) {
                            //Logger.getLogger(WelcomeUser.class.getName()).log(Level.SEVERE, null, ex);
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            Logger.getLogger(WelcomeUser.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //lb.setIcon(new ImageIcon(myimage));
                        sp2[j].lb1.setText(photo);
                        sp2[j].lb2.setText("coursename " + coursename);
                        sp2[j].lb3.setText("amount $" + amount + "");
                        sp[i].add(sp2[j]);
                        sp2[j].setBounds(x, 70, 152, 150);
                        x = x + 250;
                        repaint();
                    }

                } catch (UnirestException ex) {
                    // Logger.getLogger(WelcomeUser.class.getName()).log(Level.SEVERE, null, ex);

                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lb_em = new javax.swing.JLabel();
        bt_my = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        p3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(102, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel1.setLayout(null);

        jButton1.setBackground(new java.awt.Color(255, 255, 204));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setText("Change password");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(523, 11, 180, 40);

        jButton2.setBackground(new java.awt.Color(255, 255, 204));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton2.setText("Logout");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(1020, 10, 240, 40);

        lb_em.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(lb_em);
        lb_em.setBounds(110, 14, 220, 40);

        bt_my.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bt_my.setText("My Profile");
        bt_my.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_myActionPerformed(evt);
            }
        });
        jPanel1.add(bt_my);
        bt_my.setBounds(750, 10, 250, 40);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1360, 60);

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(null);
        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 60, 1360, 180);

        p3.setBackground(new java.awt.Color(0, 102, 153));
        p3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        p3.setLayout(null);
        jScrollPane1.setViewportView(p3);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 240, 1360, 460);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
        UserLogin obj = new UserLogin();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        changepassword obj = new changepassword(email);
        obj.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void bt_myActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_myActionPerformed
        // TODO add your handling code here:
        ViewUser obj = new ViewUser(email);
        obj.setVisible(true);
    }//GEN-LAST:event_bt_myActionPerformed

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
            java.util.logging.Logger.getLogger(WelcomeUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WelcomeUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WelcomeUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WelcomeUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new WelcomeUser(String()).setVisible(true);
            }
        });
    }

    BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_my;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_em;
    private javax.swing.JPanel p3;
    // End of variables declaration//GEN-END:variables
}
