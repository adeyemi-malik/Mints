package com.malik.mints.maps;

import android.support.annotation.NonNull;

import com.malik.mints.BasePresenter;
import com.malik.mints.BaseView;
import com.malik.mints.models.animal.Animal;

import java.util.List;

public class MapsContract {
    interface View extends BaseView<Presenter>
    {
        void setLoadingIndicator(boolean active);

        void showMaps(List<Animal> animals);

        void showAddMap();

        void showMapDetailsUi(int animalId);

        void showLoadingMapsError();

        void showNoMaps();

        void showAllFilterLabel();

        boolean isActive();

        void showFilteringPopUpMenu();

    }

    interface Presenter extends BasePresenter
    {
        void loadMaps(boolean forceUpdate);

        void addMap();

        void openMapDetails(@NonNull Animal requestedAnimal);

        void clearMaps();

        void setFiltering(MapsFilterType requestType);

        MapsFilterType getFiltering();
    }
}
