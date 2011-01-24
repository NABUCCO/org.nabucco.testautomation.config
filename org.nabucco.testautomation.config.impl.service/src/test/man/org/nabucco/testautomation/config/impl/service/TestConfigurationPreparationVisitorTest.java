/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.nabucco.testautomation.config.impl.service;

import java.util.List;

import org.junit.Test;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.impl.service.engine.visitor.TestConfigurationPreparationVisitor;

import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.script.facade.datatype.dictionary.Action;
import org.nabucco.testautomation.script.facade.datatype.dictionary.Condition;
import org.nabucco.testautomation.script.facade.datatype.dictionary.Logger;
import org.nabucco.testautomation.script.facade.datatype.dictionary.Loop;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;
import org.nabucco.testautomation.script.facade.datatype.dictionary.base.TestScriptElement;
import org.nabucco.testautomation.script.facade.datatype.dictionary.base.TestScriptElementContainer;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import org.nabucco.testautomation.script.facade.datatype.metadata.MetadataLabel;


public class TestConfigurationPreparationVisitorTest {

	@Test
	public void testInheritBrand() throws Exception {
		
		TestConfigurationPreparationVisitor visitor = new TestConfigurationPreparationVisitor(null);
		TestConfiguration config = new TestConfiguration();
		Code env = new Code();
		env.setName("DEV");
		Code env2 = new Code();
		env2.setName("TEST");
		Code release = new Code();
		release.setName("Release 1.0");
		Code release2 = new Code();
		release2.setName("Release 2.0");
		config.setEnvironmentType(env);
		config.setReleaseType(release);
		Code brand1 = new Code();
		brand1.setName("Brand1");
		Code brand2 = new Code();
		brand2.setName("Brand2");
		Code brand3 = new Code();
		brand3.setName("Brand3");
		
		
		TestConfigElement sheet = new TestConfigElement();sheet.setName("sheet");sheet.setBrandType(brand1);
		TestConfigElement case1 = new TestConfigElement();case1.setName("case1");case1.setBrandType(brand1);
		TestConfigElement case2 = new TestConfigElement();case2.setName("case2");case2.setBrandType(brand2);
		TestConfigElement step1 = new TestConfigElement();step1.setName("step1");
		TestConfigElement step2 = new TestConfigElement();step2.setName("step2");
		TestConfigElement step3 = new TestConfigElement();step3.setName("step3");
		TestConfigElement step4 = new TestConfigElement();step4.setName("step4");
		TestConfigElement step5 = new TestConfigElement();step5.setName("step5");step5.setBrandType(brand3);
		TestConfigElement step6 = new TestConfigElement();step6.setName("step6");
		
		TestScript script1 = new TestScript();script1.setName("script1");
		TestScript script2 = new TestScript();script2.setName("script2");
		TestScript script3 = new TestScript();script3.setName("script3");
		Metadata metadata1 = new Metadata();metadata1.setName("metadata1");
		Metadata metadata2 = new Metadata();metadata2.setName("metadata2");
		Metadata metadata3 = new Metadata();metadata3.setName("metadata3");
		Action action1 = new Action();action1.setName("action1");action1.setMetadata(metadata1);
		Action action2 = new Action();action2.setName("action2");action2.setMetadata(metadata2);
		Action action3 = new Action();action3.setName("action3");action3.setMetadata(metadata3);
		
		PropertyList list1 = new PropertyList();list1.setName("List1");
		PropertyList list2 = new PropertyList();list2.setName("List2");
		PropertyList list3 = new PropertyList();list3.setName("List3");
		MetadataLabel label1 = new MetadataLabel();label1.setPropertyList(list1);
		MetadataLabel label2 = new MetadataLabel();label2.setPropertyList(list2);label2.setEnvironmentType(env2);
		MetadataLabel label3 = new MetadataLabel();label3.setPropertyList(list3);label3.setReleaseType(release2);
		metadata1.getLabelList().add(label1);
		metadata1.getLabelList().add(label2);
		metadata1.getLabelList().add(label3);
		add(new Condition(), script1.getTestScriptElementList());
		add(action1, script1.getTestScriptElementList());
		add(new Loop(), script1.getTestScriptElementList());
		add(action2, script1.getTestScriptElementList());
		add(new Logger(), script1.getTestScriptElementList());
		add(action3, script1.getTestScriptElementList());
		add(new Condition(), script1.getTestScriptElementList());
		
		add(script1, step1.getTestScriptList());
		add(script2, step3.getTestScriptList());
		add(script3, step5.getTestScriptList());
		
		add(sheet, config.getTestConfigElementList());
		add(case1, sheet.getTestConfigElementList());
		add(case2, sheet.getTestConfigElementList());
		add(step1, case1.getTestConfigElementList());
		add(step2, case1.getTestConfigElementList());
		add(step3, case1.getTestConfigElementList());
		add(step4, case2.getTestConfigElementList());
		add(step5, case2.getTestConfigElementList());
		add(step6, case2.getTestConfigElementList());
		
		visitor.visit(config);
		
		System.out.println("sheet:" + sheet.getBrandType().getName().getValue());
		System.out.println("case1:" + case1.getBrandType().getName().getValue());
		System.out.println("case2:" + case2.getBrandType().getName().getValue());
		System.out.println("step1:" + step1.getBrandType().getName().getValue());
		System.out.println("step2:" + step2.getBrandType().getName().getValue());
		System.out.println("step3:" + step3.getBrandType().getName().getValue());
		System.out.println("step4:" + step4.getBrandType().getName().getValue());
		System.out.println("step5:" + step5.getBrandType().getName().getValue());
		System.out.println("step6:" + step6.getBrandType().getName().getValue());
	}
	
	private void add(TestConfigElement element, List<TestConfigElementContainer> list) {
		TestConfigElementContainer c = new TestConfigElementContainer();
		c.setElement(element);
		c.setOrderIndex(list.size());
		list.add(c);
	}
	
	private void add(TestScript script, List<TestScriptContainer> list) {
		TestScriptContainer c = new TestScriptContainer();
		c.setTestScript(script);
		c.setOrderIndex(list.size());
		list.add(c);
	}
	
	private void add(TestScriptElement element, List<TestScriptElementContainer> list) {
		TestScriptElementContainer c = new TestScriptElementContainer();
		c.setElement(element);
		c.setOrderIndex(list.size());
		list.add(c);
	}
	
}
