<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dream.model.Candidate" %>
<%@ page import="dream.store.PsqlStore" %>
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

    <script>
        function checkLog() {
            if (validate()) {
                $('#my_form').submit();
            }
        }

        function validate() {
            let flag = true;
            let email = $('#email').val();
            let password = $('#password').val();

            if (email === '') {
                alert($('#email').attr('title'));
                flag = false;
            }
            if (password === '') {
                alert($('#password').attr('title'));
                flag = false;
            }
            return flag;
        }
    </script>

    <title>Работа мечты</title>
</head>
<body>
<div class="container pt-3">

    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Авторизация
            </div>
            <div class="card-body">
                <a href="<%=request.getContextPath()%>/reg.jsp">Регистрация</a>
            </div>
            <div class="card-body">
                <form id="my_form" action="<%=request.getContextPath()%>/auth.do" method="post">
                    <div class="form-group">
                        <label for="email">Почта</label>
                        <input type="email" class="form-control" name="email" id="email" title="Вы не ввели email">
                    </div>
                    <div class="form-group">
                        <label for="password">Пароль</label>
                        <input type="password" class="form-control" name="password" id="password" title="Вы не ввели пароль">
                    </div>
                    <button type="button" onclick="checkLog();" class="btn btn-primary">Войти</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
