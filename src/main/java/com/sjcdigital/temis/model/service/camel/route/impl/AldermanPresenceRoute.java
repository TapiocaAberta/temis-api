package com.sjcdigital.temis.model.service.camel.route.impl;

import com.sjcdigital.temis.model.service.camel.processor.impl.AldermanPresenceProcessor;
import com.sjcdigital.temis.model.service.camel.route.AbstractRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fabiohbarbosa
 */
@Component
public class AldermanPresenceRoute extends AbstractRoute {

    @Autowired
    private AldermanPresenceProcessor processor;

    @Override
    public void configure() throws Exception {
        from(FILE + path() + options()).process(processor);
    }

    @Override
    protected String path() {
        return path.concat("presencas/");
    }

}
