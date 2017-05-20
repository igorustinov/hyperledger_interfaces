package com.company.exchange.services.dataproviders;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Envelope", namespace = "http://www.gesmes.org/xml/2002-08-01")
class Envelope {
    @XmlElement
    private String subject;
    private String sender;
    @XmlElement(name="Cube")
    private Cube cube;

    String getSubject() {
        return subject;
    }

    String getSender() {
        return sender;
    }

    Cube getCube() {
        return cube;
    }
}
