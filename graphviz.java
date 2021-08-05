
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.enoir.graphvizapi.*;



public class LimitConnections implements Filter {
	// declaring class variables
	private int limit = 3; // limit N connections
	private int count;
	private Object lockobj = new Object();
	
	public void doConnectoinFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
	try {
	boolean ok;
	synchronized (lockobj) {
	ok = count++ < limit;
	}
	if (ok) {
	// proeess new connection
	chain.doFilter(request, response);
	   			HandleNextIncomingConnection();
	} else { // closing connection
	CloseExistingConnection();
	}
	} finally {
	synchronized (lockobj) { //synchronizing it limit connection reaches
	count--;
	}   
	}
	}
	}


	        

	  
	

