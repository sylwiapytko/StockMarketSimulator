package com.example.sylwia.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RepoList extends AppCompatActivity {
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    List<String> repos;
    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);
        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.


//        String[] foods = {"Bacon", "Ham", "Tuna", "Candy", "Meatball", "Potato"};
        repos = new ArrayList<>();
        repos.add("bacon");
        repos.add("candy");
        repos.add("tuna");
        repos.add("ham");
        System.out.println("start");
        getRepoList();
        System.out.println("end");

    }

private void setRepoList(){
    ListAdapter buckysAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, repos);
    ListView buckysListView = (ListView) findViewById(R.id.buckysListView);
    buckysListView.setAdapter(buckysAdapter);

    buckysListView.setOnItemClickListener(
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String food = String.valueOf(parent.getItemAtPosition(position));
                    Toast.makeText(RepoList.this, food, Toast.LENGTH_LONG).show();
                }
            }
    );
}
    private void getRepoList() {
        System.out.println("in");
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).
//        this.url = "https://api.github.com/users/sylwiapytko/repos";
         this.url = "https://api.iextrading.com/1.0/stock/market/list/mostactive";
//         this.url = "https://api.iextrading.com/1.0/stock/market/list/iexvolume";
//
        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Check the length of our response (to see if the user has any repos)
                        if (response.length() > 0) {
                            System.out.println("response");
                            // The user does have repos, so let's loop through them all.
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    // For each repo, add a new line to our repo list.
                                    JSONObject jsonObj = response.getJSONObject(i);
//                                    String repoName = jsonObj.get("name").toString();
                                    //String lastUpdated = jsonObj.get("updated_at").toString();
                                    String repoName = jsonObj.get("companyName").toString();
//                                    String companyName = jsonObj.get("companyName").toString();
                                    repos.add(repoName);
//                                    System.out.println(jsonObj);
                                    System.out.println(repoName);
                                } catch (JSONException e) {
                                    // If there is an error then output this to the logs.
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                        } else {
                            // The user didn't have any repos.
                        }
                        setRepoList();

                        System.out.println("endend");

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If there a HTTP error then add a note to our repo list.
                        Log.e("Volley", error.toString());
                    }
                }
        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(arrReq);
      //  System.out.println("endend");
    }
}
