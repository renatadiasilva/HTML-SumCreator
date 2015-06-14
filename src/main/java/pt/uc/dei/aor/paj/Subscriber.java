package pt.uc.dei.aor.paj;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import pt.uc.dei.aor.paj.xml.TransformXML;

public class Subscriber implements MessageListener {
	private ConnectionFactory cf;
	private Topic t;

	private boolean stop = false;

	public Subscriber() {
		try {
			this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
			this.t = InitialContext.doLookup("jms/topic/PlayTopic");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void subscribe() {
		try (JMSContext jcontext = cf.createContext("joao", "pedro");) {
			jcontext.setClientID("user2");
			JMSConsumer mc = jcontext.createDurableConsumer(t, "user2");
			mc.setMessageListener(this);
			//Wait for stop
			while (!stop) {
				Thread.sleep(1000);
			}
			//Exit
			System.out.println("Exiting...");
			System.out.println("Goodbye!");
		} catch (JMSRuntimeException re) {
			re.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message message) {
		try {
			String msgText = ((TextMessage) message).getText();
//			System.out.println("Message: " + msgText);
			//pass String to XML
			if ("stop".equals(msgText))
				stop = true;
			else {
				String filename = outputNameFile();
				TransformXML.convertStringToXMLFile(msgText,filename);
			}
		} catch (JMSException e) {
			e.printStackTrace();
			stop = true;
		} catch (Exception e) {
			e.printStackTrace();
			stop = true;
		}
	}
	
	public static String outputNameFile() {

		Calendar now = new GregorianCalendar();
		String filename = "..\\src\\main\\resources\\output";
		filename += now.get(Calendar.YEAR);
		filename += now.get(Calendar.MONTH);
		filename += now.get(Calendar.DAY_OF_MONTH);
		filename += now.get(Calendar.HOUR_OF_DAY);
		filename += now.get(Calendar.MINUTE);
		filename += now.get(Calendar.SECOND);
		filename += ".xml";
		return filename;
		
	}

}