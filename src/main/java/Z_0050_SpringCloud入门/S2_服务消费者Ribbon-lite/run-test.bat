
::echo %~dp0
::cd /d %~dp0\E1_服务注册中心
::START mvn spring-boot:run 
:: TIMEOUT /T 5
::TASKKILL /F /FI "WINDOWTITLE eq R3_服务消费方_lite*"

START "R1_服务注册中心_lite" /D %~dp0\R1_服务注册中心_lite mvn clean spring-boot:run
TIMEOUT /T 10
START "R2_服务提供方_lite" /D %~dp0\R2_服务提供方_lite mvn clean spring-boot:run
TIMEOUT /T 20
START "R3_服务消费方_lite" /D %~dp0\R3_服务消费方_lite mvn clean test
