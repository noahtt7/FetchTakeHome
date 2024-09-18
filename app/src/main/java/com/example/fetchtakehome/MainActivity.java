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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        textView.setText("");
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
        String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //parseHtmlToJson(response);
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

//    public void parseHtmlToJson(String html) {
//        Document doc = Jsoup.parse(html);
//
//        Element scriptTag = doc.selectFirst("script");
//
//        if (scriptTag != null) {
//            // Get JSON string from script tag
//            String jsonData = scriptTag.html();
//
//            try {
//                JSONObject jsonObject = new JSONObject(jsonData);
//                textView.append("test");
//
//                JSONArray jsonArray = jsonObject.getJSONArray("");
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    int id, listId;
//                    String name;
//                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                    id = jsonObject2.getInt("id");
//                    listId = jsonObject2.getInt("listId");
//                    name = jsonObject2.getString("name");
//
//                    //textView.append("forsen");
//                    if (name != "null") {
//                        //textView.append("forsen");
//                        //textView.setText("id: " + id + " listId: " + listId + " name: " + name + "\n");
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void parseJson(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            //JSONArray jsonArray = jsonObject.getJSONArray("items");
            List list = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String name = item.getString("name");

                if (name != null && name != "")
                    list.add(jsonArray.getJSONObject(i));
            }
            Collections.sort(list, new Comparator<JSONObject>() {
                private static final String KEY_NAME = "id";
                //@Override
                public int compare(JSONObject a, JSONObject b) {
                    Integer id1 = new Integer(0);
                    Integer id2 = new Integer(0);
                    try {
                        id1 = a.getInt(KEY_NAME);
                        id2 = b.getInt(KEY_NAME);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return id1.compareTo(id2);
                }
            });

//            Collections.sort(list, new Comparator<JSONObject>() {
//                private static final String KEY_NAME = "name";
//
//                public int compare(JSONObject a, JSONObject b) {
//                    String id1 = new String();
//                    String id2 = new String();
//                    try {
//                        id1 = a.getString(KEY_NAME);
//                        id2 = b.getString(KEY_NAME);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    return id1.compareTo(id2);
//                }
//            });

            JSONArray sortedArr = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                sortedArr.put(list.get(i));
            }

            for (int i = 0; i < sortedArr.length(); i++) {
                int id, listId;
                String name;
                JSONObject jsonObject2 = sortedArr.getJSONObject(i);

                id = jsonObject2.getInt("id");
                listId = jsonObject2.getInt("listId");
                name = jsonObject2.getString("name");

                if (!name.equals("null") && !name.isEmpty())
                    textView.append("id: " + id + " listId: " + listId + " name: " + name + "\n");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}