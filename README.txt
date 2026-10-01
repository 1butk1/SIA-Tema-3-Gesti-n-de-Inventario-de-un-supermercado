SIA - SISTEMA DE GESTION DE INVENTARIO DE UN SUPERMERCADO
============================================================

Proyecto desarrollado para el curso INF2236 - Programacion Avanzada, PUCV.

Sistema de informacion que permite gestionar el inventario, ventas y
compras a proveedores de un supermercado, disponible tanto en modo
consola como en interfaz grafica.


INTEGRANTES
------------------------------------------------------------
- [Ignacio Rodriguez]
- [Matia Rios]
- [Diego Aguilar]


TECNOLOGIAS UTILIZADAS
------------------------------------------------------------
- Java (Oracle JDK 8 / JDK 11)
- Maven
- Swing (javax.swing) para la interfaz grafica
- Persistencia mediante archivo CSV


REQUISITOS PREVIOS
------------------------------------------------------------
- Java JDK 8 u 11 instalado
- Maven (o NetBeans, que lo incluye)


COMO EJECUTAR EL PROYECTO
------------------------------------------------------------

Desde NetBeans:
  1. Abrir el proyecto Proyecto_SIA en NetBeans.
  2. Ejecutar Clean and Build.
  3. Ejecutar Main.java (Shift + F6).

Desde linea de comandos:
  cd Proyecto_SIA
  mvn clean install
  java -cp target/classes supermercado.Main

Al iniciar, el sistema pedira elegir el modo de ejecucion:
  1. Modo Consola (Terminal)
  2. Modo Ventana (GUI)
  0. Salir


ESTRUCTURA DEL PROYECTO
------------------------------------------------------------
Proyecto_SIA/
  src/main/java/
    Excepciones/
      ProductoNoEncontradoException.java
      StockInsuficienteException.java
    supermercado/
      Main.java              (Punto de entrada, seleccion de modo)
      Terminal.java          (Interfaz de consola)
      Supermercado.java      (Clase principal del dominio)
      Seccion.java
      Producto.java
      Venta.java
      Proveedor.java
      OrdenCompra.java
      DetalleOrdenCompra.java
      LectorCSV.java         (Carga y guardado de datos)
      ventanas/
        VentanaPrincipal.java
        VentanaProductos.java
        VentanaVentas.java
        VentanaBuscarSeccion.java
  datos_supermercado.csv     (Archivo de persistencia)


FUNCIONALIDADES PRINCIPALES
------------------------------------------------------------
- Gestion de Secciones: agregar, listar, editar, eliminar y buscar.
- Gestion de Productos: agregar, listar, editar, eliminar, buscar y
  actualizar stock.
- Ventas: carrito de compras con validacion de stock disponible y
  descuento de inventario al confirmar.
- Reabastecimiento: alerta y reporte de productos que requieren
  reposicion, filtrado por seccion.
- Persistencia: carga automatica al iniciar y guardado al salir,
  mediante datos_supermercado.csv.


MANEJO DE EXCEPCIONES
------------------------------------------------------------
- ProductoNoEncontradoException: se lanza al buscar o eliminar un
  producto con un codigo inexistente.
- StockInsuficienteException: se lanza al intentar vender o descontar
  mas stock del disponible.


PERSISTENCIA DE DATOS
------------------------------------------------------------
Los datos se almacenan en datos_supermercado.csv, con dos tipos de
registro (SECCION y PRODUCTO). Si el archivo no existe al iniciar, el
sistema arranca con un supermercado vacio y lo genera al guardar.


REPORTE DEL PROYECTO
------------------------------------------------------------
El detalle de diseno, requerimientos cumplidos y justificacion tecnica
se encuentra en Reporte_Proyecto_SIA.docx.
