<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<h1 style="color:red; text-align: center">Politician Registration</h1>
<form:form method="POST" modelAttribute="pp">
       <table border="0" bgcolor ="cyan" align="center">
       
       <tr>
       <td> Politician name:: </td>
       <td><form:input path="pname"/></td>
       </tr>
       
       <tr>
       <td> Politician party name:: </td>
       <td><form:input path="party"/></td>
       </tr>
       
       <tr>
       <td> Politician Dob:: </td>
       <td><form:input path="dob" type = "date"/></td>
       </tr>
       
       <tr>
       <td> Politician Doj:: </td>
       <td><form:input path="doj" type="date"/></td>
       </tr>
       
       <tr>
       <td> Has ConstitutionPost?:: </td>
       <td><form:radiobutton path="conspost" value="true"/>yes <form:radiobutton path="conspost" value="false"/>no
         </td>
       </tr>
       
       <tr>
       <td colspan="4"><input type = "submit" value="register"></td>
       </tr>
       </table>
</form:form>