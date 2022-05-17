package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import java.util.HashMap;
import java.util.Map;

public class TestMessageSender {

    @Test
    void testSendToAddressWithRussanIP() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("172."))).thenReturn(new Location(null, Country.RUSSIA, null, 0));
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        MessageSender sender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> header = new HashMap<String, String>();
        header.put("x-real-ip", "172.123.35.45");
        Assertions.assertEquals(sender.send(header), "Добро пожаловать");
    }

    @Test
    void testSendToAddressWithAmericanIP() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("96."))).thenReturn(new Location(null, Country.USA, null, 0));
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        MessageSender sender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> header = new HashMap<String, String>();
        header.put("x-real-ip", "96.75.124.43");
        Assertions.assertEquals(sender.send(header), "Welcome");
    }

    @Test
    void testSendToAddressWithGermanyIP() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.matches("^(?!(96|172)[.]).*"))).thenReturn(new Location(null, Country.GERMANY, null, 0));
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.GERMANY)).thenReturn("Welcome");
        MessageSender sender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> header = new HashMap<String, String>();
        header.put("x-real-ip", "102.234.64.237");
        Assertions.assertEquals(sender.send(header), "Welcome");
    }

    @Test
    void testSendToAddressWithBrazilianIP() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.matches("^(?!(96|172)[.]).*"))).thenReturn(new Location(null, Country.BRAZIL, null, 0));
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.BRAZIL)).thenReturn("Welcome");
        MessageSender sender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> header = new HashMap<String, String>();
        header.put("x-real-ip", "156.234.64.237");
        Assertions.assertEquals(sender.send(header), "Welcome");
    }
}
