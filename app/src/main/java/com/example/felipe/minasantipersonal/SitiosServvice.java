package com.example.felipe.minasantipersonal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by felipe on 17/10/17.
 */

public interface SitiosServvice {

    @GET ("644k-i2xw.json")
    Call <List<Minas>> obtenerListadeSitios();

}
