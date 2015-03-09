package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



public class CommunityFragment extends Fragment implements View.OnClickListener {
	
	public CommunityFragment(){}

    Button b;
    EditText bdy;
    String s,branch,r,s1,query,year;
    String[] roll={"1","2","3","4","5","6","7","8","9","10",
                    "11","12","13","14","15","16","17","18","19","20",
            "21","22","23","24","25","26","27","28","29","30",
            "31","32","33","34","35","36","37","38","39","40",
            "41","42","43","44","45","46","47","48","49","50",
            "51","52","53","54","55","56","57","58","59","60",
            "61","62","63","64","65","66","67","68","69","70"};
    String[] batch={"2016","2017","2018","2019","2020","2021"};

    Spinner bspinner,rspinner,qspinner,batchspinner;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);


        bdy=(EditText)rootView.findViewById(R.id.body);
        b=(Button)rootView.findViewById(R.id.button);
        b.setOnClickListener(this);

        qspinner= (Spinner) rootView.findViewById(R.id.qspinner);
        ArrayAdapter<CharSequence> qadapter =ArrayAdapter.createFromResource(getActivity(),R.array.Query,android.R.layout.simple_spinner_item);
        qadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qspinner.setAdapter(qadapter);

        bspinner = (Spinner) rootView.findViewById(R.id.branchsp);
                                        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Branch, android.R.layout.simple_spinner_item);
                                            // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            // Apply the adapter to the spinner
        bspinner.setAdapter(adapter);

        batchspinner=(Spinner)rootView.findViewById(R.id.batchspin);
        ArrayAdapter<String> Yearadapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,batch);
        Yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batchspinner.setAdapter(Yearadapter);

        rspinner = (Spinner) rootView.findViewById(R.id.rollspinner);
                                                            // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (getActivity(),android.R.layout.simple_spinner_item, roll);

                                                            // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                // Apply the adapter to the spinner
        rspinner.setAdapter(adapter2);

        return rootView;
    }


    @Override
    public void onClick(View v) {

        branch=bspinner.getSelectedItem().toString();
        r= rspinner.getSelectedItem().toString();
        s1=bdy.getText().toString();
        query=qspinner.getSelectedItem().toString();
        year=batchspinner.getSelectedItem().toString();

        Intent send = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:" + Uri.encode("pc@xyzcollege.com") +
                "?subject=" + Uri.encode("Query Regarding "+query+" ( "+branch+"-"+year+" : "+r+" ) ") +
                "&body=" + Uri.encode(s1);
        Uri uri = Uri.parse(uriText);

        send.setData(uri);
        try {

            startActivity(Intent.createChooser(send, "Send mail..."));
            Toast.makeText(getActivity(), "Select preferred email client.", Toast.LENGTH_LONG).show();
        } catch (android.content.ActivityNotFoundException ex) {
             Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_LONG).show();
        }
    }
}
