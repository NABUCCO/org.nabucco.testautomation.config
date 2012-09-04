/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.testautomation.config.facade.datatype.comparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;


/**
 * TestConfigElementSorter
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class TestConfigElementSorter implements
		Comparator<TestConfigElementContainer> {

	public void sort(List<TestConfigElementContainer> list) {
		Collections.sort(list, this);
		
		for (TestConfigElementContainer testConfigElementContainer : list) {
			this.sort(testConfigElementContainer.getElement().getTestConfigElementList());
		}
	}
	
	public void sort(TestConfigElement element) {
		List<TestConfigElementContainer> testConfigElementList = element.getTestConfigElementList();
		Collections.sort(testConfigElementList, this);
		
		for (TestConfigElementContainer testConfigElementContainer : testConfigElementList) {
			this.sort(testConfigElementContainer.getElement());
		}
	}
	
	@Override
	public int compare(TestConfigElementContainer o1,
			TestConfigElementContainer o2) {

		if (o1 != null && o2 != null && o1.getOrderIndex() != null
				&& o2.getOrderIndex() != null) {
			int order1 = o1.getOrderIndex().getValue();
			int order2 = o2.getOrderIndex().getValue();

			return order1 - order2;
		}
		return 0;
	}

}
