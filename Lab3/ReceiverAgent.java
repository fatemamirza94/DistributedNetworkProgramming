import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;


public class ReceiverAgent extends Agent  {
	
    protected void setup() {

    	System.out.println("Hello! My name is " + getAID().getLocalName());

        ReceiveMessage rm = new ReceiveMessage();
        addBehaviour(rm);
    }


    public class ReceiveMessage extends CyclicBehaviour {

    	private String Message_Performative;
        private String Message_Content;
        private String SenderName;

    	public void action() {

			ACLMessage msg = receive();

			if (msg!=null) {

				Message_Performative = msg.getPerformative(msg.getPerformative()); // return the string representing the performative of this object
                Message_Content = msg.getContent(); // Reads :content slot.
                SenderName = msg.getSender().getLocalName(); // Reads :sender slot. and retrieve local name

				System.out.println( " - " + myAgent.getLocalName() + " received: " + msg.getContent() );
				   
				ACLMessage reply = msg.createReply();
				reply.setPerformative( ACLMessage.INFORM );
				reply.setContent("Reply from the receiver (" + myAgent.getLocalName() + ") --> \nSuccessfully received a message from the sender where," + "\n" + "the Content of the Message is --> " + msg.getContent() + "\n" + "and message Performative is --> " + msg.getPerformative(msg.getPerformative()) + "\n" );

				send(reply);
			}

			block();
		}
	}

}


