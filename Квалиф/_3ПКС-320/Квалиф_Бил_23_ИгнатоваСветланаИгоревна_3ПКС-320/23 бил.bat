@echo off
set /p website=write address: 
echo PING address %website%...
ping %website%
echo.
echo Pathping address %website%...
tracert %website%
pause
