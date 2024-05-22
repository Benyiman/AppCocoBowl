package cl.isisur.appcocobowl;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cl.isisur.appcocobowl.Clases.Bowl;

public class MenuPedidos extends AppCompatActivity {
    TextView tvLatitud, tvLongitud, tvDireccion;
    Button btVolver, bTContinuar, btMenu, btSeguir;
    private ListView lvBowlsSeleccionados;
    private ArrayAdapter<Bowl> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_pedidos);

        tvLatitud = (TextView) findViewById(R.id.tvlatitud);
        tvDireccion = (TextView) findViewById(R.id.tvdireccion);
        tvLongitud = (TextView) findViewById(R.id.tvlongitud);

        lvBowlsSeleccionados = findViewById(R.id.lvBowlsSeleccionados);

        // Obtener los extras del Intent
        List<String> nombresBowlsSeleccionados = getIntent().getStringArrayListExtra("nombresBowlsSeleccionados");
        List<Integer> cantidadesBowlsSeleccionados = getIntent().getIntegerArrayListExtra("cantidadesBowlsSeleccionados");

        // Crear un ArrayAdapter para mostrar los nombres de los bowls seleccionados en el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombresBowlsSeleccionados);

        // Establecer el ArrayAdapter en el ListView
        lvBowlsSeleccionados.setAdapter(adapter);

        btVolver = findViewById(R.id.Volver);
        btMenu = findViewById(R.id.Menu);


        Intent intent1=new Intent(this, MenuFormulario1.class);
        Intent intent2=new Intent(this, MainActivity.class);



        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent1);
            }
        });
        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Menu), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }


    }
    private void locationStart(){
        LocationManager mlocManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMenuPedidos(this);

        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!gpsEnabled){
            Intent intentgps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intentgps);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,(LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,(LocationListener) Local);
        tvLatitud.setText("Localización GPS");
        tvDireccion.setText("");

    }

    public void setLocation(Location loc) {

        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    tvDireccion.setText(DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class Localizacion implements LocationListener {
        MenuPedidos MenuPedidos;
                

        public MenuPedidos getMenuPedidos(cl.isisur.appcocobowl.MenuPedidos menuPedidos) {
            return MenuPedidos;
        }

        public void setMenuPedidos(MenuPedidos menuPedidos) {
            this.MenuPedidos = menuPedidos;
        }
        @Override
        public void onLocationChanged(Location loc) {
            loc.getLatitude();
            loc.getLongitude();
            tvLatitud.setText(String.valueOf(loc.getLatitude()));
            tvLongitud.setText(String.valueOf(loc.getLongitude()));
            this.MenuPedidos.setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider){
            tvLatitud.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider){

            tvLatitud.setText("GPS Activado");

        }
        @Override
        public void onStatusChanged(String provider, int status,Bundle extras){
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }

        }



    }
}