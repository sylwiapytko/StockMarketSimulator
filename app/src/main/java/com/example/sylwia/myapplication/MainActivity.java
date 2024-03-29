package com.example.sylwia.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sylwia.myapplication.CompList.MostActiveList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    EditText etGitHubUser; // This will be a reference to our GitHub username input.
    Button btnGetRepos;  // This is a reference to the "Get Repos" button.
    TextView tvRepoList;  // This will reference our repo list text box.
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    String baseUrl = "https://api.github.com/users/";  // This is the API base URL (GitHub API)
    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.
    List<String> repolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.etGitHubUser = (EditText) findViewById(R.id.et_github_user);  // Link our github user text box.
        this.btnGetRepos = (Button) findViewById(R.id.btn_get_repos);  // Link our clicky button.
        this.tvRepoList = (TextView) findViewById(R.id.tv_repo_list);  // Link our repository list text output box.
        this.tvRepoList.setMovementMethod(new ScrollingMovementMethod());  // This makes our text box scrollable, for those big GitHub contributors with lots of repos :)

        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.
        repolist = new ArrayList<>();
    }

    private void clearRepoList() {
        this.tvRepoList.setText("");
    }

    private void addToRepoList(String repoName, String lastUpdated) {
        String strRow = repoName + " / " + lastUpdated;
        String currentText = tvRepoList.getText().toString();

    }

    private void setRepoListText(String str) {
        // This is used for setting the text of our repo list box to a specific string.
        // We will use this to write a "No repos found" message if the user doens't have any.
        this.tvRepoList.setText(str);
    }
    private void getRepoList(String username) {
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).
//        this.url = this.baseUrl + username + "/repos";
   this.url = "https://api.iextrading.com/1.0/stock/market/list/mostactive";

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Check the length of our response (to see if the user has any repos)
                        if (response.length() > 0) {
                            // The user does have repos, so let's loop through them all.
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    // For each repo, add a new line to our repo list.
                                    JSONObject jsonObj = response.getJSONObject(i);
//                                    String repoName = jsonObj.get("name").toString();
//                                    String lastUpdated = jsonObj.get("updated_at").toString();
                                    String repoName = jsonObj.get("symbol").toString();
                                    String lastUpdated = jsonObj.get("companyName").toString();
                                    addToRepoList(repoName, lastUpdated);
                                    repolist.add(repoName);
                                    System.out.println(jsonObj);
                                    System.out.println(repoName);
                                    System.out.println(lastUpdated);
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                        } else {
                            setRepoListText("No repos found.");
                        }
                        String currentText = "";

                        String repoliststring;
                        for(String string1: repolist){
                            System.out.println("i");
                            currentText = tvRepoList.getText().toString();
                            tvRepoList.setText(currentText + "\n\n" + string1);
                        }
                        System.out.println("endend");

                    }


                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If there a HTTP error then add a note to our repo list.
                        setRepoListText("Error while calling REST API");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        requestQueue.add(arrReq);
    }

    public void getReposClicked(View v) {
        clearRepoList();
        getRepoList(etGitHubUser.getText().toString());



    }
    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, MostActiveList.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.app_menu, menu);
        return true;
    }
}
