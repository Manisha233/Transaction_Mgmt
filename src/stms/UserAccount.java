package stms;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import java.sql.*;

public class UserAccount extends javax.swing.JFrame {

    
    public UserAccount() {
        initComponents();
    }
public void close() {
        WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
    void display()
    {
        String SID=txtSchoolid.getText();
        Statement stmt=null;
        String query=("select * from principal_details where school_ID='"+SID+"'" );
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            if(rs.next())
            {
                String username=rs.getString(1);
                lblUsername.setText(username);
                String accounttype=rs.getString(4);
                lblAcctype.setText(accounttype);
            }
            else
            {
                JOptionPane.showMessageDialog(this,"Please enter the correct School ID used for logging in");
            }
        
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblReceipt9 = new javax.swing.JLabel();
        txtSchoolid = new javax.swing.JTextField();
        btnChangePswrd = new javax.swing.JButton();
        lblReceipt10 = new javax.swing.JLabel();
        lblReceipt11 = new javax.swing.JLabel();
        btnEnter = new javax.swing.JButton();
        lblAcctype = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel6.setBackground(new java.awt.Color(6, 6, 17));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Georgia", 1, 90)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/user.png"))); // NOI18N
        jLabel2.setText(" User Account");
        jPanel6.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, 10, 930, 150));

        jPanel7.setBackground(new java.awt.Color(18, 44, 70));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblReceipt9.setBackground(java.awt.Color.white);
        lblReceipt9.setFont(new java.awt.Font("Segoe UI Semibold", 1, 40)); // NOI18N
        lblReceipt9.setForeground(java.awt.Color.white);
        lblReceipt9.setText("Please enter the School ID used for logging in");
        jPanel7.add(lblReceipt9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 1390, 90));

        txtSchoolid.setFont(new java.awt.Font("Segoe UI Semilight", 1, 30)); // NOI18N
        txtSchoolid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSchoolidActionPerformed(evt);
            }
        });
        jPanel7.add(txtSchoolid, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 650, 55));

        btnChangePswrd.setBackground(new java.awt.Color(25, 25, 25));
        btnChangePswrd.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        btnChangePswrd.setForeground(java.awt.Color.white);
        btnChangePswrd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/update2.png"))); // NOI18N
        btnChangePswrd.setText(" Change Password");
        btnChangePswrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePswrdActionPerformed(evt);
            }
        });
        jPanel7.add(btnChangePswrd, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 580, 440, 110));

        lblReceipt10.setBackground(java.awt.Color.white);
        lblReceipt10.setFont(new java.awt.Font("Segoe UI Semibold", 1, 40)); // NOI18N
        lblReceipt10.setForeground(java.awt.Color.white);
        lblReceipt10.setText("Username");
        jPanel7.add(lblReceipt10, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 260, -1));

        lblReceipt11.setBackground(java.awt.Color.white);
        lblReceipt11.setFont(new java.awt.Font("Segoe UI Semibold", 1, 40)); // NOI18N
        lblReceipt11.setForeground(java.awt.Color.white);
        lblReceipt11.setText("Account Type");
        jPanel7.add(lblReceipt11, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 430, 260, -1));

        btnEnter.setBackground(new java.awt.Color(204, 204, 204));
        btnEnter.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        btnEnter.setText("Enter");
        btnEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterActionPerformed(evt);
            }
        });
        jPanel7.add(btnEnter, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 130, 260, 80));

        lblAcctype.setFont(new java.awt.Font("Segoe UI Semilight", 3, 40)); // NOI18N
        jPanel7.add(lblAcctype, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 422, 400, 80));
        lblAcctype.setForeground(java.awt.Color.white);
        lblUsername.setFont(new java.awt.Font("Segoe UI Semilight", 3, 40)); // NOI18N
        jPanel7.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 280, 420, 100));
        lblUsername.setForeground(java.awt.Color.white);

        jPanel8.setBackground(new java.awt.Color(18, 44, 70));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnHome.setBackground(new java.awt.Color(6, 6, 17));
        btnHome.setFont(new java.awt.Font("SansSerif", 1, 34)); // NOI18N
        btnHome.setForeground(java.awt.Color.white);
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/home.png"))); // NOI18N
        btnHome.setText(" Home");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });
        jPanel8.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 290, 120));

        btnLogout.setBackground(new java.awt.Color(6, 6, 17));
        btnLogout.setFont(new java.awt.Font("SansSerif", 1, 34)); // NOI18N
        btnLogout.setForeground(java.awt.Color.white);
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logout.png"))); // NOI18N
        btnLogout.setText(" Logout");
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        jPanel8.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, 290, 120));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 352, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 1371, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                    .addGap(1, 1, 1)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 1371, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 757, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(167, 167, 167)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 927, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    private void txtSchoolidActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void btnChangePswrdActionPerformed(java.awt.event.ActionEvent evt) {                                               
        PrincipalPassword ppw=new PrincipalPassword();
        ppw.setVisible(true);
        close();
    }                                              

    private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) {                                         
        display();
    }                                        

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {                                        

        PrincipalDashboard pd=new PrincipalDashboard();
        pd.setVisible(true);
        close();
    }                                       

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {                                          

        Loginn lg=new Loginn();
        lg.setVisible(true);
        close();
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
            java.util.logging.Logger.getLogger(UserAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserAccount().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnChangePswrd;
    private javax.swing.JButton btnEnter;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblAcctype;
    private javax.swing.JLabel lblReceipt10;
    private javax.swing.JLabel lblReceipt11;
    private javax.swing.JLabel lblReceipt9;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JTextField txtSchoolid;
    // End of variables declaration                   
}
