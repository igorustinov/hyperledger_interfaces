package com.company.exchange.services.dataproviders;

import com.company.exchange.model.ExchangeRate;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExchangeRateAdapterTest {
    @Test
    public void translate() throws Exception {

        String xml = "<gesmes:Envelope xmlns:gesmes=\"http://www.gesmes.org/xml/2002-08-01\">\n" +
                "<gesmes:subject>Reference rates</gesmes:subject>\n" +
                "<gesmes:Sender><gesmes:name>European Central Bank</gesmes:name></gesmes:Sender>\n" +
                "<Cube>\n" +
                "<Cube time=\"2017-05-19\">\n" +
                "<Cube currency=\"USD\" rate=\"1.1179\"/>\n" +
                "<Cube currency=\"JPY\" rate=\"124.35\"/>\n" +
                "</Cube>\n" +
                "</Cube>\n" +
                "</gesmes:Envelope>";
        JAXBContext context = JAXBContext.newInstance(Envelope.class);

        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final Envelope obj = (Envelope) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        final List<ExchangeRate> rateList = new ExchangeRateAdapter().translate(obj);

        assertEquals(rateList.size(), 2);
        assertEquals(rateList.get(0).getCurrency(), Currency.getInstance("USD"));
        assertEquals(rateList.get(1).getCurrency(), Currency.getInstance("JPY"));
        assertTrue(Double.compare(rateList.get(0).getRate(), 1.1179) == 0);
        assertTrue(Double.compare(rateList.get(1).getRate(), 124.35) == 0);
        assertEquals(rateList.get(0).getDate(), LocalDate.parse("2017-05-19"));
        assertEquals(rateList.get(1).getDate(), LocalDate.parse("2017-05-19"));

    }

}