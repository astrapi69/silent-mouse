## Deployment Instructions

Before creating a new version, consider the following changes:

1. Change all SNAPSHOT versions in the following files:
    * `CHANGELOG.md`
    * `gradle.properties`
    * `messages.properties`
2. Set the property `createIzPackInstaller` to `true` in the `gradle.properties` file.
3. Execute the `clean` task to delete the `build` directory.
4. Execute the `withAllDependendiesJar` task to create the jar with the suffix `*-all.jar`.
5. Execute the `signJar` task process with Gradle. This will generate the jar files in the `build/libs` directory and the signed jar files in the `build/signed` directory.
6. Run the `izPackCreateInstaller` task. This will automatically get the signed jar file and generate the installation jar file in the project's distributions directory `build/distributions`.

The generated signed installation jar file can now be deployed for installation on any OS.

For more detailed information, see the IzPack task in the `build.gradle` file or visit the [Gradle IzPack Plugin page](https://github.com/bmuschko/gradle-izpack-plugin).
