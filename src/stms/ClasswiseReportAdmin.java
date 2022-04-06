package stms;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.TreeMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author seema
 */
public class ClasswiseReportAdmin extends javax.swing.JFrame {

    /**
     * Creates new form ClasswiseReportAdmin
     */
    public ClasswiseReportAdmin() {
        initComponents();
        fillCombo();
    }
    public void close() {
        WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
    public void fillCombo()
    {
        try
        {
            String querycombo="select class_section from class_details_abcss";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            PreparedStatement pst=conn.prepareStatement(querycombo);
            ResultSet rs=pst.executeQuery();
            while(rs.next())
            {
                String classsection=rs.getString("class_section");
                comboClasses.addItem(classsection);
            }
            
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    void search()
    {
        try
        {
            
            Object selectedItem = comboClasses.getSelectedItem();
            String classsection = selectedItem.toString();
//            java.util.Date datefrom = jDateChooser1.getDate();
//            String strDateFrom = DateFormat.getDateInstance().format(datefrom);
//            java.util.Date dateto = jDateChooser2.getDate();
//            String strDateTo = DateFormat.getDateInstance().format(dateto);
            Statement stmt=null;
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            String query = ("select * from class_details_abcss where class_section= '"+classsection+"' ");   
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            
            boolean status=rs.next();
            if(status)
            {
                setRecordsTable();
            }
            else
            {
                JOptionPane.showMessageDialog(this,"Record not found");
            }
            
            conn.close();
         }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
//    public void  setRecordsTable()
//    {
//        Object selectedItem = comboClasses.getSelectedItem();
//        String classection = selectedItem.toString();
//        
//        try
//        {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
//            PreparedStatement pst=conn.prepareStatement("SELECT student_details_abcss.student_name, student_details_abcss.class_section,student_details_abcss.aadhar_no, money_transfer_details_abcss.receipt_no, money_transfer_details_abcss.Date, money_transfer_details_abcss.amount FROM student_details_abcss INNER JOIN money_transfer_details_abcss ON student_details_abcss.aadhar_no=money_transfer_details_abcss.aadhar_no WHERE student_details_abcss.class_section= '"+classection+"' and  ");
//            ResultSet rs=pst.executeQuery();
//            
//            
//            while(rs.next())
//            {
//                long receiptno=rs.getLong("money_transfer_details_abcss.receipt_no");
//                String studentname=rs.getString("student_details_abcss.student_name");
//                long aadhar=rs.getLong("student_details_abcss.aadhar_no");
//                String classsection=rs.getString("student_details_abcss.class_section");
//                int amount=rs.getInt("money_transfer_details_abcss.amount");
//                Date datetransfer=rs.getDate("money_transfer_details_abcss.Date");
//                Object[] obj={receiptno,studentname,aadhar,classsection,amount,datetransfer};
//                DefaultTableModel model;
//                model=(DefaultTableModel)tblClasswise.getModel();
//                model.addRow(obj);
//            }
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//    }
    void setRecordsTable()
    {
        Object selectedItem = comboClasses.getSelectedItem();
        String classection = selectedItem.toString();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String fromDate=dateFormat.format(jDateChooser1.getDate());
        String toDate=dateFormat.format(jDateChooser2.getDate());
        
        Integer amountTotal=0;
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            PreparedStatement pst=conn.prepareStatement("SELECT student_details_abcss.student_name, student_details_abcss.class_section,student_details_abcss.aadhar_no, money_transfer_details_abcss.receipt_no, money_transfer_details_abcss.Date, money_transfer_details_abcss.amount FROM student_details_abcss INNER JOIN money_transfer_details_abcss ON student_details_abcss.aadhar_no=money_transfer_details_abcss.aadhar_no WHERE student_details_abcss.class_section = ? and money_transfer_details_abcss.Date between ? and ?");
            pst.setString(1,classection);
            pst.setString(2,fromDate);
            pst.setString(3,toDate);
            ResultSet rs=pst.executeQuery();
            while(rs.next())
            {
                long receiptno=rs.getLong("money_transfer_details_abcss.receipt_no");
                String studentname=rs.getString("student_details_abcss.student_name");
                long aadhar=rs.getLong("student_details_abcss.aadhar_no");
                String classsection=rs.getString("student_details_abcss.class_section");
                int amount=rs.getInt("money_transfer_details_abcss.amount");
                Date datetransfer=rs.getDate("money_transfer_details_abcss.Date");
                
                amountTotal=amountTotal+amount;
                
                Object[] obj={receiptno,studentname,aadhar,classsection,amount,datetransfer};
                DefaultTableModel model;
                model=(DefaultTableModel)tblClasswise.getModel();
                model.addRow(obj);
            }
            lblStuMoney.setText(amountTotal.toString());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    
    void display()
    {
        Object selectedItem = comboClasses.getSelectedItem();
        String classection = selectedItem.toString();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String fromDate=dateFormat.format(jDateChooser1.getDate());
        String toDate=dateFormat.format(jDateChooser2.getDate());
        
        Statement stmt=null;
        String query=("select * from class_funds_abcss where class_section='"+classection+"' and date_from='"+fromDate+"' and date_to='"+toDate+"'" );
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/transaction_mgmt", "root", "");
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            if(rs.next())
            {
                int amt=rs.getInt(5);
                String amnt=String.valueOf(amt);
                lblSectionMoney.setText(amnt);
            }
            else
            {
                lblSectionMoney.setText("-");
            }
        
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void clearTable()
    {
        DefaultTableModel model = (DefaultTableModel)tblClasswise.getModel();
        model.setRowCount(1);
    }
    void printTable()
    {
        SimpleDateFormat Date_Format = new SimpleDateFormat("YYYY-MM-dd"); 
        String datefrom =  Date_Format.format(jDateChooser1.getDate());
        String dateto=  Date_Format.format(jDateChooser2.getDate());
       
        MessageFormat header=new MessageFormat("Report From "+datefrom+" To " +dateto);
        MessageFormat footer=new MessageFormat("page{0,number,integer}");
        try 
        {
            tblClasswise.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        } 
    }
    public void exportToExcel()
    {
        XSSFWorkbook wb=new XSSFWorkbook();
        XSSFSheet ws=wb.createSheet();
        DefaultTableModel model=(DefaultTableModel)tblClasswise.getModel();
        
        TreeMap<String,Object[]>map=new TreeMap<>();
        
        map.put("0", new Object[]{model.getColumnName(0), model.getColumnName(1), model.getColumnName(2), model.getColumnName(3), model.getColumnName(4), model.getColumnName(5)});
        
        for(int i=1; i<model.getRowCount(); i++)
        {
            map.put(Integer.toString(i), new Object[]{model.getValueAt(i, 0), model.getValueAt(i, 1), model.getValueAt(i, 2), model.getValueAt(i, 3), model.getValueAt(i, 4), model.getValueAt(i, 5)});
        }
        Set<String> id=map.keySet();
        XSSFRow fRow;
        int rowID=0;
        for(String key:id)
        {
            fRow=ws.createRow(rowID++);
            Object[] value=map.get(key);
            int cellID=0;
            for(Object object:value)
            {
                XSSFCell cell=fRow.createCell(cellID++);
                cell.setCellValue(object.toString());
            }
        }
        try
        {
            FileOutputStream fos=new FileOutputStream(new File(txtBrowse.getText()));
            wb.write(fos);
            fos.close();
            JOptionPane.showMessageDialog(this, "Report exported to Excel successfully: "+txtBrowse.getText());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        comboClasses = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnBrowse = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        btnPrint1 = new javax.swing.JButton();
        txtBrowse = new javax.swing.JTextField();
        btnBrowse1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClasswise = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblTotalStuMoney = new javax.swing.JLabel();
        lblStuMoney = new javax.swing.JLabel();
        lblSectionMoney = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(18, 44, 70));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("SansSerif", 3, 28)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("to");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 130, -1, -1));

        comboClasses.setFont(new java.awt.Font("Segoe UI Semilight", 1, 22)); // NOI18N
        comboClasses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--classes--" }));
        comboClasses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboClassesActionPerformed(evt);
            }
        });
        jPanel4.add(comboClasses, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 350, 50));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 32)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Select class and section");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jDateChooser1.setToolTipText("--to--");
        jDateChooser1.setFont(new java.awt.Font("Segoe UI Semilight", 1, 22)); // NOI18N
        jPanel4.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 380, 50));

        jDateChooser2.setToolTipText("--from--");
        jDateChooser2.setFont(new java.awt.Font("Segoe UI Semilight", 1, 22)); // NOI18N
        jPanel4.add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 120, 380, 50));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 32)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Select date");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, -1, -1));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("<html>Please select the first and last day of the month<br>for which you want to generate the report</html>");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

        btnBrowse.setBackground(new java.awt.Color(26, 26, 26));
        btnBrowse.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        btnBrowse.setForeground(new java.awt.Color(255, 255, 255));
        btnBrowse.setText("Export to Excel");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });
        jPanel4.add(btnBrowse, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 700, 280, 70));

        btnSubmit.setBackground(new java.awt.Color(204, 204, 204));
        btnSubmit.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        btnSubmit.setForeground(new java.awt.Color(0, 0, 51));
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        jPanel4.add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 210, 230, 60));
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 680, 1440, 10));
        jPanel4.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 1170, 0));

        btnPrint1.setBackground(new java.awt.Color(204, 204, 204));
        btnPrint1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        btnPrint1.setForeground(new java.awt.Color(0, 0, 51));
        btnPrint1.setText("Print");
        btnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrint1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnPrint1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 210, 230, 60));

        txtBrowse.setFont(new java.awt.Font("Times New Roman", 0, 28)); // NOI18N
        jPanel4.add(txtBrowse, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 710, 530, 50));

        btnBrowse1.setBackground(new java.awt.Color(26, 26, 26));
        btnBrowse1.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        btnBrowse1.setForeground(new java.awt.Color(255, 255, 255));
        btnBrowse1.setText("Browse");
        btnBrowse1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowse1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnBrowse1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 700, 250, 70));

        tblClasswise.setFont(new java.awt.Font("Segoe UI Semilight", 1, 24)); // NOI18N
        tblClasswise.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Receipt no", "Student name", "Aadhar no", "Class", "Amount", "Date of transfer"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblClasswise);
        tblClasswise.setRowHeight(50);
        JTableHeader header = tblClasswise.getTableHeader();
        header.setFont(new Font("SansSerif",Font.BOLD,20));
        Color c1 = new Color(6,22,38);
        header.setForeground(c1);

        TableCellRenderer rendererFromHeader = tblClasswise.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 1400, 360));

        jLabel7.setFont(new java.awt.Font("SansSerif", 3, 28)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("from");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, -1, -1));

        jSeparator4.setFont(new java.awt.Font("Cooper Black", 1, 24)); // NOI18N
        jPanel4.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 1450, 10));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, 1420, 790));

        jPanel1.setBackground(new java.awt.Color(6, 22, 38));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 79)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/reportlarge.png"))); // NOI18N
        jLabel1.setText(" View Classwise Report");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 1070, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 1420, 140));

        jPanel2.setBackground(new java.awt.Color(6, 6, 17));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnHome.setBackground(new java.awt.Color(6, 6, 17));
        btnHome.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/home.png"))); // NOI18N
        btnHome.setText("Home");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });
        jPanel2.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 230, 110));

        btnLogout.setBackground(new java.awt.Color(6, 6, 17));
        btnLogout.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logout.png"))); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        jPanel2.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 230, 110));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 310, 430));

        jPanel3.setBackground(new java.awt.Color(6, 6, 17));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("<html>Total money allocated to<br>this section for the<br>chosen month</html>");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 270, -1));

        lblTotalStuMoney.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        lblTotalStuMoney.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalStuMoney.setText("<html>Total amount distributed<br>to students of the section<br>for the chosen month</html>");
        jPanel3.add(lblTotalStuMoney, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, -1, -1));

        lblStuMoney.setFont(new java.awt.Font("Segoe UI Black", 3, 36)); // NOI18N
        lblStuMoney.setForeground(new java.awt.Color(255, 255, 255));
        lblStuMoney.setText("      ");
        jPanel3.add(lblStuMoney, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, -1, -1));

        lblSectionMoney.setBackground(new java.awt.Color(6, 22, 38));
        lblSectionMoney.setFont(new java.awt.Font("Segoe UI Black", 3, 36)); // NOI18N
        lblSectionMoney.setForeground(new java.awt.Color(255, 255, 255));
        lblSectionMoney.setText("      ");
        jPanel3.add(lblSectionMoney, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 310, 550));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {                                        
        AdminDashboard ad=new AdminDashboard();
        ad.setVisible(true);
        close();
    }                                       

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {                                          
        Loginn lg=new Loginn();
        lg.setVisible(true);
        close();
    }                                         

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {                                          
        exportToExcel();
    }                                         

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {                                          
        clearTable();
        search();
        display();
    }                                         

    private void btnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        printTable();
    }                                         

    private void btnBrowse1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.showOpenDialog(this);
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String date=dateFormat.format(new Date());
        
        try
        {
            File fi=fileChooser.getSelectedFile();
            String path=fi.getAbsolutePath();
            path=path+"_"+date+".xlsx";
            txtBrowse.setText(path);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
            
        
    }                                          

    private void comboClassesActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(ClasswiseReportAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClasswiseReportAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClasswiseReportAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClasswiseReportAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClasswiseReportAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnBrowse1;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPrint1;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JComboBox<String> comboClasses;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblSectionMoney;
    private javax.swing.JLabel lblStuMoney;
    private javax.swing.JLabel lblTotalStuMoney;
    private javax.swing.JTable tblClasswise;
    private javax.swing.JTextField txtBrowse;
    // End of variables declaration                   
}
