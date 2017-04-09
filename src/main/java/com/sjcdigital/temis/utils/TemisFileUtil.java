package com.sjcdigital.temis.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Logger;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class TemisFileUtil {

	@Inject
	private Logger logger;
	
	private static final String CONTENT_PATH = "/Volumes/MAC/temis-files"; //System.getProperty("jboss.home.dir").concat("/welcome-content");
	
	@Asynchronous
	public void saveFile(String urlPhoto, String filePahtAndName) {

		try {
			
			logger.info("Salvando Arquivo: " + filePahtAndName);
			
			Path path = Paths.get(CONTENT_PATH + "/" + filePahtAndName);

			if (!Files.exists(path.getParent())) {
				Files.createDirectories(path.getParent());
			}
			
			URL url = new URL(urlPhoto);
			Files.write(path, IOUtils.toByteArray(url), StandardOpenOption.CREATE);
			
		} catch (IOException e) {
			logger.severe(ExceptionUtils.getStackTrace(e));
		}

	}

}
