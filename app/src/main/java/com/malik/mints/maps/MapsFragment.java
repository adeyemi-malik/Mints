package com.malik.mints.maps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.malik.mints.R;
import com.malik.mints.animaldetails.AnimalDetailsActivity;
import com.malik.mints.models.animal.Animal;
import com.malik.mints.models.map.Location;
import com.malik.mints.util.ScrollChildSwipeRefreshLayout;

import java.util.List;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MapsFragment extends Fragment implements OnMapReadyCallback, MapsContract.View{

    private GoogleMap map;
    private MapsContract.Presenter presenter;

    private ScrollChildSwipeRefreshLayout swipeRefreshLayout;

    private View nomapsView;

    private ImageView noMapIcon;

    SupportMapFragment mapFragment;

    private TextView noMapMainView;

    private TextView filteringLabelView;

    public MapsFragment() {
        // Requires empty public constructor
    }

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(@NonNull MapsContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //presenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.map_fragment, container, false);

        // Set up  no maps view
        nomapsView = root.findViewById(R.id.no_maps);
        noMapIcon = root.findViewById(R.id.no_maps_Icon);
        noMapMainView = root.findViewById(R.id.no_maps_Main);

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Set up progress indicator
        swipeRefreshLayout =
                root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadMaps(false));

        return root;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }

        // Make sure setRefreshing() is called after the layout is done with everything else.
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(active));
    }

    @Override
    public void showMaps(List<Animal> animals) {
        map.clear();
        for(Animal animal : animals)
        {
            updateMap(animal);
        }
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        nomapsView.setVisibility(View.GONE);
    }

    private void updateMap(Animal animal)
    {
        Location location = animal.getLastLocation();
        String name = animal.getName();
        if(location != null)
        {
            double latitude = Double.parseDouble(location.getLatitude());
            double longitude = Double.parseDouble(location.getLongitude());
            LatLng latLng = new LatLng(latitude, longitude);
            map.addMarker(new MarkerOptions().position(latLng).title(name));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
        }
    }


    @Override
    public void showAddMap() {

    }

    @Override
    public void showNoMaps() {
        showNoMapsViews(
               "no animals map available",
                R.drawable.common_google_signin_btn_icon_dark,
                false
        );
    }

    private void showNoMapsViews(String mainText, int iconRes, boolean showAddView) {
        swipeRefreshLayout.setVisibility(View.GONE);
        nomapsView.setVisibility(View.VISIBLE);

        noMapMainView.setText(mainText);
        noMapIcon.setImageDrawable(getResources().getDrawable(iconRes));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showMapDetailsUi(int animalId) {
        Intent intent = new Intent(getContext(), MapsActivity.class);
        intent.putExtra(AnimalDetailsActivity.EXTRA_ANIMAL_ID, animalId);
        startActivity(intent);
    }

    @Override
    public void showLoadingMapsError() {
        showMessage("error load animals location");

    }


    @Override
    public void showAllFilterLabel() {

    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id.action_signout));
        //popup.getMenuInflater().inflate(R.menu.filter_tasks, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    /*case R.id.active:
                        mPresenter.setFiltering(TasksFilterType.ACTIVE_TASKS);
                        break;
                    case R.id.completed:
                        mPresenter.setFiltering(TasksFilterType.COMPLETED_TASKS);
                        break;
                    default:
                        mPresenter.setFiltering(TasksFilterType.ALL_TASKS);
                        break;*/
                }
                presenter.loadMaps(false);
                return true;
            }
        });

        popup.show();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng location = new LatLng(7.2299, 3.4387);
        map.addMarker(new MarkerOptions().position(location).title("Animal A"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));

    }
}
