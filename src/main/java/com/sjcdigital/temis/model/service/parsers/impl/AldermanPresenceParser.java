package com.sjcdigital.temis.model.service.parsers.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.document.AldermanSurrogate;
import com.sjcdigital.temis.model.document.OrdinarySession;
import com.sjcdigital.temis.model.enums.AldermanPresenceSheet;
import com.sjcdigital.temis.model.repositories.AldermanRepository;
import com.sjcdigital.temis.model.repositories.OrdinarySessionRepository;
import com.sjcdigital.temis.model.service.parsers.AbstractParser;

/**
 * @author fabiohbarbosa
 */

@Component
public class AldermanPresenceParser extends AbstractParser {

    private static final Logger LOGGER = LogManager.getLogger(AldermanPresenceParser.class);

    @Autowired
    private AldermanRepository aldermanRepository;

    @Autowired
    private OrdinarySessionRepository sessionRepository;

    @Override
    public void parse(final File file) {

        LOGGER.debug(String.format("Starting file %s parse", file.getName()));
        
        try {

            final Workbook wb = new HSSFWorkbook(FileUtils.openInputStream(file));
            final Sheet sheet = wb.getSheetAt(0);

            final int session = parseSession(wb.getSheetAt(0).getRow(AldermanPresenceSheet.SESSION_ROW.NUM));

            for (int i = AldermanPresenceSheet.FIRST_ROW.NUM; i < AldermanPresenceSheet.LAST_ROW.NUM; i++) {
                final Row row = sheet.getRow(i);

                final LocalDate date = parseDate(file);
                final Alderman alderman = parseAlderman(row);
                final boolean aldermanPresent = parsePresent(row);
                final AldermanSurrogate surrogate = parseSurrogate(aldermanPresent, row);

                save(session, date, alderman, aldermanPresent, surrogate);
            }
            
            wb.close();

        } catch (IOException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            
        }
        
    }

    private AldermanSurrogate parseSurrogate(final boolean isPresent, final Row row) {
        if (isPresent) {
            return null;
        }

        final String justification = row.getCell(AldermanPresenceSheet.SURROGATE_JUSTIFICATION.NUM).getStringCellValue().trim();
        final String surrogate = row.getCell(AldermanPresenceSheet.SURROGATE_NAME_COLUMN.NUM).getStringCellValue().trim();
        final boolean surrogatePresent = row.getCell(AldermanPresenceSheet.SURROGATE_PRESENT_COLUMN.NUM).getStringCellValue().trim().equalsIgnoreCase("SIM");

        return new AldermanSurrogate(justification, surrogate, surrogatePresent);
    }

    private void save(final int session, final LocalDate date, final Alderman alderman, final boolean isPresent, final AldermanSurrogate surrogate) {
        
    	final Integer count = sessionRepository.countBySessionAndDateAndAlderman(session, date, alderman);
        
        if (count == 0) {
            sessionRepository.save(new OrdinarySession(session, date, alderman, isPresent, surrogate));
            LOGGER.info(String.format("Save '%s' in session '%sº %s'", alderman.getName(), session, date));
            return;
        }
        
        LOGGER.debug(String.format("%s already in session '%sº %s'", alderman.getName(), session, date));
    }

    private boolean parsePresent(final Row row) {
        return row.getCell(AldermanPresenceSheet.PRESENT_COLUMN.NUM)
                .getStringCellValue()
                .trim()
                .equalsIgnoreCase("SIM");
    }

    private int parseSession(final Row row) {
        return Integer.parseInt(row.getCell(AldermanPresenceSheet.SESSION_COLUMN.NUM)
                	  .getStringCellValue()
                	  .split("-")[1].trim()
                	  .split("ª")[0].trim());
    }

    private LocalDate parseDate(final File file) {
        return LocalDate.parse(FilenameUtils.removeExtension(file.getName()));
    }

    private Alderman parseAlderman(final Row row) {
    	
        final String name = Alderman.normalizeName(row.getCell(AldermanPresenceSheet.NAME_COLUMN.NUM).getStringCellValue());
        final Optional<Alderman> optAlderman = aldermanRepository.findByNameContainingIgnoreCase(name);
        
        return optAlderman.orElseGet(() -> {
            LOGGER.warn(String.format("Not found Alderman %s", name));
            return aldermanRepository.save(new Alderman(name, true));
        });

    }

}
