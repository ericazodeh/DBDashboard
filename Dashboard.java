import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.UIManager;


import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.knowm.xchart.*;
import org.knowm.xchart.PieSeries.PieSeriesRenderStyle;
//import org.knowm.xchart.demo.charts.*;
import org.knowm.xchart.style.PieStyler.AnnotationType;
import org.knowm.xchart.style.Styler.LegendPosition;

import java.sql.*;
import java.util.*;
import java.util.List;



public class Dashboard extends JDialog {
	public boolean switchPage = false;
	private ImageIcon image1;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */

	// JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost:3306";

   //  Database credentials
   static final String USER = "root";
	 static final String PASS = "password";
	 
	public static void main(String[] args) {

		Connection conn = null;
    Statement stmt = null;

		try {

			String sql;
      ResultSet rs;
          
      Class.forName("com.mysql.jdbc.Driver");
          

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
      conn.setReadOnly(true);

      stmt = conn.createStatement();
      sql = "USE adventureworks;";
      rs = stmt.executeQuery(sql);

			Dashboard dialog = new Dashboard(conn);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
	
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public boolean getSwitch()
	{
		return switchPage;
	}

	/**
	 * Create the dialog.
	 */
	public Dashboard(Connection conn) {
		getContentPane().setBackground(new Color(173, 255, 47));
		setBackground(new Color(144, 238, 144));
		setTitle("Dashboard");
		setBounds(150*2, 150*2, 500*2, 350*2);
		//setUndecorated(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JButton btnNewButton = new JButton("Query Functions");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnNewButton)
				switchPage = !switchPage;
				System.out.println(switchPage);
				
			}
		});
		btnNewButton.setBounds(826, 638, 158, 23);
		contentPanel.add(btnNewButton);
		
		//add amd scale images to dashboard
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(179, 147, 289, 232);
		ImageIcon icon = new ImageIcon("eric.png");
		Image img = icon.getImage();
		Image imgScale = img.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(imgScale);
		
