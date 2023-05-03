
@setlocal

@call :Addition
@pause
@exit /B %errorlevel%


:Addition
@set a=10
@set b=30

@echo "%a% + %b%"

@echo Waiting for a bit...
@timeout 10


@set /a "c=%a%+%b%"
@echo The answer is %c%

exit /B 0

