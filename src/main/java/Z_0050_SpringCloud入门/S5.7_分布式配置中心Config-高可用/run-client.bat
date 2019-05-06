
::echo %~dp0
::cd /d %~dp0\E1_服务注册中心
::START mvn spring-boot:run 
:: TIMEOUT /T 5

START "K3_客户端" /D %~dp0\K3_客户端 mvn clean compile spring-boot:run