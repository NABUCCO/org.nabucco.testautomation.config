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

import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.handlers.HandlerService;
import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.command.NabuccoCommand;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;

/**
 * FindAndReplaceCommand<p/>Command to open the Search & Replace Dialog<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2011-12-13
 */
public class FindAndReplaceCommand implements NabuccoCommand {

    private FindAndReplaceHandler findAndReplaceHandler = NabuccoInjector.getInstance(FindAndReplaceCommand.class)
            .inject(FindAndReplaceHandler.class);

    public static final String ID = "org.nabucco.testautomation.config.ui.command.config.FindAndReplaceCommand";

    /** Constructs a new FindAndReplaceCommand instance. */
    public FindAndReplaceCommand() {
        super();
    }
    
    public FindAndReplaceCommand(MasterDetailTreeNode node) {
    	super();
    	findAndReplaceHandler.setNode(node);
    }

    @Override
    public void run() {
        findAndReplaceHandler.findAndReplace();
    }

    @Override
    public String getId() {
        return FindAndReplaceCommand.ID;
    }
}
