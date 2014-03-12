package com.wbd.projekt;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.sql.SQLException;

public class LoginWindow extends JFrame
{
	private JTextField txtLogin;
	private JPasswordField passwordField;
	private JTextField txtHaso;
	private JTextField txtLogin_1;
	DBConnection dbc;

	public LoginWindow()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(500, 250));
		getContentPane().setBackground(Color.ORANGE);
		setBackground(UIManager.getColor("FormattedTextField.selectionBackground"));
		setTitle("Baza danych odzia\u0142u banku");
		getContentPane().setLayout(null);

		txtLogin = new JTextField();
		txtLogin.setBounds(60, 72, 150, 24);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(268, 72, 147, 24);
		getContentPane().add(passwordField);

		txtHaso = new JTextField();
		txtHaso.setEnabled(false);
		txtHaso.setBackground(Color.ORANGE);
		txtHaso.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txtHaso.setText("Has\u0142o");
		txtHaso.setBounds(220, 72, 47, 24);
		getContentPane().add(txtHaso);
		txtHaso.setColumns(10);

		txtLogin_1 = new JTextField();
		txtLogin_1.setEnabled(false);
		txtLogin_1.setForeground(Color.BLACK);
		txtLogin_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txtLogin_1.setBackground(Color.ORANGE);
		txtLogin_1.setText("Login");
		txtLogin_1.setBounds(10, 72, 47, 25);
		getContentPane().add(txtLogin_1);
		txtLogin_1.setColumns(10);

		JButton btnZaloguj = new JButton("Zaloguj");
		btnZaloguj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				dbc = DBConnection.getInstance();
				if(txtLogin.getText().equals("admin") && new String(passwordField.getPassword()).equals("admin"))
				{
					System.out.println("ADMIN");
					dbc.connectToDataBase();
					new AdminView(dbc);
					dispose();
				}
				else if(txtLogin.getText().equals("pracownik") && new String(passwordField.getPassword()).equals("pracownik"))
				{
					System.out.println("PRACOWNIK");
				}
				else if(txtLogin.getText().equals("klient") && new String(passwordField.getPassword()).equals("klient"))
				{
					System.out.println("KLIENT");
					dbc.connectToDataBase();
					new ClientView(dbc);
					dispose();
				}
				else
				{
					System.out.println("B£ÊDNY LOGIN LUB HAS£O:" + txtLogin.getText() + ":" + new String(passwordField.getPassword()));
				}
			}
		});
		btnZaloguj.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnZaloguj.setBounds(165, 141, 89, 23);
		getContentPane().add(btnZaloguj);

		// this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args)
	{
		LoginWindow lw = new LoginWindow();
	}
}
