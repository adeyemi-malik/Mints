package com.malik.mints.maps;

import android.support.annotation.NonNull;

import com.malik.mints.data.source.MapsRepository;
import com.malik.mints.models.animal.Animal;

import java.util.List;

import static android.support.v4.util.Preconditions.checkNotNull;


public class MapsPresenter implements MapsContract.Presenter
{
    private final MapsRepository mapsRepository;

    private final MapsContract.View mapsView;

    private MapsFilterType currentFiltering = MapsFilterType.ALL_MAPS;

    private boolean firstload = true;

    public MapsPresenter(@NonNull MapsRepository mapsRepository, @NonNull MapsContract.View mapsView)
    {
        this.mapsRepository = checkNotNull(mapsRepository, "mapsrepository cannot be null");
        this.mapsView = checkNotNull(mapsView, "mapsview cannot be null!");

        this.mapsView.setPresenter(this);
    }


    @Override
    public void loadMaps(boolean forceUpdate) {
        loadMaps(forceUpdate || firstload, true);
        firstload = false;
    }

    private void loadMaps(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mapsView.setLoadingIndicator(true);
        }

        List<Animal> maps = mapsRepository.getAnimalLocations();
        processMaps(maps);

        if (showLoadingUI) {
            mapsView.setLoadingIndicator(false);
        }

    }

    private void processMaps(List<Animal> maps) {
        if (maps.isEmpty()) {
            processEmptyMaps();
        }
        else
            {
            // Show the list of maps
            mapsView.showMaps(maps);
        }
    }

    private void processEmptyMaps() {
        mapsView.showNoMaps();

    }
    @Override
    public void addMap() {

    }

    @Override
    public void openMapDetails(@NonNull Animal requestedAnimal) {
        checkNotNull(requestedAnimal, "requestedmap cannot be null!");
        mapsView.showMapDetailsUi(requestedAnimal.id);
    }

    @Override
    public void clearMaps() {

    }

    @Override
    public void setFiltering(MapsFilterType requestType) {
        currentFiltering = requestType;
    }

    @Override
    public MapsFilterType getFiltering() {
        return currentFiltering;
    }

    @Override
    public void start() {
        loadMaps(false);
    }
}
