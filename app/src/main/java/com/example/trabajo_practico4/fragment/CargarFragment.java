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

        viewModel = new ViewModelProvider(requireActivity()).get(ProductoViewModel.class);

        viewModel.getMensajeError().observe(getViewLifecycleOwner(), mensaje -> {
            if (mensaje != null && !mensaje.isEmpty()) {
                Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                viewModel.resetMensajeError();
            }
        });

        viewModel.getProductoAgregadoExito().observe(getViewLifecycleOwner(), exito -> {
            if (exito != null && exito) {
                binding.etCodigo.setText("");
                binding.etDescripcion.setText("");
                binding.etPrecio.setText("");
                binding.etCodigo.requestFocus();
                viewModel.resetProductoAgregadoExito();
            }
        });

        binding.btnGuardar.setOnClickListener(v -> {
            String codigoStr = binding.etCodigo.getText().toString().trim();
            String descripcion = binding.etDescripcion.getText().toString().trim();
            String precioStr = binding.etPrecio.getText().toString().trim();

            viewModel.agregarProducto(codigoStr, descripcion, precioStr);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
