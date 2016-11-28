package br.com.reymond.lawrence.oqrola.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

import br.com.reymond.lawrence.oqrola.R;
import br.com.reymond.lawrence.oqrola.activity.MainActivity;
import br.com.reymond.lawrence.oqrola.activity.MapActivity;
import br.com.reymond.lawrence.oqrola.interfaces.RecyclerViewOnClickListenerHack;
import br.com.reymond.lawrence.oqrola.model.Party;
import br.com.reymond.lawrence.oqrola.util.PartyAdapter;


/**
 * Created by ian on 25-Nov-16.
 */

public class PartyFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private RecyclerView mRecyclerView;
    private List<Party> mList;
    private GoogleMap mMap;
    public static String LatLon;
    private static String PartyName;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_party, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();


                PartyAdapter adapter = (PartyAdapter) mRecyclerView.getAdapter();

                if (mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1) {
                    List<Party> listAux = ((MainActivity) getActivity()).getSetPartyList(10);

                    for (int i = 0; i < listAux.size(); i++) {
                        adapter.addListItem(listAux.get(i), mList.size());
                    }
                }
            }
        });
        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener( getActivity(), mRecyclerView, this ));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mList = ((MainActivity) getActivity()).getSetPartyList(10);
        PartyAdapter adapter = new PartyAdapter(getActivity(), mList);
        mRecyclerView.setAdapter( adapter );


        return view;
    }

    @Override
    public void onClickListener(View view, int position) {

        LatLon = mList.get(position).getLocal();
        PartyName = mList.get(position).getNome();

        ImageView imgMap = (ImageView) view.findViewById(R.id.iv_map);
        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map = new Intent(getActivity(), MapActivity.class);
                startActivity(map);
               // Uri gmmIntentUri = Uri.parse("geo:"+ LatLon);
                //Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
               // mapIntent.setPackage("com.google.android.apps.maps");
               // startActivity(mapIntent);


            }
        });

        ImageView imgFacebook = (ImageView) view.findViewById(R.id.iv_facebook);
        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_VIEW);
                it.setData(Uri.parse("http://www.facebook.com"));
                startActivity(it);
            }
        });

        ImageView imgYoutube = (ImageView) view.findViewById(R.id.iv_youtube);
        imgYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_VIEW);
                it.setData(Uri.parse("http://www.youtube.com"));
                startActivity(it);
            }
        });

       // Toast.makeText(getActivity(), "onClickListener(): "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
       // Toast.makeText(getActivity(), "onLongPressClickListener(): "+position, Toast.LENGTH_SHORT).show();
    }

    public static LatLng getPlace() {

        String[] latlong =  LatLon.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        LatLng location = new LatLng(latitude, longitude);
        return location;

    }

    public static String getParty() {
        return PartyName;
    }


    private static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
        private Context mContext;
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

        public RecyclerViewTouchListener(Context c, final RecyclerView rv, RecyclerViewOnClickListenerHack rvoclh){
            mContext = c;
            mRecyclerViewOnClickListenerHack = rvoclh;

            mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerHack != null){
                        mRecyclerViewOnClickListenerHack.onLongPressClickListener(cv,
                                rv.getChildPosition(cv) );
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerHack != null){
                        mRecyclerViewOnClickListenerHack.onClickListener(cv,
                                rv.getChildPosition(cv) );
                    }

                    return(true);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
