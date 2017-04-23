package com.sjcdigital.temis.model.repositories.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.repositories.Repository;

/**
 * @author pedro-hos
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class Leis extends Repository<Lei> { }
