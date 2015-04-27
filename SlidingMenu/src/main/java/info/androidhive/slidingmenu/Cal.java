package info.androidhive.slidingmenu;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.telerik.android.common.Function;
import com.telerik.android.common.Util;
import com.telerik.widget.calendar.CalendarSelectionMode;
import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.decorations.CellDecorator;
import com.telerik.widget.calendar.decorations.CircularCellDecorator;
import com.telerik.widget.calendar.events.Event;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Cal extends Fragment {

    RadCalendarView calendarve;
    Context context;
    String s;


    public Cal(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        context=getActivity();

        RadCalendarView calendarView = (RadCalendarView)rootView.findViewById(R.id.calendarView);
        calendarve=calendarView;
        calendarView.setSelectionMode(CalendarSelectionMode.Single);
        CellDecorator decorator = new CircularCellDecorator(calendarView);
        decorator.setColor(Color.parseColor("#2c56ed"));
        decorator.setStrokeWidth(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 2));
        decorator.setScale(.75f);
        decorator.setStroked(true);
        calendarView.setCellDecorator(decorator);

        final java.util.Calendar cal = java.util.Calendar.getInstance();

        calendarView.setDateToColor(new Function<Long, Integer>() {
            @Override
            public Integer apply(Long aLong) {
                cal.setTimeInMillis(aLong);
               // if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                 //   return Color.RED;
                //}
                return null;
            }
        });

        calendarView.setOnSelectedDatesChangedListener(new RadCalendarView.
                OnSelectedDatesChangedListener() {
            @Override
            public void onSelectedDatesChanged(RadCalendarView.SelectionContext context) {
                Toast.makeText(getActivity().getApplicationContext(),
                        String.format("%tF", context.newSelection().get(0)),
                        Toast.LENGTH_SHORT).show();
                s=String.format("%tF", context.newSelection().get(0));
                Intent i = new Intent(getActivity().getApplicationContext(), event.class);
                i.putExtra("Date",s);
                startActivity(i);

            }
        });

        if(!isOnline()) {

            Log.d("Reading(For Markers): ", "Reading databse..(For Markers)");
            DatabaseHandler dba=new DatabaseHandler(getActivity().getApplicationContext());
            List<local> contacts = dba.getAlllocals();
            List<Event> events = new ArrayList<Event>();


            for (local cn : contacts) {
                String log = "Date: " + cn.getName() + " ,Event: " + cn.getPhoneNumber();
                // Writing locals to log
                Log.d("Calender Log", log);
                String d=cn.getName();
                {
                    int y=0,m=0, da=0;

                    for(int k=0;k<10;k++)
                    {
                        if (k < 4)
                            y = y*10 + Integer.parseInt(Character.toString(d.charAt(k)));
                        if (k > 4&&k<7)
                            m= m*10+Integer.parseInt(Character.toString(d.charAt(k)));
                        if (k > 7&&k<10)
                            da=da*10+Integer.parseInt(Character.toString(d.charAt(k)));

                    }

                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    calendar.set(y,m-1,da);
                    calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
                    calendar.set(java.util.Calendar.MINUTE, 0);
                    calendar.set(java.util.Calendar.SECOND, 0);
                    calendar.set(java.util.Calendar.MILLISECOND, 0);
                    long eventStart = calendar.getTimeInMillis();

                    calendar.add(java.util.Calendar.HOUR, 23);
                    long eventEnd = calendar.getTimeInMillis();

                    Event event = new Event("", eventStart, eventEnd);


                    //    event.setEventColor(18);
                    events.add(event);

                }


            }
            calendarView.getEventAdapter().setEvents(events);

            // get a Calendar object with current time
            java.util.Calendar cale = java.util.Calendar.getInstance();
            // add 5 minutes to the calendar object
            cale.add(java.util.Calendar.SECOND, 4);

        }

        else
            new read().execute();
        return rootView;
    }
    class read extends AsyncTask<Void,Void,String>
    {        DatabaseHandler db = new DatabaseHandler(context);
        List<Event> events = new ArrayList<Event>();

        String e,d;
        private ProgressDialog pdia;


        @Override
        protected void onPreExecute()
        {
            if(isOnline()) {
                pdia = new ProgressDialog(context);
                pdia.setMessage("Getting New Events");
                pdia.show();
                pdia.show();
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                sqLiteDatabase.delete("events", null, null);
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdia.dismiss();

            // get a Calendar object with current time
            java.util.Calendar cal = java.util.Calendar.getInstance();
            // add 5 minutes to the calendar object
            cal.add(java.util.Calendar.SECOND, 4);


            if(isOnline()) {
                Log.d("Reading: ", "Reading databse..");
                List<local> contacts = db.getAlllocals();

                for (local cn : contacts) {
                    String log = "Date: " + cn.getName() + " ,Event: " + cn.getPhoneNumber();
                    // Writing locals to log
                    Log.d("Calender Log", log);
                    String d=cn.getName();
                    {
                        int y=0,m=0, da=0;

                        for(int k=0;k<10;k++)
                        {
                            if (k < 4)
                                y = y*10 + Integer.parseInt(Character.toString(d.charAt(k)));
                            if (k > 4&&k<7)
                                m= m*10+Integer.parseInt(Character.toString(d.charAt(k)));
                            if (k > 7&&k<10)
                                da=da*10+Integer.parseInt(Character.toString(d.charAt(k)));

                        }

                        java.util.Calendar calendar = java.util.Calendar.getInstance();
                        calendar.set(y,m-1,da);
                        long eventStart = calendar.getTimeInMillis();

                        calendar.add(java.util.Calendar.HOUR, 1);
                        long eventEnd = calendar.getTimeInMillis();

                        Event event = new Event("", eventStart, eventEnd);


                        //    event.setEventColor(18);
                        events.add(event);

                    }


                }

                calendarve.getEventAdapter().setEvents(events);
            }}

        @Override
        protected String doInBackground(Void... params) {

            if(isOnline()) {
                localparse a = new localparse();
                try {
                    JSONArray j = a.getJSONfromURL("http://careerwings.comxa.com/getallevents.php");
                    for (int i = 0; i < j.length(); i++) {
                        e = j.getJSONObject(i).getString("event");
                        d = j.getJSONObject(i).getString("date");
                        Log.d("Insert: ", "Inserting .." + d + " " + e);
                        db.addlocal(new local(d, e));
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            return null;
        }
    }
    public boolean isOnline()
    {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
