#!/usr/bin/env sh

#     Compile the application code [*not* the test code].
#     Compiled .class files are placed in a new subdirectory called 'classes' [-d option].

#     In this example, any third-party library JARs are located in 'lib' and added to the classpath [-classpath option].
#     Remove this -classpath "lib/*" if you're not using any, which results in the following:
#     javac -d classes src/com/games/blackjack/client/*.java src/com/games/blackjack/controller/*.java src/com/games/blackjack/domain/*.java

#javac -d classes -classpath "lib/*" src/com/games/blackjack/client/*.java src/com/games/blackjack/controller/*.java src/com/games/blackjack/domain/*.java
javac -d classes src/com/css/client/*.java src/com/css/craps/*.java src/com/craps/app/*.java


#     Build the application JAR.
#     This example adds the directory tree of .class files starting at 'classes'.
#     Note that any third-party library JARs are *not* included in your application JAR, 
#     nor are any resource files, e.g., config files, data files, banner.txt, etc.

#jar --create --file blackjack-1.0.jar -C classes .
jar --create --file craps-1.0.jar -C classes .


#     Create the Javadoc.
#     The '-package' flag will include non-public classes and all class members except for private ones.
#     Again, any third-party library JARs are located in 'lib' and added to the classpath.
#     And again, remove this -classpath "lib/*" if you're not using any.

#     Running this only makes sense if you've taken the time to write API comments in your code.
#     API (javadoc) comments are important when the "product" is a library for other developers to use.
#     It's not as important when the "product" is a finished application, like with your project.
#     Just leave this commented out if not using.

#     javadoc -d doc --source-path src -classpath "lib/*" -package -windowtitle BlackJack com.games.blackjack.client com.games.blackjack.controller com.games.blackjack.domain