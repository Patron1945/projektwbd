package com.wbd.projekt;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.TextArea;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientView extends JFrame
{
	DBConnection dbc;
	
	private JTextArea txtrDane;
	private JTextArea textArea_1;
	private JTextArea textArea_2;
	private JButton btnNewButton;
	private JTextArea txtrPrzelewy;
	private JTextArea textArea;
	private JButton btnOdswiez;
	private JTextArea txtrDane_1;
	
	public ClientView(final DBConnection dbc)
	{
		this.dbc = dbc;
		
		setSize(new Dimension(750, 500));
		setTitle("Witamy w oddzialne online");
		setName("frame65");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		

		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ce) 
			{
				switch(tabbedPane.getSelectedIndex())
				{
				case 0: {
							System.out.println("DANE");
							downloadClientData();
							break;
						}
				case 1: {
							System.out.println("KONTA");
							downloadAccountData();
							break;
						}
				case 2: {
							System.out.println("KARTY");
							downloadCardsData();
							break;
						}	
				case 3: {
							System.out.println("PRZELEWY");
							downloadMoneyTransfersData();
							break;
						}
				case 4: {
							System.out.println("WPLATY/WYPLATY");
							downloadMoneyOperationsData();
							break;
						}
				default: System.out.println("B£AD");
						 break;
				}
			}
		});
		tabbedPane.setName("");
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		txtrDane_1 = new JTextArea();
		tabbedPane.addTab("Dane", null, txtrDane_1, null);

		textArea_1 = new JTextArea();
		tabbedPane.addTab("Konta", null, textArea_1, null);

		textArea_2 = new JTextArea();
		tabbedPane.addTab("Karty", null, textArea_2, null);
		
		txtrPrzelewy = new JTextArea();
		tabbedPane.addTab("Przelewy", null, txtrPrzelewy, null);
		
		textArea = new JTextArea();
		tabbedPane.addTab("Operacje got\u00F3wkowe", null, textArea, null);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(100, 100));
		panel.setSize(new Dimension(100, 100));
		getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(null);

		JButton btnWyloguj = new JButton("Wyloguj");
		btnWyloguj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				new LoginWindow();
				dispose();
			}
		});
		btnWyloguj.setBounds(0, 395, 99, 56);
		panel.add(btnWyloguj);
		
		btnNewButton = new JButton("Edytuj dane");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				new ClientDataEditionWindow(dbc);
			}
		});
		btnNewButton.setBounds(0, 21, 99, 56);
		panel.add(btnNewButton);
		
		btnOdswiez = new JButton("Odswiez");
		btnOdswiez.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				switch(tabbedPane.getSelectedIndex())
				{
				case 0: {
							System.out.println("DANE");
							downloadClientData();
							break;
						}
						
				case 1: {
							System.out.println("KONTA");
							downloadAccountData();
							break;
						}
				case 2: {
							System.out.println("KARTY");
							downloadCardsData();
							break;
						}	
				case 3: {
							System.out.println("PRZELEWY");
							downloadMoneyTransfersData();
							break;
						}
				case 4: {
							System.out.println("WPLATY/WYPLATY");
							downloadMoneyOperationsData();
							break;
						}
				default: System.out.println("B£AD");
						 break;
				}
			}
		});
		btnOdswiez.setBounds(0, 327, 99, 57);
		panel.add(btnOdswiez);
		txtrDane = new JTextArea();
		txtrDane.setBounds(-628, 21, 629, 434);
		panel.add(txtrDane);
		
		setVisible(true);
	}
	
	private void downloadMoneyOperationsData()
	{
		String query = "SELECT * FROM \"Obs³uga_gotowkowa\" WHERE " +
				"\"Id_konta\" IN (SELECT \"Id_konta\" FROM \"Konta\" WHERE \"Id_klienta\" = 1)";
		
		ResultSet rs = dbc.sendQuery(query);
		
		try
		{
			StringBuilder sb = new StringBuilder();
			String pause = "*******************************\n";
			
			sb.append("WPLATY/WYPLATY:\n" + pause);
			
			while(rs.next())
			{
				String tmpNameString = null;
				String tmpValueString = null;
				String toPrint = null;
				
				tmpNameString = "Kwota";
				tmpValueString = String.valueOf(rs.getInt(tmpNameString));
				toPrint = String.format("%-30s %20s %n", tmpNameString , tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Data_zlozenia_dyspozycji";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString , tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Data_wykonania";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString , tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Typ_operacji";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString , tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Miejsce";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString , tmpValueString);
				sb.append(toPrint);
				
				sb.append(pause);
				textArea.setText(sb.toString());
				
			}
		}
		catch (SQLException e)
		{	
			e.printStackTrace();
		}
	}
	
	private void downloadMoneyTransfersData()
	{
		String query = "SELECT * FROM \"Przelewy_pieniedzy\" WHERE " +
				"\"Id_konta\" IN (SELECT \"Id_konta\" FROM \"Konta\" WHERE \"Id_klienta\" = 1)";
		
		ResultSet rs = dbc.sendQuery(query);
		
		try
		{
			StringBuilder sb = new StringBuilder();
			String pause = "*******************************\n";
			
			sb.append("PRZELEWY:\n" + pause);
			
			while(rs.next())
			{
				String tmpNameString = null;
				String tmpValueString = null;
				String toPrint = null;
				
				tmpNameString = "Kwota";
				tmpValueString = String.valueOf(rs.getInt(tmpNameString));
				toPrint = String.format("%-30s %20s %n", tmpNameString , tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Data_zlozenia_dyspozycji";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString , tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Data_wykonania";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString , tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Numer_konta_docelowy";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString , tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Numer_konta_zrodlowy";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", "Przypisana do konta:" , tmpValueString);
				sb.append(toPrint);
				
				sb.append(pause);
				txtrPrzelewy.setText(sb.toString());
				
			}
		}
		catch (SQLException e)
		{	
			e.printStackTrace();
		}
	}
	
	private void downloadCardsData()
	{
		String query = "SELECT kr.\"Nr_karty\", kr.\"Czy_kredytowa\", kn.\"Numer_konta\"" +
						"FROM \"Karty\" kr JOIN \"Konta\" kn " + 
						"ON kr.\"Id_konta\" = kn.\"Id_konta\"" +
						"WHERE kn.\"Id_klienta\" = 1";
		ResultSet rs = dbc.sendQuery(query);
		
		try
		{
			StringBuilder sb = new StringBuilder();
			String pause = "*******************************\n";
			
			sb.append("KARTY:\n" + pause);
			
			while(rs.next())
			{
				String tmpNameString = null;
				String tmpValueString = null;
				String toPrint = null;
				

				tmpNameString = "Numer_konta";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", "Przypisana do konta:" , tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Nr_karty";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Czy_kredytowa";
				tmpValueString = rs.getString(tmpNameString);
				if(tmpValueString.equals("T"))
				{
					tmpValueString = "TAK";
				}
				else if(tmpValueString.equals("N"))
				{
					tmpValueString = "NIE";
				}
				else
				{
					tmpValueString = "BLAD";
				}
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				sb.append(pause);
				textArea_2.setText(sb.toString());

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void downloadAccountData()
	{
		String query = "SELECT * FROM \"Konta\" WHERE \"Id_klienta\" = \'1\'";
		ResultSet rs = dbc.sendQuery(query);
		
		try
		{
			StringBuilder sb = new StringBuilder();
			String pause = "*******************************\n";
			
			sb.append("KONTA:\n" + pause);
			
			while(rs.next())
			{
				String tmpNameString = null;
				String tmpValueString = null;
				String toPrint = null;
				
				tmpNameString = "Numer_konta";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Stan";
				tmpValueString = String.valueOf(rs.getFloat(tmpNameString));
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Oprocentowanie";
				tmpValueString = String.valueOf(rs.getFloat(tmpNameString));
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Data_zalozenia";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Dzienny_limit";
				tmpValueString = String.valueOf(rs.getInt(tmpNameString));
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				sb.append(pause);
				textArea_1.setText(sb.toString());

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void downloadClientData()
	{
		String query = "SELECT * FROM \"Klienci\" WHERE \"Id_klienta\" = \'1\'";
		ResultSet rs = dbc.sendQuery(query);
		
		try
		{
			StringBuilder sb = new StringBuilder();
			String pause = "*******************************\n";
			
			sb.append("DANE KLIENTA:\n" + pause);
			
			while(rs.next())
			{
				String tmpNameString = null;
				String tmpValueString = null;
				String toPrint = null;
				
				tmpNameString = "Id_klienta";
				tmpValueString = String.valueOf(rs.getInt(tmpNameString));
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Imie";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Nazwisko";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "PESEL";
				tmpValueString = String.valueOf(rs.getLong(tmpNameString));
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Miasto";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Ulica";
				tmpValueString = rs.getString(tmpNameString);
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Nr posesji";
				tmpValueString = String.valueOf(rs.getInt(tmpNameString));
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Nr mieszkania";
				tmpValueString = String.valueOf(rs.getInt(tmpNameString));
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Nr identyfikacyjny";
				tmpValueString = String.valueOf(rs.getString(tmpNameString));
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Kod pocztowy";
				tmpValueString = String.valueOf(rs.getString(tmpNameString));
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				tmpNameString = "Data_urodzenia";
				tmpValueString = String.valueOf(rs.getString(tmpNameString));
				toPrint = String.format("%-30s %20s %n", tmpNameString, tmpValueString);
				sb.append(toPrint);
				
				sb.append(pause);
				txtrDane_1.setText(sb.toString());
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		if(dbc != null)
			dbc.disconnectFromDataBase();
	}	
	
	
}
