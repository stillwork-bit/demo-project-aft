#!/bin/sh

echo "Запускает тесты с тегом = $TEST_GROUPS"
echo "Запуск тестов"

mvn clean -Dsurefire.rerunFailingTestsCount=2 test -Dgroups=$TEST_GROUPS -Dmaven.test.failure.ignore=true