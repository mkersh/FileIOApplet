javac -target 1.5 localfile.java
jar cvf localfile.jar localfile.class
rem keytool -genkey -alias mydomain -keyalg RSA -keystore keystore.jks -keysize 2048
jarsigner -keystore keystore.jks localfile.jar mydomain
copy localfile.jar ..\BloggerBackup
