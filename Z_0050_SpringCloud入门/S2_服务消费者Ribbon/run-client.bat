
::echo %~dp0
::cd /d %~dp0\E1_服务注册中心
::START mvn spring-boot:run 
:: TIMEOUT /T 5

START "R3_服务消费方" /D %~dp0\R3_服务消费方 mvn clean compile test