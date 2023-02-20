package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

public class GeoServiceTest {
    GeoService geoService = new GeoServiceImpl();

    @Test
    public void testByIp() {
        System.out.println("Geo service - byIp test");

        // arrange
        String ip = "172.123.12.19";

        // act
        Location expected = null;

        if (ip == "127.0.0.1") {
            expected = new Location(null, null, null, 0);
        } else if (ip.startsWith("172.")) {
            expected =  new Location("Moscow", Country.RUSSIA, null, 0);
        } else if (ip.startsWith("96.")) {
            expected =  new Location("New York", Country.USA, null,  0);
        } else {
            expected = null;
        }

        Location result = geoService.byIp(ip);

        //assert
        Assertions.assertEquals(locationToString(expected), locationToString(result));
    }

    @Test
    public void testByRussiaIp() {
        System.out.println("Geo service - byRussiaIp test");

        // arrange
        String ip = "172.123.12.19";

        Location expected =  new Location("Moscow", Country.RUSSIA, null, 0);
        Location result = geoService.byIp(ip);

        Assertions.assertEquals(locationToString(expected), locationToString(result));
    }

    @Test
    public void testByUsaIp() {
        System.out.println("Geo service - byUsaIp test");

        // arrange
        String ip = "96.12.33.82";

        Location expected =  new Location("New York", Country.USA, null, 0);
        Location result = geoService.byIp(ip);

        Assertions.assertEquals(locationToString(expected), locationToString(result));
    }

    private String locationToString(Location location) {
        String result = location.getCountry() + "_" + location.getCity() + "_" + location.getStreet() + "_" + location.getBuiling();
        return result;
    }

    @Test
    public void testByCoordinates() {
        System.out.println("Geo service - byCoordinates test");

        double latitude = 1.1;
        double longitude = 2.1;

        Class<RuntimeException> expected = RuntimeException.class;

        Executable executable = () -> geoService.byCoordinates(latitude, longitude);

        Assertions.assertThrows(expected, executable);
    }

}
