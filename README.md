# Móviles - Trabajo Práctico 4

Aplicación de gestión de productos en Android. Este trabajo práctico implementa el patrón de arquitectura **MVVM (Model-View-ViewModel)** utilizando **Fragments** para gestionar el estado y la interfaz de usuario de manera eficiente y limpia.

## Características

La aplicación cuenta con dos pantallas principales:

1. **Cargar Producto**: Permite al usuario ingresar un nuevo producto a través de un formulario con los siguientes campos:
    - **Código**: Número entero único que identifica al producto.
    - **Descripción**: Nombre o detalle del producto.
    - **Precio**: Valor del producto (debe ser mayor a 0).
    
    *Incluye validaciones (campos vacíos, valores numéricos, código duplicado) que son procesadas directamente por el `ViewModel`.*

2. **Listar Productos**: Muestra todos los productos cargados en una lista mediante un `RecyclerView`.
    - Los productos se ordenan alfabéticamente de forma automática por su descripción antes de mostrarse.
    - Si no hay productos cargados, se muestra un mensaje indicando que la lista está vacía.

## Arquitectura y Tecnologías

- **Lenguaje**: Java
- **SDK**: Android SDK
- **Arquitectura**: MVVM (Model - View - ViewModel)
- **Componentes Clave**:
  - `Activity` contenedora (`MainActivity`).
  - `Fragments` para las pantallas de carga y listado (`CargarFragment`, `ListarFragment`).
  - `ViewModel` (`ProductoViewModel`) compartido entre la Activity y los Fragments para preservar el estado ante cambios de configuración y centralizar la lógica de negocio.
  - `LiveData` para observar cambios en los datos (lista de productos, mensajes de error, estado de éxito) reactivamente desde la UI.
  - `RecyclerView` y `Adapter` (`ProductoAdapter`) para renderizar listas dinámicas.
  - **View Binding** para vincular vistas de manera segura y sin utilizar `findViewById`.

## Almacenamiento

Por requerimiento de la consigna, el almacenamiento de los productos se realiza de manera temporal en memoria utilizando una lista estática (`ArrayList<Producto>`) alojada en la `MainActivity`.
