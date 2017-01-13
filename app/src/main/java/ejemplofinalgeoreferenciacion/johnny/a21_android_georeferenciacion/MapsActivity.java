package ejemplofinalgeoreferenciacion.johnny.a21_android_georeferenciacion;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {



    /* 1. Cuando se crea el proyecto con la activity de mapa, recordar acceder al primer enlace que
          se genera automaticamente en el xml que aparece abierto (activity_maps.xml), ahi se crea la
          key sola, obteniendo de manera automatica el nombre del paquete del proyecto, y en dicho
          XML se pega la key

       2. En el GRADLE (Archivo build.gradle(Module:app)) se debe bajar la version del google play
          service a la 6. Verificar el codigo de este proyecto. Esto se hace porque las nuevas
          versiones ya no ofrecen opciones muy utiles que si ofrece esta version :)

       3. Añadir los permisos necesarios en el manifiest.

       4. Mirar este MAIN ACTIVITY Y HAPPY CODING!!
      */


    private final LatLng EAM = new LatLng(4.541763, -75.663464); // posicion de
    // la EAM

    private GoogleMap mapa;

    private EditText txtDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtDireccion = (EditText) findViewById(R.id.txtDireccion);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;

        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE); // tipo de mapa (normal,
        // satélite, hibrido o
        // relieve)
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(EAM, 15));// Ubica el
        // mapa en
        // la EAM,
        // con zoom
        // de 15 ( 2
        // (continente)
        // hasta 21
        // (calle))


        mapa.setMyLocationEnabled(true);// activa la visualización de la



        mapa.getUiSettings().setZoomControlsEnabled(false);// se desactiva los
        // botones de zoom
        mapa.getUiSettings().setCompassEnabled(true);// visualizar la brujula

        mapa.addMarker(new MarkerOptions().position(EAM)// posicion del marcador
                .title("EAM")// titulo del marcador
                .snippet("Escuela de administracion y mercadotecnia")
                // descripcion
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))// icono
                // marcador
                .anchor(0.5f, 0.5f));// tamaño

        mapa.setOnMapClickListener(this);// se asigna el lister asignado al
        // metodo onMapClick del mapa
    }

    @Override
    public void onMapClick(LatLng latLng) {
        // Recibe por parametro la posicion exacta donde se pulso y añade un
        // marcador
        mapa.addMarker(new MarkerOptions().position(latLng).icon(
                BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
    }



    public void dibujarLineas(View view) {
        PolygonOptions rectangulo = new PolygonOptions().add(
                new LatLng(45.0,-12.0),
                new LatLng(45.0, 5.0),
                new LatLng(34.5, 5.0),
                new LatLng(34.5, -12.0));



        rectangulo.strokeWidth(8);
        rectangulo.strokeColor(Color.RED);

        mapa.addPolygon(rectangulo);

        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.0, -12.0), 5));
        // se actualiza la posicion en pantalla
    }






    public void moveCamera(View view) {
        mapa.moveCamera(CameraUpdateFactory.newLatLng(EAM));// se actualiza la
        // posicion en la
        // pantalla
    }

    public void mostrarTerreno(View view) {
        mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    public void mostrarHibrido(View view) {
        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public void mostrarNormal(View view) {
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void miPosicionActual(View view) {

        if (mapa.getMyLocation() != null) {

            double latitudActual = mapa.getMyLocation().getLatitude(); // determina
            // la
            // latitud
            // actual
            double longitudActual = mapa.getMyLocation().getLongitude();// determina
            // la
            // longitud
            // actual

            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                    latitudActual, longitudActual), 15));// actualiza la
            // posicion del mapa
            // animadamente
        }
    }



    public void addMarker(View view) {

        double latitudMapa = mapa.getCameraPosition().target.latitude;// latitud
        // del
        // punto
        // central
        // que
        // se
        // este
        // visualizando
        double altitudMapa = mapa.getCameraPosition().target.longitude; // altitud
        // del
        // punto
        // central
        // que
        // se
        // este
        // visualizando

        mapa.addMarker(new MarkerOptions().position(new LatLng(latitudMapa,
                altitudMapa)));

    }

    public void vista3D(View view) {
        LatLng madrid = new LatLng(40.417325, -3.683081);
        CameraPosition camPos = new CameraPosition.Builder().target(madrid) // Centramos
                // el
                // mapa
                // en
                // Madrid
                .zoom(19) // Establecemos el zoom en 19
                .bearing(45) // Establecemos la orientación con el noreste
                // arriba
                .tilt(70) // Bajamos el punto de vista de la cámara 70 grados
                .build();

        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);

        mapa.animateCamera(camUpd3);
    }




    public void posActual(View view) {

        CameraPosition camPos = mapa.getCameraPosition();

        LatLng coordenadas = camPos.target;
        double latitud = coordenadas.latitude;
        double longitud = coordenadas.longitude;

        Toast.makeText(getApplicationContext(),
                "Longitud " + longitud + "\n" + " Latitud " + latitud,
                Toast.LENGTH_SHORT).show();

		/*
		 * float zoom = camPos.zoom; //calcular zoom actual float orientacion =
		 * camPos.bearing; //calcular orientacion actual float angulo =
		 * camPos.tilt; //calcular el angulo actual.
		 */

    }


    public void buscarDireccion(View view){
        String direccion = txtDireccion.getText().toString();
        List<Address> addressList = null;

        if(direccion!=null && !direccion.equals("")){
            Geocoder geocoder = new Geocoder(this);
            try {
                //String direccion, Int numero maximo de resultados
                //Devuelve los puntos de las concordancias en el mapa segun el texto ingresado
                addressList = geocoder.getFromLocationName(direccion,1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng posicion = new LatLng(address.getLatitude(), address.getLongitude());
            mapa.addMarker(new MarkerOptions().position(posicion).title(direccion));

            mapa.moveCamera(CameraUpdateFactory.newLatLng(posicion));
        }
    }
}
