package com.example.nutrinest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class LogoutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        Button btnLogoutFinal = view.findViewById(R.id.btnLogoutFinal);

        btnLogoutFinal.setOnClickListener(v -> {

            // 🔥 START LOGOUT SERVICE
            Intent intent = new Intent(getActivity(), LogoutService.class);
            requireActivity().startService(intent);

        });

        return view;
    }
}