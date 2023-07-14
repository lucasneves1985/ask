package br.com.lcn.ask.utils;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Crypt {

    final static String SALT = "3786ed63fbaa2938a2e31e8faedc2866aa6f020ed47d869c68add1b5485aa548";
    final static String PASSWORD = "sorry&is!danger*23";
    final static String PASSWORD1 = "world@is$blue85";

    public static String encrypt(String text) {
        TextEncryptor encryptor = Encryptors.text(PASSWORD, SALT);
        String encryptedText = encryptor.encrypt(text);
        encryptor = Encryptors.text(PASSWORD1, SALT);
        encryptedText = encryptor.encrypt(encryptedText);

        return encryptedText;


    }

    public static String decrypt(String text) {
        //Descriptografa string
        TextEncryptor decryptor = Encryptors.text(PASSWORD1, SALT);
        String decryptedText = decryptor.decrypt(text);
        decryptor = Encryptors.text(PASSWORD, SALT);
        decryptedText = decryptor.decrypt(decryptedText);
        return decryptedText;
    }


    public static void make(Object object, String action) {
        try {
            Method staticMethod = Crypt.class.getMethod(action, String.class);
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(object).getClass().getTypeName().equals("java.lang.String"))
                    field.set(object, staticMethod.invoke(null, (String) field.get(object)));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
