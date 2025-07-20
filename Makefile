#!/usr/bin/make -f
all: run

run:
	rm *.class 2>/dev/null || true
	javac *.java
	java -classpath . Taskify
	rm *.class 2>/dev/null || true

clean:
	rm *.class
