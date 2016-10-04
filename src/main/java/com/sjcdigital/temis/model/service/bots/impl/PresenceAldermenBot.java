package com.sjcdigital.temis.model.service.bots.impl;

import com.sjcdigital.temis.model.exceptions.BotException;
import com.sjcdigital.temis.model.service.bots.AbstractBot;
import com.sjcdigital.temis.util.File;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @author fabiohbarbosa
 */
@Component
@Order(3)
public class PresenceAldermenBot extends AbstractBot {

    private static final Logger LOGGER = LogManager.getLogger(PresenceAldermenBot.class);

    @Value("${url.presence-aldermen}")
    private String presenceAldermenUrl;

    @Value("${path.leis}")
    private String path;

    @Value("${year.start.extract}")
    private int year;

    @Autowired
    private File file;

    @Override
    public void saveData() throws BotException {

        final Collection<String> allDivs = getPresenceAldermenDivs();

        for (final String div : allDivs) {
            file.createFile(getPath(), div, getFileName(div), LocalDate.now().getYear());
        }

    }

    private Collection<String> getPresenceAldermenDivs() throws BotException {

        final Collection<String> divs = new HashSet<>();

        try {

            final Document document = Optional.ofNullable(getPage(presenceAldermenUrl).get()).orElseThrow(BotException::new);
            final Elements divsPresenca = document.getElementsByClass("presenca");

            divsPresenca.stream()
                    .map(Element::html)
                    .forEach(divs::add);

        } catch (InterruptedException | ExecutionException | IOException exception) {
            LOGGER.error(ExceptionUtils.getStackTrace(exception));
        }

        return divs;

    }

    private String getFileName(final String div) {
        final Document document = Jsoup.parse(div, "UTF-8");
        return document.select("time").html().replace("/", "-");
    }

    @Override
    protected String getPath() {
        return path.concat("presencas/");
    }
}
