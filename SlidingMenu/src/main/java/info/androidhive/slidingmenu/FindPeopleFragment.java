package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.slidingmenu.adater.CustomListAdapter;
import info.androidhive.slidingmenu.model.App;
import info.androidhive.slidingmenu.model.Company;

public class FindPeopleFragment extends Fragment {


    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Movies json url
    private static final String url = "http://careerwings.comxa.com/alljson.php";
    private ProgressDialog pDialog;
    private List<Company> companyList = new ArrayList<Company>();
    private ListView listView;
    private info.androidhive.slidingmenu.adater.CustomListAdapter adapter;





    public FindPeopleFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.activity_main_2, container, false);



        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new CustomListAdapter(getActivity(), companyList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        if (pDialog != null) {
                            pDialog.dismiss();
                            pDialog = null;
                        }

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Company comp = new Company();
                                comp.setTitle(obj.getString("title"));
                                comp.setThumbnailUrl(obj.getString("image"));
                                comp.setCategory(obj.getString("category"));
                                comp.setBranch(obj.getString("branch"));

                                // Genre is json array
								/*JSONArray genreArry = obj.getJSONArray("genre");
								ArrayList<String> genre = new ArrayList<String>();
								for (int j = 0; j < genreArry.length(); j++) {
									genre.add((String) genreArry.get(j));
								}*/
                                comp.setDescp(obj.getString("descp"));

                                // adding movie to movies array
                                companyList.add(comp);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (pDialog != null) {
                    pDialog.dismiss();
                    pDialog = null;
                }

            }
        });

        // Adding request to request queue
        App.getInstance().addToRequestQueue(movieReq);
         
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
