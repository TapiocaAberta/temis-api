package com.sjcdigital.temis.model.service.camel.route.impl;

import com.sjcdigital.temis.model.service.camel.processor.impl.PresenceAldermanProcessor;
import com.sjcdigital.temis.model.service.camel.route.AbstractRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fabiohbarbosa
 */
@Component
public class PresenceAldermanRoute extends AbstractRoute {

    @Autowired
    private PresenceAldermanProcessor processor;

    @Override
    public void configure() throws Exception {
        from(FILE + path() + options()).process(processor);
    }

    @Override
    protected String path() {
        return path.concat("presencas/");
    }

}
