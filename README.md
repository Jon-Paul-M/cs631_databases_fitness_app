# Fitness Application

This is a spring boot app.

The quickest way get the app running is to use maven:

		mvn spring-boot:run
then point your browser [here](http://localhost:8080/example/)

This is the long, more useful way:

I recommend using [eclipse](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygen2)

From the eclipse market place install the Spring Tools. (Search for _STS_)

To get the app project/src into eclipse, go to File -> Import -> Existing Maven Projects. Navigate to where you checked it out.

You can check out the app using SourceTree, the Eclipse built-in git functionality, or command line. 
The eclipse built in is not 100% featureful, so I use it combined with the CLI.


The main entry point to the application is this class:
	edu.njit.cs631.fitness.Application

Once you have the STS plugin you can right click on that class, select: Run As -> Spring Boot App

After running the first time, you can rerun it from the run icon up top (green circle with a white triangle).

Running the app this way from eclipse, changes to classes, templates, etc will be automatically reloaded.

Look for some more documentation in the wiki.