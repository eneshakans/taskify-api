module rocks.halhadus.taskify.api {
    requires java.sql;
    requires spring.web;
    requires jjwt.api;
    requires spring.context;
    requires spring.security.config;
    requires spring.security.crypto;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.jdbc;
    requires spring.security.web;
    requires org.slf4j;
    requires spring.beans;
    requires jakarta.annotation;
    requires org.apache.tomcat.embed.core;
    requires spring.security.core;
    requires spring.tx;
    requires org.xerial.sqlitejdbc;


    exports rocks.halhadus.taskify.api;
}