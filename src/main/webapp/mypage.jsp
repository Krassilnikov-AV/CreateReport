
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Test_Servlet</title>
</head>
<body>


<%--<h1>Hello ${name}</h1--%>
<%--<h1>Hello My_Servlet ${name}</h1--%>
<h1>Отправка файла на сервер</h1>
<form action="MainServlet" method="POST" enctype="multipart/form-data">

  <input type="text" name="path" value="" /></BR></BR>
  <input type="file" name="fileToUpload"  size="63" /></BR></BR>
  <input type="submit" value="Отправить" />

</form>
</body>
</html>