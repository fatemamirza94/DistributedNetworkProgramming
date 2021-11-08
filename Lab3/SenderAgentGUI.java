import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jade.gui.GuiEvent;

import java.util.ArrayList;


public class SenderAgentGUI extends JFrame {


	private SenderAgent senderAgent;


	JComboBox<String> messagePerformative;

	JComboBox<Object> receivers;

	JTextField content;

	JTextArea sentMessage, receivedMessage;

	JButton sendBtn, cancelBtn, RefreshBtn;


	JLabel performativeLabel, receiverLabel, contentLabel;


	JScrollPane scrollPane1, scrollPane2;


	JPanel controlPanel;


	ArrayList<String> receiversList;


	String[] performat = {"INFORM", "REQUEST"};



	public SenderAgentGUI(SenderAgent c) {

		super(c.getLocalName());

		senderAgent = c;

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				senderAgent.doDelete();
			}
		});

		receiversList = new ArrayList<String>();


		// Remove this agents name from the drop-down list of recipients
		// No point of sending message to thyself

		for (String agentName : senderAgent.agentList) {
			if (senderAgent.getLocalName().equals(agentName) || receiversList.contains(agentName))
				continue;
			receiversList.add(agentName);
		}


		updatePerformativeCombo();

		updateReceiverCombo();


		// all the GUI is instantiated here, so that it can be passed
		// as a parameter to the Agent class


		RefreshBtn = new JButton("Refresh");
        RefreshBtn.setPreferredSize(new Dimension(230, 35));


		content = new JTextField();
		content.setPreferredSize(new Dimension(500, 35));



		sentMessage = new JTextArea(10,40);
		sentMessage.setWrapStyleWord(true);
		sentMessage.setLineWrap(true);
		sentMessage.setEditable(false);
		scrollPane1 = new JScrollPane();
		scrollPane1.setBorder(BorderFactory.createTitledBorder("Sent Messages"));
		scrollPane1.setViewportView(sentMessage);




		receivedMessage = new JTextArea(10,40);
		receivedMessage.setWrapStyleWord(true);
		receivedMessage.setLineWrap(true);
		receivedMessage.setEditable(false);
		scrollPane2 = new JScrollPane();
		scrollPane2.setBorder(BorderFactory.createTitledBorder("Received Messages"));
		scrollPane2.setViewportView(receivedMessage);



		performativeLabel = new JLabel("Message Performative: ");
		performativeLabel.setPreferredSize(new Dimension(270, 25));


		receiverLabel = new JLabel("Receiver: ");
		receiverLabel.setPreferredSize(new Dimension(500, 25));


		contentLabel = new JLabel("Content: ");
		contentLabel.setPreferredSize(new Dimension(500, 25));


		sendBtn = new JButton("Send");
		sendBtn.setPreferredSize(new Dimension(230, 30));


		cancelBtn = new JButton("Cancel");
		cancelBtn.setPreferredSize(new Dimension(230, 30));


		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);



        RefreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                //call function from listing all sub coord
            	senderAgent.updateCombo();
            }
        });



		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent ae) {
				try {

					String perf = (String) messagePerformative.getSelectedItem();

					String rec = (String) receivers.getSelectedItem();
				
					String cont = content.getText();

					
					senderAgent.getFromGui(perf, rec, cont);

					GuiEvent guiEvent = new GuiEvent(this, 1);
					senderAgent.postGuiEvent(guiEvent); // this postGuiEvent triggers onGuiEvent method in
																// MessageAgent which in turn calls the sendMessage

					

				} catch (Exception e) {
					JOptionPane.showMessageDialog(SenderAgentGUI.this, "Invalid values. " + e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});



		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent ae) {
				
				setVisible(false);	// hide the Frame
			    dispose();	// free the system resources
			    senderAgent.doDelete();
			}
		});

		

		controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));




		controlPanel.add(performativeLabel);
		controlPanel.add(messagePerformative);

		controlPanel.add(receiverLabel);
		controlPanel.add(receivers);
		controlPanel.add(RefreshBtn);

		controlPanel.add(contentLabel);
		controlPanel.add(content);

		controlPanel.add(sendBtn);

		controlPanel.add(cancelBtn);

		controlPanel.add(scrollPane1);

		controlPanel.add(scrollPane2);


		Container contentPane = getContentPane();
		contentPane.setPreferredSize(new Dimension(550, 690));
		getContentPane().add(controlPanel);



		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				senderAgent.doDelete();
			}
		});
	}



	public void setMessageTextArea1(String text) {
		sentMessage.setText(text);
	}


	public void setMessageTextArea2(String text) {
		receivedMessage.setText(text);
	}



	public void displayGUI() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) screenSize.getWidth() / 2;
		int centerY = (int) screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		setTitle("Agent Smith");
		setResizable(false);
		super.setVisible(true);
	}


	public void updatePerformativeCombo() {

		messagePerformative = new JComboBox<>(performat);
		messagePerformative.setSelectedIndex(-1);
		messagePerformative.setPreferredSize(new Dimension(270, 30));
	}



	public void updateReceiverCombo() {

		receivers = new JComboBox<>(receiversList.toArray());
		receivers.setSelectedIndex(-1);
		receivers.setPreferredSize(new Dimension(230, 35));
	}


	public void addItemDLL(String name) {
		String tmp[]=name.split("@");
		Object a = tmp[0];
		receivers.addItem(a);
        receivers.setSelectedIndex(-1);
	}


	public void clearDDL() {
		receivers.removeAllItems();
	}

}
