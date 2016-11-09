/*
 * Copyright 2016 Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.ufpr.inf.gres.core.util;

import com.google.common.base.Preconditions;

public class StringUtils {

    public static String getRepeteadString(String str, int times) {
        return getRepeteadString(str, times, "");
    }

    public static String getRepeteadString(String str, int times, String separator) {
        Preconditions.checkNotNull(str, "Str cannot be null");
        Preconditions.checkNotNull(separator, "Separator cannot be null");
        Preconditions.checkArgument(times > 0, "Times");

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < times; i++) {
            buffer.append(str);
            buffer.append(separator);
        }

        return buffer.toString();
    }

    public static String replaceFirst(String text, String regex, String replacement) {
        if (OSUtils.isWindows()) {
            text = text.replaceAll("\\\\", "/");
            regex = regex.replaceAll("\\\\", "/");
        }
        return text.replaceFirst(regex, replacement);
    }
}
