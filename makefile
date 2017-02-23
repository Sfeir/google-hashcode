all: install target01 target02 target03 consolidate

install:
	mvn install

target01:
	java -jar target/hashcode-1.0-SNAPSHOT-jar-with-dependencies.jar input01 /tmp/output01

target02:
	java -jar target/hashcode-1.0-SNAPSHOT-jar-with-dependencies.jar input02 /tmp/output02

target03:
	java -jar target/hashcode-1.0-SNAPSHOT-jar-with-dependencies.jar input03 /tmp/output03
