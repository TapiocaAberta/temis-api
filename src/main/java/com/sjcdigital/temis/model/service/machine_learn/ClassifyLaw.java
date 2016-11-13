package com.sjcdigital.temis.model.service.machine_learn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sjcdigital.temis.model.document.Type;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;

/**
 * 
 * @author pedro-hos
 *         
 */
@Service
public class ClassifyLaw {
	
	private static final Logger LOGGER = LogManager.getLogger(ClassifyLaw.class);
	
	@Value("${path.machine-learn.train}")
	private String train;
	
	@Value("${path.machine-learn.bin}")
	private String bin;
	
	public String classify(String summary) {
		
		InputStream inputStrean = null;
		
		try {
			
			inputStrean = new FileInputStream(Paths.get(bin).toFile());
			DoccatModel doccatModel = new DoccatModel(inputStrean);
			DocumentCategorizerME myCategorizer = new DocumentCategorizerME(doccatModel);
			double[] outcomes = myCategorizer.categorize(summary);
			String category = myCategorizer.getBestCategory(outcomes);
			
			LOGGER.info(category);
			
			return Type.valueOf(category).getType();
			
		} catch (IOException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			return Type.SEM_CLASSIFICACAO.getType();
			
		} finally {
			if (Objects.nonNull(inputStrean)) {
				close(inputStrean);
			}
		}
		
	}
	
	private void close(InputStream is) {
		try {
			is.close();
		} catch (IOException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		
	}
	
}
