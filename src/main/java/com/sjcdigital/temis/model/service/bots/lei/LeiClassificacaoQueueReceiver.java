package com.sjcdigital.temis.model.service.bots.lei;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.repositories.impl.Leis;

/**
 * @author pesilva
 *
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/classifica") })
public class LeiClassificacaoQueueReceiver implements MessageListener {

	@Inject
	private Logger logger;
	
	@Inject
	private LeiClassificacao leiClassificacao;
	
	@Inject
	private Leis leis;

	@Override
	public void onMessage(Message message) {

		ObjectMessage ob = ((ObjectMessage) message);
		
		try {
			
			Lei lei = ob.getBody(Lei.class);
			leiClassificacao.classifica(lei.getEmenta());
			
			logger.info("Salvando Lei: " + lei.getNumeroProcesso() + " do autor: " + lei.getAutor().getNome());
			leis.salvar(lei);
			
		} catch (JMSException e) {
			logger.severe(ExceptionUtils.getStackTrace(e));
		}
	}

}
