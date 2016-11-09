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

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

/**
 * Image Utils Class
 *
 * @author Thiago Nascimento
 * @since 2015-10-26
 * @version 1.0.0
 */
public class ImageUtils {

    /*public static Image getFromFile(Object parent, String filename) {
        return getFromFile(parent, "/resources/", filename);
    }*/
    public static Image getFromFile(Object parent, String folder, String filename) {
        return Toolkit.getDefaultToolkit().getImage(parent.getClass().getResource(folder + filename));
    }

    public static Image getFromFile(Object parent, String filename) {       
        return Toolkit.getDefaultToolkit().getImage(parent.getClass().getClassLoader().getResource("images/" + filename));
    }
}
