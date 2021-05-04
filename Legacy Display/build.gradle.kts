plugins {
    id("fabric-loom")
    id("maven-publish")
    val kotlinVersion: String by System.getProperties()
    kotlin("jvm").version(kotlinVersion)
}
base {
    val archivesBaseNameTwo: String by project
    archivesBaseName = archivesBaseNameTwo
}
val modVersion: String by project
version = modVersion
val mavenGroup: String by project
group = mavenGroup
minecraft {}
repositories {
    maven("https://maven.fabricmc.net/")
    maven("https://maven.shedaniel.me/")
}
dependencies {
    val minecraftVersion: String by project
    minecraft("com.mojang:minecraft:$minecraftVersion")
    val yarnMappings: String by project
    mappings("net.fabricmc:yarn:$yarnMappings:v2")
    val loaderVersion: String by project
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    val fabricVersion: String by project
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
    val fabricKotlinVersion: String by project
    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")
    val modMenuVersion: String by project
    modImplementation("io.github.prospector:modmenu:$modMenuVersion")
    val clothConfigVersion: String by project
    modApi("me.shedaniel.cloth:cloth-config-fabric:$clothConfigVersion") {
        exclude("net.fabricmc.fabric-api")
    }
}
tasks {
    val javaVersion = JavaVersion.VERSION_1_8.toString()
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions { jvmTarget = javaVersion }
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    jar { from("LICENSE") }
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        inputs.property("version", project.version)
        from(sourceSets["main"].resources.srcDirs) {
            include("fabric.mod.json")
            expand(mutableMapOf("version" to project.version))
        }
        from(sourceSets["main"].resources.srcDirs) { exclude("fabric.mod.json") }
    }
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }
    artifacts { archives(sourcesJar) }
}