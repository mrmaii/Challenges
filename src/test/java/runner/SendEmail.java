package runner;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import flow.ActionException;
import flow.EmptyFlowException;
import flow.Engine;
import flow.IAction;
import flow.IAdapter;
import flow.IEvent;
import flow.samples.*;

public class SendEmail { // Test class generated from cucumber

	// variables to be used in the scenario
	TextAgent agent; //new agent
	TextApp app; // new mail server
	TextAdapter adapt = new TextAdapter(); // new mail client
	Engine engine; //new engine to run all other classes
	Map<String, IAdapter> adapters = new HashMap<>(); // map of type of message and mail clients
	ArrayList<String> response = new ArrayList(); //Support list to store response and be used to validate if the messages are in the server

	@Given("^A User sends an \"([^\"]*)\" message$")
	public void a_User_sends_an_message(String message) throws Throwable {

		agent = new TextAgent(message); //initializing the agent receiving the message
		app = new TextApp(); // initializing the mail mail server

	}

	@When("^The message is converted by the Adapter$")
	public void the_message_is_converted_by_the_Adapter() throws Throwable {

		for (IAction action : agent.act()) { //running all the "sending email" action
			response.add(app.in(adapt.adapt(action))); // adding the action to the mail server
			adapters.put(action.getType(), adapt); // collecting the type of message and storing in the map variable "adapters"
			engine = new Engine(agent, adapters, app); // innitializing the engine and sending all necessary objects
			engine.run(); // running the "engine"
			
		}
	}

	@Then("^The Message server should contain the \"([^\"]*)\" message in the queue$")
	public void the_Message_server_should_contain_the_message_in_the_queue(String message) throws Throwable {

		for (String string : response) { //running all messages stored in the list of "responses"
			assertEquals(string, "ACK:"+message); // comparing if the message is presented in the mail server. "ACK:" was added in a private method inside mail server class.
		}
				
	}

}
