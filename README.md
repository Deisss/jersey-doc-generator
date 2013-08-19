jersey-doc-generator
====================

Jersey-doc-generator is a simple java project to build documentation from an existing [Jersey](http://jersey.java.net/)
project.

The program generate a JSON file which can be easily used to create documentation in HTML (or what you want).

Features:
  * Full url access map (GET/POST/PUT/DELETE, ...)
  * Full parameter support (PathParam, FormParam, CookieParam, ...)
  * Media type sypport (application/json, ...)
  * sub resource support

If you use [jersey-doc-annotation](https://github.com/Deisss/jersey-doc-annotation) project, it also support:
  * Uniplemented/Deprecated tag support
  * Documentation on class/method support
  * author/version support


__Note__: this project only create raw data, we recommand you
[jersey-doc-template](https://github.com/Deisss/jersey-doc-template)
for rendering them. See Rendering section below.


Installation
------------

The project use maven as compile tool, it does create a jar file auto-including everything and ready to use:

    mvn assembly:assembly

Will create the jar file: `./target/jersey-doc-generator-0.0.1-jar-with-dependencies.jar` depending on current
version (here version 0.0.1).

If you want, you can safely rename this file into `jersey-doc-generator.jar`.

**Other jar/content from 'target' folder are not needed for the rest of this tutorial, you can safely delete them.**



Usage
-----

### Example ###

Once you created the jar file (and everything went fine), we can start to use it. For that, imagine a basic
jersey project:
  * com
    * mycompany
      * User.java
      * RootResource.java

So the package would be `com.mycompany` and the jersey root into `RootResource.java`, like this:

    package com.mycompany;
    
    import javax.ws.rs.*;
    import javax.ws.rs.core.MediaType;

    @Path("core")
    public class RootResource {
      @GET
      public String get() {}
      
      @POST
      @Consumes(MediaType.APPLICATION_JSON)
      @Produces(MediaType.APPLICATION_JSON)
      public User post(User user) {}
    }

We define a really simple function (User is an empty class) imple class 
(the function content is empty for example purpose), 
we can now use jersey-doc-generator (assuming project is located on %PRJ% folder).

### Result ###

**To start jersey-doc-generator:**

    java -jar ./target/jersey-doc-generator-0.0.1.jar -p "%PRJ%/build/classes" -c "com.mycompany.RootResource"

Where build/classes contains all *.class project file, with same structure (`com/mycompany/RootResource.class`).

It will produce the following result:

    {
      "name" : "com.mycompany.RootResource",
      "path" : "core",
      "doc" : "",
      "author" : "",
      "version" : "",
      "produceList" : [ ],
      "consumeList" : [ ],
      "methodList" : [ {
        "name" : "get",
        "path" : "",
        "doc" : "",
        "author" : "",
        "version" : "",
        "produceList" : [ ],
        "consumeList" : [ ],
        "type" : "javax.ws.rs.GET",
        "inputList" : [ ],
        "output" : {
          "name" : "java.lang.String"
        },
        "subResource" : false,
        "unimplemented" : false,
        "deprecated" : false
      }, {
        "name" : "post",
        "path" : "",
        "doc" : "",
        "author" : "",
        "version" : "",
        "produceList" : [ "application/json" ],
        "consumeList" : [ "application/json" ],
        "type" : "javax.ws.rs.POST",
        "inputList" : [ {
          "name" : "",
          "context" : "",
          "type" : "com.mycompany.User",
          "defaultValue" : ""
        } ],
        "output" : {
          "name" : "com.mycompany.User"
        },
        "subResource" : false,
        "unimplemented" : false,
        "deprecated" : false
      } ],
      "unimplemented" : false,
      "deprecated" : false
    }

This example show how exaustive are data produced with jersey-doc-generator. Now you can use thoose data
to create beautifull doc.


### Rendering/presenting data ###

Now you get raw data from your project, you probably want to make them readable by anyone...

We recommand you [this template](https://github.com/Deisss/jersey-doc-template) to 
present results in a readable way.

This project is a simple html page with some javascript, able to load data stored into files, and show them to user.



Command line arguments
----------------------

On this example we see the most basic case using -c and -p arguments, here is the full list:
  * **--help**: display help
  * **--class** or **-c**: Specify the class to start from (in the example: com.mycompany.RootResource) (+1 arg: the class name)
  * **--path** or **-p**: The path where builded content is located (for Eclipse: ./build/classes from project root) (+1 arg: the path)
  * **--out** or **-o**: Don't print result on console, print into given file (+1 arg: the path)
  * **--type** or **-t**: The type (by default it's class), can be jar or war also, define the type of document the system have to parse
  * **--tmp**: change the tmp folder, by default it's /tmp on linux, and C:\\ on windows.



Limitations
-----------

Some limitation exist on this project:
  * The system is not able to find the root resource by itself, you must provide it yourself using -c parameter
  * The system will generate a .jar file of your project if you are using it from *.class
    * for this reason, jar executable (from JDK) should be OK from command line (type jar in command line to check)
    * this program should be authorize to write on tmp folder (see --help to change that folder)
  * If there is too many sub resource (include inside include inside include...) the system may raise a StackOverflowError

Except the -c problem, all of them should almost never be a problem.



Licence
-------

This project is licensed under MIT licence.
