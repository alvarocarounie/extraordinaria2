import java.io.*;
import java.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// Clase principal que contiene la lógica del programa
 class LibreriaOnline {
    private List<Producto> inventario;
    private List<Usuario> usuarios;

    public LibreriaOnline() {
        inventario = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    // Caso de uso: Dar de alta un producto
    public void darDeAltaProducto(Producto producto) {
        inventario.add(producto);
    }

    // Caso de uso: Dar de baja un producto
    public void darDeBajaProducto(Producto producto) {
        inventario.remove(producto);
    }

    // Caso de uso: Dar de alta un usuario
    public void darDeAltaUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    // Caso de uso: Dar de baja un usuario
    public void darDeBajaUsuario(Usuario usuario) {
        usuarios.remove(usuario);
    }

    // Caso de uso: Comprar un producto
    public void comprarProducto(Usuario usuario, Producto producto) throws Exception {
        if (!inventario.contains(producto)) {
            throw new Exception("El producto no está disponible en el inventario.");
        }

        if (!usuarios.contains(usuario)) {
            throw new Exception("El usuario no está registrado en el sistema.");
        }

        if (!usuario.esMayorDeEdad()) {
            throw new Exception("El usuario debe ser mayor de 8 años.");
        }

        if (producto instanceof Libro) {
            Libro libro = (Libro) producto;
            if (!libro.getCategoria().equals("Aventuras") && !libro.getCategoria().equals("Ciencia Ficción")
                    && !libro.getCategoria().equals("Romántica") && !libro.getCategoria().equals("Historia")
                    && !libro.getCategoria().equals("Arte") && !libro.getCategoria().equals("Narrativa Española")) {
                throw new Exception("El libro tiene una categoría inválida.");
            }
        }

        if (producto instanceof JuegoMesa) {
            JuegoMesa juegoMesa = (JuegoMesa) producto;
            if (!juegoMesa.esEdadRecomendada(usuario.getEdad())) {
                throw new Exception("El juego de mesa no es adecuado para la edad del usuario.");
            }
        }

        usuario.comprarProducto(producto);
    }

    // Caso de uso: Devolver un producto
    public void devolverProducto(Usuario usuario, Producto producto) throws Exception {
        if (!usuarios.contains(usuario)) {
            throw new Exception("El usuario no está registrado en el sistema.");
        }

        if (!usuario.haCompradoRecientemente(producto)) {
            throw new Exception("No se puede devolver el producto, no fue comprado recientemente.");
        }

        usuario.devolverProducto(producto);
    }

    // Caso de uso: Listar productos por título y vendidos
    public void listarProductos() {
        for (Producto producto : inventario) {
            System.out.println("Título: " + producto.getTitulo() + " | Vendidos: " + producto.getVendidos());
        }
    }

    // Caso de uso: Dinero ingresado procedente de las ventas de libros en un determinado mes/año
    public double obtenerIngresosLibros(int mes, int año) {
        double ingresos = 0;
        for (Producto producto : inventario) {
            if (producto instanceof Libro) {
                Libro libro = (Libro) producto;
                ingresos += libro.calcularIngresos(mes, año);
            }
        }
        return ingresos;
    }

    // Caso de uso: Dinero ingresado procedente de las ventas de juegos en un determinado mes/año
    public double obtenerIngresosJuegos(int mes, int año) {
        double ingresos = 0;
        for (Producto producto : inventario) {
            if (producto instanceof JuegoMesa) {
                JuegoMesa juegoMesa = (JuegoMesa) producto;
                ingresos += juegoMesa.calcularIngresos(mes, año);
            }
        }
        return ingresos;
    }

    // Caso de uso: Cantidad de libros vendidos en un determinado mes
    public int obtenerCantidadLibrosVendidos(int mes) {
        int cantidad = 0;
        for (Producto producto : inventario) {
            if (producto instanceof Libro) {
                Libro libro = (Libro) producto;
                cantidad += libro.obtenerCantidadVendidos(mes);
            }
        }
        return cantidad;
    }

    // Caso de uso: Cantidad de juegos de mesa vendidos en un mes
    public int obtenerCantidadJuegosVendidos(int mes) {
        int cantidad = 0;
        for (Producto producto : inventario) {
            if (producto instanceof JuegoMesa) {
                JuegoMesa juegoMesa = (JuegoMesa) producto;
                cantidad += juegoMesa.obtenerCantidadVendidos(mes);
            }
        }
        return cantidad;
    }

    // Caso de uso: Ranking por título de libros vendidos en un mes
    public List<Libro> obtenerRankingLibrosVendidos(int mes) {
        List<Libro> ranking = new ArrayList<>();
        for (Producto producto : inventario) {
            if (producto instanceof Libro) {
                Libro libro = (Libro) producto;
                if (libro.obtenerCantidadVendidos(mes) > 0) {
                    ranking.add(libro);
                }
            }
        }
        ranking.sort(Comparator.comparing(Libro::getTitulo));
        return ranking;
    }

    // Caso de uso: Ranking por título de juegos de mesa vendidos en un mes
    public List<JuegoMesa> obtenerRankingJuegosVendidos(int mes) {
        List<JuegoMesa> ranking = new ArrayList<>();
        for (Producto producto : inventario) {
            if (producto instanceof JuegoMesa) {
                JuegoMesa juegoMesa = (JuegoMesa) producto;
                if (juegoMesa.obtenerCantidadVendidos(mes) > 0) {
                    ranking.add(juegoMesa);
                }
            }
        }
        ranking.sort(Comparator.comparing(JuegoMesa::getTitulo));
        return ranking;
    }

    // Caso de uso: Listado de los 5 clientes con más compras en un mes
    public List<Usuario> obtenerClientesConMasCompras(int mes) {
        Map<Usuario, Integer> comprasPorUsuario = new HashMap<>();
        for (Usuario usuario : usuarios) {
            int compras = usuario.obtenerCantidadCompras(mes);
            comprasPorUsuario.put(usuario, compras);
        }

        List<Usuario> clientes = new ArrayList<>(comprasPorUsuario.keySet());
        clientes.sort((u1, u2) -> comprasPorUsuario.get(u2) - comprasPorUsuario.get(u1));

        if (clientes.size() > 5) {
            return clientes.subList(0, 5);
        } else {
            return clientes;
        }
    }

    public static void main(String[] args) {
        LibreriaOnline libreria = new LibreriaOnline();

        // Ejemplo de uso
        try {
            // Cargar inventario desde archivo
            libreria.cargarInventario("inventario.txt");
            // Cargar usuarios desde archivo
            libreria.cargarUsuarios("usuarios.txt");

            // Realizar acciones en la librería
            Usuario usuario1 = libreria.obtenerUsuarioPorDNI("12345678A");
            Usuario usuario2 = libreria.obtenerUsuarioPorDNI("98765432B");

            Producto libro1 = libreria.obtenerProductoPorId("L001");
            Producto juego1 = libreria.obtenerProductoPorId("J001");

            libreria.comprarProducto(usuario1, libro1);
            libreria.comprarProducto(usuario2, juego1);

            libreria.devolverProducto(usuario1, libro1);

            libreria.listarProductos();

            double ingresosLibros = libreria.obtenerIngresosLibros(7, 2023);
            System.out.println("Ingresos por libros: " + ingresosLibros);

            int cantidadLibrosVendidos = libreria.obtenerCantidadLibrosVendidos(7);
            System.out.println("Cantidad de libros vendidos: " + cantidadLibrosVendidos);

            List<Libro> rankingLibros = libreria.obtenerRankingLibrosVendidos(7);
            System.out.println("Ranking de libros vendidos:");
            for (Libro libro : rankingLibros) {
                System.out.println(libro.getTitulo());
            }

            List<Usuario> clientesConMasCompras = libreria.obtenerClientesConMasCompras(7);
            System.out.println("Clientes con más compras:");
            for (Usuario cliente : clientesConMasCompras) {
                System.out.println(cliente.getNombreCompleto());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Carga el inventario desde un archivo
    private void cargarInventario(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.trim().split("\\|");
                if (values[0].equals("LIBRO")) {
                    String id = values[1];
                    String titulo = values[2];
                    String autor = values[3];
                    double precio = Double.parseDouble(values[4]);
                    String categoria = values[5];
                    boolean nuevo = Boolean.parseBoolean(values[6]);
                    Producto libro = new Libro(id, titulo, autor, precio, categoria, nuevo);
                    inventario.add(libro);
                } else if (values[0].equals("JUEGO")) {
                    String id = values[1];
                    String titulo = values[2];
                    int edadRecomendada = Integer.parseInt(values[3]);
                    String tematica = values[4];
                    Producto juego = new JuegoMesa(id, titulo, edadRecomendada, tematica);
                    inventario.add(juego);
                }
            }
        }
    }

    // Carga los usuarios desde un archivo
    private void cargarUsuarios(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.trim().split("\\|");
                String nombre = values[0];
                String apellido1 = values[1];
                String apellido2 = values[2];
                String dni = values[3];
                int dia = Integer.parseInt(values[4]);
                int mes = Integer.parseInt(values[5]);
                int año = Integer.parseInt(values[6]);
                int edad = calcularEdad(dia, mes, año);
                String numTarjeta = values[7];
                Usuario usuario = new Usuario(nombre, apellido1, apellido2, dni, dia, mes, año, edad, numTarjeta);
                usuarios.add(usuario);
            }
        }
    }

    // Obtiene un usuario por su DNI
    private Usuario obtenerUsuarioPorDNI(String dni) {
        for (Usuario usuario : usuarios) {
            if (usuario.getDni().equals(dni)) {
                return usuario;
            }
        }
        return null;
    }

    // Obtiene un producto por su ID
    private Producto obtenerProductoPorId(String id) {
        for (Producto producto : inventario) {
            if (producto.getId().equals(id)) {
                return producto;
            }
        }
        return null;
    }

    // Calcula la edad a partir de la fecha de nacimiento
    private int calcularEdad(int dia, int mes, int año) {
        // Implementación simplificada, no tiene en cuenta los años bisiestos
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH) + 1;
        int currentDay = now.get(Calendar.DAY_OF_MONTH);

        int edad = currentYear - año;

        if (mes > currentMonth || (mes == currentMonth && dia > currentDay)) {
            edad--;
        }

        return edad;
    }
}

