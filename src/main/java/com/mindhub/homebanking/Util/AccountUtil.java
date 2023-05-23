package com.mindhub.homebanking.Util;

public class AccountUtil {
    public static String generaRandom(){
        int cuentaRandom = (int) (Math.random()*899999+100000);
        String numberAccount = "vin " + Integer.toString(cuentaRandom);
        return numberAccount;
    }
}
