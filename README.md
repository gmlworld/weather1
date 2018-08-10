# Spring Boot + 天气爬虫 + server酱微信推送

## Usage

```
./package.bat
```
```
java -jar weather1-0.0.1-SNAPSHOT.jar
```

## TableResource
依次向三个实体类对象表添加数据：
area、user、userArea
<br>
添加地区：
<br>
http://127.0.0.1:8080/weather/addAndGet?areaName="北京"
<br>
添加用户：
<br>
http://127.0.0.1:8080/user/add?name="张三"&sckey="*******"
<br>
sckey来自server酱 网址：http://sc.ftqq.com/3.version
<br>
添加用户与地区的关联：
<br>
http://127.0.0.1:8080/userArea/add?userId="user表记录id"&areaId="area表记录id"

