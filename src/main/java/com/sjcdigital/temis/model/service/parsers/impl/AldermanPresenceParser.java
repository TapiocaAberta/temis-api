package com.sjcdigital.temis.model.service.parsers.impl;

import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.document.AldermanPresenceSheet;
import com.sjcdigital.temis.model.document.OrdinarySession;
import com.sjcdigital.temis.model.repositories.AldermanRepository;
import com.sjcdigital.temis.model.repositories.OrdinarySessionRepository;
import com.sjcdigital.temis.model.service.parsers.AbstractParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * @author fabiohbarbosa
 */
@Component
public class AldermanPresenceParser extends AbstractParser {

    @Autowired
    private AldermanRepository aldermanRepository;

    @Autowired
    private OrdinarySessionRepository sessionRepository;

    @Override
    public void parse(final File file) {

        try {

            final HSSFWorkbook wb = new HSSFWorkbook(FileUtils.openInputStream(file));
            final HSSFSheet sheet = wb.getSheetAt(0);

            final int session = parseSession(wb.getSheetAt(0).getRow(AldermanPresenceSheet.SESSION_ROW.NUM));

            for (int i = AldermanPresenceSheet.FIRST_ROW.NUM; i < AldermanPresenceSheet.LAST_ROW.NUM; i++) {
                final HSSFRow row = sheet.getRow(i);

                final LocalDate date = parseDate(file);
                final Alderman alderman = parseAlderman(row);
                final boolean isPresent = parsePresent(row);
                save(session, date, alderman, isPresent);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void save(final int session, final LocalDate date, final Alderman alderman, final boolean isPresent) {
        Optional<Alderman> optSession = sessionRepository.findBySessionAndDateAndAlderman(session, date, alderman);
        if (!optSession.isPresent()) {
            sessionRepository.save(new OrdinarySession(session, date, alderman, isPresent));
        }
    }

    private boolean parsePresent(final HSSFRow row) {
        return row.getCell(AldermanPresenceSheet.PRESENT_COLUMN.NUM).getStringCellValue().equalsIgnoreCase("SIM");
    }

    private int parseSession(final HSSFRow row) {
        return Integer.parseInt(row.getCell(AldermanPresenceSheet.SESSION_COLUMN.NUM)
                .getStringCellValue()
                .trim()
                .split("-")[1]
                .trim()
                .split("ª")[0]
                .trim());
    }

    private LocalDate parseDate(final File file) {
        return LocalDate.parse(FilenameUtils.removeExtension(file.getName()));
    }

    private Alderman parseAlderman(final HSSFRow row) {

        final String name = row.getCell(AldermanPresenceSheet.NAME_COLUMN.NUM).getStringCellValue().trim();
        final Optional<Alderman> optAlderman = aldermanRepository.findByNameContainingIgnoreCase(name);
        return optAlderman.orElseGet(() -> aldermanRepository.save(new Alderman(WordUtils.capitalize(name.toLowerCase()), true)));

    }

}
