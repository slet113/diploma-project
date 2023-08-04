# ДИПЛОМНЫЙ ПРОЕКТ
Этот проект является итоговой аттестацией обучения в Нетологии на профессии Тестировщик ПО. В нём реализованы все этапы пройденные в обучении:
1. Написание подготовительной документации к проеку, тест-плана;
2. Настройка окружения для написания автоматизированных тестов;
3. Написание автоматизированных тестов;
4. Создание баг-репортов на осное пройденных тестов;
5. Написание итоговой документации после проведённых тестов.

## Начало работы
Для того чтобы получить копию проекта необходимо:
1. Находясь на главной странице проекта в GitHub нажать на "Code"
2. Во вкладке "Local" скопировать HTTPS адрес
3. После этого открыть Intellij Idea
4. В Intellij Idea в верхнем меню открыть вкладку "File", далее "New", далее "Project from Version Control"
5. В открывшемся окне в строку "URL" вставить HTTPS адрес скопированный на сайте GitHub
6. Нажать на "Clone".

## Prerequisites
Программы необходимые для запуска проекта на ПК:
1. Браузер (Google Chrome, Яндекс и другие)
2. Intellij Idea
3. Docker

## Установка и запуск
1. Запустить Docker;
2. Открыть проект в Intellij Idea (см. Начало работы);
3. В терминале запустить контейнера при помощи команды docker-compose up --build;
4. Запустить в терминале сервис командой:
  * _для базы данных mySQL -_ java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
  * _для базы данных postgreSQL -_ java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
5. Запустить в терминале тесты командой:
  * _для использования базы данных mySQL -_ ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
  * _для использования базы данных postgreSQL -_ ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"

## Документация
1. План написания автоматизированных тестов https://github.com/slet113/diploma-project/blob/main/docs/Plan.md
2. Отчёт о проведённом тестировании https://github.com/slet113/diploma-project/blob/main/docs/Report.md
3. Отчёт о проведённой автоматизации https://github.com/slet113/diploma-project/blob/main/docs/Summary.md


