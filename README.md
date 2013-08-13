jersey-doc-generator
====================

Jersey-doc-generator is a simple java project to build documentation from an existing [Jersey](http://jersey.java.net/)
project.

The program generate a JSON file which can be easily used into templates to create documentation in HTML way.

Features:
  * Full url access map (including GET/POST/PUT/DELETE, ...)
  * Full parameter support (PathParam, FormParam, CookieParam, ...)
  * sub resource support

If you use [jersey-doc-annotation](https://github.com/Deisss/jersey-doc-annotation) project, it also support:
  * Documentation on class/method
  * author/version support



Installation
------------

The project use maven as compile tool, it does create a jar file auto-including everything and ready to use:

    mvn assembly:assembly

Will create the jar file: **./target/jersey-doc-generator-0.0.1-jar-with-dependencies.jar** depending on current
version (here version 0.0.1).

If you want, you can safely rename this file into __jersey-doc-generator.jar__.

__Other jar/content from this folder are not needed for the rest of this tutorial.__



Usage
-----

Once you created the jar file (and everything went fine), we can start to use it. For that, let's imagine first a basic
jersey project (we describe the structure):
  * com
    * mycompany
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

Quite simple class (the function content is empty for example purpose), 
jersey-doc-generator will produce the following result:




**The project does not support auto-check root resource, you must provide it by yourselve**
