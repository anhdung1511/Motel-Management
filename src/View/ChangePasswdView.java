/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package View;

import Controller.AccountController;
import Model.AccountModel;
import Security.HashSHA256;
import javax.swing.JOptionPane;

/**
 *
 * @author nguye
 */
public class ChangePasswdView extends javax.swing.JDialog {

    /**
     * Creates new form ChangePasswdView
     */
    public ChangePasswdView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Change password");
        
        hidePasswd.setVisible(true);
        showPasswd.setVisible(false);
        oldPasswdTxt.setEchoChar('\u2022');
        
        hidePasswd1.setVisible(true);
        showPasswd1.setVisible(false);
        newPasswdTxt.setEchoChar('\u2022');
        
        hidePasswd2.setVisible(true);
        showPasswd2.setVisible(false);
        confirmPasswdTxt.setEchoChar('\u2022');
    }

    public void setUserName(String a) {
            userTxt.setText(a);
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
        popUpConfm = new javax.swing.JLabel();
        changeBT = new javax.swing.JButton();
        popUpNew = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        popUpOld = new javax.swing.JLabel();
        newPasswdTxt = new javax.swing.JPasswordField();
        confirmPasswdTxt = new javax.swing.JPasswordField();
        oldPasswdTxt = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        userTxt = new javax.swing.JLabel();
        hidePasswd = new javax.swing.JLabel();
        showPasswd = new javax.swing.JLabel();
        hidePasswd1 = new javax.swing.JLabel();
        showPasswd1 = new javax.swing.JLabel();
        hidePasswd2 = new javax.swing.JLabel();
        showPasswd2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        popUpConfm.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        popUpConfm.setForeground(new java.awt.Color(255, 0, 0));
        popUpConfm.setText(" ");

        changeBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        changeBT.setText("CHANGE");
        changeBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeBTActionPerformed(evt);
            }
        });

        popUpNew.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        popUpNew.setForeground(new java.awt.Color(255, 0, 0));
        popUpNew.setText(" ");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-password-reset-50.png"))); // NOI18N

        popUpOld.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        popUpOld.setForeground(new java.awt.Color(255, 0, 0));
        popUpOld.setText(" ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Confirm password");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("New password");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Current password");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Username");

        userTxt.setBackground(new java.awt.Color(255, 255, 255));
        userTxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        userTxt.setText(" ");
        userTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        hidePasswd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-hide-16.png"))); // NOI18N
        hidePasswd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hidePasswd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                hidePasswdMousePressed(evt);
            }
        });

        showPasswd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-eye-16.png"))); // NOI18N
        showPasswd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showPasswd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                showPasswdMousePressed(evt);
            }
        });

        hidePasswd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-hide-16.png"))); // NOI18N
        hidePasswd1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hidePasswd1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                hidePasswd1MousePressed(evt);
            }
        });

        showPasswd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-eye-16.png"))); // NOI18N
        showPasswd1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showPasswd1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                showPasswd1MousePressed(evt);
            }
        });

        hidePasswd2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-hide-16.png"))); // NOI18N
        hidePasswd2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hidePasswd2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                hidePasswd2MousePressed(evt);
            }
        });

        showPasswd2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-eye-16.png"))); // NOI18N
        showPasswd2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showPasswd2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                showPasswd2MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(popUpNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(changeBT, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1)
                                    .addComponent(confirmPasswdTxt)
                                    .addComponent(popUpConfm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(popUpOld, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2)
                                    .addComponent(newPasswdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(showPasswd2)
                                    .addComponent(hidePasswd2)
                                    .addComponent(showPasswd1)
                                    .addComponent(hidePasswd1)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(oldPasswdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(userTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(showPasswd)
                                    .addComponent(hidePasswd))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(oldPasswdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(popUpOld))
                    .addComponent(hidePasswd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showPasswd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newPasswdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(hidePasswd1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(showPasswd1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(popUpNew)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(confirmPasswdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(hidePasswd2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(showPasswd2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(popUpConfm)
                .addGap(28, 28, 28)
                .addComponent(changeBT, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changeBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeBTActionPerformed
        // TODO add your handling code here:
        String oldPasswd, newPasswd, confirmPass;
        oldPasswd = String.valueOf(oldPasswdTxt.getPassword());
        newPasswd = String.valueOf(newPasswdTxt.getPassword());
        confirmPass = String.valueOf(confirmPasswdTxt.getPassword());

        if(oldPasswd.equals("")){
            popUpOld.setText("Please enter a old password");
            popUpNew.setText(" ");
            popUpConfm.setText(" ");
        } else if(newPasswd.equals("")) {
            popUpNew.setText("Please enter a new password");
            popUpOld.setText(" ");
            popUpConfm.setText(" ");
        } else if (!newPasswd.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
            popUpOld.setText(" ");
            popUpConfm.setText(" ");
            popUpNew.setText("Minimum 6 characters, at least one letter and one number");
        } else if(confirmPass.equals("")) {
            popUpConfm.setText("Please enter a confirm password");
            popUpNew.setText(" ");
            popUpOld.setText(" ");
        } else if(!newPasswd.equals(confirmPass)){
            popUpConfm.setText("Does not match");
            popUpNew.setText(" ");
            popUpOld.setText(" ");
        } else{
            popUpNew.setText(" ");
            popUpConfm.setText(" ");
            popUpOld.setText(" ");
            AccountModel acc = new AccountController().getAccount(userTxt.getText());
            if(acc != null){
                String salt = "gnud4hn1511"; // Thêm muối vào để mã hóa được an toàn hơn 
                String oldPassSHA256 = HashSHA256.sha256(oldPasswd + salt);
                if(acc.getPasswd().equals(oldPassSHA256)){
                    String newPassSHA256 = HashSHA256.sha256(newPasswd + salt);
                    new AccountController().changePasswd(userTxt.getText(), newPassSHA256);
                    JOptionPane.showMessageDialog(rootPane, "Successfully");
                    this.dispose();
                }else {
                    popUpOld.setText("Wrong password");
                }
            }
        }
    }//GEN-LAST:event_changeBTActionPerformed

    private void hidePasswdMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hidePasswdMousePressed
        hidePasswd.setVisible(false);
        showPasswd.setVisible(true);
        oldPasswdTxt.setEchoChar((char)0);
    }//GEN-LAST:event_hidePasswdMousePressed

    private void showPasswdMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showPasswdMousePressed
        hidePasswd.setVisible(true);
        showPasswd.setVisible(false);
        oldPasswdTxt.setEchoChar('\u2022');
    }//GEN-LAST:event_showPasswdMousePressed

    private void hidePasswd1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hidePasswd1MousePressed
        // TODO add your handling code here:
        hidePasswd1.setVisible(false);
        showPasswd1.setVisible(true);
        newPasswdTxt.setEchoChar((char)0);
    }//GEN-LAST:event_hidePasswd1MousePressed

    private void showPasswd1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showPasswd1MousePressed
        // TODO add your handling code here:
        hidePasswd1.setVisible(true);
        showPasswd1.setVisible(false);
        newPasswdTxt.setEchoChar('\u2022');
    }//GEN-LAST:event_showPasswd1MousePressed

    private void hidePasswd2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hidePasswd2MousePressed
        // TODO add your handling code here:
        hidePasswd2.setVisible(false);
        showPasswd2.setVisible(true);
        confirmPasswdTxt.setEchoChar((char)0);
    }//GEN-LAST:event_hidePasswd2MousePressed

    private void showPasswd2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showPasswd2MousePressed
        // TODO add your handling code here:
        hidePasswd2.setVisible(true);
        showPasswd2.setVisible(false);
        confirmPasswdTxt.setEchoChar('\u2022');
    }//GEN-LAST:event_showPasswd2MousePressed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ChangePasswdView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ChangePasswdView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ChangePasswdView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ChangePasswdView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                ChangePasswdView dialog = new ChangePasswdView(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changeBT;
    private javax.swing.JPasswordField confirmPasswdTxt;
    private javax.swing.JLabel hidePasswd;
    private javax.swing.JLabel hidePasswd1;
    private javax.swing.JLabel hidePasswd2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField newPasswdTxt;
    private javax.swing.JPasswordField oldPasswdTxt;
    private javax.swing.JLabel popUpConfm;
    private javax.swing.JLabel popUpNew;
    private javax.swing.JLabel popUpOld;
    private javax.swing.JLabel showPasswd;
    private javax.swing.JLabel showPasswd1;
    private javax.swing.JLabel showPasswd2;
    private javax.swing.JLabel userTxt;
    // End of variables declaration//GEN-END:variables
}