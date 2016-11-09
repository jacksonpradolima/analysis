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

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;

import java.util.Properties;

/**
 * Convert Utils Class
 *
 * @author Thiago Nascimento
 * @version 1.0.0
 * @since 2015-10-27
 */
public class ConvertUtils<T> {

    /**
     * Convert from string array to double array
     *
     * @param array Array will be converted
     *
     * @return A double array
     */
    public static double[] toDoubleArray(Object[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array shouldn't be null");
        }

        double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++) {
            result[i] = Double.valueOf(String.valueOf(array[i]).trim());
        }

        return result;
    }

    /**
     * Convert from string array to double array
     *
     * @param array Array will be converted
     *
     * @return A double array
     */
    public static double[] toDoubleArray(String[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array shouldn't be null");
        }

        double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++) {
            result[i] = Double.valueOf(array[i].trim());
        }

        return result;
    }

    /**
     * Convert from string array to int array
     *
     * @param array Array will be converted
     * @return A int array
     */
    public static int[] toIntArray(String[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array shouldn't be null");
        }

        int[] result = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.valueOf(array[i].trim());
        }

        return result;
    }

    /**
     * Convert from string array to int array
     *
     * @param array Array will be converted
     * @return A int array
     */
    public static int[] toIntArray(Object[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array shouldn't be null");
        }

        int[] result = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.valueOf(String.valueOf(array[i]).trim());
        }

        return result;
    }

    /**
     * Convert a HashMap with double values to Java Properties file
     *
     * @param values HashMap that will be converted
     * @return A Java Properties
     */
    public static Properties toPropertiesFile(Map<String, String> values) {
        Preconditions.checkNotNull(values, "HashMap cannot be null");

        Properties properties = new Properties();

        for (Entry<String, String> entry : values.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }

        return properties;
    }
}
