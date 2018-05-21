package ficheros_bd;

import java.util.Iterator;
import java.sql.*;
import java.util.ArrayList;

public class ModeloBD extends ModeloAbs{
	
		ResultSet rset=null;
		Connection conexion=null;
		Statement stmt=null;
		PreparedStatement pst= null;
		
	    public ModeloBD()
	    {
	    	try {
	    		Class.forName("com.mysql.jdbc.Driver");
	    	}
	    	catch(ClassNotFoundException e) {
	    		System.out.println("ERROR AL REGISTRAR EL DRIVER");
	    		System.exit(0);
	    		
	    	}
	    	
	    	try {
	    	conexion = DriverManager.getConnection("jdbc:mysql://192.168.7.200:3306/ProductosDB","alumno","alumno");
	    	
	    	stmt= conexion.createStatement();
	    	}
	    	catch(SQLException e) {
	    		System.err.println("ERROR AL CONECTAR CON EL SERVIDOR");
	    		System.exit(0);
	    	}
	    	System.out.println("Usted esta conectado a la base de datos");
	        
	    }

	    // INSERT
	    public boolean insertarProducto ( Producto p){
	    	try {
	    	pst = conexion.prepareStatement("insert into Productos values(?, ?, ?, ?, ?)");
	    	pst.setInt(1, p.getCodigo());
	    	pst.setString(2,p.getNombre());
	    	pst.setInt(3,p.getStock());
	    	pst.setInt(4,p.getStock_min());
	    	pst.setFloat(5, p.getPrecio());
	    	rset=pst.executeQuery();
	    	}
	    	catch (SQLException e) {
	    		System.err.println("Error al introducir el producto");
	    	}
	    	return true;
	    }
	    
	    
	    // DELETE
	    boolean borrarProducto ( int codigo ){
	    	int nfilas = 0;
	    	try {
	    	nfilas = stmt.executeUpdate("delete from Productos where codigo = '"+codigo+"'");
	    	}
	    	catch  (SQLException e) {
	    		System.err.println("No se ha podido borrar el producto");

	    	}
	    	return ( nfilas == 1);
	    }
	    
	    // SELECT
	    public Producto buscarProducto ( int codigo){
	    	Producto aux=null;
	    	
	    	try {
	    		rset= stmt.executeQuery("select * from Productos where codigo= " +codigo);
	    		if ( rset.next()) {
	    		aux = new Producto();	
	    		aux.setCodigo(rset.getInt(1));
	    		aux.setNombre(rset.getString(2));
	    		aux.setStock(rset.getInt(3));
	    		aux.setStock_min(rset.getInt(4));
	    		aux.setPrecio(rset.getFloat(5));
	    		}
	    	}
	    	catch(SQLException e) {
	    		System.err.println("No se ha podido buscar el producto");
	    	}
	    return aux;    
	    }
	    
	    
	    //SELECT
	    void listarProductos (){
	    	
	    	
	    	try {
	    		rset=stmt.executeQuery("select * from Productos");
	    		
	    		while(rset.next()) {
	    			
	    			System.out.println("Código: " + rset.getInt(1)+" ");
	    			System.out.println("Nombre: "+ rset.getString(2));
	    			System.out.println("Precio: "+ rset.getFloat(5));
	    			System.out.println("---------------------");

	    			
	    		}
	    		
	    		
	    	}
	    	catch(SQLException e) {
	    		System.err.println("ERROR AL LISTAR PRODUCTOS");
	    		
	    	}
	        
	    }
	    
	    //UPDATE
	    boolean modificarProducto (Producto p){  
	    	int nfilas = 0;
	    	try {
	    		pst = conexion.prepareStatement("UPDATE Productos set codigo = ?, nombre = ?, stock = ?, "
	    				+ "stock_min = ?, precio = ?");
	    		pst.setInt   (1,p.getCodigo());
	    	    pst.setString(2,p.getNombre());
	    	    pst.setInt   (3,p.getStock());
	    	    pst.setInt   (4,p.getStock_min());
	    	    pst.setFloat (5,p.getPrecio());
	    	    pst.setInt   (6,p.getCodigo()); // Where
	    	    nfilas = pst.executeUpdate();
	    	}
	    	catch (SQLException e) {
	    		System.err.println("Error al modificar");
	    	}
	    	return (nfilas == 1);
	    }
	    
	    
	    
	    
	    
	    // Devuelvo un Iterador de una ArrayList con los resultados
	    // copiados de Rset al ArrayList
	     Iterator <Producto> getIterator(){
	       ArrayList <Producto> lista = new ArrayList<Producto>();
	       // Relleno el array list con los resultados de al consulta
	       
	       return lista.iterator();
	     }
	

}