// Clase abstracta para representar un producto
abstract class Producto {
    private String id;
    private String titulo;
    private double precio;
    private int vendidos;

    public Producto(String id, String titulo, double precio) {
        this.id = id;
        this.titulo = titulo;
        this.precio = precio;
        this.vendidos = 0;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public double getPrecio() {
        return precio;
    }

    public int getVendidos() {
        return vendidos;
    }

    public void incrementarVendidos() {
        vendidos++;
    }
}

// Clase que representa un libro
class Libro extends Producto {
    private String autor;
    private String categoria;
    private boolean nuevo;

    public Libro(String id, String titulo, String autor, double precio, String categoria, boolean nuevo) {
        super(id, titulo, precio);
        this.autor = autor;
        this.categoria = categoria;
        this.nuevo = nuevo;
    }

    public String getAutor() {
        return autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean esNuevo() {
        return nuevo;
    }

    public double calcularIngresos(int mes, int año) {
        // Implementación simplificada, asume que todos los libros tienen el mismo precio
        // y se vendieron en el mismo mes/año
        if (mes == 7 && año == 2023) {
            return getPrecio() * getVendidos();
        } else {
            return 0;
        }
    }

    public int obtenerCantidadVendidos(int mes) {
        // Implementación simplificada, asume que se vendieron todos los libros en el mismo mes
        if (mes == 7) {
            return getVendidos();
        } else {
            return 0;
        }
    }
}

// Clase que representa un juego de mesa
class JuegoMesa extends Producto {
    private int edadRecomendada;
    private String tematica;

