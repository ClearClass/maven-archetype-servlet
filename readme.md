﻿## О проекте
Maven-проект архетипа для веб-приложения на основе сервлетов. Основные особенности:

- конфигурирование сервлета с помощью аннотаций;
- наличие файла web.xml;
- использование *tomcat7-maven-plugin* для развертывания приложения;
- настройка плагина компилятора для работы с Java 1.8;
- использование JUnit4 для реализации тестовых классов.

### Версии

* Версия 1.0 - базовая версия.
* Версия 1.1 - добавлена jsp-страница с тегами JSTL и ссылка на JNDI-источник данных, определяемый в файле `$CATALINA_HOME/conf/context.xml`:

```xml
<Context crossContext="true">
...
   <Resource name="jdbc/postgres" auth="Container"
          type="javax.sql.DataSource" driverClassName="org.postgresql.Driver"
          url="jdbc:postgresql://127.0.0.1:5432/db1" username="postgres"
          password="post" maxActive="20" maxIdle="10" maxWait="-1"/>

</Context>
```

* Версия 1.2 - добавлен Spring-контекст (`applicationContext.xml`), содержащий jdbcTemplate-бин и встраиваемую СУБД *H2* (для тестового класса). В основном сервлете производится переопределение источника данных для jdbcTemplate на JNDI-ресурс, получаемый с помощью аннотации `@Resource()`. Метод `doGet()` производит запись нескольких строк в таблицу БД, их чтение, и отправку на jsp-страницу для отображения в виде таблицы. Для создания схемы и начальной загрузки СУБД в тестовом и основном классе используется скрипт `init.sql`.

* Версия 1.21 - получение JNDI-ресурса перенесено в Spring-контекст, в виде объекта `JndiObjectFactoryBean`.

* Версия 1.22 - в Spring-контекст добавлено пространство имен `jee`; `JndiObjectFactoryBean` заменен на `<jee:jndi-lookup />`.

## Установка
```sh
$ mvn clean install
```
Для проверки корректности установки можем сгенерировать пробный проект:
  
```sh
$ mvn archetype:generate -B -DarchetypeGroupId=lib.clearclass.maven.archetypes -DarchetypeArtifactId=maven-archetype-servlet -DarchetypeVersion=1.1 -DgroupId=com.company -DartifactId=myproject -Dversion=1.0-SNAPSHOT -Dpackage=mypack
```
Далее выполним следующие настройки. В `%CATALINA_HOME%\conf\tomcat-users.xml` необходимо добавить роль *manager-script* и пользователя с этой ролью:

```xml
<tomcat-users>
   <role rolename="manager-script"/>
   <user username="admin" password="password" roles="manager-script"/>
   ...
</tomcat-users>
```

После этого в `%M2_HOME%\conf\settings.xml` необходимо добавить блок:
```xml
<server>
   <id>localTomcat7</id>
   <username>admin</username>
   <password>password</password>
</server>
```

Выполнив эти настройки, перейдем в рабочий каталог проекта и (предварительно запустив Tomcat) выполним команду развертывания:
```sh
$ cd myproject
$ mvn tomcat7:deploy
```

После этого можем проверить работу приложения по адресу:

http://localhost:8080/contextPath/servletPath

## Использование с Eclipse  

1. Создать/обновить файл `~/.m2/archetype-catalog.xml`. Для этого перейти в каталог `~/.m2` и выполнить из него команду:
```sh
$ mvn archetype:crawl -Dcatalog=archetype-catalog.xml
```

2. Добавить в Eclipse путь к каталогу `~/.m2` (где должен находится `archetype-catalog.xml`), выполнив настройку:  
>**`Preferences->Maven->Archetypes->Add Local Catalog...`**

3. Установить в Eclipse актуальный путь к файлу `%M2_HOME%/conf/settings.xml`:  

>**`Preferences->Maven->User Settings`**,

где в поле `User Settings` необходимо задать фактический путь, например: `C:\Program Files\apache-maven-3.3.9\conf\settings.xml`
