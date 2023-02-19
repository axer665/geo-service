package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

public class LocalizationServiceTest {
    @ParameterizedTest
    @EnumSource(Country.class) //arrange
    public void testLocale (Country country) {
        System.out.println("Localization service - locale test - " + country);

        //act
        LocalizationService localizationService = new LocalizationServiceImpl();

        String expected;
        switch (country) {
            case RUSSIA:
                expected = "Добро пожаловать";
                break;
            case GERMANY:
                expected = "Willkommen";
                break;
            case BRAZIL:
                expected = "Bem-vindo ao";
                break;
            default:
                expected = "Welcome";
        }

        String result = localizationService.locale(country);

        // assert
        Assertions.assertEquals(expected, result);
    }
}
