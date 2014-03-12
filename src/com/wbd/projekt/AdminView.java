package com.wbd.projekt;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.ListModel;

import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminView extends JFrame
{

	private DBConnection dbc;

	private JList<String> list;
	private JLabel lblListaRelacji;
	private JTable table;
	private JScrollPane scrollPane_1;
	private DefaultTableModel model;
	private JPanel panel_1;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	
	private String lastOperation;
	private int lastSelectedRow;
	private int[] currentIds;

	public AdminView(final DBConnection dbc)
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(750, 500));

		this.dbc = dbc;
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(150, 150));
		getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));

		list = new JList<String>(downloadRelationsList());
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me)
			{
				if(me.getClickCount() == 2)
				{
					me.consume();
					String tableName = (String) list.getSelectedValue();
					downloadTableData(tableName);
					System.out.println(tableName);
				}
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setPreferredSize(new Dimension(140, 200));
		panel.add(list);

		lblListaRelacji = new JLabel("Lista relacji");
		lblListaRelacji.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaRelacji.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
		panel.add(lblListaRelacji, BorderLayout.NORTH);

		model = new DefaultTableModel();
		
		table = new JTable(model);
		scrollPane_1 = new JScrollPane(table);
		scrollPane_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		getContentPane().add(scrollPane_1, BorderLayout.CENTER);

		panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(125, 125));
		getContentPane().add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(null);

		btnNewButton_1 = new JButton("Usu\u0144 wiersz");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				removeRow();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.setBounds(0, 73, 125, 51);
		panel_1.add(btnNewButton_1);

		btnNewButton = new JButton("Dodaj wiersz");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				insertRow();
			}
		});
		btnNewButton.setBounds(0, 11, 125, 51);
		panel_1.add(btnNewButton);

		btnNewButton_3 = new JButton("OK");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(lastOperation.equals("InsertRow"))
				{
					StringBuilder queryBuilder = new StringBuilder();
					queryBuilder.append("INSERT INTO ");
					queryBuilder.append("\"" + list.getSelectedValue() + "\" (");
					for(int i = 0; i < model.getColumnCount(); i++)
					{
						queryBuilder.append("\"" + model.getColumnName(i) + "\"");
						if(i+1 != model.getColumnCount())
						{
							queryBuilder.append(", ");
						}
						else
						{
							queryBuilder.append(") ");
						}
					}
					queryBuilder.append(" VALUES (");
					for(int i = 0; i <model.getColumnCount(); i++)
					{
						if(model.getColumnName(i).contains("Data") && !model.getColumnName(i).contains("Data_urodzenia"))
						{
							queryBuilder.append("TO_DATE(\'" + model.getValueAt(model.getRowCount()-1, i) + "\'");
							queryBuilder.append(",\'RR-MM-DD\')");
						}
						else if(model.getColumnName(i).contains("Termin"))
						{
							queryBuilder.append("TO_DATE(\'" + model.getValueAt(model.getRowCount()-1, i) + "\'");
							queryBuilder.append(",\'RR-MM-DD\')");
						}
						else
						{
							queryBuilder.append("\'" + model.getValueAt(model.getRowCount()-1, i) + "\'");
						}
						
						if(i+1 != model.getColumnCount())
						{
							queryBuilder.append(", ");
						}
						else
						{
							queryBuilder.append(") ");
						}
							
					}
					
					System.out.println(queryBuilder.toString());
					String tmpStr = queryBuilder.toString();
					tmpStr = tmpStr.toString().replace('.', ',');
					queryBuilder = new StringBuilder(tmpStr);
					ResultSet rs = dbc.sendQuery(queryBuilder.toString());
					
					lastOperation = null;
					
					String tableName = (String) list.getSelectedValue();
					downloadTableData(tableName);
				}
				else if(lastOperation.equals("RemoveRow"))
				{
					StringBuilder queryBuilder = new StringBuilder();
					queryBuilder.append("DELETE FROM ");
					queryBuilder.append("\"" + list.getSelectedValue() +"\" ");
					queryBuilder.append("WHERE " + "\"" + model.getColumnName(0) + "\"" );
					String tmpVal = (String) model.getValueAt(lastSelectedRow, 0);
					tmpVal = tmpVal.substring(1);
					queryBuilder.append(" = " + "\'" + tmpVal+ "\'");
					
					System.out.println(queryBuilder.toString());
					
					dbc.sendQuery(queryBuilder.toString());
					
					lastOperation = null;
					lastSelectedRow = -1;
					
					String tableName = (String) list.getSelectedValue();
					downloadTableData(tableName);
				}
				else if(lastOperation.equals("UpdateRow"))
				{
					StringBuilder queryBuilder = new StringBuilder();
					queryBuilder.append("UPDATE ");
					queryBuilder.append("\"" + list.getSelectedValue() +"\" ");
					queryBuilder.append("SET ");
					
					for(int i = 0 ; i < model.getColumnCount(); i++)
					{
						queryBuilder.append("\"" + model.getColumnName(i) + "\" = " );
						queryBuilder.append("\'" + model.getValueAt(lastSelectedRow, i) + "\'");
						if(i+1 != model.getColumnCount())
						{
							queryBuilder.append(", ");
						}
						else
						{
							queryBuilder.append(" ");
						}
					}
					
					queryBuilder.append("WHERE ");
					queryBuilder.append("\"" + model.getColumnName(0) + "\"" + " = " + "\'" + currentIds[lastSelectedRow] + "\'");
					
					System.out.println(queryBuilder.toString());
					dbc.sendQuery(queryBuilder.toString());
					
					lastOperation = null;
					lastSelectedRow = -1;
					
					String tableName = (String) list.getSelectedValue();
					downloadTableData(tableName);
				}
			}

		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_3.setBounds(0, 229, 125, 51);
		panel_1.add(btnNewButton_3);

		btnNewButton_4 = new JButton("Wyloguj");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				new LoginWindow();
				dispose();
			}
		});
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_4.setBounds(0, 400, 125, 51);
		panel_1.add(btnNewButton_4);
		
		JButton btnNewButton_2 = new JButton("Edytuj wiersz");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				updateRow();
			}
		});
		btnNewButton_2.setBounds(0, 135, 125, 51);
		panel_1.add(btnNewButton_2);



		setVisible(true);
	}

	private void insertRow()
	{
		model.addRow(new String[model.getColumnCount()]);
		lastOperation = "InsertRow";
	}
	
	private void updateRow()
	{
		lastOperation = "UpdateRow";
		lastSelectedRow = table.getSelectedRow();
	}
	
	private void removeRow()
	{
		lastOperation = "RemoveRow";
		String ob = (String) model.getValueAt(table.getSelectedRow(), 0);
		ob = "-" + ob;
		model.setValueAt(ob, table.getSelectedRow(), 0);
		lastSelectedRow = table.getSelectedRow();
	}

	public ListModel<String> downloadRelationsList()
	{
		DefaultListModel<String> lm = new DefaultListModel<String>();
		String query = "SELECT table_name FROM user_tables";

		ResultSet rs = dbc.sendQuery(query);

		try
		{
			while (rs.next())
			{
				String name = "table_name";
				String tableName = null;
				tableName = rs.getString(name);
				lm.addElement(tableName);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return lm;
	}

	public void downloadTableData(String tableName)
	{
		model.setColumnCount(0);
		model.setRowCount(0);
		LinkedList<String> columns = new LinkedList<String>();
		LinkedList<String[]> attributes = new LinkedList<String[]>();
		String query = "SELECT column_name FROM user_tab_cols WHERE table_name = \'" + tableName + "\'";

		ResultSet rs = dbc.sendQuery(query);

		try
		{
			while (rs.next())
			{
				columns.add(rs.getString("column_name"));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		query = "SELECT * FROM \"" + tableName + "\"";
		rs = dbc.sendQuery(query);

		try
		{
			String[] row;
			while (rs.next())
			{
				row = new String[columns.size()];
				for (int i = 0; i < columns.size(); i++)
				{
					row[i] = rs.getString(columns.get(i));
				}

				attributes.add(row);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		String[][] tempTab = new String[attributes.size()][columns.size()];

		for (int i = 0; i < attributes.size(); i++)
		{
			for (int j = 0; j < columns.size(); j++)
			{
				tempTab[i][j] = attributes.get(i)[j];
			}
		}

		// new TableView(tempTab, columns.toArray(new String[0]));

		for (int i = 0; i < columns.size(); i++)
		{
			model.addColumn(columns.get(i));
		}

		for (int i = 0; i < attributes.size(); i++)
		{
			model.addRow(tempTab[i]);
		}

		table.createDefaultColumnsFromModel();

		currentIds = new int[attributes.size()];
		
		for (int i = 0; i < attributes.size(); i++)
		{
			currentIds[i] = Integer.valueOf(attributes.get(i)[0]);
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
