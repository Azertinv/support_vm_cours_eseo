TARGET := Main.class
JAVAC := javac
SRCS := $(shell find . -name *.java)

target: $(SRCS)
	$(JAVAC) $(SRCS)

.PHONY: clean
clean:
	$(RM) $(TARGET)
