package mmf.piskunou.lab4;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

public class MainIntentService extends IntentService {
    static final String  AMOUNT_KEY = "amount";
    static final String PRIME_NUMBERS_STRING_KEY = "prime";


    public MainIntentService() {
        super("MainIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        int number = intent.getIntExtra(MainActivity.NUMBERS_KEY, 0);
        List<Integer>  primeNumbers = getListOfPrimeNumbers(number);
        int amount = primeNumbers.size();
        StringBuilder stringOfPrimeNumbers = convertPrimeNumbersToString(primeNumbers);

        Intent responseIntent = new Intent("MainActivity");
        responseIntent.putExtra(AMOUNT_KEY, amount);
        responseIntent.putExtra(PRIME_NUMBERS_STRING_KEY, stringOfPrimeNumbers.toString());
        LocalBroadcastManager.getInstance(this).sendBroadcast(responseIntent);
    }

    private boolean isPrime(int number){
        if (number < 2) return false;
        for (int i = 2; i < number; i++) {
            if(number % i == 0) return false;
        }

        return true;
    }

    private List<Integer> getListOfPrimeNumbers(int number){
        List<Integer> primeNumbers = new ArrayList<>();
        for (int i = 0; i <= number; i++){
            if (isPrime(i)) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    private StringBuilder convertPrimeNumbersToString(List<Integer> primeNumbers){
        StringBuilder stringOfPrimeNumbers = new StringBuilder();
        for (int i = 0; i < primeNumbers.size(); i++){
            stringOfPrimeNumbers.append(primeNumbers.get(i)).append(", ");
        }
        return stringOfPrimeNumbers;
    }
}
