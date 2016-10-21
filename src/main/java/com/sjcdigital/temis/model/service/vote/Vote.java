/**
 * 
 */
package com.sjcdigital.temis.model.service.vote;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.controller.exceptions.ResourceNotFoundException;
import com.sjcdigital.temis.controller.exceptions.VoteException;
import com.sjcdigital.temis.model.document.Law;
import com.sjcdigital.temis.model.repositories.LawsRepository;

/**
 * @author pedro-hos
 */
@Component
public class Vote {
	
	private static final Logger LOGGER = LogManager.getLogger(Vote.class);
	
	@Autowired
	private LawsRepository lawsRepository;
	
	/**
	 * Esse mapa contém o IP de quem já votou e a lista de id leis já votados.
	 */
	private Map<String, Set<String>> votes;
	
	@PostConstruct
	private void init() {
		LOGGER.info("Init votes map ...");
		votes = new HashMap<>();
	}
	
	public Law voteYes(final String code, final String ip) {
		canVote(code, ip);
		Law law = lawsRepository.findByCode(code).orElseThrow(ResourceNotFoundException::new);
		law.votePositive();
		return lawsRepository.save(law);
	}
	
	public Law voteNo(final String code, final String ip) {
		canVote(code, ip);
		Law law = lawsRepository.findByCode(code).orElseThrow(ResourceNotFoundException::new);
		law.voteNegative();
		return lawsRepository.save(law);
	}
	
	/**
	 * Verifica se ip ja votou!
	 * @param code law
	 * @param ip address
	 */
	public void canVote(final String code, final String ip) {
		
		Set<String> votedLaws = votes.getOrDefault(ip, new HashSet<>());
		boolean voted = votedLaws.stream().anyMatch(code::equals);
		
		if (!voted) {
			votedLaws.add(code);
			votes.put(ip, votedLaws);
			
		} else {
			throw new VoteException();
		}
	}
	
	@Scheduled(cron = "0 0 0 1 * ?")
	public void cleanVotes() {
		LOGGER.info("Clean votes map ...");
		votes = new HashMap<>();
	}
	
}
