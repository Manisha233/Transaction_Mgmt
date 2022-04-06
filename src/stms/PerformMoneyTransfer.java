package stms;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;
import javax.swing.JOptionPane;

public class PerformMoneyTransfer extends javax.swing.JFrame {

    
    public PerformMoneyTransfer() {
        initComponents();
    }
    
       public void close()
   {
        WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
   }
    
    public void display()
    {
        String AID=txtAadhar.getText();
        Statement stmt=null;
        String query=("select Bank_name,Account_no,Student_name,Address,Phone_no,Class_section from student_details_abcss where Aadhar_no='"+AID+"'" );
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            if(rs.next())
            {
                
                String bn = rs.getString(1);
                lblBank.setText(bn);
                
                String ano=rs.getString(2);
                lblAccNo.setText(ano);
                
                String stname=rs.getString(3);
                lblStdName.setText(stname);
                
                String add=rs.getString(4);
                lblAddress.setText(add);
                
                String phno=rs.getString(5);
                lblPhone.setText(phno);
                
                String cl=rs.getString(6);
                lblClass.setText(cl);
                
                lblAd.setText("");     
            }
            else
            {
                JOptionPane.showMessageDialog(this,"Please enter the correct Aadhar no");
            }
        
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    void amtWords()
    {
        int amount=Integer.parseInt(txtAmt.getText());
        txtAmt.setText(Integer.toString(amount));
        //NumberToWordsConverter ntw= new NumberToWordsConverter();
        txtAmtWords.setText(NumberToWordsConverter.converts(amount)+" Rupees Only");
    }
//    void checkUniqueReceipt()
//    {
//        //String AID=txtAadhar.getText();
//        int ReceiptNo=Integer.parseInt(txtReceipt.getText());
//        Statement stmt=null;
//        String query=("select * from money_transfer_details_abcss" );
//        try
//        {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
//            stmt=conn.createStatement();
//            ResultSet rs=stmt.executeQuery(query);
//            while(rs.next())
//            {
//                int rct=rs.getInt(2);
//                if(ReceiptNo==rct)
//                {
//                    JOptionPane.showMessageDialog(this,"Please enter unique receipt number");
//                    txtReceipt.setText("");
//                    flag=false;
//                }
//            }
//        
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//    }

    void insert_data()
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String dateoftransfer=dateFormat.format(dateDate.getDate());
        long Aadhar=Long.parseLong(txtAadhar.getText());
        int receipt=Integer.parseInt(txtReceipt.getText());
        int amount=Integer.parseInt(txtAmt.getText());    
        
        Statement stmt=null;
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("select * from money_transfer_details_abcss");
            if(rs.next())
            {
                PreparedStatement pst=conn.prepareStatement("insert into money_transfer_details_abcss(Aadhar_no, Receipt_no, Date, Amount) values(?,?,?,?)");
                pst.setLong(1,Aadhar);
                pst.setInt(2,receipt);
                pst.setString(3,dateoftransfer);
                pst.setInt(4,amount);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this,"Money transfer record entered successfully");
            }
  
        }
            catch(Exception ex)
            {
               ex.printStackTrace();
            } 
    }
    
