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
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Dubi on 16/08/2016.
 */
public  class ServerRequest {

    static String SERVER =  "https://dd08c6ca.ngrok.io/";
    static String value = "";
    static Boolean check_request = false;
    //Patient[] patient_list;
    static ArrayList<Patient> patient_list;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    static OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build();


    //client.setConnectTimeout(30, TimeUnit.SECONDS); // connect timeout
    //client.setReadTimeout(30, TimeUnit.SECONDS);    // socket timeout
    public static String user_sign_up( String type, String username, String password, String bitmap , String doctorType) throws IOException {
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

    public static String user_sign_up( String type, String username, String password, String bitmap) throws IOException {
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
    public static ArrayList<LatLng> get_locations(String cat) throws IOException{
        Request request = new Request.Builder()
                .url(SERVER +"locations/" + cat.replace("\"", ""))
                .build();

        Response response = client.newCall(request).execute();
        String res_body = response.body().string();
        Log.d("LOCATIONS RECIEVED: ", res_body);
        return JSONtoLocations(res_body);
    }

    static String get_doctor_chat(String username) throws IOException {

        Request request = new Request.Builder()
                .url(SERVER + "chat/"+username)
                .build();

        Response response = client.newCall(request).execute();
        Log.d("RAW_RESPONCE" , response.toString());
        return response.body().string();


    }
    static String get_patient_chat(String username) throws IOException {

        Request request = new Request.Builder()
                .url(SERVER + "pchat/" + username)
                .build();

        Response response = client.newCall(request).execute();
        Log.d("RAW_RESPONCE", response.toString());
        return response.body().string();
    }
    public static String log_in(String type, String username, String password)throws IOException {
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


    public static String add_to_chat( String du, String pu) throws IOException {
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


    public static String add_diagnosis(String username, String category, String diagnosis, String lat, String lng) throws IOException {
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

    public static void doctor_view_setup(String username) {
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

    static String  req(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        Log.d("RAW_RESPONCE" , response.toString());
        return response.body().string();
    }

    private static ArrayList<LatLng> JSONtoLocations(String message){
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        try {
            ArrayList<LatLng>  patient_list = new ArrayList<LatLng>();
            ArrayList<Loc> loc_patient_list = mapper.readValue(message,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class,
                            Loc.class) );
            Log.d("JSON RESAULT", loc_patient_list.get(0).toLatLng().toString());
            Log.d("JSON convertion", " ################ success ###########");
            for(int i=0; i<loc_patient_list.size(); i++){
                patient_list.add(loc_patient_list.get(i).toLatLng());
            }
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

    private static ArrayList<Patient> JSONtoPatient(String message){

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