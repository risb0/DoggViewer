package com.example.risbo.puppyviewer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PuppyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private GoogleApiClient client;
    private static final String WEBSITE_DIRECTORY = "http://www.martystepp.com/dogs/";
    private static final  String LIST_FILE = WEBSITE_DIRECTORY + "files2.txt";

    // ButterKnife bindings allow to declare a field that will be always be set to the value of a widget with a certain ID.
    @Bind(R.id.helloworld)
    TextView textView;

    @Bind(R.id.puppy_spinner)
    Spinner spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puppy);
        ButterKnife.bind(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //Spinner spinner = (Spinner) findViewById(R.id.puppy_spinner)  : deprecated because declare with @Bind
        spinner.setOnItemSelectedListener(this);

        //download list of puppy images to display
        Ion.with(this)
                .load(LIST_FILE)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.v("ion", result);
                        String [] lines = result.split("\n");
                        loadDogs(lines);
                    }
                });

    }

    private void loadDogs(String[] imageNames){
        ArrayList<String> files = new ArrayList<>();
        for (String line : imageNames){
            if(!line.isEmpty()){
                files.add(line);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                files);

        spinner.setAdapter(adapter);
    }



    @OnClick(R.id.clickme)
    public void clickMeClick(View view) {

        YoYo.with(Techniques.Wobble)
                .duration(2000)
                .withListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                       Button butt = (Button) findViewById(R.id.clickme);
                        butt.setText("Yay done!");
                    }
                })
                .playOn(textView);
    }

    private void loadImage(){

        Spinner spin = (Spinner) findViewById(R.id.puppy_spinner);
        String filename = spin.getSelectedItem().toString();
        ImageView img = (ImageView) findViewById(R.id.puppyphoto);
        Picasso.with(this)
                .load(WEBSITE_DIRECTORY + filename)
//                .resize(500, 300)
//                .rotate(90)
                .error(R.drawable.fail)
                .placeholder(R.drawable.loading)
                .into(img);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        loadImage();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    // AUTO GENERATED

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Puppy Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.risbo.puppyviewer/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Puppy Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.risbo.puppyviewer/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }



}
