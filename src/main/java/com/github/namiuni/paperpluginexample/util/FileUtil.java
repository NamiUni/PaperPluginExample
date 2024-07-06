package com.github.namiuni.paperpluginexample.util;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * FileUtilクラスは、ファイルおよびディレクトリの操作に関するユーティリティメソッドを提供する。
 * このクラスはインスタンス化できないようにプライベートコンストラクタを持っている。
 */
@DefaultQualifier(NonNull.class)
public final class FileUtil {

    private FileUtil() {
    }

    /**
     * ディレクトリが存在しなければ作成する。
     *
     * @param path 作成を試みるディレクトリ
     * @throws IOException ディレクトリの作成に失敗した場合
     */
    public static void createDirectoriesIfNotExists(Path path) throws IOException {
        if (Files.exists(path) && (Files.isDirectory(path) || Files.isSymbolicLink(path))) {
            return;
        }

        Files.createDirectories(path);
    }
}
