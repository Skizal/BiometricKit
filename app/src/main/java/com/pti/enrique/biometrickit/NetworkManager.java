package com.pti.enrique.biometrickit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NetworkManager
{
    private static final String TAG = "NetworkManager";
    private static NetworkManager instance = null;
    private static final String prefixURL = "http://138.68.91.175:8888/api/";

    //for Volley API
    public RequestQueue requestQueue;
    private JSONObject user;
    private ArrayList<String> devices;
    private String token;

    private NetworkManager(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized NetworkManager getInstance(Context context)
    {
        if (null == instance)
            instance = new NetworkManager(context);
        return instance;
    }

    //this is so you don't need to pass context each time
    public static synchronized NetworkManager getInstance()
    {
        if (null == instance)
        {
            throw new IllegalStateException(NetworkManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void setUserJSON( String id, String password )
    {
        user = JSONCreator.token( id, password );
    }

    /*
    private void getToken( final Context con )
    {
        boolean ret = false;
        String url = prefixURL + "renewtoken";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, user,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            token = response.getString( "token" );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( con, "Error communicating with the server", Toast.LENGTH_SHORT ).show();
                    }
                });
        requestQueue.add(request);
    }
    */

    public void getDevices( final Context con )
    {
        String url = prefixURL + "getdevicesfromuser";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url , JSONCreator.basic( token ),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Main act = ( Main ) con;
                        boolean ok = false;
                        try {
                            ok = response.getBoolean( "success" );
                            JSONArray devs = response.getJSONArray( "devices" );
                            String error = response.getString( "errorType" );

                            if( ok ){
                                act.resetDevices();
                                for( int i = 0; i < devs.length(); ++i ){
                                    act.updateDevices( devs.getJSONObject( i ).getString("deviceID") );
                                }
                                act.updateRecyclerView( );
                            }
                            else{
                                Toast.makeText( con, error , Toast.LENGTH_SHORT ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( con, "Error communicating with the server", Toast.LENGTH_SHORT ).show();
                    }
                })
                    {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("x-access-token", token);
                        return headers;
                    }
                };
        requestQueue.add(request);
    }

    public void addDevice( final Context con, final String id )
    {
        String url = prefixURL + "adddevice";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, JSONCreator.device( token, id ),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        boolean ok = false;
                        String error = "";
                        try {
                            error = response.getString( "message" );
                            ok = response.getBoolean( "success" );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if( ok ){
                            Toast.makeText( con, error , Toast.LENGTH_SHORT ).show();
                            Main act = (Main) con;
                            act.updateDevices( id );
                            act.updateRecyclerView( );
                        }
                        else {
                            Toast.makeText( con, error, Toast.LENGTH_SHORT ).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( con, "Error communicating with the server", Toast.LENGTH_SHORT ).show();
                    }
                });
        requestQueue.add(request);
    }

    public void deleteDevice( final Context con, final String id )
    {
        String url = prefixURL + "deldevice";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, JSONCreator.device( token, id ),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        boolean ok = false;
                        String error = "";
                        try {
                            error = response.getString( "message" );
                            ok = response.getBoolean( "success" );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if( ok ){
                            Toast.makeText( con, error , Toast.LENGTH_SHORT ).show();
                            Main act = (Main) con;
                            act.deleteDevice( id );
                            act.updateRecyclerView( );
                        }
                        else {
                            Toast.makeText( con, error , Toast.LENGTH_SHORT ).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( con, "Error communicating with the server", Toast.LENGTH_SHORT ).show();
                    }
                });
        requestQueue.add(request);
    }

    public void getMonth( final Context con, String month, String year, String id )
    {
        String url = prefixURL + "getmonth";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, JSONCreator.month( token, year, month, id ),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Pulse act = ( Pulse ) con;
                        boolean ok = false;
                        try {
                            ok = response.getBoolean( "success" );
                            JSONArray devs = response.getJSONArray( "records" );
                            String error = response.getString( "errorType" );

                            if( ok ){
                                ArrayList<Double> month = new ArrayList<>();
                                for( int i = 0; i < devs.length(); ++i ){
                                    String dev = devs.getJSONObject( i ).getString("value");
                                    month.add(  Double.parseDouble( dev ) );
                                }
                                act.updateSeriesMonth( month );
                                Toast.makeText( con, error , Toast.LENGTH_SHORT ).show();
                            }
                            else{
                                Toast.makeText( con, error , Toast.LENGTH_SHORT ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( con, "Error communicating with the server", Toast.LENGTH_SHORT ).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-access-token", token);
                return headers;
            }
        };
        requestQueue.add(request);
    }

    public void getDay( final Context con, String day, String month, String year, String id )
    {
        String url = prefixURL + "getday";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, JSONCreator.day( token, year, month, day, id ),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Pulse act = ( Pulse ) con;
                        boolean ok = false;
                        try {
                            ok = response.getBoolean( "success" );
                            JSONArray devs = response.getJSONArray( "records" );
                            String error = response.getString( "errorType" );

                            if( ok ){
                                ArrayList<Double> month = new ArrayList<>();
                                for( int i = 0; i < devs.length(); ++i ){
                                    String dev = devs.getJSONObject( i ).getString("value");
                                    month.add(  Double.parseDouble( dev ) );
                                }
                                act.updateSeriesDay( month );
                                Toast.makeText( con, error , Toast.LENGTH_SHORT ).show();
                            }
                            else{
                                Toast.makeText( con, error , Toast.LENGTH_SHORT ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( con, "Error communicating with the server", Toast.LENGTH_SHORT ).show();
                    }
                })
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("x-access-token", token);
                        return headers;
                    }
                };
        requestQueue.add(request);
    }

    public void getReal( final Context con,  String id )
    {
        String url = prefixURL + "getrealtime";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url , JSONCreator.device( token, id ),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Pulse act = ( Pulse ) con;
                        boolean ok = false;
                        try {
                            ok = response.getBoolean( "success" );
                            JSONArray devs = response.getJSONArray( "records" );
                            String error = response.getString( "errorType" );

                            if( ok ){
                                ArrayList<Double> real = new ArrayList<>();
                                for( int i = 0; i < devs.length(); ++i ){
                                    String dev = devs.getJSONObject( i ).getString("value");
                                    real.add(  Double.parseDouble( dev ) );
                                }
                                act.updateReal( real );
                                Toast.makeText( con, error , Toast.LENGTH_SHORT ).show();
                            }
                            else{
                                Toast.makeText( con, error , Toast.LENGTH_SHORT ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( con, "Error communicating with the server", Toast.LENGTH_SHORT ).show();
                    }
                })
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("x-access-token", token);
                        return headers;
                    }
                };
        requestQueue.add(request);
    }

    public void createUser( final Context con, String id, String password, String name, String lastName, String email )
    {
        String url = prefixURL + "createuser";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, JSONCreator.createUser( id, password, name, lastName, email ),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Toast.makeText( con, "response", Toast.LENGTH_SHORT ).show();
                        boolean success = false;
                        String error = "";
                        try {
                            success = response.getBoolean( "success" );
                            error = response.getString( "message" );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if( success ){
                            SignUp act = (SignUp) con;
                            act.finishOK();
                        }
                        else{
                            if( error.equals( "idAlreadyUsed" ) ){
                                Toast.makeText( con, " UserName already used ", Toast.LENGTH_SHORT ).show();
                            }
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                            Toast.makeText( con, "Error communicating with the server", Toast.LENGTH_SHORT ).show();
                    }
                });
        requestQueue.add(request);
    }


    public void deleteHistory( final Context con )
    {
        String url = prefixURL + "deletehistory";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        String res = "";
                        try {
                            res = response.getString( "message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText( con, res , Toast.LENGTH_SHORT ).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( con, "Error communicating with the server", Toast.LENGTH_SHORT ).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-access-token", token);
                return headers;
            }
        };
        requestQueue.add(request);
    }

    public void deleteUser( final Context con )
    {
        String url = prefixURL + "deleteuser";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        String res = "";
                        try {
                            res = response.getString( "message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText( con, res , Toast.LENGTH_SHORT ).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( con, "Error communicating with the server", Toast.LENGTH_SHORT ).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-access-token", token);
                return headers;
            }
        };
        requestQueue.add(request);
    }

    public void login( final Context con )
    {
        String url = prefixURL + "login";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, user,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        boolean ok = false;
                        String error = "";
                        try {
                            token = response.getString( "token" );
                            ok = response.getBoolean( "success" );
                            error = response.getString( "message" );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if( ok ){
                            Login act = ( Login ) con;
                            act.toMain();
                        }
                        else {
                            if( error.equals( "Notvalidated" ) ){
                                Login act = (Login) con;
                                act.toValidate();
                            }
                            else {
                                Toast.makeText( con, error, Toast.LENGTH_SHORT ).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( con, "Error communicating with the server", Toast.LENGTH_SHORT ).show();
                    }
                });
        requestQueue.add(request);
    }

    public void validate( final Context con, String user, String password, String code )
    {
        String url = prefixURL + "validate";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, JSONCreator.validate( user, password, code),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        boolean ok = false;
                        String error = "";
                        try {
                            token = response.getString( "token" );
                            ok = response.getBoolean( "success" );
                            error = response.getString( "errorType" );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if( ok ){
                            Validate v = (Validate) con;
                            v.toMain();
                        }
                        else {
                            Toast.makeText( con, error, Toast.LENGTH_SHORT ).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( con, "Error communicating with the server", Toast.LENGTH_SHORT ).show();
                    }
                });
        requestQueue.add(request);
    }

    public void logout( )
    {
        user = null;
    }
}

