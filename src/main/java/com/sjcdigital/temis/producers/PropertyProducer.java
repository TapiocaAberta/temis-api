package com.sjcdigital.temis.producers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import com.sjcdigital.temis.annotations.Property;

/**
 * @author pedro-hos
 *
 */
@Dependent
public class PropertyProducer {

	private Properties properties;

	@Property
	@Produces
	public String produceString(final InjectionPoint ip) {
		return this.properties.getProperty(getKey(ip));
	}

	@Property
	@Produces
	public int produceInt(final InjectionPoint ip) {
		return Integer.valueOf(this.properties.getProperty(getKey(ip)));
	}

	@Property
	@Produces
	public boolean produceBoolean(final InjectionPoint ip) {
		return Boolean.valueOf(this.properties.getProperty(getKey(ip)));
	}

	private String getKey(final InjectionPoint ip) {
		
		return ip.getQualifiers().stream().filter(Property.class :: isInstance) 
										  .map(Property.class :: cast) 
										  .findAny() 
										  .map(Property :: value)
										  .orElseThrow(() -> new RuntimeException("no key found!"));
    }

	@PostConstruct
	public void init() {
		
		final InputStream stream = Optional.ofNullable(PropertyProducer.class.getResourceAsStream("/application.properties"))
										   .orElseThrow(() -> new RuntimeException("no properties!"));
		
		try {
			
			this.properties = new Properties();
			this.properties.load(stream);
			
		} catch (final IOException e) {
			throw new RuntimeException("Configuration could not be loaded!");
		}
		
	}

}