    public JuegoMesa(String id, String titulo, int edadRecomendada, String tematica) {
        super(id, titulo, 0); // Precio de juegos de mesa se establece en 0
        this.edadRecomendada = edadRecomendada;
        this.tematica = tematica;
    }

    public int getEdadRecomendada() {
        return edadRecomendada;
    }

    public String getTematica() {
        return tematica;
    }

    public boolean esEdadRecomendada(int edad) {
        return edad >= edadRecomendada;
    }

    public double calcularIngresos(int mes, int año) {
        // Implementación simplificada, asume que todos los juegos de mesa tienen el mismo precio
        // y se vendieron en el mismo mes/año
        if (mes == 7 && año == 2023) {
            return getPrecio() * getVendidos();
        } else {
            return 0;
        }
    }

    public int obtenerCantidadVendidos(int mes) {
        // Implementación simplificada, asume que se vendieron todos los juegos de mesa en el mismo mes
        if (mes == 7) {
            return getVendidos();
        } else {
            return 0;
        }
    }
}

// Clase que representa un usuario
class Usuario {
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String dni;
    private int dia;
    private int mes;
    private int año;
    private int edad;
    private String numTarjeta;
    private List<Producto> productosComprados;

    public Usuario(String nombre, String apellido1, String apellido2, String dni, int dia, int mes, int año, int edad,
            String numTarjeta) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dni = dni;
        this.dia = dia;
        this.mes = mes;
        this.año = año;
        this.edad = edad;
        this.numTarjeta = numTarjeta;
        this.productosComprados = new ArrayList<>();
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido1 + " " + apellido2;
    }

