package com.example.mybank;

import android.content.Context;
import android.content.SharedPreferences;

public class FirstStartPreferences {
	
	private static final String Einstellungen = "meine Einstellungen";  

    public static boolean isFirst(Context context){
        final SharedPreferences reader = context.getSharedPreferences(Einstellungen, Context.MODE_PRIVATE); 
        final boolean first = reader.getBoolean("is_first", true);
        if(first){
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_first", false);
            editor.commit();
        }
        return first;
    }

}
