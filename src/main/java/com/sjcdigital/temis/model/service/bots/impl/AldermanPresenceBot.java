package com.sjcdigital.temis.model.service.bots.impl;

import com.sjcdigital.temis.model.exceptions.BotException;
import com.sjcdigital.temis.model.service.bots.AbstractBot;
import com.sjcdigital.temis.util.File;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author fabiohbarbosa
 */
@Component
@Order(3)
public class AldermanPresenceBot extends AbstractBot {

    private static final Logger LOGGER = LogManager.getLogger(AldermanPresenceBot.class);

    @Value("${url.alderman-presence}")
    private String aldermanPresenceUrl;

    @Value("${path.leis}")
    private String path;

    @Value("${year.start.extract}")
    private int year;

    @Autowired
    private File file;

    @Override
    public void saveData() throws BotException {
        getXLSFiles()
                .forEach((date, url) -> file.createXLSFile(getPath(), url, date, LocalDate.now().getYear()));
    }

    private Map<String, String> getXLSFiles() throws BotException {

        final Map<String, String> urlFiles = new HashMap<>();

        try {

            final Document document = Optional.ofNullable(getPage(aldermanPresenceUrl).get()).orElseThrow(BotException::new);
            final Elements divsPresenca = document.getElementsByClass("presenca");

            divsPresenca.stream()
                    .collect(Collectors.toMap(e -> e.select("time").attr("datetime"), e -> e.select("a").attr("href")))
                    .forEach(urlFiles::put);

        } catch (InterruptedException | ExecutionException | IOException exception) {
            LOGGER.error(ExceptionUtils.getStackTrace(exception));
        }

        return urlFiles;

    }

    @Override
    protected String getPath() {
        return path.concat("presencas/");
    }
}
