package com.example.sevi.myapplication;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import java.util.Locale;

/**
 * Created by saxa on 12/3/2017.
 */
public class ContextWrapper extends android.content.ContextWrapper {

    public ContextWrapper(Context base) {
        super(base);
    }

    public static ContextWrapper wrap(Context context, Locale newLocale) {
        /*
        this class is useful for api25 because some methods as
        updateConfiguration is deprecated from here on
        To works for previous apis we need to adjust the code here
        a little
        The method it uses is createConfigurationContext
         */
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();
        configuration.setLocale(newLocale);
        LocaleList localeList = new LocaleList(newLocale);
        LocaleList.setDefault(localeList);
        configuration.setLocales(localeList);
        context = context.createConfigurationContext(configuration);
        return new ContextWrapper(context);
    }

}
