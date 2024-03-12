package com.avi.transaction.service;

import com.avi.transaction.dto.CurrencyConversionResponse;
import com.avi.transaction.exception.CurrencyConversionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Currency;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final RestTemplate restTemplate;

    @Value("${currency.conversion.api.url}")
    private String apiUrl;

    @Value("${currency.conversion.api.key}")
    private String apiKey;

    /*
    * It won't work because of paid subscription, to make it work remove base param from the url.
    * */
    @Override
    public BigDecimal getConversionRate(Currency toCurrency) {
        String requestUrl = String.format("%s?access_key=%s&base=ILS", apiUrl, apiKey);

        try {
            CurrencyConversionResponse response = restTemplate.getForObject(requestUrl, CurrencyConversionResponse.class);
            if (response == null || response.getRates() == null) {
                log.error("Failed to fetch conversion rates");
                throw new Exception("Failed to fetch conversion rates");
            }

            if (response.getRates().containsKey(toCurrency.toString())) {
                return response.getRates().get(toCurrency.toString());
            } else {
                log.error("Unable to fetch rate of: {}", toCurrency);
                throw new CurrencyConversionException("Unable to fetch rate of: " + toCurrency,
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            log.error("Error during currency conversion from ILS to {}: {}", toCurrency, ex.getMessage());
            throw new CurrencyConversionException("Currency conversion rate could not be retrieved.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
