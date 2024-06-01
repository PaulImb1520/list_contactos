package com.example.imbaquingo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private ArrayList<Contacto> listapersonas;
    private TableLayout table;
    private TableRow row;
    private Button button;
    private EditText editText;
    private RadioButton radioButton;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        list  = (ListView) findViewById(R.id.list);
        listapersonas = new ArrayList<Contacto>();
        listapersonas.add(new Contacto("Alberto", "Ramírez", "09876543210", "M"));
        listapersonas.add(new Contacto("Gabriela", "Gonzales", "09876544577", "F"));
        listapersonas.add(new Contacto("Daniel", "Borja", "09876541248", "M"));
        listapersonas.add(new Contacto("Emily", "Nuñez", "09876541238", "F"));
        listapersonas.add(new Contacto("Byron", "Perez", "09876549632", "M"));
        listapersonas.add(new Contacto("Beth", "Sánchez", "09876540000", "F"));

        AdaptadorContacto adaptador = new AdaptadorContacto(this);
        list.setAdapter(adaptador);
        RadioButton radioButtonMale = findViewById(R.id.radioButton);
        RadioButton radioButtonFemale = findViewById(R.id.radioButton3);

        //Ocultar todas las filas, excepto la primera por defecto
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
        for (int i = 1; i < table.getChildCount(); i++) {
            View child = table.getChildAt(i);
            if (child instanceof TableRow) {
                child.setVisibility(View.GONE);
            }
        }

        // Obtener el primer botón y añadir un OnClickListener para mostrar todas las filas
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 1; i < table.getChildCount(); i++) {
                    View child = table.getChildAt(i);
                    if (child instanceof TableRow) {
                        child.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        // Obtener el botón "Cancelar" y añadir un OnClickListener
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar todas las filas excepto la primera
                for (int i = 1; i < table.getChildCount(); i++) {
                    View child = table.getChildAt(i);
                    if (child instanceof TableRow) {
                        child.setVisibility(View.GONE);
                    }
                }
                // Limpiar los campos del formulario
                EditText editText2 = findViewById(R.id.editTextText2);
                editText2.setText("");

                EditText editText3 = findViewById(R.id.editTextText3);
                editText3.setText("");

                EditText editText4 = findViewById(R.id.editTextText4);
                editText4.setText("");

                radioButtonMale.setChecked(false);
                radioButtonFemale.setChecked(false);
            }
        });

        //Lógica para controlar la selección de los radioButton
        radioButtonMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonMale.isChecked()) {
                    radioButtonFemale.setChecked(false);
                }
            }
        });

        radioButtonFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonFemale.isChecked()) {
                    radioButtonMale.setChecked(false);
                }
            }
        });

        // Obtener el botón "Añadir" y añadir un OnClickListener
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores del formulario
                EditText editTextNombre = findViewById(R.id.editTextText2);
                EditText editTextApellido = findViewById(R.id.editTextText3);
                EditText editTextTelefono = findViewById(R.id.editTextText4);
                RadioButton radioButtonMale = findViewById(R.id.radioButton);
                RadioButton radioButtonFemale = findViewById(R.id.radioButton3);

                String nombre = editTextNombre.getText().toString();
                String apellido = editTextApellido.getText().toString();
                String telefono = editTextTelefono.getText().toString();
                String sexo = "";
                if (radioButtonMale.isChecked()) {
                    sexo = "M";
                } else if (radioButtonFemale.isChecked()) {
                    sexo = "F";
                }

                // Verificar que todos los campos están llenos
                if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || sexo.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Hacen falta datos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear un nuevo contacto y añadirlo a la lista
                Contacto nuevoContacto = new Contacto(nombre, apellido, telefono, sexo);
                listapersonas.add(nuevoContacto);

                // Notificar al adaptador que los datos han cambiado
                adaptador.notifyDataSetChanged();

                // Limpiar los campos del formulario después de añadir el contacto
                editTextNombre.setText("");
                editTextApellido.setText("");
                editTextTelefono.setText("");
                radioButtonMale.setChecked(false);
                radioButtonFemale.setChecked(false);
            }
        });
    }

    class AdaptadorContacto extends ArrayAdapter<Contacto>{
        AppCompatActivity appCompatActivity;
        AdaptadorContacto(AppCompatActivity context) {
            super(context, R.layout.customrow, listapersonas);
            appCompatActivity = context;
        }
        public View getView(int position, View converView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.customrow, null);

            TextView textView1 = item.findViewById((R.id.textView2));
            textView1.setText(listapersonas.get(position).getNombre());

            TextView textView2 = item.findViewById((R.id.textView3));
            textView2.setText(listapersonas.get(position).getApellido());

            TextView textView3 = item.findViewById((R.id.textView1));
            textView3.setText(listapersonas.get(position).getTelefono());

            ImageView imageView1 = item.findViewById(R.id.imageView1);
            if(listapersonas.get(position).getSexo().equals("M"))
                imageView1.setImageResource(R.drawable.male);
            else imageView1.setImageResource(R.drawable.female);
            return (item);
        }
    }
}