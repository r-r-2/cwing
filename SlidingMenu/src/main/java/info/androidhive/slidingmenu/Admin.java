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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

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

public class Admin extends Fragment {
	
	public Admin(){}
	Button b;
    EditText pass,ev;

    String datef,event,password;
    Spinner dd,mm,yy;
    Context context;
    String[] date={"01","02","03","04","05","06","07","08","09","10",
            "11","12","13","14","15","16","17","18","19","20",
            "21","22","23","24","25","26","27","28","29","30",
            "31"};
    String[] month={"01","02","03","04","05","06","07","08","09","10","11","12"};
    String[] year={"2015","2016","2017","2018","2019","2020","2021"};

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_pages, container, false);
        b=(Button)rootView.findViewById(R.id.submit);
        pass=(EditText)rootView.findViewById(R.id.password);
        ev=(EditText)rootView.findViewById(R.id.event);

        dd=(Spinner)rootView.findViewById(R.id.datesp);
        ArrayAdapter<String> Dateadapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,date);
        Dateadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dd.setAdapter(Dateadapter);

        mm=(Spinner)rootView.findViewById(R.id.monthsp);
        ArrayAdapter<String> Monthadapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,month);
        Monthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mm.setAdapter(Monthadapter);

        yy=(Spinner)rootView.findViewById(R.id.yearsp);
        ArrayAdapter<String> Yearadapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,year);
        Yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yy.setAdapter(Yearadapter);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event=ev.toString();
                password=pass.toString();
                datef=yy.getSelectedItem().toString()+"-"+mm.getSelectedItem().toString()+"-"+dd.getSelectedItem().toString();
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
                Log.d("PreExecute "+datef+" "+event+" "+password,"a");

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
            nameValuePair.add(new BasicNameValuePair("date", datef));
            nameValuePair.add(new BasicNameValuePair("event", event));
            nameValuePair.add(new BasicNameValuePair("pass",password));
            Log.d("FIRST "+datef+" "+event+" "+password," ");
            // Url Encoding the POST parameters
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                Log.d("first try "+datef+" "+event+" "+password," ");
            } catch (UnsupportedEncodingException e) {
                // writing error to Log
                e.printStackTrace();
            }

            // Making HTTP Request
            try {
                HttpResponse response = httpClient.execute(httpPost);

                // writing response to log
                Log.d(" On do in background "+datef+" "+event+" "+password," ");
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
            Log.d(" onPostexecute : "+datef+" "+event+" "+password,"a");
        }
    }
    public boolean isOnline()
    {
        ConnectivityManager cm =(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }




}
