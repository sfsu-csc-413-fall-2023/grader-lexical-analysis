COMPILE_DIR=target
SRC_DIR=.
SOURCE_FILE=sources
JUNIT_JAR=junit-platform-console-standalone-1.9.3.jar
TEST_TAR=assignment-1-tests.tar
ASSIGNMENT_GRADER=https://raw.githubusercontent.com/sfsu-csc-413-fall-2023/grader-lexical-analysis/main/

clean-guards:
	@rm -f .p-* .c-* .f-* .compiled-* junit-platform*

prep:
ifeq ("$(wildcard ./.p-$(TOKEN))","")
	@echo "Prepping to test..."
	@rm -rf lib
	@rm -rf tests
	@echo "prepped $(TOKEN)" > ./.p-$(TOKEN)
	@echo "Prep completed"
endif

clean: prep
ifeq ("$(wildcard ./.c-$(TOKEN))","")
	@echo "Cleaning..."
	@find . -name "*.class" -type f -delete
	@rm -rf $(COMPILE_DIR)
	@echo "cleaned $(TOKEN)" > ./.c-$(TOKEN)
	@echo "Clean completed"
endif

copy-files: clean
ifeq ("$(wildcard ./.f-$(TOKEN))","")
	@echo "Copying files..."
	@wget -q $(ASSIGNMENT_GRADER)lib/$(JUNIT_JAR)
	@mkdir lib
	@mv $(JUNIT_JAR) lib/$(JUNIT_JAR)
	@wget -q $(ASSIGNMENT_GRADER)$(TEST_TAR)
	@tar -xvf $(TEST_TAR)
	@echo "Just untarred"
	@rm $(TEST_TAR)
	@echo "copied $(TOKEN)" > ./.f-$(TOKEN)
	@echo "Files copied"
endif

all-tests: copy-files
ifeq ("$(wildcard ./.compiled-$(TOKEN))","")
	@echo "Compiling all source..."
	@javac -d $(COMPILE_DIR) lexer/tools/ToolRunner.java
	@java -cp $(COMPILE_DIR) lexer.tools.ToolRunner
	@find . -name "*.java" > $(SOURCE_FILE)
	@javac -d $(COMPILE_DIR) -cp $(COMPILE_DIR):lib/$(JUNIT_JAR):. @$(SOURCE_FILE)
	@rm $(SOURCE_FILE)
	@echo "compiled $(TOKEN)" > ./.compiled-$(TOKEN)
	@echo "Compiled"
endif

test-method: all-tests
	java -jar lib/$(JUNIT_JAR) -cp $(COMPILE_DIR) -m $(FQ_METHOD_NAME)
