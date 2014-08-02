<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
</head>
<body>
	<form:form method="post">
		<table>
			<tr>
				<td width="80">用户名：</td>
				<td><input id="username" name="username" /></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input id="password" name="password" type="password" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<button type="submit">登录系统</button>
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>