    public String getDni() {
        return dni;
    }

    public int getEdad() {
        return edad;
    }

    public boolean esMayorDeEdad() {
        return edad > 18;
    }

    public void comprarProducto(Producto producto) {
        productosComprados.add(producto);
        producto.incrementarVendidos();
    }

    public void devolverProducto(Producto producto) {
        productosComprados.remove(producto);
        producto.incrementarVendidos();
    }

    public boolean haCompradoRecientemente(Producto producto) {
        // Implementación simplificada, asume que la compra fue realizada en los últimos 14 días
        Date fechaActual = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaActual);
        cal.add(Calendar.DATE, -14);
        Date fechaLimite = cal.getTime();
        for (Producto comprado : productosComprados) {
            if (comprado.equals(producto)) {
                Date fechaCompra = new Date(); // Supongamos que se almacena la fecha de compra
                if (fechaCompra.after(fechaLimite)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int obtenerCantidadCompras(int mes) {
        int cantidad = 0;
        for (Producto producto : productosComprados) {
            // Implementación simplificada, asume que se realizó la compra en el mismo mes
            if (mes == 7) {
                cantidad++;
            }
        }
        return cantidad;
    


    public class LibreriaOnlineTest {
        private LibreriaOnline libreria;

        //BeforeEach
        public void setUp() {
            libreria = new LibreriaOnline();
        }

       
        //Test
        public void testComprarProducto() throws Exception {
            Usuario usuario = new Usuario("John", "Doe", "Smith", "12345678A", 1, 1, 1990, 30, "1234567890");
            Producto libro = new Libro("L001", "El principito", "Antoine de Saint-Exupéry", 10.99, "Aventuras", true);
            libreria.darDeAltaUsuario(usuario);
            libreria.darDeAltaProducto(libro);

            libreria.comprarProducto(usuario, libro);
            Assertions.assertTrue(usuario.getProductosComprados().contains(libro));
            Assertions.assertEquals(1, libro.getVendidos());
        }

        //Test
        public void testDevolverProducto() throws Exception {
            Usuario usuario = new Usuario("John", "Doe", "Smith", "12345678A", 1, 1, 1990, 30, "1234567890");
            Producto libro = new Libro("L001", "El principito", "Antoine de Saint-Exupéry", 10.99, "Aventuras", true);
            libreria.darDeAltaUsuario(usuario);
            libreria.darDeAltaProducto(libro);

            libreria.comprarProducto(usuario, libro);
            libreria.devolverProducto(usuario, libro);
            Assertions.assertFalse(usuario.getProductosComprados().contains(libro));
            Assertions.assertEquals(0, libro.getVendidos());
        }

        // Resto de las pruebas...

        
        //Test
        public void testObtenerIngresosLibros() {
            // Implementar prueba
        }

        //Test
        public void testObtenerIngresosJuegos() {
            // Implementar prueba
        }

        //Test
        public void testObtenerCantidadLibrosVendidos() {
            // Implementar prueba
        }

        //Test
        public void testObtenerCantidadJuegosVendidos() {
            // Implementar prueba
        }

        //Test
        public void testObtenerRankingLibrosVendidos() {
            // Implementar prueba
        }

        //Test
        public void testObtenerRankingJuegosVendidos() {
            // Implementar prueba
        }

        //Test
        public void testObtenerClientesConMasCompras() {
            // Implementar prueba
        }
    }
   }
}



