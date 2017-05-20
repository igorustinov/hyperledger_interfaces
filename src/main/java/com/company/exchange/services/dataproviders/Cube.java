package com.company.exchange.services.dataproviders;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

class Cube {
    @XmlElement(name="Cube")
    private List<Cube> cubes;
    @XmlAttribute(name="time")
    private String date;
    @XmlAttribute(name="currency")
    private String currency;
    @XmlAttribute(name="rate")
    private String rate;

    List<Cube> getCubes() {
        return cubes;
    }

    String getDate() {
        return date;
    }

    String getCurrency() {
        return currency;
    }

    String getRate() {
        return rate;
    }
}
