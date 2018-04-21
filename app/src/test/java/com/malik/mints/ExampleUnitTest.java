package com.malik.mints;

import com.malik.mints.data.source.AnimalsRepository;
import com.malik.mints.data.source.MapsRepository;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void animalocations() throws Exception {
        assertEquals(false, new MapsRepository().getAnimalLocations().isEmpty());
    }
}