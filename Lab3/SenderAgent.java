import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;

import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;

import jade.lang.acl.*;

import java.util.ArrayList;

import jade.gui.GuiAgent;

import jade.gui.GuiEvent;


public class SenderAgent extends GuiAgent {


	private SenderAgentGUI senderAgentGUI;

	public ArrayList<String> agentList;

	private String perf = "";
	private String rec = "";
	private String cont = "";


	private int req;

	
    protected void setup() {


    	// Printout a welcome message
    	System.out.println("Sender agent " + getAID().getName() + " is ready.");


    	// query the AMS to get list of all active agents in all containers
    	agentList = new ArrayList<String>();
		refreshReceiverAgents();


		senderAgentGUI = new SenderAgentGUI(this);
		senderAgentGUI.displayGUI();


		addBehaviour(new ReceiveMessage());

	}




	// Agent clean-up
	protected void takeDown() {

		// Dispose the GUI if it is there
		if (senderAgentGUI != null) {
			senderAgentGUI.dispose();
		}

		// Printout a dismissal message
		System.out.println("Sender Agent " + getAID().getName() + " is terminating.");


	}



	// Sending message is an implementation of OneShotBehavior(Send once for one time)
	public class SendMessage extends OneShotBehaviour {


		// Send message from to someone
		public void action() {

			
			if (perf.equals("INFORM"))
				req = ACLMessage.INFORM;

			else if (perf.equals("REQUEST"))
				req = ACLMessage.REQUEST;

			
			ACLMessage messageToSend = new ACLMessage(req);
			messageToSend.addReceiver(new AID(rec, AID.ISLOCALNAME));

			messageToSend.setLanguage("English");

			messageToSend.setContent(cont);

			send(messageToSend);


			String statusMessagesText1 = "";


			statusMessagesText1 = messageToSend.getContent();

			senderAgentGUI.setMessageTextArea1(statusMessagesText1);

			System.out.println(getAID().getName() + " sent a message to " + rec + "\n"
					+ "Content of the message is: " + messageToSend.getContent());

		}
	}




	public class ReceiveMessage extends CyclicBehaviour {

		// Variable for the contents of the received Message
		private String messagePerformative = "";
		private String messageContent = "";
		private String senderName = "";


		// Receive message and append it in the conversation textArea in the GUI
		//@Override
		public void action() {

			ACLMessage messageReceived = receive();

			if (messageReceived != null) {

				messagePerformative = messageReceived.getPerformative(messageReceived.getPerformative());

				messageContent = messageReceived.getContent();

				senderName = messageReceived.getSender().getLocalName();

				// print the message details in console
				System.out.println(
						"**** " + getAID().getLocalName() + " received a message" + "\n" + "Sender name: " + senderName
								+ "\n" + "Content of the message: " + messageContent + "\n");
				System.out.println("**********************************");


				String statusMessagesText2 = "";

				statusMessagesText2 = messageContent;
				senderAgentGUI.setMessageTextArea2(statusMessagesText2);
			}

			block();
		}

	}




	// get all entered input from gui agent
	public void getFromGui(final String perf1, final String rec1, final String cont1) {
		
		addBehaviour(new OneShotBehaviour() {

			public void action() {

				perf = perf1;
				rec = rec1;
				cont = cont1;

			}
		});
	}




	//@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		addBehaviour(new SendMessage());
	}


	// if new receiver agents are created after instantiating this object
	// this method will keep the lists updated
	public void refreshReceiverAgents() {

		AMSAgentDescription[] agents = null;

		try {
			SearchConstraints c = new SearchConstraints();
			c.setMaxResults(new Long(-1));
			agents = AMSService.search(this, new AMSAgentDescription(), c);
		} 
		catch (Exception e) {
            System.out.println( "Problem searching AMS: " + e );
            e.printStackTrace();
		}


		// Add all the active receiver agents in the agent list to show in drop-down
		for (int i = 0; i < agents.length; i++) {
			AID agentID = agents[i].getName();
			if (agentID.getLocalName().equals("ams") || agentID.getLocalName().equals("rma")
					|| agentID.getLocalName().equals("df"))
				continue;
			agentList.add(agentID.getLocalName());
		}
		

	}



	public void updateCombo() {
		addBehaviour(new OneShotBehaviour() {	 
			
			public void action() {

				AMSAgentDescription[] agents = null;

				SearchConstraints ALL = new SearchConstraints();
				ALL.setMaxResults(new Long(-1));  // In searchConstraints, specify the max number of replies (-1 means ALL).


				try {

					String agentName = getAID().getLocalName();

					agents = AMSService.search(myAgent, new AMSAgentDescription(), ALL);

					System.out.println("Found the following agents:");

					senderAgentGUI.clearDDL();

					for (int i = 0; i < agents.length; i++) {

						AID agentID = agents[i].getName();

						if (agentID.getLocalName().equals("ams") || agentID.getLocalName().equals("rma") || agentID.getLocalName().equals("df") || agentID.getLocalName().equals(agentName) )
							continue;

						System.out.println(agentID.getName());
						senderAgentGUI.addItemDLL(agentID.getName()); //adding the subCoordinators agents to the ComboBox
						
					}	
				
				}
				catch (FIPAException fe) {
					fe.printStackTrace();
				}
			}
		});
	}


}
