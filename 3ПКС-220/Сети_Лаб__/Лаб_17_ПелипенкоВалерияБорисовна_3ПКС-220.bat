@setlocal
@call :elka
@pause
exit /B %errorlevel%

:elka
@echo off
@start elka.mp3
@timeout /t 14 /nobreak >nul
@echo ____0____
@timeout /t 14 /nobreak >nul
@echo ___000___
@timeout /t 14 /nobreak >nul
@echo __00000__
@timeout /t 14 /nobreak >nul
@echo _0000000_
@timeout /t 14 /nobreak >nul
@echo 000000000
@timeout /t 14 /nobreak >nul
@echo ___111___
@timeout /t 14 /nobreak >nul
@echo ___111___
exit /B 0