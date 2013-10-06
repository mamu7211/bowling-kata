Bowling Kata
============

Build
-----

You'll need Maven 3 to build the jar file. To build the project from command line, cd to the "./bowling-kata-master/game" directory. There you'll find the projects pom.
Type "mvn package" or "mvn clean compile assembly:single". Test can be run with "mvn test", there should be 100 non failing tests.

Run
---

Cd to "./target/" and run it, e.g. "java -jar game-0.0.1-SNAPSHOT-jar-with-dependencies.jar". If you get some "NoClassDefFoundError" you're likely running the jar without dependencies.



