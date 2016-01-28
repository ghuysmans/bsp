ui: core/TestUI.class
	java -cp . core.TestUI
compare: core/TestCompare.class
	java -cp . core.TestCompare ${ARGS}

clean:
	rm core/*.class tests/*.class

JUNIT=tests/junit-4.11.jar:tests/hamcrest-core-1.3.jar
TESTSOURCE=$(wildcard tests/*.java)
TESTCLASSES=$(patsubst %.java,%.class,$(TESTSOURCE))
TESTS=$(subst /,.,$(patsubst %.java,%,$(TESTSOURCE)))
test: $(TESTCLASSES)
	java -cp .:$(JUNIT) org.junit.runner.JUnitCore $(TESTS)

%.class: %.java
	javac -cp .:$(JUNIT) $<
