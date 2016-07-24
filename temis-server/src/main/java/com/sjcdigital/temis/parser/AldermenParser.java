package com.sjcdigital.temis.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sjcdigital.temis.Alderman;

/**
 * 
 * @author pedro-hos
 *        
 */
public class AldermenParser {
	
	protected Alderman extractAldermenInfo(Elements elements) {
		
		Alderman alderman = new Alderman();
		
		for (Element element : elements) {
			
			String photo = extractPhoto(element.getElementsByClass("img-responsive"));
			
			Elements elementsInfo = element.getElementsByClass("col-sm-7 texto");
			
			System.out.println(elementsInfo.select("h3").text());
			System.out.println(elementsInfo.select("h3 > p").text());
			
			System.out.println(elementsInfo.select("h4"));
			
			alderman.setPhoto(photo);
		}
		
		return alderman;
		
	}
	
	private String extractPhoto(Elements elements) {
		return elements.attr("href");
	}
	
}
