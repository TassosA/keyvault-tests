Key Vault Tester
================

This project is based on the "Getting started with Quarkus" samples
You can find the original documentation of the sample at https://github.com/quarkusio/quarkus-quickstarts/blob/main/getting-started/README.md 

Key Vault Tester can access three different vaults, depending on the value of the "Environment" property, which can be *Dev*, *Stage* or *Prod*.  The vault URL for each vault must be provided in the respective properties.  All properties are defined in the `application.properties' file. 

For *Dev* environments, it is assumed that the code will run outside Azure, hence the authentication against the key vault must be done via an Azure service principal.  The *clientId*, *clientSecret* and *tenantId* values for the principal are defined in the `application.properties` file (and can be overriden by respective environmental variables as needed).   For the other environments, it is assumed that the code will run inside an Azure Container App and will leverage Azure's managed identities to be granted access to the key vault.

KeyVaultTester by default listens at port 8080 and has the following GET endpoints:
- `/keyvault/secret/{secretName}`, which returns the value of a secret 
- `/keyvault/key/{keyName}`, which returns the contents of the key.  The code can be adjusted to perform any desired operation using the key.
- `/keyvault/certificate/{certName}`, which returns the subject of a certificate.  The code can be adjusted to perform any desired operation using the certificate.

In the `application.properties` file you will also find some test entries (i.e. secret, key and cert names) to use for calling the above endpoints.

If you wish to use parts of the code in your project, you will also need to add to your POM.xml the parts of the supplied POM that are highlighted with *BEGIN / END* comments, in order to add to your project the appropriate Azure SDK dependencies.

Compilation commands:
---------------------
To compile it and run it in "normal" java, you can use one of the following:
- `quarkus -dev` (if quarkus utility has been deployed, see https://quarkus.io/get-started/)
- `./mvnw quarkus:dev`

If you wish to generate a container image, then pass `-Dquarkus.container-image.build=true` as a parameter.  Also take a look at https://quarkus.io/guides/container-image

 ***Note***:  
 When generating a container image, you may get an error from the checkstyle plugin about a missing dependency.  You can safely ignore this error. 
