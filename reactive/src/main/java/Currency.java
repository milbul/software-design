public enum Currency {
    RUB, USD, EUR;

    public static double convertRatio(Currency fromCurrency, Currency toCurrency) {
        return getRate(fromCurrency) / getRate(toCurrency);
    }

    private static double getRate(Currency currency) {
        return switch (currency) {
            case RUB -> 1;
            case USD -> 81;
            case EUR -> 89;
        };
    }
}