//    void verify_date()
//    {
//        try{
//        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
//        String dateoftransfer=dateFormat.format(dateDate.getDate());
//
//        String dot[] = dateoftransfer.split("-");
//        int month = Integer.parseInt(dot[1]);
//        Month m = Month.of(month);
//        System.out.println(m); 
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//    }
    void verify_date_database()
    {
        int counter=0;
        String AID=txtAadhar.getText();
        try
        {
            SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
            String dateoftransfer=dateFormat.format(dateDate.getDate());
            String dot_txt[] = dateoftransfer.split("-");
            int month_txt = Integer.parseInt(dot_txt[1]);
            Month m_txt = Month.of(month_txt);
            
            Statement stmt=null;
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("select * from money_transfer_details_abcss where Aadhar_no='"+AID+"'");
            while(rs.next())
            {
                Date dt=rs.getDate(3);
                Date dateObj = rs.getDate(3);
                String date = dateObj.toString();

                String dot[] = date.split("-");
                int month = Integer.parseInt(dot[1]);
                Month m = Month.of(month);
                if(m_txt==m)
                {
                    counter++;
                }
                
            }
            if(counter>0)
            {
                JOptionPane.showMessageDialog(this, "Money has already been transferred to the student in the month of "+m_txt);
                counter=0;
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Date of transfer accepted");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    void verify_receipt_no()
    {
       if(txtReceipt.getText().isEmpty())
       {
            JOptionPane.showMessageDialog(this,"Please enter unique receipt number");
       }
       else
       {
        int counter=0;
        int receiptno=Integer.parseInt(txtReceipt.getText());
        Statement stmt=null;
        String query=("select * from money_transfer_details_abcss" );
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            while(rs.next())
            {
                int rct=rs.getInt(2);
                if(receiptno==rct)
                {
                    counter++;
                }
            }
            if(counter>0)
            {
                    JOptionPane.showMessageDialog(this,"Please enter unique receipt number");
                    txtReceipt.setText("");
                    counter=0;
            }
            else
            {
                    JOptionPane.showMessageDialog(this,"Receipt number accepted");                
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
       }
    }
    void verify_amount()
    {
       if(txtAmt.getText().isEmpty())
       {
            JOptionPane.showMessageDialog(this,"Please enter amount transferred");
       }
       else
       {
           amtWords();
       }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnLogout = new javax.swing.JButton();
        btnHome = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblReceipt = new javax.swing.JLabel();
        lblReceipt1 = new javax.swing.JLabel();
        lblReceipt2 = new javax.swing.JLabel();
        lblReceipt3 = new javax.swing.JLabel();
        lblReceipt4 = new javax.swing.JLabel();
        lblReceipt5 = new javax.swing.JLabel();
        lblReceipt6 = new javax.swing.JLabel();
        lblReceipt7 = new javax.swing.JLabel();
        lblReceipt8 = new javax.swing.JLabel();
        lblReceipt9 = new javax.swing.JLabel();
        lblReceipt10 = new javax.swing.JLabel();
        txtAadhar = new javax.swing.JTextField();
        txtAmt = new javax.swing.JTextField();
        txtReceipt = new javax.swing.JTextField();
        txtAmtWords = new javax.swing.JTextField();
        dateDate = new com.toedter.calendar.JDateChooser();
        lblStdName = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        lblAccNo = new javax.swing.JLabel();
        btnVerify = new javax.swing.JButton();
        btnEnter = new javax.swing.JButton();
        lblAd = new javax.swing.JLabel();
        lblBank = new javax.swing.JLabel();
        lblClass = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(6, 6, 17));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLogout.setBackground(new java.awt.Color(6, 6, 17));
        btnLogout.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        btnLogout.setForeground(java.awt.Color.white);
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logout.png"))); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 750, 270, 120));

        btnHome.setBackground(new java.awt.Color(6, 6, 17));
        btnHome.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        btnHome.setForeground(java.awt.Color.white);
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/home.png"))); // NOI18N
        btnHome.setText("Home");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });
        jPanel1.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 270, 120));

        btnSearch.setBackground(new java.awt.Color(6, 6, 17));
        btnSearch.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        btnSearch.setForeground(java.awt.Color.white);
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search2.png"))); // NOI18N
        btnSearch.setText("<html>Search<br>Record</html>");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel1.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 530, 270, 120));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 970));

        jPanel2.setBackground(new java.awt.Color(6, 6, 17));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Georgia", 1, 90)); // NOI18N
        lblTitle.setForeground(java.awt.Color.white);
        lblTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money3.png"))); // NOI18N
        lblTitle.setText("Perform Money Transfer\n");
        jPanel2.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 1300, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 1450, 160));

        jPanel3.setBackground(new java.awt.Color(18, 44, 70));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblReceipt.setBackground(java.awt.Color.white);
        lblReceipt.setFont(new java.awt.Font("Segoe UI Semibold", 1, 26)); // NOI18N
        lblReceipt.setForeground(java.awt.Color.white);
        lblReceipt.setText("Account No.");
        jPanel3.add(lblReceipt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 260, -1));

        lblReceipt1.setBackground(java.awt.Color.white);
        lblReceipt1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 26)); // NOI18N
        lblReceipt1.setForeground(java.awt.Color.white);
        lblReceipt1.setText("Aadhar no.");
        jPanel3.add(lblReceipt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 123, 270, -1));

        lblReceipt2.setBackground(java.awt.Color.white);
        lblReceipt2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 26)); // NOI18N
        lblReceipt2.setForeground(java.awt.Color.white);
        lblReceipt2.setText("Student Name");
        jPanel3.add(lblReceipt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 260, 30));

        lblReceipt3.setBackground(java.awt.Color.white);
        lblReceipt3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 26)); // NOI18N
        lblReceipt3.setForeground(java.awt.Color.white);
        lblReceipt3.setText("Address");
        jPanel3.add(lblReceipt3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 295, 260, -1));

        lblReceipt4.setBackground(java.awt.Color.white);
        lblReceipt4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 26)); // NOI18N
        lblReceipt4.setForeground(java.awt.Color.white);
        lblReceipt4.setText("Phone No.");
        jPanel3.add(lblReceipt4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, 260, -1));

        lblReceipt5.setBackground(java.awt.Color.white);
        lblReceipt5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 26)); // NOI18N
        lblReceipt5.setForeground(java.awt.Color.white);
        lblReceipt5.setText("<html>Class and<br>Section</html>");
        jPanel3.add(lblReceipt5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 260, -1));

        lblReceipt6.setBackground(java.awt.Color.white);
        lblReceipt6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 26)); // NOI18N
        lblReceipt6.setForeground(java.awt.Color.white);
        lblReceipt6.setText("Amount");
        jPanel3.add(lblReceipt6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 510, 260, -1));

        lblReceipt7.setBackground(java.awt.Color.white);
        lblReceipt7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 26)); // NOI18N
        lblReceipt7.setForeground(java.awt.Color.white);
        lblReceipt7.setText("<html>Total Amount<br>(in words)</html>\n");
        jPanel3.add(lblReceipt7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 590, 260, -1));

        lblReceipt8.setBackground(java.awt.Color.white);
        lblReceipt8.setFont(new java.awt.Font("Segoe UI Semibold", 1, 26)); // NOI18N
        lblReceipt8.setForeground(java.awt.Color.white);
        lblReceipt8.setText("Bank Name");
        jPanel3.add(lblReceipt8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 260, 30));

        lblReceipt9.setBackground(java.awt.Color.white);
        lblReceipt9.setFont(new java.awt.Font("Segoe UI Semibold", 1, 26)); // NOI18N
        lblReceipt9.setForeground(java.awt.Color.white);
        lblReceipt9.setText("Receipt No.");
        jPanel3.add(lblReceipt9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 260, -1));

        lblReceipt10.setBackground(java.awt.Color.white);
        lblReceipt10.setFont(new java.awt.Font("Segoe UI Semibold", 1, 26)); // NOI18N
        lblReceipt10.setForeground(java.awt.Color.white);
        lblReceipt10.setText("Date");
        jPanel3.add(lblReceipt10, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 40, 270, -1));

        txtAadhar.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtAadhar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAadharActionPerformed(evt);
            }
        });
        jPanel3.add(txtAadhar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 123, 310, 48));

        txtAmt.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtAmt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmtActionPerformed(evt);
            }
        });
        jPanel3.add(txtAmt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 510, 450, 50));

        txtReceipt.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReceiptActionPerformed(evt);
            }
        });
        jPanel3.add(txtReceipt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 370, 48));

        txtAmtWords.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtAmtWords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmtWordsActionPerformed(evt);
            }
        });
        jPanel3.add(txtAmtWords, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 600, 770, 50));

        dateDate.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        jPanel3.add(dateDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 40, 310, 48));

        lblStdName.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblStdName.setForeground(java.awt.Color.white);
        jPanel3.add(lblStdName, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 230, 340, 30));

        lblAddress.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblAddress.setForeground(java.awt.Color.white);
        jPanel3.add(lblAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 301, 810, 30));

        lblPhone.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblPhone.setForeground(java.awt.Color.white);
        jPanel3.add(lblPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 370, 340, 30));

        lblAccNo.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblAccNo.setForeground(java.awt.Color.white);
        jPanel3.add(lblAccNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 350, 30));

        btnVerify.setBackground(new java.awt.Color(204, 204, 204));
        btnVerify.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        btnVerify.setText("Verify\n");
        btnVerify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerifyActionPerformed(evt);
            }
        });
        jPanel3.add(btnVerify, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 690, 270, 80));

        btnEnter.setBackground(new java.awt.Color(204, 204, 204));
        btnEnter.setFont(new java.awt.Font("SansSerif", 1, 26)); // NOI18N
        btnEnter.setText("Enter");
        btnEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterActionPerformed(evt);
            }
        });
        jPanel3.add(btnEnter, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 190, 210, 70));

        lblAd.setFont(new java.awt.Font("SansSerif", 2, 21)); // NOI18N
        lblAd.setForeground(new java.awt.Color(204, 204, 204));
        lblAd.setText("<html>Please enter Aadhar number <br> to fetch data</html>\n");
        jPanel3.add(lblAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 170, 370, 120));

        lblBank.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblBank.setForeground(java.awt.Color.white);
        jPanel3.add(lblBank, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, 350, 30));

        lblClass.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblClass.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(lblClass, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 440, 350, 30));

        btnSave.setBackground(new java.awt.Color(204, 204, 204));
        btnSave.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        btnSave.setText("Generate receipt");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel3.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 690, 330, 80));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 1450, 810));

        setSize(new java.awt.Dimension(1814, 1027));
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        SearchStudentRecords sr=new SearchStudentRecords();
        sr.setVisible(true);
        close();
        
    }                                         

    private void txtAadharActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void txtAmtActionPerformed(java.awt.event.ActionEvent evt) {                                       
        int amount=Integer.parseInt(txtAmt.getText());
        txtAmt.setText(Integer.toString(amount));

    }                                      

    private void txtReceiptActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void txtAmtWordsActionPerformed(java.awt.event.ActionEvent evt) {                                            

    }                                           

    private void btnVerifyActionPerformed(java.awt.event.ActionEvent evt) {
       verify_date_database();
       verify_receipt_no();
       verify_amount();
    }                                         

    private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) { 
//       SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
//       String date=dateFormat.format(dateDate.getDate());
//       if(txtReceipt.getText()==""||txtAadhar.getText()==""||date=="")
//       {
//           JOptionPane.showMessageDialog(this,"Fill complete fields");
//       }
//       else
//       {
//            checkUniqueReceipt();
//            if(flag==true)
//                display();
//       }
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

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {                                         
//       amtWords();
//       checkUniqueReceipt();
//       if(flag==true)
//       { 
//            insert_data();
//       }
//       else if(flag==false)
//       {
//           JOptionPane.showMessageDialog(this,"Money transfer record entry failed");
//       }
         insert_data();
         PrintReceipt pr=new PrintReceipt();
         pr.setVisible(true);
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
            java.util.logging.Logger.getLogger(PerformMoneyTransfer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PerformMoneyTransfer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PerformMoneyTransfer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PerformMoneyTransfer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PerformMoneyTransfer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnEnter;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnVerify;
    private com.toedter.calendar.JDateChooser dateDate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblAccNo;
    private javax.swing.JLabel lblAd;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblBank;
    private javax.swing.JLabel lblClass;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblReceipt;
    private javax.swing.JLabel lblReceipt1;
    private javax.swing.JLabel lblReceipt10;
    private javax.swing.JLabel lblReceipt2;
    private javax.swing.JLabel lblReceipt3;
    private javax.swing.JLabel lblReceipt4;
    private javax.swing.JLabel lblReceipt5;
    private javax.swing.JLabel lblReceipt6;
    private javax.swing.JLabel lblReceipt7;
    private javax.swing.JLabel lblReceipt8;
    private javax.swing.JLabel lblReceipt9;
    private javax.swing.JLabel lblStdName;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtAadhar;
    private javax.swing.JTextField txtAmt;
    private javax.swing.JTextField txtAmtWords;
    private javax.swing.JTextField txtReceipt;
    // End of variables declaration                   
}
