@echo off
:loop
echo Pinging mozilla.org
ping -n 1 mozilla.org
echo.
echo Pinging rutube.ru
ping -n 1 rutube.ru
echo.
echo Pinging yahoo.com
ping -n 1 yahoo.com
echo.
echo Tracert mozilla.org
tracert -d mozilla.org
echo.
echo Tracert rutube.ru
tracert -d rutube.ru
echo.
echo Tracert yahoo.com
tracert -d yahoo.com
echo.
goto loop