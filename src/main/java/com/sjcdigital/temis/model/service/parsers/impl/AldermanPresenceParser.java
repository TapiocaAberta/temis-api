package com.sjcdigital.temis.model.service.parsers.impl;

import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.repositories.AldermanRepository;
import com.sjcdigital.temis.model.service.parsers.AbstractParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author fabiohbarbosa
 */
@Component
public class PresenceAldermanParser extends AbstractParser {

    @Autowired
    private AldermanRepository aldermanRepository;

    @Override
    public void parse(final File file) {

        try {

            // TODO fabiohbarbosa pegar a data e vereadores vs. presença
            final HSSFWorkbook wb = new HSSFWorkbook(FileUtils.openInputStream(file));
            final HSSFSheet sheet = wb.getSheetAt(0);

            for (int i = 14; i < 35; i++) {
                final HSSFCell cell = sheet.getRow(i).getCell(0);
                final String aldermanName = cell.getStringCellValue();

                final Optional<Alderman> optAlderman = aldermanRepository.findByNameContainingIgnoreCase(aldermanName);

                Alderman existAlterman = optAlderman
                        .orElse(aldermanRepository.save(new Alderman(WordUtils.capitalize(aldermanName.toLowerCase()), true)));
                System.out.println(existAlterman);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
