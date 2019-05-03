
::echo %~dp0
::cd /d %~dp0\E1_服务注册中心
::START mvn spring-boot:run 
:: TIMEOUT /T 5

START "E1_服务注册中心" /D %~dp0\E1_服务注册中心 mvn clean spring-boot:run
TIMEOUT /T 10
START "E2_服务接入方" /D %~dp0\E2_服务接入方 mvn clean spring-boot:run