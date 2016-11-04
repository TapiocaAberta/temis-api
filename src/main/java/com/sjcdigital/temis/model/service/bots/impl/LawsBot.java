package com.sjcdigital.temis.model.service.bots.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.document.Law;
import com.sjcdigital.temis.model.exceptions.BotException;
import com.sjcdigital.temis.model.repositories.LawsRepository;
import com.sjcdigital.temis.model.service.bots.AbstractBot;
import com.sjcdigital.temis.util.File;

/**
 *
 * @author pedro-hos
 *
 *         Classe responsável por pegar as páginas das leis em: http://www.ceaam.net/sjc/legislacao/index.php,
 *         e salvá-las em uma pasta para realização de parse posteriormente.
 *
 */
@Component
@Order(2)
public class LawsBot extends AbstractBot {

	private static final Logger LOGGER = LogManager.getLogger(LawsBot.class);

	@Value("${url.laws}")
	private String lawsUrl;

	@Value("${path.leis}")
	private String path;

	@Value("${year.start.law.extract}")
	private int initialYear;
	
	@Value("${code.start.law.extract}")
	private String initialCode;

	@Autowired
	private File file;

	@Autowired
	private LawsRepository lawsRepository;

	@Override
	public void saveData() throws BotException {

		final List<Integer> allYears = getAllYears();

		String code = getInitialCode();
		String url  = StringUtils.EMPTY;
		String body = StringUtils.EMPTY;

		boolean tryNextYear = false;

		final int currentYear = LocalDate.now().getYear();
		int index = 0;
		int limitToTry = 10;
		Integer year = null;

		while (index != allYears.size()) {

			try {

				year = allYears.get(index);
				url = buildURL(year, code);
				body = getPage(url).get().html();

				file.createHTMLFile(getPath(), body, code, year);
				code = buildLawCode(getNextLawCode(code));
				tryNextYear = false;
				limitToTry = 10;

			} catch (InterruptedException | ExecutionException | IOException exception) {

				if (exception instanceof FileNotFoundException) {

					LOGGER.error("Error 404: " + url);

					if (year != currentYear && !tryNextYear) {
						index++;
						tryNextYear = true;

					} else {

						if (limitToTry == 0) {
							break;
						}

						if (year != currentYear) {
							index--;
						}

						limitToTry--;
						code = buildLawCode(getNextLawCode(code));
					}

				} else {
					LOGGER.error(ExceptionUtils.getStackTrace(exception));
					throw new BotException(exception);
				}

			}

		}

	}

	private String getInitialCode() {
		final Optional<Law> lastLaw = lawsRepository.findFirstByOrderByCodeDesc();
		return lastLaw.isPresent() ?  buildLawCode(getNextLawCode(lastLaw.get().getCode())) : initialCode; //L8865 is the last code of 2012
	}

	private List<Integer> getAllYears() {

		final List<Integer> years = new LinkedList<>();
		final int currentYear = LocalDate.now().getYear();

		if (lawsRepository.count() != 0) {
			years.add(currentYear);

		} else {

			while (initialYear <= currentYear) {
				years.add(initialYear);
				initialYear += 1;
			}

		}

		return years;

	}

	private String buildURL(final Integer year, final String code) {
		return lawsUrl.concat(year.toString()).concat("/").concat(code).concat(".htm");
	}

	private BigInteger getNextLawCode(final String current) {
		final BigInteger nextLawCode = new BigInteger(current.replace("L", "")).add(BigInteger.ONE);
		return nextLawCode;
	}

	private String buildLawCode(final BigInteger code) {
		return "L" + StringUtils.leftPad(code.toString(), 4, "0");
	}

	@Override
	protected String getPath() {
		return path.concat("leis/");
	}

}
