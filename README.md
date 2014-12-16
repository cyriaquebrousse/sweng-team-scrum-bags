Scrum Tool App
==============

An application made for the scrum development method


Build
-----
To build the project you first need to generate the endpoint libraries. We used the google cloud plugin for eclipse to do this (SDK 14) but you can also do it via command line as described here:

<https://cloud.google.com/appengine/docs/java/endpoints/endpoints_tool>

You can then launch the tests and measure code coverage by executing:
	ant clean emma debug install test
in the ScrumToolAppTest directory. Or simply build the project by running:
	ant clean debug
in the ScrumToolApp directory.
