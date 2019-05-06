
::echo %~dp0
::cd /d %~dp0\E1_服务注册中心
::START mvn spring-boot:run 
:: TIMEOUT /T 5


START "K1_服务注册中心" /D %~dp0\K1_服务注册中心 mvn clean compile spring-boot:run
TIMEOUT /T 15
START "K2_配置中心A" /D %~dp0\K2_配置中心A mvn clean compile spring-boot:run
START "K2_配置中心B" /D %~dp0\K2_配置中心B mvn clean compile spring-boot:run
TIMEOUT /T 60
START "K3_客户端" /D %~dp0\K3_客户端 mvn clean compile test