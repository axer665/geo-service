package ru.netology.i18n;

import ru.netology.entity.Country;

public class LocalizationServiceImpl implements LocalizationService {

    public String locale(Country country) {
        switch (country) {
            case RUSSIA:
                return "Добро пожаловать";
            case GERMANY:
                return "Willkommen";
            case BRAZIL:
                return "Bem-vindo ao";
            default:
                return "Welcome";
        }
    }
}
