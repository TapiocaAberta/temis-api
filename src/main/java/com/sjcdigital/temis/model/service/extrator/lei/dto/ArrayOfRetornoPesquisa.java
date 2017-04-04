package com.sjcdigital.temis.model.service.extrator.lei.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java de ArrayOfRetornoPesquisa complex type.
 * 
 * <p>
 * O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro
 * desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRetornoPesquisa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RetornoPesquisa" type="{http://tempuri.org/}RetornoPesquisa" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRetornoPesquisa", propOrder = { "retornoPesquisa" })
public class ArrayOfRetornoPesquisa {

	@XmlElement(name = "RetornoPesquisa", nillable = true)
	protected List<RetornoPesquisa> retornoPesquisa;

	/**
	 * Gets the value of the retornoPesquisa property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the retornoPesquisa property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getRetornoPesquisa().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link RetornoPesquisa }
	 * 
	 * 
	 */
	public List<RetornoPesquisa> getRetornoPesquisa() {
		if (retornoPesquisa == null) {
			retornoPesquisa = new ArrayList<RetornoPesquisa>();
		}
		return this.retornoPesquisa;
	}

}
