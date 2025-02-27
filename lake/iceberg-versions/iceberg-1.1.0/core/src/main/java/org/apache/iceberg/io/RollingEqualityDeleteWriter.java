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
package org.apache.iceberg.io;

import java.util.List;
import org.apache.iceberg.DeleteFile;
import org.apache.iceberg.PartitionSpec;
import org.apache.iceberg.StructLike;
import org.apache.iceberg.deletes.EqualityDeleteWriter;
import org.apache.iceberg.encryption.EncryptedOutputFile;
import org.apache.iceberg.relocated.com.google.common.base.Preconditions;
import org.apache.iceberg.relocated.com.google.common.collect.Lists;

/**
 * A rolling equality delete writer that splits incoming deletes into multiple files within one
 * spec/partition based on the target file size.
 */
public class RollingEqualityDeleteWriter<T>
    extends RollingFileWriter<T, EqualityDeleteWriter<T>, DeleteWriteResult> {

  private final FileWriterFactory<T> writerFactory;
  private final List<DeleteFile> deleteFiles;

  public RollingEqualityDeleteWriter(
      FileWriterFactory<T> writerFactory,
      OutputFileFactory fileFactory,
      FileIO io,
      long targetFileSizeInBytes,
      PartitionSpec spec,
      StructLike partition) {
    super(fileFactory, io, targetFileSizeInBytes, spec, partition);
    this.writerFactory = writerFactory;
    this.deleteFiles = Lists.newArrayList();
    openCurrentWriter();
  }

  @Override
  protected EqualityDeleteWriter<T> newWriter(EncryptedOutputFile file) {
    return writerFactory.newEqualityDeleteWriter(file, spec(), partition());
  }

  @Override
  protected void addResult(DeleteWriteResult result) {
    Preconditions.checkArgument(
        !result.referencesDataFiles(), "Equality deletes cannot reference data files");
    deleteFiles.addAll(result.deleteFiles());
  }

  @Override
  protected DeleteWriteResult aggregatedResult() {
    return new DeleteWriteResult(deleteFiles);
  }
}
