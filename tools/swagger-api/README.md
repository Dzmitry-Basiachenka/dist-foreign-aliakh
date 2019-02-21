# Swagger codegen usage

Full guide can be found here: https://github.com/swagger-api/swagger-codegen

Run the following gradle task to re-generate code:
```
 generateSwaggerCodeApi
```
It will generate api, configuration and model packages (output directories can be changed).\
There're different flags that can be used to adjust the generation flow (specify packages, output dirs and etc).\
More flexible way is to specify patterns for files or directories that are to be ignored during generations. 
This file is placed together with sources like _.gitignore_ and named _**.swagger-codegen-ignore**_.

There's also possibility to re-generate APIs, models, or just to specify the list of files. 

Here's an example of generated stubs for 
[requests API](https://github.com/CopyrightClearanceCenter/dist-foreign/tree/master/dist-foreign-ui/src/main/java/com/copyright/rup/dist/foreign/ui/gen).\
Templates are configurable, [mustache templates](https://mustache.github.io/) is the underlying template format. 


There's one drawback: this tool generates maven-project. It requires removing `pom.xml` file, creating and supporting
our own `build.gradle` file.

There's number of gradle plugins that can be used to generate code. 
[Gradle Swagger Generator Plugin](https://github.com/int128/gradle-swagger-generator-plugin) looks pretty simple.

# PDF generation from Swagger document

To generate PDF run the following task and find PDF in build/asciidoc/pdf folder:
```
asciidoctor
```

Actions about PDF update:
1. When new REST is implemented - update file src/docs/asciidoc/working_rest_adoc
2. If Additional information is needed for the REST - create folder with REST tag name in folder src/docs/asciidoc and make sure that dynamic path is configured in build.gradle 
