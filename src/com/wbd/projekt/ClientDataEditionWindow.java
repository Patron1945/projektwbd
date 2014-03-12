package com.wbd.projekt;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class ClientDataEditionWindow extends JFrame
{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private DBConnection dbc;
	
	public ClientDataEditionWindow(final DBConnection dbc) 
	{
		setSize(new Dimension(450, 300));
		setVisible(true);
		
		this.dbc = dbc;
		getContentPane().setBackground(SystemColor.text);
		setTitle("Edycja danych");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(199, 33, 140, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText("");
		
		JTextPane txtpnMiasto = new JTextPane();
		txtpnMiasto.setEditable(false);
		txtpnMiasto.setText("Miasto");
		txtpnMiasto.setBounds(26, 33, 115, 20);
		getContentPane().add(txtpnMiasto);
		
		JTextPane txtpnUlica = new JTextPane();
		txtpnUlica.setText("Ulica");
		txtpnUlica.setEditable(false);
		txtpnUlica.setBounds(26, 71, 115, 20);
		getContentPane().add(txtpnUlica);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(199, 71, 140, 20);
		getContentPane().add(textField_1);
		textField_1.setText("");
		
		JTextPane txtpnNrPosesji = new JTextPane();
		txtpnNrPosesji.setText("Nr posesji");
		txtpnNrPosesji.setEditable(false);
		txtpnNrPosesji.setBounds(26, 106, 115, 20);
		getContentPane().add(txtpnNrPosesji);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(199, 106, 140, 20);
		getContentPane().add(textField_2);
		textField_2.setText("");
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(199, 137, 140, 20);
		getContentPane().add(textField_3);
		textField_3.setText("");
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(199, 168, 140, 20);
		getContentPane().add(textField_4);
		textField_4.setText("");
		
		JTextPane txtpnNrMieszkania = new JTextPane();
		txtpnNrMieszkania.setText("Nr mieszkania");
		txtpnNrMieszkania.setEditable(false);
		txtpnNrMieszkania.setBounds(26, 137, 115, 20);
		getContentPane().add(txtpnNrMieszkania);
		
		JTextPane txtpnKodPocztowyxxxxx = new JTextPane();
		txtpnKodPocztowyxxxxx.setText("Kod pocztowy (np. 00123)");
		txtpnKodPocztowyxxxxx.setEditable(false);
		txtpnKodPocztowyxxxxx.setBounds(26, 168, 152, 20);
		getContentPane().add(txtpnKodPocztowyxxxxx);
		
		JButton btnAkceptuj = new JButton("Akceptuj");
		btnAkceptuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				StringBuilder sbQuery = new StringBuilder();
				
				sbQuery.append("UPDATE \"Klienci\" SET");
				if(!textField.getText().equals(""))
					sbQuery.append(" \"Miasto\" = " + "\'" + textField.getText() + "\'" + ",");
				if(!textField_1.getText().equals(""))
					sbQuery.append(" \"Ulica\" = " + "\'" + textField_1.getText() + "\'" + ",");
				if(!textField_2.getText().equals(""))
					sbQuery.append(" \"Nr posesji\" = " + "\'" + textField_2.getText() + "\'" + ",");
				if(!textField_3.getText().equals(""))
					sbQuery.append(" \"Nr mieszkania\" = " + "\'" + textField_3.getText() + "\'" + ",");
				if(!textField_4.getText().equals(""))
					sbQuery.append(" \"Kod pocztowy\" = " + "\'" + textField_4.getText() + "\'" + ",");
				if(sbQuery.toString().endsWith(","))
					sbQuery.deleteCharAt(sbQuery.length()-1);
				sbQuery.append(" WHERE \"Id_klienta\" = 1");
				
				dbc.sendQuery(sbQuery.toString());
				System.out.print(sbQuery.toString());
				
				dispose();
			}
		});
		btnAkceptuj.setBounds(52, 229, 89, 23);
		getContentPane().add(btnAkceptuj);
		
		JButton btnAnuluj = new JButton("Anuluj");
		btnAnuluj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
			}
		});
		btnAnuluj.setBounds(151, 229, 89, 23);
		getContentPane().add(btnAnuluj);
		
		JButton btnWyczy = new JButton("Wyczy\u015B\u0107");
		btnWyczy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textField_4.setText("");
			}
		});
		btnWyczy.setBounds(250, 229, 89, 23);
		getContentPane().add(btnWyczy);
	}
}
