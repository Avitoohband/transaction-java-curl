package com.avi.transaction.service;

import java.math.BigDecimal;
import java.util.Currency;

public interface CurrencyConversionService {
    BigDecimal getConversionRate(Currency toCurrency);
}
