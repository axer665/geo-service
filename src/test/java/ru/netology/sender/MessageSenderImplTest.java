package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderImplTest {
    GeoService geoService = Mockito.mock(GeoServiceImpl.class);
    LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);

    Location location = Mockito.mock(Location.class);
    @ParameterizedTest
    @MethodSource("ipSource") //arrange
    public void testSend (String ip) {
        //act
        String expected;
        Country country;

        System.out.println("Message sender - send test - " + ip);

        if (ip.startsWith("172.")) {
            country = Country.RUSSIA;
            expected = "Добро пожаловать";
          } else if (ip.startsWith("96.")) {
            country = Country.USA;
            expected = "Welcome";
        } else {
            country = null;
            expected = "Welcome local";
        }
        Mockito.when(location.getCountry()).thenReturn(country);
        location = new Location("Moscow", country, null, 0);
        Mockito.when(localizationService.locale(country)).thenReturn(expected);

        Mockito.when(geoService.byIp(ip)).thenReturn(location);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String messageSenders = messageSender.send(headers);

        //assert
        Assertions.assertEquals(expected, messageSenders);
    }

    private static Stream<String> ipSource () {
        return Stream.of(
                "172.123.12.19",
                "96.12.33.82",
                "12.122.33.18"
        );
    }

}
