package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PagesFragment extends Fragment {
	
	public PagesFragment(){}
	Button b;
    EditText pass,ev;
    DatePicker datePicker;
    String dateFormat,event,password;
    Context context;


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_pages, container, false);
        b=(Button)rootView.findViewById(R.id.submit);
        pass=(EditText)rootView.findViewById(R.id.password);
        ev=(EditText)rootView.findViewById(R.id.event);
        datePicker=(DatePicker)rootView.findViewById(R.id.datePick);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

        dateFormat = dateformat.format(new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth()));
        event=ev.toString();
        password=pass.toString();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new send().execute();
            }
        });
        return rootView;
    }

    public class send extends AsyncTask<Void,Void,String>
    {
        ProgressDialog pdia;
        @Override
        protected void onPreExecute() {
            if(isOnline()) {
                super.onPreExecute();

                pdia = new ProgressDialog(getActivity());
                Log.d("PreExecute "+dateFormat+" "+event+" "+password,"a");

                pdia.setMessage("Posting");
                pdia.show();

            }

        }

        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            // Creating HTTP Post
            HttpPost httpPost = new HttpPost(
                    "http://careerwings.comxa.com/addevent.php");

            // Building post parameters
            // key and value pair
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
            nameValuePair.add(new BasicNameValuePair("date", dateFormat));
            nameValuePair.add(new BasicNameValuePair("event", event));
            nameValuePair.add(new BasicNameValuePair("pass",password));
            Log.d("FIRST "+dateFormat+" "+event+" "+password," ");
            // Url Encoding the POST parameters
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                Log.d("first try "+dateFormat+" "+event+" "+password," ");
            } catch (UnsupportedEncodingException e) {
                // writing error to Log
                e.printStackTrace();
            }

            // Making HTTP Request
            try {
                HttpResponse response = httpClient.execute(httpPost);

                // writing response to log
                Log.d(" On do in background "+dateFormat+" "+event+" "+password," ");
                Log.d(" Http Response: ", response.toString());
            } catch (ClientProtocolException e) {
                // writing exception to log
                e.printStackTrace();
            } catch (IOException e) {
                // writing exception to log
                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdia.dismiss();
            Log.d(" onPostexecute : "+dateFormat+" "+event+" "+password,"a");
        }
    }
    public boolean isOnline()
    {
        ConnectivityManager cm =(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }




}
