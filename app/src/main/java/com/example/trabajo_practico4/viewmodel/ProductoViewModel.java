package com.example.trabajo_practico4.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajo_practico4.MainActivity;
import com.example.trabajo_practico4.modelo.Producto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Producto>> productosLiveData;
    private MutableLiveData<String> mensajeError;

    public ProductoViewModel(@NonNull Application application) {
        super(application);
        productosLiveData = new MutableLiveData<>();
        mensajeError = new MutableLiveData<>();
    }

    public MutableLiveData<List<Producto>> getProductosLiveData() {
        return productosLiveData;
    }

    public MutableLiveData<String> getMensajeError() {
        return mensajeError;
    }

    public void agregarProducto(Producto p) {
        // Validar que los campos no estén vacíos
        if (p.getDescripcion() == null || p.getDescripcion().trim().isEmpty()) {
            mensajeError.setValue("La descripción no puede estar vacía");
            return;
        }

        if (p.getPrecio() <= 0) {
            mensajeError.setValue("El precio debe ser mayor a 0");
            return;
        }

        // Validar que el código no exista
        for (Producto producto : MainActivity.listaProductos) {
            if (producto.getCodigo() == p.getCodigo()) {
                mensajeError.setValue("Código duplicado");
                return;
            }
        }

        // Si es válido, añadir a la lista y actualizar LiveData
        MainActivity.listaProductos.add(p);
        mensajeError.setValue("Producto agregado correctamente");
        productosLiveData.setValue(new ArrayList<>(MainActivity.listaProductos));
    }

    public void cargarProductosOrdenados() {
        List<Producto> listaOrdenada = new ArrayList<>(MainActivity.listaProductos);
        Collections.sort(listaOrdenada, new Comparator<Producto>() {
            @Override
            public int compare(Producto p1, Producto p2) {
                return p1.getDescripcion().compareToIgnoreCase(p2.getDescripcion());
            }
        });
        productosLiveData.setValue(listaOrdenada);
    }
}
