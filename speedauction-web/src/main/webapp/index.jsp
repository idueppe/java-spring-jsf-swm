<html>
<body>
<h2>Hello World!</h2>
<ul>
	<% 
		for (int i=0; i< 10;i++)
		{
	%>
	<li><% out.println(System.currentTimeMillis()); %></li>
	<%
		}
	 %>
</ul>
</body>
</html>
