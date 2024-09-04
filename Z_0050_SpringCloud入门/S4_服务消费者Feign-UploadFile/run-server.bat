
::echo %~dp0
::cd /d %~dp0\E1_服务注册中心
::START mvn spring-boot:run 
:: TIMEOUT /T 5

START "H1_服务注册中心" /D %~dp0\H1_服务注册中心 mvn clean compile spring-boot:run
TIMEOUT /T 15
START "H2_服务提供方" /D %~dp0\H2_服务提供方 mvn clean compile spring-boot:run