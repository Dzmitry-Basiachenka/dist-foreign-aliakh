# OpenAPI codegen usage

Full guide can be found here: [OpenAPI Codegen Guide](https://github.com/OpenAPITools/openapi-generator)

Run the following gradle task from `tools/swagger-api` directory to re-generate code:
```
 openApiGenerate
```
or from the project root folder:
```
gradlew -p tools/swagger-api <task>    
```

It will generate api and model packages (output directories `modelPackage` and `apiPackage`[can be changed](https://github.com/CopyrightClearanceCenter/dist-foreign/blob/master/tools/swagger-api/src/main/resources/swagger-config.json)).
There're different flags that can be used to [adjust the generation flow](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-gradle-plugin#configuration) (specify packages, output dirs and etc).\
There's also possibility to re-generate APIs, models, or just to specify the list of files.

Here's an example of generated stubs for
[FDA API](https://github.com/CopyrightClearanceCenter/dist-foreign/tree/master/dist-foreign-ui/src/main/java/com/copyright/rup/dist/foreign/ui/rest/gen).
Templates are configurable, [mustache templates](https://mustache.github.io/) is the underlying template format.
