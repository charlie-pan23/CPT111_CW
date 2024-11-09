
@echo off

set REPOSITORY=..\repositories\maven
set GROUP_XJTLU=xjtlu\cpt111

set SOURCE_PATH=src
set DESTINATION_PATH=build


rem del %SOURCE_PATH%\xjtlu\cpt111\assignment\quiz\*.class
rem del %SOURCE_PATH%\module-info.class
rmdir /s /q %DESTINATION_PATH%
