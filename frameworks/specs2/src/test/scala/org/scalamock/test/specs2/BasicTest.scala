// Copyright (c) 2011-2015 ScalaMock Contributors (https://github.com/paulbutcher/ScalaMock/graphs/contributors)
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package org.scalamock.test.specs2

import org.scalamock.test.mockable.TestTrait
import org.specs2.mutable.Specification
import org.scalamock.specs2.MockContext

/**
 *  Tests for mocks defined in test case scope
 *
 *  Tests for issue #25
 */
class BasicTest extends Specification {

  "Specs2 suite" should {
    "allow to use mock defined in test case scope" in new MockContext {
      val mockedTrait = mock[TestTrait]
      (mockedTrait.oneParamMethod _).expects(1).returning("one")
      (mockedTrait.oneParamMethod _).expects(2).returning("two")

      mockedTrait.oneParamMethod(1) must_== "one"
      mockedTrait.oneParamMethod(2) must_== "two"
    }

    "use separate call logs for each test case" in new MockContext {
      val mockedTrait = mock[TestTrait]
      (mockedTrait.oneParamMethod _).expects(3).returning("three")

      mockedTrait.oneParamMethod(3) must_== "three"
    }
  }
}
