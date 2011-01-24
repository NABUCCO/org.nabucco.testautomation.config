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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.wizard.schema;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;

public class SchemaSelectionTableNameColumnLabelProvider extends LabelProvider implements ILabelProvider {


	@Override
	public String getText(final Object obj) {
		String result = null;
		if(obj instanceof SchemaConfig){
			String name = ((SchemaConfig) obj).getName().getValue();
			result = name;
		} 
		return result;
	}

}
