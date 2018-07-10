<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Perfil</title>
</head>
<body>
<div>Foto de perfil</div>
<img src="${nombreFoto}" alt="FotoPerfil" width="20%" height="40%">
<br />
<c:out value="${nombre}" />
<br />
<c:out value="${apellido}" />
<br />
<c:out value="${edad}" />
<br />
<c:out value="${empresaActual}" />
<br />
<c:out value="${nombreProyecto}" />
<br />
<c:out value="${descripcion}" />
<br />
<c:out value="${email}" />
</body>
</html>
	