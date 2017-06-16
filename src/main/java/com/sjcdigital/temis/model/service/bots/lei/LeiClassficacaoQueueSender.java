package com.sjcdigital.temis.model.service.bots.lei;

import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;

import com.sjcdigital.temis.model.entities.impl.Lei;

public class LeiClassficacaoQueueSender {

	@Resource(mappedName = "jms/queue/classifica")
	private Destination destination;

	@Inject
	@JMSConnectionFactory("java:/ConnectionFactory")
	private JMSContext context;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void send(Lei lei) {		
		ObjectMessage message = context.createObjectMessage(lei);
		context.createProducer().send(destination, message);
	}

}
