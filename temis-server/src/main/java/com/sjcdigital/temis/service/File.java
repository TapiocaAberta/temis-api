package com.sjcdigital.temis.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 
 * @author pedro-hos
 *         
 *         Classe para trabalhar com files.
 *		
 */

@Service
public class File {
	
	private final Logger LOGGER = LogManager.getLogger(this.getClass());
	
	@Value("${path.leis}")
	private String path;
	
	@Async
	public void createFile(final String body, final String fileName, final Integer year) {
		
		try {
			
			LOGGER.info("Salvando Lei: " + fileName);
			
			Path pathNio = Paths.get(path.concat(year.toString()));
			Files.createDirectories(pathNio);
			Path filePath = Paths.get(pathNio.toString().concat("/").concat(fileName).concat(".html"));
			Files.write(filePath, body.getBytes());
			
		} catch (IOException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
	}
	
}
