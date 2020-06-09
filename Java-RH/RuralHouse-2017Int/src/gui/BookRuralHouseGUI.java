package gui;

import businessLogic.ApplicationFacadeInterfaceWS;

import com.toedter.calendar.*;

import domain.Offer;
import domain.RuralHouse;


import java.beans.*;
 
import java.sql.Date;
import java.text.*;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;



public class BookRuralHouseGUI extends JFrame {
private static final long serialVersionUID = 1L;

  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel6 = new JLabel();
  private JLabel jLabelResult = new JLabel();


  private JTextField jTextField2 = new JTextField();
  private JTextField jTextField3 = new JTextField();
  private JTextField jTextField4 = new JTextField();
  private JTextField jTextField5 = new JTextField();
  private JTextField jTextField6 = new JTextField();

  

  private JButton jButton2 = new JButton();
  private JButton jButton3 = new JButton();
  
  private Offer offer;
  
  // Code for JCalendar

  private JLabel jLabel5 = new JLabel();
  
  

public BookRuralHouseGUI()
 {
    try
    {
      jbInit();
      
     
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }public BookRuralHouseGUI(int houseNumber, Offer offer, java.util.Date firstDate,java.util.Date lastDate)
  {
    try
    {
    	this.offer=offer;
    	System.out.println("Entra en BookRuralHouseGUI");
      jbInit();
      
 jTextField2.setText(Integer.toString(houseNumber));
 jTextField3.setText(Integer.toString(offer.getOfferNumber()));
 jTextField4.setText(firstDate.toString());
 jTextField5.setText(lastDate.toString());




    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(null);
    this.setSize(new Dimension(410, 413));
    this.setTitle("Book Rural House");
    jLabel1.setText("Rural house:");
    ApplicationFacadeInterfaceWS facade=MainGUI.getBusinessLogic();


    jLabel1.setBounds(new Rectangle(15, 10, 115, 20));

    
    jTextField6.addFocusListener(new FocusListener()
      {
          public void focusGained(FocusEvent e)
          {
          }
  
          public void focusLost(FocusEvent e)
          {
            jTextField6_focusLost();
          }
      });
    
    jLabel2.setText("Offer number:");
    jLabel2.setBounds(new Rectangle(15, 40, 115, 20));
    jLabel3.setText("Arrival day:");
    jLabel3.setBounds(new Rectangle(15, 60, 115, 20));
    jLabel4.setText("Departure day");
    jLabel4.setBounds(new Rectangle(15, 80, 140, 20));
    jLabel6.setText("Introduce a telephone number");

    jLabel6.setBounds(new Rectangle(15, 120, 140, 20));
    
    
    jLabelResult.setText("");

    jLabelResult.setBounds(new Rectangle(15, 200, 200, 20));

    jTextField2.setBounds(new Rectangle(155, 15, 140, 20));
    jTextField2.setEditable(false);
    jTextField3.setBounds(new Rectangle(155, 40, 140, 20));
    jTextField3.setText("0");
    jTextField4.setBounds(new Rectangle(155, 60, 140, 20));
    jTextField4.setText("0");
    jTextField5.setBounds(new Rectangle(155, 80, 140, 20));
    jTextField5.setText("0");
    jTextField6.setBounds(new Rectangle(155, 120, 140, 20));
    jTextField6.setText("0");
    
    jButton2.setText("Accept");
    jButton2.setBounds(new Rectangle(50, 345, 130, 30));
    jButton2.setSize(new Dimension(130, 30));
    jButton2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
        	// House code
        	// Arrival date

        	// Last day
        	// Number of days expressed in milliseconds
        	// Telephone contact
        	String telephone=jTextField6.getText();
        	
				
        		//Obtain the business logic from a StartWindow class (local or remote)
        		ApplicationFacadeInterfaceWS facade=MainGUI.getBusinessLogic();
        		        		
        		boolean booked=facade.createBook(offer, telephone);
        		System.out.println("booked: "+booked);
        		if (booked) jLabelResult.setText("OK");
        		else jLabelResult.setText("ERROR");
        	}
			
     
      });
    jButton3.setText("Cancel");
    jButton3.setBounds(new Rectangle(220, 345, 130, 30));
    jButton3.setSize(new Dimension(130, 30));
    jButton3.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton3_actionPerformed(e);
        }
      });
    jLabel5.setBounds(new Rectangle(50, 310, 300, 20));
    jLabel5.setForeground(Color.red);
    this.getContentPane().add(jLabel5, null);
    this.getContentPane().add(jButton3, null);
    this.getContentPane().add(jButton2, null);
    this.getContentPane().add(jTextField4, null);
    this.getContentPane().add(jTextField5, null);
    this.getContentPane().add(jTextField6, null);
    


    this.getContentPane().add(jTextField3, null);
    this.getContentPane().add(jTextField2, null);
    this.getContentPane().add(jLabel6, null);

    this.getContentPane().add(jLabelResult, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel1, null);
    
    
  }
 

  private void jButton3_actionPerformed(ActionEvent e)
  {
    this.setVisible(false);
  }

 
 private void jTextField6_focusLost()
 {
   try
  {
    new Integer (jTextField6.getText());
    jLabel5.setText("");
  }
  catch (NumberFormatException ex)
  {
    jLabel5.setText("Error: Introduce a number");
  }
 }
 
 
 
}
