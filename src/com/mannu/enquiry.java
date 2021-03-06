package com.mannu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.mortennobel.imagescaling.ResampleOp;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class enquiry extends Thread{
	Connection con;
	String lognam;
	double w,h;
	JScrollPane imgscr;
	JLabel img;
	private JTable fnamtab;
	DefaultTableModel model;
	JLabel lblTxtFieldFocus;
	String userid;
	private JScrollPane scrollPane_1;
	private JTable inwardtab;
	private JLabel lblSetPath;
	private JTextField ackno;
	private JLabel lblInwardNo;
	private JTextField inwardno;
	private JPanel panel;
	private JLabel lblAppFirstName;
	private JTextField afnam;
	private JTextField sfnam;
	private JLabel lblSamFirstName;
	private JLabel lblAppMiddleName;
	private JTextField amnam;
	private JLabel lblSamMiddleName;
	private JTextField smnam;
	private JLabel lblAppLastName;
	private JTextField alnam;
	private JLabel lblSamLastName;
	private JTextField slnam;
	private JLabel lblAppNameOn;
	private JTextField afullnam;
	private JTextField sfullnam;
	private JLabel lblSamNameOn;
	private JLabel lblAppDob;
	private JTextField adob;
	private JTextField sdob;
	private JLabel lblSamDob;
	private JButton btnSetPath;
	private JButton btnPrevious;
	private JButton btnNext;
	private JLabel lblStatus;
	private JComboBox error;
	private JButton btnSave;
	private JButton btnExit;
	private JLabel lblSaveAlts;
	private JLabel lblSaveAlts1;
	String finward,tinward;
	DefaultTableModel model2;
	String filepath;
	int inselrow,fiselrow;
	double zoom = 1.0D;
	File fna;
	String na;
	JLabel ppath;
	private JMenu mnFlagDetails;
	JTable table;
	
	public enquiry(Connection connection, String string, String text) {
		this.con=connection;
		this.lognam=string;
		this.userid=text;
	}

	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void run() {
		JFrame frame=new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				
				File tmp=new File("C:\\temp\\");
				if(!tmp.exists()) {
					tmp.mkdir();
				}
			}
		});
		frame.setUndecorated(false);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		Toolkit tk=Toolkit.getDefaultToolkit();
		double w=tk.getScreenSize().getWidth();
		double h=tk.getScreenSize().getHeight();
		frame.setSize((int)w, (int)h);
	//	frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		imgscr=new JScrollPane();
		imgscr.setBounds(1, 1, (int)w-450, (int)h-55);
		frame.getContentPane().add(imgscr);

		img = new JLabel();
		imgscr.setViewportView(img);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds((int)w-440, 8, 210, 164);
		frame.getContentPane().add(scrollPane);
		
		fnamtab = new JTable();
		fnamtab.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"File Name"
			}
		));
		scrollPane.setViewportView(fnamtab);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds((int)w-220, 8, 210, 164);
		frame.getContentPane().add(scrollPane_1);
		inwardtab = new JTable();
		inwardtab.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"InwardNo"
			}
		));
		scrollPane_1.setViewportView(inwardtab);
		
		lblSetPath = new JLabel("Ack No");
		lblSetPath.setBounds((int)w-440, 179, 62, 14);
		frame.getContentPane().add(lblSetPath);
		
		ackno = new JTextField();
		ackno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent event) {
				char vchar = event.getKeyChar();
		        if ((!Character.isDigit(vchar)) || (vchar == '\b') || (vchar == '') || 
		          (ackno.getText().length() == 15)) {
		          event.consume();
		        }
			}
		});
		ackno.setBounds((int)w-440, 204, 202, 28);
		frame.getContentPane().add(ackno);
		ackno.setColumns(10);
		
		lblInwardNo = new JLabel("Inward No");
		lblInwardNo.setBounds((int)w-220, 179, 62, 14);
		frame.getContentPane().add(lblInwardNo);
		
		inwardno = new JTextField();
		inwardno.setEditable(false);
		inwardno.setColumns(10);
		inwardno.setBounds((int)w-220, 204, 202, 28);
		frame.getContentPane().add(inwardno);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "MetaData panel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds((int)w-440, 237, 429, 285);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		lblAppFirstName = new JLabel("App First Name:");
		lblAppFirstName.setBounds(10, 18, 128, 14);
		panel.add(lblAppFirstName);
		
		afnam = new JTextField();
		//afnam.setForeground(Color.RED);
		afnam.setBounds(10, 38, 184, 28);
		panel.add(afnam);
		afnam.setColumns(10);
		
		sfnam = new JTextField();
		sfnam.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				amnam.requestFocus();
			}
		});
		sfnam.setColumns(10);
		sfnam.setBounds(222, 38, 184, 28);
		panel.add(sfnam);
		
		lblSamFirstName = new JLabel("Sam First Name:");
		lblSamFirstName.setBounds(222, 18, 128, 14);
		panel.add(lblSamFirstName);
		
		lblAppMiddleName = new JLabel("App Middle Name:");
		lblAppMiddleName.setBounds(10, 69, 128, 14);
		panel.add(lblAppMiddleName);
		
		amnam = new JTextField();
		amnam.setColumns(10);
		amnam.setBounds(10, 89, 184, 28);
		panel.add(amnam);
		
		lblSamMiddleName = new JLabel("Sam Middle Name:");
		lblSamMiddleName.setBounds(222, 69, 128, 14);
		panel.add(lblSamMiddleName);
		
		smnam = new JTextField();
		smnam.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				alnam.requestFocus();
			}
		});
		smnam.setColumns(10);
		smnam.setBounds(222, 89, 184, 28);
		panel.add(smnam);
		
		lblAppLastName = new JLabel("App Last Name:");
		lblAppLastName.setBounds(10, 120, 128, 14);
		panel.add(lblAppLastName);
		
		alnam = new JTextField();
		alnam.setColumns(10);
		alnam.setBounds(10, 140, 184, 28);
		panel.add(alnam);
		
		lblSamLastName = new JLabel("Sam Last Name:");
		lblSamLastName.setBounds(222, 120, 128, 14);
		panel.add(lblSamLastName);
		
		slnam = new JTextField();
		slnam.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				afullnam.requestFocus();
			}
		});
		slnam.setColumns(10);
		slnam.setBounds(222, 140, 184, 28);
		panel.add(slnam);
		
		lblAppNameOn = new JLabel("App Name On Card");
		lblAppNameOn.setBounds(10, 174, 128, 14);
		panel.add(lblAppNameOn);
		
		afullnam = new JTextField();
		afullnam.setColumns(10);
		afullnam.setBounds(10, 194, 184, 28);
		panel.add(afullnam);
		
		sfullnam = new JTextField();
		sfullnam.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				adob.requestFocus();
			}
		});
		sfullnam.setColumns(10);
		sfullnam.setBounds(222, 194, 184, 28);
		panel.add(sfullnam);
		
		lblSamNameOn = new JLabel("Sam Name On Card");
		lblSamNameOn.setBounds(222, 174, 128, 14);
		panel.add(lblSamNameOn);
		
		lblAppDob = new JLabel("App DOB");
		lblAppDob.setBounds(10, 227, 128, 14);
		panel.add(lblAppDob);
		
		adob = new JTextField();
		adob.setColumns(10);
		adob.setBounds(10, 247, 184, 28);
		panel.add(adob);
		
		sdob = new JTextField();
		sdob.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				error.requestFocus();
			}
		});
		sdob.setColumns(10);
		sdob.setBounds(222, 247, 184, 28);
		panel.add(sdob);
		
		lblSamDob = new JLabel("Sam DOB");
		lblSamDob.setBounds(222, 227, 128, 14);
		panel.add(lblSamDob);
		
		btnSetPath = new JButton("Search");
		btnSetPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ackno.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter ACK No");
				} else {
					model=(DefaultTableModel) inwardtab.getModel();
					model.setRowCount(0);
					inwardno.setText("");
					try {
						model=(DefaultTableModel)inwardtab.getModel();
						Object[] inr=new Object[2];
						PreparedStatement ps=con.prepareStatement("select inwardno from panerror where ackno='"+ackno.getText()+"';");
						ResultSet rSet=ps.executeQuery();
						if (rSet.next()) {
							inr[0]=rSet.getString(1);
							model.addRow(inr);
							
						} else {
							JOptionPane.showMessageDialog(null, "Ack No Not Found");
							img.setIcon(null);
							
							
						}
						
						inwardtab.setRowSelectionInterval(0, 0);
			            
			            inselrow=inwardtab.getSelectedRow();
			            
			            inwardno.setText((String) model.getValueAt(inselrow, 0));
			            
			            int selval = 0;
						for (int row = 0; row <= fnamtab.getRowCount() - 1; row++) {
							String val=inwardno.getText().trim();
							String tval=fnamtab.getValueAt(row, 0).toString();
						 	if (tval.matches(val+"(.*)")) {
								System.out.println("Enter Row Number: "+row);
									selval=row;
							}
						}
						
						if(model2.getValueAt(selval, 0).toString().matches(inwardno.getText().trim()+"(.*)")) {
							System.out.println("DD: "+selval);
							fnamtab.setRowSelectionInterval(selval, selval);
							String selpdf=filepath+"//"+model2.getValueAt(fnamtab.getSelectedRow(), 0);
							try {
								PDDocument document = PDDocument.load(new File(selpdf));
								PDFRenderer pdfRenderer = new PDFRenderer(document);
								for (int i = 0; i < document.getNumberOfPages(); i++) {
									BufferedImage bim = pdfRenderer.renderImageWithDPI(i,90, ImageType.RGB);
									ImageIOUtil.writeImage(bim,"C:\\temp\\"+inwardno.getText()+i+".png",0);
								}
								document.close();
								img.setIcon(new ImageIcon("C:\\temp\\"+inwardno.getText()+"1.png"));
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(null, "Error: "+e2);
							}
							
							
						}else {
							error.setSelectedItem("Pdf Not Found");
							afnam.setText("");
							amnam.setText("");
							alnam.setText("");
							afullnam.setText("");
							adob.setText(".");
							sfnam.setText(".");
							smnam.setText(".");
							slnam.setText(".");
							sfullnam.setText(".");
							sdob.setText(".");
							JOptionPane.showMessageDialog(null, "File Not Found");
						}	
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Error: "+e2);
					}
					
					try {
						PreparedStatement ps1=con.prepareStatement("select FirstName,SamFirstName,MiddleName,SamMiddleName,LastName,SamLastName,NameOnCard,SamNameOnCard,DOB,SamDOB,flag from PanError where InwardNo="+inwardno.getText());
						ResultSet rs1=ps1.executeQuery();
						if (rs1.next()) {
							
							JOptionPane.showMessageDialog(null, "Ack No Current Flag: "+rs1.getString(11));
							if (!rs1.getString(1).equals(rs1.getString(2))) {
								
								afnam.setText(rs1.getString(1));
								sfnam.setText(rs1.getString(2));
								afnam.setEditable(true);
								sfnam.setEditable(false);
								afnam.setForeground(Color.RED);
								sfnam.setForeground(Color.RED);
							} else if(rs1.getString(1).equals(rs1.getString(2))){
								afnam.setText(rs1.getString(1));
								sfnam.setText(rs1.getString(2));
								afnam.setEditable(false);
								sfnam.setEditable(false);
								afnam.setForeground(Color.BLACK);
								sfnam.setForeground(Color.BLACK);
							}
							
							if (!rs1.getString(3).equals(rs1.getString(4))) {
								amnam.setText(rs1.getString(3));
								smnam.setText(rs1.getString(4));
								amnam.setEditable(true);
								smnam.setEditable(false);
								amnam.setForeground(Color.RED);
								smnam.setForeground(Color.RED);
								
							} else if(rs1.getString(3).equals(rs1.getString(4))) {
								amnam.setText(rs1.getString(3));
								smnam.setText(rs1.getString(4));
								amnam.setEditable(false);
								smnam.setEditable(false);
								amnam.setForeground(Color.BLACK);
								smnam.setForeground(Color.BLACK);
							}
							
							if (!rs1.getString(5).equals(rs1.getString(6))) {
								alnam.setText(rs1.getString(5));
								slnam.setText(rs1.getString(6));
								alnam.setEditable(true);
								slnam.setEditable(false);
								alnam.setForeground(Color.RED);
								slnam.setForeground(Color.RED);
							} else if(rs1.getString(5).equals(rs1.getString(6))){
								alnam.setText(rs1.getString(5));
								slnam.setText(rs1.getString(6));
								alnam.setEditable(false);
								slnam.setEditable(false);
								alnam.setForeground(Color.BLACK);
								slnam.setForeground(Color.BLACK);
							}
							
							if (!rs1.getString(7).equals(rs1.getString(8))) {
								afullnam.setText(rs1.getString(7));
								sfullnam.setText(rs1.getString(8));
								afullnam.setEditable(true);
								sfullnam.setEditable(false);
								afullnam.setForeground(Color.RED);
								sfullnam.setForeground(Color.RED);
							} else if(rs1.getString(7).equals(rs1.getString(8))){
								afullnam.setText(rs1.getString(7));
								sfullnam.setText(rs1.getString(8));
								afullnam.setEditable(false);
								sfullnam.setEditable(false);
								afullnam.setForeground(Color.BLACK);
								sfullnam.setForeground(Color.BLACK);
							}
							
							if (!rs1.getString(9).equals(rs1.getString(10))) {
								adob.setText(rs1.getString(9));
								sdob.setText(rs1.getString(10));
								adob.setEditable(true);
								sdob.setEditable(false);
								adob.setForeground(Color.RED);
								sdob.setForeground(Color.RED);
								
							} else if(rs1.getString(9).equals(rs1.getString(10))) {
								adob.setText(rs1.getString(9));
								sdob.setText(rs1.getString(10));
								adob.setEditable(false);
								sdob.setEditable(false);
								adob.setForeground(Color.BLACK);
								sdob.setForeground(Color.BLACK);
								
							}	
						}
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Error: "+e2);
					}
					
					
				}	
			}
		});
		btnSetPath.setBounds((int)w-430, 572, 94, 28);
		frame.getContentPane().add(btnSetPath);
		
		btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String imgfnam=img.getIcon().toString();
				imgfnam=imgfnam.substring(0, imgfnam.length()-4);
				System.out.println("Path: "+imgfnam);
				String lno=imgfnam.substring(imgfnam.length()-1);
				int nn=Integer.parseInt(lno)-1;
				
				String nexnam="C:\\temp\\"+inwardno.getText()+nn+".png";
				File nexfil=new File(nexnam);
				System.out.println("Next Path: "+nexnam);
				
				if (!nexfil.exists()) {
					JOptionPane.showMessageDialog(null, "This is First Page");
				} else if (nexfil.exists()) {
					img.setIcon(new ImageIcon(nexnam));
				}
			}
		});
		btnPrevious.setMnemonic(KeyEvent.VK_P);
		btnPrevious.setBounds((int)w-330, 572, 94, 28);
		frame.getContentPane().add(btnPrevious);
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String imgfnam=img.getIcon().toString();
				imgfnam=imgfnam.substring(0, imgfnam.length()-4);
				System.out.println("Path: "+imgfnam);
				String lno=imgfnam.substring(imgfnam.length()-1);
				int nn=Integer.parseInt(lno)+1;
				
				String nexnam="C:\\temp\\"+inwardno.getText()+nn+".png";
				File nexfil=new File(nexnam);
				System.out.println("Next Path: "+nexnam);
				
				if (!nexfil.exists()) {
					JOptionPane.showMessageDialog(null, "This is Last Page");
				} else if (nexfil.exists()) {
					img.setIcon(new ImageIcon(nexnam));
				}
			}
			
		});
		btnNext.setMnemonic(KeyEvent.VK_N);
		btnNext.setBounds((int)w-230, 572, 94, 28);
		frame.getContentPane().add(btnNext);
		
		lblStatus = new JLabel("Status:");
		lblStatus.setBounds((int)w-410, 540, 61, 14);
		frame.getContentPane().add(lblStatus);
		
		error = new JComboBox();
		error.setModel(new DefaultComboBoxModel(new String[] {"First Name Update", "Middle Name Update", "Last Name Update",  "Name On Card Update",  "DOB Update", "Multiple Update",  "Sam Error","Sam Error and First Name Update", "Sam Error and Middle Name Update", "Sam Error and Last Name Update","Sam Error and Full Name Update", "Sam Error and DOB Update" , "Sam Error and Multiple Update" ,"Pdf Not Found","Wrong PDF","ACK Mismatch","Reject"}));
		error.setBounds((int)w-360, 533, 224, 28);
		frame.getContentPane().add(error);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ackno.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Ack No");
				}else {
					model=(DefaultTableModel) inwardtab.getModel();
					
				try {
					if (error.getSelectedItem().equals("Name On Card Update")) {
						if (!afnam.getText().equals(sfnam.getText()) || !sfnam.getText().equals(afnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct First Name");
						} else if(!amnam.getText().equals(smnam.getText()) || !smnam.getText().equals(amnam.getText())){
							JOptionPane.showMessageDialog(null, "Please check and Correct Middle Name");
						}else if (!alnam.getText().equals(slnam.getText()) || !slnam.getText().equals(alnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct Last Name");
						}else if (!afullnam.getText().equals(sfullnam.getText()) || !sfullnam.getText().equals(afullnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct Full Name");
						}else if (!adob.getText().equals(sdob.getText()) || !sdob.getText().equals(adob.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct DOB");
						}else {
							
							String sadate=sdob.getText().substring(4)+"-"+sdob.getText().substring(2,4)+"-"+sdob.getText().substring(0,2);
							PreparedStatement pps=con.prepareStatement("update PanError set Flag='U',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
							
							pps.execute();
							
							PreparedStatement pss=con.prepareStatement("update pandetail set AppFirstName='"+sfnam.getText()+"', AppMiddleName='"+smnam.getText()+"', AppLastName='"+slnam.getText()+"', AppNameOnCard='"+sfullnam.getText()+"', AppDOB='"+sadate+"', FileGenerationFlag='U' where InhouseRefno="+inwardno.getText());
							
							pss.execute();
							
							model.removeRow(inwardtab.getSelectedRow());
							JOptionPane.showMessageDialog(null, "Update Completed");
							model.setRowCount(0);
							ackno.setText("");
							afnam.setText("");
							sfnam.setText("");
							amnam.setText("");
							smnam.setText("");
							alnam.setText("");
							slnam.setText("");
							afullnam.setText("");
							sfullnam.setText("");
							adob.setText("");
							sdob.setText("");
							inwardno.setText("");
							ackno.requestFocus();
							
						}
					} else if(error.getSelectedItem().equals("Pdf Not Found")){
						PreparedStatement pps=con.prepareStatement("update PanError set Flag='V',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
						pps.execute();
						PreparedStatement pss=con.prepareStatement("update pandetail set FileGenerationFlag='V' where InhouseRefno="+inwardno.getText());
						pss.execute();
						model.removeRow(inwardtab.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Update Completed");
						model.setRowCount(0);
						ackno.setText("");
						afnam.setText("");
						sfnam.setText("");
						amnam.setText("");
						smnam.setText("");
						alnam.setText("");
						slnam.setText("");
						afullnam.setText("");
						sfullnam.setText("");
						adob.setText("");
						sdob.setText("");
						inwardno.setText("");
						ackno.requestFocus();
					}else if (error.getSelectedItem().equals("Sam Error")) {
						PreparedStatement pps=con.prepareStatement("update PanError set Flag='S',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
						pps.execute();
						PreparedStatement pss=con.prepareStatement("update pandetail set FileGenerationFlag='S' where InhouseRefno="+inwardno.getText());
						pss.execute();
						model.removeRow(inwardtab.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Update Completed");
						model.setRowCount(0);
						ackno.setText("");
						afnam.setText("");
						sfnam.setText("");
						amnam.setText("");
						smnam.setText("");
						alnam.setText("");
						slnam.setText("");
						afullnam.setText("");
						sfullnam.setText("");
						adob.setText("");
						sdob.setText("");
						inwardno.setText("");
						ackno.requestFocus();
					}else if (error.getSelectedItem().equals("Multiple Update")) {
						if (!afnam.getText().equals(sfnam.getText()) || !sfnam.getText().equals(afnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct First Name");
						} else if(!amnam.getText().equals(smnam.getText()) || !smnam.getText().equals(amnam.getText())){
							JOptionPane.showMessageDialog(null, "Please check and Correct Middle Name");
						}else if (!alnam.getText().equals(slnam.getText()) || !slnam.getText().equals(alnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct Last Name");
						}else if (!afullnam.getText().equals(sfullnam.getText()) || !sfullnam.getText().equals(afullnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct Full Name");
						}else if (!adob.getText().equals(sdob.getText()) || !sdob.getText().equals(adob.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct DOB");
						}else {
							
							String sadate=sdob.getText().substring(4)+"-"+sdob.getText().substring(2,4)+"-"+sdob.getText().substring(0,2);
							
							PreparedStatement pps=con.prepareStatement("update PanError set Flag='U',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
							
							pps.execute();
							
							PreparedStatement pss=con.prepareStatement("update pandetail set AppFirstName='"+sfnam.getText()+"', AppMiddleName='"+smnam.getText()+"', AppLastName='"+slnam.getText()+"', AppNameOnCard='"+sfullnam.getText()+"', AppDOB='"+sadate+"', FileGenerationFlag='U' where InhouseRefno="+inwardno.getText());
							
							pss.execute();
							model.removeRow(inwardtab.getSelectedRow());
							JOptionPane.showMessageDialog(null, "Update Completed");
							model.setRowCount(0);
							ackno.setText("");
							afnam.setText("");
							sfnam.setText("");
							amnam.setText("");
							smnam.setText("");
							alnam.setText("");
							slnam.setText("");
							afullnam.setText("");
							sfullnam.setText("");
							adob.setText("");
							sdob.setText("");
							inwardno.setText("");
							ackno.requestFocus();
						}	
					}else if (error.getSelectedItem().equals("First Name Update")) {
						if (!afnam.getText().equals(sfnam.getText()) || !sfnam.getText().equals(afnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct First Name");
						} else if(!amnam.getText().equals(smnam.getText()) || !smnam.getText().equals(amnam.getText())){
							JOptionPane.showMessageDialog(null, "Please check and Correct Middle Name");
						}else if (!alnam.getText().equals(slnam.getText()) || !slnam.getText().equals(alnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct Last Name");
						}else if (!afullnam.getText().equals(sfullnam.getText()) || !sfullnam.getText().equals(afullnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct Full Name");
						}else if (!adob.getText().equals(sdob.getText()) || !sdob.getText().equals(adob.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct DOB");
						}else {
							
							String sadate=sdob.getText().substring(4)+"-"+sdob.getText().substring(2,4)+"-"+sdob.getText().substring(0,2);
							
							PreparedStatement pps=con.prepareStatement("update PanError set Flag='U',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
							
							pps.execute();
							
							PreparedStatement pss=con.prepareStatement("update pandetail set AppFirstName='"+sfnam.getText()+"', AppMiddleName='"+smnam.getText()+"', AppLastName='"+slnam.getText()+"', AppNameOnCard='"+sfullnam.getText()+"', AppDOB='"+sadate+"', FileGenerationFlag='U' where InhouseRefno="+inwardno.getText());
							
							pss.execute();
							
							model.removeRow(inwardtab.getSelectedRow());
							JOptionPane.showMessageDialog(null, "Update Completed");
							model.setRowCount(0);
							ackno.setText("");
							afnam.setText("");
							sfnam.setText("");
							amnam.setText("");
							smnam.setText("");
							alnam.setText("");
							slnam.setText("");
							afullnam.setText("");
							sfullnam.setText("");
							adob.setText("");
							sdob.setText("");
							inwardno.setText("");
							ackno.requestFocus();
						}	
					}else if (error.getSelectedItem().equals("Middle Name Update")) {
						if (!afnam.getText().equals(sfnam.getText()) || !sfnam.getText().equals(afnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct First Name");
						} else if(!amnam.getText().equals(smnam.getText()) || !smnam.getText().equals(amnam.getText())){
							JOptionPane.showMessageDialog(null, "Please check and Correct Middle Name");
						}else if (!alnam.getText().equals(slnam.getText()) || !slnam.getText().equals(alnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct Last Name");
						}else if (!afullnam.getText().equals(sfullnam.getText()) || !sfullnam.getText().equals(afullnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct Full Name");
						}else if (!adob.getText().equals(sdob.getText()) || !sdob.getText().equals(adob.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct DOB");
						}else {
							
							String sadate=sdob.getText().substring(4)+"-"+sdob.getText().substring(2,4)+"-"+sdob.getText().substring(0,2);
							
							PreparedStatement pps=con.prepareStatement("update PanError set Flag='U',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
							
							pps.execute();
							
							PreparedStatement pss=con.prepareStatement("update pandetail set AppFirstName='"+sfnam.getText()+"', AppMiddleName='"+smnam.getText()+"', AppLastName='"+slnam.getText()+"', AppNameOnCard='"+sfullnam.getText()+"', AppDOB='"+sadate+"', FileGenerationFlag='U' where InhouseRefno="+inwardno.getText());
							
							pss.execute();
							
							model.removeRow(inwardtab.getSelectedRow());
							JOptionPane.showMessageDialog(null, "Update Completed");
							model.setRowCount(0);
							ackno.setText("");
							afnam.setText("");
							sfnam.setText("");
							amnam.setText("");
							smnam.setText("");
							alnam.setText("");
							slnam.setText("");
							afullnam.setText("");
							sfullnam.setText("");
							adob.setText("");
							sdob.setText("");
							inwardno.setText("");
							ackno.requestFocus();
						}	
					}else if (error.getSelectedItem().equals("Last Name Update")) {
						if (!afnam.getText().equals(sfnam.getText()) || !sfnam.getText().equals(afnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct First Name");
						} else if(!amnam.getText().equals(smnam.getText()) || !smnam.getText().equals(amnam.getText())){
							JOptionPane.showMessageDialog(null, "Please check and Correct Middle Name");
						}else if (!alnam.getText().equals(slnam.getText()) || !slnam.getText().equals(alnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct Last Name");
						}else if (!afullnam.getText().equals(sfullnam.getText()) || !sfullnam.getText().equals(afullnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct Full Name");
						}else if (!adob.getText().equals(sdob.getText()) || !sdob.getText().equals(adob.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct DOB");
						}else {
							
							String sadate=sdob.getText().substring(4)+"-"+sdob.getText().substring(2,4)+"-"+sdob.getText().substring(0,2);
							PreparedStatement pps=con.prepareStatement("update PanError set Flag='U',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
							
							pps.execute();
							
							PreparedStatement pss=con.prepareStatement("update pandetail set AppFirstName='"+sfnam.getText()+"', AppMiddleName='"+smnam.getText()+"', AppLastName='"+slnam.getText()+"', AppNameOnCard='"+sfullnam.getText()+"', AppDOB='"+sadate+"', FileGenerationFlag='U' where InhouseRefno="+inwardno.getText());
							
							pss.execute();
							
							model.removeRow(inwardtab.getSelectedRow());
							JOptionPane.showMessageDialog(null, "Update Completed");
							model.setRowCount(0);
							ackno.setText("");
							afnam.setText("");
							sfnam.setText("");
							amnam.setText("");
							smnam.setText("");
							alnam.setText("");
							slnam.setText("");
							afullnam.setText("");
							sfullnam.setText("");
							adob.setText("");
							sdob.setText("");
							inwardno.setText("");
							ackno.requestFocus();
						}
						
					}else if (error.getSelectedItem().equals("DOB Update")) {
						
						if (!afnam.getText().equals(sfnam.getText()) || !sfnam.getText().equals(afnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct First Name");
						} else if(!amnam.getText().equals(smnam.getText()) || !smnam.getText().equals(amnam.getText())){
							JOptionPane.showMessageDialog(null, "Please check and Correct Middle Name");
						}else if (!alnam.getText().equals(slnam.getText()) || !slnam.getText().equals(alnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct Last Name");
						}else if (!afullnam.getText().equals(sfullnam.getText()) || !sfullnam.getText().equals(afullnam.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct Full Name");
						}else if (!adob.getText().equals(sdob.getText()) || !sdob.getText().equals(adob.getText())) {
							JOptionPane.showMessageDialog(null, "Please check and Correct DOB");
						}else {
							
							String sadate=sdob.getText().substring(4)+"-"+sdob.getText().substring(2,4)+"-"+sdob.getText().substring(0,2);
							PreparedStatement pps=con.prepareStatement("update PanError set Flag='U',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
							
							pps.execute();
							
							PreparedStatement pss=con.prepareStatement("update pandetail set AppFirstName='"+sfnam.getText()+"', AppMiddleName='"+smnam.getText()+"', AppLastName='"+slnam.getText()+"', AppNameOnCard='"+sfullnam.getText()+"', AppDOB='"+sadate+"', FileGenerationFlag='U' where InhouseRefno="+inwardno.getText());
							
							pss.execute();
							
							model.removeRow(inwardtab.getSelectedRow());
							JOptionPane.showMessageDialog(null, "Update Completed");
							model.setRowCount(0);
							ackno.setText("");
							afnam.setText("");
							sfnam.setText("");
							amnam.setText("");
							smnam.setText("");
							alnam.setText("");
							slnam.setText("");
							afullnam.setText("");
							sfullnam.setText("");
							adob.setText("");
							sdob.setText("");
							inwardno.setText("");
							ackno.requestFocus();
						}
					}else if (error.getSelectedItem().equals("Wrong PDF")) {
						
						PreparedStatement pps=con.prepareStatement("update PanError set Flag='V',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
						pps.execute();
						PreparedStatement pss=con.prepareStatement("update pandetail set FileGenerationFlag='V' where InhouseRefno="+inwardno.getText());
						pss.execute();
						model.removeRow(inwardtab.getSelectedRow());
						model.setRowCount(0);
						ackno.setText("");
						afnam.setText("");
						sfnam.setText("");
						amnam.setText("");
						smnam.setText("");
						alnam.setText("");
						slnam.setText("");
						afullnam.setText("");
						sfullnam.setText("");
						adob.setText("");
						sdob.setText("");
						inwardno.setText("");
						ackno.requestFocus();
						
					}else if (error.getSelectedItem().equals("ACK Mismatch")) {
						
						PreparedStatement pps=con.prepareStatement("update PanError set Flag='V',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
						pps.execute();
						PreparedStatement pss=con.prepareStatement("update pandetail set FileGenerationFlag='V' where InhouseRefno="+inwardno.getText());
						pss.execute();
						model.removeRow(inwardtab.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Update Completed");
						model.setRowCount(0);
						ackno.setText("");
						afnam.setText("");
						sfnam.setText("");
						amnam.setText("");
						smnam.setText("");
						alnam.setText("");
						slnam.setText("");
						afullnam.setText("");
						sfullnam.setText("");
						adob.setText("");
						sdob.setText("");
						inwardno.setText("");
						ackno.requestFocus();
					}else if (error.getSelectedItem().equals("Reject")) {
						
						PreparedStatement pps=con.prepareStatement("update PanError set Flag='V',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
						pps.execute();
						PreparedStatement pss=con.prepareStatement("update pandetail set FileGenerationFlag='V' where InhouseRefno="+inwardno.getText());
						pss.execute();
						model.removeRow(inwardtab.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Update Completed");
						model.setRowCount(0);
						ackno.setText("");
						afnam.setText("");
						sfnam.setText("");
						amnam.setText("");
						smnam.setText("");
						alnam.setText("");
						slnam.setText("");
						afullnam.setText("");
						sfullnam.setText("");
						adob.setText("");
						sdob.setText("");
						inwardno.setText("");
						ackno.requestFocus();
					}else if (error.getSelectedItem().equals("Sam Error and First Name Update")) {
						
						String sadate=adob.getText().substring(4)+"-"+sdob.getText().substring(2,4)+"-"+sdob.getText().substring(0,2);
						PreparedStatement pps=con.prepareStatement("update PanError set Flag='S',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
						pps.execute();
						PreparedStatement pss=con.prepareStatement("update pandetail set AppFirstName='"+afnam.getText()+"', AppMiddleName='"+amnam.getText()+"', AppLastName='"+alnam.getText()+"', AppNameOnCard='"+afullnam.getText()+"', AppDOB='"+sadate+"', FileGenerationFlag='S' where InhouseRefno="+inwardno.getText());
						pss.execute();
						model.removeRow(inwardtab.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Update Completed");
						ackno.setText("");
						afnam.setText("");
						sfnam.setText("");
						amnam.setText("");
						smnam.setText("");
						alnam.setText("");
						slnam.setText("");
						afullnam.setText("");
						sfullnam.setText("");
						adob.setText("");
						sdob.setText("");
						inwardno.setText("");
						ackno.requestFocus();
					}else if (error.getSelectedItem().equals("Sam Error and Middle Name Update")) {
						
						String sadate=adob.getText().substring(4)+"-"+sdob.getText().substring(2,4)+"-"+sdob.getText().substring(0,2);
						PreparedStatement pps=con.prepareStatement("update PanError set Flag='S',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
						pps.execute();
						PreparedStatement pss=con.prepareStatement("update pandetail set AppFirstName='"+afnam.getText()+"', AppMiddleName='"+amnam.getText()+"', AppLastName='"+alnam.getText()+"', AppNameOnCard='"+afullnam.getText()+"', AppDOB='"+sadate+"', FileGenerationFlag='S' where InhouseRefno="+inwardno.getText());
						pss.execute();
						model.removeRow(inwardtab.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Update Completed");
						ackno.setText("");
						afnam.setText("");
						sfnam.setText("");
						amnam.setText("");
						smnam.setText("");
						alnam.setText("");
						slnam.setText("");
						afullnam.setText("");
						sfullnam.setText("");
						adob.setText("");
						sdob.setText("");
						inwardno.setText("");
						ackno.requestFocus();
						
					}else if (error.getSelectedItem().equals("Sam Error and Last Name Update")) {
						
						String sadate=adob.getText().substring(4)+"-"+sdob.getText().substring(2,4)+"-"+sdob.getText().substring(0,2);
						PreparedStatement pps=con.prepareStatement("update PanError set Flag='S',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
						pps.execute();
						PreparedStatement pss=con.prepareStatement("update pandetail set AppFirstName='"+afnam.getText()+"', AppMiddleName='"+amnam.getText()+"', AppLastName='"+alnam.getText()+"', AppNameOnCard='"+afullnam.getText()+"', AppDOB='"+sadate+"', FileGenerationFlag='S' where InhouseRefno="+inwardno.getText());
						pss.execute();
						model.removeRow(inwardtab.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Update Completed");
						ackno.setText("");
						afnam.setText("");
						sfnam.setText("");
						amnam.setText("");
						smnam.setText("");
						alnam.setText("");
						slnam.setText("");
						afullnam.setText("");
						sfullnam.setText("");
						adob.setText("");
						sdob.setText("");
						inwardno.setText("");
						ackno.requestFocus();
						
					}else if (error.getSelectedItem().equals("Sam Error and Full Name Update")) {
						
						String sadate=adob.getText().substring(4)+"-"+sdob.getText().substring(2,4)+"-"+sdob.getText().substring(0,2);
						PreparedStatement pps=con.prepareStatement("update PanError set Flag='S',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
						pps.execute();
						PreparedStatement pss=con.prepareStatement("update pandetail set AppFirstName='"+afnam.getText()+"', AppMiddleName='"+amnam.getText()+"', AppLastName='"+alnam.getText()+"', AppNameOnCard='"+afullnam.getText()+"', AppDOB='"+sadate+"', FileGenerationFlag='S' where InhouseRefno="+inwardno.getText());
						pss.execute();
						model.removeRow(inwardtab.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Update Completed");
						model.setRowCount(0);
						ackno.setText("");
						afnam.setText("");
						sfnam.setText("");
						amnam.setText("");
						smnam.setText("");
						alnam.setText("");
						slnam.setText("");
						afullnam.setText("");
						sfullnam.setText("");
						adob.setText("");
						sdob.setText("");
						inwardno.setText("");
						ackno.requestFocus();
					}else if (error.getSelectedItem().equals("Sam Error and DOB Update")) {
						
						String sadate=adob.getText().substring(4)+"-"+sdob.getText().substring(2,4)+"-"+sdob.getText().substring(0,2);
						PreparedStatement pps=con.prepareStatement("update PanError set Flag='S',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
						pps.execute();
						PreparedStatement pss=con.prepareStatement("update pandetail set AppFirstName='"+afnam.getText()+"', AppMiddleName='"+amnam.getText()+"', AppLastName='"+alnam.getText()+"', AppNameOnCard='"+afullnam.getText()+"', AppDOB='"+sadate+"', FileGenerationFlag='S' where InhouseRefno="+inwardno.getText());
						pss.execute();
						model.removeRow(inwardtab.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Update Completed");
						model.setRowCount(0);
						ackno.setText("");
						afnam.setText("");
						sfnam.setText("");
						amnam.setText("");
						smnam.setText("");
						alnam.setText("");
						slnam.setText("");
						afullnam.setText("");
						sfullnam.setText("");
						adob.setText("");
						sdob.setText("");
						inwardno.setText("");
						ackno.requestFocus();
					}else if (error.getSelectedItem().equals("Sam Error and Multiple Update")) {
						String sadate=adob.getText().substring(4)+"-"+sdob.getText().substring(2,4)+"-"+sdob.getText().substring(0,2);
						PreparedStatement pps=con.prepareStatement("update PanError set Flag='S',UpdateDate=GETDATE(),REMARKS='"+error.getSelectedItem()+"', Userid='"+lognam+"' where InwardNo="+inwardno.getText());
						pps.execute();
						PreparedStatement pss=con.prepareStatement("update pandetail set AppFirstName='"+afnam.getText()+"', AppMiddleName='"+amnam.getText()+"', AppLastName='"+alnam.getText()+"', AppNameOnCard='"+afullnam.getText()+"', AppDOB='"+sadate+"', FileGenerationFlag='S' where InhouseRefno="+inwardno.getText());
						pss.execute();
						model.removeRow(inwardtab.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Update Completed");
						model.setRowCount(0);
						ackno.setText("");
						afnam.setText("");
						sfnam.setText("");
						amnam.setText("");
						smnam.setText("");
						alnam.setText("");
						slnam.setText("");
						afullnam.setText("");
						sfullnam.setText("");
						adob.setText("");
						sdob.setText("");
						inwardno.setText("");
						ackno.requestFocus();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error: "+e);
				}

			}
			}
		});
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.setBounds((int)w-130, 533, 107, 28);
		frame.getContentPane().add(btnSave);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setMnemonic(KeyEvent.VK_X);
		btnExit.setBounds((int)w-130, 572, 94, 28);
		frame.getContentPane().add(btnExit);
		
		lblSaveAlts = new JLabel("Save: Alt+S              Next: Alt+N");
		lblSaveAlts.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSaveAlts.setBounds((int)w-430, 611, 288, 14);
		frame.getContentPane().add(lblSaveAlts);
		
		lblSaveAlts1 = new JLabel("Previous: Alt+P              Exit: Alt+X");
		lblSaveAlts1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSaveAlts1.setBounds((int)w-430, 631, 288, 14);
		frame.getContentPane().add(lblSaveAlts1);
		
		JButton button = new JButton("Lode Application");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model2=(DefaultTableModel) fnamtab.getModel();
				model2.setRowCount(0);
				filepath="\\\\192.168.79.183\\nlrm-pan-f\\PAN_DATA_FOR_SCAN_IMAGES_01092017\\";
				File folder=new File(filepath);
	            File[] listofFiles=folder.listFiles();
	            Object[] frow = new Object[1];
	            for (int i = 0; i < listofFiles.length; i++) {
	            	 if (listofFiles[i].getName().endsWith(".pdf")) {
	            			 frow[0]=listofFiles[i].getName();
		                        model2.addRow(frow);
	            	 }else if (listofFiles[i].getName().endsWith(".PDF")) {
	            		 frow[0]=listofFiles[i].getName();
	                        model2.addRow(frow);
					}
	            }
				
			}
		});
		
		button.setBounds((int)w-180, 611, 130, 28);
		frame.getContentPane().add(button);
		
//		JLabel lblPath = new JLabel("Path:");
//		lblPath.setBounds(936, 656, 36, 14);
//		frame.getContentPane().add(lblPath);
//		
//		JLabel ppath = new JLabel("");
//		ppath.setBounds(982, 656, 348, 21);
//		frame.getContentPane().add(ppath);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnFlagDetails = new JMenu("Flag Details");
		mnFlagDetails.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFrame frame=new JFrame("Pan Flag Details");
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowOpened(WindowEvent arg0) {
						DefaultTableModel mod=(DefaultTableModel)table.getModel();
						
						try {
							Object[] row=new Object[4];
							PreparedStatement ps=con.prepareStatement("select * from panflagmaster order by slno;");
							ResultSet rs=ps.executeQuery();
							while (rs.next()) {
								row[0]=rs.getString(1);
								row[1]=rs.getString(2);
								row[2]=rs.getString(3);
								mod.addRow(row);
							}
							
						} catch (Exception e) {
							System.out.println("Error: "+e);
						}
					}
				});
				frame.setSize(377, 304);
				frame.setResizable(false);
				frame.getContentPane().setLayout(null);
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 11, 351, 253);
				frame.getContentPane().add(scrollPane);
				
				table = new JTable();
				table.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Sl No", "Flag", "Remarks"
					}
				));
				scrollPane.setViewportView(table);
				 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			        double width = screenSize.getWidth();
			        double height = screenSize.getHeight();
			        Point p=new Point((int)width/3, (int)height/3);
			        frame.setLocation(p);
			        
				frame.setVisible(true);
				
				
			}
		});
		menuBar.add(mnFlagDetails);
		frame.setVisible(true);
	}
}
