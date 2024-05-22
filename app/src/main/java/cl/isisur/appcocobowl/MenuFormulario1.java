package cl.isisur.appcocobowl;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cl.isisur.appcocobowl.Clases.Bowl;
import cl.isisur.appcocobowl.Clases.Tipos_de_Bowl;

public class MenuFormulario1 extends AppCompatActivity {

    private List<Bowl> ListBowl = new ArrayList<>();
    private List<Bowl> bowlsSeleccionados = new ArrayList<>();
    private List<String> ListBowlNombre = new ArrayList<>();
    private List<Tipos_de_Bowl> ListaTBowl = new ArrayList<>();
    private List<String> ListaTBowlNombre = new ArrayList<>();
    ArrayAdapter<Bowl> arrayAdapterBowl;
    ArrayAdapter<String> arrayAdapterString;
    ArrayAdapter<Tipos_de_Bowl> arrayAdapterListaTBowl;
    ArrayAdapter<String> arrayAdapterListaTBowlNombre;

    EditText eTNombre, eTEditorial;
    Button bTBoton, btEliminar;
    ListView lvListadoBowls, lvTipos_Bowl;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_formulario1);

        bTBoton = findViewById(R.id.bTAgregar);
        lvListadoBowls = findViewById(R.id.lvListadoBowls);
        lvTipos_Bowl = findViewById(R.id.lvTipos_Bowl);

        inicializarFireBase();
        listarDatos();
        Intent intent1=new Intent(this, MenuPedidos.class);
        bTBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear una lista de nombres de bowls
                List<String> nombresBowlsSeleccionados = new ArrayList<>();
                List<Integer> cantidadesBowlsSeleccionados = new ArrayList<>(); // Lista de cantidades

                for (Bowl bowl : bowlsSeleccionados) {
                    nombresBowlsSeleccionados.add(bowl.getNombre());
                    cantidadesBowlsSeleccionados.add(bowl.getCantidad()); // Agregar la cantidad de cada bowl
                }

                // Crear un nuevo Intent para abrir la actividad MenuPedidos
                Intent intent = new Intent(MenuFormulario1.this, MenuPedidos.class);

                // Agregar la lista de nombres de bowls como un extra al Intent
                intent.putStringArrayListExtra("nombresBowlsSeleccionados", new ArrayList<>(nombresBowlsSeleccionados));

                // Agregar la lista de cantidades de bowls como un extra al Intent
                intent.putIntegerArrayListExtra("cantidadesBowlsSeleccionados", new ArrayList<>(cantidadesBowlsSeleccionados));

                // Iniciar la actividad MenuPedidos
                startActivity(intent);
            }
        });

    }

    private void listarDatos() {
        databaseReference.child("Tipos_de_Bowl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListaTBowl.clear();
                ListaTBowlNombre.clear();
                for (DataSnapshot objs : snapshot.getChildren()) {
                    Tipos_de_Bowl TB = objs.getValue(Tipos_de_Bowl.class);
                    if (TB != null) {
                        ListaTBowl.add(TB);
                        ListaTBowlNombre.add(TB.getNombreTBowl().trim());
                    }
                }
                arrayAdapterListaTBowlNombre = new ArrayAdapter<>(MenuFormulario1.this, android.R.layout.simple_expandable_list_item_1, ListaTBowlNombre);
                lvTipos_Bowl.setAdapter(arrayAdapterListaTBowlNombre);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });

        databaseReference.child("Bowls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListBowl.clear();
                for (DataSnapshot objs : snapshot.getChildren()) {
                    Bowl bol = objs.getValue(Bowl.class);
                    if (bol != null) {
                        ListBowl.add(bol);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });

        lvTipos_Bowl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTipoBowl = ListaTBowlNombre.get(position);
                updateBowlListByType(selectedTipoBowl);
            }
        });
    }

    public void updateBowlListByType(String selectedTipoBowl) {
        ListBowlNombre.clear();
        for (Bowl bol : ListBowl) {
            String bowlType = bol.getNombreTBowl().trim();
            //System.out.println("Checking bowl: " + bol.getNombre() + " - " + bowlType);
            if (bowlType.equalsIgnoreCase(selectedTipoBowl.trim())) {
                ListBowlNombre.add(bol.getNombre() + " " + bol.getEstado());
            }
        }

        // Verificación: imprime la lista de bowls que coinciden con el tipo seleccionado
        //System.out.println("Bowls matching type '" + selectedTipoBowl + "': " + ListBowlNombre);

        arrayAdapterString = new ArrayAdapter<>(MenuFormulario1.this, android.R.layout.simple_expandable_list_item_1, ListBowlNombre);
        lvListadoBowls.setAdapter(arrayAdapterString);
        lvListadoBowls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el bowl seleccionado en la posición actual
                Bowl selectedBowl = ListBowl.get(position);

                // Mostrar un cuadro de diálogo para que el usuario ingrese la cantidad deseada
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuFormulario1.this);
                builder.setTitle("Ingrese la cantidad");

                final EditText input = new EditText(MenuFormulario1.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cantidadStr = input.getText().toString();
                        if (!cantidadStr.isEmpty()) {
                            int cantidad = Integer.parseInt(cantidadStr);

                            // Agregar el bowl seleccionado junto con la cantidad a la lista de bowls seleccionados
                            selectedBowl.setCantidad(cantidad);
                            bowlsSeleccionados.add(selectedBowl);

                            // Opcionalmente, puedes mostrar un mensaje de confirmación
                            Toast.makeText(MenuFormulario1.this, "Bowl seleccionado: " + selectedBowl.getNombre() + ", Cantidad: " + cantidad, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MenuFormulario1.this, "Debe ingresar una cantidad", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

    }

    private void inicializarFireBase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


}
