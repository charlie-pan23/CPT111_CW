#!/bin/sh


REPOSITORY=../repositories/maven
GROUP_XJTLU=xjtlu/cpt111

SOURCE_PATH=src
DESTINATION_PATH=build


java -cp .:${DESTINATION_PATH}:${REPOSITORY}/${GROUP_XJTLU}/xjtlu.cpt111.assignment.quiz.lib/0.0.1/xjtlu.cpt111.assignment.quiz.lib-0.0.1.jar \
	xjtlu.cpt111.assignment.quiz.ReadQuestions

