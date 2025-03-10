/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.iceberg.spark.data.vectorized;

import java.util.Map;
import org.apache.iceberg.Schema;
import org.apache.iceberg.arrow.vectorized.VectorizedReaderBuilder;
import org.apache.iceberg.parquet.TypeWithSchemaVisitor;
import org.apache.iceberg.relocated.com.google.common.collect.Maps;
import org.apache.parquet.schema.MessageType;

public class VectorizedSparkParquetReaders {

  private VectorizedSparkParquetReaders() {}

  public static ColumnarBatchReader buildReader(
      Schema expectedSchema, MessageType fileSchema, boolean setArrowValidityVector) {
    return buildReader(expectedSchema, fileSchema, setArrowValidityVector, Maps.newHashMap());
  }

  public static ColumnarBatchReader buildReader(
      Schema expectedSchema,
      MessageType fileSchema,
      boolean setArrowValidityVector,
      Map<Integer, ?> idToConstant) {
    return (ColumnarBatchReader)
        TypeWithSchemaVisitor.visit(
            expectedSchema.asStruct(),
            fileSchema,
            new VectorizedReaderBuilder(
                expectedSchema,
                fileSchema,
                setArrowValidityVector,
                idToConstant,
                ColumnarBatchReader::new));
  }
}
