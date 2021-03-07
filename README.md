---
#  Демо-проект для обмена валют

### 1. Описание
Проект предназначен для осуществления валютных и крипто-торговых операций с разными валютами.
Представляет собой серверную часть системы с REST архитектурой взаимодействия с внешними системами.
Можно создать кошельки с разными валютами, загружать текущие котировки обмена валют из внешних источников 
и создавать ордера на обмен валют. При закрытии ордера производится списание ден. средств с кошелька 
и зачисляется валюта согласно ордеру.

Также частично реализован сервис/контроллер по оценке наиболее выгодных котировок обмена (AnalyzerController).

Тэги: #jdk11, #spring, #hibernate, #mockito, #gradle

В проекте используется: JDK8, Local MySql DB

### 2. Требования
Please make sure You have following software:
1) Java 11
2) Gradle 6.6.1
3) MySQL (local or run Docker-Compose image from mysql-image/docker-compose)

### 3. Запуск проекта
In order to run project: <br>
1) Создайте БД MySQL "sample", согласно настроек application.yml или запустить Docker-Compose image (./mysql-image/docker-compose)
2) Запустите приложение "TestApplication" в IDE или в консоли `gradle bootRun'

### 5. Endpoint документация
Для проверки работы сервиса можно воспользоваться Документацией Swagger "http://localhost:8083/swagger-ui.html"
