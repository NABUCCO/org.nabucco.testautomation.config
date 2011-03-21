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
package org.nabucco.testautomation.config.ui.rcp.images;

/**
 * Global Registry of all component images.
 * <p>
 * Used to register / create component images with
 * {@link org.nabucco.framework.plugin.base.layout.ImageProvider}.
 * 
 * @author Marco Sussek, PRODYNA AG
 */
public enum ConfigImageRegistry {

	ICON_CONFIG("icons/config/configure.png", "/icons/configure.png"),

	ICON_STEP("icons/config/step.png", "/icons/step.png"),
	
	ICON_RUN("icons/config/run.png", "/icons/run.png"),

	ICON_SCHEMA_64X64("icons/config/schema_64x64.gif", "/icons/schema_64x64.gif"),

	ICON_MANUAL_CHECK_64X64("icons/config/manual_check_64x64.png", "/icons/manual_check_64x64.png"),

	ICON_INFO_64X64("icons/config/info_64x64.png", "/icons/info_64x64.png"),

	ICON_SKIP_8X8("icons/config/skip_8x8.png", "/icons/skip_8x8.png"),
	
	ICON_DETAIL_64X64("icons/config/detail_64x64.png", "/icons/detail_64x64.png"),
	
	ICON_SHEET("icons/project.png", "icons/project.png"),

	ICON_CASE("icons/folder.png", "icons/folder.png"),
	
	ICON_REUSED("icons/config/reused.png", "/icons/reused.png"),
	
	ICON_MANUAL("icons/config/hand_8x8.png", "/icons/hand_8x8.png"),
	
	ICON_CURRENT_ELEMENT("icons/config/arrow.png", "/icons/arrow.png");

	/**
	 * The unique symbolic name of the image used to identify the image in the
	 * <code>ImageProvider</code>.
	 */
	private String id;

	/**
	 * The physical path of the image within the component JAR.
	 */
	private String resourcePath;

	private ConfigImageRegistry(String id, String resourcePath) {
		this.id = id;
		this.resourcePath = resourcePath;
	}

	/**
	 * Gets the unique symbolic name of the image used to identify the image in
	 * the <code>ImageProvider</code>.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the physical path of the image within the component JAR.
	 * 
	 * @return the resourcePath
	 */
	public String getResourcePath() {
		return resourcePath;
	}
}
