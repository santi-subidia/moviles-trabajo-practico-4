package com.example.trabajo_practico4.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.trabajo_practico4.adapter.ProductoAdapter;
import com.example.trabajo_practico4.databinding.FragmentListarBinding;
import com.example.trabajo_practico4.viewmodel.ProductoViewModel;

public class ListarFragment extends Fragment {

    private FragmentListarBinding binding;
    private ProductoViewModel viewModel;
    private ProductoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentListarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Instanciar ViewModel compartido con la Activity
        viewModel = new ViewModelProvider(requireActivity()).get(ProductoViewModel.class);

        // Configurar RecyclerView
        adapter = new ProductoAdapter();
        binding.recyclerViewProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewProductos.setAdapter(adapter);

        // Observar cambios en la lista de productos
        viewModel.getProductosLiveData().observe(getViewLifecycleOwner(), productos -> {
            if (productos != null) {
                adapter.setListaProductos(productos);

                // Mostrar/ocultar mensaje de lista vacía
                if (productos.isEmpty()) {
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    binding.recyclerViewProductos.setVisibility(View.GONE);
                } else {
                    binding.tvEmpty.setVisibility(View.GONE);
                    binding.recyclerViewProductos.setVisibility(View.VISIBLE);
                }
            }
        });

        // Cargar productos ordenados
        viewModel.cargarProductosOrdenados();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
