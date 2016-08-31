package com.sjcdigital.temis.model.service.bots;

import com.sjcdigital.temis.model.exceptions.BotException;

/**
 * @author Rafael Peretta
 *
 * Interface responsável por definir a operação que será executado por um bot.
 *
 */
public interface Bot {

    void saveData() throws BotException;
}
