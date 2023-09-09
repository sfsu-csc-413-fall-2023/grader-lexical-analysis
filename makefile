COMPILE_DIR=target
SRC_DIR=.
SOURCE_FILE=sources
JUNIT_JAR=lib/junit-platform-console-standalone-1.9.3.jar	

clean:
	@find . -name "*.class" -type f -delete
	@rm -rf $(COMPILE_DIR)
	@rm -rf lib
	@rm -rf tests
	@echo "-- Clean finished --"

copy-files: clean
	