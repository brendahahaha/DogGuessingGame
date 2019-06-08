package com.brenda.android.dogguessinggame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    RadioButton radioButtonA;
    RadioButton radioButtonB;
    RadioButton radioButtonC;
    RadioButton radioButtonD;
    int[] numberArray = new int[]{0,1,2,3};
    final int [] shuffleArray = RandomizeArray(numberArray);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioButtonA = findViewById(R.id.radio_A);

        radioButtonB = findViewById(R.id.radio_B);

        radioButtonC = findViewById(R.id.radio_C);

        radioButtonD = findViewById(R.id.radio_D);



        refreshImage();

        Button refreshButton = findViewById(R.id.refresh);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshImage();
            }
        });

        String[] answersArray = new String[3];

        //getRandom();

    }

    private void refreshImage() {

        final ImageView dogImageView = (ImageView) findViewById(R.id.dog_image);

        getBreeds();



        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://dog.ceo/api/breeds/image/random";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String urlString = json.getString("message");
                            Picasso.get().load(urlString).into(dogImageView);

                            String[] separated = urlString.split("/");
                            String breed = separated[4]; //breed
                            getRadioButton(shuffleArray[0]).setText(breed);
                            Log.d("PURPLE",breed);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        queue.add(stringRequest);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_A:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_B:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.radio_C:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.radio_D:
                if (checked)
                    // Ninjas rule
                    break;

        }
    }

    private void getBreeds() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://dog.ceo/api/breeds/list/all";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       Log.d("ORANGE",response);
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONObject breedsList = json.getJSONObject("message");
                            JSONArray keys = breedsList.names();

                            int length = keys.length();

                            getRadioButton(shuffleArray[1]).setText(keys.getString(new Random().nextInt(length)));
                            getRadioButton(shuffleArray[2]).setText(keys.getString(new Random().nextInt(length)));
                            getRadioButton(shuffleArray[3]).setText(keys.getString(new Random().nextInt(length)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }
    public static int[] RandomizeArray(int[] array){
        Random rgen = new Random();  // Random number generator

        for (int i=0; i<array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        return array;
    }

    public RadioButton getRadioButton(int index) {
        if (index ==0) {
            return radioButtonA;
        }
        else if (index ==1) {
            return radioButtonB;
        }
        else if (index ==2) {
            return radioButtonC;
        }
        else if(index ==3) {
            return radioButtonD;
        }

        return radioButtonA;
    }


}




