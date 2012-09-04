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

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.result.facade.datatype.ExecutionType;

/**
 * EditExecutionTypeVisitor
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class EditExecutionTypeVisitor extends TestConfigurationVisitor {

    private boolean dirty = false;

    private ExecutionType executionType;

    public EditExecutionTypeVisitor(ExecutionType executionType) {
        this.executionType = executionType;
    }

    @Override
    protected void visit(TestConfigElement element) throws VisitorException {

        if (element.getExecutionType() != this.executionType) {
            Flag manualExecutionAllowed = element.getSchemaElement().getManualExecutionAllowed();

            if (this.executionType == ExecutionType.MANUAL
                    && manualExecutionAllowed != null && manualExecutionAllowed.getValue() != null
                    && manualExecutionAllowed.getValue().booleanValue()) {
                element.setExecutionType(this.executionType);
                element.setDatatypeState(DatatypeState.MODIFIED);
                this.dirty = true;
            } else if (this.executionType == ExecutionType.AUTOMATED) {
                element.setExecutionType(this.executionType);
                element.setDatatypeState(DatatypeState.MODIFIED);
                this.dirty = true;
            }
        }

        super.visit(element);
    }

    public boolean isDirty() {
        return this.dirty;
    }

}
