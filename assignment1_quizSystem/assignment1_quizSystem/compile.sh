#!/bin/sh


REPOSITORY=../repositories/maven
GROUP_XJTLU=xjtlu/cpt111

SOURCE_PATH=src
DESTINATION_PATH=build


javac -sourcepath ${SOURCE_PATH} \
  -d "${DESTINATION_PATH}" \
  -p .:${REPOSITORY}/${GROUP_XJTLU}/xjtlu.cpt111.assignment.quiz.lib/0.0.1/xjtlu.cpt111.assignment.quiz.lib-0.0.1.jar \
  ${SOURCE_PATH}/xjtlu/cpt111/assignment/quiz/*.java

