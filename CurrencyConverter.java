import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {
    private static final String API_BASE_URL = "https://api.exchangerate.host/";

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Double> favoriteCurrencies = new HashMap<>();

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Add Favorite Currency");
            System.out.println("2. View Favorite Currencies");
            System.out.println("3. Update Favorite Currencies");
            System.out.println("4. Convert Currency");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addFavoriteCurrency();
                    break;
                case 2:
                    viewFavoriteCurrencies();
                    break;
                case 3:
                    updateFavoriteCurrencies();
                    break;
                case 4:
                    convertCurrency();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Exiting Currency Converter. Goodbye!");
    }

    private static void addFavoriteCurrency() {
        System.out.print("Enter the currency code to add to favorites: ");
        String currencyCode = scanner.nextLine().toUpperCase();

        if (isValidCurrencyCode(currencyCode)) {
            System.out.print("Enter the exchange rate for 1 unit of " + currencyCode + ": ");
            double exchangeRate = scanner.nextDouble();
            favoriteCurrencies.put(currencyCode, exchangeRate);
            System.out.println(currencyCode + " added to favorites with exchange rate: " + exchangeRate);
        } else {
            System.out.println("Invalid currency code. Please enter a valid code.");
        }
    }

    private static void viewFavoriteCurrencies() {
        if (favoriteCurrencies.isEmpty()) {
            System.out.println("Favorite currency list is empty.");
        } else {
            System.out.println("Favorite Currencies:");
            for (Map.Entry<String, Double> entry : favoriteCurrencies.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    private static void updateFavoriteCurrencies() {
        viewFavoriteCurrencies(); 
        System.out.print("Enter the currency code to update: ");
        String currencyCode = scanner.nextLine().toUpperCase();

        if (favoriteCurrencies.containsKey(currencyCode)) {
            System.out.print("Enter the new exchange rate for 1 unit of " + currencyCode + ": ");
            double newExchangeRate = scanner.nextDouble();
            favoriteCurrencies.put(currencyCode, newExchangeRate);
            System.out.println(currencyCode + " updated with new exchange rate: " + newExchangeRate);
        } else {
            System.out.println("Currency not found in favorites. Please enter a valid code.");
        }
    }

    private static void convertCurrency() {
        viewFavoriteCurrencies(); 
        System.out.print("Enter the source currency code: ");
        String sourceCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Enter the target currency code: ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        if (favoriteCurrencies.containsKey(sourceCurrency) && favoriteCurrencies.containsKey(targetCurrency)) {
            System.out.print("Enter the amount to convert from " + sourceCurrency + " to " + targetCurrency + ": ");
            double amount = scanner.nextDouble();

            double sourceExchangeRate = favoriteCurrencies.get(sourceCurrency);
            double targetExchangeRate = favoriteCurrencies.get(targetCurrency);

            double convertedAmount = (amount / sourceExchangeRate) * targetExchangeRate;
            System.out.println(amount + " " + sourceCurrency + " is equal to " + convertedAmount + " " + targetCurrency);
        } else {
            System.out.println("Invalid currency codes. Please enter valid codes from your favorite list.");
        }
    }

    private static boolean isValidCurrencyCode(String currencyCode) {
       
        return currencyCode.matches("[A-Z]{3}");
    }
}
