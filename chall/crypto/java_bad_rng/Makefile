JAVAC := javac
SRCS := $(shell find ./src/ -name *.java)

target: $(SRCS)
	$(JAVAC) -d . $(SRCS)

.PHONY: clean
clean:
	$(RM) ./*.class
