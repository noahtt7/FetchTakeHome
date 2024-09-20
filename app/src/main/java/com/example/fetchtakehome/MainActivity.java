package com.example.fetchtakehome;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Model> modelList;
    CustomAdapter customAdapter;
    TextView labelId;
    TextView labelListId;
    TextView labelName;
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
        labelId = findViewById(R.id.labelText);
        labelListId = findViewById(R.id.idLabelText);
        labelName = findViewById(R.id.nameLabelText);

        modelList = new ArrayList<>();
        button = findViewById(R.id.buttonFetch);
        button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fetchData();
            }
        });
    }

    /**
     * Sets up and displays the items in the RecyclerView
     */
    private void displayItems() {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        customAdapter = new CustomAdapter(this, modelList);
        recyclerView.setAdapter(customAdapter);
    }

    /**
     * Receives JSON data. Parses the JSON if successful.
     */
    public void fetchData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //parseHtmlToJson(response);
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //textView.setText("Error fetching URL.");
                        System.out.println("Error fetching URL");
                        Log.e("VolleyError: ", error.toString());
                        Toast.makeText(getApplicationContext(), "Failed to fetch data.", Toast.LENGTH_LONG).show();
                    }
        });
        queue.add(stringRequest);
    }

    /**
     * Parses JSON data, extracts all valid items
     * and displays the items on the app.
     */
    public void parseJson(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);

            List<JSONObject> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String name = item.getString("name");

                if (!name.isEmpty())
                    list.add(jsonArray.getJSONObject(i));
            }

            sortById(list);
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
                    modelList.add(new Model(id, listId, name));
            }
            labelId.setText("ID");
            labelListId.setText("List ID");
            labelName.setText("Name");
            displayItems();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sorts each item by their ID.
     */
    private void sortById(List<JSONObject> list) {
        Collections.sort(list, new Comparator<JSONObject>() {
            private static final String KEY_NAME = "id";
            @Override
            public int compare(JSONObject a, JSONObject b) {
                Integer id1 = new Integer(0);
                Integer id2 = new Integer(0);
                try {
                    id1 = a.getInt(KEY_NAME);
                    id2 = b.getInt(KEY_NAME);
                } catch (JSONException e) {
                    Log.e("TAG","An error occured: ", e);
                }
                return id1.compareTo(id2);
            }
        });
    }
}