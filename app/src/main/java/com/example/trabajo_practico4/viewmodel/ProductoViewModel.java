package com.example.trabajo_practico4.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
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
    private MutableLiveData<Boolean> productoAgregadoExito;
    private MutableLiveData<Boolean> listaVaciaLiveData;

    public ProductoViewModel(@NonNull Application application) {
        super(application);
        productosLiveData = new MutableLiveData<>();
        mensajeError = new MutableLiveData<>();
        productoAgregadoExito = new MutableLiveData<>();
        listaVaciaLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Producto>> getProductosLiveData() {
        return productosLiveData;
    }

    public LiveData<String> getMensajeError() {
        return mensajeError;
    }

    public LiveData<Boolean> getProductoAgregadoExito() {
        return productoAgregadoExito;
    }

    public LiveData<Boolean> getListaVaciaLiveData() {
        return listaVaciaLiveData;
    }

    public void agregarProducto(String codigoStr, String descripcionStr, String precioStr) {
        // Reset success state
        productoAgregadoExito.setValue(false);

        // Validaciones básicas de campos vacíos
        if (codigoStr == null || codigoStr.isEmpty()) {
            mensajeError.setValue("Ingrese el código");
            return;
        }
        if (descripcionStr == null || descripcionStr.isEmpty()) {
            mensajeError.setValue("Ingrese la descripción");
            return;
        }
        if (precioStr == null || precioStr.isEmpty()) {
            mensajeError.setValue("Ingrese el precio");
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            double precio = Double.parseDouble(precioStr);

            if (precio <= 0) {
                mensajeError.setValue("El precio debe ser mayor a 0");
                return;
            }

            // Validar que el código no exista
            for (Producto producto : MainActivity.listaProductos) {
                if (producto.getCodigo() == codigo) {
                    mensajeError.setValue("Código duplicado");
                    return;
                }
            }

            // Crear el producto si las validaciones pasan
            Producto p = new Producto(codigo, descripcionStr, precio);

            // Si es válido, añadir a la lista y actualizar LiveData
            MainActivity.listaProductos.add(p);
            mensajeError.setValue("Producto agregado correctamente");
            productoAgregadoExito.setValue(true);
            
            ArrayList<Producto> listaActualizada = new ArrayList<>(MainActivity.listaProductos);
            productosLiveData.setValue(listaActualizada);
            listaVaciaLiveData.setValue(listaActualizada.isEmpty());

        } catch (NumberFormatException e) {
            mensajeError.setValue("Código o precio inválido");
        }
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
        listaVaciaLiveData.setValue(listaOrdenada.isEmpty());
    }
}
