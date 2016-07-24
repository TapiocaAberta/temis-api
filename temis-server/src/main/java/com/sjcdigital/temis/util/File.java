package com.sjcdigital.temis.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 
 * @author pedro-hos
 *         
 *         Classe para trabalhar com files.
 *		
 */

@Component
public class File {
	
	private final Logger LOGGER = LogManager.getLogger(this.getClass());
	
	@Async
	public void createFile(final String path, final String body, final String fileName, final Integer year) {
		
		try {
			
			LOGGER.info("Salvando file: " + fileName);
			
			Path pathNio = Paths.get(path.concat(year.toString()));
			Files.createDirectories(pathNio);
			Path filePath = Paths.get(pathNio.toString().concat("/").concat(fileName).concat(".html"));
			Files.write(filePath, body.getBytes());
			
		} catch (IOException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	
}
