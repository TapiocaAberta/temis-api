package com.sjcdigital.temis.model.service.bots.presenca.parser;

import java.util.HashMap;
import java.util.Map;

import com.sjcdigital.temis.model.service.bots.presenca.dto.PresencaVereadores;

/**
 * @author wsiqueir
 *
 */
public class ParserUtils {

	private ParserUtils() {
	}

	public static PresencaVereadores fromText(String text) {
		PresencaVereadores aldermanAttendance;
		Map<String, String> attendance = new HashMap<>();
		String legislature = "", session = "", date = "";
		text = text.replaceAll("\\\t", "");
		for (String line : text.split("\\n")) {
			for (String aldermanName : ConstantsData.aldermanNames) {
				if (line.startsWith(aldermanName)) {
					String attendanceStr = line.substring(aldermanName.length()).trim().substring(0, 3);
					aldermanName = correctAldermanName(aldermanName);
					attendance.put(aldermanName, attendanceStr);
				}
			}
			if (line.contains("Legislatura")) {
				String[] sessionData = line.split(" - ");
				legislature = sessionData[0].replaceAll("\\s+$", "");
				session = sessionData[1];
				date = sessionData[2];
			}
		}
		aldermanAttendance = new PresencaVereadores(session, date, legislature, attendance);
		return aldermanAttendance;
	}

	/**
	 * Should correct alderman name when applicable - if there's not correction,
	 * then return the name itself.
	 * 
	 * The returned name should match the names loaded in Temis side (see
	 * AutorBot)
	 * 
	 * @param aldermanName
	 * @return
	 */
	private static String correctAldermanName(String aldermanName) {
		// let's start hardcoding the cases we now and then build something
		// better in the future
		if (aldermanName.equals("ROGÉRIO CYBORG")) {
			return "CYBORG";
		}
		if (aldermanName.equals("PETITI  DA FARMÁCIA COMUNITÁRIA")) {
			return "Fernando Petiti";
		}
		return aldermanName;
	}

}
