package com.sjcdigital.temis.model.service.bots.lei;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.sjcdigital.temis.model.entities.impl.Lei;

/**
 * @author pesilva
 *
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/classifica") })
public class LeiClassificacaoQueueReceiver implements MessageListener {

	@Inject
	private LeiClassificacao leiClassificacao;

	@Override
	public void onMessage(Message message) {

		ObjectMessage ob = ((ObjectMessage) message);
		
		try {
			
			Lei body = ob.getBody(Lei.class);
			leiClassificacao.classifica(body.getEmenta());
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
