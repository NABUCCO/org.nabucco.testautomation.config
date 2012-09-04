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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model;

import org.eclipse.jface.viewers.ISelection;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.plugin.base.command.CommandHandler;
import org.nabucco.framework.plugin.base.component.multipage.model.MasterDetailAble;
import org.nabucco.framework.plugin.base.component.multipage.model.XmlAble;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;

import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;

/**
 * TestConfigurationMaintenanceMultiPageEditViewModelHandler
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public interface TestConfigurationMaintenanceMultiPageEditViewModelHandler extends
        CommandHandler, MasterDetailAble<Datatype>, XmlAble<Datatype> {

    public TestConfiguration createDefaultDatatype(SchemaConfig schemaConfig);

    public boolean up(ISelection selection);

    public boolean down(ISelection selection);
    
    public boolean replace(ISelection selection);

    boolean hasPossibleChildren(Datatype datatype);

}
