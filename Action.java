// From https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
// Choe: This is a simple app, but it includes event handling, so it could
// be a good starting point. Caveat: This example does not include swing.
// It'd be much easier to use swing when creating the UI.

import java.awt.*;
import java.awt.event.*;

//Using from swing library
import javax.swing.*;
/*import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField; */

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Collections;

import java.sql.*;
import java.util.*;

public class Action extends JFrame implements WindowListener,ActionListener,MouseListener {
  
    static Vector<String> path(Statement stmt, String firstTable, String secondTable,Connection conn, ResultSet rs) {
		  try {
			  
			  //introduce variable to find if at end of BFS
			  boolean isFinished = false;
			  //queue for BFS
			  Queue<String> queue = new LinkedList<String>();
			  //turns true if it's path links to initial table
			  boolean isPotentialPath = false; 
			  
			  //parallel vector that tracks the parent nodes in the vector
			  Vector<String> pNode = new Vector<String>();
			  
			  //vector with the paths from initial to final
			  Vector<String> result = new Vector<String>();
			  
			  //find all columns that contain the keyword ID in it, used to find linkage
			  rs = stmt.executeQuery("select distinct column_name from information_schema.columns where CAST(column_name  AS BINARY) rlike '^[^_]+ID$';");
			  
			  //grab initial table and final table
			  String intitialTable = firstTable;
			  String finalTable = secondTable;
			  //track visited tables in this vector
			  Vector<String> collected = new Vector<String>();
			  
			  // store the ID columns that are in the DB into a vector
			  Vector<String> linked_cols = new Vector<String>();  
			  while(rs.next() == true)
			  {
				linked_cols.add(rs.getString(1));
			  }

			  //start BFS with initial table name
			  queue.add(intitialTable);
			  collected.add(intitialTable);
			  //first node can't have a parent
			  pNode.add("x");
			  //while there are more nodes to search 
			  while( !isFinished && !queue.isEmpty() )
			  {
				//grab first schema from the front of the queue
				ResultSet col;
				String currentSchema = queue.remove();	

				
				//grab all columns 
				stmt = conn.createStatement();
				col = stmt.executeQuery("select column_name from information_schema.columns where table_name = \'" + currentSchema + "\';");
				while(col.next() && isFinished == false)
				{
					
				  
				  ResultSet linkedTable;
				  //store column name
				  String columnName = col.getString(1);
				  if(isPotentialPath)
					{
						break;
					}

				  // if it is a match, add other related schemas to collected list
				  if (linked_cols.contains(columnName))
				  {
					

					stmt = conn.createStatement();
					linked_cols.remove(columnName);
					if(isPotentialPath)
					{
						break;
					}

					// Get related tables
					linkedTable = stmt.executeQuery("select table_name from information_schema.columns where column_name = \'" + columnName + "\';");

					while (!isFinished  && linkedTable.next()== true)
					{
					  String comparedTable = linkedTable.getString(1);
					  
					  if(!collected.contains(comparedTable))
					  {
						 //add to queue and collected vector
						queue.add(comparedTable);
						collected.add(comparedTable);
						
						if(isPotentialPath)
						{
							break;
						}
						//add the table into the parent vector
						pNode.add(currentSchema);
						//if we reach the end of the table and a match is found
						if (comparedTable.equals(finalTable))
						{
						  
						  isFinished = true;
						  if(isPotentialPath)
							{
								break;
							}
						}  
					  }
					}
				  }
				}
			  }

			int pNodeLocation = pNode.size() - 1;
			while(!(pNodeLocation == 0)  && !isPotentialPath)
			{
				result.add(pNode.get(pNodeLocation));
			  pNodeLocation = collected.indexOf(pNode.get(pNodeLocation));
			}
			//reverse list to get correct initial -> final pathing and return it
			Collections.reverse(result);
			return result;

		  }catch(SQLException e){
			System.out.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		  }catch(Exception se){
			System.out.println("ERROR: " + se.getMessage());
			
			se.printStackTrace();
		  }
			
		  try{
			if(stmt!=null)
		     stmt.close();
			}catch(SQLException exe){
				System.out.println("ERROR: " + exe.getMessage());
			}// nothing we can do
			try{
			   if(!(conn==null))
				conn.close();
			}catch(SQLException se){
			System.out.println("ERROR: " + se.getMessage());
			   se.printStackTrace();
			  }
		    
			return null;
		  }
	
	
	static Vector<String> altered_path(Statement stmt, String firstTable, String secondTable,Connection conn, ResultSet rs) {
		  try {
			  
			  //introduce variable to find if at end of BFS
			  boolean isFinished = false;
			  //queue for BFS
			  Queue<String> queue = new LinkedList<String>();
			  //turns true if it's path links to initial table
			  boolean isPotentialPath = false; 
			  
			  //parallel vector that tracks the parent nodes in the vector
			  Vector<String> pNode = new Vector<String>();
			  
			  //vector with the paths from initial to final
			  Vector<String> result = new Vector<String>();
			  
			  //find all columns that contain the keyword ID in it, used to find linkage
			  rs = stmt.executeQuery("select distinct column_name from information_schema.columns where CAST(column_name  AS BINARY) rlike '^[^_]+ID$';");
			  
			  //grab initial table and final table
			  String intitialTable = firstTable;
			  String finalTable = secondTable;
			  //track visited tables in this vector
			  Vector<String> collected = new Vector<String>();
			  
			  // store the ID columns that are in the DB into a vector
			  Vector<String> linked_cols = new Vector<String>();  
			  while(rs.next() == true)
			  {
				linked_cols.add(rs.getString(1));
			  }

			  //start BFS with initial table name
			  queue.add(intitialTable);
			  collected.add(intitialTable);
			  //first node can't have a parent
			  pNode.add("x");
			  //while there are more nodes to search 
			  while( !isFinished && !queue.isEmpty() )
			  {
				//grab first schema from the front of the queue
				ResultSet col;
				String currentSchema = queue.remove();	

				
				//grab all columns 
				stmt = conn.createStatement();
				col = stmt.executeQuery("select column_name from information_schema.columns where table_name = \'" + currentSchema + "\';");
				while(col.next() && isFinished == false)
				{
					
				  
				  ResultSet linkedTable;
				  //store column name
				  String columnName = col.getString(1);
				  if(isPotentialPath)
					{
						break;
					}

				  // if it is a match, add other related schemas to collected list
				  if (linked_cols.contains(columnName))
				  {
					

					stmt = conn.createStatement();
					linked_cols.remove(columnName);
					if(isPotentialPath)
					{
						break;
					}

					// Get related tables
					linkedTable = stmt.executeQuery("select table_name from information_schema.columns where column_name = \'" + columnName + "\';");

					while (!isFinished  && linkedTable.next()== true)
					{
					  String comparedTable = linkedTable.getString(1);
					  
					  if(!collected.contains(comparedTable))
					  {
						 //add to queue and collected vector
						queue.add(comparedTable);
						collected.add(comparedTable);
						
						if(isPotentialPath)
						{
							break;
						}
						//add the table into the parent vector
						pNode.add(currentSchema);
						//if we reach the end of the table and a match is found
						if (comparedTable.equals(finalTable))
						{
						  
						  isFinished = true;
						  if(isPotentialPath)
							{
								break;
							}
						}  
					  }
					}
				  }
				}
			  }

			int pNodeLocation = pNode.size() - 1;
			while(!(pNodeLocation == 0)  && !isPotentialPath)
			{
				
			  String s = pNode.get(pNodeLocation) + " joins with " + collected.get(pNodeLocation);
			  result.add(s);
			  pNodeLocation = collected.indexOf(pNode.get(pNodeLocation));
			}
			//reverse list to get correct initial -> final pathing and return it
			Collections.reverse(result);
			return result;

		  }catch(SQLException e){
			System.out.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		  }catch(Exception se){
			System.out.println("ERROR: " + se.getMessage());
			
			se.printStackTrace();
		  }
			
		  try{
			if(stmt!=null)
		     stmt.close();
			}catch(SQLException exe){
				System.out.println("ERROR: " + exe.getMessage());
			}// nothing we can do
			try{
			   if(!(conn==null))
				conn.close();
			}catch(SQLException se){
			System.out.println("ERROR: " + se.getMessage());
			   se.printStackTrace();
			  }
		    
			return null;
		  }


