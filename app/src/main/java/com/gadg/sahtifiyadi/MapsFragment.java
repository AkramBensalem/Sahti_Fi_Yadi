package com.gadg.sahtifiyadi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.getCity;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.getLaltitude;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.getLongitude;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            double lat = Double.parseDouble(getLaltitude(getContext()));
            double Longitude = Double.parseDouble(getLongitude(getContext()));

            String cityName = getCity(getContext());

            LatLng sydney = new LatLng(33.692064, 1.016893);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Adress "+cityName));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Toast.makeText(getActivity(),"l'établissement de",Toast.LENGTH_LONG).show();
                }
            });

     /*       googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    double lat = latLng.latitude;
                    double lng = latLng.longitude;
                    Log.d("MapAkramTest","lat : "+lat+"/n long : "+lng);
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("Ajouter votre établisment"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                }
            });

      */
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}