TARGET := java_multiple.jar
JAVAC := javac
SRCS := $(shell find ./src/ -name *.java)

target: $(SRCS)
	$(JAVAC) -d . $(SRCS)
	jar cfe $(TARGET) Main *.class

.PHONY: clean
clean:
	$(RM) $(TARGET) *.class
