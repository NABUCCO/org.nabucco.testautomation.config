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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.wizard.schema;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;

/**
 * SchemaSelectionTableDescriptionColumnLabelProvider
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class SchemaSelectionTableDescriptionColumnLabelProvider extends LabelProvider implements ILabelProvider {


	@Override
	public String getText(final Object obj) {
		String result = null;
		if(obj instanceof SchemaConfig){
			if(((SchemaConfig) obj).getDescription() != null){
				result = ((SchemaConfig) obj).getDescription().getValue();
			}
		} 
		return result;
	}

}
