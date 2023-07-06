@echo off
for /f "tokens=3" %%a in ('route print ^| findstr "\<10.37.105.1\>"') do set gateway=%%a
ping %gateway%
pause