		lblNewLabel.setIcon(scaledIcon);
		
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setBounds(468, 147, 276, 232);
		ImageIcon icon1 = new ImageIcon("brian.png");
		Image img1 = icon1.getImage();
		Image imgScale1 = img1.getScaledInstance(lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledIcon1 = new ImageIcon(imgScale1);
		lblNewLabel_1.setIcon(scaledIcon1);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(468, 377, 276, 262);
		ImageIcon icon2 = new ImageIcon("elijah.png");
		Image img2 = icon2.getImage();
		Image imgScale2 = img2.getScaledInstance(lblNewLabel_2.getWidth(), lblNewLabel_2.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledIcon2 = new ImageIcon(imgScale2);
		lblNewLabel_2.setIcon(scaledIcon2);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(179, 377, 289, 262);
		ImageIcon icon3 = new ImageIcon("paul.png");
		Image img3 = icon3.getImage();
		Image imgScale3 = img3.getScaledInstance(lblNewLabel_3.getWidth(), lblNewLabel_3.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledIcon3 = new ImageIcon(imgScale3);
		lblNewLabel_3.setIcon(scaledIcon3);
		contentPanel.add(lblNewLabel_3);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button 1");

				
				//ExampleChart<PieChart> exampleChart = new PieChart04();
				//PieChart chart = exampleChart.getChart();
				// Create Chart
				PieChart chart = new PieChartBuilder().width(800).height(600).title("# of customers per region").build();
 
				//JPanel test = new XChartPanel<PieChart>(chart); 
		/*
				// Customize Chart
				chart.getStyler().setLegendVisible(true);
				//chart.getStyler().setAnnotationType(AnnotationType.Label);
				chart.getStyler().setAnnotationDistance(.82);
				chart.getStyler().setPlotContentSize(.9);
				chart.getStyler().setDefaultSeriesRenderStyle(PieSeriesRenderStyle.Donut);
				chart.getStyler().isDrawAllAnnotations();
				// chart.getStyler().setCircular(false);
		 */
				CategoryChart chart1 = new CategoryChartBuilder().width(800).height(600).title("Regional Histogram of Sales (Globally)").xAxisTitle("Total Sales (USD)").yAxisTitle("# of Occurences").build();
				try{
						Connection conn = null;
						Statement stmt = null;
						String sql;
						ResultSet rs;
						Class.forName("com.mysql.jdbc.Driver");
		                 
						//STEP 3: Open a connection
						System.out.println("Connecting to database...");
						conn = DriverManager.getConnection(DB_URL,USER,PASS);
						conn.setReadOnly(true);
		
						//allow for reference only to adventureworks database
						stmt = conn.createStatement();
						sql = "USE adventureworks;";
						rs = stmt.executeQuery(sql);
		
						//by region
						sql = "SELECT cr.Name, count(c.CustomerID) FROM customer c LEFT JOIN customeraddress ca ON c.CustomerID = ca.CustomerID LEFT JOIN address a ON ca.AddressID = a.AddressID LEFT JOIN stateprovince sp ON a.StateProvinceID = sp.StateProvinceID LEFT JOIN countryregion cr ON sp.CountryRegionCode = cr.CountryRegionCode GROUP BY cr.Name;";
						rs = stmt.executeQuery(sql);
					/*	while(rs.next()){
							//Retrieve by region and corresponding population
							String region  = rs.getString("cr.Name");
							String population = rs.getString("count(c.CustomerID)");
													
							//add values to chart
							chart.addSeries(region, Integer.parseInt(population));
						}
		
						//STEP 6: Clean-up environment
						rs.close();
						stmt.close();
						conn.close();*/
						
						System.out.println("Yo");
			       		   String[] name = new String[10];
			               double[] salesLastYear_arr = new double[100000];
			               double[] salesYTD_arr = new double[100000];
			               
			 
			               sql = "SELECT SalesYTD, Name, SalesLastYear ";
			               sql += "FROM salesterritory;";
			              
			              rs = stmt.executeQuery(sql);
			              rs.next();
			              //.String date = rs.getString("OrderDate");
			             // int productid = rs.getInt("ProductID");
			              //int orderqty = rs.getInt("OrderQty");
			              int salesLastYear = rs.getInt("SalesLastYear");
			              int salesYTD = rs.getInt("SalesYTD");
			              String newName = rs.getString("Name");
			             // month[0] = date.substring(5,6);
			              salesLastYear_arr[0] = salesLastYear;
			              salesYTD_arr[0] = salesYTD;
			              name[0] = newName;
			              int count = 1;
			 
			              while (rs.next()) {
			               
			               int salesLastYear2 = rs.getInt("SalesLastYear");
			               int salesYTD2 = rs.getInt("SalesYTD");
			               String newName2 = rs.getString("Name");
			               salesLastYear_arr[count] = salesLastYear2;
			               salesYTD_arr[count] = salesYTD2;
			               name[count] = newName2;
			               count += 1;
			 
			              }
			              
			              double[] xData = new double[count];
			              double[] yData = new double[count];
			              List<Double> list1 = new ArrayList<Double>(count);
			              List<Double> list2 = new ArrayList<Double>(count);
			              for (int i = 0; i < count; i++) {
			            	  list1.add(salesYTD_arr[i]);
			            	  list2.add(salesLastYear_arr[i]);
			              }
			              
			              System.out.println(count);
			              
			              
			           // Create Chart
			      	    //XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", name, yData);
			      	    
			      	    // Create Chart
			      	    
			      	    
			      	    // Customize Chart
			      	    chart1.getStyler().setLegendPosition(LegendPosition.InsideNW);
			      	    chart1.getStyler().setAvailableSpaceFill(.96);
			      	    chart1.getStyler().setOverlapped(true);
			      	  
			      	    // Series
			      	    Histogram histogram1 = new Histogram(list1, 10, 1000000, 10000000);
			      	    Histogram histogram2 = new Histogram(list2, 10, 1000000, 10000000);

			      	    chart1.addSeries("Year-to-Date Sales", histogram1.getxAxisData(), histogram1.getyAxisData());
			      	    chart1.addSeries("histogram 2", histogram2.getxAxisData(), histogram2.getyAxisData());
			      	    
			      	    // Show it
			      	    System.out.println("Hey");
			      	    //new SwingWrapper(chart1).displayChart();
			      	    
			      	  // Create Chart
			      	
			      	 
			      	 // new SwingWrapper(chart1).displayChart();
						
				
		
					}catch(SQLException se){
						//Handle errors for JDBC
						System.out.println("Error: " + se.getMessage());
						se.printStackTrace();
				 }catch(Exception e2){
						//Handle errors for Class.forName
						e2.printStackTrace();
				 }

				//new SwingWrapper<PieChart>(chart).displayChart();
				JPanel chart3 = new XChartPanel<CategoryChart>(chart1);
				//JOptionPane display = new JOptionPane();
				//display.add(chart5);  
				//JOptionPane.showMessageDialog(display, "Hello", "Hello", JOptionPane.INFORMATION_MESSAGE);
				JFrame display1 = new JFrame();
				display1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				display1.getContentPane().add(chart3, BorderLayout.CENTER);
				 //4. Size the frame.
				display1.pack();
				display1.setVisible(true);
			}
		});
			
		
		btnNewButton_1.setForeground(UIManager.getColor("Button.background"));
		btnNewButton_1.setBackground(UIManager.getColor("Button.background"));
		btnNewButton_1.setBounds(179, 147, 289, 232);
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		contentPanel.add(btnNewButton_1);
		validate();
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button 2");
				
