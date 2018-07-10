package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class PerfilDB {
    /* La liste qui contiendra tous les résultats de nos essais */
    private Map<String,String> messagesMap= new HashMap<>();
    private List<String> messages= new ArrayList<String>();
    public Map<String,String> executerTests( HttpServletRequest request ) {
    	/* Chargement du driver JDBC pour MySQL */
    	try {
        	messages.add("driver cargando");
            Class.forName( "com.mysql.jdbc.Driver" );
            messages.add("Driver cargado!");
        } catch ( ClassNotFoundException e ) {
            /* Gérer les éventuelles erreurs ici. */
        }
        /* Connexion à la base de données */
        //String url = "jdbc:mysql://localhost:3306/bdd_sdzee";
        String url="jdbc:mysql://localhost:3306/bdd_sdzee?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String utilisateur = "java";
        String motDePasse = "javagui";
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        try {
        	messages.add("Conectando a la DB");
            connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
            messages.add("connexion lograda");
            statement= connexion.createStatement();
            /* Exécution d'une requête d'écriture */
            /* Récupération des paramètres d'URL saisis par l'utilisateur */
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String edad = request.getParameter("edad");
            String nombreFoto = request.getParameter("nombreFoto");
            String empresaActual = request.getParameter("empresaActual");
            String nombreProyecto = request.getParameter("nombreProyecto");
            String descripcion = request.getParameter("descripcion");
            String email = request.getParameter("email");

            /* Exécution d'une requête d'écriture */
            try {
            int statut = statement.executeUpdate( "INSERT INTO Perfil (nombre,apellido,edad,nombreFoto,EmpresaActual,nombreProyecto,descripcion,email) "
                    + "VALUES ('" + nombre + "', '" + apellido + "', '" + edad + "', '" + nombreFoto + "', '" + empresaActual +"', '" + nombreProyecto + "', '" + descripcion +"', '" + email + "');");
            messages.add( "resultado de la solicitud de actualizacion " + statut + "." );
            }
            /* Formatage pour affichage dans la JSP finale. */
            catch(Exception e){
            messages.add(e.toString());
            }
            /* Exécution d'une requête de lecture */
            resultat = statement.executeQuery( "SELECT * FROM Perfil WHERE nombre='guillaume' and apellido='tortellier';" );

            /* Récupération des données du résultat de la requête de lecture */
            while ( resultat.next() ) {
                String rqId = resultat.getString( "id" );
                messagesMap.put("id", "id : "+rqId);
                String rqNombre = resultat.getString("nombre");
                messagesMap.put("nombre", "Nombre : "+rqNombre);
                String rqApellido = resultat.getString("apellido");
                messagesMap.put("apellido", "Apellido : "+rqApellido);
                String rqEdad = resultat.getString("edad");
                messagesMap.put("edad", "Edad : "+rqEdad);
                String rqNombreFoto = resultat.getString("nombreFoto");
                messagesMap.put("nombreFoto", rqNombreFoto);
                String rqEmpresaActual = resultat.getString("empresaActual");
                messagesMap.put("empresaActual", "Empresa Actual : " + rqEmpresaActual);
                String rqNombreProyecto = resultat.getString("nombreProyecto");
                messagesMap.put("nombreProyecto", "NombreProyecto : "+rqNombreProyecto);
                String rqDescripcion = resultat.getString("descripcion");
                messagesMap.put("descripcion", "Descripcion : " +rqDescripcion);
                String rqEmail = resultat.getString("email");
                messagesMap.put("email", "Email : " +rqEmail);
                /* Formatage des données pour affichage dans la JSP finale. */
            }
        } catch ( SQLException e ) {
            /* Gérer les éventuelles erreurs ici */
        } finally {
            if ( resultat != null ) {
                try {
                    /* On commence par fermer le ResultSet */
                    resultat.close();
                } catch ( SQLException ignore ) {
                }
            }
            if ( statement != null ) {
                try {
                    /* Puis on ferme le Statement */
                    statement.close();
                } catch ( SQLException ignore ) {
                }
            }
            if ( connexion != null ) {
                try {
                    /* Et enfin on ferme la connexion */
                    connexion.close();
                } catch ( SQLException ignore ) {
                }
            }
        }

        return messagesMap;
    }
	public List<String> getMessage() {
		// TODO Auto-generated method stub
		return messages;
	}
}
