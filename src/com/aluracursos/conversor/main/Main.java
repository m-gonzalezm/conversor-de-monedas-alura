package com.aluracursos.conversor.main;

import com.aluracursos.conversor.models.Conversion;
import com.aluracursos.conversor.models.Exchange;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
        String key = "ea8dc4b7d45220120319651e";
        Map<String, String> currencies = new HashMap<String, String>();
        Conversion conversion = new Conversion();
        currencies.put("ARS", "Argentine Peso");
        currencies.put("AUD", "Australian Dollar");
        currencies.put("BRL", "Brazilian Real");
        currencies.put("CAD", "Canadian Dollar");
        currencies.put("CLP", "Chilean Peso");
        currencies.put("COP", "Colombian Peso");
        currencies.put("MXN", "Mexican Peso");
        currencies.put("PEN", "Peruvian Sol");
        currencies.put("USD", "United States Dollar");
        currencies.put("UYU", "Uruguayan Peso");

        while (true) {

            System.out.printf("ExchangeRate\nCurrencies availables:\n");
            for (String currency : currencies.keySet()) {
                System.out.println(currency + " " + currencies.get(currency));
            }
            System.out.print("Please type the currency code or exit to leave: ");
            conversion.setBase(sc.nextLine().toUpperCase());

            if (conversion.getBase().equalsIgnoreCase("exit")) {
                System.out.println("See you later!");
                break;
            }

            if (currencies.keySet().contains(conversion.getBase())) {
                while (true) {
                    System.out.print("Please type the ammount to convert: ");
                    conversion.setAmount(sc.nextDouble());
                    sc.nextLine();

                    while (true) {
                        System.out.print("Please type the currency for conversion: ");
                        String toCurrency = sc.nextLine().toUpperCase();

                        if (currencies.keySet().contains(toCurrency)) {

                            String url = "https://v6.exchangerate-api.com/v6/" + key + "/latest/" + conversion.getBase() + "/" + toCurrency;

                            try {
                                HttpClient client = HttpClient.newHttpClient();
                                HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create(url))
                                        .build();
                                HttpResponse<String> response = client
                                        .send(request, HttpResponse.BodyHandlers.ofString());

                                String json = response.body();
                                Exchange exchange = gson.fromJson(json, Exchange.class);
                                System.out.printf("%f %ss are %f %ss\n", conversion.getAmount(),
                                        currencies.get(exchange.base_code()), exchange.conversion_rate() * conversion.getAmount(),
                                        currencies.get(exchange.target_code()));
                                break;

                            } catch (IllegalArgumentException e) {
                                System.out.println("Please verify the URI.");
                            }
                        }
                    }
                }
            }
        }
    }
}
