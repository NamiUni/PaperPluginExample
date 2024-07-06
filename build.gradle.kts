import xyz.jpenilla.resourcefactory.bukkit.Permission
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml.Load

plugins {
    // Java
    id("java")

    // fatjar用のプラグイン。
    id("io.github.goooler.shadow") version "8.1.7"

    // ビルド時にpaper-plugin.ymlを生成するプラグイン。
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.1.1"

    // IntelliJ IDEAでマインクラフトサーバーを立ち上げるプラグイン。
    id("xyz.jpenilla.run-paper") version "2.3.0"
}

// プロジェクトが依存関係となるライブラリをダウンロードする場所を指定する。
repositories {
    mavenCentral()

    // PaperAPIのリポジトリ
    maven("https://repo.papermc.io/repository/maven-public/")
}

// プロジェクトが使用する外部ライブラリを指定する。これにより、ビルド時に必要なライブラリが自動的にダウンロードされる。
dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // PaperAPIはコンパイル時には必要だが、実行時には不要なのでcompileOnlyで定義する。
    compileOnly("io.papermc.paper", "paper-api", "1.21-R0.1-SNAPSHOT")

    // 設定ファイル用のライブラリを指定する。
    // Paperに内包されている設定関係のAPIは使いづらい。
    implementation("org.spongepowered", "configurate-hocon", "4.2.0-SNAPSHOT")
}

// paper-plugin.ymlの中身を定義する。
paperPluginYaml {
    author = "Unitarou"
    website = "https://morino.party"
    apiVersion = "1.21"
    description = "初心者用のサンプルプラグイン"
    version = "1.0-SNAPSHOT"

    // Paperにこのプラグインのメインクラスとブートストラップクラスの場所を教える必要がある。
    val mainPackage = "com.github.namiuni.paperpluginexample"
    main = "$mainPackage.PaperPluginExample"
    bootstrapper = "$mainPackage.PaperPluginExampleBootstrapper"

    // 所謂前提プラグインなどを定義する。
    // ここで定義する事でPaperサーバー起動時に前提プラグインのインストールをチェックできる。
    dependencies {
        server("LuckPerms", Load.BEFORE, true)
    }

    // 権限を定義する。
    // ここで定義する事でLuckPermsなどの権限プラグインで権限を確認しやすくできる。
    // プレイヤーにデフォルトで付与するかなども定義できる。
    permissions {
        register("paperpluginexample.command.reload") {
            description = "設定をリロードするコマンドの権限"
            default = Permission.Default.OP
        }
        register("paperpluginexample.command.angryzombifiedpiglin") {
            description = "ネザーのゾンビピグリンを怒らせる為のコマンドの権限"
            default = Permission.Default.TRUE
        }
        register("paperpluginexample.command.aboveteleport") {
            description = "指定したエンティティを1km上空にテレポートさせるコマンドの権限"
        }
    }
}

// Javaのバージョンを指定する事で、他の誰かがこのプロジェクトを読み込んだときに自動的にそのバージョンが適用される。
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

// ビルド時などのタスクを定義できる。
tasks {
    test {
        useJUnitPlatform()
    }

    // コンパイル時の文字コードをUTF-8に指定する
    compileJava {
        this.options.encoding = Charsets.UTF_8.name()
    }

    // fatjarを生成する時に実行するタスク。
    shadowJar {
        // jarファイルの名前の末尾に設定する文字列。指定しなければ末尾にallが付く。
        this.archiveClassifier.set(null as String?)

        // jarファイルに付けるバージョンの文字列。paperPluginYamlのversionから"1.0-SNAPSHOT"を引っ張ってくる。
        this.archiveVersion.set(paperPluginYaml.version)

    }

    // サーバーを起動する時に実行するタスク。
    runServer {
        // Paperのバージョンを指定する。
        minecraftVersion("1.21")

        // サーバー起動時に自動的にインストールするプラグインを定義する。
        downloadPlugins {
            url("https://download.luckperms.net/1549/bukkit/loader/LuckPerms-Bukkit-5.4.134.jar")
        }
    }
}
