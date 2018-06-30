<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="utf-8" />
        <title>Test</title>
    </head>

    <body>
        <p>Ceci est une page générée depuis une JSP.</p>
        <p>
        <% 
            String attribut = (String) request.getAttribute("test");
            out.println( attribut );
            
            String paramAuteur = request.getParameter( "auteur" );
            if (paramAuteur!=null){
            	out.println(paramAuteur);
            }
            %>
        </p>
    </body>
</html>