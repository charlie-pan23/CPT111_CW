
@echo off

set REPOSITORY=..\repositories\maven
set GROUP_XJTLU=xjtlu\cpt111

set SOURCE_PATH=src
set DESTINATION_PATH=build


java -cp .;%DESTINATION_PATH%;%REPOSITORY%\%GROUP_XJTLU%\xjtlu.cpt111.quiz.lib\0.0.1\xjtlu.cpt111.quiz.lib-0.0.1.jar ^
  xjtlu.cpt111.assignment.quiz.ReadQuestions

