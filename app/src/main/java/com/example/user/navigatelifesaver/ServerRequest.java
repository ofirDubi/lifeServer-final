package com.example.user.navigatelifesaver;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Dubi on 16/08/2016.
 */
public class ServerRequest {

    String SERVER =  "https://280b854e.ngrok.io/";
    String value = "";
    Boolean check_request = false;
    //Patient[] patient_list;
    ArrayList<Patient> patient_list;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    //client.setConnectTimeout(30, TimeUnit.SECONDS); // connect timeout
    //client.setReadTimeout(30, TimeUnit.SECONDS);    // socket timeout
    public String user_sign_up( String type, String username, String password, String bitmap , String doctorType) throws IOException {
        Log.d("DOCTORSINGEDUP", "******************************");
        RequestBody body =  new FormBody.Builder()
                .add("t", type)
                .add("u", username)
                .add("p", password)
                .add("b", bitmap)
                .add("c", doctorType)
                .build();
        Request request = new Request.Builder()
                .url(SERVER +"signup")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String user_sign_up( String type, String username, String password, String bitmap) throws IOException {
        RequestBody body =  new FormBody.Builder()
                .add("t", type)
                .add("u", username)
                .add("p", password)
                .add("b", bitmap)
                .add("a" , "16")
                .add("g", "m")
                .build();
        Request request = new Request.Builder()
                .url(SERVER +"signup")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public ArrayList<LatLng> get_locations(String c) throws IOException{
        Request request = new Request.Builder()
                .url(SERVER +"locations/"+ c)
                .build();

        Response response = client.newCall(request).execute();
        Log.d("LOCATIONS RECIEVED: ", response.toString());
        return JSONtoLocations(response.body().string());
    }

    String get_doctor_chat(String username) throws IOException {

        Request request = new Request.Builder()
                .url(SERVER + "chat/"+username)
                .build();

        Response response = client.newCall(request).execute();
        Log.d("RAW_RESPONCE" , response.toString());
        return response.body().string();


    }
    String get_patient_chat(String username) throws IOException {

        Request request = new Request.Builder()
                .url(SERVER + "pchat/" + username)
                .build();

        Response response = client.newCall(request).execute();
        Log.d("RAW_RESPONCE", response.toString());
        return response.body().string();
    }
    public String log_in(String type, String username, String password)throws IOException {
        RequestBody body =  new FormBody.Builder()
                .add("t", type)
                .add("u", username)
                .add("p", password)
                .build();
        Request request = new Request.Builder()
                .url(SERVER + "signin")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }


    public String add_to_chat( String du, String pu) throws IOException {
        RequestBody body =  new FormBody.Builder()

                .add("du", du)
                .add("pu", pu)
                .build();
        Request request = new Request.Builder()
                .url(SERVER + "chat")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }


    public String add_diagnosis(String username, String category, String diagnosis, String lat, String lng) throws IOException {
        RequestBody body =  new FormBody.Builder()

                .add("u", username)
                .add("c", category)
                .add("d", diagnosis)
                .add("lat", lat)
                .add("lng", lng)
                .build();
        Request request = new Request.Builder()
                .url(SERVER)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    public void doctor_view_setup(String username) {
        try
        {
            value = req( SERVER + username);

            Log.d("RESPONSE", "this is the responce in json from server: " + value);
            JSONtoPatient(value);
        }
        catch (Exception e)
        {


            check_request =true;
            Log.d("server requests error", e.toString());
            e.printStackTrace();
        }
    }

    String req(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        Log.d("RAW_RESPONCE" , response.toString());
        return response.body().string();
    }

    private ArrayList<LatLng> JSONtoLocations(String message){
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        try {

            ArrayList<LatLng> patient_list = mapper.readValue(message,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class,
                            LatLng.class) );
            Log.d("JSON convertion", " ################ success ###########");
            return patient_list;
        } catch(JsonGenerationException e){
            Log.d("JSON convertion error", e.toString());
            e.printStackTrace();

        } catch (JsonMappingException e){
            Log.d("JSON convertion error", e.toString());
            e.printStackTrace();
        } catch (IOException e){
            Log.d("JSON convertion error", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Patient> JSONtoPatient(String message){

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        try {

            patient_list = mapper.readValue(message,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class,
                            Patient.class) );
            Log.d("Patient image", patient_list.get(0).getImageBitmap());
            Log.d("PATIENT NAME", patient_list.get(0).getName());
            Log.d("JSON convertion", " ################ success ###########");
            return patient_list;
        } catch(JsonGenerationException e){
            Log.d("JSON convertion error", e.toString());
            e.printStackTrace();

        } catch (JsonMappingException e){
            Log.d("JSON convertion error", e.toString());
            e.printStackTrace();
        } catch (IOException e){
            Log.d("JSON convertion error", e.toString());
            e.printStackTrace();
        }
        return null;
    }

}