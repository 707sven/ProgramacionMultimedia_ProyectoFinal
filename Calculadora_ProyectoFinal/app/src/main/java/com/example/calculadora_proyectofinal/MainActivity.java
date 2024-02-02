package com.example.calculadora_proyectofinal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Variables para elementos de la interfaz
    private EditText editTextNumero1, editTextNumero2;
    private RadioGroup radioGroupOperadores;
    private RadioButton radioButtonSuma, radioButtonResta, radioButtonMultiplicacion, radioButtonDivision;
    private TextView textViewResultado;
    private Button btnCalcular, btnBorrar, btnGuardar, btnMostrar;

    // Variables para datos y operaciones
    private double resultado;
    private String operadorSeleccionado;
    private SharedPreferences sharedPreferences;
    private static final String PREF_KEY_RESULTADO = "resultado_calculadora";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener referencias a los elementos de la interfaz
        editTextNumero1 = findViewById(R.id.et1);
        editTextNumero2 = findViewById(R.id.et2);
        radioGroupOperadores = findViewById(R.id.radioGroupOperadores);
        radioButtonSuma = findViewById(R.id.radioButtonSuma);
        radioButtonResta = findViewById(R.id.radioButtonResta);
        radioButtonMultiplicacion = findViewById(R.id.radioButtonMultiplicacion);
        radioButtonDivision = findViewById(R.id.radioButtonDivision);
        textViewResultado = findViewById(R.id.textViewResultado);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnMostrar = findViewById(R.id.btnMostrar);

        // Configurar listeners para los botones y el RadioGroup
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarResultado();
            }
        });

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarResultadoGuardado();
            }
        });

        radioGroupOperadores.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String nuevoOperador = null;

                // Identificar el operador seleccionado
                if (checkedId == R.id.radioButtonSuma) {
                    nuevoOperador = "+";
                } else if (checkedId == R.id.radioButtonResta) {
                    nuevoOperador = "-";
                } else if (checkedId == R.id.radioButtonMultiplicacion) {
                    nuevoOperador = "*";
                } else if (checkedId == R.id.radioButtonDivision) {
                    nuevoOperador = "/";
                }

                // Asignar el nuevo operador
                operadorSeleccionado = nuevoOperador;
            }
        });
    }

    // Métodos para las acciones de los botones
    private void calcular() {
        // Implementación del cálculo
        double num1 = Double.parseDouble(editTextNumero1.getText().toString());
        double num2 = Double.parseDouble(editTextNumero2.getText().toString());

        switch (operadorSeleccionado) {
            case "+":
                resultado = num1 + num2;
                break;
            case "-":
                resultado = num1 - num2;
                break;
            case "*":
                resultado = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    resultado = num1 / num2;
                } else {
                    // Mensaje emergente cuando se divide por 0
                    Toast.makeText(this, "Error: División por cero", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
        textViewResultado.setText("Resultado: " + resultado);
    }

    private void borrar() {
        // Implementación para limpiar campos
        editTextNumero1.setText("");
        editTextNumero2.setText("");
        textViewResultado.setText("Resultado: ");
    }

    private void guardarResultado() {
        // Implementación para guardar el resultado
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(PREF_KEY_RESULTADO, (float) resultado);
        editor.apply();
        Toast.makeText(this, "Resultado guardado", Toast.LENGTH_SHORT).show();
    }

    private void mostrarResultadoGuardado() {
        // Implementación para mostrar el resultado guardado
        sharedPreferences = getPreferences(MODE_PRIVATE);
        float resultadoGuardado = sharedPreferences.getFloat(PREF_KEY_RESULTADO, 0.0f);
        Toast.makeText(this, "Resultado guardado: " + resultadoGuardado, Toast.LENGTH_SHORT).show();
    }
}
