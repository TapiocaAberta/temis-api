package com.sjcdigital.temis.model.service.machine_learn;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.sjcdigital.temis.annotations.Property;
import com.sjcdigital.temis.model.service.machine_learn.exception.TreinoException;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

/**
 * @author pesilva
 *
 */

@Stateless
public class TreinaClassificacao {

	private static final String CONTENT_PATH = System.getProperty("jboss.home.dir").concat("/welcome-content");

	@Inject
	private Logger logger;

	@Inject
	@Property("path.machine.learn")
	private String pathMachineLearn;

	@Inject
	@Property("file.train")
	private String trainFile;
	
	@Inject
	@Property("file.bin")
	private String binFile;

	@SuppressWarnings("deprecation")
	public void run() throws TreinoException {
		
		logger.info("## Treinando Maquina");

		DoccatModel model = null;
		OutputStream modelOut = null;

		try {

			// Ensinando a máquina
			String fullPathMachineLearn = CONTENT_PATH + pathMachineLearn;
			
			InputStreamFactory dataIn = new MarkableFileInputStreamFactory(Paths.get(fullPathMachineLearn + trainFile).toFile());
			ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
			model = DocumentCategorizerME.train("pt", sampleStream);

			// Escrevendo arquivo que ela aprendeu
			modelOut = new BufferedOutputStream(new FileOutputStream(Paths.get(fullPathMachineLearn + binFile).toFile()));
			model.serialize(modelOut);

		} catch (IOException e) {
			logger.severe(ExceptionUtils.getStackTrace(e));
		} finally {
			
			if (Objects.nonNull(modelOut)) {
				closeOutputStream(modelOut);
			}
		}

	}

	private void closeOutputStream(OutputStream modelOut) {
		try {
			modelOut.close();
		} catch (IOException e) {
			logger.severe(ExceptionUtils.getStackTrace(e));
		}
	}

}