   	// JDBC driver name and database URL
	public boolean switchPage = false;
	
   	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   	static final String DB_URL = "jdbc:mysql://localhost:3306";

   	//  Database credentials
   	static final String USER = "root";
   	static final String PASS = "password";

    //Swing objects
    JTextField text = new JTextField(40);
    JButton b;
    JTextArea output = new JTextArea(25,40);
    JScrollPane scroll;

    //dropdown menu
    String[] commands = {
        "(Please select a query command)",
      	"jdb-show-table-list",
        "jdb-show-table-column(s)",
        "jdb-show-join-table(s)",
        "jdb-any-query",
        "jdb-show-related-tables",
        "jdb-show-all-primary-keys",
        "jdb-find-column",
        "jdb-search-path",
        "jdb-search-and-join",
        "jdb-stat",
        "jdb-hr-employee-info",
        "jdb-city-num-customers",
        "jdb-products-color",
        "jdb-department",
        "jdb-hr-employee-hours"
    };
    JComboBox select = new JComboBox(commands);

    //counting variables
    private int numClicks = 0;
    JButton backButton = new JButton("Back");
    
    

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
			//GUI
         	Dashboard dash = new Dashboard(conn);
         	dash.setVisible(true);
         	
         	while(true)
         	{
         		while(dash.switchPage == false)
             	{
             		dash.setVisible(true);
             	}
             	//user clicks Query button,
             	
             	
             	
                Action myWindow = new Action("My first window", conn);
                
                myWindow.setSize(777,666);
                while(myWindow.switchPage == false)
                {
                	 myWindow.setVisible(true);
                     dash.setVisible(false);
                     
                }
                //user clicks Back button
                myWindow.switchPage = false;
                dash.switchPage = false;
         	}
         	
           
            
           

        }catch(SQLException se){
            //Handle errors for JDBC
            System.out.println("Error: " + se.getMessage());
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
             
        System.out.println("Goodbye!");
        
    }

        public Action(String title, Connection conn) {
        	
        	

            //inherit attributes according to string designation of Action object
            super(title);
            addWindowListener(this);
            
            //creating button object with description
            b = new JButton("Click Me");
            b.setBounds(358, 30, 71, 23);
            b.setHorizontalAlignment(SwingConstants.RIGHT);
            getContentPane().setLayout(null);
            select.setBounds(148, 5, 183, 20);

            //dropdown menu
            getContentPane().add(select);
            select.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    //numClicks++;
                    //text.setText("Button Clicked " + numClicks + " times");
        
                    //handling combobox selection
                    JComboBox cb = (JComboBox)e.getSource();
                    String userChoice = (String)cb.getSelectedItem();
        
                    if(userChoice == "jdb-show-table-list"){

                        text.setText("No need for input:");
                        b.setActionCommand("jdb-show-table-list");

                    }else if(userChoice == "jdb-show-table-column(s)"){

                        text.setText("Each separated by space enter desired column name(s) then table name:");
                        b.setActionCommand("jdb-show-table-column(s)");

                    }else if(userChoice == "jdb-show-join-table(s)"){

                        text.setText("Please enter name(s) of table(s):");
                        b.setActionCommand("jdb-show-join-table(s)");

                    }else if(userChoice == "jdb-any-query"){

                        text.setText("Please type in a raw SQL query here:");
                        b.setActionCommand("jdb-any-query");

                    }else if(userChoice == "jdb-show-related-tables"){

                        text.setText("Please enter name of desired table:");
                        b.setActionCommand("jdb-show-related-tables");

                    }else if(userChoice == "jdb-show-all-primary-keys"){

                        text.setText("No need to type anything here");
                        b.setActionCommand("jdb-show-all-primary-keys");  
                    
                    }else if(userChoice == "jdb-stat"){

                        text.setText("Please enter name table for stats separated by a comma, followed by the column name:");
                        b.setActionCommand("jdb-stat");

                    }else if(userChoice == "jdb-find-column"){

                        text.setText("Please enter name of desired column:");
                        b.setActionCommand("jdb-find-column");

                    }else if (userChoice == "jdb-products-color") {
                        text.setText("Please enter color of desired product:");
                        b.setActionCommand("jdb-products-color");
                    }
                    else if (userChoice == "jdb-hr-employee-info") {
                        text.setText("Please enter the desired employee ID:");
                        b.setActionCommand("jdb-hr-employee-info");
                    }
                    else if (userChoice == "jdb-department") {
                        text.setText("This will show the list of departments and their title:");
                        b.setActionCommand("jdb-department");
                    }
                    else if (userChoice == "jdb-city-num-customers") {
                        text.setText("This will show the list of departments and their title:");
                        b.setActionCommand("jdb-city-num-customers");
                    }
                    else if (userChoice == "jdb-hr-employee-hours") {
                        text.setText("This will show the list of departments and their title:");
                        b.setActionCommand("jdb-hr-employee-hours");
                    }
                    else if (userChoice == "jdb-search-path") {
                        text.setText("Please enter two tables");
                        b.setActionCommand("jdb-search-path");
                    }else if (userChoice == "jdb-search-and-join") {
                        text.setText("Please enter two tables");
                        b.setActionCommand("jdb-search-and-join");
                    }
                    else{

                        text.setText("");
                        b.setActionCommand("");
                    }
                }
            });
            text.setBounds(27, 31, 326, 20);

            //take in user input if needed
            getContentPane().add(text);
            text.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    text.setText("");
                }
            });

            //button to execute query
            getContentPane().add(b);
            b.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    String command = e.getActionCommand();

                    String sql;
                    ResultSet rs;
                    Statement stmt = null;

                    //String[] sql2 = line.split("\\s+");
                    String sql3 = text.getText();
                    String[] sql2 = sql3.split("\\s+");
                    String finalOut = "";

                    try{

                        stmt = conn.createStatement();

                        if(command == "jdb-show-table-list"){	
	
                          sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'adventureworks';";
                              rs = stmt.executeQuery(sql);
                              while(rs.next()){
                              String tableName  = rs.getString("table_name");
                              finalOut += (tableName + "\n");
                          }
                          
                          output.setText(finalOut);
                            
                      }else if(command == "jdb-show-table-column(s)"){
                      
                          sql = "SELECT ";
                          for(int i = 0; i < sql2.length-1 && i > -1; i++){
                              
                              if(i == 0){
                                  sql += (sql2[i]);
                              }else{
                                  sql += ("," + sql2[i]);
                              }
                          }
                          
                          if(sql2[sql2.length-1] != null){
                              sql += (" FROM " + sql2[sql2.length-1] + ";");
                          }

                          rs = stmt.executeQuery(sql);
                          ResultSetMetaData rsmd = rs.getMetaData();
                          int numColumns = rsmd.getColumnCount();
                          while(rs.next()){

                              for(int i = 1; i <= numColumns; i++){
                                  finalOut += rs.getString(i) + " ";
                              }
                              finalOut += "\n";
                          }

                          output.setText(finalOut);

                      }else if(command == "jdb-show-join-table(s)"){      //needs work, progam halts for a long time

                          sql = "";
                          //formatting query
                          for(int i = 0; i < sql2.length && i < 4; i++){
                              
                              if(i == 0){
                                  sql += ("SELECT * FROM " + sql2[i]);
                              }else{
                                  sql += ("," + sql2[i]);
                              }
                          }

                          sql += ";";

                          rs = stmt.executeQuery(sql);
                          ResultSetMetaData rsmd = rs.getMetaData();
                          int numColumns = rsmd.getColumnCount();
                          while(rs.next()){

                              for(int i = 1; i <= numColumns; i++){
                                  finalOut += rs.getString(i) + " ";
                              }
                              finalOut += "\n";
                          }

                          output.setText(finalOut);

                      }else if(command == "jdb-any-query"){
                          sql = text.getText();
                          if(sql.contains("show") && sql.contains("from")){
                              //String tablename = sql2[3];
                              //Retrieving the meta data object
                      
                              rs = stmt.executeQuery(sql);

                              ResultSetMetaData rsmd = rs.getMetaData();
                              int numColumns = rsmd.getColumnCount();
                              while(rs.next()){

                                  for(int i = 1; i <= numColumns; i++){
                                      finalOut += rs.getString(i) + " " + "\n";
                                  }
                              }
                          }
                          else if(sql.contains("show")) {
                              rs = stmt.executeQuery(sql);
                              while(rs.next()){
                                  String name = rs.getString(1);
                                  finalOut += name + "\n";
                              }
                          }
                          output.setText(finalOut);
                      }
                      else if(command == "jdb-show-related-tables"){
                          sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_SCHEMA = 'adventureworks' AND CONSTRAINT_NAME = 'PRIMARY' AND TABLE_NAME = \'";
                          sql += (sql2[0] + "\';");
                          
                          rs = stmt.executeQuery(sql);

                          //pull all found primary keys into an array
                          ArrayList<String> userTablePKs = new ArrayList<String>();
                          while(rs.next()){
                              userTablePKs.add(rs.getString("COLUMN_NAME"));
                              //System.out.println(rs.getString("COLUMN_NAME"));
                          }
                          //that command returns EmployeeID., then, with that knowledge,
                          
                          //then compare the specified primary keys against all the other table's primary keys
                          for(int j = 0; j < userTablePKs.size(); j++){

                              sql = "SELECT DISTINCT TABLE_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = \'";
                              sql += (userTablePKs.get(j) + "\';");

                              rs = stmt.executeQuery(sql);
                              while(rs.next()){
                                  //get tablename that has primary key
                                  String tableName  = rs.getString("TABLE_NAME");

                                  //Display values
                                  finalOut += ("Table Name: " + tableName + "\n");
                              }
                          }
                          output.setText(finalOut);
                      
                      }
                      else if(command == "jdb-show-all-primary-keys"){

                          sql = "SELECT TABLE_NAME, COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE CONSTRAINT_NAME = \'PRIMARY\';";
                          rs = stmt.executeQuery(sql);
                          while(rs.next()){
                              //Retrieve by tablename and corresponding column name
                              String tableName  = rs.getString("TABLE_NAME");
                              String columnName = rs.getString("COLUMN_NAME");
                  
                              //Display values
                              finalOut += ("Table Name: " + tableName);
                              finalOut += (", Column: " + columnName + "\n");
                          }
                          output.setText(finalOut);

                      }
                      else if(command == "jdb-stat") {
                           String table = text.getText().split(",")[0];
                           String column = text.getText().split(",")[1];
                        String sqlQuery = "";
                        String min = "";
                        String max = "";
                        String avg = "";
                    sqlQuery = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'adventureworks';";
                    rs = stmt.executeQuery(sqlQuery);
                          ArrayList<String> tables = new ArrayList<String>();
                    while(rs.next()) {
                      tables.add(rs.getString("table_name"));
                    }
                        if(tables.contains(table)) {
                             sqlQuery = "SELECT MIN(" + column + ") FROM "+ table + ";";
                          rs = stmt.executeQuery(sqlQuery);
                        while(rs.next()) {
                          String tmp = "MIN("+ column + ")";
                          min = rs.getString(tmp);
                        }
                        finalOut += "MIN: " + min + " " + "\n";
                        sqlQuery = "SELECT MAX(" + column + ") FROM "+ table + ";";
                        rs = stmt.executeQuery(sqlQuery);
                        while(rs.next()) {
                          String tmp = "MAX("+ column + ")";
                          max = rs.getString(tmp);
                        }
                        finalOut += "MAX: " + max + " " + "\n";
                        sqlQuery = "SELECT AVG(" + column + ") FROM "+ table + ";";
                        rs = stmt.executeQuery(sqlQuery);
                        while(rs.next()) {
                          String tmp = "AVG("+ column + ")";
                          avg = rs.getString(tmp);
                        }
                        finalOut += "AVG: " + avg + " " + "\n";
                        sqlQuery = "SELECT " + column + "  FROM  " + table + ";";
                        rs = stmt.executeQuery(sqlQuery);
                        sqlQuery = "SELECT " + column + " FROM "+ table + ";";
                        ArrayList<Integer> results = new ArrayList<Integer>();
                        while(rs.next()) {
                          results.add(Integer.parseInt(rs.getString(column)));
                        }
                        Collections.sort(results);
                        Integer median = 0;
                        if(results.size() % 2 == 0) {
                          median = (results.get(results.size()/2 - 1) + results.get(results.size()/2))/2;
                          finalOut += "MEDIAN: " + median + " " + "\n";
                        }
                        else {
                          median = results.get(results.size()/2);
                          finalOut += "MEDIAN: " + median + " " + "\n";
                        }
                    }
                        output.setText(finalOut);
                      }
                      else if(command == "jdb-find-column"){
                          sql = "SELECT COLUMN_NAME, TABLE_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = \"";
                            sql = sql + text.getText() + "\";";
                            rs = stmt.executeQuery(sql);
                          
                          while(rs.next()){
                              //Retrieve by tablename and corresponding columns name
                              String tableName  = rs.getString("TABLE_NAME");
                              String columnName = rs.getString("COLUMN_NAME");
                              
                              finalOut += ("Table Name: " + tableName + ", Column: " + columnName + "\n");   
                          }

                          output.setText(finalOut);

                      }
                      //given a color it will output all the products that are listed in that color
                      else if (command == "jdb-products-color") {
                              sql = "SELECT Name, ProductNumber, ProductID, Color ";
                              sql += "FROM product ";
                              sql += "WHERE Color LIKE \'";
                              sql = sql.concat(sql2[0]);
                              sql = sql.concat("\'");
                              rs = stmt.executeQuery(sql);
                              while (rs.next()) {
                                      String name = rs.getString("Name");
                                      String product = rs.getString("ProductNumber");
                                      String productid = rs.getString("ProductID");
                                      finalOut += "Name: " + name + ", Product Number: " + product + ", Product ID: " + productid + "\n";
                              }
                              output.setText(finalOut);
                      }
                      // given an employee id (1,2,...) it will give the user the name, title and phone number of employee
                      // ex: jdb-hr-employee-info 24 5 2 will output the information of employee ID's 24,5 and 2
                      else if (command == "jdb-hr-employee-info") { 
                              int sql_length = sql2.length;
                              for (int i = 0; i < sql_length; i++) {
                                      sql = "SELECT ContactID, Title from employee where EmployeeID = ";
                                      sql = sql.concat(sql2[i]);
                                      rs = stmt.executeQuery(sql);
                                      rs.next();

                                      String ID = rs.getString("ContactID");
                                      String title = rs.getString("Title");
                                      sql = "SELECT FirstName, LastName, Phone,ContactID from contact where ContactID = ";
                                      sql = sql.concat(ID);
                                      rs = stmt.executeQuery(sql);

                                      while (rs.next()){
                                              String first = rs.getString("FirstName");
                                              String last = rs.getString("LastName");
                                              String phone = rs.getString("Phone");

                                              finalOut += sql2[i] + ": " + first + " " + last + " " + title + " " + phone + "\n";
                                      }
                              }
                              output.setText(finalOut);
                      }
                      // given a job department id, it will output all the employees in that department and their titles 
                      else if (command == "jdb-department") { 
                              // given a job department id, it will output all the employees in that department and their titles 
                              int count = 0;
                              sql = "SELECT d.Name, d.GroupName, c.FirstName, c.LastName, e.Title ";
                              sql += "from department d, contact c, employee e ";
                              sql += "Where e.ContactID = c.ContactID && d.DepartmentID = ";
                              sql = sql.concat(sql2[0]);
                              sql += " order by e.title ASC";
                              rs = stmt.executeQuery(sql);
                              while (rs.next()) {
                                 String name = rs.getString("Name");
                                 String group = rs.getString("GroupName");
                                 String first = rs.getString("FirstName");
                                 String last = rs.getString("LastName");
                                 String title = rs.getString("Title");
                                // count variable to only print the department/group name once to avoid repititive printing of department name
                                 if (count == 0) {
                                      finalOut += "Department: " + name + ", Group Name: " + group + "\n";
                                      count++;
                                 }
                                 finalOut += "   "+ first + " " + last + ", " + title + "\n";
               
                              }
                              output.setText(finalOut);
                      }
                      //Lists quantity of customers that lives in each city within table
                      else if(command == "jdb-city-num-customers"){
                          sql =  "SELECT address.City, COUNT(customer.CustomerID) ";
                          sql += "FROM customer, customeraddress, address ";
                          sql += "WHERE customer.CustomerID = customeraddress.CustomerID AND ";
                          sql += "customeraddress.AddressID = address.AddressID GROUP BY address.City;";
           
                          rs = stmt.executeQuery(sql);
                          while(rs.next()){
                             //Retrieve by column name
                             String city  = rs.getString("address.City");
                             int population = rs.getInt("COUNT(customer.CustomerID)");
                 
                             //Display values
                             finalOut += "City: " + city + ",\n";
                             finalOut += "# of customers: " + population + "\n";
                          }
                          output.setText(finalOut);
                      }
                      //do not need to input anything just hit click
                      else if (command == "jdb-hr-employee-hours") {
                          int count = 1;
                          float night = 0;
                          int night_count = 0;
                          float day = 0;
                          int day_count = 0;
                          float evening = 0;
                          int evening_count = 0;
                          sql = "select s.Name, c.FirstName, c.LastName, e.Title, e.VacationHours, e.SickLeaveHours ";
                          sql += "from employeedepartmenthistory ed, shift s, employee e, contact c ";
                          sql += "where ed.EmployeeID = e.EmployeeID && e.ContactID = c.ContactID ";
                          sql += "order by (e.VacationHours + e.SickLeaveHours) DESC";
                          rs = stmt.executeQuery(sql);
                          while (rs.next()) {
                             String first = rs.getString("FirstName");
                             String last = rs.getString("LastName");
                             String title = rs.getString("Title");
                             String shift = rs.getString("Name");
                             int hours = rs.getInt("VacationHours") + rs.getInt("SickLeaveHours");
                             if (count <= 3) {
                                switch(shift) {
                                   case "Day":
                                      day += hours;
                                      day_count++;
                                      break;
                                   case "Evening":
                                      evening += hours;
                                      evening_count++;
                                      break;
                                   case "Night":
                                      night += hours;
                                      night_count++;
                                      break;
                                }
                                finalOut += first + " " + last + ", " + title + " Total Sick/Vacation Hours: " + hours + "\n";
                             }
                             else if (count == 9) {
                                count = 0;
                             }
                             count++;
                          }
                          day = day/day_count;
                          evening = evening/evening_count;
                          night = night/night_count;
                          finalOut += "\nAverage Sick/Vacation Hours:\n";
                          finalOut += "Day: " + day + "\nEvening: " + evening + "\nNight: " + night;
                          output.setText(finalOut);
                       }
                      else if (command == "jdb-search-path")
                      {
                        
                        
                        rs = null;
                        String line = text.getText();
                        String[] items = line.split("\\s+");
                        String table1 = items[0];
                        String table2 = items[1];
                        
                        Vector<String> table_path = path(stmt,table1,table2,conn,rs);
                        String res = "";
                        for(int i = 0; i < table_path.size(); i++)
                        {
                          if(i == table_path.size()-1)
                          {
                            res += table_path.get(i);
                          }
                          else
                          {
                            res += table_path.get(i) + " -> ";
                          }
                          
                        }
                        
                        output.setText(res);
                      }
                      
                      else if (command == "jdb-search-and-join")
                      {
                        
                        
                        rs = null;
                        String line = text.getText();
                        String[] items = line.split("\\s+");
                        String table1 = items[0];
                        String table2 = items[1];
                        
                        Vector<String> table_path = altered_path(stmt,table1,table2,conn,rs);
                        String res = "";
                        for(int i = 0; i < table_path.size(); i++)
                        {
                          if(i == table_path.size()-1)
                          {
                            res += table_path.get(i);
                          }
                          else
                          {
                            res += table_path.get(i) + " -> ";
                          }
                          
                        }
                        
                        output.setText(res);
                      }
                      else{
                          output.selectAll();
                          output.replaceSelection("");
                      }

                    }catch(SQLException se){
                        //Handle errors for JDBC
                        System.out.println("Error: " + se.getMessage());
                        se.printStackTrace();
                     }catch(Exception e1){
                        //Handle errors for Class.forName
                        e1.printStackTrace();
                     }

                }
            });

            //JTextArea to hold output from queries
            scroll = new JScrollPane(output,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            //scroll.setBounds(434, 40, 19, 2);
            //getContentPane().add(scroll);
            scroll.setBounds(78, 58, 324, 454);
            getContentPane().add(scroll);
            backButton.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		if(e.getSource() == backButton)
        				switchPage = !switchPage;
        				System.out.println(switchPage);
            	}
            });
            backButton.setBounds(399, 263, 81, 23);
            
            getContentPane().add(backButton);
        }

        //This is for handling button click events associated with specific commands
        @Override
        public void actionPerformed(ActionEvent e) {
            
        }

        public void windowClosing(WindowEvent e) {

            dispose();
            System.exit(0);
        }

        public void windowOpened(WindowEvent e) {}
        public void windowActivated(WindowEvent e) {}
        public void windowIconified(WindowEvent e) {}
        public void windowDeiconified(WindowEvent e) {}
        public void windowDeactivated(WindowEvent e) {}
        public void windowClosed(WindowEvent e) {}

        public void mouseClicked(MouseEvent event){  
            System.out.println("Mouse clicked @ position x = "
                + event.getX() + " y = " + event.getY());      
        }

        public void mouseEntered(MouseEvent event){
            System.out.println("Mouse entered. x = " 
                + event.getX() + " y = " + event.getY());
        }

        public void mouseExited(MouseEvent event){  
            System.out.println("Mouse exited. x = " 
                + event.getX() + " y = " + event.getY());
        }

        public void mousePressed(MouseEvent event){
            System.out.println("Mouse pressed. x = " 
                + event.getX() + " y = " + event.getY());
        }

        public void mouseReleased(MouseEvent event){  
            System.out.println("Mouse released. x = " 
                + event.getX() + " y = " + event.getY());
        }
}