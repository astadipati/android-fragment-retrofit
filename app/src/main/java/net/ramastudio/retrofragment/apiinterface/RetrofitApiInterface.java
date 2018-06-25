package net.ramastudio.retrofragment.apiinterface;

import net.ramastudio.retrofragment.model.ResponseMovie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApiInterface {

    @GET("movies.json")
    Call<List<ResponseMovie>> getMovie();

}
