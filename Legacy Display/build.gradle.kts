import net.darkhax.curseforgegradle.TaskPublishCurseForge
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    id("fabric-loom")
    kotlin("jvm")
    id("com.google.devtools.ksp")
    id("com.modrinth.minotaur")
    id("net.darkhax.curseforgegradle")
    id("co.uzzu.dotenv.gradle")
}
val archivesBaseName = providers.gradleProperty("archives_base_name")
val modVersion = providers.gradleProperty("mod_version")
val mavenGroup = providers.gradleProperty("maven_group")
val minecraftVersion = providers.gradleProperty("minecraft_version")
val yarnMappings = providers.gradleProperty("yarn_mappings")
val loaderVersion = providers.gradleProperty("loader_version")
val fabricVersion = providers.gradleProperty("fabric_version")
val fabricLanguageKotlinVersion = providers.gradleProperty("fabric_language_kotlin_version")
val owoVersion = providers.gradleProperty("owo_version")
val kspOwoConfigVersion = providers.gradleProperty("ksp_owo_config_version")
val javaVersion = providers.gradleProperty("java_version")
base.archivesName = archivesBaseName.get()
version = modVersion.get()
group = mavenGroup.get()
repositories {
    maven("https://maven.wispforest.io")
    maven("https://maven.kosmx.dev/")
}
dependencies {
    minecraft("com.mojang:minecraft:${minecraftVersion.get()}")
    mappings("net.fabricmc:yarn:${yarnMappings.get()}:v2")
    modImplementation("net.fabricmc:fabric-loader:${loaderVersion.get()}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fabricVersion.get()}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${fabricLanguageKotlinVersion.get()}")
    modImplementation("io.wispforest:owo-lib:${owoVersion.get()}")
    ksp("dev.kosmx.kowoconfig:ksp-owo-config:${kspOwoConfigVersion.get()}")
}
tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.get()
        targetCompatibility = javaVersion.get()
        options.release = javaVersion.get().toInt()
    }
    withType<JavaExec>().configureEach { defaultCharacterEncoding = "UTF-8" }
    withType<Javadoc>().configureEach { options.encoding = "UTF-8" }
    withType<Test>().configureEach { defaultCharacterEncoding = "UTF-8" }
    withType<KotlinCompile>().configureEach {
        compilerOptions {
            extraWarnings = true
            jvmTarget = JvmTarget.valueOf("JVM_${javaVersion.get()}")
        }
    }
    named<Jar>("jar") {
        val rootLicense = layout.projectDirectory.file("LICENSE")
        val parentLicense = layout.projectDirectory.file("../LICENSE")
        val licenseFile = when {
            rootLicense.asFile.exists() -> {
                logger.lifecycle("Using LICENSE from project root: ${rootLicense.asFile}")
                rootLicense
            }
            parentLicense.asFile.exists() -> {
                logger.lifecycle("Using LICENSE from parent directory: ${parentLicense.asFile}")
                parentLicense
            }
            else -> {
                logger.warn("No LICENSE file found in project or parent directory.")
                null
            }
        }
        licenseFile?.let {
            from(it) {
                rename { original -> "${original}_${archiveBaseName.get()}" }
            }
        }
    }
    processResources {
        val stringModVersion = modVersion.get()
        val stringLoaderVersion = loaderVersion.get()
        val stringFabricVersion = fabricVersion.get()
        val stringFabricLanguageKotlinVersion = fabricLanguageKotlinVersion.get()
        val stringMinecraftVersion = minecraftVersion.get()
        val stringJavaVersion = javaVersion.get()
        val stringOwoVersion = owoVersion.get()
        inputs.property("modVersion", stringModVersion)
        inputs.property("loaderVersion", stringLoaderVersion)
        inputs.property("fabricVersion", stringFabricVersion)
        inputs.property("fabricLanguageKotlinVersion", stringFabricLanguageKotlinVersion)
        inputs.property("minecraftVersion", stringMinecraftVersion)
        inputs.property("javaVersion", stringJavaVersion)
        inputs.property("owoVersion", stringOwoVersion)
        filesMatching("fabric.mod.json") {
            expand(
                mapOf(
                    "version" to stringModVersion,
                    "fabricloader" to stringLoaderVersion,
                    "fabric_api" to stringFabricVersion,
                    "fabric_language_kotlin" to stringFabricLanguageKotlinVersion,
                    "minecraft" to stringMinecraftVersion,
                    "java" to stringJavaVersion,
                    "owo_version" to stringOwoVersion
                )
            )
        }
        filesMatching("*.mixins.json") { expand(mapOf("java" to stringJavaVersion)) }
    }
    java {
        toolchain.languageVersion = JavaLanguageVersion.of(javaVersion.get())
        sourceCompatibility = JavaVersion.toVersion(javaVersion.get().toInt())
        targetCompatibility = JavaVersion.toVersion(javaVersion.get().toInt())
        withSourcesJar()
    }
    register<TaskPublishCurseForge>("publishCurseForge") {
        disableVersionDetection()
        apiToken = env.fetch("CURSEFORGE_TOKEN", "")
        val file = upload(478650, remapJar)
        file.displayName = "[${minecraftVersion.get()}] Legacy Display"
        file.addEnvironment("Client")
        file.changelog = ""
        file.releaseType = "release"
        file.addModLoader("Fabric")
        file.addGameVersion(minecraftVersion.get())
    }
}
modrinth {
    token.set(env.fetch("MODRINTH_TOKEN", ""))
    projectId.set("legacy-display")
    uploadFile.set(tasks.remapJar)
    gameVersions.addAll(minecraftVersion.get())
    versionName.set("[${minecraftVersion.get()}] Legacy Display")
    dependencies {
        required.project("fabric-api", "fabric-language-kotlin", "owo-lib")
        optional.project("modmenu")
    }
}