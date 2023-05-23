package com.mindhub.homebanking.Util;

public class CardUtil {
    public static String generaRandomCardNumber() {
        String cuentaRandomCard = "";
        for (int i = 0; i < 4; i++) {
            int min = 1000;
            int max = 8999;
            cuentaRandomCard += String.valueOf((int) (Math.random() * max + min)) + " ";
        }
        return cuentaRandomCard;
    }

    public static int randomCardCvv() {
        int numberCvv = (int)(Math.random()*899+100);
        return numberCvv;
    }
}
