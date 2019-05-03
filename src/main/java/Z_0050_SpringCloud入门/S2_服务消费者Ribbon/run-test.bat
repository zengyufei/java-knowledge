
::echo %~dp0
::cd /d %~dp0\E1_服务注册中心
::START mvn spring-boot:run 
:: TIMEOUT /T 5

START /D %~dp0\R1_服务注册中心 mvn clean spring-boot:run
TIMEOUT /T 10
START /D %~dp0\R2_服务提供方A mvn clean spring-boot:run
START /D %~dp0\R2_服务提供方B mvn clean spring-boot:run
TIMEOUT /T 20
START /D %~dp0\R3_服务消费方 mvn clean spring-boot:run