<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>foo page</title>
</head>
<body>
   <table>
      <caption>Содержание таблицы:</caption>
      <colgroup>
         <col style="background-color: LightCyan" />
         <col span="2" style="background: Khaki" />
      </colgroup>
      <tr>
         <th width="50">id</th>
         <th width="200">word</th>
         <th width="200">num</th>
      </tr>
      <c:forEach items="${data}" var="st">
         <tr>
            <td>${st.get("id")}</td>
            <td>${st.get("word")}</td>
            <td>${st.get("num")}</td>
         </tr>
      </c:forEach>
   </table>
</body>
</html>