package com.mannu;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.decorator.HighlightPredicate.RowGroupHighlightPredicate;

public class MainPage extends Thread {
	Connection con;
	String lognam;
	private JTable table;
	DefaultTableModel model;
	JComboBox comboBox;
	JLabel Pending,Tody,TotalRecord;
	JXDatePicker fdate,tdate;
	
	public MainPage(Connection connection) {
		this.con=connection;
	}
	public MainPage() {
		run();
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public void run() {
		JFrame frame=new JFrame("Pan Error Management");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				String dd=df.format(new java.util.Date());
				
				try {
					PreparedStatement ps=con.prepareStatement("dayrep '"+dd+"';");
					ResultSet rSet=ps.executeQuery();
					while (rSet.next()) {
						TotalRecord.setText("Total: "+rSet.getString(1)+",  ");
						Pending.setText("Pending: "+rSet.getString(2));
						
						PreparedStatement pps=con.prepareStatement("select count(*) from PanError where Flag!='P' and UpdateDate between '"+dd+" 00:00:00.000' and '"+dd+" 23:59:59.999'");
						ResultSet rs=pps.executeQuery();
						if (rs.next()) {
							Tody.setText("Today Complete: "+rs.getString(1)+",    Total Complete: "+rSet.getString(3)+",  ");
							
						}
						pps.close();
						rs.close();
					}
					ps.close();
					rSet.close();
					
					
					
					
				} catch (Exception e) {
					System.out.println("Error: "+e);
				}
				
				
			}
		});
		frame.setSize(844, 437);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Point p=new Point((int)width/6, (int)height/6);
        frame.setLocation(p);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Control Panel", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 818, 61);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Work Report", "Pdf Missing Report"}));
		comboBox.setBounds(87, 20, 158, 30);
		panel.add(comboBox);
		
		JLabel lblFrom = new JLabel("From:");
		lblFrom.setBounds(256, 25, 46, 14);
		panel.add(lblFrom);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(428, 26, 46, 14);
		panel.add(lblTo);
		
		JButton btnWorkAssign = new JButton("Get Report");
		btnWorkAssign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedItem().equals("Work Report")) {
										
					try {
						
						table.setModel(new DefaultTableModel(
								new Object[][] {
								},
								new String[] {
									"Sl No", "Operator Name", "Date", "Type", "Count"
								}
							));
						model=(DefaultTableModel) table.getModel();
						model.setRowCount(0);
						Object[] row=new Object[6];
						DateFormat fo=new SimpleDateFormat("yyyy-MM-dd");
						String fd=fo.format(fdate.getDate());
						String td=fo.format(tdate.getDate());
						int sl=0;
						PreparedStatement ps=con.prepareStatement("select Userid,CONVERT(varchar(20),UpdateDate,106),REMARKS,count(*) from PanError where Flag!='P' and UpdateDate between '"+fd+" 00:00:00.000' and '"+td+" 23:59:59.999' group by Userid,CONVERT(varchar(20),UpdateDate,106),REMARKS");
						ResultSet rs=ps.executeQuery();
						while (rs.next()) {
							sl=1+sl;
							row[0]=sl;
							row[1]=rs.getString(1).toUpperCase();
							row[2]=rs.getString(2);
							row[3]=rs.getString(3).toUpperCase();
							row[4]=rs.getString(4);
							model.addRow(row);
							
						}
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Error: "+e2);
					}
					
					
				} else if(comboBox.getSelectedItem().equals("Pdf Missing Report")){
					table.setModel(new DefaultTableModel(
							new Object[][] {
							},
							new String[] {
								"Sl No", "Inward No", "Ack No"
							}
						));
					try {
						model=(DefaultTableModel) table.getModel();
						model.setRowCount(0);
						Object[] row=new Object[4];
						int sl=0;
						PreparedStatement ps=con.prepareStatement("select InwardNo,AckNo from PanError where Flag='A' or Flag='V'");
						ResultSet rs=ps.executeQuery();
						while (rs.next()) {
							sl=1+sl;
							row[0]=sl;
							row[1]=rs.getString(1);
							row[2]=rs.getString(2);
							model.addRow(row);
						}
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Error: "+e2);
					}
					

				}
				
			}
		});
		btnWorkAssign.setBounds(593, 20, 110, 30);
		panel.add(btnWorkAssign);
		
		JButton btnReport = new JButton("Export");
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser=new JFileChooser();
				int retrive=chooser.showSaveDialog(btnReport);
				String path2=chooser.getSelectedFile().getPath();
				String path=path2+".xls";
				if (retrive==JFileChooser.APPROVE_OPTION) {
					try {
						ExcelExporter exporter=new ExcelExporter(table,path);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("path not set");
				}
				

				
			}
		});
		btnReport.setBounds(713, 20, 89, 30);
		panel.add(btnReport);
		
		JLabel lblReportType = new JLabel("Report Type:");
		lblReportType.setBounds(10, 25, 91, 14);
		panel.add(lblReportType);
		
		fdate = new JXDatePicker();
		fdate.setFormats(new String[] {"yyyy-MM-dd"});
		fdate.setDate(new java.util.Date());
		fdate.setBounds(288, 20, 130, 30);
		panel.add(fdate);
		
		tdate = new JXDatePicker();
		tdate.setFormats(new String[] {"yyyy-MM-dd"});
		tdate.setDate(new java.util.Date());
		tdate.setBounds(453, 20, 130, 30);
		panel.add(tdate);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 83, 818, 293);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnAddNewOperator = new JMenu("Add New Operator");
		mnAddNewOperator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				AddNewOperator ano=new AddNewOperator(con);
				ano.start();
				frame.dispose();
			}
		});
		menuBar.add(mnAddNewOperator);
		
		JLabel lblNewLabel = new JLabel("________________________________");
		lblNewLabel.setForeground(Color.WHITE);
		menuBar.add(lblNewLabel);

		TotalRecord = new JLabel("");
		TotalRecord.setForeground(Color.BLUE);
		menuBar.add(TotalRecord);
		
		Tody = new JLabel("");
		Tody.setForeground(Color.BLACK);
		menuBar.add(Tody);
		
		Pending = new JLabel("");
		Pending.setForeground(Color.RED);
		menuBar.add(Pending);
		

		
		
		frame.setVisible(true);
	}
}
