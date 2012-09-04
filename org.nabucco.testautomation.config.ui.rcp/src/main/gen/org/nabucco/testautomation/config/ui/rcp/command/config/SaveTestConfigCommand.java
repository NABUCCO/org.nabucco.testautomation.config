/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.command.NabuccoCommand;

/**
 * SaveTestConfigCommand<p/>This command should save a TestConfig<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class SaveTestConfigCommand implements NabuccoCommand {

    private SaveTestConfigHandler saveTestConfigHandler = NabuccoInjector.getInstance(SaveTestConfigCommand.class)
            .inject(SaveTestConfigHandler.class);

    public static final String ID = "org.nabucco.testautomation.config.ui.command.config.SaveTestConfigCommand";

    /** Constructs a new SaveTestConfigCommand instance. */
    public SaveTestConfigCommand() {
        super();
    }

    @Override
    public void run() {
        saveTestConfigHandler.saveTestConfig();
    }

    @Override
    public String getId() {
        return SaveTestConfigCommand.ID;
    }
}
