package extraordinaria2;

public class diagramadeclases {

    //	+-----------------------------------------+
    //	|                  Libreria               |
    //	+-----------------------------------------+
    //	| - inventario: List<Producto>            |
    //	| - usuarios: List<Usuario>               |
	//	+-----------------------------------------+
	//	| + darDeAltaProducto(producto: Producto) |
	//| + darDeBajaProducto(producto: Producto) |
	//| + darDeAltaUsuario(usuario: Usuario)    |
	//| + darDeBajaUsuario(usuario: Usuario)    |
	//+-----------------------------------------+

	//+----------------------+
	//	|       Producto       |
	//	+----------------------+
	//	| - id: String          |
	//	| - titulo: String      |
	//	| - precio: double      |
	//	+----------------------+
	//	| + getId(): String     |
	//	| + getTitulo(): String |
	//	| + getPrecio(): double |
	//	+----------------------+

	//	      /                  \
	//	+------------+       +--------------+
	//	|   Libro    |       | JuegoDeMesa  |
	//	+------------+       +--------------+
	//	| - autor: String      | - edadRecomendada: int    |
	//	| - categoria: String   | - tematica: String        |
	//	| - nuevo: boolean      |                          |
	//	+-----------------------+                          |
	//	| + getAutor(): String                             |
	//	| + getCategoria(): String                         |
	//	| + esNuevo(): boolean                             |
	//	+-------------------------------------------------+

	//	+----------------------------------------+
	//	|               Usuario                   |
	//	+----------------------------------------+
	//| - nombre: String                       |
	//	| - apellido1: String                    |
	//	| - apellido2: String                    |
	//	| - dni: String                          |
	//	| - fechaNacimiento: Date                |
	//	| - tarjetaCredito: String               |
	//	| - productosComprados: List<Producto>   |
	//	| - padres: List<Usuario>                |
	//	+----------------------------------------+
	//	| + getNombreCompleto(): String          |
	//	| + getDni(): String                     |
	//	| + getEdad(): int                       |
    //	| + esMayorDeEdad(): boolean             |
	//	| + comprarProducto(producto: Producto)  |
	//| + devolverProducto(producto: Producto) |
	//| + haCompradoRecientemente(producto: Producto): boolean |
	//| + obtenerPadres(): List<Usuario>       |
	//| + esUsuarioSistema(): boolean          |
	//| + getProductosComprados(): List<Producto> |
	//+----------------------------------------+

	
}
