
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/afc003b5c88c487da5ce159cf9d48345)](https://app.codacy.com/manual/pkokovin/jwtapitest?utm_source=github.com&utm_medium=referral&utm_content=pkokovin/jwtapitest&utm_campaign=Badge_Grade_Dashboard)

<b>Тестовое задание.</b>

<b>Задача:</b>
<br/>необходимо реализовать RESTful  приложение

- 1:	При разработке использовать spring boot остальное на ваше усмотрение, желательно придерживаться экосистемы spring.
- 2:	При разработке использовать в качестве базы Postgres
- 3:	cross-origin должно быть отключено.	
- 4:	доступ к сервису возможен только при наличием токена ‘secret’ во всех остальных случаях кроме “GET /exit” возвращать 401 название и реализация на ваше усмотрение, инструкция для передачи токена должна прилагаться в месте с тестовым заданием.
- 5:	сервис должен запускатся на 8010 порту
- 6:	name и email должны быть регистронезависимые 
- 7:	Добавить фильтр при регистрации на проверку уникальности поля email в случае если Email есть в базе возвращать 403 статус
- 8:	к исходникам должен прилагаться артефакт приложения
- 9:	Для данного приложения реализуйте и подключил OpenApi (swagger)
- 10:	Версия java не выше 11
- 11:	Сборщик Maven

Реализация:
- 1: При разработке использован spring boot
- 2: Использована база PostgreSQL <br/>(при запуске приложения должна существовать база на локальной машине с именем jwtapitest, логином admin, паролем password)
- 3: cross-origin разрешен с /**
- 4: Доступ к сервису возможен только при наличии токена, <br/>токен получается POST запросом на /api/v1/auth/login
<br/>принимает json со следующей структурой: {"username": string, "password": string}  (username: admin, password: test)
<br/>Response: в случае успеха возвращает json: {"jwt": string}
<br/>Для доступа к сервису в заголовок необходимо поместить Key: "Authorization" Value: "Bearer_" + полученный токен
- 5: Сервис запускается на 8010 порту
- 6: name и email регистронезависимые
- 7: email проверяется на уникальность
- 8: подключен springdoc-openapi-ui (Swagger) доступен по default endpoint /v3/api-docs и /swagger-ui.html
- 9: java 8
- 10: Сборщик Maven