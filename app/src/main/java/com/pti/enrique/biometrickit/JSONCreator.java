package com.pti.enrique.biometrickit;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Enrique on 17/11/2016.
 */

public final class JSONCreator {

    private JSONCreator(){

    }

   public static JSONObject createUser( String id, String password, String name, String lastName, String email ){
       JSONObject newUser = new JSONObject();
       try {
           newUser.put( "id", id );
           newUser.put( "password", password );
           newUser.put( "name", name );
           newUser.put( "lastName", lastName );
           newUser.put( "email", email );

       } catch (JSONException e) {
           e.printStackTrace();
       }
       return newUser;
   }

    public static JSONObject basic( String token ){
        JSONObject userToken = new JSONObject();
        try {
            userToken.put( "token", token );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userToken;
    }

    public static JSONObject id( String id ){
        JSONObject idj = new JSONObject();
        try {
            idj.put( "deviceID", id );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return idj;
    }

    public static JSONObject device( String token, String deviceID ){
        JSONObject device = new JSONObject();
        try {
            device.put( "token", token );
            device.put( "deviceID", deviceID );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return device;
    }

    public static JSONObject token( String id, String password ){
        JSONObject login = new JSONObject();
        try {
            login.put( "id", id );
            login.put( "password", password );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return login;
    }

    public static JSONObject validate( String id, String password, String code ){
        JSONObject validate = new JSONObject();
        try {
            validate.put( "id", id );
            validate.put( "password", password );
            validate.put( "code", code );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return validate;
    }

    public static JSONObject month( String token, String year, String month, String deviceID ){
        JSONObject jMonth = new JSONObject();
        try {
            jMonth.put( "token", token );
            jMonth.put( "year", year );
            jMonth.put( "month", jMonth );
            jMonth.put( "deviceID", deviceID );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jMonth;
    }

    public static JSONObject day(  String token, String year, String month, String day, String deviceID ){
        JSONObject jDay = new JSONObject();
        try {
            jDay.put( "token", token );
            jDay.put( "year", year );
            jDay.put( "month", month );
            jDay.put( "day", day );
            jDay.put( "deviceID", deviceID );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jDay;
    }

}
