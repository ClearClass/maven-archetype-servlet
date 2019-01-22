## О проекте
Maven-проект архетипа для веб-приложения на основе сервлетов. Основные особенности:

- конфигурирование сервлета с помощью аннотаций;
- наличие файла web.xml;
- использование *tomcat7-maven-plugin* для развертывания приложения;
- настройка плагина компилятора для работы с Java 1.8;
- использование JUnit4 для реализации тестовых классов.

## Установка
```sh
$ mvn clean install
```
Для проверки корректности установки можем сгенерировать пробный проект:
  
```sh
$ mvn archetype:generate -B -DarchetypeGroupId=lib.clearclass.maven.archetypes -DarchetypeArtifactId=maven-archetype-servlet -DarchetypeVersion=1.0 -DgroupId=com.company -DartifactId=myproject -Dversion=1.0-SNAPSHOT -Dpackage=mypack
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

1. Создать/обновить файл `~/.m2/archetype-catalog.xml`. Для этого из рабочего каталога, в котором находится проект архетипа, выполнить команду:
```sh
$ mvn archetype:update-local-catalog
```

2. Добавить в Eclipse путь к каталогу `~/.m2` (где должен находится `archetype-catalog.xml`), выполнив настройку:  
>**`Preferences->Maven->Archetypes->Add Local Catalog...`**

3. Установить в Eclipse актуальный путь к файлу `%M2_HOME%/conf/settings.xml`:  

>**`Preferences->Maven->User Settings`**,

где в поле `User Settings` необходимо задать фактический путь, например: `C:\Program Files\apache-maven-3.3.9\conf\settings.xml`



