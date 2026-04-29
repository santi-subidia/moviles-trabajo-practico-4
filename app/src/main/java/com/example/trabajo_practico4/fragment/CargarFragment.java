package com.example.trabajo_practico4.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trabajo_practico4.databinding.FragmentCargarBinding;
import com.example.trabajo_practico4.modelo.Producto;
import com.example.trabajo_practico4.viewmodel.ProductoViewModel;

public class CargarFragment extends Fragment {

    private FragmentCargarBinding binding;
    private ProductoViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCargarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Instanciar ViewModel compartido con la Activity
        viewModel = new ViewModelProvider(requireActivity()).get(ProductoViewModel.class);

        // Observar mensajes de error/éxito
        viewModel.getMensajeError().observe(getViewLifecycleOwner(), mensaje -> {
            if (mensaje != null && !mensaje.isEmpty()) {
                Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();

                // Si fue exitoso, limpiar los campos
                if (mensaje.equals("Producto agregado correctamente")) {
                    binding.etCodigo.setText("");
                    binding.etDescripcion.setText("");
                    binding.etPrecio.setText("");
                    binding.etCodigo.requestFocus();
                }
            }
        });

        // Botón guardar
        binding.btnGuardar.setOnClickListener(v -> {
            String codigoStr = binding.etCodigo.getText().toString().trim();
            String descripcion = binding.etDescripcion.getText().toString().trim();
            String precioStr = binding.etPrecio.getText().toString().trim();

            // Validaciones básicas en la vista
            if (codigoStr.isEmpty()) {
                Toast.makeText(getContext(), "Ingrese el código", Toast.LENGTH_SHORT).show();
                return;
            }

            if (descripcion.isEmpty()) {
                Toast.makeText(getContext(), "Ingrese la descripción", Toast.LENGTH_SHORT).show();
                return;
            }

            if (precioStr.isEmpty()) {
                Toast.makeText(getContext(), "Ingrese el precio", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int codigo = Integer.parseInt(codigoStr);
                double precio = Double.parseDouble(precioStr);

                Producto producto = new Producto(codigo, descripcion, precio);
                viewModel.agregarProducto(producto);

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Código o precio inválido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
