package bdd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bdd.TestJDBC;

public class Perfil extends HttpServlet {
    public static final String ATT_MESSAGES = "messages";
    public static final String VUE          = "/WEB-INF/Perfil.jsp";
    

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Initialisation de l'objet Java et récupération des messages */
        PerfilDB test = new PerfilDB();
        Map<String,String> messagesMap = test.executerTests( request );
        List<String> messages = test.getMessage();
        
        /* Enregistrement de la liste des messages dans l'objet requête */
        request.setAttribute("messages",messages);
        Set<String> attributeList =  messagesMap.keySet();
        for (String key : attributeList) {
        	request.setAttribute( key, messagesMap.get(key) );
        }
        /* Transmission vers la page en charge de l'affichage des résultats */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}
