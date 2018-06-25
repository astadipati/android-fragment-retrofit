package net.ramastudio.retrofragment.fragments;


import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.ramastudio.retrofragment.R;
import net.ramastudio.retrofragment.adapters.MovieAdapter;
import net.ramastudio.retrofragment.apiinterface.RetrofitApiInterface;
import net.ramastudio.retrofragment.model.ResponseMovie;
import net.ramastudio.retrofragment.utils.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    MovieAdapter movieAdapter;
    RecyclerView recyclerView;
    List<ResponseMovie> movieList = new ArrayList<ResponseMovie>();
    private OnFragmentInteractionListener listener;
//    harus static supaya dapat pakai newsInstance
    public static HomeFragment newInstance() {
        // Required empty public constructor
        return new HomeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvHome);

        movieAdapter = new MovieAdapter(movieList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(movieAdapter);

        getMovieList();

        return view;
    }
    public void getMovieList() {
        RetrofitApiInterface apiInterface = RetroClient.getClient().create(RetrofitApiInterface.class);
        Call<List<ResponseMovie>> call = apiInterface.getMovie();
        call.enqueue(new Callback<List<ResponseMovie>>() {
            @Override
            public void onResponse(Call<List<ResponseMovie>> call, Response<List<ResponseMovie>> response) {

                if (response==null){
                    Toast.makeText(getActivity(), "Somthing Went Wrong...!!", Toast.LENGTH_SHORT).show();
                }else{
                    for (ResponseMovie movie:response.body()){
                        movieList.add(movie);
                    }
                    Log.i("RESPONSE: ", ""+response.toString());
                }
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ResponseMovie>> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public interface OnFragmentInteractionListener {
    }

}
