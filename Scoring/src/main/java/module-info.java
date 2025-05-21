module Scoring {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;

    opens dk.sdu.mmmi.cbse.scoring to spring.core;
    exports dk.sdu.mmmi.cbse.scoring;
}