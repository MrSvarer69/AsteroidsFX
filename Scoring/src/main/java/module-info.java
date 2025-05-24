module Scoring {
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.webmvc;
    requires spring.web;

    opens dk.sdu.mmmi.cbse.scoring to spring.core, spring.beans, spring.context;
    exports dk.sdu.mmmi.cbse.scoring;
}