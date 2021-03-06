<%--
  Created by IntelliJ IDEA.
  User: grishinv
  Date: 16.02.2021
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dream.model.Candidate" %>
<%@ page import="dream.store.PsqlStore" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>

    <script>
        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/dreamjob/cities',
                contentType: "application/json"
            }).done(function (data) {
                let result = "";
                result += "<option disabled selected>Выберите город</option>";
                for (let i = 0; i < data.length; i++) {
                    result += "<option value=" + data[i]['id'] + ">" + data[i]['name'] + "</option>";
                }
                $('#city').html(result);
            });
        });
        function validate() {
            let flag = true;
            let name = $('#name').val();
            let city = $('select').val();
            if (name === '') {
                alert($('#name').attr('title'));
                flag = false;
            }
            if (city === null) {
                alert($('#city').attr('title'));
                flag = false;
            }
            if (flag) {
                $('#form').submit();
            }
        }
    </script>

    <title>Работа мечты</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "", 0, 0);
    if (id != null) {
        candidate = PsqlStore.instOf().findCandidateById(Integer.valueOf(id));
    }
%>
<div class="container pt-3">

    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить кандидата</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
            </li>
        </ul>
    </div>

    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новый кандидат.
                <% } else { %>
                Редактирование кандидата.
                <% } %>
            </div>
            <div class="card-body">
                <form id="form" action="<%=request.getContextPath()%>/candidates.do?id=<%=candidate.getId()%>" method="post">
                    <div class="form-group">
                        <label for="name">Имя</label>
                        <input type="text" class="form-control" name="name" id="name" title="Вы не ввели имя" value="<%=candidate.getName()%>">
                    </div>
                    <div class="form-group">
                        <label for="city">Город</label>
                        <select name="city" id="city" class="form-control" title="Вы не выбрали город">
                            <option disabled selected>Выберите город</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <a href="<%=request.getContextPath()%>/upload.jsp?id=<%=candidate.getId()%>">Добавить фото</a>
                    </div>
                    <button type="button" onclick="validate()" class="btn btn-primary">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
