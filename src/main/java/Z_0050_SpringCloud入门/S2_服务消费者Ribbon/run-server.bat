
::echo %~dp0
::cd /d %~dp0\E1_服务注册中心
::START mvn spring-boot:run 
:: TIMEOUT /T 5

START "R1_服务注册中心" /D %~dp0\R1_服务注册中心 mvn clean compile spring-boot:run
TIMEOUT /T 15
START "R2_服务提供方A" /D %~dp0\R2_服务提供方A mvn clean compile spring-boot:run
START "R2_服务提供方B" /D %~dp0\R2_服务提供方B mvn clean compile spring-boot:run