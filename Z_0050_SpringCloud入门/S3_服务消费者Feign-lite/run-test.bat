
::echo %~dp0
::cd /d %~dp0\E1_服务注册中心
::START mvn spring-boot:run 
:: TIMEOUT /T 5

START "F1_服务注册中心_lite" /D %~dp0\F1_服务注册中心_lite mvn clean compile spring-boot:run
TIMEOUT /T 15
START "F2_服务提供方_lite" /D %~dp0\F2_服务提供方_lite mvn clean compile spring-boot:run
TIMEOUT /T 30
START "F3_服务消费方_lite" /D %~dp0\F3_服务消费方_lite mvn clean compile test