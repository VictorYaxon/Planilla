package org.victoryaxon.db;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
public class Conexion {
    private Connection conexion;
    private Statement sentencia;
    private static Conexion instancia;
    public Conexion() {
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            //conexion = DriverManager.getConnection("jdbc:sqlserver://VIARO-CARLOS:1433;instanceName=SQLEXPRESS;databaseName=Planilla;user=vyaxon;password=12345");
            conexion = DriverManager.getConnection("jdbc:sqlserver://VICTORYAXON-PC:1433;instanceName=MSSQLEXPRESS;databaseName=PlanillaDB;user=sa;password=Kinal2015;");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(InstantiationException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static Conexion getInstancia(){
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }
    public ResultSet ejecutarProcedimiento(String nombreProcedimiento){
        ResultSet resultado = null;
        try{
        CallableStatement procedimiento = conexion.prepareCall(nombreProcedimiento);
        resultado = procedimiento.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return resultado;
    }
   
    public Connection getConexion() {
        return conexion;
    }
    
}