				//ExampleChart<PieChart> exampleChart = new PieChart04();
				//PieChart chart = exampleChart.getChart();
				// Create Chart
				PieChart chart = new PieChartBuilder().width(800).height(600).title("# of customers per region").build();
 
				//JPanel test = new XChartPanel<PieChart>(chart); 
		
				// Customize Chart
				chart.getStyler().setLegendVisible(true);
				//chart.getStyler().setAnnotationType(AnnotationType.Label);
				chart.getStyler().setAnnotationDistance(.82);
				chart.getStyler().setPlotContentSize(.9);
				chart.getStyler().setDefaultSeriesRenderStyle(PieSeriesRenderStyle.Donut);
				chart.getStyler().isDrawAllAnnotations();
				// chart.getStyler().setCircular(false);
		 
					
				try{
						Connection conn = null;
						Statement stmt = null;
						String sql;
						ResultSet rs;
						Class.forName("com.mysql.jdbc.Driver");
		
						//STEP 3: Open a connection
						System.out.println("Connecting to database...");
						conn = DriverManager.getConnection(DB_URL,USER,PASS);
						conn.setReadOnly(true);
		
						//allow for reference only to adventureworks database
						stmt = conn.createStatement();
						sql = "USE adventureworks;";
						rs = stmt.executeQuery(sql);
		
						//by region
						sql = "SELECT cr.Name, count(c.CustomerID) FROM customer c LEFT JOIN customeraddress ca ON c.CustomerID = ca.CustomerID LEFT JOIN address a ON ca.AddressID = a.AddressID LEFT JOIN stateprovince sp ON a.StateProvinceID = sp.StateProvinceID LEFT JOIN countryregion cr ON sp.CountryRegionCode = cr.CountryRegionCode GROUP BY cr.Name;";
						rs = stmt.executeQuery(sql);
						while(rs.next()){
							//Retrieve by region and corresponding population
							String region  = rs.getString("cr.Name");
							String population = rs.getString("count(c.CustomerID)");
													
							//add values to chart
							chart.addSeries(region, Integer.parseInt(population));
						}
		
						//STEP 6: Clean-up environment
						rs.close();
						stmt.close();
						conn.close();
		
					}catch(SQLException se){
						//Handle errors for JDBC
						System.out.println("Error: " + se.getMessage());
						se.printStackTrace();
				 }catch(Exception e2){
						//Handle errors for Class.forName
						e2.printStackTrace();
				 }

