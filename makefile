COMPILE_DIR=target
SRC_DIR=.
SOURCE_FILE=sources
JUNIT_JAR=lib/junit-platform-console-standalone-1.9.3.jar	
CURRENT_TIME=`date +%s`

prep:
ifneq (,$(wildcard ./.p-$(CURRENT_TIME)))
	@rm -rf lib
	@rm -rf tests
	@echo "prepped $(CURRENT_TIME)" > ./.p-$(CURRENT_TIME)
endif

clean: prep
ifneq (,$(wildcard ./.c-$(CURRENT_TIME)))
	@find . -name "*.class" -type f -delete
	@rm -rf $(COMPILE_DIR)
	@echo "cleaned $(CURRENT_TIME)" > ./.c-$(CURRENT_TIME)
endif

copy-files: prep clean
ifneq (,$(wildcard ./.f-$(CURRENT_TIME)))
	@mkdir lib
	@wget https://github.com/sfsu-csc-413-fall-2023/template-grader/blob/main/lib/junit-platform-console-standalone-1.9.3.jar -o ./lib/junit-platform-console-standalone-1.9.3.jar
	@wget https://github.com/sfsu-csc-413-fall-2023/template-grader/blob/main/tests/assignment-1-tests.tar
	@tar -xvf assignment-1-tests.tar
	@rm assignment-1-tests.tar
	@echo "copied $(CURRENT_TIME)" > ./.f-$(CURRENT_TIME)
endif

all-tests: copy-files
ifneq (,$(wildcard ./.compiled-$(CURRENT_TIME)))
	@find . -name "*.java" > $(SOURCE_FILE)
	@javac -d $(COMPILE_DIR) -cp $(COMPILE_DIR):$(JUNIT_JAR):. @$(SOURCE_FILE)
	@rm $(SOURCE_FILE)
	@echo "compiled $(CURRENT_TIME)" > ./.compiled-$(CURRENT_TIME)
endif

test-method: all-tests
	@java -jar lib/junit-platform-console-standalone-1.9.3.jar -cp target -m $(FQ_METHOD_NAME)