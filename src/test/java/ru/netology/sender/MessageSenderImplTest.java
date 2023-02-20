package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderImplTest {
    GeoService geoService = Mockito.mock(GeoServiceImpl.class);
    LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
    Location location = Mockito.mock(Location.class);

    @Test
    public void testUsaSend () {
        System.out.println("Message sender - send test - USA ip");

        //arrange
        String ip = "172.12.33.82";

        //act
        String expected = "Welcome";
        Country country = Country.USA;

        Mockito.when(location.getCountry()).thenReturn(country);
        Mockito.when(localizationService.locale(country)).thenReturn("Welcome");
        Mockito.when(geoService.byIp(ip)).thenReturn(location);


        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String messageSenders = messageSender.send(headers);

        //assert
        Assertions.assertEquals(expected, messageSenders);
    }

    @Test
    public void testRussiaSend () {
        System.out.println("Message sender - send test - Russia ip");

        //arrange
        String ip = "172.123.12.19";

        //act
        String expected = "Добро пожаловать";
        Country country = Country.RUSSIA;

        Mockito.when(location.getCountry()).thenReturn(country);
        Mockito.when(localizationService.locale(country)).thenReturn("Добро пожаловать");
        Mockito.when(geoService.byIp(ip)).thenReturn(location);


        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String messageSenders = messageSender.send(headers);

        //assert
        Assertions.assertEquals(expected, messageSenders);
    }

    @Test
    public void testLocalSend () {
        System.out.println("Message sender - send test - localhost");

        //arrange
        String ip = "127.0.0.1";

        //act
        String expected = "Welcome to local";

        Mockito.when(location.getCountry()).thenReturn(null);
        Mockito.when(localizationService.locale(null)).thenReturn("Welcome to local");
        Mockito.when(geoService.byIp(ip)).thenReturn(location);


        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String messageSenders = messageSender.send(headers);

        //assert
        Assertions.assertEquals(expected, messageSenders);
    }
}
