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

package com.example.proxy.mockitostyle

import com.example.{Order, Warehouse}

import org.scalatest.WordSpec
import org.scalamock.scalatest.proxy.MockFactory

// This is a reworked version of the example from Martin Fowler's article
// Mocks Aren't Stubs http://martinfowler.com/articles/mocksArentStubs.html
class OrderTest extends WordSpec with MockFactory {
  
  "An order" when {
    "in stock" should {
      "remove inventory" in {
        val mockWarehouse = stub[Warehouse]
        
        mockWarehouse.when('hasInventory)("Talisker", 50).returns(true)
        
        val order = new Order("Talisker", 50)
        order.fill(mockWarehouse)
        
        assert(order.isFilled)
        mockWarehouse.verify('remove)("Talisker", 50).once
      }
    }
    
    "out of stock" should {
      "remove nothing" in {
        val mockWarehouse = stub[Warehouse]
        mockWarehouse.when('hasInventory)(*, *).returns(false)
        
        val order = new Order("Talisker", 50)
        order.fill(mockWarehouse)
        
        assert(!order.isFilled)
      }
    }
  }
}
