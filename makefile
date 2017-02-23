all: install target01 target02 target03 consolidate

install:
	mvn install

zip:
	rm -f /tmp/code.zip
	zip -r /tmp/code.zip src/main/java
