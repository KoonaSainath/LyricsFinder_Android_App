package com.kunasainath.LyricsFinder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText authorName, songName;
    private Button getLyrics;
    private TextView lyrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authorName = findViewById(R.id.author_name);
        songName = findViewById(R.id.song_name);
        getLyrics = findViewById(R.id.get_lyrics);
        lyrics = findViewById(R.id.lyrics);

        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String author, song;
                author = authorName.getText().toString();
                song = songName.getText().toString();
                if(author.length()==0 || song.length()==0){
                    Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = "https://api.lyrics.ovh/v1/" + author + "/" + song;
                url.replace(" ","20%");

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)  {
                        try {
                            lyrics.setText(response.getString("lyrics"));
                        }catch (JSONException e){

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(request);
            }
        };
        getLyrics.setOnClickListener(listener);
    }
}