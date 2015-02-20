package jacksonh98.pwdgen;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.CardLayout;

import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;

import javax.swing.JProgressBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class gui extends JFrame implements ActionListener
{
	JFrame g_jfMain = new JFrame();
	
	JPanel g_jpPanel = new JPanel();
	
	JButton g_jbGenerate = new JButton("Generate");
	
	JTextField g_jtLength = new JTextField(3);
	JTextField g_jtMaxSym = new JTextField(3);
	JTextField g_jtMaxAlph = new JTextField(3);
	JTextField g_jtMaxNum = new JTextField(3);
	JTextField g_jtPassword = new JTextField(40);
	
	JCheckBox g_jrSym = new JCheckBox("Use symbols");
	JCheckBox g_jrNum = new JCheckBox("Use numbers");
	JCheckBox g_jrAlph = new JCheckBox("Use letters");
	
	JProgressBar g_jpEstStrength = new JProgressBar();
	
	JMenuBar g_mbMain = new JMenuBar();
	JMenuItem g_miInfo = new JMenuItem("Info");
	
	JLabel g_jlError = new JLabel("");
	//Dimension g_diSeparator = g_jsSeparator.getPreferredSize();
	
	int 
		g_iMaxSize = 8, 
		g_iRandom,
		g_iBoxesSelected = 0,
		g_iMaxAlph = 0,
		g_iMaxNum = 0,
		g_iMaxSym = 0;

	char[] 
		g_cFinal = new char[40],
		//Following arrays contain alphabetical characters (g_cAlpha), numbers (g_cNum), symbols (g_cSym) through ASCII codes
		g_cAlpha = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122}, //a-z, A-Z
		g_cNum = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57}, //0-9
		g_cSym = {33, 35, 36, 38, 40, 41, 60, 62, 63, 64, 94}; //!#$&()<>?@^
	
	Random 
		random = new Random();
	
	String g_szFinal = "";
	
	gui()
	{
		g_jtMaxSym.setBounds(133, 73, 30, 20);
		g_jtMaxSym.setEnabled(false);
		g_jtMaxAlph.setBounds(133, 23, 30, 20);
		g_jtMaxAlph.setEnabled(false);
		g_jtMaxNum.setBounds(133, 47, 30, 20);
		g_jtMaxNum.setEnabled(false);
		g_jtPassword.setBounds(6, 193, 326, 20);
		g_jtPassword.setEnabled(false);
		g_jtPassword.setText("Click generate!");
		g_jtLength.setBounds(6, 137, 30, 20);
		g_jtLength.setText(String.valueOf(g_iMaxSize));
		g_jtLength.setEnabled(false);
		
		//g_diSeparator.height = g_jtPassword.getPreferredSize().height;
		g_jrAlph.setBounds(6, 22, 121, 23);
		
		
		g_jrAlph.setToolTipText("Use letters in generated password");
		g_jrNum.setBounds(6, 46, 121, 23);
		g_jrNum.setToolTipText("Use numbers in generated password");
		g_jrSym.setBounds(6, 72, 121, 23);
		g_jrSym.setToolTipText("Use symbols in generated password");
		g_jtMaxAlph.setToolTipText("Specify how many letters you want (must be enabled)");
		g_jtMaxNum.setToolTipText("Specify how many numbers you want (must be enabled)");
		g_jtMaxSym.setToolTipText("Specify how many symbols you want (must be enabled)");
		
		g_jfMain.setSize(471, 324);
		g_jfMain.setTitle("Password Generator");
		g_jfMain.setDefaultCloseOperation(EXIT_ON_CLOSE);
		g_jpPanel.setLayout(null);
		g_jpPanel.add(g_jrAlph);
		g_jpPanel.add(g_jtMaxAlph);
		g_jpPanel.add(g_jrNum);
		g_jpPanel.add(g_jtMaxNum);
		g_jpPanel.add(g_jrSym);
		g_jpPanel.add(g_jtMaxSym);
		JLabel g_jlLength = new JLabel("Length: (Auto detects length)");
		g_jlLength.setBounds(6, 104, 194, 14);
		g_jpPanel.add(g_jlLength);
		g_jpPanel.add(g_jtLength);
		JLabel g_jlPassword = new JLabel("Password:");
		g_jlPassword.setBounds(6, 168, 326, 14);
		g_jpPanel.add(g_jlPassword);
		g_jpPanel.add(g_jtPassword);
		g_jbGenerate.setBounds(336, 193, 103, 23);
		g_jpPanel.add(g_jbGenerate);
		g_jlError.setBounds(434, 69, 0, 0);
		g_jpPanel.add(g_jlError);
		g_jfMain.getContentPane().add(g_jpPanel);
	
		g_jpEstStrength.setBounds(299, 240, 146, 14);
		g_jpPanel.add(g_jpEstStrength);
		g_jfMain.setVisible(true);
		
		g_jpEstStrength.setMinimum(0);
		g_jpEstStrength.setMaximum(100);
		
		JLabel lblPasswordStrengthEstimation = new JLabel("Password strength estimation:");
		lblPasswordStrengthEstimation.setBounds(89, 240, 200, 14);
		g_jpPanel.add(lblPasswordStrengthEstimation);
		
		g_jbGenerate.addActionListener(this);
		g_jrAlph.addActionListener(this);
		g_jrNum.addActionListener(this);
		g_jrSym.addActionListener(this);
		g_jfMain.setLocationRelativeTo(null);
		g_jfMain.setResizable(false);
		
		g_jfMain.setJMenuBar(g_mbMain);
		
		JMenu g_mnHelp = new JMenu("Help");
		g_mbMain.add(g_mnHelp);
		
		g_mnHelp.add(g_miInfo);
		g_miInfo.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object src = e.getSource();
		if(src == g_jbGenerate) {
			if(g_iBoxesSelected <= 0) {
				//g_jlError.setText("Could not generate blank password (0 boxes checked)");
				JOptionPane.showMessageDialog(null, "Could not generate blank password (0 boxes checked)");
			}
			else {
				g_jlError.setText("");
				if(IsANumber(g_jtLength.getText()))
					g_iMaxSize = Integer.valueOf(g_jtLength.getText());
				
				generate();
				g_jtPassword.setEnabled(true);
				g_jtPassword.setText(g_szFinal);
			}
		} 
		else if(src == g_jrAlph) {
			//Letter checkbox
			if(g_jrAlph.isSelected()) {
				g_jtMaxAlph.setEnabled(true);
				g_iBoxesSelected++;		
				if(g_iMaxAlph == 0) {
					g_iMaxAlph = UpdateMax(g_iMaxAlph);
					g_jtMaxAlph.setText(String.valueOf(g_iMaxAlph));
				}
				else g_iMaxAlph = Integer.valueOf(g_jtMaxAlph.getText());
				
			}
			else {
				g_jtMaxAlph.setEnabled(false);
				g_iBoxesSelected--;
				g_iMaxAlph = 0;
			}
		}
		else if(src == g_jrNum) {
			//Number checkbox
			if(g_jrNum.isSelected()) {
				g_jtMaxNum.setEnabled(true);
				g_iBoxesSelected++;
				
				if(g_iMaxNum == 0) {
					g_iMaxNum = UpdateMax(g_iMaxNum);
					g_jtMaxNum.setText(String.valueOf(g_iMaxNum));
				}
				else g_iMaxNum = Integer.valueOf(g_jtMaxNum.getText());
			}
			else {
				g_jtMaxNum.setEnabled(false);
				g_iBoxesSelected--;
				g_iMaxNum = 0;
			}
		}
		else if(src == g_jrSym) {
			//Symbol checkbox
			if(g_jrSym.isSelected()) {
				g_jtMaxSym.setEnabled(true);
				g_iBoxesSelected++;
				
				if(g_iMaxSym == 0) { 
					g_iMaxSym = UpdateMax(g_iMaxSym);
					g_jtMaxSym.setText(String.valueOf(g_iMaxSym));
				}
				else g_iMaxSym = Integer.valueOf(g_jtMaxSym.getText());
			}
			else {
				g_jtMaxSym.setEnabled(false);
				g_iBoxesSelected--;
				g_iMaxSym = 0;
			}
		}
		else if(src == g_miInfo) {
			JOptionPane.showMessageDialog(null, "This software was developed by Jackson Harris.\nPassword strength is in no way guaranteed and common sense should be used when selecting passwords.\nLicensed under the Apache License, Version 2.0.\n");
		}
	}
	
	private String generate()
	{
		DiscoverValues();
		g_jtLength.setText(String.valueOf(g_iMaxSize));
		int
			iAlph = 0,
			iNum = 0,
			iSym = 0,
			iSlot = 0;
		
		char[] cLetters = new char[g_iMaxAlph+1], //minimum array size of 1
				cNumbers = new char[g_iMaxNum+1],
				cSymbols = new char[g_iMaxSym+1],
				cFinal = new char[g_iMaxSize];
		
		//Generates random number/letter/symbol for each slot 
		if(g_iMaxAlph > 0) {
			iSlot = 0;
			while(iAlph < g_iMaxAlph) {
				g_iRandom = random.nextInt(g_cAlpha.length);
				cLetters[iSlot] = g_cAlpha[g_iRandom];
				iAlph++;
				iSlot++;
			}
		}
		if(g_iMaxNum > 0) {
			iSlot = 0;
			while(iNum < g_iMaxNum) {
				g_iRandom = random.nextInt(g_cNum.length);
				cNumbers[iSlot] = g_cNum[g_iRandom];
				iNum++;
				iSlot++;
			}
		}
		if(g_iMaxSym > 0) {
			iSlot = 0;
			while(iSym < g_iMaxSym) {
				g_iRandom = random.nextInt(g_cSym.length);
				cSymbols[iSlot] = g_cSym[g_iRandom];
				iSym++;
				iSlot++;
			}
		}
		
		
		List<Character> algo = new ArrayList<Character>();
		iSlot = 0;
		
		if(g_iMaxAlph > 0) {
			for(int i = 0; i < g_iMaxAlph; i++) {
				algo.add(cLetters[i]);
				//System.out.printf("%c(%d) added to array (%c)\n", cLetters[i], (int) cLetters[i], algo.get(iSlot));
				iSlot++;
			}
		}
		
		if(g_iMaxNum > 0) {
			for(int i = 0; i < g_iMaxNum; i++) {
				algo.add(cNumbers[i]);
				//System.out.printf("%c(%d) added to array (%c)\n", cNumbers[i], (int) cNumbers[i], algo.get(iSlot));
				iSlot++;
			}
		}
		if(g_iMaxSym > 0) {
			for(int i = 0; i < g_iMaxSym; i++) {
				algo.add(cSymbols[i]);
				//System.out.printf("%c(%d) added to array (%c)\n", cSymbols[i], (int) cSymbols[i], algo.get(iSlot));
				iSlot++;
			}
		}
		
		Collections.shuffle(algo);
		
		for(int i = 0; i < g_iMaxSize; i++) {
			cFinal[i] = algo.get(i);
		}
		
		g_szFinal = String.valueOf(cFinal);
		//System.out.printf("Generated password: %s (%d, %d, %d, %d, %d, %d)\n", g_szFinal, iNum, g_iMaxNum, iAlph, g_iMaxAlph, iSym, g_iMaxSym);
		
		
		//Generates an estimated password strength based on recommended passwords (8+ characters, at least 2 symbols/numbers)
		int 
			iPercent = 0;
		
		if(cFinal.length > 8) iPercent = 20;
		else if(cFinal.length < 8) iPercent = 5;
		
		if(cFinal.length > 0) {
			if(cLetters.length >= 8) iPercent += 10;
			if(cNumbers.length >= 2) iPercent += 10;
			if(cSymbols.length >= 2) iPercent += 10;
			
			if(cNumbers.length - 2 >  0) iPercent += cNumbers.length;
			if(cSymbols.length - 2 > 0) iPercent += cSymbols.length;
			if(cLetters.length - 8 > 0) iPercent += (cLetters.length/2);
			
			if(iPercent > 100) iPercent = 100;
		}
		else 
			iPercent = 0;
		
		g_jpEstStrength.setValue(iPercent);
		g_jpEstStrength.setStringPainted(true);
		
		return g_szFinal;
	}
	
	private boolean IsANumber(String str)
	{
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) > 57 || str.charAt(i) < 49) return false;
		}
		return true;
	}
	
	private int UpdateMax(int iType)
	{ 
		int iMax = g_iMaxSize-((g_iMaxSym+g_iMaxNum+g_iMaxAlph)-iType);
		return iMax;
	}
	
	private void DiscoverValues()
	{
		g_iMaxSize = 0;
		if(g_jrAlph.isSelected()) {
			g_iMaxAlph = Integer.valueOf(g_jtMaxAlph.getText());
			g_iMaxSize += g_iMaxAlph;
		}
		
		if(g_jrNum.isSelected()) {
			g_iMaxNum = Integer.valueOf(g_jtMaxNum.getText());
			g_iMaxSize += g_iMaxNum;
		}
		if(g_jrSym.isSelected()) {
			g_iMaxSym = Integer.valueOf(g_jtMaxSym.getText());
			g_iMaxSize += g_iMaxSym;
		}
	}
}
