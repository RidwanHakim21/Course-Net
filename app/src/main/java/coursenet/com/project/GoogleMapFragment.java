package coursenet.com.project;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {


    public GoogleMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View b = inflater.inflate(R.layout.fragment_google_map, container, false);

        // ambil fragment dari xml
        SupportMapFragment peta = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.peta);

        // load petanya
        peta.getMapAsync(GoogleMapFragment.this);

        return b;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setTrafficEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(getActivity(), "Plese Give Your Permission to this App To Access Your Location", Toast.LENGTH_LONG).show();

            return;
        }

        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setMyLocationEnabled(true);

        double latitude = -6.2297333;
        double longitude = 106.9941355;

        LatLng koordinat = new LatLng(latitude, longitude);
        MarkerOptions titik = new MarkerOptions();
        titik.position(koordinat);
        titik.title("Mall SMS");

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(koordinat, 17.0f));

        googleMap.addMarker(titik);
    }
}
