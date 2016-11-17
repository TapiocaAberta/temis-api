/**
 * 
 */
package com.sjcdigital.temis.model.service.parsers.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author pedro-hos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class AldermanParserUtilTest {
	
	@InjectMocks
	private AldermanParserUtil aldermanParserUtil;
	
	@Test
	public void testGetNoSpecificPolitician() {
		Assert.assertEquals("Mesa Diretora", aldermanParserUtil.specificNoPolitician("(Projeto de Lei 418/2009 de autoria da Mesa Diretora)").get());
		Assert.assertEquals("Comissão de Justiça", aldermanParserUtil.specificNoPolitician("(Projeto de Lei de autoria da Comissão de Justiça)").get());
	}
	
}
