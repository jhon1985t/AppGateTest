package com.jhonjto.appgatetest;

import static com.jhonjto.appgatetest.utils.Constants.CREATE_EMAIL;
import static com.jhonjto.appgatetest.utils.Constants.CREATE_PASSWORD;
import static com.jhonjto.appgatetest.utils.Constants.LIST_INTENTS;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.jhonjto.appgatetest.Interceptors.DefaultServiceLocator;
import com.jhonjto.appgatetest.common.ViewModelFactory;
import com.jhonjto.appgatetest.databinding.ActivityMainBinding;
import com.jhonjto.appgatetest.viewmodels.ValidationsViewModel;
import com.jhonjto.data.preferences.PreferencesProvider;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String currentLocalTime;
    private ArrayList<String> validationList;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PreferencesProvider.getInstance(getApplicationContext());

        ViewModelFactory viewModelFactory = new ViewModelFactory(DefaultServiceLocator.getInstance(getApplication()));
        ValidationsViewModel validationsViewModel = viewModelFactory.create(ValidationsViewModel.class);

        validationList = new ArrayList<>();

        binding.btnCreateUser.setOnClickListener(v -> {
            if (isValidEmail(binding.etCreateEmail.getText()) && isValidPassword(binding.etCreatePassword.getText().toString())) {
                PreferencesProvider.putString(CREATE_EMAIL, binding.etCreateEmail.getText().toString());
                PreferencesProvider.putString(CREATE_PASSWORD, binding.etCreatePassword.getText().toString());
                binding.tvCreateNewUser.setVisibility(View.GONE);
                binding.etCreateEmail.setVisibility(View.GONE);
                binding.etCreatePassword.setVisibility(View.GONE);
                binding.btnCreateUser.setVisibility(View.GONE);
                binding.tvLoginUser.setVisibility(View.VISIBLE);
                binding.etLoginEmail.setVisibility(View.VISIBLE);
                binding.etLoginPassword.setVisibility(View.VISIBLE);
                binding.btnValidateUser.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(MainActivity.this, "El email o el password no son validos", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnValidateUser.setOnClickListener(v -> {
            Set<String> validationSet = PreferencesProvider.getList(LIST_INTENTS);

            if (validationSet == null) {
                Toast.makeText(this, "No hay registros guardados", Toast.LENGTH_SHORT).show();
            } else {
                validationList.addAll(validationSet);
            }

            binding.validationsListView.setVisibility(View.VISIBLE);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, validationList);

            binding.validationsListView.setAdapter(adapter);

            if (binding.etLoginEmail.getText().toString().matches(PreferencesProvider.getString(CREATE_EMAIL)) &&
                    binding.etLoginPassword.getText().toString().matches(PreferencesProvider.getString(CREATE_PASSWORD))) {
                extracted(validationsViewModel, "EXITOSO");
            } else {
                extracted(validationsViewModel, "DENEGADO");
            }
        });
    }

    private void extracted(ValidationsViewModel validationsViewModel, String state) {
        validationsViewModel.onSearchHistoryItemClick(latitude, longitude);
        validationsViewModel.getSearchHistoryItems().observe(MainActivity.this, event -> {
            String result = event.getContentIfNotHandled();
            try {
                JSONObject obj = new JSONObject(result);

                currentLocalTime = obj.getString("currentLocalTime");
            } catch (Throwable tx) {
                Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
            }
        });
        int sum = 0;
        sum += 1;

        String intents = String.valueOf(sum);

        ArrayList<String> saveIntents = new ArrayList<>();
        saveIntents.add("" + intents + " " + " " + currentLocalTime + " " + " " + state + " ");

        Set<String> set = new HashSet<>(saveIntents);
        PreferencesProvider.putList(LIST_INTENTS, set);
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean isValidPassword(String target) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        return target.matches(pattern);
    }

    private void enableLocationSettings() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }

    private void getCurrentLocation() {
        // Checking Location Permission
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            resultLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        });
    }

    private final ActivityResultLauncher<String> resultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            getCurrentLocation();
        } else {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
        }
    });


    @Override
    protected void onStart() {
        super.onStart();

        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {
            enableLocationSettings();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
