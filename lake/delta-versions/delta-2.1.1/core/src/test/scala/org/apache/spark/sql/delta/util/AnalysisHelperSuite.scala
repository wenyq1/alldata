/*
 * Copyright (2021) The Delta Lake Project Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.sql.delta.util

import org.apache.spark.sql.QueryTest
import org.apache.spark.sql.test.SharedSparkSession

class AnalysisHelperSuite extends QueryTest with SharedSparkSession {

  test("should not throw NullPointerException when Exception has null description") {
    class FakeAnalysisHelper extends AnalysisHelper {
      def throwInterruptedException(): Unit = super.improveUnsupportedOpError {
        throw new InterruptedException()
      }
    }

    // Should throw original exception
    assertThrows[InterruptedException] {
      new FakeAnalysisHelper {}.throwInterruptedException()
    }
  }
}