				//new SwingWrapper<PieChart>(chart).displayChart();
				JPanel chart5 = new XChartPanel<PieChart>(chart);
				//JOptionPane display = new JOptionPane();
				//display.add(chart5);  
				//JOptionPane.showMessageDialog(display, "Hello", "Hello", JOptionPane.INFORMATION_MESSAGE);
				JFrame display = new JFrame();
				display.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				display.getContentPane().add(chart5, BorderLayout.CENTER);
				 //4. Size the frame.
				display.pack();
				display.setVisible(true);
			}
		});
		btnNewButton_2.setForeground(UIManager.getColor("Button.background"));
		btnNewButton_2.setBackground(UIManager.getColor("Button.background"));
		btnNewButton_2.setBounds(468, 147, 276, 232);
		btnNewButton_2.setOpaque(false);
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.setBorderPainted(false);
		contentPanel.add(btnNewButton_2);
		validate();

		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button 3");
				CategoryChart chart_spr = new CategoryChartBuilder().width(800).height(600).title("Most Popular Products for Spring Season").xAxisTitle("ProductID").yAxisTitle("Amount of Sales").build();
				CategoryChart chart_sum = new CategoryChartBuilder().width(800).height(600).title("Most Popular Products for Summer Season").xAxisTitle("ProductID").yAxisTitle("Amount of Sales").build();
				CategoryChart chart_f = new CategoryChartBuilder().width(800).height(600).title("Most Popular Products for Fall Season").xAxisTitle("ProductID").yAxisTitle("Amount of Sales").build();
				CategoryChart chart_w = new CategoryChartBuilder().width(800).height(600).title("Most Popular Products for Winter Season").xAxisTitle("ProductID").yAxisTitle("Amount of Sales").build();
				
				
				chart_spr.getStyler().setLegendPosition(LegendPosition.InsideNW);
				chart_spr.getStyler().setHasAnnotations(true);
				chart_sum.getStyler().setLegendPosition(LegendPosition.InsideNW);
				chart_sum.getStyler().setHasAnnotations(true);
				chart_f.getStyler().setLegendPosition(LegendPosition.InsideNW);
				chart_f.getStyler().setHasAnnotations(true);
				chart_w.getStyler().setLegendPosition(LegendPosition.InsideNW);
				chart_w.getStyler().setHasAnnotations(true);



				String[] spring = new String[5000];
				String[] summer = new String[5000];
				String[] fall = new String[5000];
				String[] winter = new String[5000];
				Integer[] w_pid = new Integer[5000];
				Integer[] w_qty = new Integer[5000];
				Integer[] spr_pid = new Integer[5000];
				Integer[] spr_qty = new Integer[5000];
				Integer[] f_pid = new Integer[5000];
				Integer[] f_qty = new Integer[5000];
				Integer[] sum_pid = new Integer[5000];
				Integer[] sum_qty = new Integer[5000];
				Integer[] w_pid_final = new Integer[10];
				Integer[] w_qty_final = new Integer[10];
				Integer[] spr_pid_final = new Integer[10];
				Integer[] spr_qty_final = new Integer[10];
				Integer[] f_pid_final = new Integer[10];
				Integer[] f_qty_final = new Integer[10];
				Integer[] sum_pid_final = new Integer[10];
				Integer[] sum_qty_final = new Integer[10];
				int w_count = 0;
				int spr_count = 0;
				int f_count = 0;
				int sum_count = 0;
				int temp = -1;
				try{
					Connection conn = null;
					Statement stmt = null;
					String sql;
					ResultSet rs;
					Class.forName("com.mysql.jdbc.Driver");
	
					//STEP 3: Open a connection
					System.out.println("Connecting to database...");
					conn = DriverManager.getConnection(DB_URL,USER,PASS);
					conn.setReadOnly(true);
	
					//allow for reference only to adventureworks database
					stmt = conn.createStatement();
					sql = "USE adventureworks;";
					rs = stmt.executeQuery(sql);
	
					sql = "select po.OrderQty, po.ProductID, poh.OrderDate ";
					sql += "from purchaseorderdetail po, purchaseorderheader poh ";
					sql += "where po.PurchaseOrderID = poh.PurchaseOrderID ";
	
					rs = stmt.executeQuery(sql);
					rs.next();
					String date = rs.getString("OrderDate");
					int productid = rs.getInt("ProductID");
					int orderqty = rs.getInt("OrderQty");
					switch (date.substring(5,7)) {
						case "12":
						case "01":
						case "02":
							winter[w_count] = date.substring(5,7);
							w_pid[w_count] = productid;
							w_qty[w_count] = orderqty;
							w_count += 1;
								break;
						case "03":
						case "04":
						case "05":
							spring[spr_count] = date.substring(5,7);
							spr_pid[spr_count] = productid;
							spr_qty[spr_count] = orderqty;
							spr_count += 1;
							break;
						case "06":
						case "07":
						case "08":
							summer[sum_count] = date.substring(5,7);
							sum_pid[sum_count] = productid;
							sum_qty[sum_count] = orderqty;
							sum_count += 1;
							break;
						case "09":
						case "10":
						case "11":
							fall[f_count] = date.substring(5,7);
							f_pid[f_count] = productid;
							f_qty[f_count] = orderqty;
							f_count += 1;
							break;
					}
					while (rs.next()) {
						String date2 = rs.getString("OrderDate");
						int productid2 = rs.getInt("ProductID");
						int orderqty2 = rs.getInt("OrderQty");

						switch (date2.substring(5,7)) {
							case "12":
							case "01":
							case "02":
								winter[w_count] = date2.substring(5,7);
								w_pid[w_count] = productid2;
								w_qty[w_count] = orderqty2;
								w_count += 1;
									break;
							case "03":
							case "04":
							case "05":
								spring[spr_count] = date2.substring(5,7);
								spr_pid[spr_count] = productid2;
								spr_qty[spr_count] = orderqty2;
								spr_count += 1;
								break;
							case "06":
							case "07":
							case "08":
								summer[sum_count] = date2.substring(5,7);
								sum_pid[sum_count] = productid2;
								sum_qty[sum_count] = orderqty2;
								sum_count += 1;
								break;
							case "09":
							case "10":
							case "11":
								fall[f_count] = date2.substring(5,7);
								f_pid[f_count] = productid2;
								f_qty[f_count] = orderqty2;
								f_count += 1;
								break;
						}
					}
					// adding every product id that is the same to each other, if it has already been added the product id will be negative
					for (int i = 0; i < spr_count; i++) {
						for (int j = 0; j < spr_count; j++) {
							if (spr_pid[i] - spr_pid[j] == 0) {
								if (i != j) {

									spr_qty[i] += spr_qty[j];
									spr_pid[j] = spr_pid[j] * temp;
									temp -= 1;
								}
							}
						}
					}
					temp = -1;
					for (int i = 0; i < sum_count; i++) {
						for (int j = 0; j < sum_count; j++) {
							if (sum_pid[i] - sum_pid[j] == 0) {
								if (i != j) {
									sum_qty[i] += sum_qty[j];
									sum_pid[j] = sum_pid[j] * temp;
									temp -= 1;
								}
							}
						}
					}
					for (int i = 0; i < f_count; i++) {
						for (int j = 0; j < f_count; j++) {
							if (f_pid[i] - f_pid[j] == 0) {
								if (i != j) {
									f_qty[i] += f_qty[j];
									f_pid[j] = f_pid[j] * temp;
									temp -= 1;
								}
							}
						}
					}
					temp = -1;
						for (int i = 0; i < w_count; i++) {
						for (int j = 0; j < w_count; j++) {
							if (w_pid[i] - w_pid[j] == 0) {
								if (i != j) {
									w_qty[i] += w_qty[j];
									w_pid[j] = w_pid[j] * temp;
									temp -= 1;
								}
							}
						}
					}
	
					// // finding the top ten products for each season
					int max = spr_qty[0];
					int pid = spr_pid[0];
					int index = spr_pid[0];
					for (int j = 0; j < 10; j++) {
						for (int i = 0; i < spr_count; i++) {
							if (spr_pid[i] > 0) {
								if (spr_qty[i] > max) {
									max = spr_qty[i];
									pid = spr_pid[i];
									index = i;
								}
							}
						}
						spr_qty_final[j] = max;
						spr_pid_final[j] = pid;
						spr_pid[index] = spr_pid[index] * -1;
						max = 0;
						pid = 0;
						index = 0;
					}
					for (int j = 0; j < 10; j++) {
						for (int i = 0; i < sum_count; i++) {
							if (sum_pid[i] > 0) {
								if (sum_qty[i] > max) {
									max = sum_qty[i];
									pid = sum_pid[i];
									index = i;
								}
							}
						}
						sum_qty_final[j] = max;
						sum_pid_final[j] = pid;
						sum_pid[index] = sum_pid[index] * -1;
						max = 0;
						pid = 0;
						index = 0;
					}
					for (int j = 0; j < 10; j++) {
						for (int i = 0; i < f_count; i++) {
							if (f_pid[i] > 0) {
								if (f_qty[i] > max) {
									max = f_qty[i];
									pid = f_pid[i];
									index = i;
								}
							}
						}
						f_qty_final[j] = max;
						f_pid_final[j] = pid;
						f_pid[index] = f_pid[index] * -1;
						max = 0;
						pid = 0;
						index = 0;
					}
					for (int j = 0; j < 10; j++) {
						for (int i = 0; i < w_count; i++) {
							if (w_pid[i] > 0) {
								if (w_qty[i] > max) {
									max = w_qty[i];
									pid = w_pid[i];
									index = i;
								}
							}
						}
						w_qty_final[j] = max;
						w_pid_final[j] = pid;
						w_pid[index] = w_pid[index] * -1;
						max = 0;
						pid = 0;
						index = 0;
					}
					
					chart_spr.addSeries("test 1", Arrays.asList(spr_pid_final), Arrays.asList(spr_qty_final));
					chart_sum.addSeries("test 1", Arrays.asList(sum_pid_final), Arrays.asList(sum_qty_final));
					chart_f.addSeries("test 1", Arrays.asList(f_pid_final), Arrays.asList(f_qty_final));
					chart_w.addSeries("test 1", Arrays.asList(w_pid_final), Arrays.asList(w_qty_final));
					// chart.addSeries("test 1", Arrays.asList(new Integer[]{0,1,2,3,4}), Arrays.asList(new Integer[] {4,5,9,6,5}));

					//STEP 6: Clean-up environment
					rs.close();
					stmt.close();
					conn.close();
	
				}catch(SQLException se){
					//Handle errors for JDBC
					System.out.println("Error: " + se.getMessage());
					se.printStackTrace();
				}catch(Exception e2){
					//Handle errors for Class.forName
					e2.printStackTrace();
				}

				//new SwingWrapper<PieChart>(chart).displayChart();
				JPanel chart4 = new XChartPanel<CategoryChart>(chart_spr);
				JFrame display3 = new JFrame();
				display3.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				display3.getContentPane().add(chart4, BorderLayout.CENTER);
				 //4. Size the frame.
				display3.pack();
				display3.setVisible(true);

				JPanel chart5 = new XChartPanel<CategoryChart>(chart_sum);
				JFrame display4 = new JFrame();
				display4.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				display4.getContentPane().add(chart5, BorderLayout.CENTER);
				 //4. Size the frame.
				display4.pack();
				display4.setVisible(true);

				JPanel chart6 = new XChartPanel<CategoryChart>(chart_f);
				JFrame display5 = new JFrame();
				display5.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				display5.getContentPane().add(chart6, BorderLayout.CENTER);
				 //4. Size the frame.
				display5.pack();
				display5.setVisible(true);

				JPanel chart7 = new XChartPanel<CategoryChart>(chart_w);
				JFrame display6 = new JFrame();
				display6.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				display6.getContentPane().add(chart7, BorderLayout.CENTER);
				 //4. Size the frame.
				display6.pack();
				display6.setVisible(true);
			}
		});
		btnNewButton_3.setForeground(UIManager.getColor("Button.background"));
		btnNewButton_3.setBackground(UIManager.getColor("Button.background"));
		btnNewButton_3.setBounds(179, 377, 289, 262);
		btnNewButton_3.setOpaque(false);
		btnNewButton_3.setContentAreaFilled(false);
		btnNewButton_3.setBorderPainted(false);
		contentPanel.add(btnNewButton_3);
		validate();
		
		JButton btnNewButton_4 = new JButton("New button");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button 4");
				CategoryChart chart1 = new CategoryChartBuilder().title("Revenue (M) by Year").xAxisTitle("Year").width(500).height(300).yAxisTitle("Revenue (M)").build();
				
				chart1.getStyler().setHasAnnotations(true);
				chart1.getStyler().setPlotGridLinesVisible(false);
				chart1.getStyler().setXAxisDecimalPattern("#");
				chart1.getStyler().setLegendVisible(false);
				ArrayList<Double> sum = new ArrayList<Double>();
				ArrayList<Integer> year = new ArrayList<Integer>();				
				try{
					Connection conn = null;
					Statement stmt = null;
					String sql;
					ResultSet rs;
					Class.forName("com.mysql.jdbc.Driver");
	
					//STEP 3: Open a connection
					System.out.println("Connecting to database...");
					conn = DriverManager.getConnection(DB_URL,USER,PASS);
					conn.setReadOnly(true);
	
					//allow for reference only to adventureworks database
					stmt = conn.createStatement();
					sql = "USE adventureworks;";
					rs = stmt.executeQuery(sql);
	
					sql = "select sum(LineTotal), year(ModifiedDate) from salesorderdetail group by year(ModifiedDate);";
					rs = stmt.executeQuery(sql);
					while(rs.next()){
						//Retrieve by region and corresponding population
						String sumstr  = rs.getString("sum(LineTotal)");
						String yearstr = rs.getString("year(ModifiedDate)");
						year.add(Integer.parseInt(yearstr));
						sum.add(Double.parseDouble(sumstr)/1000000);
					}
	
					//STEP 6: Clean-up environment
					rs.close();
					stmt.close();
					conn.close();
	
				}catch(SQLException se){
					//Handle errors for JDBC
					System.out.println("Error: " + se.getMessage());
					se.printStackTrace();
				}catch(Exception e2){
					//Handle errors for Class.forName
					e2.printStackTrace();
				}
				
				chart1.addSeries("Revenue by Year", year, sum);
				JPanel chart4 = new XChartPanel<CategoryChart>(chart1);
				JFrame display3 = new JFrame();
				display3.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				display3.getContentPane().add(chart4, BorderLayout.CENTER);
				display3.pack();
				display3.setVisible(true);
			}
		});
		btnNewButton_4.setForeground(UIManager.getColor("Button.background"));
		btnNewButton_4.setBackground(UIManager.getColor("Button.background"));
		btnNewButton_4.setBounds(468, 377, 276, 262);
		btnNewButton_4.setOpaque(false);
		btnNewButton_4.setContentAreaFilled(false);
		btnNewButton_4.setBorderPainted(false);
		contentPanel.add(btnNewButton_4);
		
		JTextPane txtpnAdventureworksDashboard = new JTextPane();
		txtpnAdventureworksDashboard.setBackground(Color.LIGHT_GRAY);
		txtpnAdventureworksDashboard.setText("AdventureWorks Dashboard");
		txtpnAdventureworksDashboard.setBounds(179, 11, 566, 132);
		contentPanel.add(txtpnAdventureworksDashboard);
		
		JTextPane txtpnRegionalStatistics = new JTextPane();
		txtpnRegionalStatistics.setText("Regional Statistics");
		txtpnRegionalStatistics.setBounds(10, 255, 144, 67);
		contentPanel.add(txtpnRegionalStatistics);
		
		JTextPane txtpnMostPopularProducts = new JTextPane();
		txtpnMostPopularProducts.setText("Most Popular Products (4 total graphs)");
		txtpnMostPopularProducts.setBounds(23, 448, 131, 67);
		contentPanel.add(txtpnMostPopularProducts);
		
		JTextPane txtpnNumberOfCustomers = new JTextPane();
		txtpnNumberOfCustomers.setText("Number of Customers per Region");
		txtpnNumberOfCustomers.setBounds(762, 241, 115, 50);
		contentPanel.add(txtpnNumberOfCustomers);
		
		JTextPane txtpnRevenuePerYear = new JTextPane();
		txtpnRevenuePerYear.setText("Sales Revenue per Year");
		txtpnRevenuePerYear.setBounds(771, 495, 87, -37);
		contentPanel.add(txtpnRevenuePerYear);
		
		JTextPane txtpnSalesOrderRevenue = new JTextPane();
		txtpnSalesOrderRevenue.setText("Sales Order Revenue per Year");
		txtpnSalesOrderRevenue.setBounds(762, 448, 125, 50);
		contentPanel.add(txtpnSalesOrderRevenue);
		validate();
	}
}