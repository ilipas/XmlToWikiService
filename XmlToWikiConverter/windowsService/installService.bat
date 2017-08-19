REM Service display name
set SERVICE_NAME=XmlToWikiService
REM Service name description
set SERVICE_DESCRIPTION=Xml to Wiki Service

REM User defined input directory path passed as parameter to the start method
set INPUT_DIRECTORY_PATH=input
REM User defined output directory path passed as parameter to the start method
set OUTPUT_DIRECTORY_PATH=output

REM Service description
set PR_DESCRIPTION=%SERVICE_DESCRIPTION%

REM Path to the service application executable directory (xtwService64.exe file)
set SERVICE_PATH=C:\Users\...<path>

REM Path to service application executable file (has to be absolute path)
set PR_INSTALL=%SERVICE_PATH%\xtwService64.exe
 
REM Service log configuration
set PR_LOGPREFIX=%SERVICE_NAME%
set PR_LOGPATH=%SERVICE_PATH%
set PR_STDOUTPUT=%PR_LOGPATH%\stdout.txt
set PR_STDERROR=%PR_LOGPATH%\stderr.txt
set PR_LOGLEVEL=Debug
 
REM Path to java installation
set PR_JVM=%JAVA_HOME%\jre\bin\server\jvm.dll
REM Path to service's jar file
set PR_CLASSPATH=xmltowiki.jar
 
REM Startup configuration
REM Service startup mode can be either auto or manual
set PR_STARTUP=auto
set PR_STARTMODE=jvm
REM Class that contains the startup method.
set PR_STARTCLASS=com.ili.pas.main.MainService
REM Name of method to be called when service is started.
set PR_STARTMETHOD=start
REM List of parameters that will be passed to StartClass. 
REM Parameters are separated using either # or ; character.
set PR_STARTPARAMS=%INPUT_DIRECTORY_PATH%;%OUTPUT_DIRECTORY_PATH%
 
REM Shutdown configuration
set PR_STOPMODE=jvm
REM Class that contains the stop method.
set PR_STOPCLASS=com.ili.pas.main.MainService
REM Name of method to be called when service is started.
set PR_STOPMETHOD=stop
 
REM JVM configuration
set PR_JVMMS=256
set PR_JVMMX=1024
set PR_JVMSS=4000
 
REM Install service 
%SERVICE_PATH%\xtwService64.exe //IS//%SERVICE_NAME%