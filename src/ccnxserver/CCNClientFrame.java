/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccnxserver;

import gson.util.GsonMessageBuilder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.ccnx.ccn.apps.ccnchat.CCNChatNet;

import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;
import org.pl.lecznica.messages.Message;
import org.pl.lecznica.messages.MessageFactory;

/**
 *
 * @author ws
 */

/**
 * Based on a client/server chat example in Robert Sedgewick's Algorithms
 * in Java.
 * 
 * Refactored to be just the JFrame UI.
 */

public class CCNClientFrame extends JFrame  {

private static final long serialVersionUID = -8779269133035264361L;

    // Chat window
    protected ChatPanel chatPanel;
    private CCNClientChat chat;

    
    public CCNClientFrame(CCNClientChat _chat, String namespace, String name) throws MalformedContentNameStringException {

        chatPanel = new ChatPanel(_chat, namespace, name);
        chat = _chat;
        
    	// close output stream  - this will cause listen() to stop and exit
        addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    try {
						chat.stop();
					} catch (IOException e1) {
						System.out.println("IOException shutting down listener: " + e1);
						e1.printStackTrace();
					}
                }
            }
        );
        
        
        // Make window


        Container content = getContentPane();
        content.add(new JScrollPane(chatPanel), BorderLayout.CENTER);
        
        // display the window, with focus on typing box
//        setTitle("CCNClientChat: [" + namespace + "]:"+restrictedName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        setVisible(true);
    }
	
	

}


    

