@echo off
set /p website=write address: 
echo PING address %website%...
ping %website%
echo.
echo Tracert address %website%...
tracert %website%
pause
