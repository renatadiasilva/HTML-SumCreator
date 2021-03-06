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
import pt.uc.dei.aor.paj.xml.XMLValidation;

public class RunHTMLSumCreator implements MessageListener {
	
	private ConnectionFactory cf;
	private Topic t;

	private boolean stop = false;

	//inicializa
	public RunHTMLSumCreator() {
		try {
			this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
			this.t = InitialContext.doLookup("jms/topic/PlayTopic");
		} catch (NamingException e) {
            System.out.println("RunHTMLSumCreator Error: "+e.getMessage());
		}
	}

	//recebe mensagens
	public void subscribe() {
		try (JMSContext jcontext = cf.createContext("joao", "pedro");) {
			jcontext.setClientID("user2");
			JMSConsumer mc = jcontext.createDurableConsumer(t, "user2");
			mc.setMessageListener(this);
			//Wait for stop
			while (!stop) {
				Thread.sleep(5000);
			}
			//Exit
			System.out.println("Exiting...");
			System.out.println("Goodbye!");
		} catch (JMSRuntimeException re) {
            System.out.println("RunHTMLSumCreator.subscribe Error: "+re.getMessage());
		} catch (InterruptedException e) {
            System.out.println("RunHTMLSumCreator.subscribe Error: "+e.getMessage());
		}
	}

	//MAIN
	public static void main(String[] args) throws NamingException, JMSException{
		RunHTMLSumCreator r = new RunHTMLSumCreator();
		r.subscribe();
	}

	@Override
	public void onMessage(Message message) {
		try {
			String msgText = ((TextMessage) message).getText();
			if ("stop".equals(msgText))
				stop = true;
			else {
				System.out.println("Message received.");
				//pass reveived String to XML
				String filepathXML = outputNameFile();
				TransformXML.convertStringToXMLFile(msgText,filepathXML);
				System.out.println("String transformed to XML.");

				//XSD verification
				String filepathXSD = "..\\src\\main\\resources\\noticia.xsd";
				if (XMLValidation.validateXMLSchema(filepathXSD,filepathXML))
					System.out.println("XSD Validation: OK.");
				else System.out.println("XSD Validation: FAILED!");
			}
		} catch (JMSException je) {
            System.out.println("RunHTMLSumCreator.onMessage Error: "+je.getMessage());
		} catch (Exception e) {
            System.out.println("RunHTMLSumCreator.onMessage Error: "+e.getMessage());
		}
	}
	
	public static String outputNameFile() {

		Calendar now = new GregorianCalendar();
		String filename = "..\\src\\main\\resources\\output";
		filename += "_"+now.get(Calendar.YEAR);
		int mes = now.get(Calendar.MONTH)+1;
		filename += "_"+mes+"";
		filename += "_"+now.get(Calendar.DAY_OF_MONTH);
		filename += "_"+now.get(Calendar.HOUR_OF_DAY);
		filename += "_"+now.get(Calendar.MINUTE);
		filename += "_"+now.get(Calendar.SECOND);
		filename += "_"+now.get(Calendar.MILLISECOND);
		filename += ".xml";
		return filename;
		
	}

}