package com.tds.gihbookmarks.NavigationMenuFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.tds.gihbookmarks.PageAdapter_RequestedItems;
import com.tds.gihbookmarks.R;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link RequestedItemsFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link RequestedItemsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class RequestedItemsFragment extends Fragment {


    private TabLayout tabLayout;
    private TabItem yourRequests, buyerRequests;
    private ViewPager viewPager;
    private PageAdapter_RequestedItems pageAdapter;
    private View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public RequestedItemsFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment RequestedItemsFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static RequestedItemsFragment newInstance(String param1, String param2) {
//        RequestedItemsFragment fragment = new RequestedItemsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    //    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("View created", "onCreateView: ");
        view = inflater.inflate(R.layout.fragment_requested_items, container, false);
        Log.d("View created", "onCreateView: ");

        tabLayout = view.findViewById(R.id.requestedItem_tabLayout);

        yourRequests = view.findViewById(R.id.your_requested_item);
        buyerRequests = view.findViewById(R.id.buyer_requested_item);

        viewPager = view.findViewById(R.id.request_item_viewpager);

        pageAdapter = new PageAdapter_RequestedItems(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        Log.d("Tab check", "onCreateView: " + tabLayout.getTabAt(0).getText());
        tabLayout.setupWithViewPager(viewPager);


        try {
            tabLayout.getTabAt(0).setText("Your Requested Items");
            tabLayout.getTabAt(1).setText("Buyer Requested Items");
            tabLayout.getTabAt(2).setText("Received Items");
            Log.d("Debugging", "onCreateView: Tabtext set");

        } catch (Exception e) {
            Log.d("Error in tab layout", e.toString());
        }
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
