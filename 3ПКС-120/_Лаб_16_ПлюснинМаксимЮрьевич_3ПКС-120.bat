@echo off 
setlocal
call :restart
pause
exit /B %errorlevel%

:restart
@echo "Waiting for restart"


shutdown -r -t 120


exit /B 0