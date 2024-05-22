package cl.isisur.appcocobowl;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.isisur.appcocobowl.Clases.Bowl;

public class MenuPedidos extends AppCompatActivity {
    Button btVolver, bTContinuar, btMenu, btSeguir;
    private ListView lvBowlsSeleccionados;
    private ArrayAdapter<Bowl> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_pedidos);


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




    }
}