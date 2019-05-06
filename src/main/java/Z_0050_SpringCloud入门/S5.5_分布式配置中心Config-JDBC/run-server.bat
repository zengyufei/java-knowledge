
::echo %~dp0
::cd /d %~dp0\E1_服务注册中心
::START mvn spring-boot:run 
:: TIMEOUT /T 5

START "V1_配置中心" /D %~dp0\V1_配置中心 mvn clean compile spring-boot:run