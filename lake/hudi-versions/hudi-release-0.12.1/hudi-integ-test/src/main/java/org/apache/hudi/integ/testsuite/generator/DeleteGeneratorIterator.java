/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hudi.integ.testsuite.generator;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;

import java.util.Iterator;

/**
 * Lazy delete record generator.
 */
public class DeleteGeneratorIterator implements Iterator<GenericRecord> {

  // Use the full payload generator as default
  private GenericRecordFullPayloadGenerator generator;
  // iterator
  private Iterator<GenericRecord> itr;

  public DeleteGeneratorIterator(Iterator<GenericRecord> itr, String schemaStr, int minPayloadSize) {
    this.itr = itr;
    Schema schema = new Schema.Parser().parse(schemaStr);
    this.generator = new GenericRecordFullPayloadGenerator(schema, minPayloadSize);
  }

  @Override
  public boolean hasNext() {
    return itr.hasNext();
  }

  @Override
  public GenericRecord next() {
    GenericRecord newRecord = itr.next();
    return this.generator.generateDeleteRecord(newRecord);
  }
}
