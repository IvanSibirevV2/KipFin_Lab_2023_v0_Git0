@echo off

set /P website="Insert web-domain: "

echo Ping Server... %website%...
ping %website%

echo.
echo Tracert rout to: %website%...
tracert %website%

pause