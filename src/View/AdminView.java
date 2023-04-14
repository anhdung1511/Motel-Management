package View;

import Time.DateTime;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import Model.*;
import Controller.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author nguye
 */
public class AdminView extends javax.swing.JFrame {

    /**
     * Creates new form MainView
     */
    public String getUserName = "";
    private Image img = Toolkit.getDefaultToolkit().getImage("D:\\QuanLyNhaTro_CNPM\\QuanLyNhaTro\\src\\Image\\iconMain.png");    
    private DateTime dt = new DateTime();

//    // ============= Khai báo các lớp đối tượng ==================
    private ArrayList<CustomerModel> listCustomer;
    private ArrayList<RoomModel> listRoom;
    private ArrayList<ServiceModel> listService;
    private ArrayList<BillModel> listBill;
    private ArrayList<ContractDetailModel> listContractDetail;
//    // ===================================================
//    
//    // ============ Khai báo model ========================
    private DefaultTableModel modelCustomer, modelRoom, modelDetailRoom, modelService, modelRentaiBill, modelBill, modelContract, motelContractDetail;
//    // ====================================================
//    
//    
//    //============ Khai báo và khởi tạo các lớp Controller ===================
    private CustomerController customerDAO = new CustomerController();
    private RoomController roomDAO = new RoomController();
    private StatusRoomController statusDAO = new StatusRoomController();
    private ContractDetailController detailDAO = new ContractDetailController();
    private ServiceController serviceDAO = new ServiceController();
    private ContractController contractDAO = new ContractController();
    private BillController billDAO = new BillController();
    private BillDetailController billDetailDAO = new BillDetailController();
    private ArrayList<MotelModel> listMotel = new MotelController().getListMotel();
    //========================================================================
    
    public AdminView() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setIconImage(img);
        
       /// showMenu();

        // ========== Thao tác View. ===============
        mainPanel.setVisible(true);
        customerPanel.setVisible(false);
        roomPanel.setVisible(false);
        servicePanel.setVisible(false);
        billPanel.setVisible(false);
        statisticalPanel.setVisible(false);
        contractPanel.setVisible(false);
        //=======================================

        //  =========== Lấy ngày tháng năm và thời gian thực tế =========
        dt.showTime(timeTxt);
        dt.showDate(dateTxt);
        // ================================================================

        // ============ Khởi tạo các model =============
        modelCustomer = (DefaultTableModel) customerTB.getModel();
        modelRoom = (DefaultTableModel) roomListTB.getModel();
        modelDetailRoom = (DefaultTableModel) detailTB.getModel();
        modelRentaiBill = (DefaultTableModel) rentalBillTB.getModel();
        modelBill = (DefaultTableModel) billTB.getModel();
        modelContract = (DefaultTableModel) contractDetailTB.getModel();
        motelContractDetail = (DefaultTableModel) contractDetailWithRoomTB.getModel();
        // ====================================================

        // ===== Hiển thị table Customer và table Room. ===
        setHeaderTB();
        showCustomerTable();
        showRoomTable();
        showBillTB();
        showContractDetailByStatusTB(true);
        showCountStatusBill();
        showCountRoom();
        showMotelCB();
        showService();
        showLineChartRoom();
        //=================================================
    }

    // Create function setSex convert boolean to string (True: Male, False: Female)
    private String setSex(boolean sex) {
        return sex ? "Male" : "Female";        
    }

    // Create function setHeaderTB set Header Table
    private void setHeaderTB() {
        JTableHeader hdCus = customerTB.getTableHeader();
        hdCus.setBackground(Color.PINK);
        hdCus.setFont(new Font("Segor UI", Font.BOLD, 12));
        
        JTableHeader hdRoom = roomListTB.getTableHeader();
        hdRoom.setBackground(Color.PINK);
        hdRoom.setFont(new Font("Segor UI", Font.BOLD, 14));
        
        JTableHeader hdDetail = detailTB.getTableHeader();
        hdDetail.setBackground(Color.PINK);
        hdDetail.setFont(new Font("Segor UI", Font.BOLD, 14));
        
        JTableHeader hdBill = rentalBillTB.getTableHeader();
        hdBill.setBackground(Color.PINK);
        hdBill.setFont(new Font("Segor UI", Font.BOLD, 14));
        
        JTableHeader hdC = contractDetailWithRoomTB.getTableHeader();
        hdC.setBackground(Color.PINK);
        hdC.setFont(new Font("Segor UI", Font.BOLD, 14));
    }

    // Create function setNameStatusBill convert boolean to string (True: Paid, False: Unpaid)
    private String setNameStatusBill(boolean s) {
        return s == true ? "Paid" : "Unpaid";
    }
    
    
    private void showRentailBill(String idR) {
        modelRentaiBill.setRowCount(0);
        String idC = contractDAO.getIDContract(idR);
        listBill = billDAO.getListBillAtRoom(idC);
        for (BillModel bill : listBill) {
            modelRentaiBill.addRow(new Object[]{
                bill.getIdBill(),
                bill.getCollectionDate(),
                bill.getTotalMoney(),
                setNameStatusBill(bill.isStatusBill())
            });
        }
    }
    
    private void showContractDetailWithRoom(String idR) {
        motelContractDetail.setRowCount(0);
        String idC = contractDAO.getIDContract(idR);
        listContractDetail = detailDAO.getListContractDetailWithRoom(idC);
        for (ContractDetailModel detail : listContractDetail) {
            motelContractDetail.addRow(new Object[] {
                detail.getIdContractFK(),
                detail.getCmndFK(),
                customerDAO.getFullNameCustomer(detail.getCmndFK()),
                detail.getStartDate(),
                detail.getEndDate()
            });
        }
    }
    
    private String setRentalStsCus(boolean ck) {
        return ck ? "Yes" : "No";
    }
    
    private void showCustomerTable() {
        modelCustomer.setRowCount(0);        
        listCustomer = customerDAO.getListCustomer();
        for (CustomerModel cus : listCustomer) {
            modelCustomer.addRow(new Object[]{
                cus.getCMND(),
                cus.getFirstName() + " " + cus.getLastName(),
                setSex(cus.isSex()),
                cus.getDateOfBirth(),
                cus.getPhoneNumber(),
                cus.getAddress(),
                cus.getJob(),
                setRentalStsCus(cus.isRentalStatus())
            });
        }
    }

    private void showFindCustomerTable(String name) {
        modelCustomer.setRowCount(0);
        for (CustomerModel cus : customerDAO.getFindCustomerByName(name)) {
            modelCustomer.addRow(new Object[]{
                cus.getCMND(),
                cus.getFirstName() + " " + cus.getLastName(),
                setSex(cus.isSex()),
                cus.getDateOfBirth(),
                cus.getPhoneNumber(),
                cus.getAddress(),
                cus.getJob(),
                detailDAO.countContractDetail(cus.getCMND())
            });
        }
    }
    
//    // Tạo hàm showRoomTable() để lấy thông tin Room vào table.
    private void showRoomTable() {
        modelRoom.setRowCount(0);
        listRoom = roomDAO.getListRoom();
        for (RoomModel r : listRoom) {
            modelRoom.addRow(new Object[]{
                r.getIdRoom(),
                r.getIdMotelFK(),
                statusDAO.getNameStatus(r.getIdStatusFK())
            });
        }
    }
    
    private void showRoomByMotel(String name) {
        modelRoom.setRowCount(0);
        listRoom = roomDAO.getListRoomByMotel(name);
        for (RoomModel r : listRoom) {
            modelRoom.addRow(new Object[]{
                r.getIdRoom(),
                r.getIdMotelFK(),
                statusDAO.getNameStatus(r.getIdStatusFK())
            });
        }
    }
    
    private void showDetailRoom() {
        modelDetailRoom.setRowCount(0);
        String idR = nameRoom.getText().substring(5);
        String idC = contractDAO.getIDContract(idR);
        listCustomer = customerDAO.getInfo(idR, idC);
        for (CustomerModel cus : listCustomer) {
            modelDetailRoom.addRow(new Object[]{
                cus.getFirstName() + " " + cus.getLastName(),
                cus.getCMND(),
                detailDAO.getStartDate(cus.getCMND(),contractDAO.getIDContract(nameRoom.getText().substring(5)))
            });
        }
    }
    
    private void showBillTB() {
        modelBill.setRowCount(0);
        listBill = billDAO.getListBillWithMonthYear(monthCS.getMonth() + 1, yearCS.getYear());
        for (BillModel i : listBill) {
            modelBill.addRow(new Object[]{
                i.getIdBill(),
                contractDAO.getIDRoom(i.getIdContractFK()),
                i.getCollectionDate(),
                i.getTotalMoney(),
                setNameStatusBill(i.isStatusBill())
            });
        }
    }
    
    private void showMotelCB() {
        for (MotelModel i : listMotel) {
            motelCB.addItem(i.getNameMotel());
        }
    }
    
    private void showBillStatusTB(boolean sts) {
        modelBill.setRowCount(0);
        listBill = billDAO.getListBillWithMonthYearStatus(monthCS.getMonth() + 1, yearCS.getYear(), sts);
        for (BillModel i : listBill) {
            modelBill.addRow(new Object[]{
                i.getIdBill(),
                contractDAO.getIDRoom(i.getIdContractFK()),
                i.getCollectionDate(),
                i.getTotalMoney(),
                setNameStatusBill(i.isStatusBill())
            });
        }
    }
    
    private void showCountStatusBill() {
        listBill = billDAO.getListBillWithMonthYear(monthCS.getMonth() + 1, yearCS.getYear());
        numTotalBill.setText(String.valueOf(listBill.size()));
        numPaid.setText(String.valueOf(billDAO.getCountBillStatus(true, monthCS.getMonth() + 1, yearCS.getYear())));
        numUnpaid.setText(String.valueOf(billDAO.getCountBillStatus(false, monthCS.getMonth() + 1, yearCS.getYear())));
    }
    
    private void showCountRoom() {
        numTotalRM.setText(String.valueOf(roomDAO.getTotalRoom()));
        numRentedRM.setText(String.valueOf(roomDAO.getTotalRoomRented()));
        numUnrentedRM.setText(String.valueOf(roomDAO.getTotalRoomUnrented()));
    }
    
    private void showCountRoomByMotel(String name) {
        numTotalRM.setText(String.valueOf(roomDAO.getTotalRoomByMotel(name)));
        numRentedRM.setText(String.valueOf(roomDAO.getTotalRoomRentedByMotel(name)));
        numUnrentedRM.setText(String.valueOf(roomDAO.getTotalRoomUnrentedByMotel(name)));
    }
    
    private String setStatusContract(boolean sts) {
        return sts ? "Unexpired" : "Expired";
    }
    
    private void showContractDetailByStatusTB(boolean sts) {
        modelContract.setRowCount(0);
        listContractDetail = detailDAO.getListContractDetailByStatus(sts);
        for (ContractDetailModel i : listContractDetail) {
            modelContract.addRow(new Object[]{
                i.getCmndFK(),
                customerDAO.getFirstName(i.getCmndFK()) + " " + customerDAO.getLastName(i.getCmndFK()),
                i.getIdContractFK(),
                i.getStartDate(),
                setStatusContract(i.isStatusDetail())
            });
        }
    }
    
    
    private void showContractByRoomStatus(String idR, boolean sts) {
        modelContract.setRowCount(0);
        listContractDetail = detailDAO.getListContractDetailByRoomStatus(idR, sts);
        for (ContractDetailModel i : listContractDetail) {
            modelContract.addRow(new Object[]{
                i.getCmndFK(),
                customerDAO.getFirstName(i.getCmndFK()) + " " + customerDAO.getLastName(i.getCmndFK()),
                i.getIdContractFK(),
                i.getStartDate(),
                setStatusContract(i.isStatusDetail())
            });
        }
    }
    
    private void showContractByRoom(String idR) {
        modelContract.setRowCount(0);
        listContractDetail = detailDAO.getListContractDetailRoom(idR);
        for (ContractDetailModel i : listContractDetail) {
            modelContract.addRow(new Object[]{
                i.getCmndFK(),
                customerDAO.getFirstName(i.getCmndFK()) + " " + customerDAO.getLastName(i.getCmndFK()),
                i.getIdContractFK(),
                i.getStartDate(),
                setStatusContract(i.isStatusDetail())
            });
        }
    }

    // Tạo hàm hideMenu() để ẩn thao tác trước khi login.
    private void hideMenu() {
        this.customerMenu.setEnabled(false);
        this.roomMenu.setEnabled(false);
        this.serviceMenu.setEnabled(false);
        this.billMenu.setEnabled(false);
        this.statisticalMenu.setEnabled(false);
        this.logoutItem.setVisible(false);
        this.changeItem.setVisible(false);
        this.contractMenu.setEnabled(false);
    }
