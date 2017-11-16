package com.sjcdigital.temis.model.service.machine_learn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.sjcdigital.temis.annotations.Property;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;

@Stateless
public class ClassificaLeis {

	private static final String CONTENT_PATH = System.getProperty("jboss.home.dir").concat("/welcome-content");

	@Inject
	private Logger logger;

	@Inject
	@Property("path.machine.learn")
	private String pathMachineLearn;

	@Inject
	@Property("file.bin")
	private String binFile;
	
	public String classifica(String text) {

		InputStream inputStrean = null;

		try {

			String caminhoCompletoParaArquivoBin = CONTENT_PATH + pathMachineLearn.concat(binFile);

			inputStrean = new FileInputStream(Paths.get(caminhoCompletoParaArquivoBin).toFile());
			DoccatModel doccatModel = new DoccatModel(inputStrean);
			DocumentCategorizerME myCategorizer = new DocumentCategorizerME(doccatModel);

			double[] outcomes = myCategorizer.categorize(text);

			return myCategorizer.getBestCategory(outcomes);

		} catch (IOException e) {
			logger.severe(ExceptionUtils.getStackTrace(e));
			return "SEM_CLASSIFICACAO";

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
			logger.severe(ExceptionUtils.getStackTrace(e));
		}

	}

}
