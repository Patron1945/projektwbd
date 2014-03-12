package com.wbd.projekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection
{
	
	private static DBConnection instance = null;
	
	private Connection mCon;
	private Statement mStmt;
	private ResultSet mRs;
	
	private String mUser, mPass;
	private String mDBUrl;
	
	private boolean mConnected;
	
	PreparedStatement ps;
	
	static
	{
	    try 
	    {
	        Class.forName ("oracle.jdbc.OracleDriver");   
	    }   
	    catch (ClassNotFoundException e) 
	    {
	        e.printStackTrace();
	    }
	}
	
	protected DBConnection()
	{
		mUser = "pkucharz";
		mPass = "abcdefgh";
		mDBUrl = "jdbc:oracle:thin:@localhost:1521:OracleWBD";	
		
	}
	
	public static DBConnection getInstance() 
	{
	      if(instance == null) 
	      {
	         instance = new DBConnection();
	      }
	      
	      return instance;
	   }
	
	public void connectToDataBase()
	{
		try
		{
			mCon = DriverManager.getConnection(mDBUrl, mUser, mPass);
			mConnected = true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			mConnected = false;
		}
	}
	
	public void disconnectFromDataBase()
	{
		if(mConnected)
		{
			try
			{
				mRs.close();
				mStmt.close();
				mCon.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public ResultSet sendQuery(String query)
	{	
		try
		{
			mStmt = mCon.createStatement();
			mRs = mStmt.executeQuery(query);
			return mRs;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		
		
		
	}
	
//	public static void main(String... args)
//	{
//		DBConnection dbc = DBConnection.getInstance();
//		try
//		{
//			dbc.connectToDataBase();
//		}
//		catch (SQLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
