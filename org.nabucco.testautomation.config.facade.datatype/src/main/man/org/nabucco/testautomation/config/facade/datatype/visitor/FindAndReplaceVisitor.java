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
package org.nabucco.testautomation.config.facade.datatype.visitor;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.config.facade.datatype.attribute.TextAttributeValue;
import org.nabucco.testautomation.property.facade.datatype.FileProperty;
import org.nabucco.testautomation.property.facade.datatype.NumericProperty;
import org.nabucco.testautomation.property.facade.datatype.SqlProperty;
import org.nabucco.testautomation.property.facade.datatype.TextProperty;
import org.nabucco.testautomation.property.facade.datatype.XPathProperty;
import org.nabucco.testautomation.property.facade.datatype.XmlProperty;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.facade.datatype.visitor.FindAndReplacePropertyVisitor;

/**
 * FindAndReplaceVisitor
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class FindAndReplaceVisitor extends TestConfigurationVisitor {

    private int matches = 0;
    
    private String findPattern;

    private Pattern pattern;

    private String replacement;

    private boolean checkName, checkDescription, checkDocumentation, checkProperties, checkAttributes, regex;

	private FindAndReplacePropertyVisitor propertyVisitor;

    public FindAndReplaceVisitor(String findPattern, String replacement, boolean checkName, boolean checkDescription,
            boolean checkDocumentation, boolean checkProperties, boolean checkAttributes, boolean regex) {
        this.findPattern = findPattern;
        this.replacement = replacement;
        this.checkName = checkName;
        this.checkDescription = checkDescription;
        this.checkDocumentation = checkDocumentation;
        this.checkProperties = checkProperties;
        this.checkAttributes = checkAttributes;
        this.regex = regex;
        this.pattern = Pattern.compile(findPattern);
        if(checkProperties) {
        	propertyVisitor = new FindAndReplacePropertyVisitor(findPattern, replacement, checkName, checkDescription, false, regex);
        }
    }

    /**
     * Return the number of matches
     * 
     * @return the number of matches
     */
    public int getMatches() {
        return this.matches + propertyVisitor.getMatches();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit(TestConfiguration element) throws VisitorException {

        if (this.checkName && element.getName() != null) {
            String result = findAndReplace(element.getName().getValue());

            if (result != null) {
                element.getName().setValue(result);

                if (element.getDatatypeState() == DatatypeState.PERSISTENT) {
                    element.setDatatypeState(DatatypeState.MODIFIED);
                }
            }
        }

        if (this.checkDescription && element.getDescription() != null) {
            String result = findAndReplace(element.getDescription().getValue());

            if (result != null) {
                element.getDescription().setValue(result);

                if (element.getDatatypeState() == DatatypeState.PERSISTENT) {
                    element.setDatatypeState(DatatypeState.MODIFIED);
                }
            }
        }

        super.visit(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit(TestConfigElement element) throws VisitorException {

        if (this.checkName && element.getName() != null) {
            String result = findAndReplace(element.getName().getValue());

            if (result != null) {
                element.getName().setValue(result);

                if (element.getDatatypeState() == DatatypeState.PERSISTENT) {
                    element.setDatatypeState(DatatypeState.MODIFIED);
                }
            }
        }

        if (this.checkDescription && element.getDescription() != null) {
            String result = findAndReplace(element.getDescription().getValue());

            if (result != null) {
                element.getDescription().setValue(result);

                if (element.getDatatypeState() == DatatypeState.PERSISTENT) {
                    element.setDatatypeState(DatatypeState.MODIFIED);
                }
            }
        }

        if (this.checkDocumentation && element.getDocumentation() != null) {
            String result = findAndReplace(element.getDocumentation().getValue());

            if (result != null) {
                element.getDocumentation().setValue(result);

                if (element.getDatatypeState() == DatatypeState.PERSISTENT) {
                    element.setDatatypeState(DatatypeState.MODIFIED);
                }
            }
        }

        super.visit(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit(Property element) throws VisitorException {
        if (this.checkProperties) {
        	try {
				propertyVisitor.visit(element);
			} catch (VisitorException e) {
				// do nothing
			}
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit(AttributeValue element) throws VisitorException {

        if (this.checkAttributes && element instanceof TextAttributeValue) {
            TextAttributeValue textAttribute = (TextAttributeValue) element;

            if (textAttribute.getValue() != null) {
                String result = findAndReplace(textAttribute.getValue().getValue());

                if (result != null) {
                    textAttribute.getValue().setValue(result);

                    if (element.getDatatypeState() == DatatypeState.PERSISTENT) {
                        element.setDatatypeState(DatatypeState.MODIFIED);
                    }
                }
            }
        }

        super.visit(element);
    }

    /**
     * Return a new String with replaced values or null, if no matches were found.
     * 
     * @param value
     * @return
     */
    private String findAndReplace(String value) {

        String result = null;

        if (this.regex) {
            Matcher matcher = this.pattern.matcher(value);
            result = matcher.replaceAll(this.replacement);
        } else {
            result = value.replace(this.findPattern, this.replacement);
        }

        if (!value.equals(result)) {
            this.matches++;
            return result;
        } else {
            return null;
        }
    }

}
