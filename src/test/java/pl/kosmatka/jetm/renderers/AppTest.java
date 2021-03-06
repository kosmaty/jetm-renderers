/*
 * (C) Copyright 2013 Krzysztof Kosmatka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package pl.kosmatka.jetm.renderers;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import pl.kosmatka.jetm.renderers.html.JQueryTreetableRenderer;
import etm.core.aggregation.Aggregate;
import etm.core.aggregation.ExecutionAggregate;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void testRenderer() {
		ExecutionAggregate aggregate1 = new TestAggregate("test 1");
		ExecutionAggregate aggregate2 = new TestAggregate("test 2");
		ExecutionAggregate aggregate3 = new TestAggregate("test 3");
		ExecutionAggregate aggregate1_1 = new TestAggregate("test 1_1");
		ExecutionAggregate aggregate1_2 = new TestAggregate("test 1_2");
		ExecutionAggregate aggregate1_1_1 = new TestAggregate("test 1_1_1");
		ExecutionAggregate aggregate2_1 = new TestAggregate("test 2_1");
		ExecutionAggregate aggregate3_1 = new TestAggregate("test 3_1");

		aggregate1.getChilds().put(aggregate1_1.getName(), aggregate1_1);
		aggregate1.getChilds().put(aggregate1_2.getName(), aggregate1_2);
		aggregate1_1.getChilds().put(aggregate1_1_1.getName(), aggregate1_1_1);
		aggregate2.getChilds().put(aggregate2_1.getName(), aggregate2_1);
		aggregate3.getChilds().put(aggregate3_1.getName(), aggregate3_1);

		Map<String, Aggregate> map = new HashMap<String, Aggregate>();
		map.put(aggregate1.getName(), aggregate1);
		map.put(aggregate2.getName(), aggregate2);
		map.put(aggregate3.getName(), aggregate3);

		JQueryTreetableRenderer renderer = new JQueryTreetableRenderer();
		renderer.render(map);

		System.out.println(renderer.getHtmlDocument());
	}
}

class TestAggregate extends ExecutionAggregate {
	public TestAggregate(String name) {
		super(name);
		setChilds(new HashMap());
	}
}
