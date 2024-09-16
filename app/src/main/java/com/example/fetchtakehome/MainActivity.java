package com.example.fetchtakehome;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView = findViewById(R.id.results);
        button = findViewById(R.id.buttonFetch);
        button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fetchData();
            }
        });
    }

    public void fetchData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://fetch-hiring.s3.amazonaws.com/mobile.html";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJson(response);
//                        Toast.makeText(getApplicationContext(), "Response: " + response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error fetching URL.");
                    }
        });

        queue.add(stringRequest);
    }

    public void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                int id, listId;
                String name;
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                id = jsonObject2.getInt("id");
                listId = jsonObject2.getInt("listId");
                name = jsonObject2.getString("name");

                textView.append("id: " + id + " listId: " + listId + " name: " + name + "\n");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}