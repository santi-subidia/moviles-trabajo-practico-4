package com.example.trabajo_practico4;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.trabajo_practico4.databinding.ActivityMainBinding;
import com.example.trabajo_practico4.fragment.CargarFragment;
import com.example.trabajo_practico4.fragment.ListarFragment;
import com.example.trabajo_practico4.modelo.Producto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Producto> listaProductos = new ArrayList<>();

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCargar.setOnClickListener(v -> {
            reemplazarFragmento(new CargarFragment());
        });

        binding.btnListar.setOnClickListener(v -> {
            reemplazarFragmento(new ListarFragment());
        });

        binding.btnSalir.setOnClickListener(v -> {
            mostrarDialogoSalir();
        });
    }

    private void reemplazarFragmento(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void mostrarDialogoSalir() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Desea cerrar la aplicación?")
                .setPositiveButton("Sí", (dialog, which) -> finishAffinity())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}