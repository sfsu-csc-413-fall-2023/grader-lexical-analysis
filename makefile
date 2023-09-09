COMPILE_DIR=target
SRC_DIR=.
SOURCE_FILE=sources
JUNIT_JAR=lib/junit-platform-console-standalone-1.9.3.jar	

prep:
	@rm -rf lib
	@rm -rf tests
	@echo "-- Clean finished --"

clean:
	@find . -name "*.class" -type f -delete
	@rm -rf $(COMPILE_DIR)

copy-files: prep clean
	@mkdir lib
	@wget https://github.com/sfsu-csc-413-fall-2023/template-grader/blob/main/lib/junit-platform-console-standalone-1.9.3.jar -o ./lib/junit-platform-console-standalone-1.9.3.jar
	@wget https://github.com/sfsu-csc-413-fall-2023/template-grader/blob/main/tests/assignment-1-tests.tar
	@tar -xvf assignment-1-tests.tar
	@rm assignment-1-tests.tar

all-tests: clean
	@echo "Compiling for testing..."
	@find . -name "*.java" > $(SOURCE_FILE)
	@javac -d $(COMPILE_DIR) -cp $(COMPILE_DIR):$(JUNIT_JAR):. @$(SOURCE_FILE)
	@rm $(SOURCE_FILE)

test-method: all-tests
	@java -jar lib/junit-platform-console-standalone-1.9.3.jar -cp target -m $(FQ_METHOD_NAME)