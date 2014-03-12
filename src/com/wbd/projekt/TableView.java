package com.wbd.projekt;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import java.awt.Dimension;

public class TableView extends JFrame
{
	private JTable table;
	public TableView(String[][] data, String[] columns) 
	{
		setVisible(true);
		setSize(new Dimension(450, 300));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		table = new JTable(data, columns);		
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}

}
