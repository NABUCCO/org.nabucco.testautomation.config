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
package org.nabucco.testautomation.config.facade.datatype;

import org.junit.Assert;
import org.junit.Test;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.validation.ValidationType;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;

/**
 * TestConfigElementValidationTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class TestConfigElementValidationTest {

    @Test
    public void testValidation() throws Exception {

        TestConfigElement element = new TestConfigElement();
//        element.setName("Test");
        element.setDescription("Test Description");

        ValidationResult result = new ValidationResult();
        element.validate(result, ValidationType.DEEP);

        System.out.println(result);
        
        Assert.assertFalse(result.isEmpty());

    }

}
