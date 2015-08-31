@echo off

echo Setting temporary enviroment variables . . .
SET ZIP_HOME=C:\Program Files\7-Zip
SET PATH=%path%;%ZIP_HOME%;

echo Copying files to madness.jar . . .
7z a -tzip madness.jar .\copy2jar\*

pause