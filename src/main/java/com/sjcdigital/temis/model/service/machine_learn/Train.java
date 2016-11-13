package com.sjcdigital.temis.model.service.machine_learn;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

/**
 * @author pedro-hos
 */
@Component
public class Train {
	
	private static final Logger LOGGER = LogManager.getLogger(Train.class);
	
	@Value("${path.machine-learn.train}")
	private String train;
	
	@Value("${path.machine-learn.bin}")
	private String bin;
	
	@PostConstruct
	@SuppressWarnings("deprecation")
	public void run() {
		
		DoccatModel model = null;
		OutputStream modelOut = null;
		
		try {
			
			// Ensinando a máquina
			InputStreamFactory dataIn = new MarkableFileInputStreamFactory(Paths.get(train).toFile());
			ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
			model = DocumentCategorizerME.train("pt", sampleStream);
			
			// Escrevendo arquivo que ela aprendeu
			modelOut = new BufferedOutputStream(new FileOutputStream(Paths.get(bin).toFile()));
			model.serialize(modelOut);
			
		} catch (IOException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		} finally {
			if (Objects.nonNull(modelOut)) {
				closeOutputStream(modelOut);
			}
		}
		
	}
	
	private static void closeOutputStream(OutputStream modelOut) {
		try {
			modelOut.close();
		} catch (IOException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
	}
	
}
