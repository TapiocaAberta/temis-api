package com.sjcdigital.temis.model.service.parsers.impl;

import com.sjcdigital.temis.model.service.parsers.AbstractParser;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author fabiohbarbosa
 */
@Component
public class PresenceAldermanParser extends AbstractParser {

    @Override
    public void parse(final File file) {

        try {
            final Document document = readFile(file).get();

            // TODO fabiohbarbosa pegar a data e vereadores vs. presença

            System.out.println("");
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }

    }

}
