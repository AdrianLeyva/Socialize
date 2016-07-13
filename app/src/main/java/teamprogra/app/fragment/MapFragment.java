package teamprogra.app.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import teamprogra.app.domain.Event;
import teamprogra.app.socialize.socialize.R;
import teamprogra.app.util.GpsLocation;
import teamprogra.app.util.Util;


public class MapFragment extends Fragment implements OnMapReadyCallback,View.OnClickListener {

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 11;

    private com.google.android.gms.maps.MapFragment mapFragment;
    private OnFragmentInteractionListener mListener;
    private GpsLocation gpsLocation;
    private Location location;
    private GoogleMap googleMap;
    private Marker marker;
    private MarkerOptions markerOptions;
    private Event event;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragmentNewEventFirstStep fragmentNewEventFirstStep;
    private Button buttonGoBack;
    private Button buttonNext;
    private boolean markerFlag;


    public MapFragment() {
        // Required empty public constructor
    }


    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        markerOptions = new MarkerOptions();
        gpsLocation = new GpsLocation();
        fragmentManager = getActivity().getSupportFragmentManager();
        //Verify if gps is online....
        if (!gpsLocation.isGpsOnline(getActivity())) {
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (com.google.android.gms.maps.MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.map);
        buttonGoBack = (Button)view.findViewById(R.id.button_fragmentMapGoBack);
        buttonNext = (Button)view.findViewById(R.id.button_fragmentMapNext);
        buttonGoBack.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        mapFragment.getMapAsync(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        this.googleMap = googleMap;
        //show MyLocationEnabled
        showButtonMyLocation();

        //configure google maps settings...
        UiSettings setting = googleMap.getUiSettings();
        setting.setZoomGesturesEnabled(true);
        setting.setCompassEnabled(true);

        //configure map type...
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //set onClickListener...
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(marker == null){
                    generateMarker(latLng);
                }else{
                    marker.remove();
                    generateMarker(latLng);
                }

            }
        });
    }

    public void showButtonMyLocation(){
        //Set actual position button.....
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            googleMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public void generateMarker(LatLng latLng){
        marker = googleMap.addMarker(markerOptions
                .position(new LatLng(latLng.latitude, latLng.longitude))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .title("Aquí será mi evento")
                .snippet("Da click en continuar.")
                .draggable(true)
                .alpha(0.9f));
        marker.showInfoWindow();
        markerFlag = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_fragmentMapGoBack:

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentNewEventFirstStep = FragmentNewEventFirstStep.newInstance();
                fragmentNewEventFirstStep.setEvent(event);
                fragmentTransaction.replace(R.id.container,fragmentNewEventFirstStep);
                fragmentTransaction.commit();
                break;

            case R.id.button_fragmentMapNext:
                break;
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG","si entra");
                            showButtonMyLocation();
                }
        }
    }

    public void setEvent(Event event){
        this.event = event;
    }

    public Event getEvent(){
        return this.event;
    }
}
