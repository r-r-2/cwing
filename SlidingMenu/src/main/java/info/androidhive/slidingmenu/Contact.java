package info.androidhive.slidingmenu;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class Contact extends Fragment {
	
	public Contact(){}
    ListView list;
    String[] name = {
            "Mr. Kevin Singh",
            "Mr. Mohan Verma",
            "Mr. Abdul Kaif ",
            "Miss. Zoey Mathew",
            "Mr. Akshay Rawat",
            "Miss. Nandini Goyal ",
            "Mr. Manish Gupta"
    } ;
    String[] role={"Chairperson","Secretary","CSE Coordinator","EEE Coordinator","ECE Coordinator","EBE Coordinator","Staff InCharge"};
    String[] phone ={

            "+918547735952",
            "+919037735952",
            "+918547735952",
            "+919037735952",
            "+918547735952",
            "+919037735952",
            "+918547735952"
    };
    String[] email ={

            "pc.chair@xyz.com",
            "pc.sec@xyz.com",
            "pc.cse@xyz.com",
            "pc.eee@xyz.com",
            "pc.ece@xyz.com",
            "pc.ebe@xyz.com",
            "pc.incharge@xyz.com"
    };
    Integer[] call={R.drawable.ic_call};
    /*Integer[] imageId = {
            R.drawable.ic_contact,
            R.drawable.ic_contact,
            R.drawable.ic_contact,
            R.drawable.ic_contact,
            R.drawable.ic_contact,
            R.drawable.ic_contact,
            R.drawable.ic_contact
    }; */
    Integer[] mail={R.drawable.mailto};
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.contactmain, container, false);
        CustomList adapter = new
                CustomList(getActivity(),name,role, call, phone,mail,email);
        list=(ListView)rootView.findViewById(R.id.list);
        list.setAdapter(adapter);
       /* list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "Long Press to Call " +web[+ position], Toast.LENGTH_SHORT).show();
            }
        });
        */

         /*   list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {
                    // TODO Auto-generated method stub

                    Toast.makeText(getActivity(), "Calling " + web[+pos] + " at " + phone[+pos], Toast.LENGTH_LONG).show();
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phone[+pos]));//change the number
                    startActivity(callIntent);

                    return true;
                }
            });
            */

        Toast.makeText(getActivity(),"Long Press icons to call or mail",Toast.LENGTH_SHORT).show();
        return rootView;
    }



    class CustomList extends ArrayAdapter<String>
    {
        private final Activity context;
        private final String[] name;
        private final String[] role;
        private final String[] phone;
        private final Integer[] call;
        private final Integer[] mail;
        private final String[] email;

        public CustomList(Activity context,
                          String[] name,String[] role, Integer[] call, String[] phone,Integer[] mail,String[] email) {
            super(context, R.layout.contact, name);
            this.context = context;
            this.name = name;
            this.role=role;
            this.call = call;
            this.phone = phone;
            this.mail=mail;
            this.email=email;
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.contact, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
            TextView roleTitle=(TextView) rowView.findViewById(R.id.role);
            TextView phNumber =(TextView) rowView.findViewById(R.id.ph);
            TextView Email=(TextView)rowView.findViewById(R.id.email);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            ImageView mailView=(ImageView)rowView.findViewById(R.id.img2);

            txtTitle.setText(name[position]);
            roleTitle.setText(role[position]);
            imageView.setImageResource(call[0]);
            phNumber.setText(phone[position]);
            Email.setText(email[position]);
            mailView.setImageResource(mail[0]);
            imageView.setTag(position);
            mailView.setTag(position);


            imageView.setOnLongClickListener(new View.OnLongClickListener() {


                @Override
                public boolean onLongClick(View v) {
                    int position = (Integer) v.getTag();

                    Toast.makeText(getActivity(), "Calling " + name[+position] + " at " + phone[+position], Toast.LENGTH_LONG).show();
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phone[+position]));//change the number
                    startActivity(callIntent);

                    return false;
                }
            });
            mailView.setOnLongClickListener(new View.OnLongClickListener() {


                @Override
                public boolean onLongClick(View v) {
                    int position = (Integer) v.getTag();

                    Intent send = new Intent(Intent.ACTION_SENDTO);
                    String uriText = "mailto:" + Uri.encode(email[position]) +
                            "?subject=" + Uri.encode("") +
                            "&body=" + Uri.encode("");
                    Uri uri = Uri.parse(uriText);

                    send.setData(uri);
                    try {

                        startActivity(Intent.createChooser(send, "Send mail..."));
                        Toast.makeText(getActivity(), "Select preferred email client.", Toast.LENGTH_LONG).show();
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_LONG).show();
                    }

                    return false;
                }
            });
            return rowView;

        }


    }


}
