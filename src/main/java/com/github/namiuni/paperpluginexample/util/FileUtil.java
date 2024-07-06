/*
 * PaperPluginExample
 *
 * Copyright (c) 2024. Namiu (Unitarou)
 *                    Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
