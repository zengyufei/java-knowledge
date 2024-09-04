
::echo %~dp0
::cd /d %~dp0\E1_服务注册中心
::START mvn spring-boot:run 
:: TIMEOUT /T 5

START "J1_配置中心A" /D %~dp0\J1_配置中心A mvn clean compile spring-boot:run
START "J2_配置中心B" /D %~dp0\J2_配置中心B mvn clean compile spring-boot:run