//    
//    // Tạo hàm showMenu() để hiện thao tác sau khi login thành công.

//    private void showMenu() {
//        this.customerMenu.setEnabled(true);
//        this.roomMenu.setEnabled(true);
//        this.serviceMenu.setEnabled(true);
//        this.contractMenu.setEnabled(true);
//        this.billMenu.setEnabled(true);
//        this.statisticalMenu.setEnabled(true);
//        this.logoutItem.setVisible(true);
//        this.changeItem.setVisible(true);
//    }
    
    private void showLineChartRoom() {
        String[] months = {"", "January", "February", "March", "Aprill", "May", "June", "July", "August", "September", "October", "November", "December"};
        int year = yearChartSP.getYear();
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (int i = 1; i <= 12; i++) {
            dataset.setValue(roomDAO.getStatisticalByRoomMonthYear(i, year), "Amount", months[i]);
        }
        
        JFreeChart jchart = ChartFactory.createBarChart("Total room rented monthly", "Months", "Total", dataset, PlotOrientation.VERTICAL, true, true, false);
        
        CategoryPlot lineCategoryPlot = jchart.getCategoryPlot();
        lineCategoryPlot.setRangeGridlinePaint(new Color(255, 204, 204));
        lineCategoryPlot.setBackgroundPaint(Color.white);
        
        ChartPanel lineChartPanel = new ChartPanel(jchart);
        panelLineChart.removeAll();
        panelLineChart.add(lineChartPanel, BorderLayout.CENTER);
        panelLineChart.validate();
        
    }
    
    private void showLineChartBillMonth() {
        String[] months = {"", "January", "February", "March", "Aprill", "May", "June", "July", "August", "September", "October", "November", "December"};
        int year = yearChartSP.getYear();
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (int i = 1; i <= 12; i++) {
            dataset.setValue(billDAO.getStatisticalByMonthYear(i, year), "Amount", months[i]);
        }
        
        JFreeChart jchart = ChartFactory.createBarChart("Total bill monthly", "Months", "Total", dataset, PlotOrientation.VERTICAL, true, true, false);
        
        CategoryPlot lineCategoryPlot = jchart.getCategoryPlot();
        
        lineCategoryPlot.setRangeGridlinePaint(new Color(255, 204, 204));
        lineCategoryPlot.setBackgroundPaint(Color.white);
        
        ChartPanel lineChartPanel = new ChartPanel(jchart);
        panelLineChart.removeAll();
        panelLineChart.add(lineChartPanel, BorderLayout.CENTER);
        panelLineChart.validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customerMainGR = new javax.swing.ButtonGroup();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        statisticalPanel = new javax.swing.JPanel();
        yearChartSP = new com.toedter.calendar.JYearChooser();
        showChartBT = new javax.swing.JButton();
        panelLineChart = new javax.swing.JPanel();
        chartComboBox = new javax.swing.JComboBox<>();
        backRoomBT1 = new javax.swing.JButton();
        billPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        timeLB3 = new javax.swing.JLabel();
        dateLB3 = new javax.swing.JLabel();
        billDetailBT = new javax.swing.JButton();
        backBillBT = new javax.swing.JButton();
        changeBillDetailBT = new javax.swing.JButton();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        billMainPN = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        billTB = new javax.swing.JTable();
        yearCS = new com.toedter.calendar.JYearChooser();
        monthCS = new com.toedter.calendar.JMonthChooser();
        statusBillCB = new javax.swing.JComboBox<>();
        numPaid = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        numUnpaid = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        numTotalBill = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        billDetailPN = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        titleBill = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        elecTxt = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        WaterTxt = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        InternetTxt = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        trashTxt = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        idRTxt = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        priceRm = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        priceElec = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        priceWater = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        priceInternet = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        priceTrash = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        totalRm = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        totalElec = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        totalWater = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        totalInternet = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        totalTrash = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        totalMoneyTxt = new javax.swing.JLabel();
        statusBillTxt = new javax.swing.JLabel();
        paidBT = new javax.swing.JButton();
        servicePanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        timeLB2 = new javax.swing.JLabel();
        dateLB2 = new javax.swing.JLabel();
        backServiceBT = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        changeElecBT = new javax.swing.JButton();
        jLabel54 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        priceElecTxt = new javax.swing.JLabel();
        unitElec = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        changeWaterBT = new javax.swing.JButton();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        priceWaterTxt = new javax.swing.JLabel();
        unitWater = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel118 = new javax.swing.JLabel();
        changeTrashBT = new javax.swing.JButton();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        priceTrashTxt = new javax.swing.JLabel();
        unitTrash = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel127 = new javax.swing.JLabel();
        changeWfBT = new javax.swing.JButton();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        priceWfTxt = new javax.swing.JLabel();
        unitWF = new javax.swing.JLabel();
        customerPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        timeLB = new javax.swing.JLabel();
        dateLB = new javax.swing.JLabel();
        backCusBT = new javax.swing.JButton();
        changeCusBT = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        customerTB = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        findCustomerTxt = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        timeTxt = new javax.swing.JLabel();
        dateTxt = new javax.swing.JLabel();
        textBg2 = new javax.swing.JLabel();
        roomPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        timeLB1 = new javax.swing.JLabel();
        dateLB1 = new javax.swing.JLabel();
        roomDetailsBT = new javax.swing.JButton();
        backRoomBT = new javax.swing.JButton();
        addRoomBT = new javax.swing.JButton();
        motelButton = new javax.swing.JButton();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        roomMainPN = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        numTotalRM = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        numRentedRM = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        numUnrentedRM = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        motelCB = new javax.swing.JComboBox<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        roomListTB = new javax.swing.JTable();
        jLabel63 = new javax.swing.JLabel();
        deleteRoom = new javax.swing.JButton();
        detailRoomPN = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        nameRoom = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        maxTxt = new javax.swing.JLabel();
        crrTxt = new javax.swing.JLabel();
        priceRmTxt = new javax.swing.JLabel();
        statusRmTxt = new javax.swing.JLabel();
        createBillBT = new javax.swing.JButton();
        addCusBT = new javax.swing.JButton();
        changeRoomBT = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        detailTB = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        rentalBillTB = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        contractDetailWithRoomTB = new javax.swing.JTable();
        checkOutRoomBT = new javax.swing.JButton();
        contractPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        timeLB4 = new javax.swing.JLabel();
        dateLB4 = new javax.swing.JLabel();
        contractDetailBT = new javax.swing.JButton();
        backContractBT = new javax.swing.JButton();
        extendContract = new javax.swing.JButton();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        mainContractPN = new javax.swing.JPanel();
        chooseRoomCB = new javax.swing.JComboBox<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        contractDetailTB = new javax.swing.JTable();
        jLabel39 = new javax.swing.JLabel();
        statusContractCB = new javax.swing.JComboBox<>();
        detailContractPN = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        cmndContract = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        fnameContract = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        idCtr = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        rentedRM = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        startD = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        endD = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        statusContractDetail = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        customerMenu = new javax.swing.JMenu();
        roomMenu = new javax.swing.JMenu();
        serviceMenu = new javax.swing.JMenu();
        billMenu = new javax.swing.JMenu();
        contractMenu = new javax.swing.JMenu();
        statisticalMenu = new javax.swing.JMenu();
        accountMenu = new javax.swing.JMenu();
        changeItem = new javax.swing.JMenuItem();
        logoutItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý phòng trọ");

        jLayeredPane1.setBackground(new java.awt.Color(255, 255, 255));

        statisticalPanel.setBackground(new java.awt.Color(255, 255, 255));
        statisticalPanel.setPreferredSize(new java.awt.Dimension(1222, 652));

        yearChartSP.setBackground(new java.awt.Color(255, 255, 255));

        showChartBT.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        showChartBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/show-chart.png"))); // NOI18N
        showChartBT.setText("SHOW");
        showChartBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showChartBTActionPerformed(evt);
            }
        });

        panelLineChart.setLayout(new java.awt.BorderLayout());

        chartComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chartComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Total room rented monthly", "Total bill monthly" }));

        backRoomBT1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backRoomBT1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-back-arrow-32.png"))); // NOI18N
        backRoomBT1.setText("BACK TO HOME SCREEN");
        backRoomBT1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backRoomBT1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout statisticalPanelLayout = new javax.swing.GroupLayout(statisticalPanel);
        statisticalPanel.setLayout(statisticalPanelLayout);
        statisticalPanelLayout.setHorizontalGroup(
            statisticalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statisticalPanelLayout.createSequentialGroup()
                .addGap(320, 320, 320)
                .addComponent(chartComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(yearChartSP, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(showChartBT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backRoomBT1)
                .addContainerGap(174, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statisticalPanelLayout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addComponent(panelLineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 1128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        statisticalPanelLayout.setVerticalGroup(
            statisticalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticalPanelLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(panelLineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addGroup(statisticalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(statisticalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(showChartBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(yearChartSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chartComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                    .addComponent(backRoomBT1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        billPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 204, 204));

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-house-56.png"))); // NOI18N

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Bill Management");

        timeLB3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timeLB3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLB3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-clock-32.png"))); // NOI18N

        dateLB3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dateLB3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateLB3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-calendar-32.png"))); // NOI18N

        billDetailBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        billDetailBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-info-32.png"))); // NOI18N
        billDetailBT.setText("BILL DETAIL");
        billDetailBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billDetailBTActionPerformed(evt);
            }
        });

        backBillBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backBillBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-back-arrow-32.png"))); // NOI18N
        backBillBT.setText("BACK TO HOME SCREEN");
        backBillBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBillBTActionPerformed(evt);
            }
        });

        changeBillDetailBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        changeBillDetailBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/report.png"))); // NOI18N
        changeBillDetailBT.setText("CHANGE BILL");
        changeBillDetailBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeBillDetailBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(timeLB3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dateLB3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(backBillBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billDetailBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(changeBillDetailBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addGap(23, 23, 23)
                .addComponent(timeLB3)
                .addGap(18, 18, 18)
                .addComponent(dateLB3)
                .addGap(93, 93, 93)
                .addComponent(billDetailBT, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(changeBillDetailBT, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(backBillBT, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        billMainPN.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("BILL LIST");

        billTB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Bill", "Room", "Collection date", "Total money", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        billTB.setGridColor(new java.awt.Color(255, 255, 255));
        billTB.setRowHeight(30);
        billTB.setSelectionBackground(new java.awt.Color(255, 204, 204));
        billTB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(billTB);

        yearCS.setBackground(new java.awt.Color(255, 255, 255));

        monthCS.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        statusBillCB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        statusBillCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Paid", "Unpaid" }));
        statusBillCB.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        statusBillCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusBillCBActionPerformed(evt);
            }
        });

        numPaid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        numPaid.setText("0000");

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel47.setText("Paid");

        numUnpaid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        numUnpaid.setText("0000");

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel51.setText("Unpaid");

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel49.setText("Total");

        numTotalBill.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        numTotalBill.setText("0000");

        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/search.png"))); // NOI18N
        jLabel66.setText("FIND");

        javax.swing.GroupLayout billMainPNLayout = new javax.swing.GroupLayout(billMainPN);
        billMainPN.setLayout(billMainPNLayout);
        billMainPNLayout.setHorizontalGroup(
            billMainPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 919, Short.MAX_VALUE)
            .addGroup(billMainPNLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(billMainPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, billMainPNLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(numTotalBill)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel49)
                        .addGap(39, 39, 39)
                        .addComponent(numPaid)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel47)
                        .addGap(29, 29, 29)
                        .addComponent(numUnpaid)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel51)))
                .addContainerGap())
            .addGroup(billMainPNLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel66)
                .addGap(18, 18, 18)
                .addComponent(monthCS, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(yearCS, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(statusBillCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        billMainPNLayout.setVerticalGroup(
            billMainPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billMainPNLayout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(137, 137, 137)
                .addGroup(billMainPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(yearCS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(monthCS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusBillCB, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(billMainPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numPaid)
                    .addComponent(jLabel47)
                    .addComponent(numUnpaid)
                    .addComponent(jLabel51)
                    .addComponent(jLabel49)
                    .addComponent(numTotalBill))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        billDetailPN.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/invoice.png"))); // NOI18N

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        titleBill.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        titleBill.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleBill.setText("NULL");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Electric");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Water");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Internet");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setText("Trash");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setText("Room");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Price");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Price");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Price");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Price");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("Price");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("Total");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setText("Total");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setText("Total");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setText("Total");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setText("Total");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setText("Total money :");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel36.setText("Status            :");

        totalMoneyTxt.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        totalMoneyTxt.setText("0.0");

        statusBillTxt.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        statusBillTxt.setText("NULL");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(elecTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(WaterTxt)
                    .addComponent(InternetTxt)
                    .addComponent(trashTxt)
                    .addComponent(idRTxt))
                .addGap(39, 39, 39)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(priceRm, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(priceElec)
                    .addComponent(priceWater)
                    .addComponent(priceInternet)
                    .addComponent(priceTrash))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel31)
                        .addComponent(totalRm, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel32)
                        .addComponent(totalElec, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel33)
                        .addComponent(totalWater, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel34)
                        .addComponent(totalInternet, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel35)
                        .addComponent(totalTrash, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalMoneyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusBillTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(titleBill)
                .addGap(45, 45, 45)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel25)
                                            .addComponent(jLabel26)
                                            .addComponent(jLabel31))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(idRTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(priceRm, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(totalRm, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGap(32, 32, 32)
                                                .addComponent(jLabel15)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(elecTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel32)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(totalElec, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(priceElec, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(WaterTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel33)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(totalWater, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(priceWater, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(InternetTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalInternet, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(priceInternet, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trashTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(priceTrash, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalTrash, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(totalMoneyTxt))
                .addGap(45, 45, 45)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(statusBillTxt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        paidBT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        paidBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/paid64.png"))); // NOI18N
        paidBT.setText("PAID");
        paidBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paidBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout billDetailPNLayout = new javax.swing.GroupLayout(billDetailPN);
        billDetailPN.setLayout(billDetailPNLayout);
        billDetailPNLayout.setHorizontalGroup(
            billDetailPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billDetailPNLayout.createSequentialGroup()
                .addGroup(billDetailPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(billDetailPNLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(paidBT, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        billDetailPNLayout.setVerticalGroup(
            billDetailPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billDetailPNLayout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(paidBT, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, billDetailPNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLayeredPane2.setLayer(billMainPN, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(billDetailPN, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(billMainPN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(billDetailPN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(billMainPN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(billDetailPN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout billPanelLayout = new javax.swing.GroupLayout(billPanel);
        billPanel.setLayout(billPanelLayout);
        billPanelLayout.setHorizontalGroup(
            billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billPanelLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane2))
        );
        billPanelLayout.setVerticalGroup(
            billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLayeredPane2)
        );

        servicePanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 204, 204));

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-house-56.png"))); // NOI18N

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Service Management");

        timeLB2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timeLB2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLB2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-clock-32.png"))); // NOI18N

        dateLB2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dateLB2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateLB2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-calendar-32.png"))); // NOI18N

        backServiceBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backServiceBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-back-arrow-32.png"))); // NOI18N
        backServiceBT.setText("BACK TO HOME SCREEN");
        backServiceBT.setToolTipText("");
        backServiceBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backServiceBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
            .addComponent(timeLB2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dateLB2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backServiceBT)
                .addGap(57, 57, 57))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addGap(23, 23, 23)
                .addComponent(timeLB2)
                .addGap(18, 18, 18)
                .addComponent(dateLB2)
                .addGap(167, 167, 167)
                .addComponent(backServiceBT, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 204), 1, true));

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/plugin.png"))); // NOI18N

        changeElecBT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        changeElecBT.setText("CHANGE");
        changeElecBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeElecBTActionPerformed(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel54.setText("Name:");

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel62.setText("ID Service:");

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel64.setText("Price:");

        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel65.setText("Unit:");

        jLabel56.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel56.setText("D");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel58.setText("Electric");

        priceElecTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        priceElecTxt.setText("3500");

        unitElec.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        unitElec.setText("kWh");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel62)
                            .addComponent(jLabel64)
                            .addComponent(jLabel65))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel56)
                            .addComponent(jLabel58)
                            .addComponent(priceElecTxt)
                            .addComponent(unitElec)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(changeElecBT)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(jLabel56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(jLabel58))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(priceElecTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(unitElec))
                .addGap(18, 18, 18)
                .addComponent(changeElecBT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 204), 1, true));

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel109.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/faucet.png"))); // NOI18N

        changeWaterBT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        changeWaterBT.setText("CHANGE");
        changeWaterBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeWaterBTActionPerformed(evt);
            }
        });

        jLabel110.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel110.setText("Name:");

        jLabel111.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel111.setText("ID Service:");

        jLabel112.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel112.setText("Price:");

        jLabel113.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel113.setText("Unit:");

        jLabel114.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel114.setText("N");

        jLabel115.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel115.setText("Water");

        priceWaterTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        priceWaterTxt.setText("20000");

        unitWater.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        unitWater.setText("Khối");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel111)
                    .addComponent(jLabel112)
                    .addComponent(jLabel113))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel114)
                    .addComponent(jLabel115)
                    .addComponent(priceWaterTxt)
                    .addComponent(unitWater))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(changeWaterBT)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel109)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel111)
                    .addComponent(jLabel114))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel110)
                    .addComponent(jLabel115))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel112)
                    .addComponent(priceWaterTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel113)
                    .addComponent(unitWater))
                .addGap(18, 18, 18)
                .addComponent(changeWaterBT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 204), 1, true));

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel118.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/trash.png"))); // NOI18N

        changeTrashBT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        changeTrashBT.setText("CHANGE");
        changeTrashBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeTrashBTActionPerformed(evt);
            }
        });

        jLabel119.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel119.setText("Name:");

        jLabel120.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel120.setText("ID Service:");

        jLabel121.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel121.setText("Price:");

        jLabel122.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel122.setText("Unit:");

        jLabel123.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel123.setText("R");

        jLabel124.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel124.setText("Electric");

        priceTrashTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        priceTrashTxt.setText("20000");

        unitTrash.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        unitTrash.setText("room/month");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel118, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel120)
                    .addComponent(jLabel121)
                    .addComponent(jLabel122))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel123)
                    .addComponent(jLabel124)
                    .addComponent(priceTrashTxt)
                    .addComponent(unitTrash))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(changeTrashBT)
                .addGap(47, 47, 47))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel118)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel120)
                    .addComponent(jLabel123))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel119)
                    .addComponent(jLabel124))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel121)
                    .addComponent(priceTrashTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel122)
                    .addComponent(unitTrash))
                .addGap(18, 18, 18)
                .addComponent(changeTrashBT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 204), 1, true));

        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel127.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/wifi-router.png"))); // NOI18N

        changeWfBT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        changeWfBT.setText("CHANGE");
        changeWfBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeWfBTActionPerformed(evt);
            }
        });

        jLabel128.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel128.setText("Name:");

        jLabel129.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel129.setText("ID Service:");

        jLabel130.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel130.setText("Price:");

        jLabel131.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel131.setText("Unit:");

        jLabel132.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel132.setText("I");

        jLabel133.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel133.setText("Internet");

        priceWfTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        priceWfTxt.setText("150000");

        unitWF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        unitWF.setText("room/month");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel127, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel129)
                            .addComponent(jLabel130)
                            .addComponent(jLabel131))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel132)
                            .addComponent(jLabel133)
                            .addComponent(priceWfTxt)
                            .addComponent(unitWF)))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(changeWfBT)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel127)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel129)
                    .addComponent(jLabel132))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel128)
                    .addComponent(jLabel133))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel130)
                    .addComponent(priceWfTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel131)
                    .addComponent(unitWF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(changeWfBT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout servicePanelLayout = new javax.swing.GroupLayout(servicePanel);
        servicePanel.setLayout(servicePanelLayout);
        servicePanelLayout.setHorizontalGroup(
            servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servicePanelLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(287, 287, 287)
                .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(109, 109, 109))
        );
        servicePanelLayout.setVerticalGroup(
            servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(servicePanelLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(63, 63, 63)
                .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        customerPanel.setBackground(new java.awt.Color(255, 255, 255));
        customerPanel.setPreferredSize(new java.awt.Dimension(1222, 652));

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-user-list-66.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Customers Management");

        timeLB.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timeLB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-clock-32.png"))); // NOI18N

        dateLB.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dateLB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateLB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-calendar-32.png"))); // NOI18N

        backCusBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backCusBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-back-arrow-32.png"))); // NOI18N
        backCusBT.setText("Back to home screen");
        backCusBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backCusBTActionPerformed(evt);
            }
        });

        changeCusBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        changeCusBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-more-info-32.png"))); // NOI18N
        changeCusBT.setText("Change customer");
        changeCusBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeCusBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(timeLB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dateLB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backCusBT, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changeCusBT, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(23, 23, 23)
                .addComponent(timeLB)
                .addGap(18, 18, 18)
                .addComponent(dateLB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(changeCusBT, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(backCusBT, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );

        customerTB.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 0));
        customerTB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CMND/CCCD", "Full name", "Sex", "Date of birth", "Phone number", "Address", "Job", "Leases"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        customerTB.setGridColor(new java.awt.Color(255, 255, 255));
        customerTB.setRowHeight(30);
        customerTB.setSelectionBackground(new java.awt.Color(255, 204, 204));
        customerTB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        customerTB.setShowGrid(true);
        jScrollPane1.setViewportView(customerTB);
        if (customerTB.getColumnModel().getColumnCount() > 0) {
            customerTB.getColumnModel().getColumn(0).setPreferredWidth(50);
            customerTB.getColumnModel().getColumn(1).setPreferredWidth(130);
            customerTB.getColumnModel().getColumn(2).setPreferredWidth(10);
            customerTB.getColumnModel().getColumn(3).setPreferredWidth(40);
            customerTB.getColumnModel().getColumn(4).setPreferredWidth(45);
            customerTB.getColumnModel().getColumn(5).setPreferredWidth(170);
            customerTB.getColumnModel().getColumn(6).setPreferredWidth(30);
            customerTB.getColumnModel().getColumn(7).setPreferredWidth(10);
        }

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("CUSTOMER LIST");

        findCustomerTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                findCustomerTxtKeyReleased(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/search-profile.png"))); // NOI18N
        jLabel61.setText("FIND NAME");

        javax.swing.GroupLayout customerPanelLayout = new javax.swing.GroupLayout(customerPanel);
        customerPanel.setLayout(customerPanelLayout);
        customerPanelLayout.setHorizontalGroup(
            customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 885, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 947, Short.MAX_VALUE)
                    .addGroup(customerPanelLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(findCustomerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        customerPanelLayout.setVerticalGroup(
            customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerPanelLayout.createSequentialGroup()
                .addContainerGap(102, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(findCustomerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/11.png"))); // NOI18N

        timeTxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        timeTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeTxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-clock-32.png"))); // NOI18N

        dateTxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dateTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateTxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-calendar-32.png"))); // NOI18N

        textBg2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        textBg2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textBg2.setText("CHÀO MỪNG ĐẾN VỚI PHẦN MỀM QUẢN LÝ PHÒNG TRỌ");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(timeTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dateTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(textBg2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1284, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(timeTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateTxt)
                .addGap(33, 33, 33)
                .addComponent(textBg2)
                .addContainerGap(384, Short.MAX_VALUE))
        );

        roomPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-house-56.png"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Room Management");

        timeLB1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timeLB1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLB1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-clock-32.png"))); // NOI18N

        dateLB1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dateLB1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateLB1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-calendar-32.png"))); // NOI18N

        roomDetailsBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        roomDetailsBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-info-32.png"))); // NOI18N
        roomDetailsBT.setText("ROOM DETAILS");
        roomDetailsBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomDetailsBTActionPerformed(evt);
            }
        });

        backRoomBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backRoomBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-back-arrow-32.png"))); // NOI18N
        backRoomBT.setText("BACK TO HOME SCREEN");
        backRoomBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backRoomBTActionPerformed(evt);
            }
        });

        addRoomBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addRoomBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/house.png"))); // NOI18N
        addRoomBT.setText("ADD ROOM");
        addRoomBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRoomBTActionPerformed(evt);
            }
        });

        motelButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        motelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/motel.png"))); // NOI18N
        motelButton.setText("MOTEL MANAGEMENT");
        motelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                motelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(timeLB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dateLB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(backRoomBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roomDetailsBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addRoomBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(motelButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addGap(23, 23, 23)
                .addComponent(timeLB1)
                .addGap(18, 18, 18)
                .addComponent(dateLB1)
                .addGap(43, 43, 43)
                .addComponent(motelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(addRoomBT, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(roomDetailsBT, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(backRoomBT, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );

        roomMainPN.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ROOM LIST");

        numTotalRM.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        numTotalRM.setText("0000");

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel53.setText("Total:");

        numRentedRM.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        numRentedRM.setText("0000");

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel55.setText("Rented:");

        numUnrentedRM.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        numUnrentedRM.setText("0000");

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel57.setText("Unrented:");

        motelCB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        motelCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL" }));
        motelCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                motelCBActionPerformed(evt);
            }
        });

        roomListTB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Room", "Motel", "Status room"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        roomListTB.setGridColor(new java.awt.Color(255, 255, 255));
        roomListTB.setRowHeight(30);
        roomListTB.setSelectionBackground(new java.awt.Color(255, 204, 204));
        roomListTB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        roomListTB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roomListTBMousePressed(evt);
            }
        });
        jScrollPane8.setViewportView(roomListTB);

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/search-house.png"))); // NOI18N
        jLabel63.setText("FIND");

        deleteRoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/del.png"))); // NOI18N
        deleteRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRoomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roomMainPNLayout = new javax.swing.GroupLayout(roomMainPN);
        roomMainPN.setLayout(roomMainPNLayout);
        roomMainPNLayout.setHorizontalGroup(
            roomMainPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 962, Short.MAX_VALUE)
            .addGroup(roomMainPNLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roomMainPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roomMainPNLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numTotalRM)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numRentedRM)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numUnrentedRM, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roomMainPNLayout.createSequentialGroup()
                        .addComponent(jLabel63)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(motelCB, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(deleteRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roomMainPNLayout.setVerticalGroup(
            roomMainPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roomMainPNLayout.createSequentialGroup()
                .addGroup(roomMainPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roomMainPNLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel5)
                        .addGap(74, 74, 74)
                        .addGroup(roomMainPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(motelCB, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel63)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roomMainPNLayout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(deleteRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addGroup(roomMainPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numTotalRM)
                    .addComponent(jLabel55)
                    .addComponent(numUnrentedRM)
                    .addComponent(jLabel57)
                    .addComponent(jLabel53)
                    .addComponent(numRentedRM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8)
                .addContainerGap())
        );

        detailRoomPN.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/accomodation.png"))); // NOI18N

        nameRoom.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        nameRoom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameRoom.setText("NULL");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Max quantity:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Current quantity:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Price room:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Status:");

        maxTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maxTxt.setText("0");

        crrTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        crrTxt.setText("0");

        priceRmTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        priceRmTxt.setText("NULL");

        statusRmTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        statusRmTxt.setText("NULL");

        createBillBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        createBillBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-bill-32.png"))); // NOI18N
        createBillBT.setText("Create bill");
        createBillBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBillBTActionPerformed(evt);
            }
        });

        addCusBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addCusBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-add-32.png"))); // NOI18N
        addCusBT.setText("Add customer");
        addCusBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCusBTActionPerformed(evt);
            }
        });

        changeRoomBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        changeRoomBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-change-contract-32.png"))); // NOI18N
        changeRoomBT.setText("Change infomation");
        changeRoomBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeRoomBTActionPerformed(evt);
            }
        });

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        detailTB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tenant's name", "CMND/CCCD", "Date of hire"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        detailTB.setGridColor(new java.awt.Color(255, 255, 255));
        detailTB.setRowHeight(30);
        detailTB.setSelectionBackground(new java.awt.Color(255, 204, 204));
        detailTB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        detailTB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(detailTB);

        jTabbedPane1.addTab("Customer list", jScrollPane5);

        rentalBillTB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Bill", "Collection date", "Total money", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rentalBillTB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rentalBillTB.setGridColor(new java.awt.Color(255, 255, 255));
        rentalBillTB.setRowHeight(30);
        rentalBillTB.setSelectionBackground(new java.awt.Color(255, 204, 204));
        rentalBillTB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        rentalBillTB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(rentalBillTB);

        jTabbedPane1.addTab("Rental bill list", jScrollPane4);

        contractDetailWithRoomTB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Contract", "CMND", "Customer name", "Start date", "End date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        contractDetailWithRoomTB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contractDetailWithRoomTB.setGridColor(new java.awt.Color(255, 255, 255));
        contractDetailWithRoomTB.setRowHeight(30);
        contractDetailWithRoomTB.setSelectionBackground(new java.awt.Color(255, 204, 204));
        contractDetailWithRoomTB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        contractDetailWithRoomTB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(contractDetailWithRoomTB);

        jTabbedPane1.addTab("Contract detail", jScrollPane2);

        checkOutRoomBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        checkOutRoomBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/check-out.png"))); // NOI18N
        checkOutRoomBT.setText("Check-out");
        checkOutRoomBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkOutRoomBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout detailRoomPNLayout = new javax.swing.GroupLayout(detailRoomPN);
        detailRoomPN.setLayout(detailRoomPNLayout);
        detailRoomPNLayout.setHorizontalGroup(
            detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailRoomPNLayout.createSequentialGroup()
                .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailRoomPNLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(detailRoomPNLayout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(detailRoomPNLayout.createSequentialGroup()
                                        .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(maxTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(crrTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(priceRmTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(statusRmTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(nameRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detailRoomPNLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                                .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addCusBT, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(createBillBT, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(99, 99, 99)
                                .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(changeRoomBT)
                                    .addComponent(checkOutRoomBT, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(97, 97, 97))))
                    .addGroup(detailRoomPNLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1)))
                .addContainerGap())
        );
        detailRoomPNLayout.setVerticalGroup(
            detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detailRoomPNLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(detailRoomPNLayout.createSequentialGroup()
                        .addComponent(nameRoom)
                        .addGap(30, 30, 30)
                        .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(maxTxt))
                        .addGap(18, 18, 18)
                        .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(crrTxt))
                        .addGap(18, 18, 18)
                        .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(priceRmTxt))
                        .addGap(18, 18, 18)
                        .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(statusRmTxt))
                        .addGap(35, 35, 35)
                        .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(changeRoomBT, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addCusBT))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(detailRoomPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createBillBT)
                    .addComponent(checkOutRoomBT))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLayeredPane3.setLayer(roomMainPN, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(detailRoomPN, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
        jLayeredPane3.setLayout(jLayeredPane3Layout);
        jLayeredPane3Layout.setHorizontalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roomMainPN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(detailRoomPN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane3Layout.setVerticalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roomMainPN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(detailRoomPN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roomPanelLayout = new javax.swing.GroupLayout(roomPanel);
        roomPanel.setLayout(roomPanelLayout);
        roomPanelLayout.setHorizontalGroup(
            roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roomPanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane3))
        );
        roomPanelLayout.setVerticalGroup(
            roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLayeredPane3)
        );

        contractPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 204, 204));

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-house-56.png"))); // NOI18N

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Contract Management");

        timeLB4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timeLB4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLB4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-clock-32.png"))); // NOI18N

        dateLB4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dateLB4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateLB4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-calendar-32.png"))); // NOI18N

        contractDetailBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        contractDetailBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-info-32.png"))); // NOI18N
        contractDetailBT.setText("CONTRACT DETAILS");
        contractDetailBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contractDetailBTActionPerformed(evt);
            }
        });

        backContractBT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backContractBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-back-arrow-32.png"))); // NOI18N
        backContractBT.setText("BACK TO HOME SCREEN");
        backContractBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backContractBTActionPerformed(evt);
            }
        });

        extendContract.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        extendContract.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-change-contract-32.png"))); // NOI18N
        extendContract.setText("Extend Contract");
        extendContract.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        extendContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                extendContractActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(timeLB4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dateLB4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(extendContract, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backContractBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contractDetailBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addGap(23, 23, 23)
                .addComponent(timeLB4)
                .addGap(18, 18, 18)
                .addComponent(dateLB4)
                .addGap(93, 93, 93)
                .addComponent(contractDetailBT, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(extendContract, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(backContractBT, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
        );

        mainContractPN.setBackground(new java.awt.Color(255, 255, 255));

        chooseRoomCB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        chooseRoomCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL" }));
        chooseRoomCB.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        chooseRoomCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chooseRoomCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseRoomCBActionPerformed(evt);
            }
        });

        contractDetailTB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CMND", "Customer name", "Contract", "Start date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        contractDetailTB.setGridColor(new java.awt.Color(255, 255, 255));
        contractDetailTB.setRowHeight(30);
        contractDetailTB.setSelectionBackground(new java.awt.Color(255, 204, 204));
        contractDetailTB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(contractDetailTB);

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("CONTRACT LIST");

        statusContractCB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        statusContractCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unexpired", "Expired" }));
        statusContractCB.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        statusContractCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        statusContractCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusContractCBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainContractPNLayout = new javax.swing.GroupLayout(mainContractPN);
        mainContractPN.setLayout(mainContractPNLayout);
        mainContractPNLayout.setHorizontalGroup(
            mainContractPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainContractPNLayout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(mainContractPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainContractPNLayout.createSequentialGroup()
                        .addComponent(chooseRoomCB, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(statusContractCB, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        mainContractPNLayout.setVerticalGroup(
            mainContractPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContractPNLayout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addGroup(mainContractPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chooseRoomCB, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusContractCB, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        detailContractPN.setBackground(new java.awt.Color(255, 255, 255));

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/contract.png"))); // NOI18N

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Contract Detail");

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel42.setText("CMND:");

        cmndContract.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmndContract.setText("null");

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel44.setText("Full name:");

        fnameContract.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        fnameContract.setText("null");

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel46.setText("ID Contract:");

        idCtr.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        idCtr.setText("null");

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel48.setText("Room rented:");

        rentedRM.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rentedRM.setText("null");

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel50.setText("Start day:");

        startD.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        startD.setText("null");

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel52.setText("End day:");

        endD.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        endD.setText("null");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel43.setText("Status:");

        statusContractDetail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        statusContractDetail.setForeground(new java.awt.Color(0, 140, 17));
        statusContractDetail.setText("null");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel42)
                            .addComponent(jLabel44)
                            .addComponent(jLabel48)
                            .addComponent(jLabel46)
                            .addComponent(jLabel50)
                            .addComponent(jLabel52)
                            .addComponent(jLabel43))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmndContract)
                                    .addComponent(fnameContract)
                                    .addComponent(idCtr)
                                    .addComponent(rentedRM)
                                    .addComponent(startD)
                                    .addComponent(endD))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 345, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(statusContractDetail)
                                .addGap(0, 345, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel41)
                .addGap(67, 67, 67)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(cmndContract))
                .addGap(41, 41, 41)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(fnameContract))
                .addGap(43, 43, 43)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(idCtr))
                .addGap(36, 36, 36)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(rentedRM))
                .addGap(39, 39, 39)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(startD))
                .addGap(38, 38, 38)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(endD))
                .addGap(40, 40, 40)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(statusContractDetail))
                .addContainerGap(166, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout detailContractPNLayout = new javax.swing.GroupLayout(detailContractPN);
        detailContractPN.setLayout(detailContractPNLayout);
        detailContractPNLayout.setHorizontalGroup(
            detailContractPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailContractPNLayout.createSequentialGroup()
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        detailContractPNLayout.setVerticalGroup(
            detailContractPNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(detailContractPNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLayeredPane4.setLayer(mainContractPN, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane4.setLayer(detailContractPN, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane4Layout = new javax.swing.GroupLayout(jLayeredPane4);
        jLayeredPane4.setLayout(jLayeredPane4Layout);
        jLayeredPane4Layout.setHorizontalGroup(
            jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainContractPN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(detailContractPN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane4Layout.setVerticalGroup(
            jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainContractPN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(detailContractPN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout contractPanelLayout = new javax.swing.GroupLayout(contractPanel);
        contractPanel.setLayout(contractPanelLayout);
        contractPanelLayout.setHorizontalGroup(
            contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contractPanelLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane4))
        );
        contractPanelLayout.setVerticalGroup(
            contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLayeredPane4)
        );

        jLayeredPane1.setLayer(statisticalPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(billPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(servicePanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(customerPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(mainPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(roomPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(contractPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(customerPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1287, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(servicePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(billPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(statisticalPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1287, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(roomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contractPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(customerPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(servicePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(billPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(statisticalPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(roomPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contractPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        customerMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-customers-32.png"))); // NOI18N
        customerMenu.setText("Customers Management");
        customerMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        customerMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        customerMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerMenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(customerMenu);

        roomMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-hostel-32.png"))); // NOI18N
        roomMenu.setText("Room Managament");
        roomMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        roomMenu.setFocusable(false);
        roomMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        roomMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomMenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(roomMenu);

        serviceMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-equipment-32.png"))); // NOI18N
        serviceMenu.setText("Services Managament");
        serviceMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        serviceMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        serviceMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                serviceMenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(serviceMenu);

        billMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-invoice-32.png"))); // NOI18N
        billMenu.setText("Bill Management");
        billMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        billMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        billMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                billMenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(billMenu);

        contractMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-contract-32.png"))); // NOI18N
        contractMenu.setText("Contract Management");
        contractMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        contractMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contractMenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(contractMenu);

        statisticalMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/pie-chart.png"))); // NOI18N
        statisticalMenu.setText("Statistical");
        statisticalMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        statisticalMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        statisticalMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                statisticalMenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(statisticalMenu);

        accountMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-admin-settings-male-32.png"))); // NOI18N
        accountMenu.setText("Account");
        accountMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        changeItem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        changeItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-password-reset-32.png"))); // NOI18N
        changeItem.setText("Change password");
        changeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeItemActionPerformed(evt);
            }
        });
        accountMenu.add(changeItem);

        logoutItem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        logoutItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-logout-32.png"))); // NOI18N
        logoutItem.setText("Sign out");
        logoutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutItemActionPerformed(evt);
            }
        });
        accountMenu.add(logoutItem);

        exitItem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        exitItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-circled-x-32.png"))); // NOI18N
        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        accountMenu.add(exitItem);

        jMenuBar1.add(accountMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(rootPane, "You want to close the program ?", "Message", JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_exitItemActionPerformed

    private void logoutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutItemActionPerformed
//        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(rootPane, "You want to sign out ?", "Message", JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION) {
            LoginView t = new LoginView();
            t.setVisible(true);
            t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(false);
        }
    }//GEN-LAST:event_logoutItemActionPerformed

    private void changeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeItemActionPerformed
        // TODO add your handling code here:
        ChangePasswdView change = new ChangePasswdView(this, rootPaneCheckingEnabled);
        change.setUserName(getUserName);
        change.setIconImage(img);
        change.setVisible(true);
    }//GEN-LAST:event_changeItemActionPerformed

    private void backCusBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backCusBTActionPerformed
        // TODO add your handling code here:
        
        mainPanel.setVisible(true);
        customerPanel.setVisible(false);
        roomPanel.setVisible(false);
        servicePanel.setVisible(false);
        billPanel.setVisible(false);
        statisticalPanel.setVisible(false);
        contractPanel.setVisible(false);

        dt.showTime(timeLB);
        dt.showDate(dateLB);

        this.roomMenu.setVisible(true);
        this.serviceMenu.setVisible(true);
        this.billMenu.setVisible(true);
        this.statisticalMenu.setVisible(true);
        this.accountMenu.setVisible(true);
        this.contractMenu.setVisible(true);
    }//GEN-LAST:event_backCusBTActionPerformed

    private void changeCusBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeCusBTActionPerformed
        // TODO add your handling code here:
        int cusIndex = customerTB.getSelectedRow();
        
        if (listCustomer.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No information");
        } else if (cusIndex == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please select a line you want to change");
        } else {
            ChangeCustomerView cg = new ChangeCustomerView(this, rootPaneCheckingEnabled);
            
            String cmnd = (String) modelCustomer.getValueAt(cusIndex, 0);
            String first = customerDAO.getFirstName(cmnd);
            String last = customerDAO.getLastName(cmnd);
            String sex = (String) modelCustomer.getValueAt(cusIndex, 2);
            String phone = (String) modelCustomer.getValueAt(cusIndex, 4);
            String date = (String) modelCustomer.getValueAt(cusIndex, 3);
            String address = (String) modelCustomer.getValueAt(cusIndex, 5);
            String job = (String) modelCustomer.getValueAt(cusIndex, 6);
            
            try {
                cg.setItems(cmnd, first, last, sex, phone, date, address, job);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            cg.setVisible(true);
            findCustomerTxt.setText("");
            showCustomerTable();
        }
    }//GEN-LAST:event_changeCusBTActionPerformed

    private void roomDetailsBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomDetailsBTActionPerformed
        // TODO add your handling code here:
        
        int detailIndex = roomListTB.getSelectedRow();
        
        if (listRoom.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No information");
            this.roomMainPN.setVisible(true);
            this.detailRoomPN.setVisible(false);
        } else if (detailIndex == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please select a line you want to change");
            this.roomMainPN.setVisible(true);
            this.detailRoomPN.setVisible(false);
        } else {
            
            if (!modelRoom.getValueAt(detailIndex, 2).equals("Chưa có người thuê")) {
                changeRoomBT.setEnabled(false);
            } else {
                changeRoomBT.setEnabled(true);
            }
            
            this.motelButton.setVisible(false);
            this.addRoomBT.setVisible(false);
            this.roomDetailsBT.setVisible(false);
            
            String idR = (String) modelRoom.getValueAt(detailIndex, 0);
            
            RoomModel r = roomDAO.getRoom(idR);
            
            nameRoom.setText("ROOM " + r.getIdRoom());
            
            showDetailRoom();
            showRentailBill(r.getIdRoom());
            showContractDetailWithRoom(r.getIdRoom());
            
            maxTxt.setText(String.valueOf(r.getMaxQuantity()));
            crrTxt.setText(String.valueOf(r.getCurrentQuantity()));
            priceRmTxt.setText(String.valueOf(r.getPriceRoom()));
            statusRmTxt.setText((String) modelRoom.getValueAt(detailIndex, 2));
            
            this.roomMainPN.setVisible(false);
            this.detailRoomPN.setVisible(true);
            
            
            int current = Integer.valueOf(crrTxt.getText());
            int max = Integer.valueOf(maxTxt.getText());
            
            if (current == 0) {
                createBillBT.setEnabled(false);
                checkOutRoomBT.setEnabled(false);
            } else {
                createBillBT.setEnabled(true);
                checkOutRoomBT.setEnabled(true);
            }
            
            if (current == max) {
                addCusBT.setEnabled(false);
            } else {
                addCusBT.setEnabled(true);
            }
            
        }
        
    }//GEN-LAST:event_roomDetailsBTActionPerformed

    private void backRoomBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backRoomBTActionPerformed
        // TODO add your handling code here:
        showCountRoom();
        if (this.detailRoomPN.isVisible()) {
            this.roomMainPN.setVisible(true);
            this.detailRoomPN.setVisible(false);
            this.motelButton.setVisible(true);
            this.addRoomBT.setVisible(true);
            this.roomDetailsBT.setVisible(true);
            showRoomTable();
            deleteRoom.setVisible(true);
        } else if (this.roomMainPN.isVisible()) {
            mainPanel.setVisible(true);
            customerPanel.setVisible(false);
            roomPanel.setVisible(false);
            servicePanel.setVisible(false);
            billPanel.setVisible(false);
            statisticalPanel.setVisible(false);
            contractPanel.setVisible(false);

            dt.showTime(timeLB);
            dt.showDate(dateLB);

            this.customerMenu.setVisible(true);
            this.serviceMenu.setVisible(true);
            this.billMenu.setVisible(true);
            this.statisticalMenu.setVisible(true);
            this.accountMenu.setVisible(true);
            this.contractMenu.setVisible(true);
            showBillTB();
            
            showCustomerTable();
        }
    }//GEN-LAST:event_backRoomBTActionPerformed

    private void addCusBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCusBTActionPerformed
        // TODO add your handling code here:
        
        
        
        CheckNewOrOldCustomerView view = new CheckNewOrOldCustomerView(this, true);
        view.idR = nameRoom.getText().substring(5);
        view.priceRoom = Double.valueOf(priceRmTxt.getText());
        view.setVisible(true);
        
        
        RoomModel r = roomDAO.getRoom(nameRoom.getText().substring(5));
        maxTxt.setText(String.valueOf(r.getMaxQuantity()));
        crrTxt.setText(String.valueOf(r.getCurrentQuantity()));
        showDetailRoom();
        showRentailBill(r.getIdRoom());
        showContractDetailWithRoom(r.getIdRoom());
        
        if (roomDAO.getStatus(nameRoom.getText().substring(5)).equals("TT1")) {
            statusRmTxt.setText("Chưa có người thuê");
        } else if (roomDAO.getStatus(nameRoom.getText().substring(5)).equals("TT2")) {
            statusRmTxt.setText("Đã có người thuê");
        } else if (roomDAO.getStatus(nameRoom.getText().substring(5)).equals("TT3")) {
            statusRmTxt.setText("Đã đủ số lượng");
        } else if (roomDAO.getStatus(nameRoom.getText().substring(5)).equals("TT4")) {
            statusRmTxt.setText("Đang bảo trì");
        }
        
        int current = Integer.valueOf(crrTxt.getText());
        int max = Integer.valueOf(maxTxt.getText());
        
        if (current == 0) {
            createBillBT.setEnabled(false);
            checkOutRoomBT.setEnabled(false);
            changeRoomBT.setEnabled(true);
        } else {
            createBillBT.setEnabled(true);
            checkOutRoomBT.setEnabled(true);
            changeRoomBT.setEnabled(false);
        }
        
        if (current == max) {
            addCusBT.setEnabled(false);
        } else {
            addCusBT.setEnabled(true);
        }
        
    }//GEN-LAST:event_addCusBTActionPerformed

    private void createBillBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBillBTActionPerformed
        // TODO add your handling code here:
        String idC = contractDAO.getIDContract(nameRoom.getText().substring(5));
        int sttBill = billDAO.getMaxSTTBill(idC);
        if (sttBill > 0) {
            if (!billDAO.checkBillPaidOrUnPaid(sttBill)) {
                String idM = billDAO.getIDBill(sttBill);
                JOptionPane.showMessageDialog(rootPane, "Please pay the bill with ID: " + idM);
            } else {
                CreateBillView create = new CreateBillView(this, rootPaneCheckingEnabled);
                
                create.setIDR(nameRoom.getText().substring(5));
                create.setVisible(true);
                
                showRentailBill(nameRoom.getText().substring(5));
            }
        } else if (sttBill == 0) {
            CreateBillView create = new CreateBillView(this, rootPaneCheckingEnabled);

            create.setIDR(nameRoom.getText().substring(5));
            create.setVisible(true);

            showRentailBill(nameRoom.getText().substring(5));
        }
        

    }//GEN-LAST:event_createBillBTActionPerformed

    private void changeRoomBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeRoomBTActionPerformed
        // TODO add your handling code here:
        ChangeRoomView cg = new ChangeRoomView(this, rootPaneCheckingEnabled);
        String idR = nameRoom.getText().substring(5);
        String idM = roomDAO.getIDMotel(idR);
        int max = Integer.valueOf(maxTxt.getText());
        double price = Double.valueOf(priceRmTxt.getText());
        String sts = statusRmTxt.getText();
        
        cg.setModel(idR, idM, max, price, sts);
        cg.setVisible(true);
        
        maxTxt.setText(cg.max);
        statusRmTxt.setText(cg.s);
        priceRmTxt.setText(cg.prc);
        
    }//GEN-LAST:event_changeRoomBTActionPerformed

    private void billDetailBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billDetailBTActionPerformed
        // TODO add your handling code here:
        int billIndex = billTB.getSelectedRow();
        
        if (listBill.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No information");
        } else if (billIndex == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please select a line");
        } else {
            
            changeBillDetailBT.setVisible(false);
            billDetailBT.setVisible(false);
            String idBill = String.valueOf(billTB.getValueAt(billIndex, 0));
            
            titleBill.setText("BILL DETAIL: " + idBill);
            
            idRTxt.setText(String.valueOf(billTB.getValueAt(billIndex, 1)));
            
            priceRm.setText(String.valueOf(roomDAO.getPriceRoom(String.valueOf(billTB.getValueAt(billIndex, 1)))));
            totalRm.setText(priceRm.getText());
            
            float consumptionElec = billDetailDAO.getNewIndex(String.valueOf(billTB.getValueAt(billIndex, 0)), "D") - billDetailDAO.getOldIndex(String.valueOf(billTB.getValueAt(billIndex, 1)), "D");
            float comsumtionWater = billDetailDAO.getNewIndex(String.valueOf(billTB.getValueAt(billIndex, 0)), "N") - billDetailDAO.getOldIndex(String.valueOf(billTB.getValueAt(billIndex, 1)), "N");
            
            double totalElecTMP = billDetailDAO.getTotalPrice(String.valueOf(billTB.getValueAt(billIndex, 0)), "D");
            double totalWaterTMP = billDetailDAO.getTotalPrice(String.valueOf(billTB.getValueAt(billIndex, 0)), "N");
            double totalTrashTMP = billDetailDAO.getTotalPrice(String.valueOf(billTB.getValueAt(billIndex, 0)), "R");
            double totalInterTMP = billDetailDAO.getTotalPrice(String.valueOf(billTB.getValueAt(billIndex, 0)), "I");
            
            elecTxt.setText(String.valueOf(consumptionElec));
            WaterTxt.setText(String.valueOf(comsumtionWater));
            InternetTxt.setText("1");
            trashTxt.setText("1");
            
            priceElec.setText(String.valueOf(billDetailDAO.getPrice(idBill , "D")));
            priceWater.setText(String.valueOf(billDetailDAO.getPrice(idBill , "N")));
            priceInternet.setText(String.valueOf(billDetailDAO.getPrice(idBill , "I")));
            priceTrash.setText(String.valueOf(billDetailDAO.getPrice(idBill , "R")));
            
            totalElec.setText(String.valueOf(totalElecTMP));
            totalWater.setText(String.valueOf(totalWaterTMP));
            totalInternet.setText(String.valueOf(totalInterTMP));
            totalTrash.setText(String.valueOf(totalTrashTMP));
            
            totalMoneyTxt.setText(String.valueOf(billTB.getValueAt(billIndex, 3)) + " VND");
            if (billDAO.getStatusBill(String.valueOf(billTB.getValueAt(billIndex, 0)))) {
                statusBillTxt.setText("Paid");
                statusBillTxt.setForeground(new Color(0,140,17));
                paidBT.setVisible(false);
            } else {
                statusBillTxt.setText("Unpaid");
                statusBillTxt.setForeground(Color.red);
                paidBT.setVisible(true);
            }
            
            idRTxt.setEditable(false);
            priceRm.setEditable(false);
            totalRm.setEditable(false);
            
            elecTxt.setEditable(false);
            priceElec.setEditable(false);
            totalElec.setEditable(false);
            
            WaterTxt.setEditable(false);
            priceWater.setEditable(false);
            totalWater.setEditable(false);
            
            InternetTxt.setEditable(false);
            priceInternet.setEditable(false);
            totalInternet.setEditable(false);
            
            trashTxt.setEditable(false);
            priceTrash.setEditable(false);
            totalTrash.setEditable(false);
            
            billMainPN.setVisible(false);
            billDetailPN.setVisible(true);
        }
        
    }//GEN-LAST:event_billDetailBTActionPerformed

    private void backBillBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBillBTActionPerformed
        // TODO add your handling code here:
        if (this.billDetailPN.isVisible()) {
            showCountStatusBill();
            this.billMainPN.setVisible(true);
            this.billDetailPN.setVisible(false);
            changeBillDetailBT.setVisible(true);
            billDetailBT.setVisible(true);
            showBillTB();
        } else if (this.roomMainPN.isVisible()) {

            mainPanel.setVisible(true);
            customerPanel.setVisible(false);
            roomPanel.setVisible(false);
            servicePanel.setVisible(false);
            billPanel.setVisible(false);
            statisticalPanel.setVisible(false);
            contractPanel.setVisible(false);

            dt.showTime(timeLB);
            dt.showDate(dateLB);

            this.roomMenu.setVisible(true);
            this.serviceMenu.setVisible(true);
            this.customerMenu.setVisible(true);
            this.statisticalMenu.setVisible(true);
            this.accountMenu.setVisible(true);
            this.contractMenu.setVisible(true);

        }
        
    }//GEN-LAST:event_backBillBTActionPerformed

    private void paidBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paidBTActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(rootPane, "You want to pay the bill ?", "Message", JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION) {
            
            if (billDAO.updateStatusBill(titleBill.getText().substring(13), true)) {
                statusBillTxt.setText("Paid");
                paidBT.setVisible(false);
            }
            showBillTB();
        }
        

    }//GEN-LAST:event_paidBTActionPerformed

    private void contractDetailBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contractDetailBTActionPerformed
        // TODO add your handling code here:
        int contractIndex = contractDetailTB.getSelectedRow();
        
        if (listContractDetail.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No information");
        } else if (contractIndex == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please select a line");
        } else {
            
            cmndContract.setText(String.valueOf(contractDetailTB.getValueAt(contractIndex, 0)));
            fnameContract.setText(String.valueOf(contractDetailTB.getValueAt(contractIndex, 1)));
            idCtr.setText(String.valueOf(contractDetailTB.getValueAt(contractIndex, 2)));
            startD.setText(String.valueOf(contractDetailTB.getValueAt(contractIndex, 3)));
            endD.setText(detailDAO.getEndDate(cmndContract.getText(), idCtr.getText()));
            rentedRM.setText(contractDAO.getIDRoom(idCtr.getText()));
            
            if (String.valueOf(contractDetailTB.getValueAt(contractIndex, 4)).equals("Unexpired")) {
                statusContractDetail.setText(String.valueOf(contractDetailTB.getValueAt(contractIndex, 4)));
                
                statusContractDetail.setForeground(new Color(0, 140, 17));
            } else {
                statusContractDetail.setText(String.valueOf(contractDetailTB.getValueAt(contractIndex, 4)));
                statusContractDetail.setForeground(Color.red);
            }
            
            this.mainContractPN.setVisible(false);
            this.detailContractPN.setVisible(true);
            this.extendContract.setVisible(false);
            this.contractDetailBT.setVisible(false);
        }
    }//GEN-LAST:event_contractDetailBTActionPerformed

    private void backContractBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backContractBTActionPerformed
        // TODO add your handling code here:
        if (this.detailContractPN.isVisible()) {
            this.mainContractPN.setVisible(true);
            this.detailContractPN.setVisible(false);
            chooseRoomCB.setSelectedIndex(0);
            statusContractCB.setSelectedIndex(0);
            showContractDetailByStatusTB(true);
            this.extendContract.setVisible(true);
            this.contractDetailBT.setVisible(true);
        } else if (this.mainContractPN.isVisible()) {
            
            mainPanel.setVisible(true);
            customerPanel.setVisible(false);
            roomPanel.setVisible(false);
            servicePanel.setVisible(false);
            billPanel.setVisible(false);
            statisticalPanel.setVisible(false);
            contractPanel.setVisible(false);

            dt.showTime(timeLB);
            dt.showDate(dateLB);

            this.roomMenu.setVisible(true);
            this.serviceMenu.setVisible(true);
            this.billMenu.setVisible(true);
            this.statisticalMenu.setVisible(true);
            this.accountMenu.setVisible(true);
            this.customerMenu.setVisible(true);

            showRoomTable();
            showCustomerTable();
        }
        
    }//GEN-LAST:event_backContractBTActionPerformed

    private void addRoomBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRoomBTActionPerformed
        // TODO add your handling code here:
        AddRoomView rv = new AddRoomView(this, true);
        rv.setVisible(true);
        showRoomTable();
        showCountRoom();
    }//GEN-LAST:event_addRoomBTActionPerformed

    // Show items service
    private void showService() {
        priceElecTxt.setText(String.valueOf(serviceDAO.getPrice("D")));
        unitElec.setText(String.valueOf(serviceDAO.getUnit("D")));
        
        priceWaterTxt.setText(String.valueOf(serviceDAO.getPrice("N")));
        unitWater.setText(String.valueOf(serviceDAO.getUnit("N")));
        
        priceWfTxt.setText(String.valueOf(serviceDAO.getPrice("I")));
        unitWF.setText(String.valueOf(serviceDAO.getUnit("I")));
        
        priceTrashTxt.setText(String.valueOf(serviceDAO.getPrice("R")));
        unitTrash.setText(String.valueOf(serviceDAO.getUnit("R")));
    }
    

    private void changeElecBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeElecBTActionPerformed
        // TODO add your handling code here:
        double price = serviceDAO.getPrice("D");
        String unit = serviceDAO.getUnit("D");
        ChangeServiceView cv = new ChangeServiceView(this, true);
        cv.setItems("D", "Electric", String.valueOf(price), unit);
        cv.setVisible(true);
        priceElecTxt.setText(String.valueOf(serviceDAO.getPrice("D")));
        unitElec.setText(String.valueOf(serviceDAO.getUnit("D")));
        
    }//GEN-LAST:event_changeElecBTActionPerformed

    private void changeWaterBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeWaterBTActionPerformed
        // TODO add your handling code here:
        double price = serviceDAO.getPrice("N");
        String unit = serviceDAO.getUnit("N");
        ChangeServiceView cv = new ChangeServiceView(this, true);
        cv.setItems("N", "Water", String.valueOf(price), unit);
        cv.setVisible(true);
        priceWaterTxt.setText(String.valueOf(serviceDAO.getPrice("N")));
        unitWater.setText(String.valueOf(serviceDAO.getUnit("N")));
    }//GEN-LAST:event_changeWaterBTActionPerformed

    private void changeWfBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeWfBTActionPerformed
        // TODO add your handling code here:
        double price = serviceDAO.getPrice("I");
        String unit = serviceDAO.getUnit("I");
        ChangeServiceView cv = new ChangeServiceView(this, true);
        cv.setItems("I", "Internet", String.valueOf(price), unit);
        cv.setVisible(true);
        priceWfTxt.setText(String.valueOf(serviceDAO.getPrice("I")));
        unitWF.setText(String.valueOf(serviceDAO.getUnit("I")));
    }//GEN-LAST:event_changeWfBTActionPerformed

    private void changeTrashBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeTrashBTActionPerformed
        // TODO add your handling code here:
        double price = serviceDAO.getPrice("R");
        String unit = serviceDAO.getUnit("R");
        ChangeServiceView cv = new ChangeServiceView(this, true);
        cv.setItems("R", "Trash", String.valueOf(price), unit);
        cv.setVisible(true);
        priceTrashTxt.setText(String.valueOf(serviceDAO.getPrice("R")));
        unitTrash.setText(String.valueOf(serviceDAO.getUnit("R")));
    }//GEN-LAST:event_changeTrashBTActionPerformed

    private void backServiceBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backServiceBTActionPerformed
        // TODO add your handling code here:
        
        mainPanel.setVisible(true);
        customerPanel.setVisible(false);
        roomPanel.setVisible(false);
        servicePanel.setVisible(false);
        billPanel.setVisible(false);
        statisticalPanel.setVisible(false);
        contractPanel.setVisible(false);

        dt.showTime(timeLB);
        dt.showDate(dateLB);

        this.roomMenu.setVisible(true);
        this.customerMenu.setVisible(true);
        this.billMenu.setVisible(true);
        this.statisticalMenu.setVisible(true);
        this.accountMenu.setVisible(true);
        this.contractMenu.setVisible(true);
    }//GEN-LAST:event_backServiceBTActionPerformed

    private void showChartBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showChartBTActionPerformed
        // TODO add your handling code here:
        int idx = chartComboBox.getSelectedIndex();
        if (idx == 0) {
            showLineChartRoom();
        } else if (idx == 1) {
            showLineChartBillMonth();
        }
        

    }//GEN-LAST:event_showChartBTActionPerformed

    private void customerMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerMenuMouseClicked
        // TODO add your handling code here:
        mainPanel.setVisible(false);
        customerPanel.setVisible(true);
        roomPanel.setVisible(false);
        servicePanel.setVisible(false);
        billPanel.setVisible(false);
        statisticalPanel.setVisible(false);
        contractPanel.setVisible(false);
        
        dt.showTime(timeLB);
        dt.showDate(dateLB);
        
        this.roomMenu.setVisible(false);
        this.serviceMenu.setVisible(false);
        this.billMenu.setVisible(false);
        this.statisticalMenu.setVisible(false);
        this.accountMenu.setVisible(false);
        this.contractMenu.setVisible(false);
        
    }//GEN-LAST:event_customerMenuMouseClicked

    private void roomMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomMenuMouseClicked
        // TODO add your handling code here:
        deleteRoom.setVisible(true);
        mainPanel.setVisible(false);
        customerPanel.setVisible(false);
        roomPanel.setVisible(true);
        servicePanel.setVisible(false);
        billPanel.setVisible(false);
        statisticalPanel.setVisible(false);
        contractPanel.setVisible(false);
        
        dt.showTime(timeLB1);
        dt.showDate(dateLB1);
        
        this.customerMenu.setVisible(false);
        this.serviceMenu.setVisible(false);
        this.billMenu.setVisible(false);
        this.statisticalMenu.setVisible(false);
        this.accountMenu.setVisible(false);
        this.contractMenu.setVisible(false);
        

    }//GEN-LAST:event_roomMenuMouseClicked

    private void serviceMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_serviceMenuMouseClicked
        // TODO add your handling code here:
        mainPanel.setVisible(false);
        customerPanel.setVisible(false);
        roomPanel.setVisible(false);
        servicePanel.setVisible(true);
        billPanel.setVisible(false);
        statisticalPanel.setVisible(false);
        contractPanel.setVisible(false);
        
        dt.showTime(timeLB2);
        dt.showDate(dateLB2);
        
        this.roomMenu.setVisible(false);
        this.customerMenu.setVisible(false);
        this.billMenu.setVisible(false);
        this.statisticalMenu.setVisible(false);
        this.accountMenu.setVisible(false);
        this.contractMenu.setVisible(false);
    }//GEN-LAST:event_serviceMenuMouseClicked

    private void billMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billMenuMouseClicked
        // TODO add your handling code here:
        
        mainPanel.setVisible(false);
        customerPanel.setVisible(false);
        roomPanel.setVisible(false);
        servicePanel.setVisible(false);
        billPanel.setVisible(true);
        statisticalPanel.setVisible(false);
        contractPanel.setVisible(false);
        
        billMainPN.setVisible(true);
        billDetailPN.setVisible(false);
        dt.showTime(timeLB3);
        dt.showDate(dateLB4);
        
        showBillTB();
        this.roomMenu.setVisible(false);
        this.serviceMenu.setVisible(false);
        this.customerMenu.setVisible(false);
        this.statisticalMenu.setVisible(false);
        this.accountMenu.setVisible(false);
        this.contractMenu.setVisible(false);
    }//GEN-LAST:event_billMenuMouseClicked

    private void contractMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contractMenuMouseClicked
        // TODO add your handling code here:
        for (RoomModel i : listRoom) {
            chooseRoomCB.addItem(i.getIdRoom());
        }
        
        mainPanel.setVisible(false);
        customerPanel.setVisible(false);
        roomPanel.setVisible(false);
        servicePanel.setVisible(false);
        billPanel.setVisible(false);
        statisticalPanel.setVisible(false);
        contractPanel.setVisible(true);
        
        dt.showTime(timeLB4);
        dt.showDate(dateLB4);
        
        this.roomMenu.setVisible(false);
        this.serviceMenu.setVisible(false);
        this.billMenu.setVisible(false);
        this.statisticalMenu.setVisible(false);
        this.accountMenu.setVisible(false);
        this.customerMenu.setVisible(false);
        
    }//GEN-LAST:event_contractMenuMouseClicked

    private void statisticalMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statisticalMenuMouseClicked
        // TODO add your handling code here:
        mainPanel.setVisible(false);
        customerPanel.setVisible(false);
        roomPanel.setVisible(false);
        servicePanel.setVisible(false);
        billPanel.setVisible(false);
        statisticalPanel.setVisible(true);
        contractPanel.setVisible(false);
        
        this.roomMenu.setVisible(false);
        this.serviceMenu.setVisible(false);
        this.billMenu.setVisible(false);
        this.customerMenu.setVisible(false);
        this.accountMenu.setVisible(false);
        this.contractMenu.setVisible(false);
    }//GEN-LAST:event_statisticalMenuMouseClicked

    private void motelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_motelButtonActionPerformed
        // TODO add your handling code here:
        MotelManagementView motelV = new MotelManagementView(this, true);
        motelV.setVisible(true);
    }//GEN-LAST:event_motelButtonActionPerformed

    private void findCustomerTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findCustomerTxtKeyReleased
        // TODO add your handling code here:
        String s = findCustomerTxt.getText().trim();
        
        if (s.equals("")) {
            showCustomerTable();
        } else {
        showFindCustomerTable(findCustomerTxt.getText().trim());
        }
    }//GEN-LAST:event_findCustomerTxtKeyReleased

    private void motelCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_motelCBActionPerformed
        // TODO add your handling code here:
        int idx = motelCB.getSelectedIndex();
        if (idx != 0) {
            showCountRoomByMotel(motelCB.getItemAt(idx));
            showRoomByMotel(motelCB.getItemAt(idx));
        } else {
            showRoomTable();
            showCountRoom();
        }
    }//GEN-LAST:event_motelCBActionPerformed

    private void chooseRoomCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseRoomCBActionPerformed
        // TODO add your handling code here:
        int index = chooseRoomCB.getSelectedIndex();
        int index1 = statusContractCB.getSelectedIndex();        
        if (index != 0) {
            String idR = chooseRoomCB.getItemAt(index);
            if (statusContractCB.getItemAt(index1).equals("Unexpired")) {
                showContractByRoomStatus(idR, true);
            } else if (statusContractCB.getItemAt(index1).equals("Expired")) {
                showContractByRoomStatus(idR, false);
            }
        } else {
            if (statusContractCB.getItemAt(index1).equals("Unexpired")) {
                showContractDetailByStatusTB(true);
            } else if (statusContractCB.getItemAt(index1).equals("Expired")) {
                showContractDetailByStatusTB(false);
            } 
        }
    }//GEN-LAST:event_chooseRoomCBActionPerformed

    private void statusContractCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusContractCBActionPerformed
        // TODO add your handling code here:
        int index = chooseRoomCB.getSelectedIndex();
        int index1 = statusContractCB.getSelectedIndex();        
        if (index != 0) {
            String idR = chooseRoomCB.getItemAt(index);
            if (statusContractCB.getItemAt(index1).equals("Unexpired")) {
                showContractByRoomStatus(idR, true);
            } else if (statusContractCB.getItemAt(index1).equals("Expired")) {
                showContractByRoomStatus(idR, false);
            }
        } else {
            if (statusContractCB.getItemAt(index1).equals("Unexpired")) {
                showContractDetailByStatusTB(true);
            } else if (statusContractCB.getItemAt(index1).equals("Expired")) {
                showContractDetailByStatusTB(false);
            }
        }
    }//GEN-LAST:event_statusContractCBActionPerformed

    private void deleteRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRoomActionPerformed
        // TODO add your handling code here:
        int idx = roomListTB.getSelectedRow();

        if (roomDAO.checkExistRoom(String.valueOf(roomListTB.getValueAt(idx, 0)))) {
            if (JOptionPane.showConfirmDialog(rootPane, "You want to delete with ID" + String.valueOf(roomListTB.getValueAt(idx, 0)) + "?" , "Message", JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION) {
                if (roomDAO.deleteRoom(String.valueOf(roomListTB.getValueAt(idx, 0)))) {
                    JOptionPane.showMessageDialog(rootPane, "Successfully!!!");
                    showRoomTable();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Failed!!!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "The room with the ID: " + String.valueOf(roomListTB.getValueAt(idx, 0)) + " cannot be deleted because the room has a pre-contracted contract");
            showRoomTable();
        }
    }//GEN-LAST:event_deleteRoomActionPerformed

    private void backRoomBT1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backRoomBT1ActionPerformed
        // TODO add your handling code here:
        mainPanel.setVisible(true);
        customerPanel.setVisible(false);
        roomPanel.setVisible(false);
        servicePanel.setVisible(false);
        billPanel.setVisible(false);
        statisticalPanel.setVisible(false);
        contractPanel.setVisible(false);

        dt.showTime(timeLB);
        dt.showDate(dateLB);

        this.roomMenu.setVisible(true);
        this.serviceMenu.setVisible(true);
        this.billMenu.setVisible(true);
        this.statisticalMenu.setVisible(true);
        this.accountMenu.setVisible(true);
        this.customerMenu.setVisible(true);
    }//GEN-LAST:event_backRoomBT1ActionPerformed

    private void roomListTBMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomListTBMousePressed
        // TODO add your handling code here:
        int idx = roomListTB.getSelectedRow();
        if(idx != -1) {
            if(String.valueOf(roomListTB.getValueAt(idx, 2)).equals("Chưa có người thuê")) {
                deleteRoom.setVisible(true);
            }else {
                deleteRoom.setVisible(false);
            }
        }
    }//GEN-LAST:event_roomListTBMousePressed

    private void checkOutRoomBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkOutRoomBTActionPerformed
        // TODO add your handling code here:
        int idx = contractDetailWithRoomTB.getSelectedRow();
        if (listContractDetail.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No information");
        } else if (idx == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please select a line you want to check-out");
        } else {
            String idC = String.valueOf(motelContractDetail.getValueAt(idx, 0));
            int sttBill = billDAO.getMaxSTTBill(idC);

            if (sttBill > 0) {
                if (!billDAO.checkBillPaidOrUnPaid(sttBill)) {
                    JOptionPane.showMessageDialog(rootPane, "You can't check-out because you haven't paid the bill with ID: " + billDAO.getIDBill(sttBill));
                } else {
                    CheckOutView ch = new CheckOutView(this, true);
                    String cmnd = String.valueOf(motelContractDetail.getValueAt(idx, 1));
                    ch.setItems(nameRoom.getText().substring(5), idC, cmnd);
                    ch.setVisible(true);
                    if (ch.flag) {
                        
                        RoomModel r = roomDAO.getRoom(nameRoom.getText().substring(5));
                        maxTxt.setText(String.valueOf(r.getMaxQuantity()));
                        crrTxt.setText(String.valueOf(r.getCurrentQuantity()));
                        
                        showContractDetailWithRoom(r.getIdRoom());
                        showDetailRoom();
                        showRentailBill(r.getIdRoom());

                        if (roomDAO.getStatus(nameRoom.getText().substring(5)).equals("TT1")) {
                            statusRmTxt.setText("Chưa có người thuê");
                        } else if (roomDAO.getStatus(nameRoom.getText().substring(5)).equals("TT2")) {
                            statusRmTxt.setText("Đã có người thuê");
                        } else if (roomDAO.getStatus(nameRoom.getText().substring(5)).equals("TT3")) {
                            statusRmTxt.setText("Đã đủ số lượng");
                        } else if (roomDAO.getStatus(nameRoom.getText().substring(5)).equals("TT4")) {
                            statusRmTxt.setText("Đang bảo trì");
                        }

                        int current = Integer.valueOf(crrTxt.getText());
                        int max = Integer.valueOf(maxTxt.getText());

                        if (current == 0) {
                            createBillBT.setEnabled(false);
                            checkOutRoomBT.setEnabled(false);
                        } else {
                            createBillBT.setEnabled(true);
                            checkOutRoomBT.setEnabled(true);
                        }

                        if (current == max) {
                            addCusBT.setEnabled(false);
                        } else {
                            addCusBT.setEnabled(true);
                        }
                    }
                }
            } else {
                CheckOutView ch = new CheckOutView(this, true);
                String cmnd = String.valueOf(motelContractDetail.getValueAt(idx, 1));
                ch.setItems(nameRoom.getText().substring(5), idC, cmnd);
                ch.setVisible(true);
                if (ch.flag) {
                    
                    RoomModel r = roomDAO.getRoom(nameRoom.getText().substring(5));
                    maxTxt.setText(String.valueOf(r.getMaxQuantity()));
                    crrTxt.setText(String.valueOf(r.getCurrentQuantity()));

                    showContractDetailWithRoom(r.getIdRoom());
                    showDetailRoom();
                    showRentailBill(r.getIdRoom());

                    if (roomDAO.getStatus(nameRoom.getText().substring(5)).equals("TT1")) {
                        statusRmTxt.setText("Chưa có người thuê");
                    } else if (roomDAO.getStatus(nameRoom.getText().substring(5)).equals("TT2")) {
                        statusRmTxt.setText("Đã có người thuê");
                    } else if (roomDAO.getStatus(nameRoom.getText().substring(5)).equals("TT3")) {
                        statusRmTxt.setText("Đã đủ số lượng");
                    } else if (roomDAO.getStatus(nameRoom.getText().substring(5)).equals("TT4")) {
                        statusRmTxt.setText("Đang bảo trì");
                    }

                    int current = Integer.valueOf(crrTxt.getText());
                    int max = Integer.valueOf(maxTxt.getText());

                    if (current == 0) {
                        createBillBT.setEnabled(false);
                        checkOutRoomBT.setEnabled(false);
                    } else {
                        createBillBT.setEnabled(true);
                        checkOutRoomBT.setEnabled(true);
                    }

                    if (current == max) {
                        addCusBT.setEnabled(false);
                    } else {
                        addCusBT.setEnabled(true);
                    }
                }
            }
        }
    }//GEN-LAST:event_checkOutRoomBTActionPerformed

    private void changeBillDetailBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeBillDetailBTActionPerformed
        // TODO add your handling code here:
        int changeBillIndex = billTB.getSelectedRow();
        
        if (listBill.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No information");
        } else if (changeBillIndex == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please select a line you want to change");
        } else {
            
            if (modelBill.getValueAt(changeBillIndex, 4).equals("Paid")) {
                JOptionPane.showMessageDialog(rootPane, "The bill not changed because the bill has been paid");
            } else {
                String idB = (String) modelBill.getValueAt(changeBillIndex, 0);

                float oldElec = billDetailDAO.getOldIndex(idB, "D");
                float oldWater = billDetailDAO.getOldIndex(idB, "N");

                float newWater = billDetailDAO.getNewIndex(idB, "N");
                float newElec = billDetailDAO.getNewIndex(idB, "D");
                ChangeBillView cView = new ChangeBillView(this, true);

                cView.setItems(idB, oldElec, oldWater, newElec, newWater, String.valueOf(modelBill.getValueAt(changeBillIndex, 1)));

                cView.setVisible(true); 
                
                showBillTB();
            }
        }
    }//GEN-LAST:event_changeBillDetailBTActionPerformed

    private void statusBillCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusBillCBActionPerformed
        // TODO add your handling code here:
        int index = statusBillCB.getSelectedIndex();
        showCountStatusBill();
        if (index != 0) {
            if (String.valueOf(statusBillCB.getItemAt(index)).equals("Unpaid")) {
                showBillStatusTB(false);
            } else if (String.valueOf(statusBillCB.getItemAt(index)).equals("Paid")) {
                showBillStatusTB(true);
            }
        } else {
            showBillTB();
        }
    }//GEN-LAST:event_statusBillCBActionPerformed

    private void extendContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_extendContractActionPerformed
        // TODO add your handling code here:
        ExtendContractView view = new ExtendContractView(this, true);
        view.setVisible(true);
        showContractDetailByStatusTB(true);
    }//GEN-LAST:event_extendContractActionPerformed

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
//            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new AdminView().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField InternetTxt;
    private javax.swing.JTextField WaterTxt;
    private javax.swing.JMenu accountMenu;
    private javax.swing.JButton addCusBT;
    private javax.swing.JButton addRoomBT;
    private javax.swing.JButton backBillBT;
    private javax.swing.JButton backContractBT;
    private javax.swing.JButton backCusBT;
    private javax.swing.JButton backRoomBT;
    private javax.swing.JButton backRoomBT1;
    private javax.swing.JButton backServiceBT;
    private javax.swing.JButton billDetailBT;
    private javax.swing.JPanel billDetailPN;
    private javax.swing.JPanel billMainPN;
    private javax.swing.JMenu billMenu;
    private javax.swing.JPanel billPanel;
    private javax.swing.JTable billTB;
    private javax.swing.JButton changeBillDetailBT;
    private javax.swing.JButton changeCusBT;
    private javax.swing.JButton changeElecBT;
    private javax.swing.JMenuItem changeItem;
    private javax.swing.JButton changeRoomBT;
    private javax.swing.JButton changeTrashBT;
    private javax.swing.JButton changeWaterBT;
    private javax.swing.JButton changeWfBT;
    private javax.swing.JComboBox<String> chartComboBox;
    private javax.swing.JButton checkOutRoomBT;
    private javax.swing.JComboBox<String> chooseRoomCB;
    private javax.swing.JLabel cmndContract;
    private javax.swing.JButton contractDetailBT;
    private javax.swing.JTable contractDetailTB;
    private javax.swing.JTable contractDetailWithRoomTB;
    private javax.swing.JMenu contractMenu;
    private javax.swing.JPanel contractPanel;
    private javax.swing.JButton createBillBT;
    private javax.swing.JLabel crrTxt;
    private javax.swing.ButtonGroup customerMainGR;
    private javax.swing.JMenu customerMenu;
    private javax.swing.JPanel customerPanel;
    private javax.swing.JTable customerTB;
    private javax.swing.JLabel dateLB;
    private javax.swing.JLabel dateLB1;
    private javax.swing.JLabel dateLB2;
    private javax.swing.JLabel dateLB3;
    private javax.swing.JLabel dateLB4;
    private javax.swing.JLabel dateTxt;
    private javax.swing.JButton deleteRoom;
    private javax.swing.JPanel detailContractPN;
    private javax.swing.JPanel detailRoomPN;
    private javax.swing.JTable detailTB;
    private javax.swing.JTextField elecTxt;
    private javax.swing.JLabel endD;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JButton extendContract;
    private javax.swing.JTextField findCustomerTxt;
    private javax.swing.JLabel fnameContract;
    private javax.swing.JLabel idCtr;
    private javax.swing.JTextField idRTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuItem logoutItem;
    private javax.swing.JPanel mainContractPN;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel maxTxt;
    private com.toedter.calendar.JMonthChooser monthCS;
    private javax.swing.JButton motelButton;
    private javax.swing.JComboBox<String> motelCB;
    private javax.swing.JLabel nameRoom;
    private javax.swing.JLabel numPaid;
    private javax.swing.JLabel numRentedRM;
    private javax.swing.JLabel numTotalBill;
    private javax.swing.JLabel numTotalRM;
    private javax.swing.JLabel numUnpaid;
    private javax.swing.JLabel numUnrentedRM;
    private javax.swing.JButton paidBT;
    private javax.swing.JPanel panelLineChart;
    private javax.swing.JTextField priceElec;
    private javax.swing.JLabel priceElecTxt;
    private javax.swing.JTextField priceInternet;
    private javax.swing.JTextField priceRm;
    private javax.swing.JLabel priceRmTxt;
    private javax.swing.JTextField priceTrash;
    private javax.swing.JLabel priceTrashTxt;
    private javax.swing.JTextField priceWater;
    private javax.swing.JLabel priceWaterTxt;
    private javax.swing.JLabel priceWfTxt;
    private javax.swing.JTable rentalBillTB;
    private javax.swing.JLabel rentedRM;
    private javax.swing.JButton roomDetailsBT;
    private javax.swing.JTable roomListTB;
    private javax.swing.JPanel roomMainPN;
    private javax.swing.JMenu roomMenu;
    private javax.swing.JPanel roomPanel;
    private javax.swing.JMenu serviceMenu;
    private javax.swing.JPanel servicePanel;
    private javax.swing.JButton showChartBT;
    private javax.swing.JLabel startD;
    private javax.swing.JMenu statisticalMenu;
    private javax.swing.JPanel statisticalPanel;
    private javax.swing.JComboBox<String> statusBillCB;
    private javax.swing.JLabel statusBillTxt;
    private javax.swing.JComboBox<String> statusContractCB;
    private javax.swing.JLabel statusContractDetail;
    private javax.swing.JLabel statusRmTxt;
    private javax.swing.JLabel textBg2;
    private javax.swing.JLabel timeLB;
    private javax.swing.JLabel timeLB1;
    private javax.swing.JLabel timeLB2;
    private javax.swing.JLabel timeLB3;
    private javax.swing.JLabel timeLB4;
    private javax.swing.JLabel timeTxt;
    private javax.swing.JLabel titleBill;
    private javax.swing.JTextField totalElec;
    private javax.swing.JTextField totalInternet;
    private javax.swing.JLabel totalMoneyTxt;
    private javax.swing.JTextField totalRm;
    private javax.swing.JTextField totalTrash;
    private javax.swing.JTextField totalWater;
    private javax.swing.JTextField trashTxt;
    private javax.swing.JLabel unitElec;
    private javax.swing.JLabel unitTrash;
    private javax.swing.JLabel unitWF;
    private javax.swing.JLabel unitWater;
    private com.toedter.calendar.JYearChooser yearCS;
    private com.toedter.calendar.JYearChooser yearChartSP;
    // End of variables declaration//GEN-END:variables
}
