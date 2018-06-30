package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class TestJDBC {
    /* La liste qui contiendra tous les résultats de nos essais */
    private List<String> messages = new ArrayList<String>();

    public List<String> executerTests( HttpServletRequest request ) {
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
            String paramEmail = request.getParameter( "email" );
            String paramMotDePasse = request.getParameter( "motdepasse" );
            String paramNom = request.getParameter( "nom" );

            /* Exécution d'une requête d'écriture */
            int statut = statement.executeUpdate( "INSERT INTO Utilisateur (email, mot_de_passe, nom, date_inscription) "
                    + "VALUES ('" + paramEmail + "', MD5('" + paramMotDePasse + "'), '" + paramNom + "', NOW());" );
            
            //int statut = statement.executeUpdate( "INSERT INTO Utilisateur (email, mot_de_passe, nom, date_inscription) VALUES ('jmarc@mail.fr', MD5('lavieestbelle78'), 'jean-marc', NOW());" );

            /* Formatage pour affichage dans la JSP finale. */
            messages.add( "resultado de la solicitud de actualizacion " + statut + "." );
            /* Exécution d'une requête de lecture */
            resultat = statement.executeQuery( "SELECT id, email, mot_de_passe, nom  FROM Utilisateur;" );

            /* Récupération des données du résultat de la requête de lecture */
            while ( resultat.next() ) {
                int idUtilisateur = resultat.getInt( "id" );
                String emailUtilisateur = resultat.getString( "email" );
                String motDePasseUtilisateur = resultat.getString( "mot_de_passe" );
                String nomUtilisateur = resultat.getString( "nom" );
                /* Formatage des données pour affichage dans la JSP finale. */
                messages.add( "Datos volvidos por la solicitud = id : " + idUtilisateur + ", email = " + emailUtilisateur
                        + ", motdepasse = "
                        + motDePasseUtilisateur + ", nom = " + nomUtilisateur + "." );
            }

        } catch ( SQLException e ) {
            /* Gérer les éventuelles erreurs ici */
        	messages.add( "Error de connexion <br/>"
                    + e.getMessage() );
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

        return messages;
    }
}
