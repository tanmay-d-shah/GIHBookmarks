package com.tds.gihbookmarks.NavigationMenuFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tds.gihbookmarks.R;

/*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ImageView editimg,icon,plus;
    public EditText name,email,password,phone,add,sem,branch,eno,clg,des,city;
    public TextView save;



    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editimg=(ImageView)view.findViewById(R.id.edit);
        name=(EditText)view.findViewById(R.id.name);
        email=(EditText)view.findViewById(R.id.email);
        password=(EditText)view.findViewById(R.id.password);
        phone=(EditText)view.findViewById(R.id.phone_number);
        add=(EditText)view.findViewById(R.id.address);
        sem=(EditText)view.findViewById(R.id.semester);
        branch=(EditText)view.findViewById(R.id.branch);
        eno=(EditText)view.findViewById(R.id.enrollment);
        clg=(EditText)view.findViewById(R.id.college);
        des=(EditText)view.findViewById(R.id.designation);
        city=(EditText)view.findViewById(R.id.city);
        icon=(ImageView)view.findViewById(R.id.icon);
        plus=(ImageView)view.findViewById(R.id.plus);
        save=(TextView)view.findViewById(R.id.save);


        editimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name.setEnabled(true);
                email.setEnabled(true);
                password.setEnabled(true);
                phone.setEnabled(true);
                add.setEnabled(true);
                sem.setEnabled(true);
                branch.setEnabled(true);
                eno.setEnabled(true);
                clg.setEnabled(true);
                des.setEnabled(true);
                city.setEnabled(true);
                icon.setEnabled(true);
                plus.setEnabled(true);
                save.setEnabled(true);


            }
        });

        return  view;
    }

}
