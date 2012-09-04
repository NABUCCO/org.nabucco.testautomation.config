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
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.command.NabuccoCommand;

/**
 * ImportDatatypeCommand<p/>This command is used to enable editing of an foreign owned datatype.<p/>
 *
 * @author Markus Jorroch, PRODYNA AG, 2010-04-15
 */
public class ImportDatatypeCommand implements NabuccoCommand {

    private ImportDatatypeHandler importDatatypeHandler = NabuccoInjector.getInstance(ImportDatatypeCommand.class)
            .inject(ImportDatatypeHandler.class);

    public static final String ID = "org.nabucco.testautomation.config.ui.command.config.ImportDatatypeCommand";

    /** Constructs a new ImportDatatypeCommand instance. */
    public ImportDatatypeCommand() {
        super();
    }

    @Override
    public void run() {
        importDatatypeHandler.importDatatype();
    }

    @Override
    public String getId() {
        return ImportDatatypeCommand.ID;
    }
}
