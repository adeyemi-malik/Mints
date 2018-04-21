package com.malik.mints.providers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.malik.mints.data.source.MapsRepository;

import static android.support.v4.util.Preconditions.checkNotNull;

public class Injection
{
    public static MapsRepository provideMapsRepository() {
        return MapsRepository.getInstance();
    }
}
